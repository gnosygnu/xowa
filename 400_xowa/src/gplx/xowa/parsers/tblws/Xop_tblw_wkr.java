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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.lists.*; import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.miscs.*;
public class Xop_tblw_wkr implements Xop_ctx_wkr {
	private int tblw_te_ignore_count = 0;
	public boolean Cell_pipe_seen() {return cell_pipe_seen;} public Xop_tblw_wkr Cell_pipe_seen_(boolean v) {cell_pipe_seen = v; return this;} private boolean cell_pipe_seen; // status of 1st cell pipe; EX: \n| a | b | c || -> flag pipe between a and b but ignore b and c
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {cell_pipe_seen = false; tblw_te_ignore_count = 0;}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public void AutoClose(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		tkn.Subs_move(root);
		tkn.Src_end_(cur_pos);
	}
	public static final byte Called_from_general = 0, Called_from_list = 1, Called_from_pre = 2;
	public int Make_tkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, boolean tbl_is_xml, byte wlxr_type, byte called_from, int atrs_bgn, int atrs_end) {// REF.MW: Parser|doTableStuff
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) {
			bgn_pos = 0;	// do not allow -1 pos
		}

		int list_tkn_idx = ctx.Stack_idx_find_but_stop_at_tbl(Xop_tkn_itm_.Tid_list);
		if (	list_tkn_idx != -1								// list is in effect; DATE:2014-05-05
			&& !tbl_is_xml										// tbl is wiki-syntax; ie: auto-close if "{|" but do not close if "<table>"; DATE:2014-02-05
			&& called_from != Called_from_list					// do not close if called from list; EX: consider "{|"; "* a {|" is called from list_wkr, and should not close; "* a\n{|" is called from tblw_lxr and should close; DATE:2014-02-14
			) {
			if (wlxr_type == Tblw_type_td2) {					// if in list, treat "||" as lnki, not tblw; EX: es.d:casa; es.d:tres; DATE:2014-02-15
				ctx.Subs_add(root, ctx.Tkn_mkr().Pipe(bgn_pos, cur_pos));	// NOTE: technically need to check if pipe or pipe_text; for now, do pipe as pipe_text could break [[File:A.png||20px]]; DATE:2014-05-06
				return cur_pos;
			}
			else {
				Xop_list_wkr_.Close_list_if_present(ctx, root, src, bgn_pos, cur_pos);
			}
		}
		if (ctx.Apos().Stack_len() > 0)							// open apos; note that apos keeps its own stack, as they are not "structural" (not sure about this)
			ctx.Apos().End_frame(ctx, root, src, cur_pos, true);// close it
		Xop_tblw_tkn prv_tkn = ctx.Stack_get_tbl();
		if (	prv_tkn == null									// prv_tkn not found; i.e.: no earlier "{|" or "<table>"
			|| (	ctx.Stack_get_tblw_tb() == null				// no {| on stack; DATE:2014-05-05
				&&	!tbl_is_xml									// and cur is tblw (i.e.: not xnde); DATE:2014-05-05
				)
			) {
			switch (wlxr_type) {
				case Tblw_type_tb: {							// "{|";
					// close para when table starts; needed for TRAILING_TBLW fix; PAGE:en.w:Template_engine_(web) DATE:2017-04-08
					ctx.Para().Process_block__bgn__nl_w_symbol(ctx, root, src, bgn_pos, cur_pos - 1, Xop_xnde_tag_.Tag__table);	// -1 b/c cur_pos includes sym_byte; EX: \n{
					break;										//	noop; by definition "{|" does not need to have a previous "{|"
				}
				case Tblw_type_td:								// "|"
				case Tblw_type_td2:								// "||"
					if (tbl_is_xml) {							// <td> should automatically add <table><tr>
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, tkn_mkr.Tblw_tb(bgn_pos, bgn_pos, tbl_is_xml, true));
						prv_tkn = tkn_mkr.Tblw_tr(bgn_pos, bgn_pos, tbl_is_xml, true);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, prv_tkn);
						break;
					}					
					else {
						if (called_from == Called_from_pre)
							return -1;
						else {	// DATE:2014-02-19; NOTE: do not add nl if ||; DATE:2014-04-14							
							if (wlxr_type == Tblw_type_td) {	// "\n|"
								ctx.Subs_add(root, ctx.Tkn_mkr().NewLine(bgn_pos, bgn_pos + 1, Xop_nl_tkn.Tid_char, 1));
								ctx.Subs_add(root, ctx.Tkn_mkr().Pipe(bgn_pos + 1, cur_pos));
							}
							else								// "||"
								ctx.Subs_add(root, ctx.Tkn_mkr().Pipe(bgn_pos, cur_pos));
							return cur_pos;
						}
					}
				case Tblw_type_th:								// "!"
				case Tblw_type_th2:								// "!!"
				case Tblw_type_tc:								// "|+"
				case Tblw_type_tr:								// "|-"
					if (tbl_is_xml) {							// <tr> should automatically add <table>; DATE:2014-02-13
						prv_tkn = tkn_mkr.Tblw_tb(bgn_pos, bgn_pos, tbl_is_xml, true);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, prv_tkn);
						break;
					}
					else {
						if (called_from == Called_from_pre)
							return -1;
						else
							return Xop_tblw_wkr.Handle_false_tblw_match(ctx, root, src, bgn_pos, cur_pos, ctx.Tkn_mkr().Txt(bgn_pos + 1, cur_pos), true);	// DATE:2014-02-19
					}
				case Tblw_type_te:								// "|}"
					if (tblw_te_ignore_count > 0) {
						--tblw_te_ignore_count;
						return cur_pos;
					}
					else {
						if (called_from == Called_from_pre)
							return -1;
						else
							return Xop_tblw_wkr.Handle_false_tblw_match(ctx, root, src, bgn_pos, cur_pos, tkn_mkr.Txt(bgn_pos + 1, cur_pos), true);	// +1 to skip "\n" in "\n|}" (don't convert \n to text); DATE:2014-02-19
					}
				default: throw Err_.new_unhandled(wlxr_type);
			}
		}

		int prv_tid = prv_tkn == null ? Xop_tkn_itm_.Tid_null : prv_tkn.Tkn_tid();
		if (prv_tkn != null && !prv_tkn.Tblw_xml()) {			// note that this logic is same as Atrs_close; repeated here for "perf"
			switch (prv_tid) {
				case Xop_tkn_itm_.Tid_tblw_tb: case Xop_tkn_itm_.Tid_tblw_tr:
					Atrs_make(ctx, src, root, this, prv_tkn, Bool_.N);
					break;
			}
		}
		if (wlxr_type == Tblw_type_te)
			return Make_tkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_tblw_te, wlxr_type, prv_tkn, prv_tid, tbl_is_xml);
		else
			return Make_tkn_bgn_tblw(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, wlxr_type, tbl_is_xml, atrs_bgn, atrs_end, prv_tkn, prv_tid);
	}
	private int Make_tkn_bgn_tblw(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, byte wlxr_type, boolean tbl_is_xml, int atrs_bgn, int atrs_end, Xop_tblw_tkn prv_tkn, int prv_tid) {
		if (wlxr_type != Tblw_type_tb)	// NOTE: do not ignore ws if {|; will cause strange behavior with pre; DATE:2013-02-12
			Ignore_ws(ctx, root);
		Xop_tblw_tkn new_tkn = null;
		switch (wlxr_type) {
			case Tblw_type_tb:								// <table>
				boolean ignore_prv = false, auto_create = false;
				switch (prv_tid) {
					case Xop_tkn_itm_.Tid_null:				// noop; <table>
						break;
					case Xop_tkn_itm_.Tid_tblw_td:			// noop; <td><table>
					case Xop_tkn_itm_.Tid_tblw_th:			// noop; <th><table>
						break;
					case Xop_tkn_itm_.Tid_tblw_tb:			// fix;  <table><table>			-> <table>; ignore current table; DATE:2014-02-02
						if (prv_tkn.Tblw_xml()) {			// fix:  <table><table>			-> <table>; earlier tbl is xnde; ignore; EX:en.b:Wikibooks:Featured books; DATE:2014-02-08
							((Xop_tblw_tb_tkn)prv_tkn).Tblw_xml_(false);	// if <table>{|, discard <table>, but mark {| as <table>; needed to handle <table>\n{|\n| where "|" must be treated as tblw dlm; DATE:2014-02-22
							ignore_prv = true;
						}
						// else								// fix:  <table><table>			-> <table><tr><td><table>; earlier tbl is tblw; auto-create; EX:it.w:Main_Page; DATE:2014-02-08; TIDY:depend on tidy to fix; PAGE: it.w:Portal:Animali; DATE:2014-05-31
						//	auto_create = true;
						break;
					case Xop_tkn_itm_.Tid_tblw_tr:			// noop: <table><tr><table>     -> <table><tr><td><table>; should probably auto-create td, but MW does not; DATE:2014-03-18
					case Xop_tkn_itm_.Tid_tblw_tc:			// noop; <caption><table>; TIDY:was <caption></caption><tr><td><table>; PAGE: es.w:Savilla DATE:2014-06-29
						break;
				}
				if		(ignore_prv) {
					ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, cur_pos, Xop_ignore_tkn.Ignore_tid_htmlTidy_tblw));
					++tblw_te_ignore_count;
					cur_pos = Bry_find_.Find_fwd_until(src, cur_pos, src_len, Byte_ascii.Nl);	// NOTE: minor hack; this tblw tkn will be ignored, so ignore any of its attributes as well; gobble up all chars till nl. see:  if two consecutive tbs, ignore attributes on 2nd; en.wikibooks.org/wiki/Wikibooks:Featured books
					return cur_pos;
				}
				if (auto_create) {
					ctx.Subs_add_and_stack_tblw(root, prv_tkn, tkn_mkr.Tblw_tr(bgn_pos, bgn_pos, tbl_is_xml, true));
					ctx.Subs_add_and_stack_tblw(root, prv_tkn, tkn_mkr.Tblw_td(bgn_pos, bgn_pos, tbl_is_xml));
				}
				Xop_tblw_tb_tkn tb_tkn = tkn_mkr.Tblw_tb(bgn_pos, cur_pos, tbl_is_xml, false);
				new_tkn = tb_tkn;
				break;
			case Tblw_type_tr:								// <tr>
				switch (prv_tid) {
					case Xop_tkn_itm_.Tid_tblw_tb: break;	// noop; <table><tr>
					case Xop_tkn_itm_.Tid_tblw_tc:			// fix;  <caption><tr>      -> <caption></caption><tr>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tc), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						break;
					case Xop_tkn_itm_.Tid_tblw_td:			// fix;  <td><tr>           -> <td></td></tr><tr>
					case Xop_tkn_itm_.Tid_tblw_th:			// fix;  <th><tr>           -> <th></th></tr><tr>
						if (!tbl_is_xml)
							ctx.Para().Process_nl(ctx, root, src, bgn_pos, bgn_pos + 1);	// simulate "\n"; 2012-12-08
						int stack_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr);
						if	(	stack_pos != Xop_ctx.Stack_not_found	// don't pop <tr> if none found; PAGE:en.w:Turks_in_Denmark DATE:2014-03-02
							&&	!tbl_is_xml								// cur is "|-", not <tr>; PAGE:en.w:Aargau; DATE:2016-08-14
							) {
							ctx.Stack_pop_til(root, src, stack_pos, true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_tr:			// fix;  <tr><tr>           -> <tr>							
						if (prv_tkn.Tblw_subs_len() == 0) {	// NOTE: set prv_row to ignore, but do not pop; see Tr_dupe_xnde and [[Jupiter]]; only invoke if same type; EX: <tr><tr> but not |-<tr>; DATE:2013-12-09
							Xop_tkn_itm prv_row = ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr), false, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
							prv_row.Ignore_y_();
						}
						else
							ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);						
						break;
				}					
				Xop_tblw_tr_tkn tr_tkn = tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, false);
				new_tkn = tr_tkn;
				break;
			case Tblw_type_td:								// <td>
			case Tblw_type_td2:
				boolean create_th = false;
				switch (prv_tid) {
					case Xop_tkn_itm_.Tid_tblw_tr:
						if (wlxr_type == Tblw_type_td2) {	// ignore sequences like "\n|- ||"; PAGE: nl.w:Tabel_van_Belgische_gemeenten; DATE:2015-12-03
							ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, cur_pos, Xop_ignore_tkn.Ignore_tid_double_pipe));
							return cur_pos;
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_td:			// fix;  <td><td>           -> <td></td><td>
						if (	prv_tkn.Tblw_xml()			// prv is <td>
							&&	!tbl_is_xml					// cur is "\n|"
							) {								// insert <tr>; EX: "<tr><td>\n|" -> "<tr><td><tr><td>" PAGE:fi.w:Salibandyn_maailmanmestaruuskilpailut_2012 DATE:2015-09-07
							int prv_tr_tkn_idx = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr);
                                if (prv_tr_tkn_idx != Xop_ctx.Stack_not_found) { 	// <tr> exists
								int prv_tb_tkn_idx = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tb);
								if (prv_tb_tkn_idx < prv_tr_tkn_idx) 			// don't close <tr> above current tbl
									ctx.Stack_pop_til(root, src, prv_tr_tkn_idx, true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);	// close <tr>
							}
							new_tkn = tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, true);	// make a new <tr>
							new_tkn.Atrs_rng_set(bgn_pos, bgn_pos);
							ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
						}
						else {
							if (!tbl_is_xml)					// only for "\n|" not <td>
								ctx.Para().Process_nl(ctx, root, src, bgn_pos, bgn_pos + 1);	// simulate "\n"; DATE:2014-02-20; ru.w:;home/wiki/Dashboard/Image_databases; DATE:2014-02-20
							ctx.Para().Process_block__bgn_y__end_n(Xop_xnde_tag_.Tag__td);		// <td>
							ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(prv_tid), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_th:			// fix;  <th><td>           -> <th></th><td>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(prv_tid), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						if (wlxr_type == Tblw_type_td2) create_th = true;	// !a||b -> <th><th>; but !a|b -> <th><td>
						break;
					case Xop_tkn_itm_.Tid_tblw_tb:			// fix;  <table><td>        -> <table><tr><td>
						if (wlxr_type == Tblw_type_td2) {	// NOTE: ignore || if preceded by {|; {|a||b\n
							prv_tkn.Atrs_rng_set(-1, -1);	// reset atrs_bgn; remainder of line will become part of tb atr
							return cur_pos;
						}
						else {
							new_tkn = tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, true);
							new_tkn.Atrs_rng_set(bgn_pos, bgn_pos);
							ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
							prv_tid = new_tkn.Tkn_tid();
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_tc:			// fix;  <caption><td>      -> <caption></caption><tr><td>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tc), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						new_tkn = tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, true);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
						prv_tid = new_tkn.Tkn_tid();
						break;
				}
