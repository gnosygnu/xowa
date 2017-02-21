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
package gplx.core.js; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Js_wtr_tst {
	@Before public void Init() {fxt.Clear();} private Js_wtr_fxt fxt = new Js_wtr_fxt();
	@Test   public void Basic() {
		fxt.Test_write_val_html("abc"					, "'abc'");
		fxt.Test_write_val_html("a'b"					, "'a\\'b'");
		fxt.Test_write_val_html("a\"b"					, "'a\\\"b'");
		fxt.Test_write_val_html("a\nb"					, "'a\\nb'");
		fxt.Test_write_val_html("a\rb"					, "'ab'");
		fxt.Test_write_val_html("a\\&b"					, "'a\\\\&b'");		// PURPOSE: backslashes need to be escaped; need for MathJax and "\&"; PAGE:Electromagnetic_field_tensor; DATE:2014-06-24
	}
}
class Js_wtr_fxt {
	private Js_wtr wtr = new Js_wtr();
	public void Clear() {
		wtr.Clear();
		wtr.Quote_char_(Byte_ascii.Apos);
	}
	public void Test_write_val_html(String raw, String expd) {
		wtr.Write_val(Bry_.new_u8(raw));
		Tfds.Eq(expd, wtr.To_str_and_clear());
	}
}
