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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.nss.*;
public class Adjustment_cmd implements Gfo_invk {
	private final    Xow_wiki wiki;
	private final    Page_matcher_mgr matcher_mgr;
	private boolean enabled = true;
	private double median_factor = .001d;
	public Adjustment_cmd(Xow_wiki wiki) {
		this.wiki = wiki;
		this.matcher_mgr = new Page_matcher_mgr(wiki);
	}
	public void Exec() {
		// init
		if (!enabled) return;
		wiki.Init_by_wiki();
		Xob_db_file pl_db = Xob_db_file.New__page_link(wiki);
		Db_conn pl_conn =  pl_db.Conn();

		// add fields to page_rank_tbl
		String page_rank_tbl = Xobldr__page__page_score.Pagerank__tbl_name;
		pl_conn.Meta_fld_assert(page_rank_tbl, "score_old"	, Dbmeta_fld_tid.Itm__int, 0);
		pl_conn.Meta_fld_assert(page_rank_tbl, "len_avg"	, Dbmeta_fld_tid.Itm__int, 0);

		// create penalty summary tbl
		pl_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("penalty_summary"
		, Dbmeta_fld_itm.new_int("page_ns")
		, Dbmeta_fld_itm.new_int("score_bgn")
		, Dbmeta_fld_itm.new_int("score_end")
		, Dbmeta_fld_itm.new_int("len_avg")
		, Dbmeta_fld_itm.new_int("page_count")
		));
		Db_stmt summary_stmt = pl_conn.Stmt_insert("penalty_summary", "page_ns", "score_bgn", "score_end", "len_avg", "page_count");
		Db_stmt detail_stmt = pl_conn.Stmt_update(page_rank_tbl, String_.Ary("page_id"), "page_score", "score_old", "len_avg");

		// iterate namespaces
		pl_conn.Meta_idx_create("page_rank_temp", "adjustment", "page_namespace", "page_score");
		Db_stmt page_select = pl_conn.Stmt_sql("SELECT pr.page_id, pr.page_is_redirect, pr.page_len, pr.page_score FROM page_rank_temp pr WHERE pr.page_namespace = ? ORDER BY pr.page_score DESC");// ANSI.Y
		int ns_len = wiki.Ns_mgr().Ords_len();
		for (int i = 0; i < ns_len; ++i) {
			Xow_ns ns = wiki.Ns_mgr().Ords_get_at(i);
			if (ns.Count() > 0) {
				Page_matcher_wkr matcher_wkr = matcher_mgr.Get_by(ns.Id()).Load_all();
				pl_conn.Txn_bgn("pl_penalty");
				Calc_for_ns(wiki, matcher_wkr, page_select, summary_stmt, detail_stmt, ns);
				pl_conn.Txn_end();
			}
		}

		// cleanup
		detail_stmt.Rls();
		summary_stmt.Rls();
		page_select.Rls();
	}
	private void Calc_for_ns(Xow_wiki wiki, Page_matcher_wkr wkr, Db_stmt page_select, Db_stmt summary_stmt, Db_stmt detail_stmt, Xow_ns ns) {
		// calc sample
		int sample_len = (int)(ns.Count() * .0001);	// 12,000,000 * .0001 -> 1200
		if (sample_len < 100) sample_len = 100;		// if < 100, default to 100; else small sample sizes like 5 will create strange medians
		Page_stub[] page_ary = new Page_stub[sample_len];

		// loop
		Gfo_log_.Instance.Prog("loading pages for ns", "ns_id", ns.Id());
		Db_rdr rdr = page_select.Clear().Crt_int("page_namespace", ns.Id()).Exec_select__rls_manual();	// ANSI.Y
		int page_count = 0;
		int score_end = -1, score_cur = -1;
		int range_count = 0;
		while (rdr.Move_next()) {
			int page_id = rdr.Read_int("page_id");
			boolean page_is_redirect = rdr.Read_bool_by_byte("page_is_redirect");
			int page_len = rdr.Read_int("page_len");
			score_cur = rdr.Read_int("page_score");
			if (score_end == -1) score_end = score_cur;

			page_ary[page_count] = new Page_stub(page_id, page_is_redirect, page_len, score_cur);
			++page_count;
			if (page_count == sample_len) {
				if ((++range_count % 100) == 0) Gfo_log_.Instance.Prog("updating range", "ns", ns.Id(), "score_cur", score_cur, "score_end", score_end);
				Save_sample(page_ary, wkr, wiki.Domain_bry(), ns.Id(), score_cur, score_end, summary_stmt, detail_stmt);

				// reset
				page_count = 0;
				score_end = -1;
			}
		}
		page_ary = (Page_stub[])Array_.Resize(page_ary, page_count);
		Save_sample(page_ary, wkr, wiki.Domain_bry(), ns.Id(), score_cur, score_end, summary_stmt, detail_stmt);
		rdr.Rls();
	}
	private void Save_sample(Page_stub[] page_ary, Page_matcher_wkr wkr, byte[] domain_bry, int ns_id, int score_cur, int score_end, Db_stmt summary_stmt, Db_stmt detail_stmt) {			
		// calc median
		Array_.Sort(page_ary);
		int ary_len = page_ary.length; if (ary_len == 0) return;	// occurs when ns has exact multiple of .01% pages; EX: 10,000 pages (but not 10,001, 10,002, etc..)
		int median = Calc_median(page_ary);

		// insert
		for (int i = 0; i < ary_len; ++i) {
			Page_stub page = page_ary[i];
			int score_old = page.Score;
			int score_new = score_old;
			Page_matcher_itm itm = wkr.Get_by_or_null(page.Id);
			if (itm != null) {
				score_new = itm.Calc(score_old);
			}
			// if len < median, penalize by .01 of difference;
			/*
				EX: score=1000; page_len=80; median_len=100
					  .8	<- 80 / 100 
					  .2	<- 1 - .8
					.002	<- .2 * .01 
					   2	<- 1000 * .002
					 998	<- 1000 - 2
			*/
			else if (page.Len < median)
				score_new = (int)((1 - (1 - ((double)page.Len / median)) * median_factor) * score_old);

			detail_stmt.Clear()
				.Val_int("page_score"	, score_new)
				.Val_int("score_old"	, score_old)
				.Val_int("len_avg"		, median)
				.Crt_int("page_id"		, page.Id)
				.Exec_update();
		}
		summary_stmt.Clear().Val_int("page_ns", ns_id).Val_int("score_bgn", score_cur).Val_int("score_end", score_end)
			.Val_int("len_avg", median).Val_int("page_count", ary_len)
			.Exec_insert();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__enabled_))			enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__median_factor_))	median_factor = m.ReadDouble("v");
		else if	(ctx.Match(k, Invk__match_mgr))			return matcher_mgr;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__match_mgr = "match_mgr", Invk__enabled_ = "enabled_", Invk__median_factor_ = "median_factor_";

	private static int Calc_median(Page_stub[] ary) {
		int len = ary.length;
		int redirect_end = 0;			
		for (int i = 0; i < len; ++i) {
			Page_stub itm = ary[i];
			if (!itm.Is_redirect) {
				redirect_end = i;
				break;
			}
		}
		int median_idx = (len - redirect_end) / 2;
		return ary[median_idx].Len;
	}
}
