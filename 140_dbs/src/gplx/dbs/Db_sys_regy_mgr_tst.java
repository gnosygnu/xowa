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
public class Db_sys_regy_mgr_tst {
	@Before public void init() {fxt.Init();} private Db_sys_regy_mgr_fxt fxt = new Db_sys_regy_mgr_fxt();
	@Test   public void Insert() {
		fxt	.Exec_set("grp", "key", "val_0")
			.Test_get("grp", "key", "val_0");
	}
	@Test   public void Update() {
		fxt	.Exec_set("grp", "key", "val_0")
			.Exec_set("grp", "key", "val_1")
			.Test_get("grp", "key", "val_1");
	}
	@Test   public void Delete() {
		fxt	.Exec_set("grp", "key_0", "val_0")
			.Exec_set("grp", "key_1", "val_0")
			.Exec_del("grp", "key_1")
			.Test_get("grp", "key_0", null)
			.Test_get("grp", "key_1", null)
			;
	}
}
class Db_sys_regy_mgr_fxt {
	private Db_sys_regy_mgr sys_regy_mgr;
	public void Init() {
		if (sys_regy_mgr == null) {
			Db_conn_pool.I.Set_mem("test_db", Db_sys_regy_tbl.new_meta("test_regy"));
			sys_regy_mgr = new Db_sys_regy_mgr(Db_url_.mem_("test_db"), "test_regy");				
		}
	}
	public Db_sys_regy_mgr_fxt Exec_set(String grp, String key, String val) {sys_regy_mgr.Set(grp, key, val); return this;}
	public Db_sys_regy_mgr_fxt Exec_del(String grp, String key)				{sys_regy_mgr.Del(grp, key); return this;}
	public Db_sys_regy_mgr_fxt Test_get(String grp, String key, String expd_val) {
		Tfds.Eq(expd_val, sys_regy_mgr.Get_val_as_str_or(grp, key, null));
		return this;
	}
}
