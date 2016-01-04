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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_img_hzip__view__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic__border__class__caption() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%iAA.png#T\";abc~other~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' class='thumbborder other' alt='abc'></a>"
		);
	}
	@Test   public void Link() {	// [[File:A.png|link=B]]
		fxt.Test__bicode
		( "~%SgB~A.png~##T\";B~A.png~"
		, "<a href='/wiki/B' class='image' title='B' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' alt='A.png'></a>"
		);
	}
	// link_site: [[File:A.png|link=//en.wiktionary.org/B]] -> [[File:Wiktionary-logo-en.svg|25x25px|link=//en.wiktionary.org/wiki/Special:Search/Earth|Search Wiktionary]] -> <a href="/site/en.wiktionary.org/wiki/Special:Search/Earth" rel="nofollow" title="Search Wiktionary" xowa_title="Wiktionary-logo-en.svg"><img id="xoimg_88" alt="Search Wiktionary" src="file:///J:/xowa/file/commons.wikimedia.org/thumb/f/8/c/4/Wiktionary-logo-en.svg/23px.png" width="23" height="25"></a>
}
