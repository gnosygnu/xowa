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
package gplx.xowa.hdumps.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.dbs.*;
public class Xodb_wiki_page_html_tbl {
	private Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public void Init_by_wiki(Xow_wiki wiki) {
		this.zip_mgr = wiki.App().Zip_mgr();
	}
	public byte Zip_tid() {return zip_tid;} public void Zip_tid_(byte v) {zip_tid = v;} private byte zip_tid = Io_stream_.Tid_gzip;
	public Db_conn Conn() {return conn;} public Xodb_wiki_page_html_tbl Conn_(Db_conn v) {this.Rls_all(); conn = v; return this;} private Db_conn conn;
	@gplx.Virtual public int Insert(int page_id, int tid, byte[] data) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		try {
			data = zip_mgr.Zip(zip_tid, data);
			stmt_insert.Clear().Val_int(page_id).Val_int(tid).Val_bry(data).Exec_insert();
			return data.length;
		}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(conn, Tbl_name, Fld_page_id);
		try {stmt_delete.Clear().Val_int(page_id).Exec_delete();}
		catch (Exception exc) {stmt_delete = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Select_by_page(ListAdp rv, int page_id) {
		if (stmt_select == null) stmt_select = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_page_id), Flds__all));
		try {
			Db_rdr rdr = stmt_select.Clear().Val_int(page_id).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xodb_wiki_page_html_row row = new Xodb_wiki_page_html_row
				( rdr.Read_int(0)
				, rdr.Read_int(1)
				, zip_mgr.Unzip(zip_tid, rdr.Read_bry(2))
				);
				rv.Add(row);
			}
			rdr.Rls();
		}
		catch (Exception exc) {stmt_select = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	public void Rls_all() {
		if (stmt_select != null) {stmt_select.Rls(); stmt_select = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		conn = null;
	}
	public static final String Tbl_name = "wiki_page_html", Fld_page_id = "page_id", Fld_html_tid = "html_tid", Fld_html_data = "html_data";
	private static final String[] Flds__all = new String[] {Fld_page_id, Fld_html_tid, Fld_html_data};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS wiki_page_html"
	, "( page_id                integer             NOT NULL"
	, ", html_tid               integer             NOT NULL"
	, ", html_data              blob                NOT NULL"
	, ");"
	);
	public static final Db_idx_itm
	  Idx_core = Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS wiki_page_html__core ON wiki_page_html (page_id, html_tid);")
	;
}
