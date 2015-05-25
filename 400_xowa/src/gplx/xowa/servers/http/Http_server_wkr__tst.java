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
package gplx.xowa.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
import org.junit.*;
public class Http_server_wkr__tst {
	@Before public void init() {fxt.Clear();} private Http_server_wkr__fxt fxt = new Http_server_wkr__fxt();
	@Test  public void Assert_main_page() {
		fxt.Init_wiki_main_page("fr.wikiversity.org", "Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/"		, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki"	, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki/"	, "/fr.wikiversity.org/wiki/Accueil");
		fxt.Test_assert_main_page("/fr.wikiversity.org/wiki/A"	, "/fr.wikiversity.org/wiki/A");
	}
}
class Http_server_wkr__fxt {
	private Xoae_app app;
	public void Clear() {
		this.app = Xoa_app_fxt.app_();
	}
	public void Init_wiki_main_page(String domain, String main_page) {
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(Bry_.new_u8(domain));
		wiki.Props().Main_page_(Bry_.new_u8(main_page));
	}
	public void Test_assert_main_page(String url, String expd) {
		Tfds.Eq(expd, Http_server_wkr_.Assert_main_page(app, url));
	}
}
