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
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__pgbnr__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Test__bicode
		( "~%}$DP@A.png~\"A~~wpb-banner-image ~00"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"A\" xowa_title=\"A.png\"><img data-xowa-title='A.png' data-xoimg='1|-1|-1|-1|-1|-1' src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:-1px'></a>"
		);
	}
	@Test   public void Data_pos_x() {	// PAGE:en.v:Antartica; DATE:2016-07-12
		fxt.Test__bicode
		( "~%}$DP@A.png~\"A~~wpb-banner-image ~1.20"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"A\" xowa_title=\"A.png\"><img data-xowa-title='A.png' data-xoimg='1|-1|-1|-1|-1|-1' src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='1.2' data-pos-y='0' style='max-width:-1px'></a>"
		);
	}
}
