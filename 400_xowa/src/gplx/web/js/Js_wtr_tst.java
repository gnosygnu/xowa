/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.web.js; import gplx.*; import gplx.web.*;
import org.junit.*;
public class Js_wtr_tst {
	@Before public void Init() {fxt.Clear();} private Js_wtr_fxt fxt = new Js_wtr_fxt();
	@Test   public void Basic() {
		fxt.Test_write_val_html("abc"					, "'abc'");
		fxt.Test_write_val_html("a'b"					, "'a\\'b'");
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
		wtr.Add_str_quote_html(Bry_.new_utf8_(raw));
		Tfds.Eq(expd, wtr.Xto_str_and_clear());
	}
}
