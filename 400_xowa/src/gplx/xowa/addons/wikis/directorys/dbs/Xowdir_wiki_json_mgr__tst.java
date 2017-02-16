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
import gplx.langs.jsons.*;
public class Xowdir_wiki_json_mgr__tst {
	private final    Xowdir_wiki_json_mgr__fxt fxt = new Xowdir_wiki_json_mgr__fxt();

	@Test   public void Import__wiki__missing_all() {
		// handle all imported .xowa wikis pre v4.3
		fxt.Init__wiki_cfg__json(null);

		Xowdir_wiki_json expd = fxt.Make__json("test", "test", "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.Y, expd);
		fxt.Test__wiki_cfg(expd);
	}
	@Test   public void Import__wiki__missing_domain() {
		// handle personal wikis from v4.2
		fxt.Init__wiki_cfg__json(fxt.Make__json(null, "test", "Main_Page"));

		Xowdir_wiki_json expd = fxt.Make__json("test", "test", "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.Y, expd);
		fxt.Test__wiki_cfg(expd);
	}
	@Test   public void Import__wiki__clean() {
		// handle clean wiki
		fxt.Init__wiki_cfg__json(fxt.Make__json("test", "test", "Main_Page"));

		Xowdir_wiki_json expd = fxt.Make__json("test", "test", "Main_Page");
		fxt.Test__verify(Bool_.Y, "/dir/test.xowa", Bool_.N, expd);
		fxt.Test__wiki_cfg(expd);
	}
}
class Xowdir_wiki_json_mgr__fxt {
	private final    Xowdir_wiki_json_mgr mgr = new Xowdir_wiki_json_mgr__mock();
	private final    Json_wtr json_wtr = new Json_wtr();
	public Xowdir_wiki_json Make__json(String domain, String name, String main_page) {
		return new Xowdir_wiki_json(domain, name, main_page);
	}
	public void Init__wiki_cfg__json(Xowdir_wiki_json itm) {
		if (itm != null)
			mgr.Wiki_cfg__upsert(Xowdir_wiki_cfg_.Key__wiki_json, itm.To_str(json_wtr));
	}
	public void Test__verify(boolean mode_is_import, String url, boolean expd_dirty, Xowdir_wiki_json expd) {
		Xowdir_wiki_json actl = mgr.Verify(mode_is_import, "", Io_url_.new_any_(url));

		String expd_str = expd.To_str(json_wtr);
		String actl_str = actl.To_str(json_wtr);
		Gftest.Eq__ary__lines(expd_str, actl_str, "expd");
		Gftest.Eq__bool(expd_dirty, mgr.Dirty(), "dirty");
	}
	public void Test__wiki_cfg(Xowdir_wiki_json expd) {
		String expd_str = expd.To_str(json_wtr);
		Gftest.Eq__ary__lines(expd_str, mgr.Wiki_cfg__select_or(Xowdir_wiki_cfg_.Key__wiki_json, null), "wiki_cfg");
	}
//			Gftest.Eq__ary__lines(expd_str, mgr.User_reg__select(actl.Domain()), "user_reg");
}
