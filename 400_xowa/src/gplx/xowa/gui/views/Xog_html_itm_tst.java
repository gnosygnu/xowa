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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import org.junit.*;
public class Xog_html_itm_tst {
	@Before public void init() {fxt.Clear();} private Xog_html_itm_fxt fxt = new Xog_html_itm_fxt();
	@Test   public void Extract_href__text() {
		fxt.Test_extract_href("0|"													, "");
		fxt.Test_extract_href("1|selected_text"										, "selected_text");
		fxt.Test_extract_href("2|http://a.org"										, "http://a.org");
	}
	@Test   public void Extract_href__file() {
		fxt.Test_extract_href("2|file:///site/en.wiktionary.org/wiki/Page_1"		, "en.wiktionary.org/wiki/Page_1");
		fxt.Test_extract_href("2|file:///wiki/Page_2"								, "en.wikipedia.org/wiki/Page_2");
		fxt.Test_extract_href("2|file://#anchor"									, "en.wikipedia.org/wiki/Page_0#anchor");
	}
	@Test   public void Extract_href__internal() {	// 
		fxt.Test_extract_href("2|/site/en.wiktionary.org/wiki/Page_1"				, "en.wiktionary.org/wiki/Page_1");
		fxt.Test_extract_href("2|/wiki/Page_2"										, "en.wikipedia.org/wiki/Page_2");
		fxt.Test_extract_href("2|#anchor"											, "en.wikipedia.org/wiki/Page_0#anchor");
	}
}
class Xog_html_itm_fxt {
	public void Clear() {
		cur_wiki = "en.wikipedia.org";
		cur_page = "Page_0";
	}
	public String Cur_wiki() {return cur_wiki;} public Xog_html_itm_fxt Cur_wiki_(String v) {cur_wiki = v; return this;} private String cur_wiki;
	public String Cur_page() {return cur_page;} public Xog_html_itm_fxt Cur_page_(String v) {cur_page = v; return this;} private String cur_page;
	public void Test_extract_href(String text_str, String expd) {
		Tfds.Eq(expd, Xog_html_itm__href_extractor.Html_extract_text(cur_wiki, cur_page, text_str));
	}
}
