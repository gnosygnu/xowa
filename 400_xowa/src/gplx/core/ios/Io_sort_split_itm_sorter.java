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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_sort_split_itm_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Io_sort_split_itm lhs = (Io_sort_split_itm)lhsObj, rhs = (Io_sort_split_itm)rhsObj;
		return Bry_.Compare(lhs.Bfr(), lhs.Key_bgn(), lhs.Key_end(), rhs.Bfr(), rhs.Key_bgn(), rhs.Key_end());
	}
	public static final    Io_sort_split_itm_sorter Instance = new Io_sort_split_itm_sorter(); Io_sort_split_itm_sorter() {}	// TS.static
}
