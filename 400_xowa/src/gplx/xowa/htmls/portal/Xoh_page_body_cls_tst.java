/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.wikis.*; import gplx.xowa.xtns.wbases.*;
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
		Tfds.Eq(expd, String_.new_u8(Xoh_page_body_cls.Escape_cls(Bry_.new_u8(raw))));
	}
	public void Test_calc(byte page_tid, String ttl_str, String expd) {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			tmp_bfr = Bry_bfr_.Reset(255);
			wiki.Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, Wdata_wiki_mgr.Ns_property_name);
		}
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str));
		Tfds.Eq(expd, String_.new_u8(Xoh_page_body_cls.Calc(tmp_bfr, ttl, page_tid)));
	}
}
