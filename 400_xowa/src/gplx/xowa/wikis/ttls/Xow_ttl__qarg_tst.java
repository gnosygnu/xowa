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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ttl__qarg_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Base_txt_wo_qarg() {
		fxt.Init_ttl("Special:Search/A?b=c").Expd_base_txt_wo_qarg("Search").Expd_qarg_txt("b=c").Test();
	}
	@Test   public void Leaf_txt_wo_qarg() {
		fxt.Init_ttl("Special:Search/A?b=c").Expd_leaf_txt_wo_qarg("A").Expd_qarg_txt("b=c").Test();
	}
	@Test   public void Ttl_has_question_mark() {	// PURPOSE: handle titles that have both question mark and leaf; PAGE:en.w:Portal:Organized_Labour/Did_You_Know?/1 DATE:2014-06-08
		fxt.Init_ttl("A/B?/1").Expd_page_txt("A/B?/1").Expd_base_txt("A/B?").Expd_leaf_txt("1").Expd_leaf_txt_wo_qarg("1").Expd_qarg_txt(null).Test();
	}
}
