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
package gplx.xowa.htmls.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
public class Xoh_dom_tst {
	@Test  public void Find_atr() {
		String src = "a <nde0 atr0=\"val0\" atr1=\"val1\"/> b <nde1 atr0=\"val3\" atr1=\"val4\"/> c";
		tst_Find(src, "nde0", "atr0", "val0", "atr1", "val1");	// match nde0
		tst_Find(src, "nde1", "atr0", "val3", "atr1", "val4");	// match nde1
		tst_Find(src, "nde0", "atr0", "val5", "atr1", null);	// wrong val
		tst_Find(src, "nde0", "atr2", "val0", "atr1", null);	// wrong key
		tst_Find(src, "nde2", "atr0", "val0", "atr1", null);	// wrong nde
	}
	@Test  public void Title_by_href() {// PURPOSE: handle content-editable=n and finding file directly for download
		Title_by_href_tst
		( "/wiki/File:Bazille,_Fr%C3%A9d%C3%A9ric_%7E_Le_Petit_Jardinier_%28The_Little_Gardener%29,_c1866-67_oil_on_canvas_Museum_of_Fine_Arts,_Houston.jpg"
		, "<a href=\"lure\" xowa_title=\"wrong\"></a><a href=\"/wiki/File:Bazille,_Fr%C3%A9d%C3%A9ric_%7E_Le_Petit_Jardinier_%28The_Little_Gardener%29,_c1866-67_oil_on_canvas_Museum_of_Fine_Arts,_Houston.jpg\" xowa_title=\"find_me\"></a>"
		, "find_me"
		);
	}
	private void tst_Find(String src, String where_nde, String where_key, String where_val, String select_key, String expd) {
		Xoh_find rv = new Xoh_find();
		byte[] actl = Xoh_dom_.Query_val_by_where(rv, Bry_.new_u8(src), Bry_.new_u8(where_nde), Bry_.new_u8(where_key), Bry_.new_u8(where_val), Bry_.new_u8(select_key), 0);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	private void Title_by_href_tst(String href, String html_src, String expd) {
		String actl = Xoh_dom_.Title_by_href(Bry_.new_u8(href), Bry_.new_u8(html_src));
		Tfds.Eq(expd, actl);
	}
}
