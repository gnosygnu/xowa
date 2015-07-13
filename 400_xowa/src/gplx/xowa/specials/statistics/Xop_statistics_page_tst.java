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
package gplx.xowa.specials.statistics; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*;
public class Xop_statistics_page_tst {
@Before public void init() {fxt.Clear();} private Xop_statistics_page_fxt fxt = new Xop_statistics_page_fxt();
	@Test   public void Basic() {
		fxt.Test_html(String_.Concat_lines_nl_skip_last
		(  "<div id=\"mw-content-text\">"
		,	"<table class=\"wikitable mw-statistics-table\">"
		,	"  <tr>"
		,	"    <th colspan=\"2\">Page statistics</th>"
		,	"  </tr>"
		,	"  <tr class=\"mw-statistics-articles\">"
		,	"    <td>Content pages</td>"
		,	"    <td class=\"mw-statistics-numbers\" style='text-align:right'>0</td>"
		,	"  </tr>"
		,	"  <tr class=\"mw-statistics-pages\">"
		,	"    <td>Pages<br /><small class=\"mw-statistic-desc\"> All pages in the wiki, including talk pages, redirects, etc.</small></td>"
		,	"    <td class=\"mw-statistics-numbers\" style='text-align:right'>0</td>"
		,	"  </tr>"
		,	"  <tr>"
		,	"    <th colspan=\"2\">Namespace statistics</th>"
		,	"  </tr>"
		,	"</table>"
		,	"</div>"
		));
	}
}
class Xop_statistics_page_fxt {
	public void Clear() {
		parser_fxt = new Xop_fxt();
		parser_fxt.Reset();
		wiki = parser_fxt.Wiki();
		special_page = wiki.Special_mgr().Page_statistics();
	}	private Xop_fxt parser_fxt; private Xop_statistics_page special_page; private Xowe_wiki wiki;
	public void Test_html(String expd) {
		Tfds.Eq_str_lines(expd, String_.new_u8(special_page.Build_html(wiki)));
	}
}
