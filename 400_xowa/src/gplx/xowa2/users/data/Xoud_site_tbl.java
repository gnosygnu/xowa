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
	@gplx.Virtual public void Insert(int user_id, int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.New_stmt_insert(Tbl_name, Flds);
		try {
			stmt .Val_int(Fld_user_id, user_id)
				 .Val_int(Fld_site_id, site_id).Val_int(Fld_site_priority, priority).Val_str(Fld_site_domain, domain).Val_str(Fld_site_name, name).Val_str(Fld_site_path, path).Val_str(Fld_site_xtn, xtn)
				 .Exec_insert();
		}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Insert");}
	}
	@gplx.Virtual public void Update(int user_id, int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.New_stmt_update_by_meta(Tbl_name, Flds, Fld_user_id, Fld_site_id);
		try {
			stmt .Val_int(Fld_site_priority, priority).Val_str(Fld_site_domain, domain).Val_str(Fld_site_name, name).Val_str(Fld_site_path, path).Val_str(Fld_site_xtn, xtn)
				 .Crt_int(Fld_user_id, user_id).Crt_int(Fld_site_id, site_id)
				 .Exec_update();
		}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Update");}
	}
	@gplx.Virtual public void Delete(int user_id, int site_id) {
		Db_stmt stmt = conn.New_stmt_delete(Tbl_name, Fld_user_id, Fld_site_id);
		try {stmt.Crt_int(Fld_user_id, user_id).Crt_int(Fld_site_id, site_id).Exec_delete();}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Delete");}
	}
	@gplx.Virtual public Xoud_site_row[] Select_all(int user_id) {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = conn.New_stmt_select_all_where(Tbl_name, Flds, Fld_user_id);
		try {
			Db_rdr rdr = stmt.Crt_int(Fld_user_id, user_id).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_site_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
			return (Xoud_site_row[])rv.Xto_ary_and_clear(Xoud_site_row.class);
		}
		catch (Exception exc) {throw Db_stmt_.err_(exc, stmt, "Select_all");}
	}
	public Xoud_site_row[] Select_by_domain(int user_id, String domain) {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Null;
		try {
			stmt = conn.New_stmt_select_all_where(Tbl_name, Flds, Fld_user_id, Fld_site_domain);
			rdr = stmt.Crt_int(Fld_user_id, user_id).Crt_str(Fld_site_domain, domain).Exec_select_as_rdr();
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
		( rdr.Read_int(Fld_user_id)
		, rdr.Read_int(Fld_site_id)
		, rdr.Read_int(Fld_site_priority)
		, rdr.Read_str(Fld_site_domain)
		, rdr.Read_str(Fld_site_name)
		, rdr.Read_str(Fld_site_path)
		, rdr.Read_str(Fld_site_xtn)
		);
	}
	public void Rls_all() {}
	private static final String Tbl_name = "user_site_regy";
	private static final Db_meta_fld_list Flds = Db_meta_fld_list.new_();
	private static final String
	  Fld_user_id				= Flds.Add_int("user_id")
	, Fld_site_id				= Flds.Add_int("site_id")
	, Fld_site_priority			= Flds.Add_int("site_priority")				// EX: 0=default; 1+ is order if 0 is unavailable
	, Fld_site_domain			= Flds.Add_str("site_domain", 255)			// EX: en.wikipedia.org; NOTE: no protocol (https:)
	, Fld_site_name				= Flds.Add_str("site_name", 255)			// EX: English Wikipedia
	, Fld_site_path				= Flds.Add_str("site_path", 255)			// EX: ~{xowa_root}/wiki/en.wikipedia.org/
	, Fld_site_xtn				= Flds.Add_text("site_xtn")
	;
//		private static final Db_meta_tbl meta = Db_meta_tbl.new_(Tbl_name, Flds.To_fld_ary()
//		, Db_meta_idx.new_unique(Tbl_name, "pkey", Fld_user_id, Fld_site_id)
//		);
}
