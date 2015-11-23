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
public class Xoh_img_hzip__dump__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%!!A.png~0|220|110|0.5|-1|-1~abc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding() {	// [[File:Aéb.png|abc]]
		fxt.Test__bicode
		( "~%!!Aéb.png~0|220|110|0.5|-1|-1~abc~"
		, "<a href='/wiki/File:A%C3%A9b.png' class='image' title='abc' xowa_title='Aéb.png'><img id='xoimg_0' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__n() {	// [[File:A.png|link=http://a.org|abc]]
		fxt.Test__bicode
		( "~%!Dhttp://a.org~A.png~0|220|110|0.5|-1|-1~abc~"
		, "<a href='http://a.org' rel='nofollow' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
//		@Test   public void Link__wm__y() {	// [[File:A.png|link=//en.wiktionary.org/wiki/A|abc]]
//			fxt.Test__bicode
//			( "~%!i=!!!!A~abc~"
//			, "<a href='/site/en.wiktionary.org/wiki/A' class='image' title='abc'><img id='xoimg_0' alt='abc'></a>"
//			);
//		}
	@Test   public void Href__image() {	// [[Image:A.png|abc]]
		fxt.Test__bicode
		( "~%-%A.png~0|220|110|0.5|-1|-1~abc~"
		, "<a href='/wiki/Image:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Missing() { // PURPOSE: bad dump shouldn't write corrupt data
		fxt.Test__bicode
		( "%|\"\\QA.png!!!!A"
		, "<a href='/wiki/File:A.png' class='image' title='A' xowa_title='A.png'><img id='xoimg_0' alt='A'></a>"
		);
	}
}
