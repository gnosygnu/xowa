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
package gplx.xowa.xtns.insiders; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Insider_func_tst {
	@Before public void init()				{fxt.Reset();} private Insider_func_fxt fxt = new Insider_func_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("{{#insider:A}}x{{#insider:B}}", "x", "A", "B");
	}
}
class Insider_func_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
	}
	public void Test_parse(String raw, String expd, String... insiders) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", expd);
		Insider_xtn_skin_itm skin_itm = (Insider_xtn_skin_itm)fxt.Page().Html_data().Xtn_skin_mgr().Get_or_null(Insider_xtn_skin_itm.KEY);
		List_adp list = skin_itm.Itms();
		byte[][] brys = (byte[][])list.To_ary(byte[].class);
		Tfds.Eq_ary_str(insiders, String_.Ary(brys));
	}
}
