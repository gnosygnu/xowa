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
public class Pf_tag_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()			{fxt.Reset();}
	@Test   public void Basic()			{fxt.Test_html_full_str("{{#tag:pre|a|id=b|style=c}}"				, "<pre id=\"b\" style=\"c\">a</pre>");}
//		@Test   public void Missing_val()	{fxt.ini_Msg(Mwl_tag_rsc._.Invalid).Test_parse_tmpl_str_test("{{#tag:pre|a|id=}}"	, "{{test}}"	, "");}	// see {{Reflist|colwidth=30em}} -> <ref group=a>a</ref>{{#tag:references||group=}} -> ""
	@Test   public void Atr2_empty()	{fxt.Test_html_full_str("{{#tag:pre|a|id=b|}}"						, "<pre id=\"b\">a</pre>");}	// see {{Reflist|colwidth=30em}} -> <ref group=a>a</ref>{{#tag:references||group=a|}} -> "<references group=a/>"
	@Test   public void Val_apos()		{fxt.Test_html_full_str("{{#tag:pre|a|id='b'}}"						, "<pre id=\"b\">a</pre>");}
	@Test   public void Val_quote()		{fxt.Test_html_full_str("{{#tag:pre|a|id=\"b\"}}"					, "<pre id=\"b\">a</pre>");}
	@Test   public void Ws_all()	    {fxt.Test_html_full_str("{{#tag:pre|a|   id   =   b   }}"			, "<pre id=\"b\">a</pre>");}
	@Test   public void Ws_quoted()		{fxt.Test_html_full_str("{{#tag:pre|a|   id   = ' b ' }}"			, "<pre id=\"_b_\">a</pre>");}
	@Test   public void Err_bad_key()	{fxt.Test_html_full_str("{{#tag:pre|a|id=val|b}}"					, "<pre id=\"val\">a</pre>");}	// PURPOSE: b was failing b/c id was larger and key_end set to 4 (whereas b was len=1)
//		@Test   public void Exc()		{
//			fxt.Test_parse_tmpl_str_test("{{#tag:ref|George Robertson announced in January 2003 that he would be stepping down in December.<ref> {{cite news|title =NATO Secretary General to Leave His Post in December After 4 Years |first = Craig | last = Smith | work = The New York Times | date = January 23, 2003| url = http://www.nytimes.com/2003/01/23/world/nato-secretary-general-to-leave-his-post-in-december-after-4-years.html?scp=2&sq=lord+robertson&st=nyt|accessdate = 2009-03-29}}</ref> Jaap de Hoop Scheffer was selected as his successor, but could not assume the office until January 2004 because of his commitment in the Dutch Parliament.<ref> {{cite news|title = Jaap de Hoop Scheffer | work = Newsmakers | issue = 1 | publisher = Thomson Gale | date = January 1, 2005}}</ref> Robertson was asked to extend his term until Scheffer was ready, but declined, so Minuto-Rizzo, the Deputy Secretary General, took over in the interim.<ref name =\"ncsd\" />  |group=N|}}"
//				, "{{test}}"	, "<pre id=\" b \">a</pre>");}
	@Test   public void Nested_tmpl() {	// PURPOSE: nested template must get re-evaluated; EX:de.wikipedia.org/wiki/Freiburg_im_Breisgau; DATE:2013-12-18;
		fxt.Init_page_create("Template:!", "|");
		fxt.Init_page_create("Template:A", "{{#ifeq:{{{1}}}|expd|pass|fail}}");
		fxt.Test_html_full_frag("{{#tag:ref|{{A{{!}}expd}}}}<references/>", "<span class=\"reference-text\">pass</span>");
	}
}
