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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_amp_wkr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Name()					{fxt.Test_parse_page_wiki("&amp;"			, fxt.tkn_html_ref_("&amp;"));}			// check for html_ref 
	@Test  public void Name_fail()				{fxt.Test_parse_page_wiki("&nil;"			, fxt.tkn_txt_(0, 5));}					// check for text
	@Test  public void Hex()					{fxt.Test_parse_page_wiki("&#x3A3;"			, fxt.tkn_html_ncr_(931));}				// check for html_ncr; Σ: http://en.wikipedia.org/wiki/Numeric_character_reference
	@Test  public void Num_fail_incomplete()	{fxt.Test_parse_page_wiki("&#"				, fxt.tkn_txt_());}
	@Test  public void Convert_to_named()		{fxt.Test_parse_page_wiki_str("&amp;"		, "&amp;");}	// note that &amp; is printed, not &
	@Test  public void Convert_to_named_amp()	{fxt.Test_parse_page_wiki_str("&"			, "&amp;");}	// PURPOSE: html_wtr was not handling & only
	@Test  public void Convert_to_numeric()		{fxt.Test_parse_page_wiki_str("&aacute;"	, "&#225;");}	// testing that &#225; is outputted, not á
	@Test  public void Defect_bad_code_fails() { // PURPOSE: early rewrite of Xop_amp_mgr caused Xoh_html_wtr_escaper to fail with array out of bounds error; EX:w:Czech_Republic; DATE:2014-05-11
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|alt=<p>&#10;</p>]]"	// basically checks amp parsing inside xnde inside lnki's alt (which uses different parsing code
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"&#10;\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
	}
	@Test  public void Ignore_ncr() {	// PURPOSE: check that ncr is unescaped; PAGE:de.w:Cross-Site-Scripting; DATE:2014-07-23
		fxt.Test_parse_page_all_str
		( "a <code>&#60;iframe&#62;</code>) b"
		, "a <code>&#60;iframe&#62;</code>) b"	// &#60; should not become <
		);
	}
}
