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
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.pfuncs.ttls.*; import gplx.xowa.xtns.relatedSites.*;
import gplx.xowa.parsers.tmpls.*; import gplx.xowa.wikis.pages.lnkis.*;
public class Xop_lnki_wkr_ {
	public static final int Invalidate_lnki_len = 128;
	public static int Invalidate_lnki(Xop_ctx ctx, byte[] src, Xop_root_tkn root, Xop_lnki_tkn lnki, int cur_pos) {
		lnki.Tkn_tid_to_txt();						// convert initial "[[" to text; note that this lnki has no pipes as pipe_lxr does similar check; EX: [[<invalid>]]; DATE:2014-03-26
		root.Subs_del_after(lnki.Tkn_sub_idx() + 1);// remove all tkns after [[ from root
		int reparse_bgn = lnki.Src_end();			// NOTE: reparse all text from "[["; needed to handle [[|<i>a</i>]] where "<i>a</i>" cannot be returned as text; DATE:2014-03-04
		ctx.App().Msg_log().Add_itm_none(Xop_lnki_log.Invalid_ttl, src, reparse_bgn, reparse_bgn + 128);
		// int reparse_len = cur_pos - reparse_bgn;
		// if (reparse_len > 512) ctx.App().Usr_dlg().Warn_many("", "", "lnki.reparsing large block; page=~{0} len=~{1} src=~{2}", Xop_ctx_.Page_as_str(ctx), reparse_len, Xop_ctx_.Src_limit_and_escape_nl(src, reparse_bgn, Invalidate_lnki_len));
		return reparse_bgn;
	}
	public static boolean Parse_ttl(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, int pipe_pos) {
		int ttl_bgn = lnki.Src_bgn() + Xop_tkn_.Lnki_bgn_len;
		ttl_bgn = Bry_find_.Find_fwd_while(src, ttl_bgn, pipe_pos, Byte_ascii.Space);		// remove \s from bgn
		int ttl_end = Bry_find_.Find_bwd_while(src, pipe_pos, ttl_bgn, Byte_ascii.Space);	// remove \s from end
		++ttl_end; // +1 to place after non-ws; EX: [[ a ]]; ttl_end should go from 3 -> 4
		return Parse_ttl(ctx, src, lnki, ttl_bgn, ttl_end);
	}
	public static boolean Parse_ttl(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, int ttl_bgn, int ttl_end) {
		byte[] ttl_bry = Bry_.Mid(src, ttl_bgn, ttl_end);
		ttl_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url_ttl.Decode(ttl_bry);
		int ttl_bry_len = ttl_bry.length;
		Xoa_ttl page_ttl = ctx.Page().Ttl();
		Xowe_wiki wiki = ctx.Wiki();
		if (page_ttl.Ns().Subpages_enabled()
			&& Pfunc_rel2abs.Rel2abs_ttl(ttl_bry, 0, ttl_bry_len)) { // Linker.php|normalizeSubpageLink
			Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
			Int_obj_ref rel2abs_tid = ctx.Tmp_mgr().Pfunc_rel2abs().Val_zero_();
			byte[] new_bry = Pfunc_rel2abs.Rel2abs(tmp_bfr, wiki.Parser_mgr().Rel2abs_ary(), ttl_bry, page_ttl.Raw(), rel2abs_tid);
			lnki.Subpage_tid_(rel2abs_tid.Val());
			lnki.Subpage_slash_at_end_(Bry_.Get_at_end(ttl_bry) == Byte_ascii.Slash);
			ttl_bry = new_bry;
			tmp_bfr.Mkr_rls();
		}
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);		
		if (ttl == null) return false;
		if	(	wiki.Cfg_parser_lnki_xwiki_repos_enabled()			// wiki has lnki.xwiki_repos
			&&	ttl.Wik_bgn() != Null_wik_bgn						// xwiki available; EX: [[en:]]
			)	
			ttl = Adj_ttl_for_file(wiki, ctx, ttl, ttl_bry);
		lnki.Ttl_(ttl);
		lnki.Ns_id_(ttl.Ns().Id());
		return true;
	}
	private static Xoa_ttl Adj_ttl_for_file(Xowe_wiki wiki, Xop_ctx ctx, Xoa_ttl ttl, byte[] ttl_bry) {	// NOTE: remove the xwiki part; EX: [[en:File:A.png]] -> [[File:A.png]]
		byte[] xwiki_bry = ttl.Wik_txt(); if (xwiki_bry == null) return ttl; // should not happen, but just in case
		int xwiki_bry_len = xwiki_bry.length;
		int ttl_bry_len = ttl_bry.length;
		if (xwiki_bry_len + 1 >= ttl_bry_len) return ttl;	// invalid ttl; EX: [[en:]]
		byte[] ttl_in_xwiki_bry = Bry_.Mid(ttl_bry, xwiki_bry_len + 1, ttl_bry_len); // +1 to position after xwiki :; EX: [[en:File:A.png]]; +1 to put after ":" at "F"
		if (!wiki.Cfg_parser().Lnki_cfg().Xwiki_repo_mgr().Has(xwiki_bry)) return ttl;	// alias not in xwikis; EX: [[en_bad:File:A.png]]
		Xoa_ttl ttl_in_xwiki = Xoa_ttl.Parse(wiki, ttl_in_xwiki_bry);
		if (ttl_in_xwiki == null) return ttl; // occurs if ttl is bad in xwiki; EX: [[en:<bad>]]
		return ttl_in_xwiki.Ns().Id_is_file() ? ttl_in_xwiki : ttl;
	}
	public static int Chk_for_tail(Xol_lang_itm lang, byte[] src, int cur_pos, int src_len, Xop_lnki_tkn lnki) {
		int bgn_pos = cur_pos;
		Btrie_slim_mgr lnki_trail = lang.Lnki_trail_mgr().Trie();
		while (true) {	// loop b/c there can be multiple consecutive lnki_trail_chars; EX: [[A]]bcde
			if (cur_pos == src_len) break;
			byte[] lnki_trail_bry = (byte[])lnki_trail.Match_bgn_w_byte(src[cur_pos], src, cur_pos, src_len);
			if (lnki_trail_bry == null) break;	// no longer a lnki_trail char; stop
			cur_pos += lnki_trail_bry.length;	// lnki_trail char; add
		}
		if (bgn_pos != cur_pos && lnki.Ns_id() == Xow_ns_.Tid__main) {	// only mark trail if Main ns (skip trail for Image)
			lnki.Tail_bgn_(bgn_pos).Tail_end_(cur_pos);
			return cur_pos;
		}
		else
			return bgn_pos;
	}
	public static void Page_parse(Xop_ctx ctx, byte[] src, Gfo_number_parser number_parser, Xop_lnki_tkn lnki, Arg_nde_tkn arg) {
		int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
		byte[] val_bry = Bry_.Trim(src, val_tkn_bgn, val_tkn_end);	// some tkns have trailing space; EX.WWI: [[File:Bombers of WW1.ogg|thumb |thumbtime=3]]
		number_parser.Parse(val_bry);
		if (number_parser.Has_err())
			ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, val_tkn_bgn, val_tkn_end);
		else
			lnki.Page_(number_parser.Rv_as_int());
	}
	public static byte[] Val_extract(byte[] src, Arg_nde_tkn arg) {
		int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
		return Bry_.Trim(src, val_tkn_bgn, val_tkn_end);	// trim trailing space
	}
	public static void Thumbtime_parse(Xop_ctx ctx, byte[] src, Gfo_number_parser number_parser, Xop_lnki_tkn lnki, Arg_nde_tkn arg) {
		int val_tkn_bgn = arg.Val_tkn().Src_bgn(), val_tkn_end = arg.Val_tkn().Src_end();
		long fracs = Time_span_.parse_to_fracs(src, val_tkn_bgn, val_tkn_end, false);
		if (fracs == Time_span_.parse_null) {
			ctx.Msg_log().Add_itm_none(Xop_lnki_log.Upright_val_is_invalid, src, val_tkn_bgn, val_tkn_end);
		}
		else
			lnki.Time_(fracs / Time_span_.Ratio_f_to_s);
	}
	public static boolean Adjust_for_brack_end_len_of_3(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int cur_pos, Xop_lnki_tkn lnki) {
		if (	cur_pos < src_len										// bounds check
			&&	src[cur_pos] == Byte_ascii.Brack_end					// is next char after "]]", "]"; i.e.: "]]]"; PAGE:en.w:Aubervilliers DATE:2014-06-25
			) {
			int nxt_pos = cur_pos + 1;
			if (	nxt_pos == src_len									// allow "]]]EOS"
				||	(	nxt_pos < src_len								// bounds check
					&&	src[nxt_pos] != Byte_ascii.Brack_end			// is next char after "]]]", "]"; i.e.: not "]]]]"; PAGE:ru.w:Меркатале_ин_Валь_ди_Песа; DATE:2014-02-04
					)
			) {
				if (	lnki.Caption_exists()							// does a caption exist?
					&&	lnki.Caption_tkn().Src_end() + 2 == cur_pos		// is "]]]" at end of caption?; 2="]]".Len; handle [http://a.org [[File:A.png|123px]]] PAGE:ar.w:محمد; DATE:2014-08-20
					&&	lnki.Ttl() != null								// only change "]]]" to "]" + "]]" if lnki is not title; otherwise [[A]]] -> "A]" which will be invalid; PAGE:en.w:Tall_poppy_syndrome DATE:2014-07-23
					) {
					Xop_tkn_itm caption_val_tkn = lnki.Caption_val_tkn();
					caption_val_tkn.Subs_add(tkn_mkr.Bry_raw(cur_pos, cur_pos + 1, Byte_ascii.Brack_end_bry));	// add "]" as bry
					caption_val_tkn.Src_end_(caption_val_tkn.Src_end() + 1);
					return true;
				}
			}
		}
		return false;
	}
	public static void Write_lnki(Bry_bfr bfr, Xoa_ttl ttl, boolean literal) {
		bfr.Add(Xop_tkn_.Lnki_bgn);
		if (literal) bfr.Add_byte(Byte_ascii.Colon);
		bfr.Add(ttl.Full_db());
		bfr.Add(Xop_tkn_.Lnki_end);
	}
	private static final int Null_wik_bgn = -1;
}
