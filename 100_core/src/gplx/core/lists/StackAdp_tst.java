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
package gplx.core.lists; import gplx.*; import gplx.core.*;
import org.junit.*;
public class StackAdp_tst {
	@Test  public void XtoList() {
		tst_XtoList(1, 2, 3);
	}
	void tst_XtoList(int... ary) {
		StackAdp stack = StackAdp_.new_();
		for (int i : ary)
			stack.Push(i);
		List_adp list = stack.XtoList();
		int[] actl = (int[])list.To_ary(int.class);
		for (int i = 0; i < ary.length; i++)
			Tfds.Eq(ary[i], actl[i]);
	}
}
