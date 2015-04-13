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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.xmls.*; import gplx.xowa.wikis.data.tbls.*;
class Xob_export_wtr {
	public void Write(Xowd_page_itm page) {

	}
	public void Write_search() {
		// Xoh_html_bldr bldr = new Xoh_html_bldr();
		// bldr.Write_lnki(null, null, 0, 
	}
}
class Xoh_html_bldr {
	private final Gfo_xml_wtr html_wtr = new Gfo_xml_wtr();
	public void Write_lnki(Xow_ns_mgr ns_mgr, byte[] wiki, int id, byte[] page, int ns_id, byte[] text) {	// EX: <a href="/wiki/Page" id="xowa_lnki_1" title="Page">Page</a>
		Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		if (ns == null) {/*use main_ns; log*/}
		byte[] page_ttl_db = null;	// TODO: url_encode
		byte[] title = null;
		html_wtr.Nde_lhs_bgn("a");
		html_wtr.Atr_bgn("href"	).Atr_val_str_a7("/site/").Atr_val_bry(wiki).Atr_val_bry(page_ttl_db).Atr_end();
		html_wtr.Atr_bgn("id"	).Atr_val_str_a7("xowa_lnki_").Atr_val_int(id).Atr_end();
		html_wtr.Atr_kv_bry("title", title);
		html_wtr.Nde_lhs_end();
		html_wtr.Txt_bry(text);
		html_wtr.Nde_rhs();
	}
//		private void Write_plain(Bry_bfr bfr, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xoa_ttl lnki_ttl, Xop_lnki_caption_wtr caption_wkr) {
//			byte[] ttl_bry = lnki.Ttl_ary();
//			if (Bry_.Len_eq_0(ttl_bry)) ttl_bry = lnki_ttl.Full_txt_raw();		// NOTE: handles ttls like [[fr:]] and [[:fr;]] which have an empty Page_txt, but a valued Full_txt_raw
//			if (Bry_.Eq(lnki_ttl.Full_txt(), page.Ttl().Full_txt())) {			// lnki is same as pagename; bold; SEE: Month widget on day pages will bold current day; PAGE:en.w:January 1
//				if (lnki_ttl.Anch_bgn() == -1 && Bry_.Eq(lnki_ttl.Wik_txt(), page.Ttl().Wik_txt())) {		// only bold if lnki is not pointing to anchor on same page; PAGE:en.w:Comet; [[Comet#Physical characteristics|ion tail]]
//					bfr.Add(Html_tag_.B_lhs);
//					Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
//					bfr.Add(Html_tag_.B_rhs);
//					return;
//				}
//			}
//			if (lnki.Xtn_sites_link()) return;									// lnki marked for relatedSites; don't write to page
//			if (hctx.Mode_is_alt())
//				Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
//			else {
//				if (hctx.Mode_is_hdump())
//					wiki.Html_mgr().Hzip_mgr().Itm__anchor().Html_plain(bfr, lnki);
//				else
//					bfr.Add(Xoh_consts.A_bgn);							// '<a href="'
//				app.Href_parser().Build_to_bfr(bfr, wiki, lnki_ttl, hctx.Mode_is_popup());	// '/wiki/A'
//				if (cfg.Lnki_id()) {
//					int lnki_html_id = lnki.Html_id();
//					if (lnki_html_id > Lnki_id_ignore)					// html_id=0 for skipped lnkis; EX:anchors and interwiki
//						bfr	.Add(Xoh_consts.A_mid_id)					// '" id=\"xowa_lnki_'
//							.Add_int_variable(lnki_html_id);			// '1234'
//				}
//				if (cfg.Lnki_title()) {
//					bfr	.Add(Xoh_consts.A_bgn_lnki_0);					// '" title=\"'
//					byte[] lnki_title_bry = lnki_ttl.Page_txt();		// 'Abcd'		NOTE: use Page_txt to (a) replace underscores with spaces; (b) get title casing; EX:[[roman_empire]] -> Roman empire
//					Html_utl.Escape_html_to_bfr(bfr, lnki_title_bry, 0, lnki_title_bry.length, Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.N);	// escape title; DATE:2014-10-27
//				}
//				if (hctx.Mode_is_hdump()) {
//					bfr.Add(gplx.xowa.html.hdumps.abrvs.Xohd_abrv_.Html_redlink_bgn);
//					bfr.Add_int_variable(lnki.Html_id());
//					bfr.Add(gplx.xowa.html.hdumps.abrvs.Xohd_abrv_.Html_redlink_end);
//				}
//				else {
//					if (cfg.Lnki_visited()
//						&& history_mgr.Has(wiki.Domain_bry(), ttl_bry))
//						bfr.Add(Bry_xowa_visited);						// '" class="xowa-visited'
//					bfr.Add(Xoh_consts.__end_quote);					// '">'	
//				}
//				if (lnki_ttl.Anch_bgn() != -1 && !lnki_ttl.Ns().Id_main()) {	// anchor exists and not main_ns; anchor must be manually added b/c Xoa_ttl does not handle # for non main-ns
//					byte[] anch_txt = lnki_ttl.Anch_txt();
//					byte anch_spr 
//					= (anch_txt.length > 0 && anch_txt[0] == Byte_ascii.Hash)	// 1st char is #; occurs when page_txt has trailing space; causes 1st letter of anch_txt to start at # instead of 1st letter
//					? Byte_ascii.Space	// ASSUME: 1 space ("Help:A #b"); does not handle multiple spaces like ("Help:A   #b"); needs change to Xoa_ttl 
//					: Byte_ascii.Hash;	// Anch_txt bgns at 1st letter, so add # for caption; 
//					ttl_bry = Bry_.Add_w_dlm(anch_spr, ttl_bry, anch_txt);	// manually add anchor; else "Help:A#b" becomes "Help:A". note that lnki.Ttl_ary() uses .Full_txt (wiki + page but no anchor) to captialize 1st letter of page otherwise "Help:A#b" shows as "Help:A" (so Help:a -> Help:A); DATE:2013-06-21
//				}
//				Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
//				bfr.Add(Xoh_consts.A_end);								// </a>
//			}
//		}
}
/*
<mediawiki xmlns="http://www.mediawiki.org/xml/export-0.10/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xml/export-0.10.xsd" version="0.10" xml:lang="en">
  <siteinfo>
    <sitename>Wikipedia</sitename>
    <dbname>simplewiki</dbname>
    <super>http://simple.wikipedia.org/wiki/Main_Page</super>
    <generator>MediaWiki 1.25wmf20</generator>
    <case>first-letter</case>
    <namespaces>
      <namespace key="-2" case="first-letter">Media</namespace>
      <namespace key="-1" case="first-letter">Special</namespace>
      <namespace key="0" case="first-letter" />
      <namespace key="1" case="first-letter">Talk</namespace>
      <namespace key="2" case="first-letter">User</namespace>
      <namespace key="3" case="first-letter">User talk</namespace>
      <namespace key="4" case="first-letter">Wikipedia</namespace>
      <namespace key="5" case="first-letter">Wikipedia talk</namespace>
      <namespace key="6" case="first-letter">File</namespace>
      <namespace key="7" case="first-letter">File talk</namespace>
      <namespace key="8" case="first-letter">MediaWiki</namespace>
      <namespace key="9" case="first-letter">MediaWiki talk</namespace>
      <namespace key="10" case="first-letter">Template</namespace>
      <namespace key="11" case="first-letter">Template talk</namespace>
      <namespace key="12" case="first-letter">Help</namespace>
      <namespace key="13" case="first-letter">Help talk</namespace>
      <namespace key="14" case="first-letter">Category</namespace>
      <namespace key="15" case="first-letter">Category talk</namespace>
      <namespace key="828" case="first-letter">Module</namespace>
      <namespace key="829" case="first-letter">Module talk</namespace>
    </namespaces>
  </siteinfo>
  <page>
    <title>April</title>
    <ns>0</ns>
    <id>1</id>
    <revision>
      <id>4926273</id>
      <parentid>4784983</parentid>
      <timestamp>2014-10-30T22:07:53Z</timestamp>
      <contributor>
        <username>AJona1992</username>
        <id>104337</id>
      </contributor>
      <comment>Events in April +Selena Day in Texas</comment>
      <model>wikitext</model>
      <format>text/x-wiki</format>
      <text xml:space="preserve">{{monththisyear|4}}
{{Months}}</text>
      <sha1>5dbo5ljegrwg7jdt0z5myizoeryhwft</sha1>
    </revision>
  </page>
*/
