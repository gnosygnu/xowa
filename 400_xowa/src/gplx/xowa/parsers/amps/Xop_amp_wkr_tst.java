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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_amp_wkr_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
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
