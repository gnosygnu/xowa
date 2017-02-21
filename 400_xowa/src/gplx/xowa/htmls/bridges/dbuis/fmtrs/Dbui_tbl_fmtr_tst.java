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
package gplx.xowa.htmls.bridges.dbuis.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
import org.junit.*;
public class Dbui_tbl_fmtr_tst {
	@Before public void init() {fxt.Clear();} private final    Dbui_tbl_fmtr_fxt fxt = new Dbui_tbl_fmtr_fxt();
	@Test  public void Basic() {
//			fxt.Test_write
//			( fxt.Make_tbl()
//			, String_.Concat_lines_nl_skip_last()
//			);
	}
}
class Dbui_tbl_fmtr_fxt {
	private final    Bry_bfr bfr = Bry_bfr_.New_w_size(255);
	private final    Dbui_tbl_fmtr tbl_fmtr = new Dbui_tbl_fmtr();
	public void Clear() {}
	public Dbui_tbl_itm Make_tbl() {
		return null;
	}
	public void Test_write(Dbui_tbl_itm tbl, String expd) {
		tbl_fmtr.Write(bfr, tbl, null, null, null);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
