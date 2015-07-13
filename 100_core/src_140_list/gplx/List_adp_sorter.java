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
import gplx.lists.*;
public class List_adp_sorter {
	private ComparerAble comparer = null;
	public void Sort(Object[] orig, int origLen) {Sort(orig, origLen, true, null);}
	public void Sort(Object[] orig, int origLen, boolean asc, ComparerAble comparer) {
		this.comparer = comparer;
		Object[] temp = new Object[origLen];
		MergeSort(asc, orig, temp, 0, origLen - 1);
		this.comparer = null;
	}
	void MergeSort(boolean asc, Object[] orig,Object[] temp, int lhs, int rhs) {
		if (lhs < rhs) {
			int mid = (lhs + rhs) / 2;
			MergeSort(asc, orig, temp, lhs, mid);
			MergeSort(asc, orig, temp, mid + 1, rhs);
			Combine(asc, orig, temp, lhs, mid + 1, rhs);
		}
	}
	private void Combine(boolean asc, Object[] orig, Object[] temp, int lhsPos, int rhsPos, int rhsEnd) {
		int lhsEnd = rhsPos - 1;
		int tmpPos = lhsPos;
		int aryLen = rhsEnd - lhsPos + 1;
	    
		while (lhsPos <= lhsEnd && rhsPos <= rhsEnd) {
			int compareVal = 0;
			if (comparer != null)
				compareVal = ComparerAble_.Compare(comparer, orig[lhsPos], orig[rhsPos]);
			else {
				Comparable lhsComp = (Comparable)orig[lhsPos];	
				compareVal = lhsComp == null ? CompareAble_.Less : lhsComp.compareTo(orig[rhsPos]);
			}
			if (!asc) compareVal *= -1;
			if (compareVal <= CompareAble_.Same) // NOTE: (a) must be < 0; JAVA's String.compareTo returns -number based on position; (b) must be <= else sorting sorted list will change order; EX: sorting (a,1;a,2) on fld0 will switch to (a,2;a,1)
				temp[tmpPos++] = orig[lhsPos++];
			else
				temp[tmpPos++] = orig[rhsPos++];
		}
	    
		while (lhsPos <= lhsEnd)	// Copy rest of first half
			temp[tmpPos++] = orig[lhsPos++];
		while (rhsPos <= rhsEnd)	// Copy rest of right half
			temp[tmpPos++] = orig[rhsPos++];
		for (int i = 0; i < aryLen; i++, rhsEnd--)
			orig[rhsEnd] = temp[rhsEnd];
	}

	public static List_adp_sorter new_() {return new List_adp_sorter();} List_adp_sorter() {}
}
class Iterator_objAry implements java.util.Iterator {
	public boolean hasNext() {return ++pos < len;}
	public Object next() {return ary[pos];}
	public void remove() {pos = -1;}
	Object[] ary; int pos = -1; int len = 0;
	public Iterator_objAry(Object[] v, int count) {ary = v; len = count;}
}