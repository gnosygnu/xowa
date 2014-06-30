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
import org.junit.*;
public class Xoa_url_tst {
	Xoa_url_fxt fxt = new Xoa_url_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Eq_page() {
		fxt.Eq_page_tst(fxt.url_("en.wikipedia.org", "Earth", false), fxt.url_("en.wikipedia.org", "Earth", false), true);
		fxt.Eq_page_tst(fxt.url_("en.wikipedia.org", "Earth", false), fxt.url_("en.wikipedia.org", "Earth", true ), false);
	}
}
class Xoa_url_fxt {
	public void Clear() {}
	public Xoa_url url_(String wiki_str, String page_str, boolean redirect_force) {return new Xoa_url().Wiki_bry_(Bry_.new_utf8_(wiki_str)).Page_bry_(Bry_.new_utf8_(page_str)).Redirect_force_(redirect_force);}
	public void Eq_page_tst(Xoa_url lhs, Xoa_url rhs, boolean expd) {Tfds.Eq(expd, lhs.Eq_page(rhs));}
}
