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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import org.junit.*; import gplx.core.tests.*;
public class Xowdir_wiki_props_mgr__tst {
	private final    Xowdir_wiki_props_mgr__fxt fxt = new Xowdir_wiki_props_mgr__fxt();

	@Test   public void Import__wiki__missing_all() {
		// handle all imported .xowa wikis pre v4.3
		fxt.Init__props(null, null, null);
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.Y, fxt.Make__json("test", "test", "Main_Page"));
	}
	@Test   public void Import__wiki__missing_domain() {
		// handle personal wikis from v4.2
		fxt.Init__props(null, null, "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.Y, fxt.Make__json("test", "test", "Main_Page"));
	}
	@Test   public void Import__wiki__wmf_domain() {
		// handle wmf wikis with a core-file of "test-core.xowa"
		fxt.Init__props(null, null, "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test-core.xowa", Bool_.Y, fxt.Make__json("test", "test", "Main_Page"));
	}
	@Test   public void Import__wiki__clean() {
		// handle clean wiki
		fxt.Init__props("test", "test", "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.N, fxt.Make__json("test", "test", "Main_Page"));
	}
}
class Xowdir_wiki_props_mgr__fxt {
	private final    Xowdir_wiki_props_mgr mgr = new Xowdir_wiki_props_mgr__mock();
	public Xowdir_wiki_props Make__json(String domain, String name, String main_page) {
		return new Xowdir_wiki_props(domain, name, main_page);
	}
	public void Init__props(String domain, String name, String main_page) {
		mgr.Wiki_cfg__upsert(Xowdir_wiki_cfg_.Key__domain, domain);
		mgr.Wiki_cfg__upsert(Xowdir_wiki_cfg_.Key__name, name);
		mgr.Wiki_cfg__upsert(Xowdir_wiki_cfg_.Key__main_page, main_page);
	}
	public void Test__verify(boolean mode_is_import, String url, boolean expd_dirty, Xowdir_wiki_props expd) {
		Xowdir_wiki_props actl = mgr.Verify(mode_is_import, "", Io_url_.new_any_(url));

		Gftest.Eq__ary__lines(expd.To_str(), actl.To_str(), "expd");
		Gftest.Eq__bool(expd_dirty, actl.Dirty(), "dirty");
	}
}
