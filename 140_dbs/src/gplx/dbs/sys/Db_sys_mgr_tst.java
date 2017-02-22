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
import org.junit.*;
public class Db_sys_mgr_tst {
	private final Db_sys_mgr_fxt fxt = new Db_sys_mgr_fxt();
	@Test  public void FetchNextId() {
		fxt.Test__autonum_next("tbl_1.fld", 1);	// default to "1" on first creation
		fxt.Test__autonum_next("tbl_1.fld", 2);	// read "2" from db
	}
}
class Db_sys_mgr_fxt {
	private final Db_sys_mgr sys_mgr;
	public Db_sys_mgr_fxt() {
		Db_conn conn = Db_conn_pool.Instance.Get_or_new(Db_conn_info_.mem_("test"));
		sys_mgr = new Db_sys_mgr(conn);
	}
	public void Test__autonum_next(String key, int expd) {Tfds.Eq_int(expd, sys_mgr.Autonum_next(key));}
}
