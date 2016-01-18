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
public class Xoud_site_tbl implements Rls_able {
	private final String tbl_name = "user_site"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_site_id, fld_site_priority, fld_site_domain, fld_site_name, fld_site_path, fld_site_xtn;
	private final Db_conn conn;
	public Xoud_site_tbl(Db_conn conn) {
		this.conn = conn;
		fld_site_id				= flds.Add_int_pkey("site_id");
		fld_site_priority		= flds.Add_int("site_priority");			// EX: 0=default; 1+ is order if 0 is unavailable
		fld_site_domain			= flds.Add_str("site_domain", 255);			// EX: en.wikipedia.org; NOTE: no protocol (https:)
		fld_site_name			= flds.Add_str("site_name", 255);			// EX: English Wikipedia
		fld_site_path			= flds.Add_str("site_path", 255);			// EX: ~{xowa_root}/wiki/en.wikipedia.org/
		fld_site_xtn			= flds.Add_text("site_xtn");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Rls() {}
	public void Insert(int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Val_int(fld_site_id, site_id)
			.Val_int(fld_site_priority, priority).Val_str(fld_site_domain, domain).Val_str(fld_site_name, name).Val_str(fld_site_path, path).Val_str(fld_site_xtn, xtn)
			.Exec_insert();
	}
	public void Update(int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_site_id);
		stmt.Val_int(fld_site_priority, priority).Val_str(fld_site_domain, domain).Val_str(fld_site_name, name).Val_str(fld_site_path, path).Val_str(fld_site_xtn, xtn)
			.Crt_int(fld_site_id, site_id)
			.Exec_update();
	}
	public void Delete(int site_id) {
		Db_stmt stmt = conn.Stmt_delete(tbl_name, fld_site_id);
		stmt.Crt_int(fld_site_id, site_id).Exec_delete();
	}
	public Xoud_site_row[] Select_all() {
		List_adp rv = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(new_row(rdr));
			return (Xoud_site_row[])rv.To_ary_and_clear(Xoud_site_row.class);
		}
		finally {rdr.Rls();}
	}
	public Xoud_site_row[] Select_by_domain(String domain) {
		List_adp rv = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_site_domain).Crt_str(fld_site_domain, domain).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(new_row(rdr));
			return (Xoud_site_row[])rv.To_ary_and_clear(Xoud_site_row.class);
		}
		finally {rdr.Rls();}
	}
	private Xoud_site_row new_row(Db_rdr rdr) {
		return new Xoud_site_row
		( rdr.Read_int(fld_site_id)
		, rdr.Read_int(fld_site_priority)
		, rdr.Read_str(fld_site_domain)
		, rdr.Read_str(fld_site_name)
		, rdr.Read_str(fld_site_path)
		, rdr.Read_str(fld_site_xtn)
		);
	}
}
