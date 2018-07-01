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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
public class Xoh_rtl_utl_tst {
	@Before public void init() {fxt.Init();} private Xoh_rtl_utl_fxt fxt = new Xoh_rtl_utl_fxt();
	@Test  public void Basic() {
		fxt.Test_reverse_li("<ul><li>a</li><li>b</li></ul>", "<ul><li>b</li><li>a</li></ul>");
	}
	@Test  public void Zero() {
		fxt.Test_reverse_li("a", "a");
	}
	@Test  public void One() {
		fxt.Test_reverse_li("<ul><li>a</li></ul>", "<ul><li>a</li></ul>");
	}
	@Test  public void Example() {
		fxt.Test_reverse_li(String_.Concat_lines_nl_skip_last
		( "<div>"
		, "  <ul>"
		, "    <li>a"
		, "    </li>"
		, "    <li>b"
		, "    </li>"
		, "    <li>c"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "<div>"
		, "  <ul>"
		, "    <li>c"
		, "    </li>"
		, "    <li>b"
		, "    </li>"
		, "    <li>a"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		));
	}
}
class Xoh_rtl_utl_fxt {
	public void Init() {
	}
	public void Test_reverse_li(String raw, String expd) {
		byte[] actl = Xoh_rtl_utl.Reverse_li(Bry_.new_u8(raw));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
