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
package gplx.fsdb; import gplx.*;
import org.junit.*;
public class Binary_search__tst {
	private Binary_search__fxt fxt = new Binary_search__fxt();
	@Test  public void Basic() {
		fxt.Init_ary("", "e", "j", "o", "t", "y");
		fxt.Test_binary_search("a", 0);
		fxt.Test_binary_search("f", 1);
		fxt.Test_binary_search("k", 2);
		fxt.Test_binary_search("p", 3);
		fxt.Test_binary_search("u", 4);
		fxt.Test_binary_search("z", 5);
	}
	@Test  public void One() {
		fxt.Init_ary("");
		fxt.Test_binary_search("a", 0);
	}
}
class Binary_search__fxt {
	public void Init_ary(String... v) {
		int ary_len = v.length;
		ary = new String_obj_val[ary_len];
		for (int i = 0; i < ary_len; i++)
			ary[i] = String_obj_val.new_(v[i]);
	} 	private String_obj_val[] ary;
	public void Test_binary_search(String val, int expd) {
		int actl = Binary_search_.Search(ary, ary.length, String_obj_val.new_(val));
		Tfds.Eq(expd, actl, val);
	}
}
