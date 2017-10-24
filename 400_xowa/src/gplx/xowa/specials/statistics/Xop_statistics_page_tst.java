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
