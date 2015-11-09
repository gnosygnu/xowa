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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Html_atr_cls_ {
	public static boolean Has(byte[] src, int src_bgn, int src_end, byte[] cls) {
		int cls_bgn = src_bgn; 
		int pos = src_bgn;
		while (true) {
			boolean pos_is_last = pos == src_end;
			byte b = pos_is_last ? Byte_ascii.Space : src[pos];
			if (b == Byte_ascii.Space) {
				if (cls_bgn != -1) {
					if (Bry_.Match(src, cls_bgn, pos, cls))return true;
					cls_bgn = -1;
				}
			}
			else {
				if (cls_bgn == -1) cls_bgn = pos;
			}
			if (pos_is_last) break;
			++pos;
		}
		return false;
	}
	public static byte Find_1st(byte[] src, int src_bgn, int src_end, Hash_adp_bry hash) {
		int cls_bgn = src_bgn; 
		int pos = src_bgn;
		while (true) {
			boolean pos_is_last = pos == src_end;
			byte b = pos_is_last ? Byte_ascii.Space : src[pos];
			if (b == Byte_ascii.Space) {
				if (cls_bgn != -1) {
					byte rv = hash.Get_as_byte_or(src, cls_bgn, pos, Byte_.Max_value_127);
					if (rv != Byte_.Max_value_127) return rv;
					cls_bgn = -1;
				}
			}
			else {
				if (cls_bgn == -1) cls_bgn = pos;
			}
			if (pos_is_last) break;
			++pos;
		}
		return Byte_.Max_value_127;
	}
}
