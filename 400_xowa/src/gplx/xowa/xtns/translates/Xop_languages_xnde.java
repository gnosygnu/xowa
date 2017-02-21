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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.langs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public class Xop_languages_xnde implements Xox_xnde {
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		this.xnde = xnde;
		langs = Find_lang_pages(ctx, wiki);
	}
	public List_adp Langs() {return langs;} private List_adp langs;
	public Xoa_ttl Root_ttl() {return root_ttl;} private Xoa_ttl root_ttl;
	private Xoa_ttl Root_ttl_of(Xowe_wiki wiki, Xoa_ttl ttl) {
		byte[] page_bry = ttl.Page_db();
		int slash_pos = Bry_find_.Find_bwd(page_bry, Xoa_ttl.Subpage_spr);
		if (slash_pos == Bry_find_.Not_found) return ttl;
		byte[] root_bry = Bry_.Mid(page_bry, 0, slash_pos);
		return Xoa_ttl.Parse(wiki, ttl.Ns().Id(), root_bry);
	}
	private List_adp Find_lang_pages(Xop_ctx ctx, Xowe_wiki wiki) {
		this.root_ttl = Root_ttl_of(wiki, ctx.Page().Ttl());
		List_adp rslts = List_adp_.New(); 
		Int_obj_ref rslt_count = Int_obj_ref.New(0);
		Xow_ns page_ns = root_ttl.Ns();
		wiki.Db_mgr().Load_mgr().Load_ttls_for_all_pages(Cancelable_.Never, rslts, null, null, rslt_count, page_ns, root_ttl.Page_db(), Int_.Max_value, 0, Int_.Max_value, true, false);
		int len = rslt_count.Val();
		if (len == 0) return List_adp_.Noop;				// no lang pages; return;
		List_adp rv = List_adp_.New();
		byte[] root_ttl_bry = root_ttl.Page_db();		// get root_ttl_bry; do not use ns
		int lang_bgn = root_ttl_bry.length + 1;			// lang starts after /; EX: "Page" will have subpage of "Page/fr" and lang_bgn of 5
		boolean english_needed = true;
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)rslts.Get_at(i);
			byte[] page_ttl_bry = page.Ttl_page_db();
			int page_ttl_bry_len = page_ttl_bry.length;
			if 		(Bry_.Eq(root_ttl_bry, page_ttl_bry)) continue; 	// ignore self; EX: "page"
			if 		(lang_bgn < page_ttl_bry_len 							// guard against out of bounds
				&& 	page_ttl_bry[lang_bgn - 1] == Xoa_ttl.Subpage_spr		// prv char must be /; EX: "Page/fr"
				) {
				byte[] lang_key = Bry_.Mid(page_ttl_bry, lang_bgn, page_ttl_bry_len);
				if (Bry_.Eq(lang_key, Xol_lang_itm_.Key_en))			// lang is english; mark english found;
					english_needed = false;
				Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_null(lang_key);
				if (lang_itm == null) continue; // not a known lang
				rv.Add(lang_itm);
			}
		}
		if (rv.Count() == 0) return List_adp_.Noop;	// no lang items; handles situations where just "Page" is returned
		if (english_needed)	// english not found; always add; handles situations wherein Page/fr and Page/de added, but not Page/en
			rv.Add(Xol_lang_stub_.Get_by_key_or_en(Xol_lang_itm_.Key_en));
		rv.Sort_by(Xol_lang_stub_.Comparer_key);
		return rv;
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (langs.Count() == 0) return; // no langs; don't write anything;
		fmtr_mgr_itms.Init(langs, ctx.Wiki(), root_ttl, ctx.Page().Lang().Key_bry());
		fmtr_all.Bld_bfr_many(bfr, "Other languages", fmtr_mgr_itms);
	}
	private static final    Xop_languages_fmtr fmtr_mgr_itms = new Xop_languages_fmtr();
	public static final    Bry_fmtr fmtr_all = Bry_fmtr.new_(String_.Concat_lines_nl
	(	"<table>"
	,	"  <tbody>"
	,	"    <tr valign=\"top\">"
	,	"		<td class=\"mw-pt-languages-label\">~{other_languages_hdr}:</td>"
	,	"       <td class=\"mw-pt-languages-list\">~{language_itms}"
	,	"       </td>"
	,	"    </tr>"
	,	"  </tbody>"
	,	"</table>"
	), "other_languages_hdr", "language_itms")
	,	fmtr_itm_basic = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(	""
	,	"         <a href=\"~{anchor_href}\" title=\"~{anchor_title}\">~{anchor_text}</a>&#160;•"		
	), "anchor_href", "anchor_title", "anchor_text")
	,	fmtr_itm_english = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(	""
	,	"         <a href=\"~{anchor_href}\" title=\"~{anchor_title}\"><span class=\"mw-pt-languages-ui\">~{anchor_text}</span></a>&#160;•"
	), "anchor_href", "anchor_title", "anchor_text")
	,	fmtr_itm_selected = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(	""
	,	"         <span class=\"mw-pt-languages-selected\">~{anchor_text}</span>&#160;•"
	), "anchor_href", "anchor_title", "anchor_text")
	;
	// "<img src=\"//bits.wikimedia.org/static-1.22wmf9/extensions/Translate/res/images/prog-1.png\" alt=\"~{img_alt}\" title=\"~{img_title}\" width=\"9\" height=\"9\" />&#160;•&#160;‎"
}
class Xop_languages_fmtr implements gplx.core.brys.Bfr_arg {
	public void Init(List_adp langs, Xowe_wiki wiki, Xoa_ttl root_ttl, byte[] cur_lang) {
		this.langs = langs;
		this.wiki = wiki;
		this.root_ttl = root_ttl;
		this.cur_lang = cur_lang;
	}	private List_adp langs; private Xowe_wiki wiki; private Xoa_ttl root_ttl; private byte[] cur_lang;
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = langs.Count();
		Xoh_href_wtr href_wtr = wiki.Html__href_wtr();
		int ns_id = root_ttl.Ns().Id();
		byte[] root_ttl_bry = root_ttl.Page_db();	// NOTE: do not use .Full(); ns will be added in Xoa_ttl.Parse below
		for (int i = 0; i < len; i++) {
			Xol_lang_stub lang = (Xol_lang_stub)langs.Get_at(i);
			byte[] lang_key = lang.Key();
			boolean lang_is_en = Bry_.Eq(lang_key, Xol_lang_itm_.Key_en);
			byte[] lang_ttl_bry = lang_is_en ? root_ttl_bry : Bry_.Add_w_dlm(Xoa_ttl.Subpage_spr, root_ttl_bry, lang_key);
			Xoa_ttl lang_ttl = Xoa_ttl.Parse(wiki, ns_id, lang_ttl_bry);
			byte[] lang_href = href_wtr.Build_to_bry(wiki, lang_ttl);
			byte[] lang_title = lang_ttl.Full_txt_w_ttl_case();
			Bry_fmtr fmtr = null;
			if		(Bry_.Eq(lang_key, Xol_lang_itm_.Key_en)) 	fmtr = Xop_languages_xnde.fmtr_itm_english;
			else if	(Bry_.Eq(lang_key, cur_lang))			fmtr = Xop_languages_xnde.fmtr_itm_selected;
			else 												fmtr = Xop_languages_xnde.fmtr_itm_basic;
			fmtr.Bld_bfr_many(bfr, lang_href, lang_title, lang.Canonical_name());
		}
	}
}
