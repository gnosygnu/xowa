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
package gplx;
import org.junit.*;
public class DateAdp_parser_tst {
	@Before public void init() {} DateAdp_parser_fxt fxt = new DateAdp_parser_fxt();
	@Test  public void Parse_gplx() {
		fxt.Test_Parse_iso8651_like("2000-01-02T03:04:05.006-05:00"		, 2000, 1, 2, 3, 4, 5, 6);
		fxt.Test_Parse_iso8651_like("2000-01-02"						, 2000, 1, 2, 0, 0, 0, 0);
	}
}
class DateAdp_parser_fxt {
	DateAdp_parser parser = DateAdp_parser.new_(); int[] actl = new int[7];
	public void Test_Parse_iso8651_like(String s, int... expd) {
		byte[] bry = Bry_.new_a7(s);
		parser.Parse_iso8651_like(actl, bry, 0, bry.length);
		Tfds.Eq_ary(expd, actl, s);
	}
}
