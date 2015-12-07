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
		( "~%!!A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Anch() {	// [[File:A.png#b|abc]]
		fxt.Test__bicode
		( "~%\"<A.png#file~A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png#file' class='image' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__cs() {	// [[File:A.png|link=File:a.ogg|abc]]
		fxt.Test__bicode
		( "~%!Aa.ogg~A.png~)#Sabc~"
		, "<a href='/wiki/File:a.ogg' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding() {	// [[File:Aéb.png|abc]]
		fxt.Test__bicode
		( "~%!qAéb.png~)#Sabc~"
		, "<a href='/wiki/File:A%C3%A9b.png' class='image' xowa_title='Aéb.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding__link() {	// [[File:Aéb.png|abc|link=Aéb]]
		fxt.Test__bicode
		( "~%#gAéb~Aéb.png~#)#Sabc~"
		, "<a href='/wiki/A%C3%A9b' class='image' xowa_title='Aéb.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__apos() {		// [[File:A'b.png|border|link=A'b_link|A'b_capt]]
		String html = "<a href=\"/wiki/A%27b_link\" class=\"image\" xowa_title=\"A'b.png\"><img id=\"xoimg_0\" data-xoimg=\"0|220|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" class=\"thumbborder\" alt=\"A'b_capt\"></a>";
		fxt.Test__bicode_raw("~%#oA'b_link~A'b.png~#)#SA'b_capt~", html, html);
	}
	@Test   public void Link__wm__n() {	// [[File:A.png|link=http://a.org|abc]]
		fxt.Test__bicode
		( "~%!Dhttp://a.org~A.png~)#Sabc~"
		, "<a href='http://a.org' rel='nofollow' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__y() {	// [[File:A.png|link=http://en.wikitionary.org/wiki/Special:Search/A|abc]]
		fxt.Test__bicode
		( "~%\"men.wiktionary.org|Search/A~A.png~\")#Sabc~"
		, "<a href='/site/en.wiktionary.org/wiki/Special:Search/A' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	// lhs='<a href="/site/en.wiktionary.org/wiki/Special:Search/A" class="image" title="B" xowa_title="Commons-logo.svg"><img data-xoimg="0|40|40|-1|-1|-1" src="" width="0" height="0" alt="B"></a>
	@Test   public void Href__image() {	// [[Image:A.png|abc]]
		fxt.Test__bicode
		( "~%-%A.png~Image~)#Sabc~"
		, "<a href='/wiki/Image:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Missing() { // PURPOSE: bad dump shouldn't write corrupt data
		fxt.Test__bicode
		( "%|\"\\QA.png!!!!A"
		, "<a href='/wiki/File:A.png' class='image' title='A' xowa_title='A.png'><img id='xoimg_0' alt='A'></a>"
		);
	}
}
