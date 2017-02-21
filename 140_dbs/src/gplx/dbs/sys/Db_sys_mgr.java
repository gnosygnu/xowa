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
package gplx.dbs.sys; import gplx.*; import gplx.dbs.*;
public class Db_sys_mgr {
	private final    Db_conn conn;
	private final    Db_sys_tbl sys_tbl;
	private boolean assert_exists = true;
	public Db_sys_mgr(Db_conn conn) {
		this.conn = conn;
		this.sys_tbl = new Db_sys_tbl(conn);
	}
	public int Autonum_next(String tbl, String fld) {return Autonum_next(String_.Concat(tbl, ".", fld));}
	public int Autonum_next(String key) {
		if (assert_exists) Assert_exists();
		int rv = sys_tbl.Assert_int_or(key, 1);
		sys_tbl.Update_int(key, rv + 1);
		return rv;
	}
	private void Assert_exists() {
		assert_exists = false;
		if (!conn.Meta_tbl_exists(sys_tbl.Tbl_name())) sys_tbl.Create_tbl();
	}
}
