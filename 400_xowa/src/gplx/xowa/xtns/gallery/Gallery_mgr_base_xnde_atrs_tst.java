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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.htmls.*;
public class Gallery_mgr_base_xnde_atrs_tst {
	private Gallery_mgr_base_fxt fxt = new Gallery_mgr_base_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Atr_misc() {// PURPOSE: add other atrs to end of ul
		fxt.Test_html_frag
		( "<gallery id=a>File:A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\" id=\"a\">"
		);
	}
	@Test  public void Atr_style() {	// PURPOSE: combine style with itms_per_row
		fxt.Test_html_frag
		( "<gallery perrow=2 style='color:blue;'>File:A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\" style=\"max-width:326px;_width:326px; color:blue;\">"
		);
	}
	@Test  public void Atr_caption() {	// PURPOSE: caption atr adds new element
		fxt.Test_html_frag
		( "<gallery caption=B>File:A.png</gallery>"
		, "<li class='gallerycaption'>B</li>"
		);
	}
	@Test  public void Atr_caption_ignore() {	// PURPOSE: blank caption should not create caption element; PAGE:fr.w:Chronologie_du_si�ge_de_Paris_(1870) DATE:2014-08-15
		fxt.Test_html_frag_n
		( "<gallery caption=>File:A.png</gallery>"
		, "<li class='gallerycaption'>"
		);
	}
	@Test  public void Atr_show_filename() {	// PURPOSE: show filename
		fxt.Test_html_frag
		( "<gallery showfilename=true>A.png</gallery>"
		, "<div class=\"gallerytext\"><a href=\"/wiki/File:A.png\">File:A.png</a>"
		);
	}
	@Test  public void Atr_whitelist() {	// PURPOSE: ignore atrs not in whitelist
		fxt.Test_html_frag
		( "<gallery onmouseover='alert();'>A.png</gallery>"
		, "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
		);
	}
//		@Test  public void Ttl_caption() {	// TODO: PURPOSE: category entries get rendered with name only (no ns)
//			fxt.Test_html_frag
//			( "<gallery>Category:A</gallery>"
//			, "<li class='gallerycaption'>B</li>"
//			);
//		}
}
