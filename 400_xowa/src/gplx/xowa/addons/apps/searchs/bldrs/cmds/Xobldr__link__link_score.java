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
package gplx.xowa.addons.apps.searchs.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.bldrs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.addons.builds.utils_rankings.bldrs.*;
import gplx.xowa.addons.apps.searchs.dbs.*;
public class Xobldr__link__link_score extends Xob_cmd__base {
	private int score_multiplier = 100000000;
	private boolean page_rank_enabled = false;
	private boolean delete_plink_db = false;
	public Xobldr__link__link_score(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xobldr__link__link_score Page_rank_enabled_(boolean v) {this.page_rank_enabled = v; return this;}
	public Xobldr__link__link_score Delete_plink_db_(boolean v) {this.delete_plink_db = v; return this;}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		Db_conn plink_conn = Db_conn_bldr.Instance.Get_or_autocreate(false, wiki.Fsys_mgr().Root_dir().GenSubFil(Xob_db_file.Name__page_link));
		String page_rank_tbl = Xobldr__page__page_score.Pagerank__tbl_name;
		String log_module = "search.page.score";
		Xoa_app_.Plog_none(log_module, "adding fields to page_rank_temp");
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_len"		, Dbmeta_fld_tid.Itm__int, 0);
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_len_score"	, Dbmeta_fld_tid.Itm__int, 0);
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_rank_score"	, Dbmeta_fld_tid.Itm__double, 0);
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_score"		, Dbmeta_fld_tid.Itm__int, 0);
		int link_score_max = Srch_search_addon.Score_max;

		// percentize page_len_score to 0 : 100,000,000; NOTE: 100,000,000 so that no individual score should have 2+ page; i.e.: score_range > page_count
		new Db_attach_mgr(plink_conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Tbl__page().conn))
			.Exec_sql_w_msg("filling page_rank_temp.page_len", String_.Concat_lines_nl_skip_last
			( "UPDATE   page_rank_temp"
			, "SET      page_len = (SELECT page_len FROM <page_db>page p WHERE p.page_id = page_rank_temp.page_id)"
			));
		new Sqlite_percentile_cmd(bldr, wiki).Init_by_rel_url(Xob_db_file.Name__page_link, "temp_page_len", link_score_max, String_.Concat_lines_nl_skip_last
		( "SELECT   p.page_id, p.page_len"
		, "FROM     <page_db>page p"
		, "ORDER BY p.page_len"	// NOTE: ORDER BY is needed b/c INSERT into AUTO INCREMENT table
		)).Cmd_run();
		plink_conn.Exec_sql("finalizing page_rank_temp.page_len_score", String_.Concat_lines_nl_skip_last
		( "UPDATE   page_rank_temp"
		, "SET      page_len_score = (SELECT tpl.row_score FROM temp_page_len tpl WHERE tpl.row_key = page_rank_temp.page_id)"
		));

