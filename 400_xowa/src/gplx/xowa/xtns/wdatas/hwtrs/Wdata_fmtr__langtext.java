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
import gplx.html.*; import gplx.xowa.wikis.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.apis.xowa.html.*;
class Wdata_fmtr__langtext_tbl implements Bry_fmtr_arg {
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
	public void Init_by_wdoc(OrderedHash list) {
		this.list_len = list.Count(); if (list_len == 0) return;
		toc_data.Make(list_len);
		list.SortBy(lang_sorter);
		fmtr_row.Init_by_page(list);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (list_len == 0) return;
		fmtr.Bld_bfr_many(bfr, toc_data.Href(), toc_data.Text(), col_hdr_lang_name, col_hdr_lang_code, col_hdr_text, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), fmtr_row);
	}
	private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div class='wikibase-sitelinkgroupview'>"
	, "    <div class='wikibase-sitelinkgroupview-heading-container'>"
	, "      <h2 class='wb-section-heading wikibase-sitelinkgroupview-heading' dir='auto' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "    </div>"
	, "    <table class='wikibase-sitelinklistview'~{toggle_hdr}>"
	, "      <colgroup>"
	, "        <col class='wikibase-sitelinklistview-sitename' />"
	, "        <col class='wikibase-sitelinklistview-siteid' />"
	, "        <col class='wikibase-sitelinklistview-link' />"
	, "      </colgroup>"
	, "      <thead>"
	, "        <tr class='wikibase-sitelinklistview-columnheaders'>"
	, "          <th class='wikibase-sitelinkview-sitename'>~{hdr_lang_name}</th>"
	, "          <th class='wikibase-sitelinkview-siteid'>~{hdr_lang_code}</th>"
	, "          <th class='wikibase-sitelinkview-link'>~{hdr_page}</th>"
	, "        </tr>"
	, "      </thead>"
	, "      <tbody>~{rows}"
	, "      </tbody>"
	, "    </table>"
	, "  </div>"
	), "hdr_href", "hdr_text", "hdr_lang_name", "hdr_lang_code", "hdr_page", "toggle_btn", "toggle_hdr", "rows"
	);
}
interface Wdata_fmtr__langtext_row extends Bry_fmtr_arg {
	void Init_by_page(OrderedHash list);
}
class Wdata_fmtr__langtext_row_base implements Wdata_fmtr__langtext_row {
	private OrderedHash list;
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_langtext_itm itm = (Wdata_langtext_itm)list.FetchAt(i);
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(itm.Lang());
			row_fmtr.Bld_bfr_many(bfr, itm.Lang(), Html_utl.Escape_html_as_bry(lang_itm.Local_name()), Html_utl.Escape_html_as_bry(itm.Text()));
		}
	}
	private final Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "        <tr class='wikibase-sitelinkview'>"
	, "          <td class='wikibase-sitelinkview-sitename' lang='~{lang_code}' dir='auto'>~{lang_name}</td>"
	, "          <td class='wikibase-sitelinkview-siteid'>~{lang_code}</td>"
	, "          <td class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>~{text}</td>"
	, "        </tr>"
	), "lang_code", "lang_name", "text"
	);
}
class Wdata_fmtr__alias_row implements Wdata_fmtr__langtext_row {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255); private OrderedHash list;
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_alias_itm itm = (Wdata_alias_itm)list.FetchAt(i);
			byte[][] vals_ary = itm.Vals();
			int vals_len = vals_ary.length;
			for (int j = 0; j < vals_len; ++j) {
				byte[] val = vals_ary[j];
				if (j != 0) tmp_bfr.Add(Html_tag_.Br_inl);
				tmp_bfr.Add(Html_utl.Escape_html_as_bry(val));
			}
			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(itm.Lang());
			row_fmtr.Bld_bfr_many(bfr, lang_itm.Key(), Html_utl.Escape_html_as_bry(lang_itm.Local_name()), tmp_bfr.Xto_bry_and_clear());
		}
	}
	private final Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "        <tr class='wikibase-sitelinkview'>"
	, "          <td class='wikibase-sitelinkview-sitename' lang='~{lang_code}' dir='auto'>~{lang_name}</td>"
	, "          <td class='wikibase-sitelinkview-siteid'>~{lang_code}</td>"
	, "          <td class='wikibase-sitelinkview-link' lang='~{lang_code}' dir='auto'>~{text}</td>"
	, "        </tr>"
	), "lang_code", "lang_name", "text"
	);
}
