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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.apis.xowa.html.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.apis.xowa.xtns.*;
public class Wdata_hwtr_mgr {
	private final Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
	@gplx.Internal protected Wdata_fmtr__toc_div	Fmtr_toc() {return fmtr_toc;} private final Wdata_fmtr__toc_div fmtr_toc = new Wdata_fmtr__toc_div();
	@gplx.Internal protected Wdata_fmtr__json		Fmtr_json() {return fmtr_json;} private final Wdata_fmtr__json fmtr_json = new Wdata_fmtr__json();
	@gplx.Internal protected Wdata_fmtr__claim_grp	Fmtr_claim() {return fmtr_claim;} private final Wdata_fmtr__claim_grp fmtr_claim = new Wdata_fmtr__claim_grp();
	private final Wdata_fmtr__langtext_tbl fmtr_label = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__langtext_tbl fmtr_descr = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__langtext_tbl fmtr_alias = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__slink_grp fmtr_slink = new Wdata_fmtr__slink_grp();
	private final Wdata_fmtr__oview_tbl fmtr_oview = new Wdata_fmtr__oview_tbl();
	private Wdata_lang_sorter lang_sorter = new Wdata_lang_sorter();		
	public Bry_fmtr Fmtr_main() {return fmtr_main;} private final Bry_fmtr fmtr_main = Bry_fmtr.new_("~{oview}~{toc}~{claims}~{links}~{labels}~{descriptions}~{aliases}~{json}", "oview", "toc", "claims", "links", "labels", "descriptions", "aliases", "json");
	public Wdata_hwtr_msgs Msgs() {return msgs;} private Wdata_hwtr_msgs msgs;
	@gplx.Internal protected Wdata_lbl_mgr Lbl_mgr() {return lbl_mgr;} private final Wdata_lbl_mgr lbl_mgr = new Wdata_lbl_mgr();
	public void Init_by_ctor(Xoapi_wikibase wikibase_api, Wdata_lbl_wkr lbl_wkr, Url_encoder href_encoder, Xoapi_toggle_mgr toggle_mgr, Xow_xwiki_mgr xwiki_mgr) {
		lbl_mgr.Wkr_(lbl_wkr);
		fmtr_oview.Init_by_ctor(wikibase_api, href_encoder);
		fmtr_claim.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr, lbl_mgr);
		fmtr_slink.Init_by_ctor(lang_sorter, toggle_mgr, lbl_mgr, href_encoder, fmtr_toc, xwiki_mgr);
		fmtr_label.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-label", new Wdata_fmtr__langtext_row_base());
		fmtr_descr.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-descr", new Wdata_fmtr__langtext_row_base());
		fmtr_alias.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-alias", new Wdata_fmtr__alias_row());
		fmtr_json .Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr);
		lang_sorter.Langs_(wikibase_api.Sort_langs());
		GfoEvMgr_.SubSame_many(wikibase_api, lang_sorter, Xoapi_wikibase.Evt_sort_langs_changed);
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		this.msgs = msgs;
		fmtr_toc.Init_by_lang(msgs);
		fmtr_oview.Init_by_lang(lang_sorter.Langs()[0], msgs);
		fmtr_claim.Init_by_lang(msgs);
		fmtr_slink.Init_by_lang(msgs);
		fmtr_label.Init_by_lang(msgs, msgs.Label_tbl_hdr(), msgs.Label_col_hdr());
		fmtr_descr.Init_by_lang(msgs, msgs.Descr_tbl_hdr(), msgs.Descr_col_hdr());
		fmtr_alias.Init_by_lang(msgs, msgs.Alias_tbl_hdr(), msgs.Alias_col_hdr());
		fmtr_json.Init_by_lang(msgs);
	}
	public void Init_by_wdoc(Wdata_doc wdoc) {
		lang_sorter.Init_by_wdoc(wdoc);
		fmtr_toc  .Init_by_wdoc(wdoc);
		fmtr_oview.Init_by_wdoc(wdoc);
		fmtr_claim.Init_by_wdoc(wdoc.Qid(), wdoc.Claim_list());
		fmtr_slink.Init_by_wdoc(wdoc.Slink_list());
		fmtr_label.Init_by_wdoc(wdoc.Label_list());
		fmtr_descr.Init_by_wdoc(wdoc.Descr_list());
		fmtr_alias.Init_by_wdoc(wdoc.Alias_list());
		fmtr_json.Init_by_wdoc (wdoc.Jdoc());
		lbl_mgr.Gather_labels(wdoc, lang_sorter);
	}
	public byte[] Popup(Wdata_doc wdoc) {
		fmtr_oview  .Init_by_wdoc(wdoc);
		fmtr_label.Init_by_wdoc(wdoc.Label_list());
		fmtr_descr.Init_by_wdoc(wdoc.Descr_list());
		fmtr_alias.Init_by_wdoc(wdoc.Alias_list());
		bfr.Add_str("<div id='wb-item' class='wikibase-entityview wb-item' lang='en' dir='ltr'>");
		fmtr_main.Bld_bfr_many(bfr, fmtr_oview, "", "", "", "", "", "", "");
		bfr.Add_str("</div>");
		return bfr.XtoAryAndClear();
	}
	public byte[] Write(Wdata_doc wdoc) {
		bfr.Add_str("<div id='wb-item' class='wikibase-entityview wb-item' lang='en' dir='ltr'>");
		fmtr_main.Bld_bfr_many(bfr, fmtr_oview, fmtr_toc, fmtr_claim, fmtr_slink, fmtr_label, fmtr_descr, fmtr_alias, fmtr_json);
		bfr.Add_str("</div>");
		return bfr.XtoAryAndClear();
	}
	public static void Write_link_wikidata(Bry_bfr bfr, byte[] href, byte[] text) {
		text = gplx.html.Html_utl.Escape_html_as_bry(text);
		fmtr_link_wikidata.Bld_bfr_many(bfr, href, text);
	}	private static final Bry_fmtr fmtr_link_wikidata = Bry_fmtr.new_("<a href='/wiki/~{href}'>~{text}</a>", "href", "text");
}
