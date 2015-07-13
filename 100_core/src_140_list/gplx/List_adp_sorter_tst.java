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
public class List_adp_sorter_tst {
	@Test  public void Basic() {
		Object[] src = new Object[] {0,8,1,7,2,6,3,5,4};
		List_adp_sorter.new_().Sort(src, src.length);
		Tfds.Eq_ary(src, Sequential(0, 8));
	}
	@Test  public void Basic2() {
		Object[] src = new Object[] {"0","8","1","7","2","6","3","5","4"};
		List_adp_sorter.new_().Sort(src, src.length);
		Tfds.Eq_ary(src, new Object[] {"0","1","2","3","4","5","6","7","8"});
	}
	Object[] Sequential(int bgn, int end) {
		Object[] rv = new Object[end - bgn + 1];
		for (int i = 0; i < Array_.Len(rv); i++)
			rv[i] = i + bgn;
		return rv;
	}
}
