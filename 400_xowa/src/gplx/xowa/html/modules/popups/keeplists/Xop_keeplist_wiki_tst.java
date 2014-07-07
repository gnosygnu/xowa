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
package gplx.xowa.html.modules.popups.keeplists; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*; import gplx.xowa.html.modules.popups.*;
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
		Xoa_app app = Xoa_app_fxt.app_();
		Xow_wiki wiki = Xoa_app_fxt.wiki_(app, "enwiki");
		Xow_popup_mgr popup_mgr = wiki.Html_mgr().Module_mgr().Popup_mgr();
		popup_mgr.Init_by_wiki(wiki);
		popup_mgr.Parser().Tmpl_keeplist_init_(Bry_.new_utf8_(raw));
		Xop_keeplist_wiki rv = popup_mgr.Parser().Tmpl_keeplist();
		return rv;
	}
	public void Test_Match_y(Xop_keeplist_wiki keeplist_wiki, String... itms) {Test_Match(keeplist_wiki, itms, Bool_.Y);}
	public void Test_Match_n(Xop_keeplist_wiki keeplist_wiki, String... itms) {Test_Match(keeplist_wiki, itms, Bool_.N);}
	private void Test_Match(Xop_keeplist_wiki keeplist_wiki, String[] itms, boolean expd) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			String itm = itms[i];
			Tfds.Eq(expd, keeplist_wiki.Match(Bry_.new_utf8_(itm)), "itm={0} expd={1}", itm, expd);
		}
	}
}
