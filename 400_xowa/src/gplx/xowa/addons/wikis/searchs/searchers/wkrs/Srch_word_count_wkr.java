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
import gplx.dbs.*; import gplx.dbs.stmts.*; import gplx.dbs.percentiles.*;
import gplx.xowa.addons.wikis.searchs.dbs.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
class Srch_word_count_wkr extends Percentile_select_base {
	private Srch_word_tbl word_tbl; private Srch_db_cfg db_cfg;
	private Srch_crt_itm sub;
	private int total_link_count, rows_read; private boolean score_too_low;
	private final    Db_stmt_mgr stmt_mgr = new Db_stmt_mgr(); private Db_stmt stmt;
	public int Get_top_10(Srch_search_ctx ctx, Srch_word_tbl word_tbl, Srch_crt_itm sub) {
		super.cxl = ctx.Cxl;
		this.db_cfg = ctx.Addon.Db_mgr().Cfg();
		super.rng = new Percentile_rng().Init(db_cfg.Word_count(), db_cfg.Link_count_score_max());
		super.rng.Select_init(10, Percentile_rng.Score_null, Percentile_rng.Score_null, 0);
		super.rng_log = new Percentile_rng_log(db_cfg.Link_count_score_max());

		rng_log.Init(sub.Raw, 10);
		this.word_tbl = word_tbl;
		this.sub = sub;
		this.total_link_count = 0;
		this.rows_read = 0;
		this.score_too_low = false;
		try {this.Select();}
		finally {stmt = Db_stmt_.Rls(stmt);}
		if		(score_too_low)		return Srch_db_cfg_.Link_count_score_cutoff;
		else if	(rows_read == 1)	return total_link_count * 2;
		else						return total_link_count;
	}
	@Override protected Db_rdr Rdr__init() {
		stmt_mgr.Add_crt_int(word_tbl.fld_link_count_score, rng.Score_bgn());
		stmt_mgr.Add_crt_int(word_tbl.fld_link_count_score, rng.Score_end());
		stmt_mgr.Add_crt_str(word_tbl.fld_text, sub.Sql_data.Rng_bgn);
		stmt_mgr.Add_crt_str(word_tbl.fld_text, sub.Sql_data.Rng_end);
		if (stmt == null) stmt = stmt_mgr.Make_stmt(word_tbl.conn, Fmt__main);
		stmt_mgr.Fill_stmt_and_clear(stmt);
		return stmt.Exec_select__rls_manual();
	}
	@Override protected boolean Row__read(Db_rdr rdr) {
		if (!rdr.Move_next()) return false;
		int cur_link_count = rdr.Read_int(word_tbl.fld_link_count);
		total_link_count += cur_link_count;
		++rows_read;
		return true;
	}
	@Override protected boolean Found_enough() {
		if (rng.Score_bgn() <= db_cfg.Link_count_score_cutoff()) score_too_low = true;
		return rows_read > 0 || score_too_low;
	}
	@Override protected void Rdr__done(boolean rslts_are_enough, boolean rslts_are_done) {
		if (rslts_are_enough) Gfo_usr_dlg_.Instance.Log_many("", "", "search.word_count; rng=~{0}", rng_log.To_str_and_clear());
	}
	private static Bry_fmt
	  Fmt__main = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( "SELECT  w.link_count"
	, "FROM    search_word w INDEXED BY search_word__link_count_score__word_text"
	, "WHERE   w.link_count_score >= ~{score_min}"
	, "AND     w.link_count_score <  ~{score_max}"
	, "AND     w.word_text >= ~{rng_bgn}"
	, "AND     w.word_text <  ~{rng_end}"		
	, "LIMIT 1"
	));
}
