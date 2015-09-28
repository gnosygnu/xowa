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
package gplx;
import gplx.core.brys.*;
public class Bry_split_ {
	private static final Object thread_lock = new Object();
	public static byte[][] Split(byte[] src, byte dlm) {return Split(src, dlm, false);}
	public static byte[][] Split(byte[] src, byte dlm, boolean trim) {
		synchronized (thread_lock) {
			Bry_split_wkr__to_ary wkr = Bry_split_wkr__to_ary.I;
			Split(src, 0, src == null ? 0 : src.length, dlm, trim, wkr);
			return wkr.To_ary();
		}
	}
	public static int Split(byte[] src, int src_bgn, int src_end, byte dlm, boolean trim, Bry_split_wkr wkr) {
		if (src == null || src_end - src_bgn < 1) return 0;
		int pos = src_bgn; 
		int itm_bgn = -1, itm_end = -1;
		int count = 0;
		while (true) {
			boolean pos_is_last = pos == src_end;
			byte b = pos_is_last ? dlm : src[pos];
			int nxt_pos = pos + 1;
			boolean process = true;
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: // ignore ws; assumes that flags have no ws (they are single char) and vnts have no ws (EX: zh-hans)
					if (trim && b != dlm) process = false;			// b != dlm handles cases where ws is dlm, but trim is enabled; EX: " a \n b" -> "a", "b"
					break; 
			}
			if (process) {
				if (b == dlm) {
					boolean reset = true;
					if (itm_bgn == -1) {
						if (pos_is_last) {}							// skip dlm at bgn / end; EX: "a,"
						else {wkr.Split(src, itm_bgn, itm_end);}	// else, process "empty" dlm; EX: ",a"
					}	
					else {
						int rv = wkr.Split(src, itm_bgn, itm_end);
						switch (rv) {
							case Rv__ok:		++count; break;
							case Rv__extend:	reset = false; break;
							case Rv__cancel:	return count;
							default:			throw Err_.new_unhandled(rv);
						}
					}
					if (reset) itm_bgn = itm_end = -1;
				}
				else {
					if (itm_bgn == -1) itm_bgn = pos;
					itm_end = nxt_pos;
				}
			}
			if (pos_is_last) break;
			pos = nxt_pos;
		}
		return count;
	}
	public static byte[][] Split(byte[] src, byte[] dlm) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int cur_pos = 0, src_len = src.length, dlm_len = dlm.length;
		List_adp rv = List_adp_.new_();
		while (true) {
			int find_pos = Bry_find_.Find_fwd(src, dlm, cur_pos);
			if (find_pos == Bry_.NotFound) {
				if (cur_pos == src_len) break;	// dlm is last sequence in src; do not create empty itm
				find_pos = src_len;			
			}
			rv.Add(Bry_.Mid(src, cur_pos, find_pos));
			if (find_pos == src_len) break;
			cur_pos = find_pos + dlm_len;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
	public static byte[][] Split_lines(byte[] src) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int src_len = src.length, src_pos = 0, fld_bgn = 0;
		List_adp rv = List_adp_.new_();
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? Byte_ascii.Nl : src[src_pos];
			int nxt_bgn = src_pos + 1; 
			switch (b) {
				case Byte_ascii.Cr:
				case Byte_ascii.Nl:
					if (	b == Byte_ascii.Cr		// check for crlf
						&& nxt_bgn < src_len && src[nxt_bgn] == Byte_ascii.Nl) {
							++nxt_bgn;
					}
					if (last && (src_pos - fld_bgn == 0)) {}	// ignore trailing itms
					else
						rv.Add(Bry_.Mid(src, fld_bgn, src_pos));
					fld_bgn = nxt_bgn;
					break;
			}
			if (last) break;
			src_pos = nxt_bgn;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
	public static final int Rv__ok = 0, Rv__extend = 1, Rv__cancel = 2;
}
class Bry_split_wkr__to_ary implements gplx.core.brys.Bry_split_wkr {
	private final List_adp list = List_adp_.new_();
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		synchronized (list) {
			byte[] bry = itm_end == itm_bgn ? Bry_.Empty : Bry_.Mid(src, itm_bgn, itm_end);
			list.Add(bry);
			return Bry_split_.Rv__ok;
		}
	}
	public byte[][] To_ary() {
		synchronized (list) {
			return (byte[][])list.To_ary_and_clear(byte[].class);
		}
	}
        public static final Bry_split_wkr__to_ary I = new Bry_split_wkr__to_ary(); Bry_split_wkr__to_ary() {}
}
