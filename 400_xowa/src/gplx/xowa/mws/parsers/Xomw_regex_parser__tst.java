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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_regex_parser__tst {
	private final    Xomw_regex_parser__fxt fxt = new Xomw_regex_parser__fxt();
	@Test   public void Ary__space() {
		fxt.Test__parse_ary(String_.Ary("\\s"), String_.Ary(" "));
	}
	@Test   public void Ary__utf8() {
		fxt.Test__parse_ary(String_.Ary("\\xc2\\xa7", "\\xe0\\xb9\\x90"), String_.Ary("§", "๐"));
	}
	@Test   public void Rng__ascii() {
		fxt.Test__parse_rng("a", "c", String_.Ary("a", "b", "c"));
	}
}
class Xomw_regex_parser__fxt {
	private final    Xomw_regex_parser parser = new Xomw_regex_parser();
	public void Test__parse_ary(String[] ary, String[] expd) {
		parser.Add_ary(ary);
		Gftest.Eq__ary(expd, String_.Ary(parser.Rslt()));
	}
	public void Test__parse_rng(String bgn, String end, String[] expd) {
		parser.Add_rng("a", "c");
		Gftest.Eq__ary(expd, String_.Ary(parser.Rslt()));
	}
}
