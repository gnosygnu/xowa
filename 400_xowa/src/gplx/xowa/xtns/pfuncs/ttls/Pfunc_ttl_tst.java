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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_ttl_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test   public void Ttl_page_txt()			{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAME}}"								, "{{test}}", "A b");}
	@Test   public void Ttl_page_txt_empty()	{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAME:}}"								, "{{test}}", "");}
	@Test   public void Ttl_page_txt_empty_2()	{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAME:{{{a|}}}}}"						, "{{test}}", "");}	// PURPOSE.fix: {{PAGENAME:{{{nom|}}}}} should return ""; pt.wikipedia.org/wiki/Nicholas Kratzer; DATE:2013-02-20
	@Test   public void Ttl_page_txt_arg()		{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAME:c d}}"							, "{{test}}", "C d");}
	@Test   public void Ttl_page_txt_amp()		{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAME:c & d}}"						, "{{test}}", "C & d");}
	@Test   public void Ttl_page_url()			{fxt.Page_ttl_("a b"); fxt.Test_parse_tmpl_str_test("{{PAGENAMEE}}"								, "{{test}}", "A_b");}
	@Test   public void Ttl_full_txt()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{FULLPAGENAME}}"						, "{{test}}", "Help:A b");}
	@Test   public void Ttl_full_url()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{FULLPAGENAMEE}}"					, "{{test}}", "Help:A_b");}
	@Test   public void Ttl_leaf_txt()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{SUBPAGENAME}}"						, "{{test}}", "d e");}
	@Test   public void Ttl_leaf_url()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{SUBPAGENAMEE}}"					, "{{test}}", "d_e");}
	@Test   public void Ttl_base_txt()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{BASEPAGENAME}}"					, "{{test}}", "A b/c");}
	@Test   public void Ttl_base_url()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{BASEPAGENAMEE}}"					, "{{test}}", "A_b/c");}
	@Test   public void Rootpagename()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{ROOTPAGENAME}}"					, "{{test}}", "A b");}
	@Test   public void Rootpagenamee()			{fxt.Page_ttl_("a b/c/d e"); fxt.Test_parse_tmpl_str_test("{{ROOTPAGENAMEE}}"					, "{{test}}", "A_b");}
	@Test   public void Ttl_subj_txt()			{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{SUBJECTPAGENAME}}"				, "{{test}}", "Help:A b");}
	@Test   public void Ttl_subj_url()			{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{SUBJECTPAGENAMEE}}"			, "{{test}}", "Help:A_b");}
	@Test   public void Ttl_talk_txt()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{TALKPAGENAME}}"						, "{{test}}", "Help talk:A b");}
	@Test   public void Ttl_talk_url()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{TALKPAGENAMEE}}"					, "{{test}}", "Help_talk:A_b");}
	@Test   public void Ns_num()				{fxt.Page_ttl_("Help:A"); fxt.Test_parse_tmpl_str_test("{{NAMESPACENUMBER}}"					, "{{test}}", "12");}
	@Test   public void Ns_txt()				{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{NAMESPACE}}"					, "{{test}}", "Help talk");}
	@Test   public void Ns_url()				{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{NAMESPACEE}}"					, "{{test}}", "Help_talk");}
	@Test   public void Ns_subj_txt()			{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{SUBJECTSPACE}}"				, "{{test}}", "Help");}
	@Test   public void Ns_subj_url()			{fxt.Page_ttl_("Help talk:a b"); fxt.Test_parse_tmpl_str_test("{{SUBJECTSPACEE}}"				, "{{test}}", "Help");}
	@Test   public void Ns_talk_txt()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{TALKSPACE}}"						, "{{test}}", "Help talk");}
	@Test   public void Ns_talk_url()			{fxt.Page_ttl_("Help:a b"); fxt.Test_parse_tmpl_str_test("{{TALKSPACEE}}"						, "{{test}}", "Help_talk");}
	@Test   public void Ns_safesubst()			{fxt.Page_ttl_("Help:a"); fxt.Test_parse_tmpl_str_test("{{{{{|safesubst:}}}NAMESPACE}}"			, "{{test}}", "Help");}	// PURPOSE: "safesubst:NAMESPACE" was being interpreted as "NAMESPACE:" due to false colon detection; DATE:2013-11-11
	@Test   public void Ns_safesubst2()			{fxt.Page_ttl_("Help:a"); fxt.Test_parse_tmpl_str_test("{{{{{|safesubst:}}}NAMESPACE:}}"		, "{{test}}", "");} //PURPOSE: makes sure "safesubst:NAMESPACE:" still returns ""
	@Test   public void Exc_empty2()			{fxt.Page_ttl_("Test"); fxt.Test_parse_tmpl_str_test("{{NAMESPACE:<noinclude>x</noinclude>}}"	, "{{test}}", "");} // {{NMS:<ni>x</ni>}} -> {{NMS:}}
}
