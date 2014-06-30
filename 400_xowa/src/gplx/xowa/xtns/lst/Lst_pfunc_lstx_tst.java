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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Lst_pfunc_lstx_tst {
	private Lst_pfunc_lst_fxt fxt = new Lst_pfunc_lst_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|key0}}", "a b val1 c");
	}
	@Test  public void Replace() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|key0|val3}}", "aval3 b val1 c");
	}
	@Test  public void Section_is_empty() {
		fxt.Page_txt_("a<section begin=key0/> val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c").Test_lst("{{#lstx:section_test|}}", "a val0 b val1 c");
	}
	@Test  public void Missing_bgn_end() {
		fxt.Page_txt_("a<section begin=key0/> b<section end=key0/> c").Test_lst("{{#lstx:section_test}}", "a b c");
	}
}
