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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_claim_itm_time extends Wdata_claim_itm_core { 	public Wdata_claim_itm_time(int pid, byte snak_tid, byte[] time, byte[] timezone, byte[] before, byte[] after, byte[] precision, byte[] calendar) {
		this.Ctor(pid, snak_tid);
		this.time = time; this.before = before; this.after = after; this.precision = precision; this.calendar = calendar;
	}
	@Override public byte Val_tid() {return Wdata_dict_val_tid.Tid_time;}
	public byte[] Time() {return time;} private final byte[] time;
	public Wdata_date Time_as_date() {
		if (time_as_date == null) time_as_date = Wdata_date.parse(time, this.Precision_int(), this.Before_int(), this.After_int(), this.Calendar_is_julian());
		return time_as_date;
	}	private Wdata_date time_as_date;
	public byte[] Before() {return before;} private final byte[] before;
	public byte[] After() {return after;} private final byte[] after;
	public byte[] Precision() {return precision;} private final byte[] precision;
	public int Precision_int() {
		if (precision_int == Int_.Min_value) {
			precision_int = Bry_.To_int_or(precision, -1);
			if (precision_int == -1) {
				precision_int = Wdata_date.Fmt_ymdhns;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown precision: ~{0}", String_.new_u8(precision));
			}
		}
		return precision_int;
	}	private int precision_int = Int_.Min_value;
	public int Before_int() {
		if (before_int == Int_.Min_value) {
			before_int = Bry_.To_int_or(before, -1);
			if (before_int == -1) {
				before_int = 0;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown before: ~{0}", String_.new_u8(before));
			}
		}
		return before_int;
	}	private int before_int = Int_.Min_value;
	public int After_int() {
		if (after_int == Int_.Min_value) {
			after_int = Bry_.To_int_or(after, -1);
			if (after_int == -1) {
				after_int = 0;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown after: ~{0}", String_.new_u8(after));
			}
		}
		return after_int;
	}	private int after_int = Int_.Min_value;
	public byte[] Calendar() {return calendar;} private final byte[] calendar;
	public byte[] Calendar_ttl() {return calendar_ttl;} public void Calendar_ttl_(byte[] v) {calendar_ttl = v;} private byte[] calendar_ttl;
	public boolean Calendar_is_julian() {return Bry_.Eq(calendar, Calendar_julian);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()), String_.new_u8(time), String_.new_u8(before), String_.new_u8(after), String_.new_u8(precision), String_.new_u8(calendar));
	}
	@Override public void Welcome(Wdata_claim_visitor visitor) {visitor.Visit_time(this);}
	public void Write_to_bfr(Bry_bfr bfr, Bry_bfr tmp_time_bfr, Bry_fmtr tmp_time_fmtr, Wdata_hwtr_msgs msgs, byte[] ttl) {
		try {
			Wdata_date date = this.Time_as_date();
			boolean calendar_is_julian = this.Calendar_is_julian();
			byte[] calendar_display = null;
			if (calendar_is_julian) {
				date = Wdata_date.Xto_julian(date);
				calendar_display = msgs.Time_julian();
			}
			Wdata_date.Xto_str(bfr, tmp_time_fmtr, tmp_time_bfr, msgs, date);
			if (calendar_display != null)
				bfr.Add_byte_space().Add(calendar_display);
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to write time; ttl=~{0} pid=~{1} err=~{2}", ttl, this.Pid(), Err_.Message_gplx_log(e));
		}
	}
	private static final byte[] Calendar_julian = Bry_.new_a7("http://www.wikidata.org/entity/Q1985786");
}
