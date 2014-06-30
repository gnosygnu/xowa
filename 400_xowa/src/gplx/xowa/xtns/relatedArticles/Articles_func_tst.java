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
package gplx.xowa.xtns.relatedArticles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Articles_func_tst {
	@Before public void init()				{fxt.Reset();} private Articles_func_fxt fxt = new Articles_func_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("{{#related:A1 && A 1}}{{#related:B1 && B 1}}", String_.Concat_lines_nl_skip_last
		(  "<div class=\"portal\" role=\"navigation\" id=\"p-relatedarticles\">"
		, "  <h3>Related articles</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/A1\">A 1</a></li>"
		, "      <li class=\"interwiki-relart\"><a href=\"/wiki/B1\">B 1</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));	
	}
}
class Articles_func_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Wiki().Lang().Msg_mgr().Itm_by_key_or_new("relatedarticles-title", "Related articles");
		fxt.Reset();
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_page_all_str(raw, "");
		fxt.Page().Xtn_mgr().Exec();
		Tfds.Eq_str_lines(expd, fxt.Page().Html_data().Portal_div_xtn().XtoStrAndClear());
	}
}
