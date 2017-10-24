/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
class Xoud_user_mgr {
	private Xoud_user_tbl tbl = new Xoud_user_tbl();
	public void Conn_(Db_conn conn, boolean created) {tbl.Conn_(conn, created);}
	public int Get_id_or_new(String name) {
		int rv = tbl.Select_id_by_name(name);
		if (rv == Int_.Min_value) {
			rv = tbl.Select_id_next();
			tbl.Insert(rv, name);
		}
		return rv;
	}
}
class Xoud_user_tbl {
	private String tbl_name = "user_user_regy"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private String fld_id, fld_name;
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_id					= flds.Add_int_pkey("id");
		fld_name				= flds.Add_str("name", 255);
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "name", fld_name)
			);
			conn.Meta_tbl_create(meta);
		}
	}
	public void Insert(int id, String name) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Val_int(fld_id, id).Val_str(fld_name, name)
			.Exec_insert();
	}
	public int Select_id_by_name(String name) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_name).Crt_str(fld_name, name).Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? rdr.Read_int(fld_id) : Int_.Min_value;
		}
		finally {rdr.Rls();}
	}
	public int Select_id_next() {
		int rv = 1;
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int cur = rdr.Read_int(fld_id);
				if (cur >= rv) rv = cur + 1;
			}
			return rv;
		}
		finally {rdr.Rls();}
	}
}
