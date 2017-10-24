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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoa_url__basic__tst {
	private final    Xoa_url_fxt fxt = new Xoa_url_fxt();
	@Test  public void Eq_page() {
		fxt.Test_eq_page(Bool_.Y, "en.wikipedia.org/wiki/A?redirect=yes", "en.wikipedia.org/wiki/A?redirect=yes");
		fxt.Test_eq_page(Bool_.N, "en.wikipedia.org/wiki/A?redirect=no"	, "en.wikipedia.org/wiki/A?redirect=yes");
	}
}
class Xoa_url_fxt extends Xow_url_parser_fxt { 	public void Test_eq_page(boolean expd, String lhs_str, String rhs_str) {
		Xoa_url lhs_url = parser.Parse(Bry_.new_u8(lhs_str));
		Xoa_url rhs_url = parser.Parse(Bry_.new_u8(rhs_str));
		Tfds.Eq_bool(expd, lhs_url.Eq_page(rhs_url), "Eq_page");
	}
}
