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
public class Bry_split__tst {
	private final Bry_split__fxt fxt = new Bry_split__fxt();
	@Test   public void Split() {
		fxt.Test_Split("a"				, Byte_ascii.Pipe, Bool_.N, "a");					// no trim
		fxt.Test_Split("a|"				, Byte_ascii.Pipe, Bool_.N, "a");
		fxt.Test_Split("|a"				, Byte_ascii.Pipe, Bool_.N, "", "a");
		fxt.Test_Split("|"				, Byte_ascii.Pipe, Bool_.N, "");
		fxt.Test_Split(""				, Byte_ascii.Pipe, Bool_.N);
		fxt.Test_Split("a|b|c"			, Byte_ascii.Pipe, Bool_.N, "a", "b", "c");
		fxt.Test_Split(" a "			, Byte_ascii.Pipe, Bool_.Y, "a");					// trim
		fxt.Test_Split(" a |"			, Byte_ascii.Pipe, Bool_.Y, "a");
		fxt.Test_Split("| a "			, Byte_ascii.Pipe, Bool_.Y, "", "a");
		fxt.Test_Split(" | "			, Byte_ascii.Pipe, Bool_.Y, "");
		fxt.Test_Split(" "				, Byte_ascii.Pipe, Bool_.Y);
		fxt.Test_Split(" a | b | c "	, Byte_ascii.Pipe, Bool_.Y, "a", "b", "c");
		fxt.Test_Split(" a b | c d "	, Byte_ascii.Pipe, Bool_.Y, "a b", "c d");
		fxt.Test_Split(" a \n b "		, Byte_ascii.Nl  , Bool_.N, " a ", " b ");			// ws as dlm
		fxt.Test_Split(" a \n b "		, Byte_ascii.Nl  , Bool_.Y, "a", "b");				// ws as dlm; trim
	}
}
class Bry_split__fxt {
	public void Test_Split(String raw_str, byte dlm, boolean trim, String... expd) {
		byte[][] actl_ary = Bry_split_.Split(Bry_.new_a7(raw_str), dlm, trim);
		Tfds.Eq_ary_str(expd, String_.Ary(actl_ary));
	}
}
