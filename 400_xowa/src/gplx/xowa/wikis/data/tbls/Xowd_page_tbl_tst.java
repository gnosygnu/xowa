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
package gplx.xowa.wikis.data.tbls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xowd_page_tbl_tst {
	private Xowd_page_tbl_fxt fxt = new Xowd_page_tbl_fxt();
	@Test public void Find_search_end() {
		fxt.Test_find_search_end("ab", "ac");
		fxt.Test_find_search_end("ab%", "ac%");
	}
}
class Xowd_page_tbl_fxt {
	public void Test_find_search_end(String val, String expd) {GfoTstr.EqObj(expd, StringUtl.NewU8(Find_search_end(BryUtl.NewU8(val))));}
	private static byte[] Find_search_end(byte[] orig) {	// NOTE: moved from old Xowd_page_tbl; is probably obsolete
		byte[] rv = BryUtl.Copy(orig);
		int rv_len = rv.length;
		int increment_pos = rv[rv_len - 1] == AsciiByte.Percent ? rv_len - 2 : rv_len - 1;	// increment last char, unless it is %; if %, increment one before it
		return BryUtl.IncrementLast(rv, increment_pos);
	}
}
