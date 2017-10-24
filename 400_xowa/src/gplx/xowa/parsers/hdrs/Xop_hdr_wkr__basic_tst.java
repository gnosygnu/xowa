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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_hdr_wkr__basic_tst {
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void H2()							{fxt.Test_parse_page_wiki_str("==a=="				, "<h2>a</h2>\n");}
	@Test  public void H3()							{fxt.Test_parse_page_wiki_str("===a==="				, "<h3>a</h3>\n");}
	@Test  public void H6_limit()					{fxt.Test_parse_page_wiki_str("=======a======="		, "<h6>=a=</h6>\n");}
	@Test  public void Mismatch_bgn()				{fxt.Test_parse_page_wiki_str("=====a=="			, "<h2>===a</h2>\n");}
	@Test  public void Mismatch_end()				{fxt.Test_parse_page_wiki_str("==a====="			, "<h2>a===</h2>\n");}
	@Test  public void Dangling()					{fxt.Test_parse_page_wiki_str("==a"					, "==a");}
	@Test  public void Comment_bgn()				{fxt.Test_parse_page_all_str ("<!--b-->==a=="		, "<h2>a</h2>\n");}
	@Test  public void Comment_end()				{fxt.Test_parse_page_all_str ("==a==<!--b-->"		, "<h2>a</h2>\n");}
	@Test  public void Ws_end() {	// PURPOSE: "==\n" merges all ws following it; \n\n\n is not transformed by Para_wkr to "<br/>"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"==a== \t"
		,	""
		,	""
		,	""
		,	"b"
		), String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	"b"
		));
	}
	@Test  public void Many() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"==a=="
		,	"===b==="
		), String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	""
		,	"<h3>b</h3>"
		,	""
		));
	}
	@Test  public void Hdr_w_tblw() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"==a=="
		,	"{|"
		,	"|+"
		,	"|}"
		), String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	"<table>"
		,	"  <caption>"
		,	"  </caption>"
		,	"</table>"
		,	""
		));
	}
	@Test  public void Hdr_w_hr() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"==a=="
		,	"----"
		), String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	"<hr/>"
		));
	}
	@Test  public void Mix_apos_dangling()			{fxt.Test_parse_page_wiki_str("==''a=="				, "<h2><i>a</i></h2>\n");}
	@Test  public void Mix_xnde_dangling()			{fxt.Test_parse_page_wiki_str("==<i>a=="			, "<h2><i>a</i></h2>\n");}
	@Test  public void Mix_tblw_cell()				{fxt.Test_parse_page_wiki_str("==a!!=="				, "<h2>a!!</h2>\n");}
	@Test  public void Ws()							{fxt.Test_parse_page_wiki_str("== a b =="			, "<h2> a b </h2>\n");}
	@Test  public void Err_hdr()					{fxt.Init_log_(Xop_hdr_log.Mismatched)					.Test_parse_page_wiki_str("====a== =="	, "<h2>==a== </h2>\n").tst_Log_check();}
	@Test  public void Err_end_hdr_is_1()			{fxt.Init_log_(Xop_hdr_log.Mismatched, Xop_hdr_log.Len_1).Test_parse_page_wiki_str("==a="			, "<h1>=a</h1>\n").tst_Log_check();}
	@Test  public void Html_hdr_many() {
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		fxt.Test_parse_page_wiki_str__esc(String_.Concat_lines_nl_skip_last
		(	"==a=="
		,	"==a=="
		,	"==a=="
		), String_.Concat_lines_nl_skip_last
		(	"<h2><span class='mw-headline' id='a'>a</span></h2>"
		,	""
		,	"<h2><span class='mw-headline' id='a_2'>a</span></h2>"
		,	""
		,	"<h2><span class='mw-headline' id='a_3'>a</span></h2>"
		,	""
		));
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	@Test  public void Hdr_inside_dangling_tmpl_fix() {	// PURPOSE: one-off fix to handle == inside dangling tmpl; DATE:2014-02-11
		fxt.Test_parse_page_all_str("{{a|}\n==b=="
		, String_.Concat_lines_nl_skip_last
		(	"{{a|}"
		,	""
		,	"<h2>b</h2>"
		,	""
		));
	}
	@Test  public void Pfunc() {// multiple = should not be interpreted as key-val equals; PAGE:en.w:Wikipedia:Picture_of_the_day/June_2014 DATE:2014-07-21
		fxt.Test_parse_page_all_str
		( "{{#if:exists|==a==|no}}"
		, String_.Concat_lines_nl_skip_last
		( "<h2>a</h2>"
		, ""
		));
	}
//		@Test  public void Hdr_inside_dangling_tmpl_fix_2() {	// PURPOSE: hdr == inside dangling tmpl; DATE:2014-06-10
//			fxt.Init_defn_add("Print", "{{{1}}}");
//			fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
//			( "=={{Print|b=="
//			, "}}"
//			), String_.Concat_lines_nl_skip_last
//			(	"==b="
//			,	""
//			));
//		}
}
