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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Imap_xnde_html_itm_tst {
	@Before public void init() {fxt.Reset();} private Imap_html_bldr_fxt fxt = new Imap_html_bldr_fxt();
	@Test  public void Rect()	{fxt.Test_shape_html(fxt.itm_rect_("[[A|b]]", 1, 2, 3, 4), "\n        <area href=\"/wiki/A\" shape=\"rect\" coords=\"1,2,3,4\" alt=\"b\" title=\"b\"/>");}
	@Test  public void Circle() {fxt.Test_shape_html(fxt.itm_circle_("[[A|b]]", 1, 2, 3 ), "\n        <area href=\"/wiki/A\" shape=\"circle\" coords=\"1,2,3\" alt=\"b\" title=\"b\"/>");}
	@Test  public void Poly()	{fxt.Test_shape_html(fxt.itm_poly_("[[A|b]]", 1, 2, 3, 4), "\n        <area href=\"/wiki/A\" shape=\"poly\" coords=\"1,2,3,4\" alt=\"b\" title=\"b\"/>");}
}
class Imap_html_bldr_fxt extends Imap_fxt_base {
	private Bry_bfr bfr = Bry_bfr.new_();
	private Imap_pts_fmtr_arg pts_fmtr_arg = new Imap_pts_fmtr_arg();
	public void Test_shape_html(Imap_itm_shape shape, String expd) {
		Imap_shapes_fmtr.Fmt_shape(bfr, Imap_html_fmtrs.Area, pts_fmtr_arg, shape);
		Tfds.Eq(expd, bfr.Xto_str_and_clear());
	}
}
