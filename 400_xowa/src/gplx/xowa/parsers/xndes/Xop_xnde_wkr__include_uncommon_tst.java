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
public class Xop_xnde_wkr__include_uncommon_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()							{fxt.Reset();}
	@Test  public void Ex_Tmpl_io_oi()		{		// PURPOSE: <includeonly> not parsing internals; PAGE:en.w:[[Template:MONTHNAME]]
		fxt.Test_parse_tmpl_str_test("<includeonly>{{#if:{{{1}}}|a|b}}</includeonly><noinclude>c</noinclude>", "{{test|1}}", "a");
	}
	@Test  public void Ex_Tmpl_io_subst()		{	// PURPOSE: <includeonly> and @gplx.Internal protected subst; PAGE:en.w:[[Template:Dubious]]
		fxt.Init_defn_clear();
		fxt.Init_defn_add("mwo_print", "{{{1}}}");
		fxt.Init_defn_add("substcheck", "SUBST");
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
			(	"{{mwo_print"
			,	"|<includeonly>{{subst:</includeonly><includeonly>substcheck}}</includeonly>"
			,	"}}"
			), "{{test}}"
			,	"{{subst:substcheck}}\n"
			);
		fxt.Reset();
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
			(	"{{mwo_print"
			,	"|<includeonly>{{safesubst:</includeonly><includeonly>substcheck}}</includeonly>"
			,	"}}"
			), "{{test}}"
			,	"SUBST\n");
		fxt.Init_defn_clear();
	}
	@Test  public void Ex_Tmpl_noinclude_prm_1() {	// PURPOSE: <noinclude> should not process @gplx.Internal protected tkns; PAGE:en.w:[[Template:See]]
		fxt.Init_defn_clear();
		fxt.Init_defn_add("mwo_print", "{{{1}}}{{{2}}}");
		fxt.Test_parse_tmpl_str_test
			(	"{{mwo_print|{{{1<noinclude>|not_seen</noinclude>}}}|{{{2}}}}}"
			,	"{{test|a|b}}"
			,	"ab"
			);
		fxt.Init_defn_clear();
	}
	@Test  public void Ex_Tmpl_noinclude_prm_2()	{	// PURPOSE: <noinclude> should not process default tkn;
		fxt.Test_parse_tmpl_str_test
			(	"{{#if: {{{x|<noinclude>y</noinclude>}}} | visible | hidden}}"	// {{#if: {{{x|<noinclude>y</noinclude>}}} -> {{#if: {{{x|}} -> hidden
			,	"{{test}}"
			,	"hidden"
			);
	}
	@Test  public void Ex_Tmpl_noinclude2() {	// PURPOSE: <noinclude> should be separate from tkns {{convert|50|km|0|abbr=on}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("mwo_print", "{{{1}}}{{{2}}}");
		fxt.Test_parse_tmpl_str_test
			(	"{{mwo_print<noinclude>{{{?}}}</noinclude>|a|b}}"
			,	"{{test}}"
			,	"ab"
			);
		fxt.Init_defn_clear();
	}
	@Test  public void Exception_incompleteTag_matchNext() {	// PURPOSE: "</noinclude" should not be matched;
		fxt.Test_parse_tmpl_str_test
			(	"a<noinclude>b</noinclude c<noinclude>d</noinclude>e"
			,	"{{test}}"
			,	"ae"
			);
	}
	@Test  public void Exception_noCloseTag() {
		fxt.Test_parse_tmpl_str_test
			(	"a<noinclude>bcde"
			,	"{{test}}"
			,	"a"
			);
	}
	@Test  public void Exception_inline() {
		fxt.Test_parse_tmpl_str_test
			(	"a<noinclude/>bcde"
			,	"{{test}}"
			,	"abcde"
			);
	}
	@Test  public void Exception_inline_2() {
		fxt.Test_parse_tmpl_str_test
			(	"a<noinclude/a/>bcde"
			,	"{{test}}"
			,	"a<noinclude/a/>bcde"
			);
	}
	@Test  public void Defect_onlyinclude_inside_template() {	// PURPOSE: was eating up next template; PAGE:en.w:Wikipedia:Featured_articles
		fxt.Test_parse_page_all_str
			(	"{{formatnum: <onlyinclude>1</onlyinclude>}} {{formatnum:2}}"
			,	"1 2"
			);
	}
	@Test  public void Only_include_preserves_nl() {	// PURPOSE: given "a\n<onlyinclude>{|\n", "{|" should be table; PAGE:en.w:Wikipedia:Reference_desk
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
			(	"a"
			,	"<onlyinclude>==b==</onlyinclude>"
			,	"c"
			)
//			,	"{{test}}"
			,	String_.Concat_lines_nl
			(	"a"
			,	""
			,	"<h2>b</h2>"
			,	"c"
			));
	}
	@Test  public void Only_include_interprets_template() {	// PURPOSE: <oi> should interpret templates
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "see_me");
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
			(	"a"
			,	"<onlyinclude>{{test}}</onlyinclude>"
			,	"c"
			)
			,	String_.Concat_lines_nl
			(	"a"
			,	"see_me"
			,	"c"
			));
	}
	@Test  public void Include_only_in_template_name() {// PURPOSE: includeonly in tmpl_name should be ignored; EX:de.w:Wikipedia:Projektdiskussion; DATE:2014-01-24
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "abc");
		fxt.Test_parse_page_all_str("{{<includeonly></includeonly>test}}", "abc");
	}
	@Test  public void Include_only_in_transcluded_page() {// PURPOSE: include only in transcluded page should be ignored; EX:de.w:Wikipedia:Projektdiskussion; DATE:2014-01-24; DATE:2014-05-10
		fxt.Init_page_create("page", "abc");	// create page in main ns
		fxt.Test_parse_page_all_str("{{:<includeonly>safesubst:</includeonly>page}}", "abc");	// will become {{:page}} which should then transclude page
	}
	@Test  public void Include_only_subst_in_function() {// PURPOSE: includeonly and subst inside function should be ignored; PAGE:en.w:WikiProject_Articles_for_creation/BLD_Preload; DATE:2014-04-29
		fxt.Test_parse_page_all_str("{{<includeonly>subst:</includeonly>#expr:0}}", "0");
	}
	@Test  public void Hdr() {	// PURPOSE: includeonly should be evaluated during template parse; EX: es.b:Billar/T�cnica/Clases_de_puentes; DATE:2014-02-12
		fxt.Test_parse_page_all_str("=<includeonly>=</includeonly>A=<includeonly>=</includeonly>", "<h1>A</h1>\n");
	}
