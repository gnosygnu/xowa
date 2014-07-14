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
package gplx.xowa; import gplx.*;
import gplx.core.btries.*;
import gplx.xowa.wikis.*; import gplx.xowa.parsers.lnkis.redlinks.*;
public class Xop_lnki_wkr implements Xop_ctx_wkr, Xop_arg_wkr {
	private Arg_bldr arg_bldr = Arg_bldr._;
	private NumberParser number_parser = new NumberParser();
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public Xop_lnki_logger File_wkr() {return file_wkr;} public Xop_lnki_wkr File_wkr_(Xop_lnki_logger v) {file_wkr = v; return this;} private Xop_lnki_logger file_wkr;
	public void Auto_close(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)tkn;
		lnki.Tkn_tid_to_txt();
		ctx.Msg_log().Add_itm_none(Xop_misc_log.Eos, src, lnki.Src_bgn(), lnki.Src_end());			
	}
	private static final byte[] Brack_end_bry = Bry_.new_ascii_("]");
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (ctx.Cur_tkn_tid() == Xop_tkn_itm_.Tid_lnke) {		// if lnke then take 1st ] in "]]" and use it close lnke
			int lnke_end_pos = bgn_pos + 1;
			ctx.Lnke().MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, lnke_end_pos);
			return lnke_end_pos;
		}
		if (	cur_pos < src_len								// bounds check
			&&	src[cur_pos] == Byte_ascii.Brack_end			// is next char after "]]", "]"; i.e.: "]]]"; PAGE:en.w:Aubervilliers DATE:2014-06-25
			) {
			int nxt_pos = cur_pos + 1;
			if (	nxt_pos == src_len							// allow "]]]EOS"
				||	(	nxt_pos < src_len						// bounds check
					&&	src[nxt_pos] != Byte_ascii.Brack_end	// is next char after "]]]", "]"; i.e.: not "]]]]"; PAGE:ru.w:Меркатале_ин_Валь_ди_Песа; DATE:2014-02-04
					)

			) {
				root.Subs_add(tkn_mkr.Bry(bgn_pos, bgn_pos + 1, Brack_end_bry));	// add "]" as bry
				++bgn_pos; ++cur_pos;							// position "]]" at end of "]]]"
			}
		}
		int lvl_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_lnki);
		if (lvl_pos == Xop_ctx.Stack_not_found) return ctx.Lxr_make_txt_(cur_pos);	// "]]" found but no "[[" in stack; interpet "]]" literally
		Xop_lnki_tkn lnki = (Xop_lnki_tkn)ctx.Stack_pop_til(root, src, lvl_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_lnki_end);
		if (!arg_bldr.Bld(ctx, tkn_mkr, this, Xop_arg_wkr_.Typ_lnki, root, lnki, bgn_pos, cur_pos, lnki.Tkn_sub_idx() + 1, root.Subs_len(), src))
			return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
		cur_pos = Xop_lnki_wkr_.Chk_for_tail(ctx.Lang(), src, cur_pos, src_len, lnki);
		lnki.Src_end_(cur_pos);	// NOTE: must happen after Chk_for_tail; redundant with above, but above needed b/c of returns
		root.Subs_del_after(lnki.Tkn_sub_idx() + 1);	// all tkns should now be converted to args in owner; delete everything in root
		boolean lnki_is_file = false;
		switch (lnki.Ns_id()) {
			case Xow_ns_.Id_file:
				if (	Xop_lnki_type.Id_is_thumbable(lnki.Lnki_type())		// thumbs produce <div> cancels pre
					||	lnki.Align_h() != Xop_lnki_align_h.Null				// halign (left, right, none) also produces <div>; DATE:2014-02-17
					)
					ctx.Para().Process_block_lnki_div();
				lnki_is_file = true;
				break;
			case Xow_ns_.Id_media:
				lnki_is_file = true;
				break;
			case Xow_ns_.Id_category:
				if (!lnki.Ttl().ForceLiteralLink())					// NOTE: do not remove ws if literal; EX:[[Category:A]]\n[[Category:B]] should stay the same; DATE:2013-07-10
					ctx.Para().Process_lnki_category(ctx, root, src,cur_pos, src_len);	// removes excessive ws between categories; EX: [[Category:A]]\n\s[[Category:B]] -> [[Category:A]][[Category:B]] (note that both categories will not be rendered directly in html, but go to the bottom of the page)
				break;
		}
		if (lnki_is_file) {
			ctx.Cur_page().Lnki_list().Add(lnki);
			if (file_wkr != null) file_wkr.Wkr_exec(ctx, src, lnki, gplx.xowa.bldrs.files.Xob_lnki_src_tid.Tid_file);
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
				switch (arg_tid) {
					case Xop_lnki_arg_parser.Tid_none:			lnki.Align_h_(Xop_lnki_type.Id_none); break;
					case Xop_lnki_arg_parser.Tid_border:		lnki.Border_(Bool_.Y_byte); break;
					case Xop_lnki_arg_parser.Tid_thumb:			lnki.Lnki_type_(Xop_lnki_type.Id_thumb); break;
					case Xop_lnki_arg_parser.Tid_frame:			lnki.Lnki_type_(Xop_lnki_type.Id_frame); break;
					case Xop_lnki_arg_parser.Tid_frameless:		lnki.Lnki_type_(Xop_lnki_type.Id_frameless); break;
					case Xop_lnki_arg_parser.Tid_left:			lnki.Align_h_(Xop_lnki_align_h.Left); break;
					case Xop_lnki_arg_parser.Tid_center:		lnki.Align_h_(Xop_lnki_align_h.Center); break;
					case Xop_lnki_arg_parser.Tid_right:			lnki.Align_h_(Xop_lnki_align_h.Right); break;
					case Xop_lnki_arg_parser.Tid_top:			lnki.Align_v_(Xop_lnki_align_v.Top); break;
					case Xop_lnki_arg_parser.Tid_middle:		lnki.Align_v_(Xop_lnki_align_v.Middle); break;
					case Xop_lnki_arg_parser.Tid_bottom:		lnki.Align_v_(Xop_lnki_align_v.Bottom); break;
					case Xop_lnki_arg_parser.Tid_super:			lnki.Align_v_(Xop_lnki_align_v.Super); break;
					case Xop_lnki_arg_parser.Tid_sub:			lnki.Align_v_(Xop_lnki_align_v.Sub); break;
					case Xop_lnki_arg_parser.Tid_text_top:		lnki.Align_v_(Xop_lnki_align_v.TextTop); break;
					case Xop_lnki_arg_parser.Tid_text_bottom:	lnki.Align_v_(Xop_lnki_align_v.TextBottom); break;
					case Xop_lnki_arg_parser.Tid_baseline:		lnki.Align_v_(Xop_lnki_align_v.Baseline); break;
					case Xop_lnki_arg_parser.Tid_alt:			lnki.Alt_tkn_(arg); 
						lnki.Alt_tkn().Tkn_ini_pos(false, arg.Src_bgn(), arg.Src_end());
						break;
					case Xop_lnki_arg_parser.Tid_caption:
						Xop_tkn_itm cur_caption_tkn = lnki.Caption_tkn();
						if (	cur_caption_tkn == Xop_tkn_null.Null_tkn	// lnki doesn't have caption; add arg as caption
							||	lnki.Ttl().Ns().Id_file_or_media()) {		// or lnki is File; always take last
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
							caption_val_tkn.Subs_add(ctx.Tkn_mkr().Bry(pipe_bgn, pipe_bgn + 1, Const_pipe));	// NOTE: add pipe once for entire caption tkn; used to add for every val tkn; DATE:2014-06-08
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
							int valTknBgn = arg.Val_tkn().Src_bgn(), valTknEnd = arg.Val_tkn().Src_end();
							number_parser.Parse(src, valTknBgn, valTknEnd);
							if (number_parser.HasErr())
								ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, valTknBgn, valTknEnd);
							else
								lnki.Upright_(number_parser.AsDec().XtoDouble());
						}
						else	// no =; EX: [[Image:a|upright]]
							lnki.Upright_(1);
						break;
					case Xop_lnki_arg_parser.Tid_noicon:		lnki.Media_icon_n_();  break;
					case Xop_lnki_arg_parser.Tid_page:			Xop_lnki_wkr_.Page_parse(ctx, src, number_parser, lnki, arg); break;
					case Xop_lnki_arg_parser.Tid_thumbtime:		Xop_lnki_wkr_.Thumbtime_parse(ctx, src, number_parser, lnki, arg); break;
				}
			}
			return true;
		} catch (Exception e) {
			ctx.App().Usr_dlg().Warn_many("", "", "fatal error in lnki: page=~{0} src=~{1} err=~{2}", String_.new_utf8_(ctx.Cur_page().Ttl().Full_db()), String_.new_utf8_(src, lnki.Src_bgn(), lnki.Src_end()), Err_.Message_gplx(e));
			return false;
		}
	}	private static final byte[] Const_pipe = Bry_.new_ascii_("|");
}
class Xop_lnki_wkr_ {
	private static final Int_obj_ref rel2abs_tid = Int_obj_ref.zero_();
	public static final int Invalidate_lnki_len = 128;
	public static int Invalidate_lnki(Xop_ctx ctx, byte[] src, Xop_root_tkn root, Xop_lnki_tkn lnki, int cur_pos) {
		lnki.Tkn_tid_to_txt();						// convert initial "[[" to text; note that this lnki has no pipes as pipe_lxr does similar check; EX: [[<invalid>]]; DATE:2014-03-26
		root.Subs_del_after(lnki.Tkn_sub_idx() + 1);// remove all tkns after [[ from root
		int reparse_bgn = lnki.Src_end();			// NOTE: reparse all text from "[["; needed to handle [[|<i>a</i>]] where "<i>a</i>" cannot be returned as text; DATE:2014-03-04
		int reparse_len = cur_pos - reparse_bgn;
		ctx.App().Msg_log().Add_itm_none(Xop_lnki_log.Invalid_ttl, src, reparse_bgn, reparse_bgn + 128);
		if (reparse_len > 512)
			ctx.App().Usr_dlg().Warn_many("", "", "lnki.reparsing large block; page=~{0} len=~{1} src=~{2}", Xop_ctx_.Page_as_str(ctx), reparse_len, Xop_ctx_.Src_limit_and_escape_nl(src, reparse_bgn, Invalidate_lnki_len));
		return reparse_bgn;
	}
	public static boolean Parse_ttl(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, int pipe_pos) {
		int ttl_bgn = lnki.Src_bgn() + Xop_tkn_.Lnki_bgn_len;
		ttl_bgn = Bry_finder.Find_fwd_while(src, ttl_bgn, pipe_pos, Byte_ascii.Space);		// remove \s from bgn
		int ttl_end = Bry_finder.Find_bwd_while(src, pipe_pos, ttl_bgn, Byte_ascii.Space);	// remove \s from end
		++ttl_end; // +1 to place after non-ws; EX: [[ a ]]; ttl_end should go from 3 -> 4
		return Parse_ttl(ctx, src, lnki, ttl_bgn, ttl_end);
	}
	public static boolean Parse_ttl(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, int ttl_bgn, int ttl_end) {
		Xoa_app app = ctx.App();
		byte[] ttl_bry = Bry_.Mid(src, ttl_bgn, ttl_end);
		ttl_bry = app.Url_converter_url_ttl().Decode(ttl_bry);
		int ttl_bry_len = ttl_bry.length;
		Xoa_ttl page_ttl = ctx.Cur_page().Ttl();
		if (page_ttl.Ns().Subpages_enabled()
			&& Pf_xtn_rel2abs.Rel2abs_ttl(ttl_bry, 0, ttl_bry_len)) { // Linker.php|normalizeSubpageLink
			Bry_bfr tmp_bfr = app.Utl_bry_bfr_mkr().Get_b512();
			byte[] new_bry = Pf_xtn_rel2abs.Rel2abs(tmp_bfr, ttl_bry, page_ttl.Raw(), rel2abs_tid.Val_zero_());
			lnki.Subpage_tid_(rel2abs_tid.Val());
			lnki.Subpage_slash_at_end_(Bry_.Get_at_end(ttl_bry) == Byte_ascii.Slash);
			ttl_bry = new_bry;
			tmp_bfr.Mkr_rls();
		}
		Xow_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry);		
		if (ttl == null) return false;
		if	(	wiki.Cfg_parser_lnki_xwiki_repos_enabled()			// wiki has lnki.xwiki_repos
			&&	ttl.Wik_bgn() != Xoa_ttl.Null_wik_bgn				// xwiki available; EX: [[en:]]
			)	
			ttl = Adj_ttl_for_file(wiki, ctx, ttl, ttl_bry);
		lnki.Ttl_(ttl);
		lnki.Ns_id_(ttl.Ns().Id());
		return true;
	}
	private static Xoa_ttl Adj_ttl_for_file(Xow_wiki wiki, Xop_ctx ctx, Xoa_ttl ttl, byte[] ttl_bry) {	// NOTE: remove the xwiki part; EX: [[en:File:A.png]] -> [[File:A.png]]
		byte[] xwiki_bry = ttl.Wik_txt(); if (xwiki_bry == null) return ttl; // should not happen, but just in case
		int xwiki_bry_len = xwiki_bry.length;
		int ttl_bry_len = ttl_bry.length;
		if (xwiki_bry_len + 1 >= ttl_bry_len) return ttl;	// invalid ttl; EX: [[en:]]
		byte[] ttl_in_xwiki_bry = Bry_.Mid(ttl_bry, xwiki_bry_len + 1, ttl_bry_len); // +1 to position after xwiki :; EX: [[en:File:A.png]]; +1 to put after ":" at "F"
		if (!wiki.Cfg_parser().Lnki_cfg().Xwiki_repo_mgr().Has(xwiki_bry)) return ttl;	// alias not in xwikis; EX: [[en_bad:File:A.png]]
		Xoa_ttl ttl_in_xwiki = Xoa_ttl.parse_(wiki, ttl_in_xwiki_bry);
		if (ttl_in_xwiki == null) return ttl; // occurs if ttl is bad in xwiki; EX: [[en:<bad>]]
		return ttl_in_xwiki.Ns().Id_file() ? ttl_in_xwiki : ttl;
	}
	public static int Chk_for_tail(Xol_lang lang, byte[] src, int cur_pos, int src_len, Xop_lnki_tkn lnki) {
		int bgn_pos = cur_pos;
		Btrie_slim_mgr lnki_trail = lang.Lnki_trail_mgr().Trie();
		while (true) {	// loop b/c there can be multiple consecutive lnki_trail_chars; EX: [[A]]bcde
			if (cur_pos == src_len) break;
			byte[] lnki_trail_bry = (byte[])lnki_trail.Match_bgn_w_byte(src[cur_pos], src, cur_pos, src_len);
			if (lnki_trail_bry == null) break;	// no longer a lnki_trail char; stop
			cur_pos += lnki_trail_bry.length;	// lnki_trail char; add
		}
		if (bgn_pos != cur_pos && lnki.Ns_id() == Xow_ns_.Id_main) {	// only mark trail if Main ns (skip trail for Image)
			lnki.Tail_bgn_(bgn_pos).Tail_end_(cur_pos);
			return cur_pos;
		}
		else
			return bgn_pos;
	}
	public static void Page_parse(Xop_ctx ctx, byte[] src, NumberParser number_parser, Xop_lnki_tkn lnki, Arg_nde_tkn arg) {
		int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
		byte[] val_bry = Bry_.Trim(src, val_tkn_bgn, val_tkn_end);	// some tkns have trailing space; EX.WWI: [[File:Bombers of WW1.ogg|thumb |thumbtime=3]]
		number_parser.Parse(val_bry);
		if (number_parser.HasErr())
			ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, val_tkn_bgn, val_tkn_end);
		else
			lnki.Page_(number_parser.AsInt());
	}
	public static void Thumbtime_parse(Xop_ctx ctx, byte[] src, NumberParser number_parser, Xop_lnki_tkn lnki, Arg_nde_tkn arg) {
		int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
		long fracs = TimeSpanAdp_.parse_to_fracs(src, val_tkn_bgn, val_tkn_end, false);
		if (fracs == TimeSpanAdp_.parse_null) {
			ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, val_tkn_bgn, val_tkn_end);
		}
		else
			lnki.Thumbtime_(fracs / TimeSpanAdp_.Ratio_f_to_s);
	}
}
