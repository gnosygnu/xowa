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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.htmls.*;
public class Gallery_mgr_base__xatrs__tst {		
	@Before public void init() {fxt.Reset();} private final    Gallery_mgr_base_fxt fxt = new Gallery_mgr_base_fxt();
	@Test  public void Atr_misc() {// PURPOSE: add other atrs to end of ul
		fxt.Test_html_frag
		( "<gallery id=a>File:A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\" id=\"a\">"
		);
	}
	@Test  public void Atr_style() {	// PURPOSE: combine style with itms_per_row
		fxt.Test_html_frag
		( "<gallery perrow=2 style='color:blue;'>File:A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\" style=\"max-width:326px; _width:326px; color:blue;\">"
		);
	}
	@Test  public void Atr_whitelist() {	// PURPOSE: ignore atrs not in whitelist
		fxt.Test_html_frag
		( "<gallery onmouseover='alert();'>A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
		);
	}
	@Test  public void Mgr_caption() {		// PURPOSE: caption atr adds new element
		fxt.Test_html_frag
		( "<gallery caption=B>File:A.png</gallery>"
		, "<li class=\"gallerycaption\">B</li>"
		);
	}
	@Test  public void Mgr_caption_ignore() {	// PURPOSE: blank caption should not create caption element; PAGE:fr.w:Chronologie_du_si�ge_de_Paris_(1870) DATE:2014-08-15
		fxt.Test_html_frag_n
		( "<gallery caption=>File:A.png</gallery>"
		, "<li class=\"gallerycaption\">"
		);
	}
	@Test  public void Show_filename() {	// PURPOSE: show filename
		fxt.Test_html_frag
		( "<gallery showfilename=true>A.png</gallery>"
		, "<div class=\"gallerytext\"><a href=\"/wiki/File:A.png\">File:A.png</a>"
		);
	}
	//@Test  public void Ttl_caption() {	// PURPOSE: category entries get rendered with name only (no ns)
	//	fxt.Test_html_frag
	//	( "<gallery>Category:A</gallery>"
	//	, "<li class='gallerycaption'>B</li>"
	//	);
	//}
}
