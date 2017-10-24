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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import org.junit.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
import gplx.xowa.guis.views.*;
public class Xow_popup_anchor_finder__id_tst {
	@Before public void init() {fxt.Clear();} private Xop_popup_hdr_finder_fxt fxt = new Xop_popup_hdr_finder_fxt();
	@Test   public void Basic() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span id=\"a\"/>"
		, "c"
		);
		fxt.Test_find(src_str, "a",  1);
		fxt.Test_find_not(src_str, "b");
		fxt.Test_find_not(src_str, "c");
	}
	@Test   public void Ws() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span id = \"a\"/>"
		, "c"
		);
		fxt.Test_find(src_str, "a",  1);
	}
	@Test   public void Fail() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span xid = \"a\"/>"
		, "c"
		);
		fxt.Test_find_not(src_str, "a");
	}
}
