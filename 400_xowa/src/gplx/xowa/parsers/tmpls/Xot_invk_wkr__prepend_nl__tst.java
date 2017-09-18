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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_invk_wkr__prepend_nl__tst {		
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {	// PURPOSE: if {| : ; # *, auto add new_line REF.MW:Parser.php|braceSubstitution
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_inner", "# a");
		fxt.Test_parse_tmpl_str_test("{{test_inner}}"	, "z {{test}}"				, "z \n# a");
		fxt.Init_defn_clear();
	}
	@Test  public void Skip_if_nl_exists() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_inner", "# a");
		fxt.Test_parse_tmpl_str_test("{{test_inner}}"	, "z \n{{test}}"			, "z \n# a");		// NOTE: no \n
		fxt.Init_defn_clear();
	}
	@Test  public void Skip_if_nl_exists_2() {	// PURPOSE: \n inside template args should not print \n\n; PAGE:bn.w:লিওনেল_মেসি |ko.w:도쿄_지하철_히비야_선|DATE:2014-05-27
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_list", "# a");
		fxt.Init_defn_add("test_print", "{{{1}}}");
		fxt.Test_parse_tmpl_str_test(String_.Concat_lines_nl_skip_last
		( "{{test_print|"
		, "{{test_list}}"	// note that there is a "\n" here, but test_list will return "#"; "#" should not be prepended with \n
		, "{{test_list}}"
		, "}}"
		), "{{test}}"
		, String_.Concat_lines_nl_skip_last
		( ""		// NOTE: \n still prints b/c of \n between "{{test_print|" and "{{test_list}}"; should trim ws at start;
		, "# a"
		, "# a"
		));
		fxt.Init_defn_clear();
	}
	@Test  public void Pfunc() {// PURPOSE: if {| : ; # *, auto add new_line; parser_function variant; PAGE:en.w:Soviet Union; Infobox former country
		fxt.Test_parse_tmpl_str_test(""					, "z {{#if:true|#a|n}}"		, "z \n#a");
	}
	@Test  public void Bos() {	// PURPOSE: function should expand "*a" to "\n*a" even if "*a" is bos; SEE:NOTE_1 PAGE:en.w:Rome and Panoramas; DATE:2014-02-05
		fxt.Test_parse_page_tmpl_str("{{#if:x|*a}}", "\n*a");
	}
	@Test  public void Tmpl_arg() {	// PURPOSE: tmpl arg should auto-create; PAGE:vi.w:Friedrich_II_của_Phổ; DATE:2014-04-26
		fxt.Init_defn_add("cquote"		, "*b");
		fxt.Init_defn_add("blockquote"	, "<blockquote>{{{1}}}</blockquote>");
		fxt.Test_html_full_str("a\n{{blockquote|{{cquote}}}}"
		, String_.Concat_lines_nl_skip_last
		( "a"
		, "<blockquote>"
		, "<ul>"
		, "  <li>b"
		, "  </li>"
		, "</ul></blockquote>"
		)
		);
	}
	@Test  public void Nested_1_n() {	// PURPOSE: handled nested templates; PAGE:en.w:Central_Line en.w:Panama_Canal; DATE:2014-08-21
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Nest_1"		, "a{{Nest_1_1}}");			// 0: no \n
		fxt.Init_defn_add("Nest_1_1"	, "b\n{{Nest_1_1_1}}");		// 1: \n
		fxt.Init_defn_add("Nest_1_1_1"	, "*c");					// 2: "*" should not prepend \n b/c (1) has \n; used to only check (0)
		fxt.Test_parse_tmpl_str_test("{{Nest_1}}", "{{test}}", String_.Concat_lines_nl_skip_last
		( "ab"	// not prepended
		, "*c"
		));
	}
	@Test  public void Nested_1_y() {	// PURPOSE: handled nested templates; PAGE:en.w:Lackawanna_Cut-Off; DATE:2014-08-21
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Nest_1"		, "a\n{{Nest_1_1}}");		// 0: no \n
		fxt.Init_defn_add("Nest_1_1"	, "b{{Nest_1_1_1}}");		// 1: char
		fxt.Init_defn_add("Nest_1_1_1"	, "*c");					// 2: "*" should prepend \n b/c (1) has char; used to check (0) and not prepend
		fxt.Test_parse_tmpl_str_test("{{Nest_1}}", "{{test}}", String_.Concat_lines_nl_skip_last
		( "a"
		, "b"	// prepended
		, "*c"
		));
	}
	@Test  public void Nested_0_n() {	// PURPOSE: handled nested templates variation of above; DATE:2014-08-21
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Nest_1"		, "a\n{{Nest_1_1}}");		// 0: \n
		fxt.Init_defn_add("Nest_1_1"	, "{{Nest_1_1_1}}");		// 1: ""
		fxt.Init_defn_add("Nest_1_1_1"	, "*b");					// 2: "*" should not prepend \n b/c (1) is empty and (0) has \n
		fxt.Test_parse_tmpl_str_test("{{Nest_1}}", "{{test}}", String_.Concat_lines_nl_skip_last
		( "a"	// not prepended
		, "*b"
		));
	}
	@Test  public void Nested_0_y() {	// PURPOSE: handled nested templates variation of above; DATE:2014-08-21
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Nest_1"		, "a{{Nest_1_1}}");			// 0: no \n
		fxt.Init_defn_add("Nest_1_1"	, "{{Nest_1_1_1}}");		// 1: ""
		fxt.Init_defn_add("Nest_1_1_1"	, "*b");					// 2: "*" should prepend \n b/c (1) is empty and (0) has char
		fxt.Test_parse_tmpl_str_test("{{Nest_1}}", "{{test}}", String_.Concat_lines_nl_skip_last
		( "a"	// prepended
		, "*b"
		));
	}
}
/*
NOTE_1: function should expand "*a" to "\n*a" even if "*a" is bos
consider following
Template:Test with text of "#a"
a) "a{{test}}" would return "a\n#a" b/c of rule for auto-adding \n
b) bug was that "{{test}}" would return "#a" b/c "#a" was at bos which would expand to list later
   however, needs to be "\n#a" b/c appended to other strings wherein bos would be irrelevant.
Actual situation was very complicated. PAGE:en.w:Rome;
*/
