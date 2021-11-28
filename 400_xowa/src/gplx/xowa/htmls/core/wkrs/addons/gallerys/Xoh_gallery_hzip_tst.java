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
package gplx.xowa.htmls.core.wkrs.addons.gallerys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_gallery_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test  public void Basic() {
		fxt.Test__decode
		( "<ul class=\"gallery mw-gallery-traditional style=\"max-width:489px; _width:489px;\">"
		, "<ul class=\"gallery mw-gallery-traditional style=\"max-width:489px; _width:489px;\">"
		);
		Gftest.Eq__bool_y(fxt.Page().Xtn__gallery_exists());
	}
}
