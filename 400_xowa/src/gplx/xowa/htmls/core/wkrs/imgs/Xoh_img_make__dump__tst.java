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
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_img_make__dump__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void Init() {fxt.Clear();}
	@Test   public void Html_exists__n() {
		String
		  orig = "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' data-xoimg='0|220|110|0.5|-1|-1' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='220' height='110'></a>"
		, expd = "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_0' alt='abc'></a>"
		;
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("en.w", "A.png", Xop_lnki_type.Id_null, 0.5, 220, 110, -1, -1)
		);
	}
//		@Test   public void Html_exists__y__cache() {
//			String
//			  orig = "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' data-xoimg='0|220|110|0.5|-1|-1' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='220' height='110'/></a>"
//			, expd = "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_0' alt='abc' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/B.png/330px.png' width='330' height='110'/></a>"
//			;
//			fxt.Init_img_cache("en.w", "A.png", 0, 0.5, 220, 110, -1, -1, Bool_.Y, "B.png", 330, 110, -1, -1);
//			fxt.Test__make(orig
//			, fxt.Page_chkr().Body_(expd)
//				.Imgs__add("en.w", "A.png", 0, 0.5, 220, 110, -1, -1)
//			);
//		}
//		@Test   public void Html_exists__y__html() {
//			String
//			  orig = "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='330' height='110'/></a>"
//			, expd = "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_0' alt='abc' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/B.png/330px.png' width='330' height='110'/></a>"
//			;
//			fxt.Test__make(orig
//			, fxt.Page_chkr().Body_(expd)
//				.Imgs__add("en.w", "A.png", 0, 0.5, 220, 110, -1, -1)
//			);
//		}
}
