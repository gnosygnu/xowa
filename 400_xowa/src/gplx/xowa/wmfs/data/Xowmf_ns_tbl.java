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
public class Xowmf_ns_tbl implements RlsAble {
	private static final String tbl_name = "wmf_ns"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_id, fld_case, fld_subpages, fld_content, fld_name, fld_canonical;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_ns_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id	= flds.Add_int("site_id");
		this.fld_id			= flds.Add_int("ns_id");
		this.fld_case		= flds.Add_byte("ns_case");
		this.fld_subpages	= flds.Add_byte("ns_subpages");
		this.fld_content	= flds.Add_byte("ns_content");
		this.fld_name		= flds.Add_str("ns_name", 255);
		this.fld_canonical	= flds.Add_str("ns_canonical", 255);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id, fld_id)));}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Insert(int site_id, int id, byte case_match, boolean subpages, boolean content, byte[] name, byte[] canonical) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_site_id, site_id).Val_int(fld_id, id).Val_byte(fld_case, case_match).Val_bool_as_byte(fld_subpages, subpages).Val_bool_as_byte(fld_content, content)
			.Val_bry_as_str(fld_name, name).Val_bry_as_str(fld_canonical, canonical).Exec_insert();
	}
	public void Select_all(Xow_ns_mgr rv, int site_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_site_id, site_id).Exec_select__rls_manual();
		rv.Clear();
		try {
			while (rdr.Move_next()) {
				rv.Add_new
				( rdr.Read_int			(fld_id)
				, rdr.Read_bry_by_str	(fld_name)
				, rdr.Read_byte			(fld_case)
				, Bool_.N);
			}
		}
		finally {rdr.Rls();}
		rv.Init();			
	}
}
