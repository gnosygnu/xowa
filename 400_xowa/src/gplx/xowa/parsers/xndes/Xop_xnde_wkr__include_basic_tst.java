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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xnde_wkr__include_basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
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

==includeonly -- aka: do not eval in template ==
main: a<includeonly>b</includeonly>c<br/>
tmpl: {{mwo_include_only|a|b|c}}

==noinclude   -- aka: eval in template only==
main: a<noinclude>b</noinclude>c<br/>
tmpl: {{mwo_no_include|a|b|c}}

==onlyinclude -- aka: only include in template only (ignore everything else) ==
main: a<onlyinclude>b</onlyinclude>c<br/>
tmpl: {{mwo_only_include|a|b|c}}
*/