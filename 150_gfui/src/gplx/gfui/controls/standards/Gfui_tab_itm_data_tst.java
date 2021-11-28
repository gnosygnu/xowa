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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import org.junit.*;
public class Gfui_tab_itm_data_tst {		
	@Before public void init() {} private Gfui_tab_itm_data_fxt fxt = new Gfui_tab_itm_data_fxt();
	@Test public void Get_idx_after_closing() {
		fxt.Test_Get_idx_after_closing(0, 1, -1);
		fxt.Test_Get_idx_after_closing(4, 5, 3);
		fxt.Test_Get_idx_after_closing(3, 5, 4);
	}
}
class Gfui_tab_itm_data_fxt {
	public void Test_Get_idx_after_closing(int cur, int len, int expd) {
		Tfds.Eq(expd, Gfui_tab_itm_data.Get_idx_after_closing(cur, len));
	}
}
