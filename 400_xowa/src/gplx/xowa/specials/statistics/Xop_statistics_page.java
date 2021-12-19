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
package gplx.xowa.specials.statistics;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.numbers.*;
import gplx.xowa.wikis.nss.*;
public class Xop_statistics_page implements Xow_special_page {
	private Xop_statistics_stats_page_grp stats_page = new Xop_statistics_stats_page_grp();
//		private Xop_statistics_stats_wiki_grp stats_wiki = new Xop_statistics_stats_wiki_grp();
	private Xop_statistics_stats_ns_grp stats_ns = new Xop_statistics_stats_ns_grp();
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__statistics;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		byte[] html = Build_html(wiki);
		page.Html_data().Html_restricted_n_();	// [[Special:]] pages allow all HTML
		page.Db().Text().Text_bry_(html);
	}
	public byte[] Build_html(Xowe_wiki wiki) {
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetM001();
		stats_page.Wiki_(wiki);
//			stats_wiki.Wiki_(wiki);
		stats_ns.Wiki_(wiki);
		fmtr_all.BldToBfrMany(tmp_bfr, stats_page, stats_ns);
		return tmp_bfr.ToBryAndRls();
	}
	private BryFmtr fmtr_all = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	(	"<div id=\"mw-content-text\">"
	,	"<table class=\"wikitable mw-statistics-table\">~{page_stats}~{ns_stats}"
	,	"</table>"
	,	"</div>"
	), "page_stats", "ns_stats");

	public Xow_special_page Special__clone() {return this;}
}
class Xop_statistics_stats_page_grp implements BryBfrArg {
	public void Wiki_(Xowe_wiki v) {this.wiki = v;} private Xowe_wiki wiki;
	public void AddToBfr(BryWtr bfr) {
		byte[] lbl_header_pages = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_statistics_header_pages);
		byte[] lbl_articles = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_statistics_articles);
		byte[] lbl_pages = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_statistics_pages);
		byte[] lbl_pages_desc = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_statistics_pages_desc);
		Xol_num_mgr num_mgr = wiki.Lang().Num_mgr();
		fmtr_page.BldToBfrMany(bfr, lbl_header_pages, lbl_articles, lbl_pages, lbl_pages_desc , num_mgr.Format_num_by_long(wiki.Stats().Num_articles()), num_mgr.Format_num_by_long(wiki.Stats().Num_pages()));
	}
	private BryFmtr fmtr_page = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	(	""
	,	"  <tr>"
	,	"    <th colspan=\"2\">~{lbl_header_pages}</th>"
	,	"  </tr>"
	,	"  <tr class=\"mw-statistics-articles\">"
	,	"    <td>~{lbl_articles}</td>"
	,	"    <td class=\"mw-statistics-numbers\" style='text-align:right'>~{page_count_main}</td>"
	,	"  </tr>"
	,	"  <tr class=\"mw-statistics-pages\">"
	,	"    <td>~{lbl_pages}<br /><small class=\"mw-statistic-desc\"> ~{lbl_pages_desc}</small></td>"
	,	"    <td class=\"mw-statistics-numbers\" style='text-align:right'>~{page_count_all}</td>"
	,	"  </tr>"
	), "lbl_header_pages", "lbl_articles", "lbl_pages", "lbl_pages_desc", "page_count_main", "page_count_all");
}
class Xop_statistics_stats_ns_grp implements BryBfrArg {
	private Xop_statistics_stats_ns_itm ns_itm_fmtr = new Xop_statistics_stats_ns_itm();
	public void Wiki_(Xowe_wiki v) {this.wiki = v; ns_itm_fmtr.Wiki_(v);} private Xowe_wiki wiki;
	public void AddToBfr(BryWtr bfr) {
		byte[] lbl_header_ns = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_statistics_header_ns);
		fmtr_ns_grp.BldToBfrMany(bfr, lbl_header_ns, ns_itm_fmtr);
	}
	private BryFmtr fmtr_ns_grp = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	(	""
	,	"  <tr>"
	,	"    <th colspan=\"2\">~{lbl_header_ns}</th>"
	,	"  </tr>~{ns_itms}"
	), "lbl_header_ns", "ns_itms");
}
class Xop_statistics_stats_ns_itm implements BryBfrArg {
	public void Wiki_(Xowe_wiki v) {this.wiki = v;} private Xowe_wiki wiki;
	public void AddToBfr(BryWtr bfr) {
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		int ns_len = ns_mgr.Count();
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			if (ns.Is_meta()) continue;
			if (ns.Count() == 0) continue;
			byte[] ns_name = ns.Id_is_main() ? wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ns_blankns) : ns.Name_ui();
			fmtr_ns_itm.BldToBfrMany(bfr, ns_name, wiki.Lang().Num_mgr().Format_num(ns.Count()));
		}
	}
	private BryFmtr fmtr_ns_itm = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	(	""
	,	"  <tr>"
	,	"    <td>~{ns_name}</td>"
	,	"    <td style='text-align:right'>~{ns_count}</td>"
	,	"  </tr>"
	), "ns_name", "ns_count");
}
class Xop_statistics_stats_wiki_grp implements BryBfrArg {
	public void Wiki_(Xowe_wiki v) {this.wiki = v;} private Xowe_wiki wiki;
	public void AddToBfr(BryWtr bfr) {
		fmtr_wiki.BldToBfrMany(bfr, wiki.Db_mgr().Tid_name(), wiki.Fsys_mgr().Root_dir().Raw(), ByteUtl.ToStr(wiki.Db_mgr().Category_version()), wiki.Maint_mgr().Wiki_dump_date().ToStrFmtIso8561());
	}
	private BryFmtr fmtr_wiki = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	(	""	
	,	"  <tr>"
	,	"    <th colspan=\"2\">Wiki statistics</th>"
	,	"  </tr>"
	,	"  <tr>"
	,	"    <td>Wiki format</td>"
	,	"    <td>~{wiki_format}</td>"
	,	"  </tr>"
	,	"  <tr>"
	,	"    <td>Wiki location</td>"
	,	"    <td>~{wiki_url}</td>"
	,	"  </tr>"
	,	"  <tr>"
	,	"    <td>Category level</td>"
	,	"    <td>~{ctg_version}</td>"
	,	"  </tr>"
	,	"  <tr>"
	,	"    <td>Last page updated on</td>"
	,	"    <td>~{page_modified_max}</td>"
	,	"  </tr>"
	), "wiki_format", "wiki_url", "ctg_version", "page_modified_max");
}
