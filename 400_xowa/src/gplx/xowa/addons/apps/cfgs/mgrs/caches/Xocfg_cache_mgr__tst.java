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
package gplx.xowa.addons.apps.cfgs.mgrs.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import org.junit.*; import gplx.core.tests.*; import gplx.dbs.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.specials.maints.services.*;
public class Xocfg_cache_mgr__tst {
	private final    Xocfg_cache_mgr__fxt fxt = new Xocfg_cache_mgr__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Get__wiki() {
		fxt.Init__db_add("en.w", "key_1", "val_1");
		fxt.Test__get("en.w", "key_1", "val_1");
		fxt.Test__get("en.d", "key_1", "dflt");
	}
	@Test   public void Get__app() {
		String ctx = Xocfg_mgr.Ctx__app;
		fxt.Init__db_add(ctx, "key_1", "val_1");
		fxt.Test__get(ctx, "key_1", "val_1");
		fxt.Test__get("en.w", "key_1", "val_1");
		fxt.Test__get("en.d", "key_1", "val_1");
	}
	@Test   public void Set__app() {
		String ctx = Xocfg_mgr.Ctx__app;
		fxt.Init__db_add(ctx, "key_1", "123");
		fxt.Init__sub(ctx, "key_1", "key_1");
		fxt.Exec__set(ctx, "key_1", "234");
		fxt.Test__get(ctx, "key_1", "234");
		fxt.Sub().Test__key_1(234);
	}
}
class Xocfg_cache_mgr__fxt {
	private Xocfg_cache_mgr mgr = new Xocfg_cache_mgr();
	private int id;
	public void Clear() {
		gplx.dbs.Db_conn_bldr.Instance.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.Instance.Get_or_autocreate(true, Io_url_.new_any_("mem/xowa/wiki/en.wikipedia.org/"));
		mgr.Init_by_app(conn, conn);
	}
	public Xocfg_cache_sub_mock Sub() {return sub;} private Xocfg_cache_sub_mock sub = new Xocfg_cache_sub_mock();
	public void Init__db_add(String ctx, String key, Object val) {
		Xocfg_maint_svc.Create_grp(mgr.Db_app(), "test_grp", id++, "", "", "");
		Xocfg_maint_svc.Create_itm(mgr.Db_app(), "test_grp", id++, key, "", "", "wiki", String_.Cls_val_name, "dflt", "", "");
		mgr.Db_usr().Set_str(ctx, key, Object_.Xto_str_strict_or_null(val));
	}
	public void Init__sub(String ctx, String key, String evt) {
		mgr.Sub(sub, ctx, key, evt);
	}
	public void Test__get(String ctx, String key, String expd) {
		Gftest.Eq__str(expd, mgr.Get(ctx, key), "ctx={0} key={1}", ctx, key);
	}
	public void Exec__set(String ctx, String key, String val) {
		mgr.Set(ctx, key, val);
	}
}
class Xocfg_cache_sub_mock implements Gfo_invk {
	private int key_1;
	public void Test__key_1(int expd) {
		Gftest.Eq__int(expd, key_1);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Evt__key_1))	{key_1 = m.ReadInt("v");}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Evt__key_1 = "key_1";
}
