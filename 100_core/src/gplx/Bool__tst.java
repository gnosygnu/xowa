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
public class Bool__tst {
	private final Bool__fxt fxt = new Bool__fxt();
	@Test  public void Compare() {
		fxt.Test__compare(Bool_.Y, Bool_.Y, CompareAble_.Same);
		fxt.Test__compare(Bool_.N, Bool_.N, CompareAble_.Same);
		fxt.Test__compare(Bool_.N, Bool_.Y, CompareAble_.Less);
		fxt.Test__compare(Bool_.Y, Bool_.N, CompareAble_.More);
	}
}
class Bool__fxt {
	public void Test__compare(boolean lhs, boolean rhs, int expd) {Tfds.Eq(expd, Bool_.Compare(lhs, rhs));}
}
