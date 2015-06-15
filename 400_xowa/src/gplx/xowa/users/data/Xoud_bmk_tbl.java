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
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xoud_bmk_tbl implements RlsAble {
	private final String tbl_name = "bmk_core"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_url;
	public Xoud_bmk_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id						= flds.Add_int_pkey_autonum("bmk_id");
		fld_url						= flds.Add_str("bmk_url", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds.To_fld_ary()));}
	public void Insert(byte[] url) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_bry_as_str(fld_url, url)
			.Exec_insert();
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
	public Xoud_bmk_row[] Select_all() {
		List_adp list = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy).Clear().Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				list.Add(new_row(rdr));
		}
		finally {rdr.Rls();}
		return (Xoud_bmk_row[])list.To_ary_and_clear(Xoud_bmk_row.class);
	}
	private Xoud_bmk_row new_row(Db_rdr rdr) {
		return new Xoud_bmk_row
		( rdr.Read_int(fld_id)
		, rdr.Read_bry_by_str(fld_url)
		);
	}
	public void Rls() {}
}
