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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import gplx.lists.*;
public class Bry_comparer implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		byte[] lhs = (byte[])lhsObj, rhs = (byte[])rhsObj;
		return Bry_.Compare(lhs, 0, lhs.length, rhs, 0, rhs.length);
	}
	public static final Bry_comparer Instance = new Bry_comparer(); Bry_comparer() {}
}
