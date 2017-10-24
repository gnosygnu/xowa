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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.addons.htmls.tocs.*;
public class Xoh_hdr_hzip_tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Before public void init()		{Xoh_toc_mgr.Enabled = false;}
	@After public void term()	{Xoh_toc_mgr.Enabled = true;}
	@Test   public void Same() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"'A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h6><span class='mw-headline' id='A'>A</span></h6>"
		, "a"
		));
	}
	@Test   public void Diff() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"+<i>A</i>~A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A'><i>A</i></span></h2>"
		, "a"
		));
	}
	@Test   public void Diff_by_underscore() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"#A 1~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A_1'>A 1</span></h2>"
		, "a"
		));
	}
	@Test   public void Diff_by_lnki() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"+<a href=\"/wiki/Category:A\" title=\"Category:A\">Category:A</a>~Category:A~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='Category:A'><a href='/wiki/Category:A' title='Category:A'>Category:A</a></span></h2>"
		, "a"
		));
	}
	@Test   public void Same_w_underscore() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~\"#A_1~"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A_1'>A_1</span></h2>"
		, "a"
		));
	}
	@Test   public void Tidy__bad_end() {
		fxt.Test__bicode(
		"~\"?A~AB~B~"
		, "<h6><span class='mw-headline' id='AB'>A</span>B</h6>"
		);
	}
	@Test   public void Tidy__no_span() {	// PURPOSE.TIDY: tidy will duplicate hdr if content has center; will fail if span/div is nearby; EX: ==<center>A</center>==\n<span><div>; PAGE:en.s:On_the_Vital_Principle/Book_2/Prelude_to_Chapter_2 DATE:2016-01-21
		fxt.Test__encode(String_.Concat_lines_nl_skip_last
		( "\"+A"
		, "<center>"
		, "<h2>A</h2>"
		, "</center>"
		, "<span class=\"mw-headline\" id=\"A\"></span>"
		, "<div style=\"color:blue;\">"
		, "<p>abc</p>"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "<h2><span class='mw-headline' id='A'></span></h2>"
		, "<center>"
		, "<h2>A</h2>"
		, "</center>"
		, "<span class='mw-headline' id='A'></span>"
		, "<div style='color:blue;'>"
		, "<p>abc</p>"
		, "</div>"
		));
	}
	@Test   public void Manual__no_id() {// PURPOSE: ignore manual "<h2>" with no id; PAGE:fr.w:Portail:Nord-Am�rindiens/Image_s�lectionn�e; DATE:2016-07-01
		fxt.Test__bicode
		( "<h6><span class=\"mw-headline\">A</span></h6>"
		, "<h6><span class='mw-headline'>A</span></h6>"
		);
	}
	@Test   public void Manual__h_has_atrs() {// PURPOSE: ignore manual "<h2>" with atrs; PAGE:fr.w:Wikip�dia:LiveRC/ToDo; DATE:2016-07-02
		fxt.Test__bicode
		( "<h6 style=\"color:red\"><span class=\"mw-headline\" id=\"A\">B</span></h6>"
		, "<h6 style=\"color:red\"><span class=\"mw-headline\" id=\"A\">B</span></h6>"
		);
	}
}
