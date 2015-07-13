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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
public class Xop_log_basic_tbl {
	private Db_stmt stmt_insert;
	public Xop_log_basic_tbl(Db_conn conn){this.conn = conn; this.Create_table();} 
	public Db_conn Conn() {return conn;} private Db_conn conn; 
	private void Create_table()				{Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);}
	public void Delete()					{conn.Exec_qry(Db_qry_delete.new_all_(Tbl_name));}
	public void Insert(int log_tid, String log_msg, int log_time, int page_id, String page_ttl, int args_len, String args_str, int src_len, String src_str) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Fld_log_tid, Fld_log_msg, Fld_log_time, Fld_page_id, Fld_page_ttl, Fld_args_len, Fld_args_str, Fld_src_len, Fld_src_str);
		stmt_insert.Clear()
		.Val_int(log_tid)
		.Val_str(log_msg)
		.Val_int(log_time)
		.Val_int(page_id)
		.Val_str(page_ttl)
		.Val_int(args_len)
		.Val_str(args_str)
		.Val_int(src_len)
		.Val_str(src_str)
		.Exec_insert();
	}
	public void Rls() {
		stmt_insert.Rls();
	}
	public static final String Tbl_name = "log_basic_temp"
	, Fld_log_tid = "log_tid", Fld_log_msg = "log_msg", Fld_log_time = "log_time"
	, Fld_page_id = "page_id", Fld_page_ttl = "page_ttl"
	, Fld_args_len = "args_len", Fld_args_str = "args_str"
	, Fld_src_len = "src_len", Fld_src_str = "src_str"
	;
	private static final String Tbl_sql = String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS log_basic_temp"
		,	"( log_id                   integer             NOT NULL    PRIMARY KEY AUTOINCREMENT"
		,	", log_tid                  integer             NOT NULL"
		,	", log_msg                  varchar(255)        NOT NULL"
		,	", log_time                 integer             NOT NULL"
		,	", page_id                  integer             NOT NULL"
		,	", page_ttl                 varchar(255)        NOT NULL"
		,	", args_len                 integer             NOT NULL"
		,	", args_str                 varchar(4096)       NOT NULL"
		,	", src_len                  integer             NOT NULL"
		,	", src_str                  varchar(4096)       NOT NULL"
		,	");"
		);
}
