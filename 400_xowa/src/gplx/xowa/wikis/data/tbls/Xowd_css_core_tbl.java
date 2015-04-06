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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*;
public class Xowd_css_core_tbl implements RlsAble {
	private final String tbl_name = "css_core"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_key, fld_updated_on;
	public Xowd_css_core_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_id				= flds.Add_int_pkey_autonum("css_id");
		this.fld_key			= flds.Add_str("css_key", 255);
		this.fld_updated_on		= flds.Add_str("css_updated_on", 20);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_key)));}
	public void Rls() {}
	public int Insert(String key, DateAdp updated_on) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Val_str(fld_key, key).Val_str(fld_updated_on, updated_on.XtoStr_fmt_yyyyMMdd_HHmmss()).Exec_insert();
		return Select_id_by_key(key);
	}
	public void Update(int id, String key, DateAdp updated_on) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Val_str(fld_key, key).Val_str(fld_updated_on, updated_on.XtoStr_fmt_yyyyMMdd_HHmmss()).Crt_int(fld_id, id).Exec_update();
	}
	public int Select_id_by_key(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_key).Crt_str(fld_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld_id) : Id_null;}
		finally {rdr.Rls();}
	}
	public Xowd_css_core_itm[] Select_all() {	// TEST:
		Db_stmt stmt = conn.Stmt_select(tbl_name, flds);
		return Select_by_stmt(stmt);
	}
	private Xowd_css_core_itm[] Select_by_stmt(Db_stmt stmt) {
		ListAdp rv = ListAdp_.new_();
		Db_rdr rdr = stmt.Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(new_itm(rdr));
		} finally {rdr.Rls();}
		return (Xowd_css_core_itm[])rv.Xto_ary_and_clear(Xowd_css_core_itm.class);
	}
	private Xowd_css_core_itm new_itm(Db_rdr rdr) {
		return new Xowd_css_core_itm(rdr.Read_int(fld_id), rdr.Read_str(fld_key), rdr.Read_date_by_str(fld_updated_on));
	}
	public static final int Id_null = -1;
}
