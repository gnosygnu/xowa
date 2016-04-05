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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_page_delete_cmd extends Xob_cmd_base {
	private final    Xow_wiki wiki;
	public Xob_page_delete_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.wiki = wiki;}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_text_delete_page;}
	@Override public void Cmd_run() {
		wiki.Init_by_wiki();
		Xowd_db_file core_db = wiki.Data__core_mgr().Db__core();
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
		try {
			Xowd_db_file[] db_files = core_db.Tbl__db().Select_all(wiki.Data__core_mgr().Props(), wiki.Fsys_mgr().Root_dir());
			int len = db_files.length;
			for (int i = 0; i < len; ++i) {
				boolean db_file_is_text = Bool_.N, db_file_is_cat = Bool_.N, db_file_is_search = Bool_.N;
				Xowd_db_file db_file = db_files[i];
				switch (db_file.Tid()) {
					case Xowd_db_file_.Tid_core: case Xowd_db_file_.Tid_wiki_solo: case Xowd_db_file_.Tid_text_solo:
						if (wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()) continue;	// if mode is lot, then "core" db does not have text, cat, search; skip; DATE:2016-01-31
														db_file_is_text = db_file_is_cat = db_file_is_search = Bool_.Y; break;
					case Xowd_db_file_.Tid_text:		db_file_is_text = Bool_.Y; break;
					case Xowd_db_file_.Tid_cat:			db_file_is_cat = Bool_.Y; break;
					case Xowd_db_file_.Tid_search_core:	db_file_is_search = Bool_.Y; break;
				}
				int db_id = db_file.Id();
				if	(db_file_is_text)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting text: "  + db_id, "DELETE FROM <data_db>text WHERE page_id IN (SELECT page_id FROM page_filter WHERE page_text_db_id = {0});");
				if	(db_file_is_cat)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting cat: "   + db_id, "DELETE FROM <data_db>cat_link WHERE cl_from IN (SELECT page_id FROM page_filter);");
				if	(db_file_is_search)	Run_sql(core_db_conn, db_file.Url(), db_id, "deleting search:" + db_id, "DELETE FROM <data_db>search_link WHERE page_id IN (SELECT page_id FROM page_filter);");
				if (db_file_is_text || db_file_is_cat || db_file_is_search)
					db_file.Conn().Env_vacuum();
			}
		} catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "fatal error during page deletion: err=~{0}", Err_.Message_gplx_log(e));}
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
