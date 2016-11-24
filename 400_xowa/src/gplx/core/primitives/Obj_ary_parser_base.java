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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public abstract class Obj_ary_parser_base {
	int pos_len = 4; int[] pos;
	protected abstract void Ary_len_(int v);
	protected abstract void Parse_itm(byte[] bry, int bgn, int end);
	protected void Parse_core(byte[] bry, int bgn, int end, byte dlm_1, byte dlm_2) {
		if (pos == null) pos = new int[pos_len];
		if (end - bgn == 0) {
			this.Ary_len_(0);
			return;
		}
		int pos_idx = 0;
		int dlm_last = -1;	// NOTE: -1 b/c dlm_2 can be 0 (Byte_ascii.Null) and need to do dlm_last != dlm_2 check below
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			if (b == dlm_1 || b == dlm_2) {
				if (pos_idx == pos_len - 1) {	// -1 b/c pos[] will always be count_of_dlm + 1
					pos_len *= 2;
					int[] pos_new = new int[pos_len];
					Array_.Copy(pos, pos_new);
					pos = pos_new;
				} 
				pos[pos_idx++] = i;
				dlm_last = b;
			}
		}
		if (dlm_last != dlm_2)
			pos[pos_idx++] = end;
		this.Ary_len_(pos_idx);
		int parse_bgn = bgn; 
		for (int i = 0; i < pos_idx; i++) {
			int parse_end = pos[i];
			this.Parse_itm(bry, parse_bgn, parse_end);
			parse_bgn = parse_end + 1;
		}
		if (pos_len > 255) {	// reset
			pos_len = 4;
			pos = null;
		}
	}	
}