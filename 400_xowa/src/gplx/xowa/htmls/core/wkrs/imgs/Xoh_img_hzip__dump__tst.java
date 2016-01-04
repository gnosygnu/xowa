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
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%!!A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Anch() {	// [[File:A.png#b|abc]]
		fxt.Test__bicode
		( "~%\"<A.png#file~A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png#file' class='image' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__cs() {	// [[File:A.png|link=File:a.ogg|abc]]
		fxt.Test__bicode
		( "~%!Aa.ogg~A.png~)#Sabc~"
		, "<a href='/wiki/File:a.ogg' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding() {	// [[File:Aéb.png|abc]]
		fxt.Test__bicode
		( "~%!qAéb.png~)#Sabc~"
		, "<a href='/wiki/File:A%C3%A9b.png' class='image' xowa_title='Aéb.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding__link() {	// [[File:Aéb.png|abc|link=Aéb]]
		fxt.Test__bicode
		( "~%#gAéb~Aéb.png~#)#Sabc~"
		, "<a href='/wiki/A%C3%A9b' class='image' xowa_title='Aéb.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__apos() {		// [[File:A'b.png|border|link=A'b_link|A'b_capt]]
		String html = "<a href=\"/wiki/A%27b_link\" class=\"image\" xowa_title=\"A'b.png\"><img data-xoimg=\"0|220|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" class=\"thumbborder\" alt=\"A'b_capt\"></a>";
		fxt.Test__bicode_raw("~%#oA'b_link~A'b.png~#)#SA'b_capt~", html, html);
	}
	@Test   public void Link__wm__n() {	// [[File:A.png|link=http://a.org|abc]]
		fxt.Test__bicode
		( "~%!Dhttp://a.org~A.png~)#Sabc~"
		, "<a href='http://a.org' rel='nofollow' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__y() {	// [[File:A.png|link=http://en.wikitionary.org/wiki/Special:Search/A|abc]]
		fxt.Test__bicode
		( "~%\"men.wiktionary.org|Search/A~A.png~\")#Sabc~"
		, "<a href='/site/en.wiktionary.org/wiki/Special:Search/A' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Link__wm__n_2() {	// [[File:A.png|link=creativecommons:by/2.5]]
		fxt.Test__bicode
		( "~%#(creativecommons.org|by/2.5/~CC-BY-icon-80x15.png~#)!q"
		, "<a href='/site/creativecommons.org/wiki/by/2.5/' class='image' xowa_title='CC-BY-icon-80x15.png'><img data-xoimg='0|80|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
		);
	}
	@Test   public void Link__media() {	// [[File:A.png|link=file:///C:/A.ogg]]
		fxt.Test__bicode
		( "~%!D~A.ogg~)!,B~"
		, "<a href='' class='image' title='B' xowa_title='A.ogg'><img data-xoimg='0|11|-1|-1|-1|-1' src='' width='0' height='0' alt='B'></a>"
		);
	}
//		@Test   public void Link__encoding() {	// [[File:A.svg|24px|text-top|link=wikt:𬖾|𬖾]]; PAGE:en.w:Pho
//			fxt.Test__bicode
//			( "~%#Xen.wiktionary.org|𬖾~A.png~#)!,abc~B~"
//			, "<a href='/site/en.wiktionary.org/wiki/%F0%AC%96%BE' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|11|-1|-1|-1|-1' src='' width='0' height='0' alt='B'></a>"
//			);
//		}
	@Test   public void Href__image() {	// [[Image:A.png|abc]]
		fxt.Test__bicode
		( "~%-%A.png~Image~)#Sabc~"
		, "<a href='/wiki/Image:A.png' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Missing() { // PURPOSE: bad dump shouldn't write corrupt data
		fxt.Test__bicode
		( "%|\"\\QA.png!!!!A"
		, "<a href='/wiki/File:A.png' class='image' title='A' xowa_title='A.png'><img alt='A'></a>"
		);
	}
	@Test   public void Manual_img_cls() {	// PURPOSE: handle manual class; EX: [[File:A.png|class=noviewer]] en.w:ASCII; DATE:2015-12-21
		fxt.Test__bicode
		( "~%95A.png~)#Sabc~cls1~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='cls1' alt='abc'></a>"
		);
	}
	@Test   public void Video() {	// [[File:A.ogv]]
		fxt.Test__bicode
		( "%|E9eA.ogv~!A.ogv~~", String_.Concat_lines_nl_skip_last
		( "<div class='xowa_media_div'>"
		, "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "<div><a href='' xowa_title='A.ogv' class='xowa_anchor_button' style='width:218px;max-width:220px;'><img src='' width='22' height='22' alt='Play sound'></a></div>"
		, "</div>"
		));
	}
}
