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
public class Xoud_cfg_tbl {
	private String tbl_name = "user_cfg"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_usr, fld_grp, fld_key, fld_val;
	private Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select;
	private int usr;
	public void Conn_(Db_conn new_conn, boolean created, int usr) {
		this.conn = new_conn; flds.Clear();
		fld_usr				= flds.Add_int("usr");
		fld_grp				= flds.Add_str("grp", 255);
		fld_key				= flds.Add_str("key", 255);
		fld_val				= flds.Add_str("val", 1024);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_usr, fld_grp, fld_key, fld_val)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_update = stmt_select = null;
		this.usr = usr;
	}
	public void Insert(String grp, String key, String val) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		stmt_insert.Clear().Val_int(fld_usr, usr).Val_str(fld_grp, grp).Val_str(fld_key, key).Val_str(fld_val, val).Exec_insert();
	}	
	public void Update(String grp, String key, String val) {
		if (stmt_update == null) stmt_update = conn.Rls_reg(conn.Stmt_update_exclude(tbl_name, flds, fld_usr, fld_grp, fld_key));
		stmt_update.Clear().Val_str(fld_val, val).Crt_int(fld_usr, usr).Crt_str(fld_grp, grp).Crt_str(fld_key, key).Exec_update();
	}
	public int Select_as_int_or_fail(String grp, String key) {
		int rv = Select_as_int_or(grp, key, Int_.MinValue);
		if (rv == Int_.MinValue) throw Err_.new_fmt_("dbs.cfg_tbl.Select_as_int_or_fail: usr={0} grp={1} key={2}", usr, grp, key);
		return rv;
	}
	public int Select_as_int_or(String grp, String key, int or) {return Int_.parse_or_(Select_as_str_or(grp, key, null), or);}
	public String Select_as_str_or(String grp, String key, String or) {
		if (stmt_select == null) stmt_select = conn.Rls_reg(conn.Stmt_select(tbl_name, String_.Ary(fld_val), fld_usr, fld_grp, fld_key));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select.Clear()
				.Crt_int(fld_usr, usr)
				.Crt_str(fld_grp, grp)
				.Crt_str(fld_key, key)
				.Exec_select_as_rdr();
			return rdr.Move_next() ? rdr.Read_str(fld_val) : or;
		} finally {rdr.Rls();}
	}
//		public Xoud_cfg_grp Select_as_grp(String grp) {
//			Xoud_cfg_grp rv = null;
//			Db_stmt stmt = conn.Stmt_select(tbl_name, flds, fld_grp);
//			Db_rdr rdr = Db_rdr_.Null;
//			try {
//				rdr = stmt.Clear().Crt_str(fld_grp, grp).Exec_select_as_rdr();
//				while (rdr.Move_next()) {
//					if (rv == null) rv = new Xoud_cfg_grp(grp);
//					rv.Upsert(rdr.Read_str(fld_key), rdr.Read_str(fld_val));
//				}
//			}
//			finally {rdr.Rls();}
//			return rv == null ? Xoud_cfg_grp.Null : rv;
//		}
}
