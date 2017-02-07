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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import org.junit.*;
public class Xoh_img_cls__tst {
	private Xoh_img_cls__fxt fxt = new Xoh_img_cls__fxt();
	@Test  public void To_html() {
		fxt.Test__to_html(Xoh_img_cls_.Tid__none		, null	, "");
		fxt.Test__to_html(Xoh_img_cls_.Tid__none		, "a"	, " class=\"a\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbimage	, null	, " class=\"thumbimage\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbborder	, null	, " class=\"thumbborder\"");
		fxt.Test__to_html(Xoh_img_cls_.Tid__thumbborder	, "a"	, " class=\"thumbborder a\"");
	}
}
class Xoh_img_cls__fxt {
	public void Test__to_html(byte tid, String other, String expd) {
		Tfds.Eq(expd, String_.new_u8(Xoh_img_cls_.To_html(tid, Bry_.new_u8_safe(other))));
	}
}
