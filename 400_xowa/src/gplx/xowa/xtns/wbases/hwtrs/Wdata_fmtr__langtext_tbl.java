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
package gplx.xowa.xtns.wbases.hwtrs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.langs.htmls.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.apps.apis.xowa.html.*;
class Wdata_fmtr__langtext_tbl implements BryBfrArg {
	private Wdata_toc_data toc_data; private Wdata_lang_sorter lang_sorter; private Xoapi_toggle_itm toggle_itm; private Wdata_fmtr__langtext_row fmtr_row;
	private byte[] col_hdr_lang_name, col_hdr_lang_code, col_hdr_text; private int list_len;
	public void Init_by_ctor(Wdata_toc_data toc_data, Wdata_lang_sorter lang_sorter, Xoapi_toggle_mgr toggle_mgr, String toggle_itm_key, Wdata_fmtr__langtext_row fmtr_row) {
		this.toc_data = toc_data; this.lang_sorter = lang_sorter; this.fmtr_row = fmtr_row;
		this.toggle_itm = toggle_mgr.Get_or_new(toggle_itm_key);			
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs, byte[] tbl_hdr, byte[] col_hdr_text) {
		this.col_hdr_lang_name = msgs.Langtext_col_lang_name(); this.col_hdr_lang_code = msgs.Langtext_col_lang_code(); this.col_hdr_text = col_hdr_text;
		toc_data.Orig_(tbl_hdr);
		toggle_itm.Init_msgs(msgs.Toggle_title_y(), msgs.Toggle_title_n());
	}
	public void Init_by_wdoc(Ordered_hash list) {
		this.list_len = list.Len(); if (list_len == 0) return;
		toc_data.Make(list_len);
		list.SortBy(lang_sorter);
		fmtr_row.Init_by_page(list);
	}
	public void AddToBfr(BryWtr bfr) {
		if (list_len == 0) return;
		fmtr.BldToBfrMany(bfr, toc_data.Href(), toc_data.Text(), col_hdr_lang_name, col_hdr_lang_code, col_hdr_text, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), fmtr_row);
	}
	private final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "  <div class='wikibase-sitelinkgroupview'>"
	, "    <div class='wikibase-sitelinkgroupview-heading-container'>"
	, "      <h2 class='wb-section-heading wikibase-sitelinkgroupview-heading' dir='auto' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "    </div>"
	, "    <div class='wikibase-sitelinklistview'~{toggle_hdr}>"			
	, "      <ul class='wikibase-sitelinklistview-listview'>~{rows}"
	, "      </ul>"
	, "    </div>"
	, "  </div>"
	), "hdr_href", "hdr_text", "hdr_lang_name", "hdr_lang_code", "hdr_page", "toggle_btn", "toggle_hdr", "rows"
	);
}
interface Wdata_fmtr__langtext_row extends BryBfrArg {
	void Init_by_page(Ordered_hash list);
}
class Wdata_fmtr__langtext_row_base implements BryBfrArg, Wdata_fmtr__langtext_row {
	private Ordered_hash list;
	public void Init_by_page(Ordered_hash list) {this.list = list;}
	public void AddToBfr(BryWtr bfr) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Wdata_langtext_itm itm = (Wdata_langtext_itm)list.GetAt(i);
			Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_intl(itm.Lang());
			row_fmtr.BldToBfrMany(bfr, itm.Lang(), Gfh_utl.Escape_html_as_bry(lang_itm.Canonical_name()), Gfh_utl.Escape_html_as_bry(itm.Text()));
		}
	}
	private final BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "        <li class='wikibase-sitelinkview'>"
	, "          <span class='wikibase-sitelinkview-siteid-container'>"
	, "            <span class='wikibase-sitelinkview-siteid'>~{lang_code}</span>"
	, "          </span>"
	, "          <span class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>~{text}</span>"
	, "        </li>"
	), "lang_code", "lang_name", "text"
	);
//		, "            <li class='wikibase-sitelinkview'>"																// wikibase-sitelinkview-~{wmf_key} data-wb-siteid='~{wmf_key}'
//		, "              <span class='wikibase-sitelinkview-siteid-container'>"
//		, "                <span class='wikibase-sitelinkview-siteid'>~{wmf_key}"
//		, "                </span>"
//		, "              </span>"
//		, "              <span class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>"						// wikibase-sitelinkview-link-~{wmf_key}
//		, "                <span class='wikibase-sitelinkview-page'>"
//		, "                  <a href='~{href_site}~{href_domain}/wiki/~{href_page}' hreflang='~{lang_code}' dir='auto'>~{page_name}</a>"
//		, "                </span>"
//		, "                <span class='wikibase-badgeselector wikibase-sitelinkview-badges'>~{badges}"
//		, "                </span>"
//		, "              </span>"
//		, "            </li>"
}
class Wdata_fmtr__alias_row implements BryBfrArg, Wdata_fmtr__langtext_row {
	private Ordered_hash list;
	public void Init_by_page(Ordered_hash list) {this.list = list;}
	public void AddToBfr(BryWtr bfr) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Wdata_alias_itm itm = (Wdata_alias_itm)list.GetAt(i);
			byte[][] vals_ary = itm.Vals();
			int vals_len = vals_ary.length;
			for (int j = 0; j < vals_len; ++j) {
				byte[] val = vals_ary[j];
				Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_intl(itm.Lang());
				byte[] lang_code = AsciiByte.DashBry;
				byte[] lang_code_style = lang_code_style_n;
				if (j == 0) {
					lang_code = lang_itm.Key();
					lang_code_style = BryUtl.Empty;
				}
				row_fmtr.BldToBfrMany(bfr, lang_code, lang_code_style, Gfh_utl.Escape_html_as_bry(val));
			}
		}
	}
	private static final byte[] lang_code_style_n = BryUtl.NewA7("border:1px solid white;background:none;");
	private final BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "        <li class='wikibase-sitelinkview'>"
	, "          <span class='wikibase-sitelinkview-siteid-container' style='~{lang_code_style}>"
	, "            <span class='wikibase-sitelinkview-siteid''>~{lang_code}</span>"
	, "          </span>"
	, "          <span class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>~{text}</span>"
	, "        </li>"
	), "lang_code", "lang_code_style", "text"
	);
}
