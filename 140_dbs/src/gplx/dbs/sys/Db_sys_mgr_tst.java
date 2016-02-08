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
