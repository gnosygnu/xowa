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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_img_hzip__view__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic__border__class__caption() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%iAA.png#T\";abc~other~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' class='thumbborder other' alt='abc'></a>"
		);
	}
	@Test   public void Link() {	// [[File:A.png|link=B]]
		fxt.Test__bicode
		( "~%}'h+RB~A.png##T\";B~A.png~A.png~"
		, "<a href='/wiki/B' class='image' title='B' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' alt='A.png'></a>"
		);
	}
	// link_site: [[File:A.png|link=//en.wiktionary.org/B]] -> [[File:Wiktionary-logo-en.svg|25x25px|link=//en.wiktionary.org/wiki/Special:Search/Earth|Search Wiktionary]] -> <a href="/site/en.wiktionary.org/wiki/Special:Search/Earth" rel="nofollow" title="Search Wiktionary" xowa_title="Wiktionary-logo-en.svg"><img id="xoimg_88" alt="Search Wiktionary" src="file:///J:/xowa/file/commons.wikimedia.org/thumb/f/8/c/4/Wiktionary-logo-en.svg/23px.png" width="23" height="25"></a>
}
