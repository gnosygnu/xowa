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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_hzip_tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Free() {
		fxt.Test__bicode("~#!http://a.org~", Xoh_lnke_html__hdump__tst.Html__free);
	}
	@Test   public void Free__ws_at_end() {
		fxt.Test__bicode("~#%https://a.org/. ~https://a.org/.~", "<a href='https://a.org/. ' rel='nofollow' class='external free'>https://a.org/.</a>");
	}
	@Test   public void Auto() {
		fxt.Test__bicode("~#*http://a.org~\"", Xoh_lnke_html__hdump__tst.Html__auto);
	}
	@Test   public void Auto__tidy() {	// PURPOSE:handle reparenting of html elements by HTML tidy EX:<font color="red">[http://a.org]</font>; DATE:2015-08-25
		fxt.Test__bicode
		( "~#&http://a.org~<font color=\"red\">[123]</font>~"
		, "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external autonumber\"><font color=\"red\">[123]</font></a>"
		);
	}
	@Test   public void Auto__invalid_number() {
		String html = "<a href='http://a.org' rel='nofollow' class='external autonumber'>[abc]</a>";
		fxt.Test__bicode("~#&http://a.org~[abc]~", html);
	}
	@Test   public void Text() {
		fxt.Test__bicode("~#'http://a.org~a~", Xoh_lnke_html__hdump__tst.Html__text);
	}
	@Test   public void Wikivoyage__sleep() {
		fxt.Test__bicode
		( "~#7http://a.org~c~b~"
		, "<a href='http://a.org' rel='nofollow' class='external text' title='b'>c</a>");
	}
//		@Test   public void Xwiki__exists() {
//			String hzip			= "~#'https://en.wiktionary.org/wiki/A~A~";
//			String html_https	= "<a href='https://en.wiktionary.org/wiki/A' rel='nofollow' class='external text'>A</a>";
//			String html_xwiki	= "<a href='/site/en.wiktionary.org/wiki/A'>A</a>";
//			fxt.Test__bicode(hzip, html_https);
//			fxt.Init_wiki_installed("en.wiktionary.org");
//			fxt.Test__decode(hzip, html_xwiki);
//		}
	@Test   public void Fail__href() {
		String html = "<a rel='nofollow' class='external autonumber'>a</a>";
		fxt.Test__encode__fail(html, html);
	}
	@Test   public void Fail__lnke_type() {
		String html = "<a href='http://a.org' rel='nofollow' class='external invalid'>a</a>";
		fxt.Test__encode__fail(html, html);
	}
}
