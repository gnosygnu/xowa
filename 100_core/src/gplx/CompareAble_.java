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
public class CompareAble_ {
	public static Comparable as_(Object obj) {return obj instanceof Comparable ? (Comparable)obj : null;}
	public static int Compare_obj(Object lhs, Object rhs) {return Compare_comp(as_(lhs), as_(rhs));}
	public static int Compare_comp(Comparable lhs, Comparable rhs) {
		if		(lhs == null && rhs == null)	return CompareAble_.Same;
		else if (lhs == null)					return CompareAble_.More;
		else if (rhs == null)					return CompareAble_.Less;
		else									return Compare(lhs, rhs);
	}
	public static int Compare(Comparable lhs, Comparable rhs) {return lhs.compareTo(rhs);}	

	public static boolean Is(int expd, Comparable lhs, Comparable rhs) {
		int actl = Compare_comp(lhs, rhs);
		if (actl == Same && expd % 2 == Same)	// actl=Same and expd=(Same||MoreOrSame||LessOrSame)
			return true;
		else
			return (actl * expd) > 0;			// actl=More||Less; expd will match if on same side of 0 (ex: expd=Less; actl=Less; -1 * -1 = 1)
	}

	public static final int 
	  More = 1
	, Less = -1
	, Same = 0
	, More_or_same = 2
	, Less_or_same = -2
	;
}
