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
package gplx.xowa.xtns.syntax_highlights; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Synh_xtn_nde_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test   public void Basic() {
		fxt.Test_parse_page_all_str("<syntaxHighlight>abc</syntaxHighlight>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">abc</pre></div>");
	}
	@Test   public void Text() {
		fxt.Test_parse_page_all_str("<syntaxHighlight lang=\"text\">abc</syntaxHighlight>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">abc</pre></div>");
	}
	@Test   public void Style_pre() {
		fxt.Test_parse_page_all_str("<syntaxHighlight style=\"color:red;\">abc</syntaxHighlight>", "<div class=\"mw-highlight\" style=\"color:red;\"><pre style=\"overflow:auto\">abc</pre></div>");
	}
	@Test   public void Style_code() {
		fxt.Test_parse_page_all_str("<syntaxHighlight lang=\"text\" style=\"color:red;\">abc</syntaxHighlight>", "<div class=\"mw-highlight\" style=\"color:red;\"><pre style=\"overflow:auto\">abc</pre></div>");
	}
	@Test   public void Trim_ws() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight>"
		,	"abc"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"abc"
		,	"</pre></div>"
		));
	}
	@Test   public void Line() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight line>"
		,	"a"
		,	"b"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"<span style=\"-moz-user-select:none;\">1 </span><span>a</span>"
		,	"<span style=\"-moz-user-select:none;\">2 </span><span>b</span>"
		,	"</pre></div>"
		));
	}
	@Test   public void Start() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight line start=3>"
		,	"a"
		,	"b"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"<span style=\"-moz-user-select:none;\">3 </span><span>a</span>"
		,	"<span style=\"-moz-user-select:none;\">4 </span><span>b</span>"
		,	"</pre></div>"
		));
	}
	@Test   public void Highlight() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight line highlight='1,3'>"
		,	"a"
		,	"b"
		,	"c"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"<span style=\"-moz-user-select:none;\">1 </span><span style=\"background-color: #FFFFCC;\">a</span>"
		,	"<span style=\"-moz-user-select:none;\">2 </span><span>b</span>"
		,	"<span style=\"-moz-user-select:none;\">3 </span><span style=\"background-color: #FFFFCC;\">c</span>"
		,	"</pre></div>"
		));
	}
	@Test   public void Enclose_none() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight enclose=none style='color:red'>"
		,	"a"
		,	"b"
		,	"c"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<code class=\"mw-highlight\" style=\"color:red\">"
		,	"a"
		,	"b"
		,	"c"
		,	"</code>"
		));
	}
	@Test   public void Line_padded() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight line start=9>"
		,	"a"
		,	"b"
		,	"</syntaxHighlight>"
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"<span style=\"-moz-user-select:none;\"> 9 </span><span>a</span>"
		,	"<span style=\"-moz-user-select:none;\">10 </span><span>b</span>"
		,	"</pre></div>"
		));
	}
	@Test  public void EndTag_has_ws() {	// PURPOSE: </syntaxhighlight > not being closed correctly; PAGE:en.w:Mergesort; updated; DATE:2014-06-24
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	"<syntaxhighlight>"
		,	"b"
		,	"</syntaxhighlight >"
		,	"c"
		,	"<syntaxhighlight>"
		,	"d"
		,	"</syntaxhighlight>"
		), String_.Concat_lines_nl_skip_last
		(	"<p>a"
		,	"</p>"
		,	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"b"
		,	"</pre></div>"
		,	""
		,	"<p>c"
		,	"</p>"
		,	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"d"
		,	"</pre></div>"
		,	""
		));
		fxt.Init_para_n_();
	}
	@Test   public void Trim_ws_from_end_tab() {// PURPOSE: trim ws between "abc" and "</syntaxhighlight"; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"<syntaxHighlight>"
		,	"abc"
		,	"  </syntaxHighlight>"	// trim ws here
		), String_.Concat_lines_nl
		(	"<div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	"abc"
		,	"</pre></div>"
		));
	}
 		@Test   public void Pre() {// PURPOSE: handle pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"a"
		,   ""
		,	" <syntaxHighlight>"
		,	" b"
		,	" </syntaxHighlight>"	// trim ws here
		,   ""
		,	"c"
		), String_.Concat_lines_nl
		(	"<p>a"
		,	"</p>"
		,	" <div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		,	" b"
		,	"</pre></div>"
		,	""
		,	"<p>c"
		,	"</p>"
		));
		fxt.Init_para_n_();
	}
}
