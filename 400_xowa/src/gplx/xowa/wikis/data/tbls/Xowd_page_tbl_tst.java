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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import org.junit.*; import gplx.xowa.bldrs.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xowd_page_tbl_tst {
	private Xowd_page_tbl_fxt fxt = new Xowd_page_tbl_fxt();
	@Test  public void Find_search_end() {
		fxt.Test_find_search_end("ab", "ac");
		fxt.Test_find_search_end("ab%", "ac%");
	}
}
class Xowd_page_tbl_fxt {
	public void Test_find_search_end(String val, String expd) {Tfds.Eq(expd, String_.new_u8(Find_search_end(Bry_.new_u8(val))));}
	private static byte[] Find_search_end(byte[] orig) {	// NOTE: moved from old Xowd_page_tbl; is probably obsolete
		byte[] rv = Bry_.Copy(orig);
		int rv_len = rv.length;
		int increment_pos = rv[rv_len - 1] == Byte_ascii.Percent ? rv_len - 2 : rv_len - 1;	// increment last char, unless it is %; if %, increment one before it
		return Bry_.Increment_last(rv, increment_pos);
	}
}
