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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_string_utl__tst {
	private final    Xomw_string_utl__fxt fxt = new Xomw_string_utl__fxt();
	@Test  public void Basic() {
		fxt.Test__replace_markup("a!!b"             , "!!", "||", "a||b");
	}
	@Test  public void Missing() {
		fxt.Test__replace_markup("abcd"             , "!!", "||", "abcd");
	}
	@Test  public void Eos() {
		fxt.Test__replace_markup("a!!"              , "!!", "||", "a||");
	}
	@Test  public void Ignore() {
		fxt.Test__replace_markup("a!!b<!!>!!c"      , "!!", "||", "a||b<!!>||c");
	}
	@Test  public void Ignore__asym__lhs() {
		fxt.Test__replace_markup("a!!b<!!<!!>!!c"   , "!!", "||", "a||b<!!<!!>||c");
	}
	@Test  public void Ignore__asym__rhs() {
		fxt.Test__replace_markup("a!!b<!!>!!>!!c"   , "!!", "||", "a||b<!!>||>||c");	// NOTE: should probably be "!!>!!>", but unmatched ">" are escaped to "&gt;"
	}
}
class Xomw_string_utl__fxt {
	public void Test__replace_markup(String src_str, String find, String repl, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Xomw_string_utl.Replace_markup(src_bry, 0, src_bry.length, Bry_.new_a7(find), Bry_.new_a7(repl));
		Gftest.Eq__str(expd, src_bry);
	}
}
