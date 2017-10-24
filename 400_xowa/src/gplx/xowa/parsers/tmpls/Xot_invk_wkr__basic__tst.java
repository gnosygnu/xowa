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
import org.junit.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*;
public class Xot_invk_wkr__basic__tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Basic() {
		fxt.Test_parse_page_tmpl("{{a}}", fxt.tkn_tmpl_invk_w_name(0, 5, 2, 3));
	}
	@Test  public void Arg_many() {
		fxt.Test_parse_page_tmpl("{{a|b|c|d}}", fxt.tkn_tmpl_invk_w_name(0, 11, 2, 3)
			.Args_(fxt.tkn_arg_val_txt_(4, 5), fxt.tkn_arg_val_txt_(6, 7), fxt.tkn_arg_val_txt_(8, 9)));
	}
	@Test  public void Kv() {
		fxt.Test_parse_page_tmpl("{{a|b=c}}", fxt.tkn_tmpl_invk_w_name(0, 9, 2, 3)
			.Args_
			(	fxt.tkn_arg_nde_()
			.		Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(4, 5)))
			.		Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(6, 7)))
			));
	}
	@Test  public void Kv_arg() {
		fxt.Test_parse_tmpl("{{a|b={{{1}}}}}", fxt.tkn_tmpl_invk_w_name(0, 15, 2, 3)
			.Args_
			(	fxt.tkn_arg_nde_()
			.		Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(4, 5)))
			.		Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_tmpl_prm_find_(fxt.tkn_txt_(9, 10))))
			));
	}
	@Test  public void Kv_tmpl_compiled() {
		fxt.Test_parse_tmpl("{{a|b={{c}}}}", fxt.tkn_tmpl_invk_w_name(0, 13, 2, 3)
			.Args_
			(	fxt.tkn_arg_nde_()
			.		Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(4, 5)))
			.		Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_tmpl_invk_w_name(6, 11, 8, 9)))
			));
	}
	@Test  public void Kv_tmpl_dynamic() {
		fxt.Test_parse_tmpl("{{a|b={{c|{{{1}}}}}}}", fxt.tkn_tmpl_invk_w_name(0, 21, 2, 3)
			.Args_
			(	fxt.tkn_arg_nde_()
			.		Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(4, 5)))
			.		Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_tmpl_invk_w_name(6, 19, 8, 9).Args_(fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_().Subs_(fxt.tkn_tmpl_prm_find_(fxt.tkn_txt_(13, 14))))))
			)
			));
	}
	@Test  public void Nest() {
		fxt.Test_parse_page_tmpl("{{a|b{{c|d}}e}}", fxt.tkn_tmpl_invk_w_name(0, 15, 2, 3).Args_
			( fxt.tkn_arg_nde_().Val_tkn_(fxt.tkn_arg_itm_
				( fxt.tkn_txt_(4, 5)
				, fxt.tkn_tmpl_invk_w_name(5, 12, 7, 8).Args_
				( fxt.tkn_arg_val_txt_(9, 10)
				)
				, fxt.tkn_txt_(12, 13)
				))
			));
	}
	@Test  public void Whitespace() {
		fxt.Test_parse_page_tmpl("{{a| b = c }}", fxt.tkn_tmpl_invk_w_name(0, 13, 2, 3).Args_
			(	fxt.tkn_arg_nde_()
					.Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_space_(4, 5).Ignore_y_(), fxt.tkn_txt_(5,  6), fxt.tkn_space_( 6,  7).Ignore_y_()))
					.Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_space_(8, 9).Ignore_y_(), fxt.tkn_txt_(9, 10), fxt.tkn_space_(10, 11).Ignore_y_()))
			));
	}
	@Test  public void Whitespace_nl() {
		fxt.Test_parse_page_tmpl("{{a|b=c\n}}", fxt.tkn_tmpl_invk_w_name(0, 10, 2, 3).Args_
			(	fxt.tkn_arg_nde_()
					.Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(4,  5)))
					.Val_tkn_(fxt.tkn_arg_itm_(fxt.tkn_txt_(6,  7), fxt.tkn_nl_char_(7, 8).Ignore_y_()))
			));
	}
	@Test  public void Err_noName() { // WP: [[Russian language]]
		fxt.Test_parse_page_tmpl("{{|}}", fxt.tkn_curly_bgn_(0), fxt.tkn_pipe_(2), fxt.tkn_txt_(3, 5));
	}
	@Test  public void Err_noName_nl() {
		fxt.Test_parse_page_tmpl("{{\n|}}", fxt.tkn_curly_bgn_(0), fxt.tkn_nl_char_len1_(2), fxt.tkn_pipe_(3), fxt.tkn_txt_(4, 6));
	}
	@Test  public void Err_noName_tab() {
		fxt.Test_parse_page_tmpl("{{\t|}}", fxt.tkn_curly_bgn_(0), fxt.tkn_tab_(2), fxt.tkn_pipe_(3), fxt.tkn_txt_(4, 6));
	}
	@Test  public void Err_empty() { // WP: [[Set theory]] 
		fxt.Test_parse_page_tmpl("{{}}", fxt.tkn_txt_(0, 4));
	}
	@Test  public void DynamicName() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("proc_1", "proc_1 called");
		fxt.Init_defn_add("proc_2", "proc_2 called");
		fxt.Test_parse_tmpl_str_test("{{proc_{{{1}}}}}"			, "{{test|1}}"		, "proc_1 called");
		fxt.Test_parse_tmpl_str_test("{{proc_{{{1}}}}}"			, "{{test|2}}"		, "proc_2 called");
		fxt.Test_parse_tmpl_str_test("{{proc_{{#expr:1}}}}"		, "{{test}}"		, "proc_1 called");
	}
	@Test  public void Comment() {
		fxt.Test_parse_tmpl_str_test("b"							, "{{test<!--a-->}}"	, "b");
	}
	@Test  public void Err_equal() { // WP:[[E (mathematical constant)]]
		fxt.Test_parse_page_tmpl("{{=}}", fxt.tkn_tmpl_invk_(0, 5).Name_tkn_(fxt.tkn_arg_nde_(2, 3).Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_eq_(2)))));
	}
	@Test  public void Err_dangling() {	// WP:[[Antoni Salieri]]; {{icon it}\n
		fxt.Test_parse_page_tmpl("{{fail} {{pass}}", fxt.tkn_curly_bgn_(0), fxt.tkn_txt_(2, 7), fxt.tkn_space_(7, 8), fxt.tkn_tmpl_invk_w_name(8, 16, 10, 14));
	}
	@Test  public void MultipleColon() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("H:IPA", "{{{1}}}");
		fxt.Test_parse_tmpl_str_test("{{H:IPA|{{{1}}}}}"			, "{{test|a}}"		, "a");
	}
	@Test  public void RedundantTemplate() {	// {{Template:a}} == {{a}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("a", "a");
		fxt.Test_parse_tmpl_str_test("{{Template:a}}"				, "{{test}}"		, "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Lnki() {	// PURPOSE: pipe inside lnki should not be interpreted for tmpl: WP:[[Template:Quote]]
		fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"				, "{{test|[[a|b]]|c}}"	, "[[a|b]]c");
	}
	@Test  public void Lnki2() {// PURPOSE: pipe inside lnki inside tmpl should be interpreted WP:[[H:IPA]]
		fxt.Test_parse_tmpl_str_test("[[a|{{#switch:{{{1}}}|b=c]]|d=e]]|f]]}}"		, "{{test|b}}"			, "[[a|c]]");
	}
	@Test  public void Nowiki() {	// PURPOSE: nowiki tag should be ignored: en.w:Template:Main
		fxt.Test_parse_tmpl_str_test("<div <nowiki>class='a'</nowiki> />"				, "{{test}}"			, "<div <nowiki>class='a'</nowiki> />");
	}
	@Test  public void Nowiki_if() {	// PURPOSE: templates and functions inside nowiki should not be evaluated
		fxt.Test_parse_tmpl_str_test("a <nowiki>{{#if:|y|n}}</nowiki> b"			, "{{test}}"			, "a <nowiki>{{#if:|y|n}}</nowiki> b");
	}
	@Test  public void Nowiki_endtag() {	// PURPOSE: <nowiki/> should not match </nowiki>
		fxt.Test_parse_page_all_str("<nowiki /> ''b'' <nowiki>c</nowiki>"		, " <i>b</i> c");
	}
	@Test  public void Nowiki_xnde_frag() {	// PURPOSE: nowiki did not handle xnde frag and literalized; nowiki_xnde_frag; DATE:2013-01-27
		fxt.Test_parse_page_all_str("<nowiki><pre></nowiki>{{#expr:1}}<pre>b</pre>"	, "&lt;pre&gt;1<pre>b</pre>");
	}
	@Test  public void Nowiki_lnki() {	// PURPOSE: nowiki should noop lnkis; DATE:2013-01-27
		fxt.Test_parse_page_all_str("<nowiki>[[A]]</nowiki>"	, "[[A]]");
	}
	@Test  public void Nowiki_underscore() {	// PURPOSE: nowiki did not handle __DISAMBIG__; DATE:2013-07-28
		fxt.Test_parse_page_all_str("<nowiki>__DISAMBIG__</nowiki>"	, "__DISAMBIG__");
	}
	@Test  public void Nowiki_asterisk() {	// PURPOSE: nowiki should noop lists; DATE:2013-08-26
		fxt.Test_parse_page_all_str("<nowiki>*a</nowiki>", "*a");
	}
	@Test  public void Nowiki_space() {	// PURPOSE: nowiki should noop space (else pre); DATE:2013-09-03
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str("a\n<nowiki> </nowiki>b", "<p>a\n b\n</p>\n");
		fxt.Init_para_n_();
	}
	@Test  public void LnkiWithPipe_basic() {	// PURPOSE: pipe in link should not count towards tmpl: WP:{{H:title|dotted=no|pronunciation:|[[File:Loudspeaker.svg|11px|link=|alt=play]]}}
		fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"									, "{{test|[[b=c|d]]}}"			, "[[b=c|d]]{{{2}}}");
	}
	@Test  public void LnkiWithPipe_nested() {
		fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"									, "{{test|[[b=c|d[[e|f]]g]]}}"	, "[[b=c|d[[e|f]]g]]{{{2}}}");
	}
	@Test  public void LnkiWithPipe_unclosed() {
		fxt.Test_parse_tmpl_str_test("{{{1}}}{{{2}}}"									, "{{test|[[b=c|d}}"			, "{{test|[[b=c|d}}");
	}
	@Test  public void Err_tmp_empty() {	// PURPOSE: {{{{R from misspelling}} }}
		fxt.Test_parse_tmpl_str_test("{{{1}}}"										, "{{ {{a}} }}"					, "{{[[:Template:a]]}}");
	}
	@Test  public void Mismatch_bgn() {	// PURPOSE: handle {{{ }}; WP:Paris Commune; Infobox Former Country
		fxt.Init_defn_clear();
		fxt.Init_defn_add("!", "|");
		fxt.Test_parse_tmpl_str_test("{{#if:|{{{!}}{{!}}}|x}}"						, "{{test}}"					, "x");
	}
	@Test  public void Mismatch_tblw() {	// PURPOSE: handle {{{!}}; WP:Soviet Union
		fxt.Init_defn_clear();
		fxt.Init_defn_add("!", "|");
		fxt.Test_parse_page_all_str("a\n{{{!}}\n|b\n|}", String_.Concat_lines_nl_skip_last
			(	"a"
			,	"<table>"
			,	"  <tr>"
			,	"    <td>b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			) 
			);
		fxt.Init_defn_clear();
	}
	@Test  public void Lnki_space() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("c", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{c|[[a|b ]]}}", "[[a|b ]]");
	}
	@Test  public void Bug_innerTemplate() {	// PURPOSE: issue with inner templates not using correct key
		fxt.Init_defn_clear();
		fxt.Init_defn_add("temp_1", "{{temp_2|key1=val1}}");
		fxt.Init_defn_add("temp_2", "{{{key1}}}");
		fxt.Test_parse_tmpl_str("{{temp_1}}", "val1");
	}
	@Test  public void Xnde_xtn_preserved() {	// PURPOSE: tmpl was dropping .Xtn ndes;
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_template", "{{{1}}}");
		fxt.Test_parse_page_all_str("{{test_template|a<source>1</source>b}}", "a<div class=\"mw-highlight\"><pre style=\"overflow:auto\">1</pre></div>b");	// was just ab
		fxt.Init_defn_clear();
	}
	@Test  public void Recurse() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_recurse", "bgn:{{test_recurse}}:end");
		fxt.Test_parse_page_all_str("{{test_recurse}}", "bgn::end");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_nl() {
		fxt.Test_parse_tmpl_str_test("{{{1}}}"										, "{{test|\na}}"				, "\na");
	}
	@Test  public void Ws_trimmed_key_0() { // PURPOSE: control test for key_1, key_2
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{test_2|{{{1}}}}}");
		fxt.Init_defn_add("test_2", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{test_1| a }}", " a ");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_trimmed_key_1() { // PURPOSE: trim prm when passed as key;
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{test_2|key={{{1}}}}}");
		fxt.Init_defn_add("test_2", "{{{key}}}");
		fxt.Test_parse_tmpl_str("{{test_1| a }}", "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_trimmed_key_2() {	// PURPOSE: trim prm; note that 1 is key not idx; PAGE:en.w:Coord in Chernobyl disaster, Sahara
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{test_2|1={{{1}}}}}");
		fxt.Init_defn_add("test_2", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{test_1| a }}", "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_trimmed_key_3() { // PURPOSE: trim entire arg only, not individual prm
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{test_2|1={{{1}}}{{{2}}}}}");
		fxt.Init_defn_add("test_2", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{test_1| a | b }}", "a  b");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_eval_prm() {	// PURPOSE: skip ws in prm_idx; EX:it.w:Portale:Giochi_da_tavolo; it.w:Template:Alternate; DATE:2014-02-09
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", String_.Concat_lines_nl_skip_last
		(	"{{{ {{#expr: 1}} }}}"
		));
		fxt.Test_parse_tmpl_str("{{test_1|a}}", "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Keyd_arg_is_trimmed() { // PURPOSE: trim entire arg only, not individual prm; PAGE:en.w:William Shakespeare; {{Relatebardtree}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{test_2|1={{{{{{1}}}}}}}}");
		fxt.Init_defn_add("test_2", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{test_1| b | b = x }}", "x");
		fxt.Init_defn_clear();
	}
	@Test  public void Ws_arg() { // PURPOSE: whitespace arg should not throw array index out of bounds; EX.WIKT: wear one's heart on one's sleeve
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{test_1| }}", "");
		fxt.Init_defn_clear();
	}
	@Test  public void Xnde_xtn_ref_not_arg() {	// PURPOSE: <ref name= should not be interpreted as arg; EX: {{tmp|a<ref name="b"/>}}; arg1 is a<ref name="b"/> not "b"; PAGE:en.w:WWI
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{{1}}}");
		fxt.Test_parse_page_tmpl_str("{{test_1|a<ref name=b />}}", "a<ref name=b />");
		fxt.Init_defn_clear();
	}
	@Test  public void Multi_bgn_5_end_3_2() {
		fxt.Test_parse_tmpl("{{{{{1}}}|a}}", fxt.tkn_tmpl_invk_(0, 13)
			.Name_tkn_(fxt.tkn_arg_nde_(2, 9).Key_tkn_(fxt.tkn_arg_itm_(fxt.tkn_tmpl_prm_find_(fxt.tkn_txt_(5, 6)))))
			.Args_
			(	fxt.tkn_arg_val_txt_(10, 11)
			)
			);
	}
	@Test  public void Lnki_has_invk_end() {// PURPOSE: [[A|bcd}}]] should not break enclosing templates; EX.CM:Template:Protect
		fxt.Test_parse_page_tmpl_str(String_.Concat_lines_nl_skip_last
			(	"{{#switch:y"
			,	"  |y=[[A|b}}]]"
			,	"  |#default=n"
			,	"}}"
			),	"[[A|b}}]]");		
	}
	@Test  public void Lnki_has_prm_end() {// PURPOSE: [[A|bcd}}}]] should not break enclosing templates; EX.CM:Template:Protect
		fxt.Test_parse_page_tmpl_str(String_.Concat_lines_nl_skip_last
			(	"{{#switch:y"
			,	"  |y=[[A|b}}}]]"
			,	"  |#default=n"
			,	"}}"
			),	"[[A|b}}}]]");		
	}
	@Test  public void Tmpl_overrides_pfunc() { // PURPOSE: {{plural|}} overrides {{plural:}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("plural", "{{{1}}}");
		fxt.Test_parse_tmpl_str("{{plural|a}}"		, "a");
		fxt.Test_parse_tmpl_str("{{plural:2|a|b}}"	, "b");	// make sure pfunc still works
		fxt.Init_defn_clear();
	}
	@Test  public void Tmpl_aliases() { // PURPOSE: handled aliases for Template ns
		fxt.Wiki().Ns_mgr().Aliases_add(Xow_ns_.Tid__template, "TemplateAlias");
		fxt.Wiki().Ns_mgr().Init();
		fxt.Init_defn_clear();
		fxt.Init_defn_add("tmpl_key", "tmpl_val");
		fxt.Test_parse_tmpl_str("{{TemplateAlias:tmpl_key}}"		, "tmpl_val");
		fxt.Init_defn_clear();
	}
	@Test  public void Tmpl_aliases_2() { // PURPOSE: handled aliases for other ns; DATE:2013-02-08
		fxt.Wiki().Ns_mgr().Aliases_add(Xow_ns_.Tid__project, "WP");
		fxt.Wiki().Ns_mgr().Init();
		fxt.Init_defn_clear();
		fxt.Init_page_create("Project:tmpl_key", "tmpl_val");
		fxt.Test_parse_tmpl_str("{{WP:tmpl_key}}"		, "tmpl_val");
		fxt.Init_defn_clear();
	}
	@Test  public void Template_loop_across_namespaces() {// PURPOSE: {{Institution:Louvre}} results in template loop b/c it calls {{Louvre}}; EX: c:Mona Lisa
		fxt.Init_page_create("Template:Test", "test");
		fxt.Init_page_create("Category:Test", "{{Test}}");
		fxt.Test_parse_page_all_str("{{Category:Test}}", "test");
	}
	@Test  public void Closing_braces_should_not_extend_beyond_lnki() {	// PURPOSE: extra }} should not close any frames beyond lnki; EX:w:Template:Cite wikisource; w:John Fletcher (playwright)
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_b", "{{{test_b_0|}}}");
		fxt.Init_defn_add("test_a", "{{test_b|test_b_0=[[{{{test_a_0}}}}}]]}}");	// NOTE: extra 2 }}; should render literally and not close "{{test_b"
		fxt.Test_parse_tmpl_str("{{test_a|test_a_0=1}}"	, "[[1}}]]");
		fxt.Init_defn_clear();
	}
