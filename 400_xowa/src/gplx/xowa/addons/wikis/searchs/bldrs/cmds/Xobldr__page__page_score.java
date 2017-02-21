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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.bldrs.wmdumps.pagelinks.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xobldr__page__page_score extends Xob_cmd__base implements Xob_cmd {	// create page_rank in page_db; drop and vaccuum later; avoid cross db for now
	private double damping_factor = .85;
	private double error_margin = .0001d;
	private int iteration_max = 1000;
	private Db_conn page_conn, plink_conn;
	private int page_count = 0;
	public Xobldr__page__page_score(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xobldr__page__page_score Iteration_max_(int iteration_max) {this.iteration_max = iteration_max; return this;}
	@Override public void Cmd_run() {
		Init__tbl();
		Calc__main();
	}
	private void Init__tbl() {
		wiki.Init_assert();
		this.page_conn = wiki.Data__core_mgr().Db__core().Conn();
		page_count = page_conn.Exec_select_as_int("SELECT Count(page_id) FROM page", -1);
		
		Xob_db_file page_link_db = Xob_db_file.New__page_link(wiki);
		this.plink_conn = page_link_db.Conn();
		if (plink_conn.Meta_tbl_exists(Pagerank__tbl_name)) plink_conn.Meta_tbl_delete(Pagerank__tbl_name);
		Pglnk_page_link_tbl plink_tbl = new Pglnk_page_link_tbl(plink_conn);
		if (!plink_conn.Meta_tbl_exists(plink_tbl.Tbl_name())) plink_tbl.Create_tbl();// create page_link if it doesn't exist; occurs when page_rank_enabled == false;
		plink_conn.Meta_tbl_create(Dbmeta_tbl_itm.New
		( Pagerank__tbl_name
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_page_id).Primary_y_()
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_link_count)
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_has_converged).Default_(0)
		, Dbmeta_fld_itm.new_double		(Pagerank__fld_page_rank).Default_(1)
		, Dbmeta_fld_itm.new_int		("page_namespace").Default_(Int_.Min_value)
		, Dbmeta_fld_itm.new_byte		("page_is_redirect").Default_(0)
		, Dbmeta_fld_itm.new_int		("page_len").Default_(0)
		));

		new Db_attach_mgr(plink_conn, new Db_attach_itm("page_db", page_conn))
			.Exec_sql_w_msg("generating list of pages",  String_.Concat_lines_nl_skip_last
		( "INSERT INTO page_rank_temp (page_id, link_count, page_namespace, page_is_redirect, page_len)"
		, "SELECT   p.page_id"
		, ",        Coalesce(Count(pl.trg_id), {0}) AS link_count"
		, ",        p.page_namespace"
		, ",        p.page_is_redirect"
		, ",        p.page_len"
		, "FROM     <page_db>page p"	// NOTE: JOIN needed to filter out [[User:]] pages which are in pagelinks.sql, but not in pages-articles.xml
		, "         LEFT JOIN page_link pl ON p.page_id = pl.src_id"
		, "GROUP BY p.page_id"
		), page_count);
	}
	private void Calc__main() {
		int iteration_idx = 0;
		double damping_factor_inverse = 1 - damping_factor;
		while (true) {
			if (iteration_idx == iteration_max) break;
			int converged_count = plink_conn.Exec_select_as_int("SELECT Count(page_id) FROM page_rank_temp WHERE has_converged = 0;", 0);
			if (converged_count == 0) break;
			new Db_attach_mgr(plink_conn, new Db_attach_itm("page_db", page_conn))
				.Exec_sql_w_msg(String_.Format("calculating page_rank; iteration={0} unconverged={1}", iteration_idx, converged_count)
			, String_.Concat_lines_nl_skip_last
			( "REPLACE INTO page_rank_temp (page_id, page_rank, link_count, has_converged, page_namespace, page_is_redirect, page_len)"
			, "SELECT   pr.page_id"
			, ",        {1} + ({0} * Coalesce(w.page_rank, 0)) AS page_rank"
			, ",        pr.link_count"
			, ",        CASE WHEN Abs(pr.page_rank - ({1} + ({0} * Coalesce(w.page_rank, 0)))) < {2} THEN 1 ELSE 0 END AS has_converged"
			, ",        pr.page_namespace"
			, ",        pr.page_is_redirect"
			, ",        pr.page_len"
			, "FROM     page_rank_temp pr"
			, "         LEFT JOIN"
			, "         (SELECT	lnk.trg_id"
			, "         ,       Sum((Cast(pr2.page_rank AS double) / Cast(pr2.page_rank AS double))) * {0} AS page_rank"
			, "         FROM    page_rank_temp pr2"
			, "                 JOIN page_link lnk ON pr2.page_id = lnk.src_id"
			, "                         JOIN <page_db>page p ON lnk.src_id = p.page_id"
			// , "         WHERE   p.page_namespace = 0"
			, "         GROUP BY lnk.trg_id"
			, "         )       AS w ON w.trg_id = pr.page_id"
			, "WHERE    pr.has_converged = 0"
			), damping_factor, damping_factor_inverse, error_margin);
			++iteration_idx;
		}
		Gfo_usr_dlg_.Instance.Note_many("", "", "page_rank done; iteration=~{0}", iteration_idx);
	}
	public static final String 
	  Pagerank__tbl_name			= "page_rank_temp"
	, Pagerank__fld_page_id			= "page_id"
	, Pagerank__fld_page_rank		= "page_rank"
	, Pagerank__fld_link_count		= "link_count"
	, Pagerank__fld_has_converged	= "has_converged"
	;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__damping_factor_))		damping_factor = m.ReadDouble("v");
		else if	(ctx.Match(k, Invk__error_margin_))			error_margin = m.ReadDouble("v");
		else if	(ctx.Match(k, Invk__iteration_max_))		iteration_max = m.ReadInt("v");
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__damping_factor_ = "damping_factor_", Invk__error_margin_ = "error_margin_", Invk__iteration_max_ = "iteration_max_";

	public static final String BLDR_CMD_KEY = "search.page__page_score";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__page__page_score(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__page__page_score(bldr, wiki);}
}
