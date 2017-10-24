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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.ios.*; import gplx.xowa.wikis.nss.*;
public class Xoa_css_extractor_wiki_tst {
	@Before public void init() {fxt.Clear();} private Xoa_css_extractor_fxt fxt = new Xoa_css_extractor_fxt();
	@Test   public void Css_wiki_generate() {
		fxt.Init_page(Xow_ns_.Tid__mediawiki, "Common.css"					, "css_0");
		fxt.Init_page(Xow_ns_.Tid__mediawiki, "Vector.css"					, "css_1");
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", String_.Concat_lines_nl
		(	"/*XOWA:MediaWiki:Common.css*/"
		,	"css_0"
		,	""
		,	"/*XOWA:MediaWiki:Vector.css*/"
		,	"css_1"
		));
	}
	@Test   public void Css_wiki_missing() {
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", "");
	}
	@Test  public void Css_wiki_tab() {	// PURPOSE: swap out &#09; for xdat files
		fxt.Init_page(Xow_ns_.Tid__mediawiki, "Common.css"					, "a&#09;b");
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", String_.Concat_lines_nl
		(	"/*XOWA:MediaWiki:Common.css*/"
		,	"a\tb"
		));
	}
}
