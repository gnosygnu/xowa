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
import gplx.langs.htmls.*; import gplx.xowa.html.wtrs.*; import gplx.xowa.langs.numbers.*;
class Xows_html_wkr {		
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	private Xows_ui_qry qry; private Xow_wiki wiki; private Xol_num_mgr num_mgr;
	private Xoh_lnki_bldr lnki_bldr; private Xoh_anchor_kv_bldr self_lnkr = new Xoh_anchor_kv_bldr(); private Xows_html_row html_rows;		
	public void Init_by_wiki(Xow_wiki wiki, Xol_num_mgr num_mgr, Xows_ui_qry qry) {
		this.wiki = wiki; this.num_mgr = num_mgr; this.qry = qry;
		this.lnki_bldr = wiki.App().Html__lnki_bldr();
		this.html_rows = new Xows_html_row(lnki_bldr);
		self_lnkr.Init_w_qarg(qry.Special_link_base_href());
	}
	public byte[] Gen_page(byte[] tbls) {
		byte[] rslts_hdr = fmtr_rslts.Bld_bry_many(tmp_bfr, num_mgr.Format_num(qry.Itms_bgn() + List_adp_.Base1), num_mgr.Format_num(qry.Itms_end()), qry.Search_raw());
		byte[] option_link = lnki_bldr.Href_(Bry_.new_a7("home"), wiki.Ttl_parse(Bry_.new_a7("Help:Options/Search"))).Img_16x16(Xoh_img_path.Img_option).Bld_to_bry();
		fmtr_page.Bld_bfr_many(tmp_bfr, rslts_hdr, option_link, Paging_link(Bool_.N), Paging_link(Bool_.Y), tbls);
		return tmp_bfr.Xto_bry_and_clear();
	}
	public void Gen_tbl(Bry_bfr bfr, Xows_ui_rslt rslt, byte[] cmd_key, byte[] wiki_domain, boolean searching_db) {
		html_rows.Init(rslt);
		byte[] search_link = lnki_bldr.Href_(wiki_domain, wiki.Ttl_parse(self_lnkr.Bld_to_bry())).Caption_(wiki_domain).Img_16x16(Xoh_img_path.Img_search).Img_pos_is_left_(Bool_.Y).Bld_to_bry();
		fmtr_tbl.Bld_bfr_many(bfr, search_link, searching_db ? Cancel_link(wiki_domain, cmd_key) : Bry_.Empty, Bry_hdr_len, Bry_hdr_ttl, Xows_ui_async.Gen_insert_key(wiki_domain), html_rows);
	}		
	private byte[] Cancel_link(byte[] domain, byte[] cmd_key) {
		lnki_bldr.Id_(Bry_.Add(Bry_.new_a7("xowa_cancel_"), domain));
		lnki_bldr.Href_(wiki, self_lnkr.Add_int(Xows_arg_mgr.Arg_bry_page_index, qry.Page_idx()).Add_bry(Xows_arg_mgr.Arg_bry_cancel, cmd_key).Bld_to_bry());
		lnki_bldr.Title_(Bry_cancel);
		lnki_bldr.Img_16x16(Xoh_img_path.Img_cancel);
		return lnki_bldr.Bld_to_bry();
	}
	public byte[] Paging_link(boolean fwd) {
		int paging_idx = qry.Page_idx();
		byte[] title = null, img_path = Bry_.Empty;
		boolean img_pos_is_left = true;
		if (fwd) {
			++paging_idx;
			// if (paging_idx > qry.Page_max()) return Html_entity_.Nbsp_num_bry;
			img_pos_is_left = false;
			img_path = Xoh_img_path.Img_go_fwd;
			title = Bry_paging_fwd;
		}
		else {
			--paging_idx;
			if (paging_idx < 0) return Html_entity_.Nbsp_num_bry;
			img_path = Xoh_img_path.Img_go_bwd;
			title = Bry_paging_bwd;
		}
		return lnki_bldr.Title_(title).Href_(wiki, self_lnkr.Add_int(Xows_arg_mgr.Arg_bry_page_index, paging_idx).Bld_to_bry()).Img_16x16(img_path).Img_pos_is_left_(img_pos_is_left).Caption_(title).Bld_to_bry();
	}
	private static final Bry_fmtr fmtr_page = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "~{rslts_hdr}<span style='margin-left:10px'>~{option_link}</span>"
	, "<div id='xowa_panel_top' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>~{tbls}"
	, "<div id='xowa_panel_bot' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>"
	), "rslts_hdr", "option_link", "bwd_a", "fwd_a", "tbls");
	private static final Bry_fmtr fmtr_tbl = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<table class='wikitable sortable' style='width:60%;'>"
	, "  <tr>"
	, "    <th colspan='2' style='text-align:left'>~{wiki}<span style='float:right'>~{cancel}</span>"
	, "    </th>"
	, "  </tr>"
	, "  <tr>"
	, "    <th width='100'>~{hdr_len}"
	, "    </th>"
	, "    <th>~{hdr_ttl}"
	, "    </th>"
	, "  </tr>~{rows}"
	, "  <tr id='~{insert_key}' style='display:none;'>"
	, "  </tr>"
	, "</table>"
	), "wiki", "cancel", "hdr_len", "hdr_ttl", "insert_key", "rows");
	private static final Bry_fmtr		fmtr_rslts = Bry_fmtr.new_("Results <b>~{bgn}</b> of <b>~{end}</b> for <b>~{raw}</b>", "bgn", "end", "raw");
	private static final byte[] Bry_paging_fwd = Bry_.new_a7("Next"), Bry_paging_bwd = Bry_.new_a7("Previous"), Bry_cancel = Bry_.new_a7("Stop searching")
	, Bry_hdr_len = Bry_.new_a7("Page length"), Bry_hdr_ttl = Bry_.new_a7("Page title")
	;
}
class Xows_html_row implements Bry_fmtr_arg {
	private final Xoh_lnki_bldr lnki_bldr; private Xows_ui_rslt rslt;
	private final Object thread_lock = new Object();
	public Xows_html_row(Xoh_lnki_bldr lnki_bldr) {this.lnki_bldr = lnki_bldr;}
	public Xows_html_row Init(Xows_ui_rslt rslt) {this.rslt = rslt; return this;}
	public void XferAry(Bry_bfr bfr, int idx) { // <a href="/wiki/A" title="A" class="xowa-visited">A</a>
		int len = rslt.Len();
		for (int i = 0; i < len; ++i) {
			Xows_db_row row = rslt.Get_at(i);
			Gen_html(bfr, row);
		}
	}
	public void Gen_html(Bry_bfr bfr, Xows_db_row row) {
		synchronized (thread_lock) {
			lnki_bldr.Href_(row.Wiki_domain(), row.Page_ttl());
			byte[] title = row.Page_ttl().Full_txt();
			lnki_bldr.Title_(title);
			lnki_bldr.Caption_(title);
			fmtr.Bld_bfr_many(bfr, Html_utl.Encode_id_as_str(row.Key()), row.Page_len(), lnki_bldr.Bld_to_bry());
		}
	}
	public Bry_fmtr Fmtr() {return fmtr;} private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr id='~{page_key}'>"
	, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>~{page_len}"
	, "    </td>"
	, "    <td style='padding-left:5px; vertical-align:top;'>~{lnki}"	// SERVER:"<a href='"; DATE:2015-04-16
	, "    </td>"
	, "  </tr>"
	), "page_key", "page_len", "lnki");
}
