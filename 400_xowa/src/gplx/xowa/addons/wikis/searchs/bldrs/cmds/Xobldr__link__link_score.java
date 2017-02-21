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
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.addons.bldrs.utils_rankings.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments.*;
import gplx.xowa.addons.wikis.searchs.dbs.*;
public class Xobldr__link__link_score extends Xob_cmd__base {
	private int score_multiplier = 100000000;
	private boolean page_rank_enabled = false;
	private boolean delete_plink_db = false;
	private final    Adjustment_cmd score_adjustment_mgr;
	public Xobldr__link__link_score(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);
		this.score_adjustment_mgr = new Adjustment_cmd(wiki);
	}
	public Xobldr__link__link_score Page_rank_enabled_(boolean v) {this.page_rank_enabled = v; return this;}
	public Xobldr__link__link_score Delete_plink_db_(boolean v) {this.delete_plink_db = v; return this;}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		Db_conn plink_conn = Db_conn_bldr.Instance.Get_or_autocreate(false, wiki.Fsys_mgr().Root_dir().GenSubFil(Xob_db_file.Name__page_link));
		String page_rank_tbl = Xobldr__page__page_score.Pagerank__tbl_name;
		Gfo_usr_dlg_.Instance.Prog_none("", "", "search.page.score:adding fields to page_rank_temp");
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_len_score"	, Dbmeta_fld_tid.Itm__int, 0);
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_rank_score"	, Dbmeta_fld_tid.Itm__double, 0);
		plink_conn.Meta_fld_assert(page_rank_tbl, "page_score"		, Dbmeta_fld_tid.Itm__int, 0);
		int link_score_max = Srch_search_addon.Score_max;

		// percentize page_len_score to 0 : 100,000,000; NOTE: 100,000,000 so that no individual score should have 2+ page; i.e.: score_range > page_count
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
			Gfo_usr_dlg_.Instance.Prog_none("", "", "search.page.score:calculating page_rank range");
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

			// adjust pages; NOTE: must happen after percentize b/c adjustment is based on percentized 0 : 100,000, not the raw page_rank (0.5 : 146.5)
			score_adjustment_mgr.Exec();
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
		Db_conn page_conn = page_tbl.Conn();
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
		if (!page_tbl.Conn().Eq(word_tbl.conn)) page_tbl.Conn().Env_vacuum();	// don't vacuum if single-db
		// Srch_db_mgr.Optimize_unsafe_(word_tbl.conn, Bool_.Y);
		word_tbl.conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("link_score_mnx", Dbmeta_fld_itm.new_int("word_id"), Dbmeta_fld_itm.new_int("mnx_val")));
		int link_tbls_len = search_db_mgr.Tbl__link__len();
		for (int i = 0; i < link_tbls_len; ++i) {
			Srch_link_tbl link_tbl = search_db_mgr.Tbl__link__get_at(i);
			// update search_link.link_score
			link_tbl.conn.Meta_fld_assert(link_tbl.Tbl_name(), Srch_link_tbl.Fld_link_score, Dbmeta_fld_tid.Itm__int, 0);
			new Db_attach_mgr(link_tbl.conn
				, new Db_attach_itm("page_db", page_conn)
				).Exec_sql_w_msg
				( Bry_fmt.Make_str("updating search_link.link_score: ~{idx} of ~{total}", i + 1, link_tbls_len)
				, String_.Concat_lines_nl_skip_last
				( "UPDATE  search_link"
				, "SET     link_score = Coalesce((SELECT page_score FROM <page_db>page p WHERE p.page_id = search_link.page_id), 0)"
				));
			Calc_min_max(Bool_.Y, word_tbl, link_tbl, i, link_tbls_len);
			Calc_min_max(Bool_.N, word_tbl, link_tbl, i, link_tbls_len);
			link_tbl.Create_idx__link_score();
			if (!link_tbl.conn.Eq(word_tbl.conn)) link_tbl.conn.Env_vacuum();	// don't vacuum if single-db
			word_tbl.Create_idx();
		}
		word_tbl.conn.Meta_tbl_delete("link_score_mnx");
		Srch_db_cfg_.Update__bldr__link(search_db_mgr.Tbl__cfg(), search_db_mgr.Cfg(), link_score_max);
		// Srch_db_mgr.Optimize_unsafe_(word_tbl.conn, Bool_.N);

		if (delete_plink_db) {
			Xob_db_file plink_db = Xob_db_file.New__page_link(wiki);
			plink_db.Conn().Rls_conn();
			Io_mgr.Instance.DeleteFil(plink_db.Url());
		}
	}
	private void Calc_min_max(boolean min, Srch_word_tbl word_tbl, Srch_link_tbl link_tbl, int i, int link_tbls_len) {
		word_tbl.conn.Meta_idx_delete("link_score_mnx__main");
		word_tbl.conn.Exec_sql_concat("DELETE FROM link_score_mnx");
		String prc_name = "Max";
		String fld_name = "link_score_max";
		if (min) {
			prc_name = "Min";
			fld_name = "link_score_min";
		}
		new Db_attach_mgr(word_tbl.conn, new Db_attach_itm("link_db", link_tbl.conn))
			.Exec_sql_w_msg
			( Bry_fmt.Make_str("creating temp.link_score_max: ~{idx} of ~{total}", i + 1, link_tbls_len)
			, String_.Concat_lines_nl_skip_last
			( "INSERT INTO link_score_mnx (word_id, mnx_val)"
			, "SELECT  sw.word_id, " + prc_name + "(sl.link_score)"
			, "FROM    search_word sw"
			, "        JOIN <link_db>search_link sl ON sl.word_id = sw.word_id"
			, "GROUP BY sw.word_id"
			))
			;
		word_tbl.conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_name("link_score_mnx", "main", "word_id", "mnx_val"));
		word_tbl.conn.Exec_sql_concat_w_msg
			( Bry_fmt.Make_str("creating temp.link_score_max: ~{idx} of ~{total}", i + 1, link_tbls_len)
			, "UPDATE  search_word"
			, "SET     " + fld_name + " = "
			, "                       Coalesce(("
			, "                         SELECT  mnx_val"
			, "                         FROM    link_score_mnx lsm"
			, "                         WHERE   lsm.word_id = search_word.word_id"
			, "                         AND     lsm.mnx_val " + (min ? "<" : ">") + " search_word." + fld_name
			, "                       ), " + fld_name + ")"
			);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__page_rank_enabled_))		page_rank_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__delete_plink_db_))			delete_plink_db = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__delete_plink_db_))			delete_plink_db = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__score_adjustment_mgr))		return score_adjustment_mgr;
		else													return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__page_rank_enabled_ = "page_rank_enabled_", Invk__delete_plink_db_ = "delete_plink_db_"
	, Invk__score_adjustment_mgr = "score_adjustment_mgr"
	;

	public static final String BLDR_CMD_KEY = "search.link__link_score";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__link__link_score(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__link__link_score(bldr, wiki);}
}
