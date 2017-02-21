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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_lnki_html__hdump__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Same()		{fxt.Test__html("[[A]]"				, "<a href='/wiki/A' title='A'>A</a>");}
	@Test   public void Diff()		{fxt.Test__html("[[A|b]]"			, "<a href='/wiki/A' title='A'>b</a>");}
	@Test   public void Trail()		{fxt.Test__html("[[A]]b"			, "<a href='/wiki/A' title='A'>Ab</a>");}
	@Test   public void Xwiki()	{
		fxt.Parser_fxt().Init_xwiki_add_wiki_and_user_("wikt", "en.wiktionary.org");
		fxt.Test__html("[[wikt:a]]", "<a href='https://en.wiktionary.org/wiki/a' title='a'>wikt:a</a>");
	}
	@Test   public void Anch()		{fxt.Test__html("[[#a]]"			, "<a href='#a'>#a</a>");}
	@Test   public void Alt_has_quotes() {
		fxt.Test__html("[[File:A.png|alt=[[\"A\"]] B c]]", "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img data-xowa-title=\"A.png\" data-xoimg=\"0|-1|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"&quot;A&quot; B c\"/></a>");
	}
}
