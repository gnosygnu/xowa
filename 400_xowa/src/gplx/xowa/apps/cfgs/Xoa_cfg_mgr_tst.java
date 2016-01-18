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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.dbs.*;
public class Xoa_cfg_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoa_cfg_mgr_fxt fxt = new Xoa_cfg_mgr_fxt();
	@Test  public void Init() {
		fxt.Init_cfg_all("import.db_text_max", "1000");
		fxt.Test_init_wiki("simple.wikipedia.org", "import.db_text_max", "1000");
		fxt.Test_cfg_itm("simple.wikipedia.org", "import.db_text_max", false, false);
	}
	@Test  public void Notify() {
		fxt.Exec_make_wiki("simple.wikipedia.org");
		fxt.Test_cfg_set("app.cfgs.get('import.db_text_max', 'simple.wikipedia.org').val = '2000';", "simple.wikipedia.org", "import.db_text_max", "2000");
		fxt.Test_cfg_itm("simple.wikipedia.org", "import.db_text_max", true, true);
		fxt.Test_save("app.cfgs.get('import.db_text_max', 'simple.wikipedia.org').val = '2000';\n");
	}
	@Test  public void Notify_quote() {
		fxt.Test_cfg_set("app.cfgs.get('app.gui.html.css_xtn', 'app').val = 'a''b';", "*", "app.gui.html.css_xtn", "a'b");
		fxt.Test_cfg_itm("app", "app.gui.html.css_xtn", true, true);
		fxt.Test_save("app.cfgs.get('app.gui.html.css_xtn', 'app').val = 'a''b';\n");
	}
	@Test  public void Init_should_not_notify_if_app() {
		fxt.Init_cfg_app("import.db_text_max", "1000");
		fxt.Test_init_wiki("simple.wikipedia.org", "import.db_text_max", "3000");	// 3000 is default; itm shouldn't change b/c cfg is app level; DATE:2013-07-14
	}
}
class Xoa_cfg_mgr_fxt {
	public void Clear() {
//			if (app == null) {
			app = Xoa_app_fxt.app_();
			cfg_mgr = app.Cfg_mgr();
//			}
	}	private Xoae_app app; Xoa_cfg_mgr cfg_mgr;
	public void Init_cfg_all(String key, String val) {Init_cfg(key, val, Xoa_cfg_grp_tid.Key_all_bry);}
	public void Init_cfg_app(String key, String val) {Init_cfg(key, val, Xoa_cfg_grp_tid.Key_app_bry);}
	private void Init_cfg(String key, String val, byte[] tid) {
		Xoa_cfg_itm itm = app.Cfg_mgr().Get_itm_or_make(Bry_.new_a7(key), tid);
		itm.Val_(val);
	}
	public Xowe_wiki Exec_make_wiki(String wiki_key_str) {return Exec_make_wiki(Bry_.new_a7(wiki_key_str));} 
	public Xowe_wiki Exec_make_wiki(byte[] wiki_key_bry) {return app.Wiki_mgr().Get_by_or_make(wiki_key_bry);}
	public void Test_init_wiki(String wiki_key_str, String itm_key_str, String expd_val) {
		byte[] wiki_key_bry = Bry_.new_a7(wiki_key_str);
		Xowe_wiki wiki = Exec_make_wiki(wiki_key_bry);
		wiki.Init_assert();
		Test_cfg_get(wiki, itm_key_str, expd_val);
	}	
	public void Test_cfg_set(String cfg_msg, String wiki_key_str, String prop_key, String expd_val) {
		byte[] wiki_key_bry = Bry_.new_a7(wiki_key_str);
		app.Gfs_mgr().Run_str_for(app, cfg_msg);
		Xowe_wiki wiki = Exec_make_wiki(wiki_key_bry);
		Test_cfg_get(wiki, prop_key, expd_val);
	}
	private void Test_cfg_get(GfoInvkAble invk, String prop, String expd) {
		Tfds.Eq(expd, Object_.Xto_str_strict_or_null_mark(app.Cfg_mgr().Eval_get(invk, prop)));		
	}
	public void Test_cfg_itm(String wiki, String prop, boolean expd_customized, boolean expd_dirty) {
		Test_cfg_itm(Bry_.new_a7(wiki), Bry_.new_a7(prop), expd_customized, expd_dirty);
	}
	public void Test_cfg_itm(byte[] wiki, byte[] prop, boolean expd_customized, boolean expd_dirty) {
		Xoa_cfg_itm itm = cfg_mgr.Get_itm_or_make(prop, wiki);
		Tfds.Eq(itm.Val_is_customized(), expd_customized);
		Tfds.Eq(itm.Val_is_dirty(), expd_dirty);		
	}
	public void Test_save(String expd) {
		Xoa_cfg_db_txt db = new Xoa_cfg_db_txt();
		cfg_mgr.Db_save(db);
		Tfds.Eq(expd, Io_mgr.Instance.LoadFilStr(db.Cfg_url(cfg_mgr)));
	}
}
