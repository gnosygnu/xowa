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
package gplx.dbs; import gplx.*;
class Db_sys_regy_tbl implements Db_conn_itm {
	private final String tbl_name;
	private Db_conn conn;
	public void Conn_term() {}
	public Db_sys_regy_tbl(Db_url url, String tbl_name) {
		this.tbl_name = tbl_name;
//			this.meta = Db_sys_regy_tbl.new_meta(tbl_name);
		this.conn = Db_conn_pool.I.Get_or_new(url);
		conn.Itms_add(this);
	}
//		private Db_meta_tbl meta;
	public void Insert(String grp, String key, String val) {
		Db_stmt stmt = conn.New_stmt_insert(tbl_name, Flds.To_str_ary());
		stmt.Clear().Val_str(grp).Val_str(key).Val_str(val).Exec_insert();
	}
	public void Update(String grp, String key, String val) {
		Db_stmt stmt = conn.New_stmt_update(tbl_name, String_.Ary(Fld_regy_grp, Fld_regy_key), Fld_regy_val);
		stmt.Clear().Val_str(val).Crt_str(Fld_regy_grp, grp).Crt_str(Fld_regy_key, key).Exec_update();
	}
	public void Delete(String grp, String key) {
		Db_stmt stmt = conn.New_stmt_delete(tbl_name, Fld_regy_grp, Fld_regy_key);
		stmt.Clear().Crt_str(Fld_regy_grp, grp).Crt_str(Fld_regy_key, key).Exec_delete();
	}
	public String Select_val_or(String grp, String key, String or) {
		Db_stmt stmt = conn.New_stmt_select_all_where(tbl_name, Flds.To_str_ary(), Fld_regy_grp, Fld_regy_key);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt.Clear().Crt_str(Fld_regy_grp, grp).Crt_str(Fld_regy_key, key).Exec_select_as_rdr();
			return rdr.Move_next() ? rdr.Read_str(Fld_regy_val) : or;
		}	finally {rdr.Rls();}
	}
	private static final Db_meta_fld_list Flds = Db_meta_fld_list.new_();
	private static final String
	  Fld_regy_grp				= Flds.Add_str("regy_grp", 1024)
	, Fld_regy_key				= Flds.Add_str("regy_key", 1024)
	, Fld_regy_val				= Flds.Add_str("regy_val", 4096)
	;
	public static Db_meta_tbl new_meta(String tbl) {
		return Db_meta_tbl.new_(tbl, Flds.To_fld_ary()
		, Db_meta_idx.new_unique(tbl, "key", Flds.To_str_ary())
		);
	} 
}
