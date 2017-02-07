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
package gplx.xowa.mws.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_string_utils__tst {
	private final    Xomw_string_utils__fxt fxt = new Xomw_string_utils__fxt();
	@Test  public void Delimiter_explode() {
		// basic
		fxt.Test__delimiter_explode("a|b|c"                             , "a", "b", "c");
		// empty
		fxt.Test__delimiter_explode("|a||c|"                            , "", "a", "", "c", "");
		// nest_1
		fxt.Test__delimiter_explode("a|-{b|c}-|d"                       , "a", "-{b|c}-", "d");
		// nest_many
		fxt.Test__delimiter_explode("a|-{b-{c|d}-e}-|f"                 , "a", "-{b-{c|d}-e}-", "f");
	}
	@Test  public void Replace_markup() {
		// basic
		fxt.Test__replace_markup("a!!b"             , "!!", "||", "a||b");
		// missing
		fxt.Test__replace_markup("abcd"             , "!!", "||", "abcd");
		// eos
		fxt.Test__replace_markup("a!!"              , "!!", "||", "a||");
		// ignore
		fxt.Test__replace_markup("a!!b<!!>!!c"      , "!!", "||", "a||b<!!>||c");
		// ignore asym_lhs
		fxt.Test__replace_markup("a!!b<!!<!!>!!c"   , "!!", "||", "a||b<!!<!!>||c");
		// ignore asym_lhs
		fxt.Test__replace_markup("a!!b<!!>!!>!!c"   , "!!", "||", "a||b<!!>||>||c");	// NOTE: should probably be "!!>!!>", but unmatched ">" are escaped to "&gt;"
	}
}
class Xomw_string_utils__fxt {
	public void Test__delimiter_explode(String src_str, String... expd) {
		List_adp tmp = List_adp_.New();
		gplx.core.btries.Btrie_rv trv = new gplx.core.btries.Btrie_rv();

		byte[][] actl = Xomw_string_utils.Delimiter_explode(tmp, trv, Bry_.new_u8(src_str));
		Gftest.Eq__ary(expd, actl, "src=~{0}", src_str);
	}
	public void Test__replace_markup(String src_str, String find, String repl, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Xomw_string_utils.Replace_markup(src_bry, 0, src_bry.length, Bry_.new_a7(find), Bry_.new_a7(repl));
		Gftest.Eq__str(expd, src_bry);
	}
}
