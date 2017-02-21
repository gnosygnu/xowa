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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*;
import gplx.xowa.parsers.hdrs.sections.*;
public class Xop_hdr_wkr implements Xop_ctx_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		// bgn never closed; mark inert; EX: "==a"
		Xop_hdr_tkn bgn = (Xop_hdr_tkn)tkn;
		int bgn_hdr_len = bgn.Num();
		bgn.Init_by_parse(0, bgn_hdr_len, 0);
		if (bgn_hdr_len > 1 && ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt)	// NOTE: \n= is not uncommon for templates; ignore them;
			ctx.Msg_log().Add_itm_none(Xop_hdr_log.Dangling_hdr, src, bgn.Src_bgn(), bgn_pos);	
	}
	public int Make_tkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) bgn_pos = 0;	// do not allow -1 pos
		ctx.Apos().End_frame(ctx, root, src, bgn_pos, false);
		Close_open_itms(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);
		ctx.Para().Process_block__bgn__nl_w_symbol(ctx, root, src, bgn_pos, cur_pos, Xop_xnde_tag_.Tag__h2);	// pass h2; should pass h# where # is correct #, but for purpose of Para_wkr, <h2> tag does not matter
		int new_pos = Bry_find_.Find_fwd_while(src, cur_pos, src_len, Xop_hdr_lxr.Hook);				// count all =
		int hdr_len = new_pos - cur_pos + 1;														// +1 b/c Hook has 1 eq: "\n="
		switch (hdr_len) {
			case 1: ctx.Msg_log().Add_itm_none(Xop_hdr_log.Len_1, src, bgn_pos, new_pos); break;			// <h1>; flag
			case 2: case 3: case 4: case 5: case 6: break;													// <h2>-<h6>: normal
			default: ctx.Msg_log().Add_itm_none(Xop_hdr_log.Len_7_or_more, src, bgn_pos, new_pos); break;	// <h7>+; limit to 6; flag; NOTE: only 14 pages in 2011-07-27
		}

		Xop_hdr_tkn tkn = tkn_mkr.Hdr(bgn_pos, new_pos, hdr_len);	// make tkn
		ctx.StackTkn_add(root, tkn);
		return new_pos;
	}
	public int Make_tkn_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int stackPos, int end_hdr_len) {// REF.MW: Parser|doHeadings
		if (ctx.Cur_tkn_tid() == Xop_tkn_itm_.Tid_tmpl_curly_bgn) return ctx.Lxr_make_txt_(cur_pos);

		// end frame
		Xop_hdr_tkn hdr = (Xop_hdr_tkn)ctx.Stack_pop_til(root, src, stackPos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_hdr);
		ctx.Apos().End_frame(ctx, root, src, bgn_pos, false);	// end any apos; EX: ==''a==

		// handle asymmetrical "="; EX: "== A ==="
		int hdr_len = hdr.Num(), bgn_manual = 0, end_manual = 0;
		boolean dirty = false;
		if		(end_hdr_len < hdr_len) {	// mismatch: end has more; adjust hdr
			bgn_manual = hdr_len - end_hdr_len;
			hdr_len = end_hdr_len;
			ctx.Msg_log().Add_itm_none(Xop_hdr_log.Mismatched, src, bgn_pos, cur_pos);
			if (hdr_len == 1) ctx.Msg_log().Add_itm_none(Xop_hdr_log.Len_1, src, bgn_pos, cur_pos);
			dirty = true;
		}
		else if (end_hdr_len > hdr_len) {	// mismatch: hdr has more; adjust variables
			end_manual = end_hdr_len - hdr_len;
			ctx.Msg_log().Add_itm_none(Xop_hdr_log.Mismatched, src, bgn_pos, cur_pos);
			dirty = true;
		}
		if (hdr_len > 6) {					// <h7>+; limit to 6; NOTE: make both bgn/end are equal length; EX: bgn=8,end=7 -> bgn=7,end=7;bgn_manual=1
			bgn_manual = end_manual = hdr_len - 6;
			hdr_len = 6;
			dirty = true;
		}
		if (dirty)
			hdr.Init_by_parse(hdr_len, bgn_manual, end_manual);

		// gobble ws; hdr gobbles up trailing ws; EX: "==a== \n\t \n \nb" gobbles up all 3 "\n"s; otherwise para_wkr will process <br/> 
		cur_pos = Find_fwd_while_ws_hdr_version(src, cur_pos, src_len);
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__h2);

		// add to root tkn; other post-processing
		hdr.Subs_move(root);
		hdr.Src_end_(cur_pos);
		if (ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt) {	// do not add if defn / tmpl mode
			ctx.Page().Wtxt().Toc().Add(hdr);
		}
		return cur_pos;
	}
	private void Close_open_itms(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int stack_pos = -1, stack_len = ctx.Stack_len(); boolean stop = false;
		for (int i = 0; i < stack_len; i++) {				// loop over stack
			Xop_tkn_itm prv_tkn = ctx.Stack_get(i);
			switch (prv_tkn.Tkn_tid()) {					// find first list/hdr; close everything until this
				case Xop_tkn_itm_.Tid_list:
				case Xop_tkn_itm_.Tid_hdr:
					stack_pos = i; stop = true; break;
			}
			if (stop) break;
		}
		if (stack_pos == -1) return;
		ctx.Stack_pop_til(root, src, stack_pos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_hdr);
	}
	private static int Find_fwd_while_ws_hdr_version(byte[] src, int cur, int end) {
		int last_nl = -1;
		while (true) {
			if (cur == end) return cur;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Nl:
					cur++;
					last_nl = cur;
					break;
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					cur++;
					break;
				default:
					return last_nl == -1 ? cur : last_nl - 1;
			}
		}
	}
}
/*
NOTE:hdr.trailing_nl
. by design, the hdr_tkn's src_end will not include the trailing \n
.. for example, for "\n==a==\n", the src_bgn will be 0, but the src_end will be 6
.. note that at 6, it does not include the \n at pos 6
. this is needed to leave the \n for the parser to handle other tkns, such as hdrs, tblws, lists.
. for example, in "\n==a==\n*b", if the \n at pos 6 was taken by the hdr_tkn, then the parser would encounter a "*" instead of a "\n*"
*/
