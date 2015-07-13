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
public class Xowmf_site_tbl implements RlsAble {
	private static final String tbl_name = "wmf_site"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_id, fld_name;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_site_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_id = flds.Add_int_pkey("site_id");
		this.fld_name = flds.Add_str("site_name", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "name", fld_name)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Insert(int id, String name) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_id, id).Val_str(fld_name, name).Exec_insert();
	}
	public int Select_id(String name) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_name);
		Db_rdr rdr = stmt_select.Clear().Crt_str(fld_name, name).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_int(fld_id) : -1;
		}
		finally {rdr.Rls();}
	}
	public static Db_conn Get_conn_or_new(Io_url xowa_root) {
		Io_url wmf_data_url = xowa_root.GenSubFil_nest("bin", "any", "xowa", "cfg", "wiki", "wmf_data.sqlite3");
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(wmf_data_url);
		Db_conn conn = conn_data.Conn();
		if (conn_data.Created()) {
			Xowmf_site_tbl site_tbl = new Xowmf_site_tbl(conn); site_tbl.Create_tbl();
			Xowmf_ns_tbl itm_tbl = new Xowmf_ns_tbl(conn); itm_tbl.Create_tbl();
		}
		return conn;
	}
}
