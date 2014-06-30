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
public class Xop_xnde_wkr__include_basic_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()							{fxt.Reset();}
	@Test  public void Tmpl_includeonly()				{fxt.Test_parse_tmpl_str_test("a<includeonly>b</includeonly>c"						, "{{test}}", "abc");}
	@Test  public void Tmpl_noinclude()					{fxt.Test_parse_tmpl_str_test("a<noinclude>b</noinclude>c"							, "{{test}}", "ac");}
	@Test  public void Tmpl_onlyinclude()				{fxt.Test_parse_tmpl_str_test("a<onlyinclude>b</onlyinclude>c"						, "{{test}}", "b");}
	@Test  public void Tmpl_onlyinclude_nest()			{fxt.Test_parse_tmpl_str_test("{{#ifeq:y|y|a<onlyinclude>b</onlyinclude>c|n}}"		, "{{test}}", "b");}	// PURPOSE: check that onlyinclude handles (a) inside {{#if}} function (old engine did not); and (b) that abc are correctly added together
	@Test  public void Tmpl_onlyinclude_page() {// PURPOSE: handle scenario similar to {{FA Number}} where # of articles is buried in page between onlyinclude tags; added noinclude as additional stress test
		fxt.Init_page_create("Transclude_1", "<noinclude>a<onlyinclude>b</onlyinclude>c</noinclude>d");
		fxt.Test_parse_tmpl_str_test("{{:Transclude_1}}"		, "{{test}}", "b");
	}
	@Test  public void Tmpl_onlyinclude_page2() {	// PURPOSE: handle scenario similar to PS3 wherein onlyinclude was being skipped (somewhat correctly) but following text (<pre>) was also included
		fxt.Init_page_create("Transclude_2", "a<onlyinclude>b<includeonly>c</includeonly>d</onlyinclude>e<pre>f</pre>g");
		fxt.Test_parse_tmpl_str_test("{{:Transclude_2}}"		, "{{test}}", "bcd");
	}
	@Test  public void Tmpl_noinclude_unmatched() {	// PURPOSE.fix: ignore unmatched </noinclude>; EX:fi.w:Sergio_Leone; DATE:2014-05-02
		fxt.Test_parse_tmpl_str_test("{{{1|</noinclude>}}}", "{{test|a}}", "a");	// was "{{{test|"
	}

	@Test  public void Wiki_includeonly()	{fxt.Test_parse_page_all_str("a<includeonly>b</includeonly>c"								, "ac");}
	@Test  public void Wiki_noinclude()		{fxt.Test_parse_page_all_str("a<noinclude>b</noinclude>c"									, "abc");}
	@Test  public void Wiki_onlyinclude()	{fxt.Test_parse_page_all_str("a<onlyinclude>b</onlyinclude>c"								, "abc");}
	@Test  public void Wiki_oi_io()			{fxt.Test_parse_page_all_str("a<onlyinclude>b<includeonly>c</includeonly>d</onlyinclude>e"	, "abde");}
	@Test  public void Wiki_oi_io_tblw() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"<onlyinclude>"
			,	"{|"
			,	"|-"
			,	"|a<includeonly>"
			,	"|}</includeonly></onlyinclude>"
			,	"|-"
			,	"|b"
			,	"|}"
			),	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"  <tr>"
			,	"    <td>b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
}
/*
<includeonly>-({{{1}}}={{{1}}}round-5)-({{{1}}}={{{1}}}round-4)-({{{1}}}={{{1}}}round-3)-({{{1}}}={{{1}}}round-2)-({{{1}}}={{{1}}}round-1)</includeonly><noinclude>
{{pp-template}}Called by {{lt|precision/0}}</noinclude>

==includeonly==
main: a<includeonly>b</includeonly>c<br/>
tmpl: {{mwo_include_only|a|b|c}}

==noinclude==
main: a<noinclude>b</noinclude>c<br/>
tmpl: {{mwo_no_include|a|b|c}}

==onlyinclude==
main: a<onlyinclude>b</onlyinclude>c<br/>
tmpl: {{mwo_only_include|a|b|c}}
*/