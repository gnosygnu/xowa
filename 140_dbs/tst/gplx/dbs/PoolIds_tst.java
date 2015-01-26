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
import org.junit.*;
public class PoolIds_tst {
	@Before public void setup() {
		conn = Db_conn_pool_old._.Get_or_new(Db_url_.Test);
		Db_qry_fxt.DeleteAll(conn, PoolIds.Tbl_Name);
		mgr = PoolIds._;
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
