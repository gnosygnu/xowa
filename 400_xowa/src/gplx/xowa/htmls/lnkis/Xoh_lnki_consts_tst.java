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
package gplx.xowa.htmls.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
public class Xoh_lnki_consts_tst {
	private Xoh_lnki_consts_fxt fxt = new Xoh_lnki_consts_fxt();
	@Test  public void Img_cls_to_bry() {
		fxt.Test_img_cls_to_bry(Xoh_lnki_consts.Tid_img_cls_none		, null	, "");
		fxt.Test_img_cls_to_bry(Xoh_lnki_consts.Tid_img_cls_none		, "a"	, " class=\"a\"");
		fxt.Test_img_cls_to_bry(Xoh_lnki_consts.Tid_img_cls_thumbimage	, null	, " class=\"thumbimage\"");
		fxt.Test_img_cls_to_bry(Xoh_lnki_consts.Tid_img_cls_thumbborder	, null	, " class=\"thumbborder\"");
		fxt.Test_img_cls_to_bry(Xoh_lnki_consts.Tid_img_cls_thumbborder	, "a"	, " class=\"thumbborder a\"");
	}
}
class Xoh_lnki_consts_fxt {
	public void Test_img_cls_to_bry(byte tid, String other, String expd) {
		Tfds.Eq(expd, String_.new_u8(Xoh_lnki_consts.Img_cls_to_bry(tid, Bry_.new_u8_safe(other))));
	}
}
