/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
			default:					throw Err_.new_unhandled(b);
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
