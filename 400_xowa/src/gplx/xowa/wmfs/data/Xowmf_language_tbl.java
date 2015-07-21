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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.dbs.*;
class Xowmf_language_tbl implements RlsAble {
	private static final String tbl_name = "wmf_language"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_code, fld_name;
	private final Db_conn conn;
	private Db_stmt stmt_insert;
	public Xowmf_language_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id				= flds.Add_int("site_id");
		this.fld_code					= flds.Add_str("code", 255);
		this.fld_name					= flds.Add_str("name", 255);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id, fld_code)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int site_id, byte[] code, byte[] name) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_site_id				, site_id)
			.Val_bry_as_str(fld_code			, code)
			.Val_bry_as_str(fld_name			, name)
			.Exec_insert();
	}		
}
