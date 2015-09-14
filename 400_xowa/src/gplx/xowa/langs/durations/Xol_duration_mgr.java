/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.langs.durations; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.*;
public class Xol_duration_mgr {
	private Xol_msg_itm[] interval_msgs = null;
	public Xol_duration_mgr(Xol_lang lang) {this.lang = lang;} private Xol_lang lang;
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Xol_interval_itm[] Get_duration_intervals(long seconds, Xol_duration_itm[] intervals) {
		if (intervals == null) intervals = Xol_duration_itm_.Ary_default;
		Array_.Sort(intervals, Xol_duration_itm_sorter._);
		int intervals_len = intervals.length;
		long val = seconds;
		List_adp rv = List_adp_.new_();
		for (int i = 0; i < intervals_len; i++) {
			Xol_duration_itm itm = intervals[i];
			long itm_seconds = itm.Seconds();
			val = seconds / itm_seconds;
			if (	val > 0
				||	(i == intervals_len - 1 && rv.Count() == 0) // always add one seg; EX: 40 seconds, but minutes requested -> 0 minutes; DATE:2014-05-10
			) {
				seconds -= val * itm_seconds;
				rv.Add(new Xol_interval_itm(itm, val));
			}
		}
		return (Xol_interval_itm[])rv.To_ary(Xol_interval_itm.class);
	}
	public byte[] Format_durations(Xop_ctx ctx, long seconds, Xol_duration_itm[] ary) {
		if (interval_msgs == null) Format_durations_init();
		Xol_interval_itm[] intervals = Get_duration_intervals(seconds, ary);
		int intervals_len = intervals.length;
		byte[][] msgs_ary = new byte[intervals_len][];
		for (int i = 0; i < intervals_len; i++) {
			Xol_interval_itm interval = intervals[i];
			Xol_msg_itm msg_itm = interval_msgs[interval.Duration_itm().Tid()];
			byte[] msg_bry = msg_itm.Fmt(tmp_bfr, interval.Val());
			msg_bry = ctx.Wiki().Parser().Parse_text_to_html(ctx, msg_bry);
			msgs_ary[i] = msg_bry;
		}
		return List_to_str(msgs_ary);
	}
	private byte[] Msg_and, Msg_word_separator, Msg_comma_separator;
	private void Format_durations_init() {
		Xol_msg_mgr msg_mgr = lang.Msg_mgr();
		int len = Xol_duration_itm_.Ary_default.length;
		interval_msgs = new Xol_msg_itm[len];
		for (int i = 0; i < len; i++) {
			Xol_duration_itm itm = Xol_duration_itm_.Ary_default[i];
			byte[] msg_key = Bry_.Add(Bry_duration, itm.Name_bry());
			interval_msgs[i] = msg_mgr.Itm_by_key_or_new(msg_key);
		}
	}	private static final byte[] Bry_duration = Bry_.new_a7("duration-");
	private void List_to_str_init() {
		Xol_msg_mgr msg_mgr = lang.Msg_mgr();
		Msg_and = msg_mgr.Val_by_str_or_empty("and");
		Msg_word_separator = msg_mgr.Val_by_str_or_empty("word-separator");
		Msg_comma_separator = msg_mgr.Val_by_str_or_empty("comma-separator");
	}
	
	public byte[] List_to_str(byte[][] segs_ary) {
		int len = segs_ary.length;
		switch (len) {
			case 0: return Bry_.Empty;
			case 1: return segs_ary[0];
			default:
				if (Msg_and == null) List_to_str_init();
				int last_idx = len - 1;
				for (int i = 0; i < last_idx; i++) {
					if (i != 0) tmp_bfr.Add(Msg_comma_separator);
					tmp_bfr.Add(segs_ary[i]);
				}
				tmp_bfr.Add(Msg_and).Add(Msg_word_separator).Add(segs_ary[last_idx]);
				return tmp_bfr.Xto_bry_and_clear();
		}
	}
}
