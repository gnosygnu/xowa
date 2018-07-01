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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class PoolIds_tst {
	@Before public void setup() {
		conn = Db_conn_pool.Instance.Get_or_new(Db_conn_info_.Test);
		Db_qry_fxt.DeleteAll(conn, PoolIds.Tbl_Name);
		mgr = PoolIds.Instance;
	}
	@Test  public void FetchNextId() {
		tst_Fetch("/test0", 0);
	}
	@Test  public void ChangeNextId_Insert() {
		run_Change("/test0", 1);

		tst_Fetch("/test0", 1);
	}
	@Test  public void ChangeNextId_Update() {
		run_Change("/test0", 0);
		run_Change("/test0", 1);

		tst_Fetch("/test0", 1);
	}
	@Test  public void FetchNextId_Multiple() {
		run_Change("/test0", 0);
		run_Change("/test1", 1);

		tst_Fetch("/test0", 0);
		tst_Fetch("/test1", 1);
	}
	void run_Change(String url, int expd) {
		mgr.Commit(conn, url, expd);
	}
	void tst_Fetch(String url, int expd) {
		int actl = mgr.FetchNext(conn, url);
		Tfds.Eq(expd, actl);
	}
	Db_conn conn;
	PoolIds mgr;
}
