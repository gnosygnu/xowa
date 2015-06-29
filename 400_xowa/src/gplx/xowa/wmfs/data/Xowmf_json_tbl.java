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
public class Xowmf_json_tbl implements RlsAble {
	private static final String tbl_name = "wmf_json"; private final Db_meta_fld_list flds = new Db_meta_fld_list();
	private final String fld_site_id, fld_text, fld_date;
	private final Db_conn conn;
	private Db_stmt stmt_insert, stmt_select;
	public Xowmf_json_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_id = flds.Add_int("site_id");
		this.fld_date = flds.Add_str("json_date", 20);
		this.fld_text = flds.Add_text("json_text");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_name(tbl_name, "main", fld_site_id)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Delete_all() {conn.Stmt_delete(tbl_name, Db_meta_fld.Ary_empty).Exec_delete();}
	public void Insert(int site_id, DateAdp date, byte[] text) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_site_id, site_id).Val_str(fld_date, date.XtoStr_gplx()).Val_bry_as_str(fld_text, text).Exec_insert();
	}
	public String Select_text_or_null(int site_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_site_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_site_id, site_id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_str(fld_text) : null;
		}
		finally {rdr.Rls();}
	}
}
