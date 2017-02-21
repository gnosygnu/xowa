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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xop_parser__tst {
	@Before public void init() {fxt.Clear();} private Xop_parser__fxt fxt = new Xop_parser__fxt();
	@Test  public void Para_y() {
		fxt.Test_parse_to_html(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, "b"
		), true, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		));
	}
	@Test  public void Para_n() {
		fxt.Test_parse_to_html(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, "b"
		), false, String_.Concat_lines_nl_skip_last
		( "a"
		, "b"
		));
	}
}
class Xop_parser__fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public void Clear() {
		fxt.Reset();
	}
	public void Test_parse_to_html(String raw, boolean para_enabled, String expd)  {
		byte[] raw_bry = Bry_.new_u8(raw);
		fxt.Wiki().Parser_mgr().Main().Parse_text_to_html(bfr, fxt.Ctx(), fxt.Page(), para_enabled, raw_bry);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
