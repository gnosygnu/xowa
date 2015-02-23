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
package gplx.xowa.html.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.xowa.wikis.*; import gplx.xowa.xtns.wdatas.*;
public class Xoh_page_body_cls_tst {
	@Before public void init() {} private Xoh_page_body_cls_fxt fxt = new Xoh_page_body_cls_fxt();
	@Test   public void Escape_cls() {
		fxt.Test_escape_cls("0123456789", "0123456789");											// noop: num
		fxt.Test_escape_cls("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");			// noop: ucase
		fxt.Test_escape_cls("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz");			// noop: lcase
		fxt.Test_escape_cls("!\"#$%&'()*+,.\\/:;<=>?@[]^`{|}~", "______________________________");	// underline: syms
		fxt.Test_escape_cls("a.bcd..ef.", "a_bcd__ef_");											// letters + syms
		fxt.Test_escape_cls("a__b___c"	, "a_b_c");													// multiple underlines
		fxt.Test_escape_cls("a b", "a_b");															// nbsp
	}
	@Test   public void Calc() {
		fxt.Test_calc(Xow_page_tid.Tid_wikitext	, "A"							, "ns-0 ns-subject page-A");
		fxt.Test_calc(Xow_page_tid.Tid_wikitext	, "Talk:A"						, "ns-1 ns-talk page-Talk_A");
		fxt.Test_calc(Xow_page_tid.Tid_wikitext	, "Wikipedia:Página principal"	, "ns-4 ns-subject page-Wikipedia_Página_principal");
		fxt.Test_calc(Xow_page_tid.Tid_json		, "Q2"							, "ns-0 ns-subject page-Q2 wb-entitypage wb-itempage wb-itempage-Q2");
		fxt.Test_calc(Xow_page_tid.Tid_json		, "Property:P1"					, "ns-120 ns-subject page-Property_P1 wb-entitypage wb-propertypage wb-propertypage-P1");
	}
}
class Xoh_page_body_cls_fxt {
	private Bry_bfr tmp_bfr; private Xoae_app app; private Xowe_wiki wiki;
	public void Test_escape_cls(String raw, String expd) {
		Tfds.Eq(expd, String_.new_utf8_(Xoh_page_body_cls.Escape_cls(Bry_.new_utf8_(raw))));
	}
	public void Test_calc(byte page_tid, String ttl_str, String expd) {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			tmp_bfr = Bry_bfr.reset_(255);
			wiki.Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, Wdata_wiki_mgr.Ns_property_name);
		}
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_utf8_(ttl_str));
		Tfds.Eq(expd, String_.new_utf8_(Xoh_page_body_cls.Calc(tmp_bfr, ttl, page_tid)));
	}
}