//					if (prv_tid == Xop_tkn_itm_.Tid_xnde)
//						ctx.Stack_auto_close(root, src, prv_tkn, prv_tkn.Src_bgn(), prv_tkn.Src_end());
				if (create_th)	new_tkn = tkn_mkr.Tblw_th(bgn_pos, cur_pos, tbl_is_xml);
				else			new_tkn = tkn_mkr.Tblw_td(bgn_pos, cur_pos, tbl_is_xml);
				cell_pipe_seen = false;
				break;
			case Tblw_type_th:								// <th>
			case Tblw_type_th2:
				switch (prv_tid) {
					case Xop_tkn_itm_.Tid_tblw_tr: break;	// noop; <tr><th>
					case Xop_tkn_itm_.Tid_tblw_th:			// fix;  <th><th>           -> <th></th><th>
						if (tbl_is_xml														// tbl_is_xml always closes previous token
							|| (wlxr_type == Tblw_type_th2 || wlxr_type == Tblw_type_th))	// ! always closes; EX: "! !!"; "!! !!"; REMOVE: 2012-05-07; had (&& !ws_enabled) but caused "\n !" to fail; guard is no longer necessary since tblw_ws changed...
							ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(prv_tid), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						else {
							ctx.Subs_add(root, tkn_mkr.Txt(bgn_pos, cur_pos));
							return cur_pos;
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_td:			// fix;  <td><th>           -> <td></td><th> NOTE: common use of using <th> after <td> for formatting
						if (tbl_is_xml													// tbl_is_xml always closes previous token
							|| (wlxr_type == Tblw_type_th))								// "| !" closes; "| !!" does not;
							ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(prv_tid), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						else {
							ctx.Subs_add(root, tkn_mkr.Txt(bgn_pos, cur_pos));
							return cur_pos;
						}
						break;
					case Xop_tkn_itm_.Tid_tblw_tb:			// fix;  <table><th>        -> <table><tr><th>
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, true));
						break;
					case Xop_tkn_itm_.Tid_tblw_tc:			// fix;  <caption><th>      -> <caption></caption><tr><th>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tc), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, tkn_mkr.Tblw_tr(bgn_pos, cur_pos, tbl_is_xml, true));
						break;
				}
				new_tkn = tkn_mkr.Tblw_th(bgn_pos, cur_pos, tbl_is_xml);
				cell_pipe_seen = false;
				break;					
			case Tblw_type_tc:								// <caption>
				switch (prv_tid) {
					case Xop_tkn_itm_.Tid_tblw_tb: break;	// noop; <table><caption>
					case Xop_tkn_itm_.Tid_tblw_tr:			// fix;  <tr><caption>      -> <tr></tr><caption>  TODO_OLD: caption should be ignored and placed in quarantine
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						break;
					case Xop_tkn_itm_.Tid_tblw_td:			// fix;  <td><caption>      -> <td></td><caption>
					case Xop_tkn_itm_.Tid_tblw_th:			// fix;  <th><caption>		-> <th></th><caption>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);	// NOTE: closing <tr> in order to close <td>/<th>
						ctx.Msg_log().Add_itm_none(Xop_tblw_log.Caption_after_td, src, prv_tkn.Src_bgn(), bgn_pos);
						break;
					case Xop_tkn_itm_.Tid_tblw_tc:			// fix;  <caption><caption> -> <caption></caption><caption>
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tc), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						ctx.Msg_log().Add_itm_none(Xop_tblw_log.Caption_after_tc, src, prv_tkn.Src_bgn(), bgn_pos);
						break;
				}
				new_tkn = tkn_mkr.Tblw_tc(bgn_pos, cur_pos, tbl_is_xml);
				Xop_tblw_tb_tkn tblw_tb_tkn = (Xop_tblw_tb_tkn)ctx.Stack_get_typ(Xop_tkn_itm_.Tid_tblw_tb);
				tblw_tb_tkn.Caption_count_add_1();	// NOTE: null check is not necessary (impossible to have a caption without a tblw); DATE:2013-12-20
				cell_pipe_seen = false; // NOTE: always mark !seen; see Atrs_tc()
				break;
		}
		ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
		if (atrs_bgn > Xop_tblw_wkr.Atrs_ignore_check) {
			new_tkn.Atrs_rng_set(atrs_bgn, atrs_end);
			if (ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt) {
				Mwh_atr_itm[] atrs = ctx.App().Parser_mgr().Xnde__parse_atrs_for_tblw(src, atrs_bgn, atrs_end);
				new_tkn.Atrs_ary_as_tblw_(atrs);
			}
		}
		switch (wlxr_type) {
			case Tblw_type_tb:
			case Tblw_type_tr:
				ctx.Para().Process_block__bgn_y__end_n(Xop_xnde_tag_.Tag__tr);
				break;
			case Tblw_type_td:
			case Tblw_type_th:
				ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__td);
				break;
		}
		return cur_pos;
	}
	public int Make_tkn_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int typeId, byte wlxr_type, Xop_tblw_tkn prv_tkn, int prv_tid, boolean tbl_is_xml) {
		if (!tbl_is_xml)													// only for "\n|}" not </table>
			ctx.Para().Process_nl(ctx, root, src, bgn_pos, bgn_pos + 1);	// simulate "\n"; process para (which will create paras for cells) 2012-12-08
		if (tbl_is_xml && typeId == Xop_tkn_itm_.Tid_tblw_tb	// tblx: </table>
			&& prv_tkn != null && !prv_tkn.Tblw_xml()) {		// tblw is prv_tkn
			++tblw_te_ignore_count;								// suppress subsequent occurrences of "|}"; EX:ru.q:Авель; DATE:2014-02-22
		}
		Ignore_ws(ctx, root);
		if (wlxr_type == Tblw_type_te) {
			switch (prv_tid) {
				case Xop_tkn_itm_.Tid_tblw_td:			// fix;  <td></table>       -> <td></td></tr></table>
				case Xop_tkn_itm_.Tid_tblw_th:			// fix;  <th></table>       -> <th></th></tr></table>
					ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tr), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
					break;
				case Xop_tkn_itm_.Tid_tblw_tc:			// fix;  <caption></table>  -> <caption></caption></table>
					ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tc), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
					break;
				case Xop_tkn_itm_.Tid_tblw_tr:			// fix;  <tr></table>       -> </table>  : tr but no tds; remove tr
					boolean blank = true;
					for (int j = prv_tkn.Tkn_sub_idx() + 1; j < root.Subs_len(); j++) {
						Xop_tkn_itm t = root.Subs_get(j);
						switch (t.Tkn_tid()) {
							case Xop_tkn_itm_.Tid_newLine:
							case Xop_tkn_itm_.Tid_para:
								break;
							default:
								blank = false;
								j = root.Subs_len();
								break;
						}
					}
					if (blank)
						root.Subs_del_after(prv_tkn.Tkn_sub_idx());
					break;
				case Xop_tkn_itm_.Tid_tblw_tb:			// fix;  <table></table>    -> <table><tr><td></td></tr></table>
					boolean has_subs = false;
					for (int i = prv_tkn.Tkn_sub_idx() + 1; i < root.Subs_len(); i++) {
						int cur_id = root.Subs_get(i).Tkn_tid();
						switch (cur_id) {
							case Xop_tkn_itm_.Tid_tblw_tc:
							case Xop_tkn_itm_.Tid_tblw_td:
							case Xop_tkn_itm_.Tid_tblw_th:
							case Xop_tkn_itm_.Tid_tblw_tr:
								has_subs = true;
								i = root.Subs_len();
								break;
						}
					}
					if (!has_subs) {
						Xop_tkn_itm new_tkn = tkn_mkr.Tblw_tr(bgn_pos, bgn_pos, tbl_is_xml, true);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
						new_tkn = tkn_mkr.Tblw_td(bgn_pos, bgn_pos, tbl_is_xml);
						ctx.Subs_add_and_stack_tblw(root, prv_tkn, new_tkn);
						ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tb), true, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);
						return cur_pos;
					}
					break;
			}
			int tb_idx = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tblw_tb); 
			if (tb_idx == -1) return cur_pos;	// NOTE: tb_idx can be -1 when called from Pipe in Tmpl mode
			Xop_tblw_tb_tkn tb = (Xop_tblw_tb_tkn)ctx.Stack_pop_til(root, src, tb_idx, false, bgn_pos, bgn_pos, Xop_tkn_itm_.Tid_tblw_td);	// NOTE: need to pop manually in order to set all intermediate node ends to bgn_pos, but tb ent to cur_pos; EX: for stack of "tb,tr,td" tr and td get End_() of bgn_pos but tb gets End_() of cur_pos
			tb.Subs_move(root);
			tb.Src_end_(cur_pos);
			ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__table);	// NOTE: must clear block state that was started by <tr>; code implicitly relies on td clearing block state, but no td was created
			return cur_pos;
		}
		int acs_typeId = typeId;
		if (prv_tid != typeId	// NOTE: special logic to handle auto-close of <td></th> or <th></td>
			&& (	(prv_tid == Xop_tkn_itm_.Tid_tblw_td && typeId == Xop_tkn_itm_.Tid_tblw_th)
				||	(prv_tid == Xop_tkn_itm_.Tid_tblw_th && typeId == Xop_tkn_itm_.Tid_tblw_td)
				)
			)
			acs_typeId = prv_tid;

		int acs_pos = -1, acs_len = ctx.Stack_len();
		for (int i = acs_len - 1; i > -1; i--) {						// find auto-close pos
			byte cur_acs_tid = ctx.Stack_get(i).Tkn_tid();
			switch (acs_typeId) {
				case Xop_tkn_itm_.Tid_tblw_tb:							// if </table>, match <table> only; note that it needs to be handled separately b/c of tb logic below
					if (acs_typeId == cur_acs_tid) {
						acs_pos = i;
						i = -1;	// force break;
					}
					break;
				default:												// if </t*>, match <t*> but stop at <table>; do not allow </t*> to close <t*> outside <table>
					if		(cur_acs_tid == Xop_tkn_itm_.Tid_tblw_tb)	// <table>; do not allow </t*> to close any <t*>'s above <table>; EX:w:Enthalpy_of_fusion; {{States of matter}}
						i = -1;											// this will skip acs_pos != -1 below and discard token
					else if (cur_acs_tid == acs_typeId) {				// </t*> matches <t*>
						acs_pos = i;
						i = -1; // force break
					}
					break;
			}
		}
		if (acs_pos != -1) {
			Xop_tblw_tkn bgn_tkn = (Xop_tblw_tkn)ctx.Stack_pop_til(root, src, acs_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_tblw_td);
			switch (wlxr_type) {
				case Tblw_type_tb:
					ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__table);
					break;
				case Tblw_type_td:
				case Tblw_type_th:
					ctx.Para().Process_block__bgn_y__end_n(Xop_xnde_tag_.Tag__td);
					break;
			}
			bgn_tkn.Subs_move(root);
			bgn_tkn.Src_end_(cur_pos);
		}
		return cur_pos;
	}
	public static void Atrs_close(Xop_ctx ctx, byte[] src, Xop_root_tkn root, boolean called_from_xnde) {
		Xop_tblw_tkn prv_tkn = ctx.Stack_get_tbl();
		if (prv_tkn == null || prv_tkn.Tblw_xml()) return;			// no tblw or tblw_xnde (which does not have tblw atrs)
		switch (prv_tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_tblw_tb: case Xop_tkn_itm_.Tid_tblw_tr:	// only tb and tr have tblw atrs (EX: "{|id=1\n"); td/th use pipes for atrs (EX: "|id=1|a"); tc has no atrs; te is never on stack
				Xop_tblw_wkr.Atrs_make(ctx, src, root, ctx.Tblw(), prv_tkn, called_from_xnde);
				break;
		}
	}
	public static boolean Atrs_make(Xop_ctx ctx, byte[] src, Xop_root_tkn root, Xop_tblw_wkr wkr, Xop_tblw_tkn prv_tblw, boolean called_from_xnde) {
		if (prv_tblw.Atrs_bgn() != Xop_tblw_wkr.Atrs_null) {	// atr_bgn/end is empty or already has explicit value; ignore;
			if (prv_tblw.Atrs_bgn() == Atrs_invalid_by_xnde) {	// atr range marked invalid; ignore all tkns between prv_tblw and end of root; EX:"|-id=1<br/>"; PAGE:en.w:A DATE:2014-07-16
				for (int j = root.Subs_len() - 1; j > -1; --j) {
					Xop_tkn_itm sub = root.Subs_get(j);
					if (sub == prv_tblw)
						return false;
					else
						sub.Ignore_y_();
				}
				ctx.App().Usr_dlg().Warn_many("", "", "xnde.invalided attributes could not find previous tkn; page=~{0}", ctx.Page_url_str());	// should never happen; DATE:2014-07-16
			}
			return false;	
		}
		int subs_bgn = prv_tblw.Tkn_sub_idx() + 1, subs_end = root.Subs_len() - 1;
		int subs_pos = subs_bgn;
		Xop_tkn_itm last_atr_tkn = null;
		boolean loop = true;
		while (loop) {													// loop over tkns after prv_tkn to find last_atr_tkn
			if (subs_pos > subs_end) break;
			Xop_tkn_itm tmp_tkn = root.Subs_get(subs_pos);
			switch (tmp_tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_newLine:							// nl stops; EX: "{| a b c \nd"; bgn at {| and pick up " a b c " as atrs
				case Xop_tkn_itm_.Tid_hdr: case Xop_tkn_itm_.Tid_hr:	// hdr/hr incorporate nl into tkn so include these as well; EX: "{|a\n==b==" becomes tblw,txt,hdr (note that \n is part of hdr
				case Xop_tkn_itm_.Tid_list:								// list stops; EX: "{| a b c\n* d"; "*d" ends atrs; EX: ru.d: DATE:2014-02-22
					loop = false;
					break;
				default:
					++subs_pos;
					last_atr_tkn = tmp_tkn;
					break;
			}
		}
		if (last_atr_tkn == null) {										// no atrs found; mark tblw_tkn as Atrs_empty
			int atr_rng_tid
				=	called_from_xnde
				&&	!prv_tblw.Tblw_xml()
				&&	prv_tblw.Tkn_tid() == Xop_tkn_itm_.Tid_tblw_tr		// called from xnde && current tid is Tblw_tr; EX:"|- <br/>" PAGE:en.w:A DATE:2014-07-16
				? Atrs_invalid_by_xnde									// invalidate everything
				: Atrs_empty
				;
			prv_tblw.Atrs_rng_set(atr_rng_tid, atr_rng_tid);
			return false;
		}
		root.Subs_del_between(ctx, subs_bgn, subs_pos);
		int atrs_bgn = prv_tblw.Src_end(), atrs_end = last_atr_tkn.Src_end();
		if (prv_tblw.Tkn_tid() == Xop_tkn_itm_.Tid_tblw_tr)	// NOTE: if "|-" gobble all trailing dashes; REF: Parser.php!doTableStuff; $line = preg_replace( '#^\|-+#', '', $line ); DATE:2013-06-21
			atrs_bgn = Bry_find_.Find_fwd_while(src, atrs_bgn, src.length, Byte_ascii.Dash);
		prv_tblw.Atrs_rng_set(atrs_bgn, atrs_end);
		if (ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt && atrs_bgn != -1) {
			Mwh_atr_itm[] atrs = ctx.App().Parser_mgr().Xnde__parse_atrs_for_tblw(src, atrs_bgn, atrs_end);
			prv_tblw.Atrs_ary_as_tblw_(atrs);
		}
		wkr.Cell_pipe_seen_(true);
		return true;
	}
	private void Ignore_ws(Xop_ctx ctx, Xop_root_tkn root) {
		int end = root.Subs_len() - 1;
		// get last tr, tc, tb; cannot use ctx.Stack_get_tblw b/c this gets last open tblw, and we want last tblw; EX: "<table><tr></tr>";  Stack_get_tblw gets <table> want </tr>
		boolean found = false;
		Xop_tkn_itm prv_tkn = null;
		for (int i = end; i > -1; i--) {
			prv_tkn = root.Subs_get(i);
			switch (prv_tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_tblw_tr:
				case Xop_tkn_itm_.Tid_tblw_tc:
				case Xop_tkn_itm_.Tid_tblw_tb:
					found = true;
					i = -1;
					break;
				case Xop_tkn_itm_.Tid_tblw_td:	// exclude td
				case Xop_tkn_itm_.Tid_tblw_th:	// exclude th
					i = -1;
					break;
			}
		}
		if (!found) return;
		int bgn = prv_tkn.Tkn_sub_idx() + 1;
		int rv = Ignore_ws_rng(ctx, root, bgn, end, true);
		if (rv == -1) return; // entire range is ws; don't bother trimming end 
		Ignore_ws_rng(ctx, root, end, bgn, false);
	}
	private int Ignore_ws_rng(Xop_ctx ctx, Xop_root_tkn root, int bgn, int end, boolean fwd) {
		int cur = bgn, adj = fwd ? 1 : -1;
		while (true) {			
			if (fwd) {
				if (cur > end) return -1;
			}
			else {
				if (cur < end) return -1;			
			}			
			Xop_tkn_itm ws_tkn = root.Subs_get(cur);
			switch (ws_tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab: case Xop_tkn_itm_.Tid_newLine:
				case Xop_tkn_itm_.Tid_para:
					ws_tkn.Ignore_y_grp_(ctx, root, cur);
					break;
				case Xop_tkn_itm_.Tid_xnde:
					if (ws_tkn.Src_bgn() == ws_tkn.Src_end()							// NOTE: para_wkr inserts <br/>. these should be disabled in Ignore_ws_rng; they are identified as having bgn == end; normal <br/>s will have bgn < end
						&& ((Xop_xnde_tkn)ws_tkn).Tag().Id() == Xop_xnde_tag_.Tid__br)
						ws_tkn.Ignore_y_grp_(ctx, root, cur);
					break;
				default:
					return cur;
			}
			cur += adj;
		}
	}
	public static int Handle_false_tblw_match(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int bgn_pos, int cur_pos, Xop_tkn_itm tkn, boolean add_nl) {
		if (add_nl)
			ctx.Para().Process_nl(ctx, root, src, bgn_pos, cur_pos);
		ctx.Subs_add(root, tkn);
		return cur_pos;
	}
	public static final int Atrs_null = -1, Atrs_empty = -2, Atrs_invalid_by_xnde = -3, Atrs_ignore_check = -1;
	public static final byte Tblw_type_tb = 0, Tblw_type_te = 1, Tblw_type_tr = 2, Tblw_type_td = 3, Tblw_type_th = 4, Tblw_type_tc = 5, Tblw_type_td2 = 6, Tblw_type_th2 = 7;
}
/*
NOTE_1:
Code tries to emulate HTML tidy behavior. Specifically:
- ignore <table> when directly under <table>
- if tblw, scan to end of line to ignore attributes
- ignore any closing tblws
EX:
{|id=1
{|id=2		<- ignore id=2
|}
|}
*/