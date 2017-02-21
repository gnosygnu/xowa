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
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__basic__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%!!A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Anch() {	// [[File:A.png#b|abc]]
		fxt.Test__bicode
		( "~%\"<A.png#file~A.png~)#Sabc~"
		, "<a href='/wiki/File:A.png#file' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_foreign() {	// [[File:Aéb.png|abc]]
		fxt.Test__bicode
		( "~%\"<A%C3%A9b.png~Aéb.png~)#Sabc~"
		, "<a href='/wiki/File:A%C3%A9b.png' class='image' xowa_title='Aéb.png'><img data-xowa-title='Aéb.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_question() {	// [[File:A?.png|abc]]; PAGE:en.w:Voiceless_alveolar_affricate; DATE:2016-01-04
		fxt.Test__bicode
		( "~%!qA?.png~)#Sabc~"
		, "<a href='/wiki/File:A?.png' class='image' xowa_title='A?.png'><img data-xowa-title='A?.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding__link() {	// [[File:Aéb.png|abc|link=Aéb]]
		fxt.Test__bicode
		( "~%#gA%C3%A9b~Aéb.png~#)#Sabc~"
		, "<a href='/wiki/A%C3%A9b' class='image' xowa_title='Aéb.png'><img data-xowa-title='Aéb.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__encoding_percent() {
		fxt.Test__bicode
		( "~%!q%24%3F%3D%27.png~)#Sabc~"
		, "<a href='/wiki/File:%24%3F%3D%27.png' class='image' xowa_title='%24%3F%3D%27.png'><img data-xowa-title='%24%3F%3D%27.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Href__apos() {		// [[File:A'b.png|border|link=A'b_link|A'b_capt]]
		String html = "<a href=\"/wiki/A%27b_link\" class=\"image\" xowa_title=\"A'b.png\"><img data-xowa-title=\"A'b.png\" data-xoimg=\"0|220|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" class=\"thumbborder\" alt=\"A'b_capt\"></a>";
		fxt.Test__bicode_raw("~%#oA%27b_link~A'b.png~#)#SA'b_capt~", html, html);
	}
	@Test   public void Href__image() {	// [[Image:A.png|abc]]
		fxt.Test__bicode
		( "~%-%A.png~Image~)#Sabc~"
		, "<a href='/wiki/Image:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
	}
	@Test   public void Ns__cs() {	// [[image:a.png|abc]]; PAGE:en.d:freedom_of_speech DATE:2016-01-21
		fxt.Wiki().Ns_mgr().Ns_file().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__bicode
		( "~%-%a.png~image~)#Sabc~"
		, "<a href='/wiki/image:a.png' class='image' title='abc' xowa_title='a.png'><img data-xowa-title='a.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
		fxt.Wiki().Ns_mgr().Ns_file().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__1st);
	}
	@Test   public void Ns__foreign() {	// [[Fichier:a.png|abc]]; PAGE:fr.w: DATE:2016-01-21
		Xow_ns_mgr ns_mgr = fxt.Wiki().Ns_mgr();
		ns_mgr.Ns_file().Name_bry_(Bry_.new_u8("Fichier")); ns_mgr.Init_w_defaults();
		fxt.Test__bicode
		( "~%!!a.png~)#Sabc~"
		, "<a href='/wiki/Fichier:a.png' class='image' title='abc' xowa_title='a.png'><img data-xowa-title='a.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		);
		ns_mgr.Ns_file().Name_bry_(Bry_.new_u8("File")); ns_mgr.Init_w_defaults();
	}
	@Test   public void Missing() { // PURPOSE: bad dump shouldn't write corrupt data
		fxt.Test__bicode
		( "%|\"\\QA.png!!!!A"
		, "<a href='/wiki/File:A.png' class='image' title='A' xowa_title='A.png'><img data-xowa-title='A.png' alt='A'></a>"
		);
	}
	@Test   public void Manual_img_cls() {	// PURPOSE: handle manual class; EX: [[File:A.png|class=noviewer]] en.w:ASCII; DATE:2015-12-21
		fxt.Test__bicode
		( "~%95A.png~)#Sabc~cls1~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='cls1' alt='abc'></a>"
		);
	}
	@Test   public void Imap() {
		fxt.Test__bicode
		( "~%}#P`uA.png~#:#S#+\""
		, "<img data-xowa-title='A.png' data-xoimg='1|220|180|-1|-1|-1' src='' width='0' height='0' alt='' usemap='#imagemap_1_1'>"
		);
	}
}
