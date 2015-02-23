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
	private String tbl_name = "user_site_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_user_id, fld_site_id, fld_site_priority, fld_site_domain, fld_site_name, fld_site_path, fld_site_xtn;
	private Db_conn conn; private int user_id;
	public void Conn_(Db_conn new_conn, boolean created, int user_id) {
		this.conn = new_conn; flds.Clear(); this.user_id = user_id;
		fld_user_id				= flds.Add_int("user_id");
		fld_site_id				= flds.Add_int("site_id");
		fld_site_priority		= flds.Add_int("site_priority");			// EX: 0=default; 1+ is order if 0 is unavailable
		fld_site_domain			= flds.Add_str("site_domain", 255);			// EX: en.wikipedia.org; NOTE: no protocol (https:)
		fld_site_name			= flds.Add_str("site_name", 255);			// EX: English Wikipedia
		fld_site_path			= flds.Add_str("site_path", 255);			// EX: ~{xowa_root}/wiki/en.wikipedia.org/
		fld_site_xtn			= flds.Add_text("site_xtn");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_user_id, fld_site_id)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
	}
	@gplx.Virtual public void Insert(int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Val_int(fld_user_id, user_id).Val_int(fld_site_id, site_id)
			.Val_int(fld_site_priority, priority).Val_str(fld_site_domain, domain).Val_str(fld_site_name, name).Val_str(fld_site_path, path).Val_str(fld_site_xtn, xtn)
			.Exec_insert();
	}
	@gplx.Virtual public void Update(int site_id, int priority, String domain, String name, String path, String xtn) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_user_id, fld_site_id);
		stmt.Val_int(fld_site_priority, priority).Val_str(fld_site_domain, domain).Val_str(fld_site_name, name).Val_str(fld_site_path, path).Val_str(fld_site_xtn, xtn)
			.Crt_int(fld_user_id, user_id).Crt_int(fld_site_id, site_id)
			.Exec_update();
	}
	@gplx.Virtual public void Delete(int site_id) {
		Db_stmt stmt = conn.Stmt_delete(tbl_name, fld_user_id, fld_site_id);
		stmt.Crt_int(fld_user_id, user_id).Crt_int(fld_site_id, site_id)
			.Exec_delete();
	}
	public Xoud_site_row[] Select_all() {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Null;
		try {
			stmt = conn.Stmt_select(tbl_name, flds, fld_user_id);
			rdr = stmt.Crt_int(fld_user_id, user_id).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_site_row row = Make_row(rdr);
				rv.Add(row);
			}
			return (Xoud_site_row[])rv.Xto_ary_and_clear(Xoud_site_row.class);
		}
		finally {rdr.Rls();}
	}
	public Xoud_site_row[] Select_by_domain(String domain) {
		ListAdp rv = ListAdp_.new_();
		Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Null;
		try {
			stmt = conn.Stmt_select(tbl_name, flds, fld_user_id, fld_site_domain);
			rdr = stmt.Crt_int(fld_user_id, user_id).Crt_str(fld_site_domain, domain).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xoud_site_row row = Make_row(rdr);
				rv.Add(row);
			}
			rdr.Rls();
			return (Xoud_site_row[])rv.Xto_ary_and_clear(Xoud_site_row.class);
		}
		finally {rdr.Rls();}
	}
	private Xoud_site_row Make_row(Db_rdr rdr) {
		return new Xoud_site_row
		( rdr.Read_int(fld_user_id)
		, rdr.Read_int(fld_site_id)
		, rdr.Read_int(fld_site_priority)
		, rdr.Read_str(fld_site_domain)
		, rdr.Read_str(fld_site_name)
		, rdr.Read_str(fld_site_path)
		, rdr.Read_str(fld_site_xtn)
		);
	}
}
