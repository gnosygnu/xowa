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
package gplx.xowa.langs.plurals; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_plural_ru_tst {
	@Test  public void Plural() {
		Tst(1, String_.Ary_empty, null);			// 0 forms
		Tst(1, String_.Ary("a", "b"), "a");			// 2 forms; singluar
		Tst(2, String_.Ary("a", "b"), "b");			// 2 forms; plural
		Tst(111, String_.Ary("a", "b", "c"), "c");	// 3 forms; (count % 100) / 10 == 0; should not return "a"
		Tst(1, String_.Ary("a", "b", "c"), "a");	// 3 forms; count % 10 == 1
		Tst(2, String_.Ary("a", "b", "c"), "b");	// 3 forms; count % 10 == (2, 3, 4)
		Tst(5, String_.Ary("a", "b", "c"), "c");	// 3 forms; count % 10 != (1, 2, 3, 4)
		Tst(5, String_.Ary("a"), "a");				// 1 form; count % 10 != (1, 2, 3, 4); but only 1 element, so take 1st
	}
	private void Tst(int count, String[] forms, String expd) {
		byte[] actl = Xol_plural_ru._.Plural_eval(null, count, Bry_.Ary(forms));
		Tfds.Eq_bry(Bry_.new_a7(expd), actl);
	}
}
