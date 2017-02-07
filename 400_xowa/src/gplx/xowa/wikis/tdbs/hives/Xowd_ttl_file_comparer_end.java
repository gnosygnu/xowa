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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
public class Xowd_ttl_file_comparer_end implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_hive_regy_itm lhs = (Xowd_hive_regy_itm)lhsObj, rhs = (Xowd_hive_regy_itm)rhsObj;
		if 		(lhs.Count() == 0) 	return Bry_.Compare(rhs.End(), lhs.Bgn());
		//else if (rhs.Count() == 0) 	return Bry_.Compare(lhs.End(), rhs.End());	// NOTE: this line mirrors the top, but is actually covered by below
		else						return Bry_.Compare(lhs.End(), rhs.End());
	}
	public static final Xowd_ttl_file_comparer_end Instance = new Xowd_ttl_file_comparer_end(); Xowd_ttl_file_comparer_end() {}
}
