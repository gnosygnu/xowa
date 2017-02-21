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
package gplx.xowa.htmls.modules.popups.keeplists; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*; import gplx.xowa.htmls.modules.popups.*;
import org.junit.*;
public class Xop_keeplist_wiki_tst {
	@Before public void init() {fxt.Clear();} private Xop_keeplist_wiki_fxt fxt = new Xop_keeplist_wiki_fxt();
	@Test  public void Tmpl_keeplist() {
		Xop_keeplist_wiki keeplist_wiki = fxt.keeplist_wiki_(String_.Concat_lines_nl
		( "enwiki|a*|abc*"
		));
		fxt.Test_Match_y(keeplist_wiki, "a", "ab");
		fxt.Test_Match_n(keeplist_wiki, "abc", "abcd", "d");
	}
	@Test  public void Tmpl_keeplist2() {
		Xop_keeplist_wiki keeplist_wiki = fxt.keeplist_wiki_(String_.Concat_lines_nl
		( "enwiki|a*|abc*"
		, "enwiki|b*|*xyz"
		));
		fxt.Test_Match_y(keeplist_wiki, "a", "ab");
		fxt.Test_Match_n(keeplist_wiki, "d", "abc", "abcd");
		fxt.Test_Match_y(keeplist_wiki, "b", "bxy");
		fxt.Test_Match_n(keeplist_wiki, "bxyz", "bcdxyz");
	}
}
class Xop_keeplist_wiki_fxt {
	public void Clear() {
	}
	public Xop_keeplist_wiki keeplist_wiki_(String raw) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "enwiki");
		Xow_popup_mgr popup_mgr = wiki.Html_mgr().Head_mgr().Popup_mgr();
		popup_mgr.Init_by_wiki(wiki);
		popup_mgr.Parser().Tmpl_keeplist_init_(Bry_.new_u8(raw));
		Xop_keeplist_wiki rv = popup_mgr.Parser().Tmpl_keeplist();
		return rv;
	}
	public void Test_Match_y(Xop_keeplist_wiki keeplist_wiki, String... itms) {Test_Match(keeplist_wiki, itms, Bool_.Y);}
	public void Test_Match_n(Xop_keeplist_wiki keeplist_wiki, String... itms) {Test_Match(keeplist_wiki, itms, Bool_.N);}
	private void Test_Match(Xop_keeplist_wiki keeplist_wiki, String[] itms, boolean expd) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			String itm = itms[i];
			Tfds.Eq(expd, keeplist_wiki.Match(Bry_.new_u8(itm)), "itm={0} expd={1}", itm, expd);
		}
	}
}
