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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Int_ary__tst {
	private Int_ary__fxt fxt = new Int_ary__fxt();
	@Test  public void Parse_list_or_() {
		fxt.Test_Parse_list_or("1", 1);
		fxt.Test_Parse_list_or("123", 123);
		fxt.Test_Parse_list_or("1,2,123", 1, 2, 123);
		fxt.Test_Parse_list_or("1,2,12,123", 1, 2, 12, 123);
		fxt.Test_Parse_list_or("1-5", 1, 2, 3, 4, 5);
		fxt.Test_Parse_list_or("1-1", 1);
		fxt.Test_Parse_list_or("1-3,7,11-13,21", 1, 2, 3, 7, 11, 12, 13, 21);

		fxt.Test_Parse_list_empty("1 2");			// NOTE: MW would gen 12; treat as invalid
		fxt.Test_Parse_list_empty("1,");			// eos
		fxt.Test_Parse_list_empty("1,,2");			// empty comma
		fxt.Test_Parse_list_empty("1-");			// eos
		fxt.Test_Parse_list_empty("3-1");			// bgn > end
		fxt.Test_Parse_list_empty("1,a,2");
		fxt.Test_Parse_list_empty("a-1,2");
		fxt.Test_Parse_list_empty("-1");			// no rng bgn
	}
}
class Int_ary__fxt {
	public void Test_Parse_list_empty(String raw) {Tfds.Eq_ary(Int_.Ary_empty, Int_ary_.Parse_list_or(Bry_.new_a7(raw), Int_.Ary_empty));}
	public void Test_Parse_list_or(String raw, int... expd) {Tfds.Eq_ary(expd, Int_ary_.Parse_list_or(Bry_.new_a7(raw), Int_.Ary_empty));}
}
