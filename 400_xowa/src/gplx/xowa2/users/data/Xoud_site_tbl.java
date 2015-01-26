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
package gplx.xowa2.users.data; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.users.*;
import gplx.dbs.*;
public class Xoud_site_tbl {
	public Db_conn Conn() {return conn;} public Xoud_site_tbl Conn_(Db_conn v) {this.Rls_all(); conn = v; return this;} private Db_conn conn;
	@gplx.Virtual public void Insert(int priority, String domain, String name, String path, String xtn) {
		String[] flds = new String[] {Fld_site_priority, Fld_site_domain, Fld_site_name, Fld_site_path, Fld_site_xtn};
		Db_stmt stmt = Db_stmt_.new_insert_(conn, Tbl_name, flds);
		try {stmt.Val_int(priority).Val_str(domain).Val_str(name).Val_str(path).Val_str(xtn).Exec_insert();}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Insert");}
	}
	@gplx.Virtual public void Update(int id, int priority, String domain, String name, String path, String xtn) {
		String[] flds = new String[] {Fld_site_priority, Fld_site_domain, Fld_site_name, Fld_site_path, Fld_site_xtn};
		Db_stmt stmt = Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_site_id), flds);
		try {stmt.Val_int(priority).Val_str(domain).Val_str(name).Val_str(path).Val_str(xtn).Val_int(id).Exec_insert();}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Update");}
	}
	@gplx.Virtual public void Delete(int id) {
		Db_stmt stmt = Db_stmt_.new_delete_(conn, Tbl_name, Fld_site_id);
		try {stmt.Val_int(id).Exec_delete();}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Delete");}
	}
	@gplx.Virtual public Xoud_site_row[] Select_all() {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, null, Flds__all));
		try {
			Db_rdr rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_site_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
			return (Xoud_site_row[])rv.Xto_ary_and_clear(Xoud_site_row.class);
		}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Select_all");}
	}
	public Xoud_site_row[] Select_by_domain(String domain) {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Null;
		try {
			stmt = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_site_domain), Flds__all));
			rdr = stmt.Val_str(domain).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_site_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
			return (Xoud_site_row[])rv.Xto_ary_and_clear(Xoud_site_row.class);
		}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Select_by_domain");}
		finally {rdr.Rls(); stmt.Rls();}
	}
	private Xoud_site_row Make_row(Db_rdr rdr) {
		return new Xoud_site_row
		( rdr.Read_int(0)
		, rdr.Read_int(1)
		, rdr.Read_str(2)
		, rdr.Read_str(3)
		, rdr.Read_str(4)
		, rdr.Read_str(5)
		);
	}
	public void Rls_all() {conn = null;}
	public static final String Tbl_name = "user_site_regy", Fld_site_id = "site_id", Fld_site_priority = "site_priority", Fld_site_domain = "site_domain"
	, Fld_site_name = "site_name", Fld_site_path = "site_path", Fld_site_xtn = "site_xtn";
	private static final String[] Flds__all = new String[] {Fld_site_id, Fld_site_priority, Fld_site_domain, Fld_site_name, Fld_site_path, Fld_site_xtn};
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE user_site_regy"
	, "( site_id                integer         NOT NULL PRIMARY KEY AUTOINCREMENT"
	, ", site_priority          integer         NOT NULL                    -- EX: 0=default; 1+ is order if 0 is unavailable"
	, ", site_domain            nvarchar(255)   NOT NULL                    -- EX: en.wikipedia.org; NOTE: no protocol (https:)"
	, ", site_name              nvarchar(255)   NOT NULL                    -- EX: English Wikipedia"
	, ", site_path              nvarchar(255)   NOT NULL                    -- EX: ~{xowa_root}/wiki/en.wikipedia.org/"
	, ", site_xtn               text            NOT NULL"
	, ");"
	);
}
