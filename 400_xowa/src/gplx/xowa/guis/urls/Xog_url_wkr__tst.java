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
package gplx.xowa.guis.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import org.junit.*; import gplx.core.tests.*;
public class Xog_url_wkr__tst {
	private final    Xog_url_wkr__fxt fxt = new Xog_url_wkr__fxt();
	@Test   public void Basic() {
		fxt.Exec__parse("/wiki/A?k1=B%26C");
		fxt.Test__raw("/wiki/A?k1=B%26C");
	}
	@Test   public void Anch_early() {	// de.wikipedia.org/wiki/Kategorie:Begriffskl%C3%A4rung?pagefrom=#::12%20PANZERDIVISION#mw-pages
		fxt.Exec__parse("/wiki/A?pagefrom=%23%3A7p#mw-pages");
		fxt.Test__qarg("?pagefrom=#:7p");
		fxt.Test__anch("mw-pages");
	}		
}
class Xog_url_wkr__fxt {
	private final    Xowe_wiki wiki;
	private final    Xog_url_wkr wkr = new Xog_url_wkr();
	private Xoa_url url;
	public Xog_url_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wkr.Init(wiki);
	}
	public void Exec__parse(String href) {
		this.url = wiki.Utl__url_parser().Parse(Bry_.new_u8(href));
		this.url = wkr.Exec_url(url);
	}
	public void Test__raw(String expd)	{Gftest.Eq__str(expd, String_.new_u8(url.Raw()));}
	public void Test__qarg(String expd) {Gftest.Eq__str(expd, String_.new_u8(url.Qargs_mgr().To_bry()));}
	public void Test__anch(String expd) {Gftest.Eq__str(expd, String_.new_u8(url.Anch_bry()));}
}
