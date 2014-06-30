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
	@Test public void Eq() {
		tst_Eq(null, null, true);	// both null
		tst_Eq(5, 5, true);			// both non-null
		tst_Eq(5, null, false);		// rhs non-null
		tst_Eq(null, 5, false);		// lhs non-null
	}	void tst_Eq(Object lhs, Object rhs, boolean expd) {Tfds.Eq(expd, Object_.Eq(lhs, rhs));}
}
