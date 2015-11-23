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
import gplx.core.lists.*;
public class CompareAble_tst implements ComparerAble {
	@Test  public void Basic() {
		String[] slotAry = new String[] {"b", "e", "h"};  // 0=b 1=e 2=h
		tst_FindSlot(slotAry, "f", "h");	// f ->  1 2 -> 2
		tst_FindSlot(slotAry, "c", "e");	// c -> -1 1 -> 0 ->  0 1 -> 1
		tst_FindSlot(slotAry, "a", "b");	// a -> -1 1 -> 0 -> -1 0 -> 0
	}
	@Test  public void Null() {
		String[] slotAry = new String[] {"b", "g", "l", "q", "v", null}; 
		tst_FindSlot(slotAry, "a", "b");
		tst_FindSlot(slotAry, "b", "b");
		tst_FindSlot(slotAry, "c", "g");
		tst_FindSlot(slotAry, "v", "v");
		tst_FindSlot(slotAry, "w", null);
	}
	public int compare(Object lhsObj, Object rhsObj) {return CompareAble_.Compare_obj(lhsObj, rhsObj);}
	void tst_FindSlot(String[] slotAry, String s, String expd) {Tfds.Eq(expd, slotAry[CompareAble_.FindSlot(this, slotAry, s)]);}
}
