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
public class Object__tst {
	@Before public void init() {} private Object__fxt fxt = new Object__fxt();
	@Test  public void Eq() {
		fxt.Test_eq(null, null, true);		// both null
		fxt.Test_eq(5, 5, true);			// both non-null
		fxt.Test_eq(5, null, false);		// rhs non-null
		fxt.Test_eq(null, 5, false);		// lhs non-null
	}
	@Test  public void Xto_str_loose_or_null() {
		fxt.Test_xto_str_loose_or_null(null, null);	
		fxt.Test_xto_str_loose_or_null(2449.6000000000004d, "2449.60");	
	}
}
class Object__fxt {
	public void Test_eq(Object lhs, Object rhs, boolean expd) {Tfds.Eq(expd, Object_.Eq(lhs, rhs));}
	public void Test_xto_str_loose_or_null(Object v, String expd) {Tfds.Eq(expd, Object_.Xto_str_loose_or(v, null));}
}
