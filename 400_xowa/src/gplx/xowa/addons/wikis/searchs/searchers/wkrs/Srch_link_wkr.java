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
package gplx.xowa.addons.wikis.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.searchs.dbs.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*; import gplx.dbs.percentiles.*;
import gplx.xowa.langs.cases.*; import gplx.xowa.addons.wikis.searchs.parsers.*;	
public class Srch_link_wkr extends Percentile_select_base {
	private final    Srch_link_wkr_sql sql_mkr = new Srch_link_wkr_sql();
	private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	private final    Srch_rslt_list tmp_rslts = new Srch_rslt_list();
	private Srch_rslt_list rslts_list; private Srch_rslt_cbk rslt_cbk; private Srch_search_ctx ctx;
	private Xowd_page_tbl page_tbl;
	private Db_stmt stmt;
	private int rslts_bgn, rslts_end;
	private Srch_rslt_row cur_row; private final    Xowd_page_itm tmp_page_itm = new Xowd_page_itm();
	private int link_tbl_idx, link_tbl_nth; private boolean link_loop_done;
	private Srch_crt_itm sql_root;
	public void Search(Srch_rslt_list rslts_list, Srch_rslt_cbk rslt_cbk, Srch_search_ctx ctx) {
		// init
		Gfo_usr_dlg_.Instance.Log_many("", "", "search.search by link_tbl; search=~{0}", ctx.Qry.Phrase.Orig);
		super.cxl = ctx.Cxl;
		super.rng = ctx.Score_rng;
		super.rng_log = new Percentile_rng_log(ctx.Addon.Db_mgr().Cfg().Link_score_max());
		rng_log.Init(ctx.Qry.Phrase.Orig, ctx.Rslts_needed);
		this.rslts_list = rslts_list; this.rslt_cbk = rslt_cbk; this.ctx = ctx;
		this.rslts_bgn = rslts_list.Len(); this.rslts_end = rslts_bgn;
		this.page_tbl = ctx.Tbl__page;

		try {
			// enough results at start; occurs in Special:Search when revisiting slabs; EX: 1-100 -> 101-200 -> 1-100
			if (ctx.Qry.Slab_end < rslts_list.Len()) {
				rslts_list.Rslts_are_enough = true;
				rslt_cbk.On_rslts_found(ctx.Qry, rslts_list, 0, rslts_list.Len());
				return;
			}

			// prepare for iteration
			this.link_tbl_idx = 0;
			this.link_tbl_nth = ctx.Tbl__link__ary.length - 1;
			sql_root = Srch_link_wkr_.Find_sql_root(ctx);
			attach_mgr.Conn_links_(new Db_attach_itm("page_db", ctx.Db__core.Conn()), new Db_attach_itm("word_db", ctx.Tbl__word.conn));
			super.Select();
		}
		finally {
			try {
				Gfo_usr_dlg_.Instance.Log_many("", "", "search.detaching; phrase=~{0} score_bgn=~{1} score_end=~{2}", ctx.Qry.Phrase.Orig, ctx.Score_rng.Score_bgn(), ctx.Score_rng.Score_end());
				attach_mgr.Detach();
				stmt = Db_stmt_.Rls(stmt);
			}
			catch (Exception e) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "search.detaching fail; phrase=~{0} score_bgn=~{1} err=~{2}", ctx.Qry.Phrase.Orig, ctx.Score_rng.Score_bgn(), Err_.Message_gplx_log(e));
			}
		}
	}	
	@Override protected Db_rdr Rdr__init() {
		try {
			Db_conn link_tbl_conn = ctx.Tbl__link__ary[link_tbl_idx].conn;
			attach_mgr.Conn_main_(link_tbl_conn);
			sql_mkr.Init(ctx, attach_mgr, sql_root);
			if (stmt == null) stmt = sql_mkr.Make(ctx, attach_mgr, link_tbl_conn);
			sql_mkr.Fill(stmt);
			return stmt.Exec_select__rls_manual();
		} finally {sql_mkr.Clear();}
	}
	@Override protected boolean Found_enough() {return (rslts_list.Len() + tmp_rslts.Len()) >= ctx.Qry.Slab_end;}
	@Override protected void Rng__update(int rdr_found) {
		link_loop_done = false;
		if (ctx.Qry.Ns_mgr.Ns_main_only()) {
			link_loop_done = true;
		}
		else {
			if (link_tbl_idx == link_tbl_nth) {
				link_tbl_idx = 0;
				link_loop_done = true;
			}
			else {
				++link_tbl_idx;
			}
			// NOTE: must do detach_database and rls_stmt b/c link_tbl_conn changes
			attach_mgr.Detach();
			stmt = Db_stmt_.Rls(stmt);
		}
		if (link_loop_done)
			rng.Update(rslts_end - rslts_bgn);
	}
	@Override protected void Rdr__done(boolean rslts_are_enough, boolean rslts_are_done)  {
		if (!link_loop_done) return;
		int tmp_rslts_len = tmp_rslts.Len();

		// get redirect ttl; note that main rdr should be closed
		for (int i = 0; i < tmp_rslts_len; ++i) {
			Srch_rslt_row row = tmp_rslts.Get_at(i);
			int redirect_id = row.Page_redirect_id;
			if (redirect_id != Srch_rslt_row.Page_redirect_id_null)
				Srch_rslt_list_.Get_redirect_ttl(page_tbl, tmp_page_itm, row);
		}

		// merge to rslts_list; notify; cleanup;
		if (tmp_rslts_len > 0) rslts_list.Merge(tmp_rslts);
		rslts_list.Process_rdr_done(rng, rslts_are_enough, rslts_are_done);
		rslt_cbk.On_rslts_found(ctx.Qry, rslts_list, rslts_bgn, rslts_end);
		rslts_list.Rslts_are_first = false;
		rslts_bgn = rslts_end;
		// Gfo_usr_dlg_.Instance.Log_many("", "", "search.search rslts; rslts=~{0}", rng_log.To_str_and_clear());
	}
	@Override protected boolean Row__read(Db_rdr rdr) {
		if (!rdr.Move_next()) return false;
		byte[] wiki_bry = ctx.Wiki_domain;
		int page_id = rdr.Read_int(page_tbl.Fld_page_id());
		byte[] key = Srch_rslt_row.Bld_key(wiki_bry, page_id);
		this.cur_row = ctx.Cache__page.Get_by(key);				// note that page could have been added from another word
		if (cur_row == null) {
			int page_len = rdr.Read_int(page_tbl.Fld_page_len());
			int page_score = page_tbl.Fld_page_score() == Dbmeta_fld_itm.Key_null ? page_len : rdr.Read_int(page_tbl.Fld_page_score());
			int page_ns_id = rdr.Read_int(page_tbl.Fld_page_ns());
			byte[] page_ttl_wo_ns = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
			Xoa_ttl page_ttl = ctx.Wiki.Ttl_parse(page_ns_id, page_ttl_wo_ns);
			this.cur_row = new Srch_rslt_row(key, wiki_bry, page_ttl, page_ns_id, page_ttl_wo_ns, page_id, page_len, page_score, rdr.Read_int(page_tbl.Fld_redirect_id()));
			ctx.Cache__page.Add(cur_row);
		}
		return true;
	}
	@Override protected boolean Row__eval() {
		if (	!ctx.Qry.Ns_mgr.Has(cur_row.Page_ns)							// ignore: ns doesn't match
			||	!Srch_link_wkr_.Matches(ctx.Crt_mgr__root, ctx.Addon.Ttl_parser(), ctx.Case_mgr, cur_row.Page_ttl_wo_ns)		// ignore: ttl doesn't match ttl_matcher; EX: "A B"
			)
			return false;
		boolean rv = Srch_rslt_list_.Add_if_new(ctx, rslts_list, cur_row);
		if (rv) {
			++rslts_end;
			rslts_list.Ids__add(cur_row.Page_id, cur_row);
			tmp_rslts.Add(cur_row);
		}
		return rv;
	}
	public static int Percentile_rng__calc_adj(int last_word_len) {
		switch (last_word_len) {
			case  1: return     0;
			case  2: return    10;
			case  3: return    20;
			case  4: return    30;
			case  5: return    40;
			case  6: return    50;
			case  7: return    60;
			case  8: return    70;
			default: return    80;
		}
	}
}
