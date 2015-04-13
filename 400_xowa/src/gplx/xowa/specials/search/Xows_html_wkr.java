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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.html.*; import gplx.xowa.html.wtrs.*; import gplx.xowa.langs.numbers.*;
class Xows_html_wkr {
	private final Bry_bfr bfr = Bry_bfr.new_(255);
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	private Xows_ui_qry qry; private Xoh_lnki_wtr_utl lnki_wtr_utl; private Xol_num_mgr num_mgr;
	public Xows_html_row Html_rows() {return html_rows;} private final Xows_html_row html_rows = new Xows_html_row();
	public void Init_by_wiki(Xoh_lnki_wtr_utl lnki_wtr_utl, Xol_num_mgr num_mgr) {
		this.lnki_wtr_utl = lnki_wtr_utl; this.num_mgr = num_mgr;
		html_rows.Ctor(lnki_wtr_utl);
	}
	@gplx.Internal protected void Qry_(Xows_ui_qry qry) {this.qry = qry;}	// TEST:
	public byte[] Gen_page(Xows_ui_qry qry, byte[] tbls) {
		synchronized (bfr) {
			this.qry = qry;
			byte[] rslts_hdr = fmtr_rslts.Bld_bry_many(tmp_bfr, num_mgr.Format_num(qry.Itms_bgn() + ListAdp_.Base1), num_mgr.Format_num(qry.Itms_end()), qry.Search_raw());
			fmtr_page.Bld_bfr_many(bfr, rslts_hdr, Paging_link(Bool_.N), Paging_link(Bool_.Y), tbls);
			return bfr.Xto_bry_and_clear();
		}
	}
	public void Gen_tbl(Bry_bfr bfr, Xows_ui_qry qry, Xows_ui_rslt rslt, byte[] cmd_key, byte[] wiki_domain, boolean searching_db) {
		synchronized (bfr) {
			this.qry = qry;
			html_rows.Init(rslt);
			fmtr_tbl.Bld_bfr_many(bfr, wiki_domain, searching_db ? Cancel_link(wiki_domain, cmd_key) : Bry_fmtr_arg_.Noop, Bry_hdr_len, Bry_hdr_ttl, Xows_ui_async.Gen_insert_key(wiki_domain), html_rows);
		}
	}
	private Bry_fmtr_arg Cancel_link(byte[] domain, byte[] cmd_key) {
		byte[] ttl_bry = tmp_bfr.Add_str_ascii("Special:Search/").Add(qry.Search_raw()).Add_str_ascii("?fulltext=y&xowa_page_index=").Add_int_variable(qry.Page_idx()).Add_str("&cancel=").Add(Html_utl.Encode_id_as_bry(cmd_key)).Xto_bry_and_clear();
		byte[] href = lnki_wtr_utl.Bld_href(ttl_bry);
		byte[] title = lnki_wtr_utl.Bld_title(Bry_cancel);
		return fmtr_paging_cxl.Vals_("xowa_cancel_" + String_.new_utf8_(domain), href, title, Bry_cancel);
	}
	public Bry_fmtr_arg Paging_link(boolean fwd) {
		int paging_idx = qry.Page_idx();
		byte[] a_text = null;
		Bry_fmtr_vals rv = null;
		if (fwd) {
			++paging_idx;
			a_text = Bry_paging_fwd;
			if (paging_idx > qry.Page_max()) return Bry_fmtr_arg_.bry_(a_text);
			rv = fmtr_paging_fwd;
		}
		else {
			--paging_idx;
			a_text = Bry_paging_bwd;
			if (paging_idx < 0) return Bry_fmtr_arg_.bry_(a_text);
			rv = fmtr_paging_bwd;
		}
		byte[] a_ttl_bry = tmp_bfr.Add_str_ascii("Special:Search/").Add(qry.Search_raw()).Add_str_ascii("?fulltext=y&xowa_page_index=").Add_int_variable(paging_idx).Xto_bry_and_clear();
		byte[] a_href = lnki_wtr_utl.Bld_href(a_ttl_bry);
		byte[] a_title = lnki_wtr_utl.Bld_title(a_text);
		return rv.Vals_(a_href, a_title, a_text);
	}
	private static final Bry_fmtr fmtr_page = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "~{rslts_hdr}"
	, "<div id='xowa_panel_top'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div>~{tbls}"
	, "<div id='xowa_panel_bot'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div>"
	), "rslts_hdr", "bwd_a", "fwd_a", "tbls");
	// 
	private static final Bry_fmtr fmtr_tbl = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<table class='wikitable sortable'>"
	, "  <tr>"
	, "    <th colspan='2' style='text-align:left'><a href='/site/home/wiki/Help:Special/Search'>Help</a>"
	, "    </th>"
	, "  </tr>"
	, "  <tr>"
	, "    <th colspan='2' style='text-align:left'>~{wiki}<span style='margin-left:10px'>~{cancel}</span>"
	, "    </th>"
	, "  </tr>"
	, "  <tr>"
	, "    <th>~{hdr_len}"
	, "    </th>"
	, "    <th>~{hdr_ttl}"
	, "    </th>"
	, "  </tr>~{rows}"
	, "  <tr id='~{insert_key}' style='display:none;'>"
	, "  </tr>"
	, "</table>"
	), "wiki", "cancel", "hdr_len", "hdr_ttl", "insert_key", "rows");
	private static final Bry_fmtr		fmtr_link = Bry_fmtr.new_("<a href='~{a_href}' title='~{a_title}'>~{a_text}</a>", "a_href", "a_title", "a_text");
	private static final Bry_fmtr		fmtr_link_id = Bry_fmtr.new_("<a id='~{a_id}' href='~{a_href}' title='~{a_title}'>~{a_text}</a>", "a_id", "a_href", "a_title", "a_text");
	private static final Bry_fmtr_vals	fmtr_paging_bwd = Bry_fmtr_vals.new_(fmtr_link);
	private static final Bry_fmtr_vals	fmtr_paging_fwd = Bry_fmtr_vals.new_(fmtr_link);
	private static final Bry_fmtr_vals	fmtr_paging_cxl = Bry_fmtr_vals.new_(fmtr_link_id);
	private static final Bry_fmtr		fmtr_rslts = Bry_fmtr.new_("Results <b>~{bgn}</b> of <b>~{end}</b> for <b>~{raw}</b>", "bgn", "end", "raw");
	private static final byte[] Bry_paging_fwd = Bry_.new_ascii_("Next"), Bry_paging_bwd = Bry_.new_ascii_("Previous"), Bry_cancel = Bry_.new_ascii_("Stop searching")
	, Bry_hdr_len = Bry_.new_ascii_("Page length"), Bry_hdr_ttl = Bry_.new_ascii_("Page title")
	;
}
class Xows_html_row implements Bry_fmtr_arg {
	private Xows_ui_rslt rslt; private Xoh_lnki_wtr_utl lnki_wtr_utl;
	public Xows_html_row Ctor(Xoh_lnki_wtr_utl lnki_wtr_utl) {this.lnki_wtr_utl = lnki_wtr_utl; return this;}
	public Xows_html_row Init(Xows_ui_rslt rslt) {this.rslt = rslt; return this;}
	public void XferAry(Bry_bfr bfr, int idx) { // <a href="/wiki/A" title="A" class="xowa-visited">A</a>
		int len = rslt.Len();
		for (int i = 0; i < len; ++i) {
			Xows_db_row row = rslt.Get_at(i);
			Gen_html(bfr, row);
		}
	}
	public void Gen_html(Bry_bfr bfr, Xows_db_row row) {
		byte[] href = lnki_wtr_utl.Bld_href(row.Page_ttl_w_ns());
		byte[] title = lnki_wtr_utl.Bld_title(row.Page_ttl_w_ns());
		fmtr.Bld_bfr_many(bfr, Html_utl.Encode_id_as_str(row.Key()), row.Page_len(), href, title, Xoa_ttl.Replace_unders(row.Page_ttl_w_ns()));
	}
	public Bry_fmtr Fmtr() {return fmtr;} private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr id='~{page_key}'>"
	, "    <td>~{page_len}"
	, "    </td>"
	, "    <td><a href='~{a_href}' title='~{a_title}'>~{a_text}</a>"
	, "    </td>"
	, "  </tr>"
	), "page_key", "page_len", "a_href", "a_title", "a_text");
}