		// calc page_score
		if (page_rank_enabled) {
			// get min / max
			Xoa_app_.Plog_none(log_module, "calculating page_rank range");
			double page_rank_min = plink_conn.Exec_select_as_double("SELECT Min(page_rank) FROM " + page_rank_tbl, Double_.MinValue); if (page_rank_min == Double_.MinValue) throw Err_.new_("bldr", "failed to get min; tbl=~{0}", page_rank_tbl);
			double page_rank_max = plink_conn.Exec_select_as_double("SELECT Max(page_rank) FROM " + page_rank_tbl, Double_.MinValue); if (page_rank_max == Double_.MinValue) throw Err_.new_("bldr", "failed to get max; tbl=~{0}", page_rank_tbl);
			double page_rank_rng = page_rank_max - page_rank_min;
			if (page_rank_rng == 0) page_rank_rng = 1; // if 0, set to 1 to prevent divide by 0 below;
			String score_multiplier_as_str = Dbmeta_fld_itm.To_double_str_by_int(score_multiplier);

			// normalize page_rank to 0 : 100,000,000
			plink_conn.Exec_sql
			( "normalizing page_rank_temp.page_rank_score"
			, Bry_fmt.Make_str
				("UPDATE ~{tbl} SET page_rank_score = ((Coalesce(page_rank, 0)) / ~{page_score_rng}) * ~{score_multiplier}"
				, page_rank_tbl, Double_.To_str(page_rank_rng), score_multiplier_as_str)
			);

			// adjust for page_len where pages in lower 60% will get penalized; EX: page_rank = 100; page_len = 50,000,000 -> 100 * (50 M / 100 M) -> 50
			String page_len_cutoff = Dbmeta_fld_itm.To_double_str_by_int((int)(score_multiplier * .6));
			plink_conn.Exec_sql
			( "penalizing short pages"
			, Bry_fmt.Make_str(String_.Concat_lines_nl_skip_last
			( "UPDATE   page_rank_temp"
			, "SET      page_rank_score = (page_rank_score * CASE WHEN page_len_score < ~{page_len_cutoff} THEN (page_len_score / ~{score_multiplier}) ELSE 1 END)"
			), page_len_cutoff, score_multiplier_as_str));

			// percentize page_rank_score to 0 : 100,000
			new Sqlite_percentile_cmd(bldr, wiki).Init_by_rel_url(Xob_db_file.Name__page_link, "temp_page_score", link_score_max, String_.Concat_lines_nl_skip_last
			( "SELECT   prt.page_id, prt.page_rank_score"
			, "FROM     page_rank_temp prt"
			, "ORDER BY prt.page_rank_score"	// NOTE: ORDER BY is needed b/c INSERT into AUTO INCREMENT table
			)).Cmd_run();
			plink_conn.Exec_sql
			( "finalizing page_rank_temp.page_score"
			, String_.Concat_lines_nl_skip_last
			( "UPDATE   page_rank_temp"
			, "SET      page_score = Cast((SELECT tmp.row_score FROM temp_page_score tmp WHERE tmp.row_key = page_rank_temp.page_id) AS int)"
			));
		}
		else {
			plink_conn.Exec_sql
			( "finalizing page_rank_temp.page_score"
			, String_.Format(String_.Concat_lines_nl_skip_last
			( "UPDATE   page_rank_temp"
			, "SET      page_score = Cast(page_len_score AS int)"
			)));
		}
		plink_conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl("page_rank_temp", "page_score", "page_id", "page_score"));

		// update page table
		Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Tbl__page();
		Db_conn page_conn = page_tbl.conn;
		if (page_tbl.Fld_page_score() == Dbmeta_fld_itm.Key_null) {
			page_conn.Meta_fld_append(page_tbl.Tbl_name(), Dbmeta_fld_itm.new_int(Xowd_page_tbl.Fld__page_score__key).Default_(0));
			page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page__rebind();
		}
		new Db_attach_mgr(page_conn, new Db_attach_itm("plink_db", plink_conn))
			.Exec_sql_w_msg("updating page.page_score", String_.Concat_lines_nl_skip_last
			( "UPDATE  page"
			, "SET     page_score = Coalesce((SELECT Cast(pr.page_score AS integer) FROM <plink_db>page_rank_temp pr WHERE pr.page_id = page.page_id), 0)"
			));

		// update link tables
		Srch_db_mgr search_db_mgr = Srch_search_addon.Get(wiki).Db_mgr();
		Srch_word_tbl word_tbl = search_db_mgr.Tbl__word();
		if (!page_tbl.conn.Eq(word_tbl.conn)) page_tbl.conn.Env_vacuum();	// don't vacuum if single-db
		int link_tbls_len = search_db_mgr.Tbl__link__len();
		for (int i = 0; i < link_tbls_len; ++i) {
			Srch_link_tbl link_tbl = search_db_mgr.Tbl__link__get_at(i);
			// update search_link.link_score
			link_tbl.conn.Meta_fld_assert(link_tbl.tbl_name, Srch_link_tbl.Fld_link_score, Dbmeta_fld_tid.Itm__int, 0);
			new Db_attach_mgr(link_tbl.conn
				, new Db_attach_itm("page_db", page_conn)
				).Exec_sql_w_msg
				( Bry_fmt.Make_str("updating search_link.link_score: ~{idx} of ~{total}", i + 1, link_tbls_len)
				, String_.Concat_lines_nl_skip_last
				( "UPDATE  search_link"
				, "SET     link_score = Coalesce((SELECT page_score FROM <page_db>page p WHERE p.page_id = search_link.page_id), 0)"
				));

			// update word_tbl.page_score_max
			new Db_attach_mgr(word_tbl.conn
				, new Db_attach_itm("link_db", link_tbl.conn)
				)
				.Exec_sql_w_msg
				( Bry_fmt.Make_str("updating link_score_min: ~{idx} of ~{total}", i + 1, link_tbls_len)
				, String_.Concat_lines_nl_skip_last
				( "REPLACE INTO search_word (word_id, word_text, link_count, link_score_min, link_score_max)"
				, "SELECT  sw.word_id, sw.word_text, sw.link_count, Min(sl.link_score), sw.link_score_max"
				, "FROM    search_word sw"
				, "        JOIN <link_db>search_link sl ON sl.word_id = sw.word_id"
				, "GROUP BY sw.word_id, sw.word_text, sw.link_count, sw.link_score_max"
				, "HAVING  sw.link_score_min > Min(sl.link_score)"
				))
				.Exec_sql_w_msg
				( Bry_fmt.Make_str("updating link_score_max: ~{idx} of ~{total}", i + 1, link_tbls_len)
				, String_.Concat_lines_nl_skip_last
				( "REPLACE INTO search_word (word_id, word_text, link_count, link_score_min, link_score_max)"
				, "SELECT  sw.word_id, sw.word_text, sw.link_count, sw.link_score_min, Max(sl.link_score)"
				, "FROM    search_word sw"
				, "        JOIN <link_db>search_link sl ON sl.word_id = sw.word_id"
				, "GROUP BY sw.word_id, sw.word_text, sw.link_count, sw.link_score_min"
				, "HAVING  sw.link_score_max < Max(sl.link_score)"
				))
				;
			link_tbl.Create_idx__link_score();
			if (!link_tbl.conn.Eq(word_tbl.conn)) link_tbl.conn.Env_vacuum();	// don't vacuum if single-db
			word_tbl.Create_idx();
		}
		Srch_db_cfg_.Update__bldr__link(search_db_mgr.Tbl__cfg(), search_db_mgr.Cfg(), link_score_max);

		if (delete_plink_db) {
			Xob_db_file plink_db = Xob_db_file.New__page_link(wiki);
			plink_db.Conn().Rls_conn();
			Io_mgr.Instance.DeleteFil(plink_db.Url());
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__page_rank_enabled_))		page_rank_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__delete_plink_db_))			delete_plink_db = m.ReadYn("v");
		else													return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk__page_rank_enabled_ = "page_rank_enabled_", Invk__delete_plink_db_ = "delete_plink_db_";

	public static final String BLDR_CMD_KEY = "search.link__link_score";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__link__link_score(null, null);
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__link__link_score(bldr, wiki);}
}
