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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.parsers.lnkis.files.*; import gplx.xowa.xtns.pfuncs.ttls.*; import gplx.xowa.xtns.relatedSites.*;
import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.miscs.*;
public class Xop_lnki_wkr implements Xop_ctx_wkr, Xop_arg_wkr {
	private Arg_bldr arg_bldr = Arg_bldr.Instance;
	private Gfo_number_parser number_parser = new Gfo_number_parser();
	private Sites_regy_mgr sites_regy_mgr;
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {
		sites_regy_mgr = ctx.Wiki().Xtn_mgr().Xtn_sites().Regy_mgr(); if (!sites_regy_mgr.Xtn_mgr().Enabled()) sites_regy_mgr = null;	// sets sites_xtn_mgr status for page; see below
	}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public Xop_file_logger File_logger() {return lnki_logger;} public Xop_lnki_wkr File_logger_(Xop_file_logger v) {lnki_logger = v; return this;} private Xop_file_logger lnki_logger = Xop_file_logger_.Noop;
	public void Auto_close(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)tkn;
		lnki.Tkn_tid_to_txt();
		ctx.Msg_log().Add_itm_none(Xop_misc_log.Eos, src, lnki.Src_bgn(), lnki.Src_end());			
	}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (ctx.Cur_tkn_tid() == Xop_tkn_itm_.Tid_lnke) {		// if lnke then take 1st ] in "]]" and use it close lnke
			int lnke_end_pos = bgn_pos + 1;
			ctx.Lnke().MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, lnke_end_pos);
			return lnke_end_pos;
		}
		int stack_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_lnki);
		if (stack_pos == Xop_ctx.Stack_not_found) return ctx.Lxr_make_txt_(cur_pos);	// "]]" found but no "[[" in stack; return literal "]]"
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)ctx.Stack_pop_til(root, src, stack_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_lnki_end);
		if (!arg_bldr.Bld(ctx, tkn_mkr, this, Xop_arg_wkr_.Typ_lnki, root, lnki, bgn_pos, cur_pos, lnki.Tkn_sub_idx() + 1, root.Subs_len(), src))
			return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
		if (lnki.Ns_id() != Xow_ns_.Tid__main && Bry_.Len_eq_0(lnki.Ttl().Page_txt()))	// handle anchor-only pages; EX:[[File:#A]] PAGE:en.w:Spindale,_North_Carolina; DATE:2015-12-28
			return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
		if (Xop_lnki_wkr_.Adjust_for_brack_end_len_of_3(ctx, tkn_mkr, root, src, src_len, cur_pos, lnki))	// convert "]]]" into "]" + "]]", not "]]" + "]"				
			++cur_pos;																						// position "]]" at end of "]]]"
		cur_pos = Xop_lnki_wkr_.Chk_for_tail(ctx.Lang(), src, cur_pos, src_len, lnki);
		lnki.Src_end_(cur_pos);	// NOTE: must happen after Chk_for_tail; redundant with above, but above needed b/c of returns
		root.Subs_del_after(lnki.Tkn_sub_idx() + 1);	// all tkns should now be converted to args in owner; delete everything in root
		boolean lnki_is_file = false;
		switch (lnki.Ns_id()) {
			case Xow_ns_.Tid__file:
				if (	Xop_lnki_type.Id_is_thumbable(lnki.Lnki_type())		// thumbs produce <div> cancels pre
					||	lnki.Align_h() != Xop_lnki_align_h_.Null			// halign (left, right, none) also produces <div>; DATE:2014-02-17
					)
					ctx.Para().Process_block_lnki_div();
				lnki_is_file = true;
				break;
			case Xow_ns_.Tid__media:
				lnki_is_file = true;
				break;
			case Xow_ns_.Tid__category:
				if (!lnki.Ttl().ForceLiteralLink())					// NOTE: do not remove ws if literal; EX:[[Category:A]]\n[[Category:B]] should stay the same; DATE:2013-07-10
					ctx.Para().Process_lnki_category(ctx, root, src,cur_pos, src_len);	// removes excessive ws between categories; EX: [[Category:A]]\n\s[[Category:B]] -> [[Category:A]][[Category:B]] (note that both categories will not be rendered directly in html, but go to the bottom of the page)
				break;
		}
		if (lnki_is_file) {
			ctx.Page().Lnki_list().Add(lnki);
			lnki_logger.Log_file(ctx, lnki, Xop_file_logger_.Tid__file);
		}
		Xoa_ttl lnki_ttl = lnki.Ttl();
		if (	lnki_ttl.Wik_bgn() != -1		// lnki is xwiki
			&&	sites_regy_mgr != null			// relatedSites xtn is enabled
			) {
			lnki.Xtn_sites_link_(sites_regy_mgr.Match(ctx.Page(), lnki_ttl));
		}
		return cur_pos;
	}
	public boolean Args_add(Xop_ctx ctx, byte[] src, Xop_tkn_itm tkn, Arg_nde_tkn arg, int arg_idx) {
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)tkn;
		try {
			if (arg_idx == 0) {				// 1st arg; assume trg; process ns;
				if (lnki.Ttl() == null) {	// ttl usually set by 1st pipe, but some lnkis have no pipe; EX: [[A]]
					Arg_itm_tkn ttl_tkn = arg.Val_tkn();
					if (!Xop_lnki_wkr_.Parse_ttl(ctx, src, lnki, ttl_tkn.Dat_bgn(), ttl_tkn.Dat_end()))
						return false;
				}
				lnki.Trg_tkn_(arg);
			}
			else {									// nth arg; guess arg type
				int arg_tid = -1;
				int bgn = arg.Val_tkn().Dat_bgn(), end = arg.Val_tkn().Dat_end();
				if (arg.KeyTkn_exists()) {bgn = arg.Key_tkn().Dat_bgn(); end = arg.Key_tkn().Dat_end();}
				arg_tid = ctx.Wiki().Lang().Lnki_arg_parser().Identify_tid(src, bgn, end, lnki);
				if (arg_tid == Xop_lnki_arg_parser.Tid_caption && ctx.Wiki().Domain_itm().Domain_type_id() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__other) {
					if (end > bgn && Bry_.Eq(src, bgn, end, Xop_lnki_arg_parser.Bry_target))
						arg_tid = Xop_lnki_arg_parser.Tid_target;
				}
				switch (arg_tid) {
					case Xop_lnki_arg_parser.Tid_none:			lnki.Align_h_(Xop_lnki_type.Id_none); break;
					case Xop_lnki_arg_parser.Tid_border:		lnki.Border_(Bool_.Y_byte); break;
					case Xop_lnki_arg_parser.Tid_thumb:			lnki.Lnki_type_(Xop_lnki_type.Id_thumb); break;
					case Xop_lnki_arg_parser.Tid_frame:			lnki.Lnki_type_(Xop_lnki_type.Id_frame); break;
					case Xop_lnki_arg_parser.Tid_frameless:		lnki.Lnki_type_(Xop_lnki_type.Id_frameless); break;
					case Xop_lnki_arg_parser.Tid_left:			lnki.Align_h_(Xop_lnki_align_h_.Left); break;
					case Xop_lnki_arg_parser.Tid_center:		lnki.Align_h_(Xop_lnki_align_h_.Center); break;
					case Xop_lnki_arg_parser.Tid_right:			lnki.Align_h_(Xop_lnki_align_h_.Right); break;
					case Xop_lnki_arg_parser.Tid_top:			lnki.Align_v_(Xop_lnki_align_v_.Top); break;
					case Xop_lnki_arg_parser.Tid_middle:		lnki.Align_v_(Xop_lnki_align_v_.Middle); break;
					case Xop_lnki_arg_parser.Tid_bottom:		lnki.Align_v_(Xop_lnki_align_v_.Bottom); break;
					case Xop_lnki_arg_parser.Tid_super:			lnki.Align_v_(Xop_lnki_align_v_.Super); break;
					case Xop_lnki_arg_parser.Tid_sub:			lnki.Align_v_(Xop_lnki_align_v_.Sub); break;
					case Xop_lnki_arg_parser.Tid_text_top:		lnki.Align_v_(Xop_lnki_align_v_.TextTop); break;
					case Xop_lnki_arg_parser.Tid_text_bottom:	lnki.Align_v_(Xop_lnki_align_v_.TextBottom); break;
					case Xop_lnki_arg_parser.Tid_baseline:		lnki.Align_v_(Xop_lnki_align_v_.Baseline); break;
					case Xop_lnki_arg_parser.Tid_class:			lnki.Lnki_cls_(Xop_lnki_wkr_.Val_extract(src, arg)); break;
					case Xop_lnki_arg_parser.Tid_target:		lnki.Target = Xop_lnki_wkr_.Val_extract(src, arg); break;
					case Xop_lnki_arg_parser.Tid_alt:			lnki.Alt_tkn_(arg); 
						lnki.Alt_tkn().Tkn_ini_pos(false, arg.Src_bgn(), arg.Src_end());
						break;
					case Xop_lnki_arg_parser.Tid_caption:
						Xop_tkn_itm cur_caption_tkn = lnki.Caption_tkn();
						if (	cur_caption_tkn == Xop_tkn_null.Null_tkn	// lnki doesn't have caption; add arg as caption
							||	lnki.Ttl().Ns().Id_is_file_or_media()) {	// or lnki is File; always take last
							lnki.Caption_tkn_(arg);
							if (arg.Eq_tkn() != Xop_tkn_null.Null_tkn) {	// equal tkn exists; add val tkns to key and then swap key with val
								Arg_itm_tkn key_tkn = arg.Key_tkn(), val_tkn = arg.Val_tkn();
								key_tkn.Subs_add(arg.Eq_tkn());
								for (int i = 0; i < val_tkn.Subs_len(); i++) {
									Xop_tkn_itm sub = val_tkn.Subs_get(i);
									key_tkn.Subs_add(sub);
								}
								key_tkn.Dat_end_(val_tkn.Dat_end());
								val_tkn.Subs_clear();
								arg.Key_tkn_(Arg_itm_tkn_null.Null_arg_itm);
								arg.Val_tkn_(key_tkn);
							}
							else	// no equal tkn 
								lnki.Caption_tkn_pipe_trick_(end - bgn == 0);	// NOTE: pipe_trick check must go here; checks for val_tkn.Bgn == val_tkn.End; if there is an equal token but no val, then Bgn == End which would trigger false pipe trick (EX:"[[A|B=]]")
						}
						else {	// lnki does have caption; new caption should be concatenated; EX:[[A|B|C]] -> "B|C" x> "B"; NOTE: pipe-trick and eq tkn should not matter to multiple captions; DATE:2014-05-05
							Xop_tkn_itm val_tkn = arg.Val_tkn();
							int subs_len = val_tkn.Subs_len();
							Xop_tkn_itm caption_val_tkn = ((Arg_nde_tkn)cur_caption_tkn).Val_tkn();
							int pipe_bgn = caption_val_tkn.Src_bgn(); // for bookeeping purposes, assign | pos to same pos as val_tkn; note that pos really shouldn't be used; DATE:2014-05-05
							caption_val_tkn.Subs_add(ctx.Tkn_mkr().Bry_raw(pipe_bgn, pipe_bgn + 1, Const_pipe));	// NOTE: add pipe once for entire caption tkn; used to add for every val tkn; DATE:2014-06-08
							for (int i = 0 ; i < subs_len; i++) {
								Xop_tkn_itm sub_itm = val_tkn.Subs_get(i);
								caption_val_tkn.Subs_add(sub_itm);
							}
						}
						break;
					case Xop_lnki_arg_parser.Tid_link:			lnki.Link_tkn_(arg); break;
					case Xop_lnki_arg_parser.Tid_dim:			break;// NOOP: Identify_tid does actual setting
					case Xop_lnki_arg_parser.Tid_upright:
						if (arg.KeyTkn_exists()) {
							int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
							val_tkn_bgn = Bry_find_.Find_fwd_while_space_or_tab(src, val_tkn_bgn, val_tkn_end);	// trim ws at bgn; needed for next step
							if (val_tkn_end - val_tkn_bgn > 19) val_tkn_end = val_tkn_bgn + 19;	// HACK: limit upright tkn to 19 digits; 20 or more will overflow long; WHEN: rewrite number_parser to handle doubles; PAGE:de.w:Feuerland DATE:2015-02-03
							number_parser.Parse(src, val_tkn_bgn, val_tkn_end);
							if (number_parser.Has_err())
								ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, val_tkn_bgn, val_tkn_end);
							else
								lnki.Upright_(number_parser.Rv_as_dec().To_double());
						}
						else	// no =; EX: [[Image:a|upright]]
							lnki.Upright_(gplx.xowa.files.Xof_img_size.Upright_default_marker);// NOTE: was incorrectly hardcoded as 1; DATE:2014-07-23
						break;
					case Xop_lnki_arg_parser.Tid_noicon:		lnki.Media_icon_n_();  break;
					case Xop_lnki_arg_parser.Tid_page:			Xop_lnki_wkr_.Page_parse(ctx, src, number_parser, lnki, arg); break;
					case Xop_lnki_arg_parser.Tid_thumbtime:		Xop_lnki_wkr_.Thumbtime_parse(ctx, src, number_parser, lnki, arg); break;
				}
			}
			return true;
		} catch (Exception e) {
			ctx.App().Usr_dlg().Warn_many("", "", "fatal error in lnki: page=~{0} src=~{1} err=~{2}", String_.new_u8(ctx.Page().Ttl().Full_db()), String_.new_u8(src, lnki.Src_bgn(), lnki.Src_end()), Err_.Message_gplx_full(e));
			return false;
		}
	}	private static final    byte[] Const_pipe = Bry_.new_a7("|");
}
