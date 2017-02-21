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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_page_delete_cmd extends Xob_cmd_base {
	private final    Xow_wiki wiki;
	public Xob_page_delete_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.wiki = wiki;}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_text_delete_page;}
	@Override public void Cmd_run() {
		wiki.Init_by_wiki();
		Xow_db_file core_db = wiki.Data__core_mgr().Db__core();
		Db_conn core_db_conn = core_db.Conn();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
		usr_dlg.Plog_many("", "", "creating page_filter");
		if (!core_db_conn.Meta_tbl_exists("page_filter")) {
			core_db_conn.Meta_tbl_create
			( Dbmeta_tbl_itm.New("page_filter", new Dbmeta_fld_itm[]
			{	Dbmeta_fld_itm.new_int("page_id").Primary_y_()
			,	Dbmeta_fld_itm.new_int("page_text_db_id")
			}
			,   Dbmeta_idx_itm.new_normal_by_tbl("page_filter", "db_id__page", "page_text_db_id", "page_id")
			,   Dbmeta_idx_itm.new_normal_by_tbl("page_filter", "page_id", "page_id")
			));
		}
		core_db_conn.Exec_sql_plog_ntx("finding missing redirects", String_.Concat_lines_nl_skip_last
		( "INSERT INTO page_filter (page_id, page_text_db_id)"
		, "SELECT  ptr.page_id, ptr.page_text_db_id"
		, "FROM    page ptr"
		, "        LEFT JOIN page orig ON ptr.page_redirect_id = orig.page_id"
		, "WHERE   ptr.page_is_redirect = 1"
		, "AND     orig.page_id IS NULL"
		, "UNION"
		, "SELECT  ptr.page_id, ptr.page_text_db_id"
		, "FROM    page ptr"
		, "WHERE   ptr.page_is_redirect = 1"
		, "AND     ptr.page_redirect_id = -1"
		, ";"
		));
		
		String db_file_cur = "";
		try {
			Xow_db_file[] db_file_ary = core_db.Tbl__db().Select_all(wiki.Data__core_mgr().Props(), wiki.Fsys_mgr().Root_dir());
			int len = db_file_ary.length;
			for (int i = 0; i < len; ++i) {
				boolean db_file_is_text = Bool_.N, db_file_is_cat = Bool_.N, db_file_is_search = Bool_.N;
				Xow_db_file db_file = db_file_ary[i];
				switch (db_file.Tid()) {
					case Xow_db_file_.Tid__core: case Xow_db_file_.Tid__wiki_solo: case Xow_db_file_.Tid__text_solo:
						// if mode is lot, then "core" db does not have cat, search; skip; DATE:2016-01-31
						if (wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()) continue;
						db_file_is_cat = db_file_is_search = Bool_.Y;	// do not set db_file_is_text to true; DATE:2016-10-18
						break;
					case Xow_db_file_.Tid__text:		db_file_is_text = Bool_.Y; break;
					case Xow_db_file_.Tid__cat:			db_file_is_cat = Bool_.Y; break;
					case Xow_db_file_.Tid__search_link:	db_file_is_search = Bool_.Y; break;		// changed from search_data to search_link; DATE:2016-10-19
				}
				db_file_cur = db_file.Url().Raw();
				int db_id = db_file.Id();
				if	(db_file_is_text)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting text: "  + db_id, "DELETE FROM <data_db>text WHERE page_id IN (SELECT page_id FROM page_filter WHERE page_text_db_id = {0});");
				if	(db_file_is_cat)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting cat: "   + db_id, "DELETE FROM <data_db>cat_link WHERE cl_from IN (SELECT page_id FROM page_filter);");
				if	(db_file_is_search)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting search:" + db_id, "DELETE FROM <data_db>search_link WHERE page_id IN (SELECT page_id FROM page_filter);");
				if	(db_file_is_text || db_file_is_cat || db_file_is_search)
					db_file.Conn().Env_vacuum();
			}
		} catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "fatal error during page deletion: cur=~{0} err=~{1}", db_file_cur, Err_.Message_gplx_log(e));}
		core_db_conn.Exec_sql_plog_ntx("deleting from table: page", "DELETE FROM page WHERE page_id IN (SELECT page_id FROM page_filter);");
		// core_db_conn.Meta_tbl_delete("page_filter");
		core_db_conn.Env_vacuum();
		usr_dlg.Plog_many("", "", "");
	}
	private void Run_sql(Db_conn core_db_conn, Io_url db_url, int db_id, String prog_msg, String sql) {
		new Db_attach_mgr(core_db_conn, new Db_attach_itm("data_db", db_url))
			.Exec_sql_w_msg(prog_msg			, sql, db_id);
	}
}
