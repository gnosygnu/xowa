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
package gplx.xowa.parsers.miscs;
import gplx.core.btries.Btrie_fast_mgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_lxr;
import gplx.xowa.parsers.Xop_lxr_;
import gplx.xowa.parsers.Xop_parser_tid_;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.Xop_tkn_itm_;
import gplx.xowa.parsers.Xop_tkn_mkr;
import gplx.xowa.parsers.lnkis.Xop_lnki_tkn;
import gplx.xowa.parsers.lnkis.Xop_lnki_wkr_;
import gplx.xowa.parsers.tblws.Xop_tblw_lxr;
import gplx.xowa.parsers.tblws.Xop_tblw_lxr_ws;
import gplx.xowa.parsers.tblws.Xop_tblw_tkn;
import gplx.xowa.parsers.tblws.Xop_tblw_wkr;
public class Xop_pipe_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_pipe;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(AsciiByte.Pipe, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int cur_stack_tid = ctx.Cur_tkn_tid(), rv = -1;
		switch (cur_stack_tid) {
			case Xop_tkn_itm_.Tid_brack_bgn:					// used for tmpl mode where full lnki_wkr is too heavyweight; matches "[ |"
				switch (ctx.Parse_tid()) {
					case Xop_parser_tid_.Tid__defn:
					case Xop_parser_tid_.Tid__tmpl:
						ctx.Subs_add(root, tkn_mkr.Txt(bgn_pos, cur_pos));
						break;
					case Xop_parser_tid_.Tid__wtxt:		// should never happen?
						ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
						break;
					default: throw ErrUtl.NewUnhandled(ctx.Parse_tid());
				}
				return cur_pos;
			case Xop_tkn_itm_.Tid_tblw_tb:
			case Xop_tkn_itm_.Tid_tblw_tr:
				rv = Xop_tblw_lxr_ws.Make(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Xop_tblw_wkr.Tblw_type_td, false);
				if (rv == Xop_tblw_lxr_ws.Tblw_ws_cell_pipe) {
					int prv_nl_pos = BryFind.FindBwd(src, AsciiByte.Nl, cur_pos - 1, 0); if (prv_nl_pos == -1) prv_nl_pos = 0;	// find prv nl
					if (BryLni.Eq(src, prv_nl_pos, prv_nl_pos + 3, Xop_tblw_lxr.Hook_tr)) {					// "\n|-" aka tblw_tr
						int nl_pos = BryFind.FindFwd(src, AsciiByte.Nl, cur_pos, src_len); if (nl_pos == BryFind.NotFound) nl_pos = src_len;
						ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, nl_pos, Xop_ignore_tkn.Ignore_tid_tr_w_td));	// gobble up rest of content between "|" and "\n"; PAGE:lv.w:Starptautiska_kosmosa_stacija; DATE:2015-11-21
						return nl_pos;
					}
					else {
						ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
						return cur_pos;
					}
				}
				if (rv == Xop_tblw_lxr_ws.Tblw_ws_cell_pipe) {
					ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
					return cur_pos;
				}
				else
					return rv;
			case Xop_tkn_itm_.Tid_tblw_td:
			case Xop_tkn_itm_.Tid_tblw_th:
			case Xop_tkn_itm_.Tid_tblw_tc:
				rv = Xop_tblw_lxr_ws.Make(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Xop_tblw_wkr.Tblw_type_td, false);
				if (rv != Xop_tblw_lxr_ws.Tblw_ws_cell_pipe) return rv;

				if (ctx.Tblw().Cell_pipe_seen()) {
					ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
					return cur_pos;
				}
				else {
					Xop_tblw_tkn cur_tkn = (Xop_tblw_tkn)ctx.Stack_get_typ(cur_stack_tid);
					Xop_tblw_wkr.Atrs_make(ctx, src, root, ctx.Tblw(), cur_tkn, BoolUtl.N);
					return cur_pos;
				}
			case Xop_tkn_itm_.Tid_lnki:
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)ctx.Stack_get_last();	// BLOCK:invalid_ttl_check
				if (	lnki.Pipe_count_is_zero()
					&&	!Xop_lnki_wkr_.Parse_ttl(ctx, src, lnki, bgn_pos)) {
					ctx.Stack_pop_last();
					return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
				}
				ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
				return cur_pos;
			default:
				ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
				return cur_pos;
		}
	}
	public static final Xop_pipe_lxr Instance = new Xop_pipe_lxr();
}
