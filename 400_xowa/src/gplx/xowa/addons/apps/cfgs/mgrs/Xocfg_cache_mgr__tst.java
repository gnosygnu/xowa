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
package gplx.xowa.addons.apps.cfgs.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import org.junit.*; import gplx.core.tests.*; import gplx.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
public class Xocfg_cache_mgr__tst {
	private final    Xocfg_cache_mgr__fxt fxt = new Xocfg_cache_mgr__fxt();
	@Test   public void Basic() {
//			fxt.Init__db_add("en.w", "key_1", "val_1");
//			fxt.Test__get("en.w", "key_1", "val_1");
	}
}
class Xocfg_cache_mgr__fxt {
	private final    Xocfg_cache_mgr mgr;
	private final    Xocfg_itm_bldr cfg_bldr;
	public Xocfg_cache_mgr__fxt() {
		gplx.dbs.Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.Instance.Get_or_autocreate(true, Io_url_.new_any_("mem/xowa/wiki/en.wikipedia.org/"));
		this.mgr = new Xocfg_cache_mgr(conn);
		cfg_bldr = new Xocfg_itm_bldr(mgr.Db_mgr());
	}
	public void Init__db_add(String ctx, String key, String val) {
		cfg_bldr.Create_grp("", "test_grp", "", "");
		cfg_bldr.Create_itm("test_grp", key, "wiki", "textbox", "", "dflt", "", "");
		mgr.Db_mgr().Set_str(ctx, key, val);
	}
	public void Test__get(String ctx, String key, String expd) {
		Gftest.Eq__str(expd, mgr.Get(ctx, key));
	}
}
