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
package gplx.xowa.urls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xoa_url__basic__tst {
	private final Xoa_url_fxt fxt = new Xoa_url_fxt();
	@Test  public void Eq_page() {
		fxt.Test_eq_page(Bool_.Y, "en.wikipedia.org/wiki/A?redirect=yes", "en.wikipedia.org/wiki/A?redirect=yes");
		fxt.Test_eq_page(Bool_.N, "en.wikipedia.org/wiki/A?redirect=no"	, "en.wikipedia.org/wiki/A?redirect=yes");
	}
}
class Xoa_url_fxt extends Xoa_url_parser_fxt { 	public void Test_eq_page(boolean expd, String lhs_str, String rhs_str) {
		Xoa_url lhs_url = parser.Parse(Bry_.new_u8(lhs_str));
		Xoa_url rhs_url = parser.Parse(Bry_.new_u8(rhs_str));
		Tfds.Eq_bool(expd, lhs_url.Eq_page(rhs_url), "Eq_page");
	}
}
