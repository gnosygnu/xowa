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
package gplx.xowa; import gplx.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoae_page__tst {
	private final    Xoae_page__fxt fxt = new Xoae_page__fxt();
	@Test  public void Ttl() {
		fxt.Init__page("Abc");
		fxt.Exec__ttl("Заглавная_страница");
		fxt.Test__url("en.wikipedia.org/wiki/Заглавная_страница");	// fails if "%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0"; DATE:2016-11-25
	}
}
class Xoae_page__fxt {
	private Xoae_page page;
	private Xowe_wiki wiki;
	public Xoae_page__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public void Init__page(String ttl) {
		page = Xoae_page.New(wiki, wiki.Ttl_parse(Bry_.new_u8(ttl)));
	}
	public void Exec__ttl(String raw) {
		page.Ttl_(wiki.Ttl_parse(Bry_.new_u8(raw)));
	}
	public void Test__url(String expd) {
		Gftest.Eq__str(expd, page.Url().To_str(), "url");
	}
}
