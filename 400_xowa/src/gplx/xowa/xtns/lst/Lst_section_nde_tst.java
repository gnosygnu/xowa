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
import org.junit.*; import gplx.xowa.langs.*;
public class Lst_section_nde_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_all_str("a<section name=\"b\">c</section>d", "ad");
	}
	@Test  public void German() {	// PURPOSE: non-english tags for section DATE:2014-07-18
		fxt.Lang_by_id_(Xol_lang_itm_.Id_de);
		fxt.Test_parse_page_all_str("a<abschnitt name=\"b\">c</abschnitt>d"	, "ad");		// check that German works
		fxt.Test_parse_page_all_str("a<section name=\"b\">c</section>d"		, "ad");		// check that English still works
		fxt.Test_parse_page_all_str("a<trecho name=\"b\">c</trecho>d"		, "a&lt;trecho name=&quot;b&quot;&gt;c&lt;/trecho&gt;d");		// check that Portuguese does not work
	}
}
