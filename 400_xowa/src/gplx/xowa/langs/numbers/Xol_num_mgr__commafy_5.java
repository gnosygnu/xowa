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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
class Xol_num_mgr__commafy_5 extends Xol_num_mgr { 	@Override public byte[] Commafy(byte[] num) {
		if (Bry_.Len_eq_0(num)) return num;	// bounds check
		int num_len = num.length;
		int num_bgn = 0;
		byte b = num[num_bgn];
		if (b == Byte_ascii.Dash) {
			if (num_len == 1) return num;	// bounds check
			b = num[++num_bgn];				// skip negative sign
		}
		if (Byte_ascii.Is_num(b)) {			// check for preg_match( '/^-?\d{1,4}(\.\d+)?$/', $_ )
			int num_end = Bry_find_.Find_fwd_while_num(num, num_bgn, num_len);
			if (num_end - num_bgn < 5) {	// 1-4 digits
				if (num_end == num_len) return num; // no decimal; exit
				b = num[num_end];
				if (	b == Byte_ascii.Dot	
					&&	num_end != num_len - 1) {	// if dot at end, then no match on above regx; fall-thru to below
					num_end = Bry_find_.Find_fwd_while_num(num, num_end + 1, num_len);
					if (num_end == num_len) return num;	// only numbers after dot; matches regx;
				}
			}
		}
		return this.Num_grp_fmtr().Fmt_regx(tmp_bfr, num);	// otherwise do default grouping; '/(\d{3})(?=\d)(?!\d*\.)/', '$1,'
	}
}
class Xol_num_mgr__noop extends Xol_num_mgr { 	@Override public byte[] Commafy(byte[] num) {return num;}
}
