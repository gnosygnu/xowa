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
package gplx.xowa.addons.wikis.searchs.specials.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.langs.numbers.*; import gplx.langs.htmls.entitys.*;
import gplx.xowa.addons.wikis.searchs.specials.*; import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_html_page_bldr {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private Srch_search_qry qry; private Xow_wiki wiki; private Xol_num_mgr num_mgr;
	private int slab_idx;
	private Xoh_lnki_bldr lnki_bldr; private Xoh_anchor_kv_bldr self_lnkr = new Xoh_anchor_kv_bldr(); private Srch_html_row_bldr html_row_bldr;
	public void Init_by_wiki(Xow_wiki wiki, Xol_num_mgr num_mgr, Srch_search_qry qry) {
		this.wiki = wiki; this.num_mgr = num_mgr; this.qry = qry;
		this.lnki_bldr = wiki.Html__lnki_bldr();
		int slab_len = qry.Slab_end - qry.Slab_bgn;
		this.slab_idx = qry.Slab_bgn / slab_len;
		this.html_row_bldr = new Srch_html_row_bldr(lnki_bldr);
		self_lnkr.Init_w_qarg(tmp_bfr.Add(Bry__special_search).Add(qry.Phrase.Orig).Add(Bry__fulltext).To_bry_and_clear());
	}
	public byte[] Bld_page(byte[] html_tbls_bry) {
		byte[] rslts_hdr = fmtr_rslts.Bld_bry_many(tmp_bfr, num_mgr.Format_num(qry.Slab_bgn + List_adp_.Base1), num_mgr.Format_num(qry.Slab_end), qry.Phrase.Orig);
		byte[] option_link = lnki_bldr.Href_(Bry_.new_a7("home"), wiki.Ttl_parse(Bry_.new_a7("Options/Search"))).Img_16x16(Xoh_img_path.Img_option).Bld_to_bry();	// HOME
		fmtr_page.Bld_bfr_many(tmp_bfr, rslts_hdr, option_link, Bld_paging_link(Bool_.N), Bld_paging_link(Bool_.Y), html_tbls_bry);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Bld_tbl(Bry_bfr bfr, Srch_rslt_list rslt_list, byte[] cmd_key, byte[] wiki_domain, boolean searching_db, int slab_bgn, int slab_end) {
		html_row_bldr.Init(rslt_list, slab_bgn, slab_end);
		byte[] search_link = lnki_bldr.Href_(wiki_domain, wiki.Ttl_parse(self_lnkr.Bld_to_bry())).Caption_(wiki_domain).Img_16x16(Xoh_img_path.Img_search).Img_pos_is_left_(Bool_.Y).Bld_to_bry();
		fmtr_tbl.Bld_bfr_many(bfr, search_link, searching_db ? Bld_cancel_link(wiki_domain, cmd_key) : Bry_.Empty, Bry_hdr_len, Bry_hdr_ttl, Srch_html_row_wkr.Gen_insert_key(wiki_domain), html_row_bldr);
	}		
	private byte[] Bld_cancel_link(byte[] domain, byte[] cmd_key) {	// DELETE: remove cancel link; clicking will blank out results; DATE:2016-06-14
		//	lnki_bldr.Id_(Bry_.Add(Bry_.new_a7("xowa_cancel_"), domain));
		//	lnki_bldr.Href_(wiki, self_lnkr.Add_int(Srch_qarg_mgr.Bry__slab_idx, slab_idx).Add_bry(Srch_qarg_mgr.Bry__cancel, cmd_key).Bld_to_bry());
		//	lnki_bldr.Title_(Bry_cancel);
		//	lnki_bldr.Img_16x16(Xoh_img_path.Img_cancel);
		//	return lnki_bldr.Bld_to_bry();
		return Bry_.Empty;
	}
	public byte[] Bld_paging_link(boolean fwd) {
		byte[] title = null, img_path = Bry_.Empty;
		boolean img_pos_is_left = true;
		int qarg_slab_idx = slab_idx;
		if (fwd) {
			++qarg_slab_idx;
			// if (slab_idx > qry.Page_max()) return Gfh_entity_.Nbsp_num_bry;
			img_pos_is_left = false;
			img_path = Xoh_img_path.Img_go_fwd;
			title = Bry_paging_fwd;
		}
		else {
			--qarg_slab_idx;
			if (qarg_slab_idx < 0) return Gfh_entity_.Nbsp_num_bry;
			img_path = Xoh_img_path.Img_go_bwd;
			title = Bry_paging_bwd;
		}
		return lnki_bldr.Title_(title).Href_wo_escape_(wiki.Domain_bry(), self_lnkr.Add_int(Srch_qarg_mgr.Bry__slab_idx, qarg_slab_idx).Bld_to_bry()).Img_16x16(img_path).Img_pos_is_left_(img_pos_is_left).Caption_(title).Bld_to_bry();
	}
	private static final    Bry_fmtr fmtr_page = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "~{rslts_hdr}<span style='margin-left:10px'>~{option_link}</span>"
	, "<div id='xowa_panel_top' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>~{tbls}"
	, "<div id='xowa_panel_bot' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>"
	), "rslts_hdr", "option_link", "bwd_a", "fwd_a", "tbls");
	private static final    Bry_fmtr fmtr_tbl = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
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
	private static final    Bry_fmtr		fmtr_rslts = Bry_fmtr.new_("Results <b>~{bgn}</b> of <b>~{end}</b> for <b>~{raw}</b>", "bgn", "end", "raw");
	private static final    byte[] Bry_paging_fwd = Bry_.new_a7("Next"), Bry_paging_bwd = Bry_.new_a7("Previous")// , Bry_cancel = Bry_.new_a7("Stop searching")
	, Bry_hdr_len = Bry_.new_a7("Page score"), Bry_hdr_ttl = Bry_.new_a7("Page title")
	;
	private final    byte[] Bry__special_search = Bry_.new_a7("Special:Search/"), Bry__fulltext = Bry_.new_a7("?fulltext=y");
}
