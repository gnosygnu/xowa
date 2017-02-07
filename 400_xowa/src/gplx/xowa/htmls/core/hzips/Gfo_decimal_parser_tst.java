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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import org.junit.*;
public class Gfo_decimal_parser_tst {
	private final Gfo_decimal_parser_fxt fxt = new Gfo_decimal_parser_fxt();
	@Test   public void Positive() {
		fxt.Test__parse("123"		, Bool_.Y,  0, 123);
		fxt.Test__parse("12.3"		, Bool_.Y, -1, 123);
		fxt.Test__parse("1.23"		, Bool_.Y, -2, 123);
		fxt.Test__parse(".123"		, Bool_.Y, -3, 123);
		fxt.Test__parse("0.123"		, Bool_.Y, -3, 123);
	}
	@Test   public void Negative() {
		fxt.Test__parse("-123"		, Bool_.N,  0, 123);
		fxt.Test__parse("-12.3"		, Bool_.N, -1, 123);
		fxt.Test__parse("-1.23"		, Bool_.N, -2, 123);
		fxt.Test__parse("-.123"		, Bool_.N, -3, 123);
		fxt.Test__parse("-0.123"	, Bool_.N, -3, 123);
	}
}
class Gfo_decimal_parser_fxt {
	private final Gfo_decimal_parser bicoder = new Gfo_decimal_parser();
	public void Test__parse(String src_str, boolean expd_sign, int expd_exponent, long expd_number) {
		byte[] src_bry = Bry_.new_u8(src_str);
		bicoder.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_bool(expd_sign, bicoder.Sign());
		Tfds.Eq_int(expd_exponent, bicoder.Exponent());
		Tfds.Eq_long(expd_number, bicoder.Number());
	}
}
