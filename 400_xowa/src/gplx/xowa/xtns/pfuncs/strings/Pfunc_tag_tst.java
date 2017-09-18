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
package gplx.xowa.xtns.pfuncs.strings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_tag_tst {		
	@Before public void init()				{fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test   public void Basic()				{fxt.Test_html_full_str("{{#tag:pre|a|id=b|style=c}}"				, "<pre id=\"b\" style=\"c\">a</pre>");}
	@Test   public void Atr2_empty()		{fxt.Test_html_full_str("{{#tag:pre|a|id=b|}}"						, "<pre id=\"b\">a</pre>");}		// see {{Reflist|colwidth=30em}} -> <ref group=a>a</ref>{{#tag:references||group=a|}} -> "<references group=a/>"
	@Test   public void Val_apos()			{fxt.Test_html_full_str("{{#tag:pre|a|id='b'}}"						, "<pre id=\"b\">a</pre>");}
	@Test   public void Val_quote()			{fxt.Test_html_full_str("{{#tag:pre|a|id=\"b\"}}"					, "<pre id=\"b\">a</pre>");}
	@Test   public void Val_empty()			{fxt.Test_html_full_str("{{#tag:pre|a|id=}}"						, "<pre>a</pre>");}					// PURPOSE: ignore atrs with no val; EX:{{#ref||group=}} PAGE:ru.w:Колчак,_Александр_Васильевич; DATE:2014-07-03
	@Test   public void Val_quote_w_apos()	{fxt.Test_html_full_str("{{#tag:pre|c|id=\"a'b\"}}"					, "<pre id=\"a.27b\">c</pre>");}	// PURPOSE.fix: tag was not handling apos within quotes; PAGE:en.s:The_formative_period_in_Colby%27s_history DATE:2016-06-23
	@Test   public void Tmpl()				{fxt.Test_html_full_str("{{#tag:pre|a|{{#switch:a|a=id}}=c}}"		, "<pre id=\"c\">a</pre>");}		// PURPOSE: args must be evaluated
	@Test   public void Ws_all()		    {fxt.Test_html_full_str("{{#tag:pre|a|   id   =   b   }}"			, "<pre id=\"b\">a</pre>");}
	@Test   public void Ws_quoted()			{fxt.Test_html_full_str("{{#tag:pre|a|   id   = ' b ' }}"			, "<pre id=\"_b_\">a</pre>");}
	@Test   public void Err_bad_key()		{fxt.Test_html_full_str("{{#tag:pre|a|id=val|b}}"					, "<pre id=\"val\">a</pre>");}			// PURPOSE: b was failing b/c id was larger and key_end set to 4 (whereas b was len=1)
	@Test   public void Html_is_escaped()	{fxt.Test_html_full_str("{{#tag:pre|a|id='<br/>'}}"					, "<pre id=\".3Cbr.2F.3E\">a</pre>");}	// PURPOSE: escape html in atrs; PAGE:fr.v:France; DATE:2017-06-01
//		@Test   public void Missing_val()	{fxt.ini_Msg(Mwl_tag_rsc.Instance.Invalid).Test_parse_tmpl_str_test("{{#tag:pre|a|id=}}"	, "{{test}}"	, "");}	// see {{Reflist|colwidth=30em}} -> <ref group=a>a</ref>{{#tag:references||group=}} -> ""
//		@Test   public void Err()		{
//			fxt.Test_parse_tmpl_str_test("{{#tag:ref|George Robertson announced in January 2003 that he would be stepping down in December.<ref> {{cite news|title =NATO Secretary General to Leave His Post in December After 4 Years |first = Craig | last = Smith | work = The New York Times | date = January 23, 2003| url = http://www.nytimes.com/2003/01/23/world/nato-secretary-general-to-leave-his-post-in-december-after-4-years.html?scp=2&sq=lord+robertson&st=nyt|accessdate = 2009-03-29}}</ref> Jaap de Hoop Scheffer was selected as his successor, but could not assume the office until January 2004 because of his commitment in the Dutch Parliament.<ref> {{cite news|title = Jaap de Hoop Scheffer | work = Newsmakers | issue = 1 | publisher = Thomson Gale | date = January 1, 2005}}</ref> Robertson was asked to extend his term until Scheffer was ready, but declined, so Minuto-Rizzo, the Deputy Secretary General, took over in the interim.<ref name =\"ncsd\" />  |group=N|}}"
//				, "{{test}}"	, "<pre id=\" b \">a</pre>");}
	@Test   public void Nested_tmpl() {	// PURPOSE: nested template must get re-evaluated; EX:de.wikipedia.org/wiki/Freiburg_im_Breisgau; DATE:2013-12-18;
		fxt.Init_page_create("Template:!", "|");
		fxt.Init_page_create("Template:A", "{{#ifeq:{{{1}}}|expd|pass|fail}}");
		fxt.Test_html_full_frag("{{#tag:ref|{{A{{!}}expd}}}}<references/>", "<span class=\"reference-text\">pass</span>");
	}
	@Test   public void Nested_ref__pair() {	// PURPOSE: handle refs inside tag; PAGE:en.w:UK; DATE:2015-12-26
		fxt.Test_html_full_str("{{#tag:ref|<ref>a</ref>b}}<references/>", String_.Concat_lines_nl_skip_last
		( "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[2]</a></sup><ol class=\"references\">"
		, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">a</span></li>"
		, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\"><sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>b</span></li>"
		, "</ol>"
		));
	}
	@Test   public void Nested_ref__inline() {	// PURPOSE: handle refs inside tag; PAGE:en.w:Earth; DATE:2015-12-29
		fxt.Test_html_full_str("{{#tag:ref|<ref name='a'/>b}}<references/>", String_.Concat_lines_nl_skip_last
		( "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[2]</a></sup><ol class=\"references\">"
		, "<li id=\"cite_note-a-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-a_0-0\">^</a></span> <span class=\"reference-text\"></span></li>"
		, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\"><sup id=\"cite_ref-a_0-0\" class=\"reference\"><a href=\"#cite_note-a-0\">[1]</a></sup>b</span></li>"
		, "</ol>"
		));
	}
}