//		@Test  public void Trim_ws_on_sub_tmpls() {	// PURPOSE: ws should be trimmed on eval tkns; EX:w:Lackawanna Cut-Off; {{Lackawanna Cut-Off}}; DELETE: no longer needed; DATE:2014-02-04
//			fxt.Init_defn_clear();
//			fxt.Init_defn_add("test_b", "\n\nb\n\n");
//			fxt.Init_defn_add("test_a", "a{{test_b}}c");
//			fxt.Test_parse_tmpl_str("{{test_a}}"	, "a\n\nbc");
//			fxt.Init_defn_clear();
//		}
	@Test   public void Nowiki_tblw() {	// PURPOSE: nowiki does not exclude sections with pipe; will cause tables to fail; EX: de.wikipedia.org/wiki/Hilfe:Vorlagenprogrammierung
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"|<nowiki>{{ #time:M|Jan}}</nowiki>"
		,	"|}"
		), String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"	
		,	"    <td>{{ #time:M|Jan}}"	
		,	"    </td>"	
		,	"  </tr>"	
		,	"</table>"
		,	""
		));
	}
	@Test   public void Template_plus_other_ns() {  // PURPOSE.fix: Template:Wikipedia:A was transcluding "Wikipedia:A" instead of "Template:Wikipedia:A"; DATE:2013-04-03  
		fxt.Init_defn_clear();
		fxt.Init_page_create("Template:Wikipedia:A"				, "B");
		fxt.Test_parse_tmpl_str("{{Template:Wikipedia:A}}"		, "B");
		fxt.Init_defn_clear();
	}
	@Test  public void Return_nl() {	// PURPOSE: allow \n to be returned by tmpl; do not trim; EX: zh.wikipedia.org/wiki/北區_(香港); DATE:2014-02-04
		fxt.Init_defn_add("1x", "{{{1}}}");
		fxt.Test_parse_tmpl_str("a{{1x|\n}}b", "a\nb");
		fxt.Init_defn_clear();
	}
	@Test  public void Ignore_hdr() {	// PURPOSE: hdr-like tkns inside tmpl should not be treated as "=" in tmpl_prm; EX: key_1\n==a==; EX:it.b:Wikibooks:Vetrina; DATE:2014-02-09
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{{key_1|null_key_1}}}");
		fxt.Test_parse_tmpl_str(String_.Concat_lines_nl_skip_last		// == a === should be treated as hdr;
		(	"{{test_1|key_1"
		,	"== a =="
		,	"}}"
		)
		,	"null_key_1"
		);
		fxt.Test_parse_tmpl_str(String_.Concat_lines_nl_skip_last		// = a = should not be treated as hdr; 
		(	"{{test_1|key_1"
		,	"= a ="
		,	"}}"
		)
		,	"a ="
		);
		fxt.Init_defn_clear();
	}
	@Test  public void Ignore_hdr_2() {	// PURPOSE: hdr-like logic did not work for "== \n"; PAGE:nl.q:Geert_Wilders; DATE:2014-06-05
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Hdr_w_space", String_.Concat_lines_nl_skip_last
		( "{{#if:{{{key|}}} | "
		, "==={{{key}}}=== "	// NOTE " " after "==="
		, "|}}"
		));
		fxt.Test_parse_page_tmpl_str(String_.Concat_lines_nl_skip_last
		( "{{Hdr_w_space"
		, "|key=A"
		, "}}"
		), "===A==="
		);
		fxt.Init_defn_clear();
	}
	@Test  public void Ignore_hdr_3() {	// PURPOSE: tkn with multiple eq should have be treated as value, not header; PAGE:zh.w:Wikipedia:条目评选; DATE:2014-08-27
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{{key_1|null_key_1}}}");
		fxt.Test_parse_page_tmpl_str(String_.Concat_lines_nl_skip_last
		( "{{test_1"
		, "|key_1===A=="	// note that this is not "===A==", but "=","===A=="
		, "}}"
		), "==A=="
		);
		fxt.Init_defn_clear();
	}
	@Test  public void Ignore_hdr_4() {	// PURPOSE: variation of above; make sure 2nd "==" doesn't trigger another key; DATE:2014-08-27
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_1", "{{{key_1|null_key_1}}}");
		fxt.Test_parse_page_tmpl_str(String_.Concat_lines_nl_skip_last
		( "{{test_1"
		, "|key_1===A===B"	// = should be at "==A", not "==B"
		, "}}"
		), "==A===B"
		);
		fxt.Init_defn_clear();
	}
	@Test  public void Tmpl_case_match() {	// PURPOSE: template name should match by case; EX:es.d:eclipse; DATE:2014-02-12
		fxt.Init_defn_clear();
		fxt.Init_defn_add("CASE_MATCH", "found", Xow_ns_case_.Tid__all);
		fxt.Test_parse_tmpl_str("{{case_match}}",	"[[:Template:case_match]]");		// Xot_invk_tkn will do 2 searches: "test" and "Test"
		fxt.Test_parse_tmpl_str("{{cASE_MATCH}}",	"found");				// Xot_invk_tkn will do 2 searches: "tEST" and "TEST"
		fxt.Init_defn_clear();
	}
	@Test  public void Kv_same() {	// PURPOSE: multiple identical keys should retrieve last, not first; EX: {{A|1=a|1=b}}; PAGE:el.d:ἔχω DATE:2014-07-23
		fxt.Init_defn_clear();
		fxt.Init_defn_add("tmpl_1", "{{{1}}}");
		fxt.Test_parse_tmpl_str_test("{{tmpl_1|1=a|1=b}}"	, "{{test}}"		, "b");	// duplicate "1"; use last
		fxt.Test_parse_tmpl_str_test("{{tmpl_1|a|1=b}}"		, "{{test}}"		, "b");	// "a" has implicit key of "1"; overwritten by "1=b"; verified against MW
		fxt.Test_parse_tmpl_str_test("{{tmpl_1|1=a|b}}"		, "{{test}}"		, "b");	// "b" has implicit key of "1"; overwritten by "1=b"; verified against MW
	}
	@Test  public void Bang() {	// PURPOSE: support new bang keyword; DATE:2014-08-05
		fxt.Test_parse_tmpl_str("{{!}}", "|");
	}
}
