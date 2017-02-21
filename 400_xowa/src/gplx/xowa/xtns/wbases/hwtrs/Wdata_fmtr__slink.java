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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.encoders.*; import gplx.langs.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.xtns.wbases.core.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.apps.apis.xowa.html.*; import gplx.xowa.wikis.xwikis.*;
class Wdata_fmtr__slink_grp implements gplx.core.brys.Bfr_arg {
	private final    Wdata_fmtr__slink_tbl fmtr_tbl = new Wdata_fmtr__slink_tbl(); private boolean is_empty;
	public void Init_by_ctor(Wdata_lang_sorter lang_sorter, Xoapi_toggle_mgr toggle_mgr, Wdata_lbl_mgr lbl_regy, Gfo_url_encoder href_encoder, Wdata_fmtr__toc_div fmtr_toc, Xow_xwiki_mgr xwiki_mgr) {
		fmtr_tbl.Init_by_ctor(lang_sorter, toggle_mgr, lbl_regy, href_encoder, fmtr_toc, xwiki_mgr);
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {fmtr_tbl.Init_by_lang(msgs);}
	public void Init_by_wdoc(Ordered_hash list) {
		this.is_empty = list.Count() == 0; if (is_empty) return;
		fmtr_tbl.Init_by_wdoc(list);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (is_empty) return;
		fmtr.Bld_bfr_many(bfr, fmtr_tbl);
	}
	private final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div class='wikibase-sitelinkgrouplistview'>"
	, "    <div class='wb-listview'>~{grps}"
	, "    </div>"
	, "  </div>"
	), "grps"
	);
}
class Wdata_fmtr__slink_tbl implements gplx.core.brys.Bfr_arg {
	private final    Wdata_fmtr__slink_row fmtr_row = new Wdata_fmtr__slink_row();
	private final    Wdata_slink_grp[] grps = new Wdata_slink_grp[Wdata_slink_grp.Idx__len];
	private Wdata_lang_sorter lang_sorter; private Wdata_hwtr_msgs msgs;
	public void Init_by_ctor(Wdata_lang_sorter lang_sorter, Xoapi_toggle_mgr toggle_mgr, Wdata_lbl_mgr lbl_regy, Gfo_url_encoder href_encoder, Wdata_fmtr__toc_div fmtr_toc, Xow_xwiki_mgr xwiki_mgr) {
		this.lang_sorter = lang_sorter;
		fmtr_row.Init_by_ctor(lbl_regy, href_encoder, xwiki_mgr);
		for (int i = 0; i < Wdata_slink_grp.Idx__len; ++i) {
			byte[] wiki_name = Wdata_slink_grp.Name_by_tid(i);
			String toggle_itm_key = "wikidatawiki-slink-" + String_.new_a7(wiki_name);
			Xoapi_toggle_itm toggle_itm = toggle_mgr.Get_or_new(toggle_itm_key);
			Wdata_toc_data toc_data = new Wdata_toc_data(fmtr_toc, href_encoder);
			grps[i] = new Wdata_slink_grp(i, wiki_name, toggle_itm, toc_data);
		}
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		this.msgs = msgs;
		for (int i = 0; i < Wdata_slink_grp.Idx__len; ++i) {
			Wdata_slink_grp grp = grps[i];
			grp.Toc_data().Orig_(Wdata_slink_grp.Msg_by_tid(msgs, i));
			grp.Toggle_itm().Init_msgs(msgs.Toggle_title_y(), msgs.Toggle_title_n());
		}
	}
	public void Init_by_wdoc(Ordered_hash list) {
		Wdata_slink_grp.Sift(grps, list);
		for (int i = 0; i < Wdata_slink_grp.Idx__len; ++i) {
			Wdata_slink_grp grp = grps[i];
			int itms_count = grp.Rows().Count();
			if (itms_count == 0) continue;
			grp.Toc_data().Make(itms_count);
			grp.Rows().Sort_by(lang_sorter);
		}
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		for (int i = 0; i < Wdata_slink_grp.Idx__len; ++i) {
			Wdata_slink_grp grp = grps[i];
			if (grp.Rows().Count() == 0) continue;
			fmtr_row.Init_by_page(grp.Rows());
			Xoapi_toggle_itm toggle_itm = grp.Toggle_itm();
			fmtr.Bld_bfr_many(bfr, grp.Toc_data().Href(), grp.Toc_data().Text(), msgs.Langtext_col_lang_name(), msgs.Langtext_col_lang_code(), msgs.Slink_col_hdr_text(), toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), fmtr_row);
		}
	}
	private final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <div class='wikibase-sitelinkgroupview' data-wb-sitelinks-group='wikipedia'>"
	, "        <div class='wikibase-sitelinkgroupview-heading-container'>"
	, "          <h2 class='wb-section-heading wikibase-sitelinkgroupview-heading' dir='auto' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "        </div>"
	, "        <div class='wikibase-sitelinklistview'~{toggle_hdr}>"			
	, "          <ul class='wikibase-sitelinklistview-listview'>~{rows}"
	, "          </ul>"
	, "        </div>"
	, "      </div>"
	), "hdr_href", "hdr_text", "hdr_lang", "hdr_wiki", "hdr_page", "toggle_btn", "toggle_hdr", "rows"
	);
}
class Wdata_fmtr__slink_row implements gplx.core.brys.Bfr_arg {
	private final    Wdata_fmtr__slink_badges fmtr_badges = new Wdata_fmtr__slink_badges(); private Xow_xwiki_mgr xwiki_mgr;
	private Gfo_url_encoder href_encoder; private Ordered_hash list; 
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy, Gfo_url_encoder href_encoder, Xow_xwiki_mgr xwiki_mgr) {
		this.href_encoder = href_encoder; this.xwiki_mgr = xwiki_mgr;
		fmtr_badges.Init_by_ctor(lbl_regy);
	}
	public void Init_by_page(Ordered_hash list) {this.list = list;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.Get_at(i);
			Xow_domain_itm domain_info = itm.Domain_info();
			byte[] wmf_key			= domain_info.Abrv_wm();
			Xol_lang_stub lang_itm	= domain_info.Lang_actl_itm();
			byte[] lang_key			= lang_itm.Key();
			byte[] lang_name		= lang_itm.Canonical_name();
			byte[] domain_bry		= domain_info.Domain_bry();
			byte[] page_name		= itm.Name();
			fmtr_badges.Init_by_itm(itm.Badges());
			byte[] href_site		= xwiki_mgr.Get_by_key(domain_bry) == null ? Href_site_http : Href_site_xowa;
			fmtr_row.Bld_bfr_many(bfr, lang_name, lang_key, wmf_key, href_site, domain_bry, href_encoder.Encode(page_name), Gfh_utl.Escape_html_as_bry(itm.Name()), fmtr_badges);
		}
	}
	private static final    byte[] Href_site_xowa = Bry_.new_a7("/site/"), Href_site_http = Bry_.new_a7("https://");
	private final    Bry_fmtr fmtr_row = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "            <li class='wikibase-sitelinkview'>"																// wikibase-sitelinkview-~{wmf_key} data-wb-siteid='~{wmf_key}'
	, "              <span class='wikibase-sitelinkview-siteid-container'>"
	, "                <span class='wikibase-sitelinkview-siteid'>~{lang_code}"
	, "                </span>"
	, "              </span>"
	, "              <span class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>"						// wikibase-sitelinkview-link-~{wmf_key}
	, "                <span class='wikibase-sitelinkview-page'>"
	, "                  <a href='~{href_site}~{href_domain}/wiki/~{href_page}' hreflang='~{lang_code}' dir='auto'>~{page_name}</a>"
	, "                </span>"
	, "                <span class='wikibase-badgeselector wikibase-sitelinkview-badges'>~{badges}"
	, "                </span>"
	, "              </span>"
	, "            </li>"
	), "lang_name", "lang_code", "wmf_key", "href_site", "href_domain", "href_page", "page_name", "badges"
	);
}
class Wdata_fmtr__slink_badges implements gplx.core.brys.Bfr_arg {
	private Wdata_lbl_mgr lbl_regy; private byte[][] badges;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy) {this.lbl_regy = lbl_regy;}
	public void Init_by_itm(byte[][] badges) {this.badges = badges;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = badges.length;
		for (int i = 0; i < len; ++i) {
			byte[] ttl = badges[i];
			Wdata_lbl_itm lbl = lbl_regy.Get_itm__ttl(ttl);
			byte[] name = Bry_.Empty, cls = Bry_.Empty;
			if (lbl != null) {
				name = lbl.Text();
				cls = Bry_.Replace(lbl.Text_en(), Byte_ascii.Space_bry, Bry_.Empty);	// NOTE: use Text_en; "featured article" -> "featuredarticle"; same for "good article" -> "goodarticle"
			}
			fmtr_row.Bld_bfr_many(bfr, ttl, cls, name);
		}
	}
	private final    Bry_fmtr fmtr_row = Bry_fmtr.new_
	( "\n                  <span class='wb-badge wb-badge-~{ttl} wb-badge-~{cls}' title='~{name}'></span>"
	, "ttl", "cls", "name"
	);
}
