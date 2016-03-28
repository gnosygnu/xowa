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
package gplx.xowa.addons.searchs.dbs.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.dbs.*; import gplx.xowa.addons.searchs.dbs.bldrs.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.pagelinks.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Srch__page__page_score extends Xob_cmd__base implements Xob_cmd {	// create page_rank in page_db; drop and vaccuum later; avoid cross db for now
	private double damping_factor = .85;
	private double error_margin = .0001d;
	private int iteration_max = 1000;
	private Db_conn page_conn, plink_conn;
	private int page_count = 0;
	public Srch__page__page_score(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Srch__page__page_score Iteration_max_(int iteration_max) {this.iteration_max = iteration_max; return this;}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_search__page__page_score;}
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
		if (plink_conn.Meta_tbl_exists(Pagerank__tbl_name)) plink_conn.Meta_tbl_drop(Pagerank__tbl_name);
		Pglnk_page_link_tbl plink_tbl = new Pglnk_page_link_tbl(plink_conn);
		if (!plink_conn.Meta_tbl_exists(plink_tbl.Tbl_name())) plink_tbl.Create_tbl();// create page_link if it doesn't exist; occurs when page_rank_enabled == false;
		plink_conn.Meta_tbl_create(Dbmeta_tbl_itm.New
		( Pagerank__tbl_name
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_page_id).Primary_y_()
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_link_count)
		, Dbmeta_fld_itm.new_int		(Pagerank__fld_has_converged).Default_(0)
		, Dbmeta_fld_itm.new_double		(Pagerank__fld_page_rank).Default_(1)
		));

		new Db_attach_mgr(plink_conn, new Db_attach_itm("page_db", page_conn))
			.Exec_sql_w_msg("generating list of pages",  String_.Concat_lines_nl_skip_last
		( "INSERT INTO page_rank_temp (page_id, link_count)"
		, "SELECT   page_id"
		, ",        Coalesce(Count(pl.trg_id), {0}) AS link_count"
		, "FROM     <page_db>page p"	// NOTE: JOIN needed to filter out [[User:]] pages which are in pagelinks.sql, but not in pages-articles.xml
		, "         LEFT JOIN page_link pl ON p.page_id = pl.src_id"
		// , "WHERE    p.page_namespace = 0"
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
			( "REPLACE INTO page_rank_temp (page_id, page_rank, link_count, has_converged)"
			, "SELECT   pr.page_id"
			, ",        {1} + ({0} * Coalesce(w.page_rank, 0)) AS page_rank"
			, ",        pr.link_count"
			, ",        CASE WHEN Abs(pr.page_rank - ({1} + ({0} * Coalesce(w.page_rank, 0)))) < {2} THEN 1 ELSE 0 END AS has_converged"
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
		else												return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk__damping_factor_ = "damping_factor_", Invk__error_margin_ = "error_margin_", Invk__iteration_max_ = "iteration_max_";
}
