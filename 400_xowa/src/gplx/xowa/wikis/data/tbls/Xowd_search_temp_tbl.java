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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
public class Xowd_search_temp_tbl {
	private final String tbl_name = "search_temp"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_page_id, fld_word_text;
	private final Db_conn conn; private Db_stmt stmt_insert;
	private final String sql_create_word, sql_create_link;
	public Xowd_search_temp_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		if (schema_is_1)	{sql_create_word = Sql_create_word_v1; sql_create_link = Sql_create_link_v1;}
		else				{sql_create_word = Sql_create_word_v2; sql_create_link = Sql_create_link_v2;}
		flds.Add_int_pkey_autonum("word_id");
		fld_page_id			= flds.Add_int("page_id");
		fld_word_text		= flds.Add_str("word_text", 255);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn(); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int page_id, byte[] word) {
		stmt_insert.Clear()
			.Val_int(fld_page_id, page_id).Val_bry_as_str(fld_word_text, word)
			.Exec_insert();
	}	
	public void Make_data(Gfo_usr_dlg usr_dlg, Xowd_search_link_tbl search_link_tbl, Xowd_search_word_tbl search_word_tbl) {
		conn.Ddl_create_idx(usr_dlg, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_word_text, fld_page_id));
		conn.Exec_sql_plog_txn("search_temp.create_word", sql_create_word);
		conn.Exec_sql_plog_txn("search_temp.create_link", sql_create_link);
		Create_idx(usr_dlg, search_link_tbl, search_word_tbl);
		conn.Env_vacuum();
	}
	public void Create_idx(Gfo_usr_dlg usr_dlg, Xowd_search_link_tbl search_link_tbl, Xowd_search_word_tbl search_word_tbl) {
		conn.Ddl_delete_tbl(tbl_name);
		try {search_word_tbl.Create_idx();} 
		catch (Exception e) {usr_dlg.Warn_many("", "", "bldr.search_word.unique_search_failed: err=~{0}", Err_.Message_gplx_brief(e));}
		try {search_link_tbl.Create_idx_unique();}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "bldr.search_page.unique_search_failed: err=~{0}", Err_.Message_gplx_brief(e));
			search_link_tbl.Create_idx_normal();;
		}
	}
	private static final String Sql_create_word_v1 = String_.Concat_lines_nl
	(	"INSERT INTO search_title_word (stw_word_id, stw_word)"
	,	"SELECT  word_id"
	,	",       word_text"
	,	"FROM    search_temp"
	,	"GROUP BY "
	,	"        word_text"
	,	";"
	);
	private static final String Sql_create_link_v1 = String_.Concat_lines_nl
	(	"INSERT INTO search_title_page (stp_word_id, stp_page_id)"
	,	"SELECT  w.stw_word_id"
	,	",       t.page_id"
	,	"FROM    search_temp t"
	,	"        JOIN search_title_word w ON t.word_text = w.stw_word"
	,	";"
	);
	private static final String Sql_create_word_v2 = String_.Concat_lines_nl
	(	"INSERT INTO search_word (word_id, word_text)"
	,	"SELECT  word_id"
	,	",       word_text"
	,	"FROM    search_temp"
	,	"GROUP BY "
	,	"        word_text"
	,	";"
	);
	private static final String Sql_create_link_v2 = String_.Concat_lines_nl
	(	"INSERT INTO search_link (word_id, page_id)"
	,	"SELECT  w.word_id"
	,	",       t.page_id"
	,	"FROM    search_temp t"
	,	"        JOIN search_word w ON t.word_text = w.word_text"
	,	";"
	);
}
