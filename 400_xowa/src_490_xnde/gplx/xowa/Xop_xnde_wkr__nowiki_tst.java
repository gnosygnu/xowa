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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xop_xnde_wkr__nowiki_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str
			(	"<nowiki>''a''</nowiki>b"
			,	"''a''b"
			);
	}
	@Test  public void Template() {
		fxt.Init_para_y_();
		fxt.Init_defn_add("nowiki_test", "<nowiki>#</nowiki>a");
		fxt.Test_parse_page_all_str
			(	"{{nowiki_test}}"
			,	String_.Concat_lines_nl_skip_last
			(	"<p>#a"
			,	"</p>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void H2() {	// EX.WP:HTML
		fxt.Test_parse_page_all_str
			(	"a<nowiki><h1>b<h6></nowiki>c"
			,	String_.Concat_lines_nl_skip_last
			(	"a&lt;h1&gt;b&lt;h6&gt;c"
			));
	}
	@Test  public void Lnke() {	// EX.WP: Doomsday argument; <nowiki>[0,&nbsp;1]</nowiki>
		fxt.Test_parse_page_wiki_str("a <nowiki>[0,&nbsp;1]</nowiki> b", "a [0,&nbsp;1] b");	// NOTE: not "0" + Byte_.XtoStr(160) + "1"; depend on browser to translate &nbsp;
	}
	@Test  public void Xatrs_val_text() {
		fxt.Test_parse_page_all_str
			(	"<div id=<nowiki>a</nowiki>>b</div>"
			,	String_.Concat_lines_nl_skip_last
			(	"<div id=\"a\">b</div>"
			));
	}
	@Test  public void Xatrs_val_quote() {
		fxt.Test_parse_page_all_str
			(	"<div id='a<nowiki>b</nowiki>c'>d</div>"
			,	String_.Concat_lines_nl_skip_last
			(	"<div id='abc'>d</div>"
			));
	}
	@Test  public void Xatrs_eq() {
		fxt.Test_parse_page_all_str("<ul id<nowiki>=</nowiki>\"a\" class<nowiki>=</nowiki>\"b\"><li><span class=\"c\">d</li></ul>", String_.Concat_lines_nl_skip_last
			(	"<ul id=\"a\" class=\"b\">"
			,	"<li><span class=\"c\">d</span></li></ul>"
			));
	}
	@Test  public void Tblw_atr() {// PURPOSE: nowiki breaks token
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|style=\"background-color:<nowiki>#</nowiki>FFCC99\""
			,	"|a"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table style=\"background-color:#FFCC99\">"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void Pre() {	// PURPOSE: nowikis inside pre should be ignored; DATE:2013-03-30
		fxt.Test_parse_page_all_str("<pre>a<nowiki>&lt;</nowiki>b</pre>"				, "<pre>a&lt;b</pre>");								// basic
		fxt.Test_parse_page_all_str("<pre>a<nowiki>&lt;<nowiki>b</pre>"					, "<pre>a&lt;nowiki&gt;&lt;&lt;nowiki&gt;b</pre>");	// not closed
		fxt.Test_parse_page_all_str("<pre><nowiki>a<nowiki>b</nowiki>c</nowiki></pre>"	, "<pre>&lt;nowiki&gt;abc&lt;/nowiki&gt;</pre>");	// nested; this is wrong, but leave for now; should be a<nowiki>b</nowiki>c
	}
	@Test  public void Code() {	// PURPOSE.fix:HtmlNcr-escaped refs were being ignored; caused by HtmlTidy fix for frwiki templates;DATE:2013-06-27
		fxt.Test_parse_page_all_str("<code><nowiki>|:</nowiki></code>", "<code>|:</code>");
	}
}
