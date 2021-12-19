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
package gplx.xowa.addons.wikis.searchs.specials.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.wikis.searchs.Srch_search_addon;
import gplx.xowa.addons.wikis.searchs.searchers.Srch_search_qry;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.Srch_rslt_list;
import gplx.xowa.addons.wikis.searchs.specials.Srch_qarg_mgr;
import gplx.xowa.htmls.core.htmls.utls.Xoh_anchor_kv_bldr;
import gplx.xowa.htmls.core.htmls.utls.Xoh_img_path;
import gplx.xowa.htmls.core.htmls.utls.Xoh_lnki_bldr;
import gplx.xowa.langs.numbers.Xol_num_mgr;
public class Srch_html_page_bldr {
	private final BryWtr tmp_bfr = BryWtr.NewWithSize(255);
	private Srch_search_qry qry; private Xow_wiki wiki; private Xol_num_mgr num_mgr;
	private int slab_idx;
	private Xoh_lnki_bldr lnki_bldr; private Xoh_anchor_kv_bldr self_lnkr = new Xoh_anchor_kv_bldr(); private Srch_html_row_bldr html_row_bldr;
	private Srch_search_addon addon;
	public void Init_by_wiki(Xow_wiki wiki, Xol_num_mgr num_mgr, Srch_search_qry qry) {
		this.wiki = wiki; this.num_mgr = num_mgr; this.qry = qry;
		this.lnki_bldr = wiki.Html__lnki_bldr();
		int slab_len = qry.Slab_end - qry.Slab_bgn;
		this.slab_idx = qry.Slab_bgn / slab_len;
		this.html_row_bldr = new Srch_html_row_bldr(lnki_bldr);
		self_lnkr.Init_w_qarg(tmp_bfr.Add(Bry__special_search).Add(qry.Phrase.Orig).Add(Bry__fulltext).ToBryAndClear());
	}
	public byte[] Bld_page(byte[] html_tbls_bry) {
		// show a message if no search databases exist; ISSUE#:539; DATE:2019-08-21
		if (addon == null) {
			addon = Srch_search_addon.Get(wiki);
		}
		if (addon != null && addon.Db_mgr().Cfg() == null) {
			return BryUtl.NewU8("<span style='color:red'>Search databases are missing</span>");
		}

		byte[] rslts_hdr = fmtr_rslts.BldToBryMany(tmp_bfr, num_mgr.Format_num(qry.Slab_bgn + List_adp_.Base1), num_mgr.Format_num(qry.Slab_end), qry.Phrase.Orig);
		byte[] option_link = lnki_bldr.Href_(BryUtl.NewA7("home"), wiki.Ttl_parse(BryUtl.NewA7("Options/Search"))).Img_16x16(Xoh_img_path.Img_option).Bld_to_bry();	// HOME
		fmtr_page.BldToBfrMany(tmp_bfr, rslts_hdr, option_link, Bld_paging_link(BoolUtl.N), Bld_paging_link(BoolUtl.Y), html_tbls_bry);
		return tmp_bfr.ToBryAndClear();
	}
	public void Bld_tbl(BryWtr bfr, Srch_rslt_list rslt_list, byte[] cmd_key, byte[] wiki_domain, boolean searching_db, int slab_bgn, int slab_end) {
		html_row_bldr.Init(rslt_list, slab_bgn, slab_end);
		byte[] search_link = lnki_bldr.Href_(wiki_domain, wiki.Ttl_parse(self_lnkr.Bld_to_bry())).Caption_(wiki_domain).Img_16x16(Xoh_img_path.Img_search).Img_pos_is_left_(BoolUtl.Y).Bld_to_bry();
		fmtr_tbl.BldToBfrMany(bfr, search_link, searching_db ? Bld_cancel_link(wiki_domain, cmd_key) : BryUtl.Empty, Bry_hdr_len, Bry_hdr_ttl, Srch_html_row_wkr.Gen_insert_key(wiki_domain), html_row_bldr);
	}		
	private byte[] Bld_cancel_link(byte[] domain, byte[] cmd_key) {	// DELETE: remove cancel link; clicking will blank out results; DATE:2016-06-14
		//	lnki_bldr.Id_(Bry_.Add(Bry_.new_a7("xowa_cancel_"), domain));
		//	lnki_bldr.Href_(wiki, self_lnkr.Add_int(Srch_qarg_mgr.Bry__slab_idx, slab_idx).Add_bry(Srch_qarg_mgr.Bry__cancel, cmd_key).Bld_to_bry());
		//	lnki_bldr.Title_(Bry_cancel);
		//	lnki_bldr.Img_16x16(Xoh_img_path.Img_cancel);
		//	return lnki_bldr.Bld_to_bry();
		return BryUtl.Empty;
	}
	public byte[] Bld_paging_link(boolean fwd) {
		byte[] title = null, img_path = BryUtl.Empty;
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
			if (qarg_slab_idx < 0) return HtmlEntityCodes.NbspNumBry;
			img_path = Xoh_img_path.Img_go_bwd;
			title = Bry_paging_bwd;
		}
		return lnki_bldr.Title_(title).Href_wo_escape_(wiki.Domain_bry(), self_lnkr.Add_int(Srch_qarg_mgr.Bry__slab_idx, qarg_slab_idx).Bld_to_bry()).Img_16x16(img_path).Img_pos_is_left_(img_pos_is_left).Caption_(title).Bld_to_bry();
	}
	private static final BryFmtr fmtr_page = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "~{rslts_hdr}<span style='margin-left:10px'>~{option_link}</span>"
	, "<div id='xowa_panel_top' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>~{tbls}"
	, "<div id='xowa_panel_bot' style='width:60%;'><div style='float:right;'><span>~{bwd_a}</span><span style='margin-left:10px'>~{fwd_a}</span></div></div>"
	), "rslts_hdr", "option_link", "bwd_a", "fwd_a", "tbls");
	private static final BryFmtr fmtr_tbl = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
	private static final BryFmtr fmtr_rslts = BryFmtr.New("Results <b>~{bgn}</b> of <b>~{end}</b> for <b>~{raw}</b>", "bgn", "end", "raw");
	private static final byte[] Bry_paging_fwd = BryUtl.NewA7("Next"), Bry_paging_bwd = BryUtl.NewA7("Previous")// , Bry_cancel = Bry_.new_a7("Stop searching")
	, Bry_hdr_len = BryUtl.NewA7("Page score"), Bry_hdr_ttl = BryUtl.NewA7("Page title")
	;
	private final byte[] Bry__special_search = BryUtl.NewA7("Special:Search/"), Bry__fulltext = BryUtl.NewA7("?fulltext=y");
}
