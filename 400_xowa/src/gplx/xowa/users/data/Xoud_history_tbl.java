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
import gplx.dbs.*; import gplx.dbs.schemas.*; import gplx.dbs.schemas.updates.*;
public class Xoud_history_tbl {
	private Db_stmt stmt_select_by_page, stmt_select_by_top, stmt_insert, stmt_update, stmt_delete;
	public Db_conn Conn() {return conn;} public Xoud_history_tbl Conn_(Db_conn v) {this.Rls_all(); conn = v; return this;} private Db_conn conn;
	@gplx.Virtual public void Insert(String wiki, String page, String qarg, DateAdp time, int count) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		try {
			stmt_insert.Clear().Val_str(wiki).Val_str(page).Val_str(qarg).Val_str(time.XtoStr_fmt_iso_8561()).Val_int(count).Exec_insert();
		}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Update(String wiki, String page, String qarg, DateAdp time, int count) {
		if (stmt_update == null) stmt_update = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_history_wiki, Fld_history_page, Fld_history_qarg), String_.Ary(Fld_history_time, Fld_history_count));
		try {
			stmt_update.Clear().Val_str(time.XtoStr_fmt_iso_8561()).Val_int(count).Val_str(wiki).Val_str(page).Val_str(qarg).Exec_update();
		}
		catch (Exception exc) {stmt_update = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Delete(String wiki, String page, String qarg) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(conn, Tbl_name, Fld_history_wiki, Fld_history_page, Fld_history_qarg);
		try {stmt_delete.Clear().Val_str(wiki).Val_str(page).Val_str(qarg).Exec_delete();}
		catch (Exception exc) {stmt_delete = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public Xoud_history_row Select_by_page(String wiki, String page, String qarg) {
		if (stmt_select_by_page == null) stmt_select_by_page = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_history_wiki, Fld_history_page, Fld_history_qarg), Flds__all));
		try {
			Db_rdr rdr = stmt_select_by_page.Clear().Val_str(wiki).Val_str(page).Val_str(qarg).Exec_select_as_rdr();
			Xoud_history_row rv = null;
			if (rdr.Move_next())
				rv = Make_row(rdr);
			rdr.Rls();
			return rv;
		}
		catch (Exception exc) {stmt_select_by_page = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Select_by_top(ListAdp rv, int count) {
		Db_qry__select_in_tbl qry = new Db_qry__select_in_tbl(Tbl_name, Flds__all, null, null, null, Fld_history_time + " DESC", " LIMIT " + Int_.Xto_str(count));
		stmt_select_by_top = Db_stmt_.new_select_as_rdr(conn, qry);
		try {
			Db_rdr rdr = stmt_select_by_top.Clear().Exec_select_as_rdr();
			rv.Clear();				
			while (rdr.Move_next()) {
				Xoud_history_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
		}
		catch (Exception exc) {stmt_select_by_top = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	private Xoud_history_row Make_row(Db_rdr rdr) {
		return new Xoud_history_row
		( rdr.Read_str(0)
		, rdr.Read_str(1)
		, rdr.Read_str(2)
		, rdr.Read_date_by_str(3)
		, rdr.Read_int(4)
		);
	}
	public void Rls_all() {
		if (stmt_select_by_page != null) {stmt_select_by_page.Rls(); stmt_select_by_page = null;}
		if (stmt_select_by_top != null) {stmt_select_by_top.Rls(); stmt_select_by_top = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		conn = null;
	}
	public static final String Tbl_name = "user_history", Fld_history_wiki = "history_wiki", Fld_history_page = "history_page", Fld_history_qarg = "history_qarg"
	, Fld_history_time = "history_time", Fld_history_count = "history_count"
	;
	private static final String[] Flds__all = new String[] {Fld_history_wiki, Fld_history_page, Fld_history_qarg, Fld_history_time, Fld_history_count};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE user_history"
	, "( history_wiki                    nvarchar(255)   NOT NULL"
	, ", history_page                    nvarchar(255)   NOT NULL"
	, ", history_qarg                    nvarchar(255)   NOT NULL"
	, ", history_time                    nvarchar(20)    NOT NULL"
	, ", history_count                   integer         NOT NULL"
	, ");"
	);
	public static final Db_idx_itm Idx_core = Db_idx_itm.sql_("CREATE UNIQUE INDEX user_history_core ON user_history (history_wiki, history_page, history_qarg);");
}
