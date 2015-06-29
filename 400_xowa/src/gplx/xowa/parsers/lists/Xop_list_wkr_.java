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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_list_wkr_ {
	public static byte[] MakeSymAry(byte[] curSymAry, int curSymLen) {
		byte[] rv = new byte[curSymLen];
		for (int i = 0; i < curSymLen; i++)
			rv[i] = curSymAry[i];
		return rv;
	}
	public static byte Compare_normalize(byte b) {	// convert : to ; for sake of determining levels; EX: ";:" is actually same group
		switch (b) {
			case Byte_ascii.Star:
			case Byte_ascii.Hash:
			case Byte_ascii.Semic:		return b;
			case Byte_ascii.Colon:		return Byte_ascii.Semic;
			default:					throw Err_.unhandled(b);
		}
	}
	public static void Close_list_if_present(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int bgn_pos, int cur_pos) {// close all list tkns on stack; EX: ***\n should close all 3 stars; used to only close 1
		if (ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tmpl_invk) != Xop_ctx.Stack_not_found) return; // list is inside template; do not close;
		int acs_pos = -1, acs_len = ctx.Stack_len();
		for (int i = acs_len - 1; i > -1; i--) {				// loop backwards until earliest list tkn
			byte cur_acs_tid = ctx.Stack_get(i).Tkn_tid();
			switch (cur_acs_tid) {
				case Xop_tkn_itm_.Tid_tblw_tb:
				case Xop_tkn_itm_.Tid_tblw_tc:
				case Xop_tkn_itm_.Tid_tblw_te:
				case Xop_tkn_itm_.Tid_tblw_td:
				case Xop_tkn_itm_.Tid_tblw_th:
				case Xop_tkn_itm_.Tid_tblw_tr:	i = -1;			break;	// tblw: stop loop; do not close a list above tbl; EX: ": {| |- *a |b }" should not close ":"; stops at "|-"
				case Xop_tkn_itm_.Tid_list:		acs_pos = i;	break;	// list: update acs_pos
				default:										break;	// else: keep looping 
			}
		}
		if (acs_pos == Xop_ctx.Stack_not_found) return;					// no list tokens found; exit
		ctx.Stack_pop_til(root, src, acs_pos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_list);
	}
}
