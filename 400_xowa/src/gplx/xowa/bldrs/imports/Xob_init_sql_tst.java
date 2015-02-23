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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_init_sql_tst {
	@Before public void init() {fxt.Clear();} private Xob_init_sql_fxt fxt = new Xob_init_sql_fxt();
	@Test  public void Basic() {
//			fxt.Init_fsys();
//			fxt.Test_run();
	}
}
class Xob_init_sql_fxt {		
	public void Clear() {
		fxt.Ctor_fsys();
		app = fxt.Wiki().Appe();
		wiki = fxt.Wiki();
	}	Db_mgr_fxt fxt = new Db_mgr_fxt();
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Init_fsys() {
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		Io_url[] fils = Io_mgr._.QueryDir_fils(wiki_dir);
		int len = fils.length;
		for (int i = 0; i < len; i++) {
			Io_mgr._.DeleteFil(fils[i]);
		}
//			Io_mgr._.DeleteFil(wiki_dir.GenSubFil("en.wikipedia.org.sqlite3"));
		Io_mgr._.SaveFilStr(wiki_dir.GenSubFil("siteinfo.xml"), gplx.xowa.utls.upgrades.Upgrader_v00_02_01_tst.Str_siteinfo_xml);
//			Io_mgr._.DeleteDirDeep(Db_mgr_fxt.Test_root().GenSubDir("root", "wiki", "en.wikipedia.org"));
//			app.Fsys_mgr().Root_dir()
//			Db_mgr_fxt.Test_root()
		// Io_mgr._
		// mem/xowa/user/test_user/app/setup/wikixowa.core.sqlite3
	}
	public void Test_run() {
//			fxt.Init_db_sqlite(wiki.Fsys_mgr().Root_dir().GenSubFil("en.wikipedia.org.sqlite3"));
		app.Bldr().Cmd_mgr().Add_many(wiki, Xob_init_sql.KEY);
		app.Bldr().Run();
		// test database copied
		// test cfg values exists
		// test ns parsed
	}
} 
