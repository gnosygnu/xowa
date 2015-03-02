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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.ios.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xob_search_sql_wkr extends Xob_search_base implements Io_make_cmd {
	public Xob_search_sql_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);} private Xodb_mgr_sql db_mgr = null;
	@Override public String Wkr_key() {return KEY;} public static final String KEY = "import.sql.search_title.wkr";
	@Override public gplx.ios.Io_make_cmd Make_cmd_site() {return this;}
	public Io_sort_cmd Make_dir_(Io_url v) {return this;}	// noop	
	public void Sort_bgn() {
		db_mgr = wiki.Db_mgr_as_sql();
		boolean created = false;
		Xowd_db_file search_db = db_mgr.Core_data_mgr().Dbs__get_by_tid_1st(Xowd_db_file_.Tid_search);
		if (search_db == null) {
			search_db = db_mgr.Core_data_mgr().Dbs__add_new(Xowd_db_file_.Tid_search);
			created = true;
		}
		conn = search_db.Conn();
		if (created) {
			Xodb_search_title_word_tbl.Create_table(conn);
			Xodb_search_title_page_tbl.Create_table(conn);
		}
		conn.Txn_mgr().Txn_bgn_if_none();
		stmt_word = Xodb_search_title_word_tbl.Insert_stmt(conn);
		stmt_page = Xodb_search_title_page_tbl.Insert_stmt(conn);
	}	private Db_conn conn; private int search_id = 0; private Db_stmt stmt_word, stmt_page;
	public byte Line_dlm() {return line_dlm;} public Xob_search_sql_wkr Line_dlm_(byte v) {line_dlm = v; return this;} private byte line_dlm = Byte_ascii.Nil;
	private byte[] prv_word = Bry_.Empty;
	public void Sort_do(Io_line_rdr rdr) {
		if (line_dlm == Byte_ascii.Nil) line_dlm = rdr.Line_dlm();
		byte[] bry = rdr.Bfr();
		byte[] cur_word = Bry_.Mid(bry, rdr.Key_pos_bgn(), rdr.Key_pos_end());
		if (!Bry_.Eq(cur_word, prv_word)) {
			Xodb_search_title_word_tbl.Insert(stmt_word, ++search_id, cur_word);
			prv_word = cur_word;
		}
		Xodb_search_title_page_tbl.Insert(stmt_page, search_id, Base85_utl.XtoIntByAry(bry, rdr.Key_pos_end() + 1, rdr.Key_pos_end() +  5)); // -1: ignore rdr_dlm
	}
	public void Sort_end() {
		conn.Txn_mgr().Txn_end_all();
		Xodb_tbl_search_title_temp.Cleanup(usr_dlg, conn);
	}
}
class Xodb_tbl_search_title_temp {
	public Xodb_tbl_search_title_temp Create_table(Db_conn p) {Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql); return this;}
	public void Make_data(Gfo_usr_dlg usr_dlg, Db_conn p) {
		Xodb_search_title_word_tbl.Create_table(p);
		Xodb_search_title_page_tbl.Create_table(p);
		p.Txn_mgr().Txn_end_all_bgn_if_none();
		Sqlite_engine_.Idx_create(usr_dlg, p, "search_title_temp", Idx_main);
		p.Txn_mgr().Txn_end_all_bgn_if_none();
		p.Exec_sql(Sql_create_word);
		p.Txn_mgr().Txn_end_all_bgn_if_none();
		p.Exec_sql(Sql_create_link);
		p.Txn_mgr().Txn_end_all_bgn_if_none();
		Cleanup(usr_dlg, p);
		p.Txn_mgr().Txn_end_all_bgn_if_none();
		p.Txn_mgr().Txn_end();	// must end all transactions before vacuum
		p.Exec_sql("VACUUM;");
	}
	public static void Cleanup(Gfo_usr_dlg usr_dlg, Db_conn p) {
		p.Exec_sql("DROP TABLE IF EXISTS search_title_temp;");
		try {
			Xodb_search_title_word_tbl.Create_index(usr_dlg, p);
		} catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to create unique index for search title word: err=~{0}", Err_.Message_gplx_brief(e));
		}
		try {
			Xodb_search_title_page_tbl.Create_index_unique(usr_dlg, p);
		} catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to create unique index: err=~{0}", Err_.Message_gplx_brief(e));
			Xodb_search_title_page_tbl.Create_index_non_unique(usr_dlg, p);
		}
	}
	public Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_stt_page_id, Fld_stt_word);}
	public void Insert(Db_stmt stmt, int page_id, byte[] word) {
		stmt.Clear()
		.Val_int(page_id)
		.Val_bry_as_str(word)
		.Exec_insert();
	}	
	public static final String Tbl_name = "search_title_temp", Fld_stt_page_id = "stt_page_id", Fld_stt_word = "stt_word";
	public static final Db_idx_itm Idx_main = Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS search_title_temp__main       ON search_title_temp (stt_word, stt_page_id);");
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS search_title_temp"
	,	"( stt_id              integer             NOT NULL    PRIMARY KEY AUTOINCREMENT"
	,	", stt_word            varchar(255)        NOT NULL"
	,	", stt_page_id         integer             NOT NULL"
	,	");"
	);
	private static final String Sql_create_word = String_.Concat_lines_nl
	(	"INSERT INTO search_title_word (stw_word_id, stw_word)"
	,	"SELECT  stt_id"
	,	",       stt_word"
	,	"FROM    search_title_temp"
	,	"GROUP BY "
	,	"        stt_word"
	,	";"
	);
	private static final String Sql_create_link = String_.Concat_lines_nl
	(	"INSERT INTO search_title_page (stp_word_id, stp_page_id)"
	,	"SELECT  stw.stw_word_id"
	,	",       stt.stt_page_id"
	,	"FROM    search_title_temp stt"
	,	"        JOIN search_title_word stw ON stt.stt_word = stw.stw_word"
	,	";"
	);
}
