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
package gplx.xowa.xtns.imaps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import org.junit.*; import gplx.xowa.xtns.imaps.itms.*;
public class Imap_shapes_arg_tst {
	@Before public void init() {fxt.Reset();} private Imap_shapes_arg_fxt fxt = new Imap_shapes_arg_fxt();
	@Test  public void Rect()	{fxt.Test_shape_html(fxt.itm_rect_("[[A|b]]", 1, 2, 3, 4), "\n        <area href=\"/wiki/A\" shape=\"rect\" coords=\"1,2,3,4\" alt=\"b\" title=\"b\"/>");}
	@Test  public void Circle() {fxt.Test_shape_html(fxt.itm_circle_("[[A|b]]", 1, 2, 3 ), "\n        <area href=\"/wiki/A\" shape=\"circle\" coords=\"1,2,3\" alt=\"b\" title=\"b\"/>");}
	@Test  public void Poly()	{fxt.Test_shape_html(fxt.itm_poly_("[[A|b]]", 1, 2, 3, 4), "\n        <area href=\"/wiki/A\" shape=\"poly\" coords=\"1,2,3,4\" alt=\"b\" title=\"b\"/>");}
}
class Imap_shapes_arg_fxt extends Imap_base_fxt {
	public void Test_shape_html(Imap_part_shape shape, String expd) {
		Bry_bfr bfr = Bry_bfr_.New();
		Imap_shape_pts_arg pts_fmtr_arg = new Imap_shape_pts_arg(1);
		Imap_shapes_arg.Fmt_shape(bfr, Imap_html_fmtrs.Area, pts_fmtr_arg, shape);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
