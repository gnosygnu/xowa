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
import org.junit.*; import gplx.xowa.wikis.ttls.*;
public class Xop_subst_tst {
	private final    Xop_fxt fxt = new Xop_fxt(); 
	@Before public void init() {
		fxt.Reset();
		fxt.Init_defn_clear();
		fxt.Init_defn_add("xo_print", "{{{1}}}");
		fxt.Init_defn_add("!", "|");
	}
	@Test  public void Wiki_txt_subst()				{fxt.Test_parse_tmpl_str_test("{{{1}}}"								, "{{subst:test|a}}"			, "a");}
	@Test  public void Wiki_txt_subst_ws()			{fxt.Test_parse_tmpl_str_test("{{{1}}}"								, "{{ subst:test|a}}"			, "a");}
	@Test  public void Wiki_txt_safesubst()			{fxt.Test_parse_tmpl_str_test("{{{1}}}"								, "{{safesubst:test|a}}"		, "a");}
	@Test  public void Tmpl_txt_subst_empty()		{fxt.Test_parse_tmpl_str_test("{{subst:}}"							, "{{test}}"					, "{{subst:}}");}
	@Test  public void Tmpl_txt_safesubst()			{fxt.Test_parse_tmpl_str_test("{{safesubst:xo_print|a}}"			, "{{test}}"					, "a");}
	@Test  public void Tmpl_prm_subst()				{fxt.Test_parse_tmpl_str_test("{{{{{1|subst:}}}xo_print|a}}"		, "{{test}}"					, "{{subst:xo_print|a}}");}
	@Test  public void Tmpl_prm_subst_ws()			{fxt.Test_parse_tmpl_str_test("{{{{{1| subst:}}}xo_print|a}}"		, "{{test}}"					, "{{ subst:xo_print|a}}");}
	@Test  public void Tmpl_prm_safesubst()			{fxt.Test_parse_tmpl_str_test("{{{{{1|safesubst:}}}xo_print|a}}"	, "{{test}}"					, "a");}
	@Test  public void Tmpl_prm_safesubst_empty()	{fxt.Test_parse_tmpl_str_test("{{{{{|safesubst:}}}xo_print|a}}"		, "{{test}}"					, "a");}
	@Test  public void Tmpl_txt_subst_pf()			{fxt.Test_parse_tmpl_str_test("{{subst:#expr:0}}"					, "{{test}}"					, "0");}
	@Test  public void Tmpl_txt_safesubst_prm()		{fxt.Test_parse_tmpl_str_test("{{{{{|safesubst:}}}#if:{{{1|}}}{{{{{|safesubst:}}}!}}c1|c2}}"	, "{{test}}"					, "c2");}
	@Test  public void Exc_tmpl_prm_safesubst_ns()	{fxt.Test_parse_tmpl_str_test("{{{{{|safesubst}}}:NAMESPACE}}"		, "{{test}}"					, "");}
	@Test  public void Unreferenced() {	// PURPOSE: if subst, but in tmpl stage, do not actually subst; PAGE:en.w:Unreferenced; DATE:2013-01-31
		fxt.Init_defn_clear();
		fxt.Init_defn_add("substcheck", "SUBST");
		fxt.Init_defn_add("ifsubst", String_.Concat_lines_nl
			(	"{{ {{{|safesubst:}}}#ifeq:{{ {{{|safesubst:}}}NAMESPACE}}|{{NAMESPACE}}"
			,	" |{{{no|{{{2|}}}}}}"
			,	" |{{{yes|{{{1|}}}}}}"
			,	"}}"
			));
		fxt.Test_parse_tmpl_str_test("{{ {{{|safesubst:}}}ifsubst |yes|<includeonly>{{subst:substcheck}}</includeonly>}}", "{{test}}", "{{subst:substcheck}}");
	}
	@Test  public void Urlencode_missing_ttl() {	// PURPOSE: handle missing ttl inside {{does-template-exist}}; EX: en.d:Kazakhstan; DATE:2014-03-25
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "{{safesubst:urlencode:{{safesubst:Template:{{{1}}}}}}}");
		fxt.Test_parse_page_tmpl_str("{{test|xyz}}", "%5B%5BTemplate%3Axyz%5D%5D");	// url-encoded version of [[:Template:xyz]]; NOTE: removed leading ":" from ":Template:" DATE:2016-06-24
	}
	@Test  public void Urlencode_invalid_ttl() {	// PURPOSE: handle invalid ttl inside does-template-exist; EX: en.d:peace; DATE:2014-03-31
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "{{safesubst:urlencode:{{safesubst:Template:{{{1}}}}}}}");
		fxt.Test_parse_page_tmpl_str("{{test|[xyz]}}", "%7B%7Bsafesubst%3ATemplate%3A%5Bxyz%5D%7D%7D");	// url-encoded version of {{safesubst:Template:xyz}}
	}
	@Test  public void Urlencode_template_ttl() {	// PURPOSE: handle template ttl inside does-template-exist; based on above; DATE:2014-03-31
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test", "{{safesubst:urlencode:{{Template:{{{1}}}}}}}");
		fxt.Test_parse_page_tmpl_str("{{test|Template:[xyz]}}", "%7B%7BTemplate%3ATemplate%3A%5Bxyz%5D%7D%7D");	// url-encoded version of {{safesubst:Template:xyz}}
	}
	@Test   public void Nowiki() {	// PURPOSE: stack overflow; PAGE:Близкие_друзья_(Сезон_2) DATE:2014-10-21
		fxt.Init_defn_add("ET", "");
		fxt.Init_defn_add("ds", "<includeonly>{{subst:</includeonly><includeonly>ET|<nowiki>{{subst:ds}}</nowiki>}}</includeonly>");
		fxt.Test_parse_page_tmpl_str("{{subst:ds}}", "");	// {{subst:ds}} causes stack overflow; {{ds}} does not
	}
	// NOTE: these are actually not good tests; MW does subst just before save; it doesn't do subst on load; in this case, the tests are testing load (which will noop); they need to test save (which xowa doesn't do)
	// @Test  public void Tmpl_txt_subst()				{fxt.Test_parse_tmpl_str_test("{{subst:xo_print|a}}"				, "{{test}}"					, "a");}
	// @Test  public void Tmpl_txt_subst_prm()			{fxt.Test_parse_tmpl_str_test("{{subst:xo_print|{{{1}}}}}"			, "{{test|a}}"					, "a");}
	//@Test  public void Tmpl_txt_safesubst_prm()		{fxt.Test_parse_tmpl_str_test("{{{{{|safesubst:}}}ns:Category}}"	, "{{test}}"					, "Category");}
	//@Test  public void Tmpl_txt_subst_immed()		{fxt.Test_parse_tmpl_str_test("{{xo_print{{subst:!}}a}}"			, "{{test}}"					, "a");}
}
