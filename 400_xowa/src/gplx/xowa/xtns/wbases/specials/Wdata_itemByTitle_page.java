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
package gplx.xowa.xtns.wbases.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.specials.*;
import gplx.xowa.apps.urls.*;
public class Wdata_itemByTitle_page implements Xow_special_page {
	private Gfo_qarg_mgr_old arg_hash = new Gfo_qarg_mgr_old();
	private static final    byte[] Arg_site = Bry_.new_a7("site"), Arg_page = Bry_.new_a7("page");
	public Bry_fmtr Html_fmtr() {return html_fmtr;}
	private Wdata_itemByTitle_cfg cfg;
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__item_by_title;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		if (cfg == null) cfg = (Wdata_itemByTitle_cfg)wiki.Appe().Special_mgr().Get_or_null(Wdata_itemByTitle_cfg.Key);
		// Special:ItemByTitle/enwiki/Earth -> www.wikidata.org/wiki/Q2
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg();
		byte[] site_bry = cfg.Site_default();
		byte[] page_bry = Bry_.Empty;
		byte[] raw_bry = ttl.Full_txt_wo_qarg(); 					// EX: enwiki/Earth
		int args_len = url.Qargs_ary().length;
		if (args_len > 0) {
			arg_hash.Load(url.Qargs_ary());
			site_bry = arg_hash.Get_val_bry_or(Arg_site, Bry_.Empty);
			page_bry = arg_hash.Get_val_bry_or(Arg_page, Bry_.Empty);
		}
		int site_bgn = Bry_find_.Find_fwd(raw_bry, Xoa_ttl.Subpage_spr);
		if (site_bgn != Bry_find_.Not_found) {						// leaf arg is available
			int page_bgn = Bry_find_.Find_fwd(raw_bry, Xoa_ttl.Subpage_spr, site_bgn + 1);			
			int raw_bry_len = raw_bry.length;
			if (page_bgn != Bry_find_.Not_found && page_bgn < raw_bry_len) {	// pipe is found and not last char (EX: "enwiki/" is invalid
				site_bry = Bry_.Mid(raw_bry, site_bgn + 1, page_bgn);
				page_bry = Bry_.Mid(raw_bry, page_bgn + 1, raw_bry_len);
			}
		}
		Xoae_app app = wiki.Appe();
		if (Bry_.Len_gt_0(site_bry) && Bry_.Len_gt_0(page_bry))
			if (Navigate(usr_dlg, app, app.Wiki_mgr().Wdata_mgr(), page, site_bry, page_bry)) return;
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		html_fmtr.Bld_bfr_many(tmp_bfr, "Search for items by site and title", "Site", site_bry, "Page", page_bry, "Search");
		page.Db().Text().Text_bry_(tmp_bfr.To_bry_and_rls());
		page.Html_data().Html_restricted_n_();		// [[Special:]] pages allow all HTML
	}
	private static boolean Navigate(Gfo_usr_dlg usr_dlg, Xoae_app app, Wdata_wiki_mgr wdata_mgr, Xoae_page page, byte[] site_bry, byte[] page_bry) {
		page_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(page_bry);		// NOTE: space is converted to + on postback to url; decode
		byte[] wiki_domain = Xow_abrv_wm_.Parse_to_domain_bry(site_bry); 		if (wiki_domain == null) {usr_dlg.Warn_many("", "", "site_bry parse failed; site_bry:~{0}", String_.new_u8(site_bry)); return false;}
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(wiki_domain);			if (wiki == null) {usr_dlg.Warn_many("", "", "wiki_domain does not exist; wiki_domain:~{0}", String_.new_u8(wiki_domain)); return false;}
		Xoa_ttl wdata_ttl = Xoa_ttl.Parse(wiki, page_bry);						if (wdata_ttl == null) {usr_dlg.Warn_many("", "", "ttl is invalid; ttl:~{0}", String_.new_u8(page_bry)); return false;}
		Wdata_doc doc = wdata_mgr.Doc_mgr.Get_by_ttl_or_null(wiki, wdata_ttl); 	if (doc == null) {usr_dlg.Warn_many("", "", "ttl cannot be found in wikidata; ttl:~{0}", String_.new_u8(wdata_ttl.Raw())); return false;}		
		byte[] qid_bry = doc.Qid();
		wdata_mgr.Wdata_wiki().Data_mgr().Redirect(page, qid_bry); 	if (page.Db().Page().Exists_n()) {usr_dlg.Warn_many("", "", "qid cannot be found in wikidata; qid:~{0}", String_.new_u8(qid_bry)); return false;}
		return true;
	}
	private static Bry_fmtr html_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
	(	"<div id=\"mw-content-text\">"
	,	"<form method=\"get\" action=\"//www.wikidata.org/wiki/Special:ItemByTitle\" name=\"itembytitle\" id=\"wb-itembytitle-form1\">"
	,	"<fieldset>"
	,	"<legend>~{legend}</legend>"
	,	"<label for=\"wb-itembytitle-sitename\">~{site_lbl}:</label>"
	,	"<input id=\"wb-itembytitle-sitename\" size=\"12\" name=\"site\" value=\"~{site_val}\" accesskey=\"s\" />"
	,	""
	,	"<label for=\"pagename\">~{page_lbl}:</label>"
	,	"<input id=\"pagename\" size=\"36\" class=\"wb-input-text\" name=\"page\" value=\"~{page_val}\" accesskey=\"p\" />"
	,	""
	,	"<input id=\"wb-itembytitle-submit\" class=\"wb-input-button\" type=\"submit\" value=\"~{search_lbl}\" name=\"submit\" />"
	,	"</fieldset>"
	,	"</form>"
	,	"</div>"
	,	"<br>To change the default site, see <a href='/site/home/wiki/Options/Wikibase'>Options/Wikibase</a>"	// HOME
	)
	, 	"legend", "site_lbl", "site_val", "page_lbl", "page_val", "search_lbl");

	public Xow_special_page Special__clone() {return this;}
}
