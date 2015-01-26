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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xoud_bmk_tbl {
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Db_conn Conn() {return conn;} public Xoud_bmk_tbl Conn_(Db_conn v) {this.Rls_all(); conn = v; return this;} private Db_conn conn;
	@gplx.Virtual public void Insert(int sort, String wiki, String page, String qarg, String wtxt, DateAdp time, int count) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		try {
			stmt_insert.Clear().Val_int(sort).Val_str(wiki).Val_str(page).Val_str(qarg).Val_str(wtxt).Val_str(time.XtoStr_fmt_iso_8561()).Val_int(count).Exec_insert();
		}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Delete(int id) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(conn, Tbl_name, Fld_bmk_id);
		try {stmt_delete.Clear().Val_int(id).Exec_delete();}
		catch (Exception exc) {stmt_delete = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Select_all(ListAdp rv) {
		if (stmt_select == null) stmt_select = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, null, Flds__all));
		try {
			Db_rdr rdr = stmt_select.Clear().Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_bmk_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
		}
		catch (Exception exc) {stmt_select = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	private Xoud_bmk_row Make_row(Db_rdr rdr) {
		return new Xoud_bmk_row
		( rdr.Read_int(0)
		, rdr.Read_int(1)
		, rdr.Read_str(2)
		, rdr.Read_str(3)
		, rdr.Read_str(4)
		, rdr.Read_str(5)
		, rdr.Read_date_by_str(6)
		, rdr.Read_int(7)
		);
	}
	public void Rls_all() {
		if (stmt_select != null) {stmt_select.Rls(); stmt_select = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		conn = null;
	}
	public static final String Tbl_name = "cfg_bmk", Fld_bmk_id = "bmk_id", Fld_bmk_count = "bmk_count", Fld_bmk_sort = "bmk_sort"
	, Fld_bmk_time = "bmk_time", Fld_bmk_wiki = "bmk_wiki", Fld_bmk_page = "bmk_page", Fld_bmk_qarg = "bmk_qarg", Fld_bmk_wtxt = "bmk_wtxt";
	public static final String[] Flds__all = new String[] {Fld_bmk_id, Fld_bmk_sort, Fld_bmk_wiki, Fld_bmk_page, Fld_bmk_qarg, Fld_bmk_wtxt, Fld_bmk_time, Fld_bmk_count};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE cfg_bmk"
	, "( bmk_id					integer			NOT NULL	PRIMARY KEY   AUTOINCREMENT"
	, ", bmk_sort				integer			NOT NULL"
	, ", bmk_wiki				nvarchar(255)	NOT NULL"
	, ", bmk_page				nvarchar(255)	NOT NULL"
	, ", bmk_qarg				nvarchar(255)	NOT NULL"
	, ", bmk_wtxt				nvarchar(255)	NOT NULL"
	, ", bmk_time				nvarchar(20)	NOT NULL"
	, ", bmk_count				integer			NOT NULL"
	, ");"
	);
}
