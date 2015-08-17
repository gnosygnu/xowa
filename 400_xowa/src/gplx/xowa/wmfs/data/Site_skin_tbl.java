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
class Site_skin_tbl implements Db_tbl {
	private static final String tbl_name = "site_skin"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_abrv, fld_code, fld_dflt, fld_name, fld_unusable;
	private final Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete;
	public Site_skin_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv				= flds.Add_str("site_abrv", 255);
		this.fld_code					= flds.Add_str("code", 255);
		this.fld_dflt					= flds.Add_bool("dflt");
		this.fld_name					= flds.Add_str("name", 255);
		this.fld_unusable				= flds.Add_bool("unusable");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, Db_meta_idx.Bld_idx_name(tbl_name, "main"), fld_site_abrv, fld_code)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
	}
	public void Select(byte[] site_abrv, Ordered_hash list) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_abrv);
		list.Clear();
		Db_rdr rdr = stmt_select.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Site_skin_itm itm = new Site_skin_itm
				( rdr.Read_bry_by_str(fld_code)
				, rdr.Read_bool_by_byte(fld_dflt)
				, rdr.Read_bry_by_str(fld_name)
				, rdr.Read_bool_by_byte(fld_unusable)
				);
				list.Add(itm.Name(), itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Insert(byte[] site_abrv, Ordered_hash list) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Site_skin_itm itm = (Site_skin_itm)list.Get_at(i);
			Insert(site_abrv, itm.Code(), itm.Dflt(), itm.Name(), itm.Unusable());
		}
	}
	private void Insert(byte[] site_abrv, byte[] code, boolean dflt, byte[] name, boolean unusable) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_site_abrv		, site_abrv)
			.Val_bry_as_str(fld_code			, code)
			.Val_bool_as_byte(fld_dflt			, dflt)
			.Val_bry_as_str(fld_name			, name)
			.Val_bool_as_byte(fld_unusable		, unusable)
			.Exec_insert();
	}		
}