//		@Test  public void Noinclude_nested() {	// PURPOSE: nested noincludes don't work; th.w:ISO_3166-1;DATE:2014-04-06
//			fxt.Init_defn_clear();
//			fxt.Init_defn_add("test", "a<noinclude>b<noinclude>c</noinclude>d</noinclude>e");
//			fxt.Test_parse_page_all_str("{{test}}", "ae");
//		}

//		@Test  public void Wiki_includeonly_ignore() {fxt.Test_parse_wiki_text("[[a<includeonly>b</includeonly>c]]", "[[ac]]");}	// FUTURE: ttl parses by idx, and ignores includeonly: WHEN: upon encountering; may need to redo in other parsers?
	@Test  public void Defect_noinclude_inside_main() {		// PURPOSE: <onlyinclude> inside main was not returning content; PAGE:en.w:Wikipedia:Featured_articles
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Test_tmpl", "{{:Test_page}}");
		fxt.Data_create("Test_page", "a{{#expr:<onlyinclude>1</onlyinclude>}}c");
		fxt.Test_parse_page_all_str
			(	"{{Test_tmpl}}"
			,	"1"
			);
		fxt.Init_defn_clear();
	}
	@Test  public void Pre_and_includeonly() {	// PAGE:https://en.wikipedia.org/wiki/BSD_licenses DATE:2014-05-23
		fxt.Init_defn_add("pre2", "<pre<includeonly></includeonly>>{{{1}}}</pre>");
		fxt.Test_parse_page_all_str
			(	"{{pre2|a}}"
			,	String_.Concat_lines_nl_skip_last
			(	"<pre>a</pre>"
			));
	}
//		@Test  public void Pre_and_includeonly2() {
//			fxt.Init_defn_add("pre2", "<pre<includeonly></includeonly>><nowiki>{{{1}}}</nowiki></pre>");
//			fxt.Test_parse_page_all_str
//				(	"{{pre2|a}}"
//				,	String_.Concat_lines_nl_skip_last
//				(	"<pre>a</pre>"
//				));
//		}
	@Test  public void Noinclude_inline_w_space_inside_safesubst() {	// PURPOSE: "<noinclude />" did not work with safesubst b/c of space; PAGE:en.w:Wikipedia:Featured_picture_candidates; DATE:2014-06-24
		fxt.Test_parse_tmpl_str_test("{{SAFESUBST:<noinclude />#if:val_exists|y|n}}", "{{test}}", "y");
	}
	@Test  public void Subst() {// PURPOSE: handle subst-includeonly-subst combination; PAGE:pt.w:Argentina DATE:2014-09-24
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "{{<includeonly>subst:</includeonly>#switch:1|1=y|default=n}}");
		//fxt.Init_defn_add("test", "{{subst:#switch:1|1=y|default=n}}");	// keeping around for debugging purposes
		//fxt.Init_defn_add("test", "{{<includeonly>#switch:</includeonly>1|1=y|default=n}}"); // keeping around for debugging purposes
		fxt.Test_parse_page_all_str("{{test}}", "{{subst:#switch:1|1=y|default=n}}");	// note that subst is preserved b/c of <includeonly>
		fxt.Test_parse_page_all_str("{{subst:test}}", "y");								// note that expression is evaluated b/c of subst:
	}
}
