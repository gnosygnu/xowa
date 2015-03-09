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
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.schemas.*; import gplx.dbs.schemas.updates.*;
public class Xoud_regy_tbl {
	public static final String Tbl_name = "user_regy", Fld_regy_grp = "regy_grp", Fld_regy_key = "regy_key", Fld_regy_val = "regy_val";
	private static final String[] Flds__all = new String[] {Fld_regy_grp, Fld_regy_key, Fld_regy_val};
	public static final Db_idx_itm Idx_core = Db_idx_itm.sql_("CREATE UNIQUE INDEX user_regy_core ON user_regy (regy_grp, regy_key);");
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE user_regy"
	, "( regy_grp           nvarchar(255)           NOT NULL           -- EX: xowa.schema.site"
	, ", regy_key           nvarchar(255)           NOT NULL           -- EX: next_id"
	, ", regy_val           nvarchar(255)           NOT NULL           -- EX: 1"
	, ");"
	);
	private Db_stmt stmt_select_grp, stmt_select_key, stmt_insert, stmt_update, stmt_delete;
	public Db_conn Conn() {return conn;}
	public Xoud_regy_tbl Conn_(Db_conn v, boolean created) {
		this.Rls_all(); conn = v;
		if (created) {
			Schema_update_cmd cmd = Schema_update_cmd_.Make_tbl_create(Xoud_regy_tbl.Tbl_name	, Xoud_regy_tbl.Tbl_sql		, Xoud_regy_tbl.Idx_core);
			cmd.Exec(null, conn);
//				conn.Exec_create_tbl_and_idx(meta);
		}
		return this;
	} private Db_conn conn;
	@gplx.Virtual public void Insert(String grp, String key, String val) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, Tbl_name, Flds__all);
		try {stmt_insert.Clear().Val_str(grp).Val_str(key).Val_str(val).Exec_insert();}
		catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Update(String grp, String key, String val) {
		if (stmt_update == null) stmt_update = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_regy_grp, Fld_regy_key), Fld_regy_val);
		try {stmt_update.Clear().Val_str(val).Val_str(grp).Val_str(key).Exec_update();}
		catch (Exception exc) {stmt_update = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Delete(String grp, String key) {
		if (stmt_delete == null) stmt_delete = Db_stmt_.new_delete_(conn, Tbl_name, Fld_regy_grp, Fld_regy_key);
		try {stmt_delete.Clear().Val_str(grp).Val_str(key).Exec_delete();}
		catch (Exception exc) {stmt_delete = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public void Select_by_grp(ListAdp rv, String grp) {
		if (stmt_select_grp == null) stmt_select_grp = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_regy_grp), Flds__all, Db_qry__select_in_tbl.Order_by_null));
		try {
			Db_rdr rdr = stmt_select_grp.Clear().Val_str(grp).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_regy_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
		}
		catch (Exception exc) {stmt_select_grp = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	@gplx.Virtual public String Select_val(String grp, String key) {
		if (stmt_select_key == null) stmt_select_key = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_regy_grp, Fld_regy_key), Flds__all, Db_qry__select_in_tbl.Order_by_null));
		try {
			Db_rdr rdr = stmt_select_key.Clear().Val_str(grp).Val_str(key).Exec_select_as_rdr();
			String rv = null;
			if (rdr.Move_next())
				rv = rdr.Read_str(2);
			rdr.Rls();
			return rv;
		}
		catch (Exception exc) {stmt_select_key = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	private Xoud_regy_row Make_row(Db_rdr rdr) {
		return new Xoud_regy_row
		( rdr.Read_str(0)
		, rdr.Read_str(1)
		, rdr.Read_str(2)
		);
	}
	public void Rls_all() {
		if (stmt_select_grp != null) {stmt_select_grp.Rls(); stmt_select_grp = null;}
		if (stmt_select_key != null) {stmt_select_key.Rls(); stmt_select_key = null;}
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_delete != null) {stmt_delete.Rls(); stmt_delete = null;}
		conn = null;
	}
}
