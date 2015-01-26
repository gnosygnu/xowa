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
package gplx.dbs; import gplx.*;
import gplx.lists.*; /*ComparerAble*/
class Sql_order_by {
	public ListAdp Flds() {return flds;} ListAdp flds = ListAdp_.new_();

	public static Sql_order_by new_(Sql_order_by_itm... ary) {
		Sql_order_by rv = new Sql_order_by();
		for (Sql_order_by_itm itm : ary)
			rv.flds.Add(itm);
		return rv;
	}	Sql_order_by() {}
}
class Sql_group_by {
	public ListAdp Flds() {return flds;} ListAdp flds = ListAdp_.new_();

	public static Sql_group_by new_(String... ary) {
		Sql_group_by rv = new Sql_group_by();
		for (String itm : ary)
			rv.flds.Add(itm);
		return rv;
	}	Sql_group_by() {}
}
class Sql_order_by_itm {
	public String Name() {return name;} private String name;
	public boolean Ascending() {return ascending;} private boolean ascending;
	public String XtoSql() {
		String ascString = ascending ? "" : " DESC";
		return name + ascString;
	}
	public static Sql_order_by_itm new_(String name, boolean ascending) {
		Sql_order_by_itm rv = new Sql_order_by_itm();
		rv.name = name; rv.ascending = ascending;
		return rv;
	}	Sql_order_by_itm() {}
}
class Sql_order_by_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		GfoNde lhs = (GfoNde)lhsObj; GfoNde rhs = (GfoNde)rhsObj;
		Sql_order_by_itm item = null; Object lhsData = null, rhsData = null;
		for (int i = 0; i < items.length; i++) {
			item = items[i];
			lhsData = lhs.Read(item.Name()); rhsData = rhs.Read(item.Name());
			int compare = CompareAble_.Compare_obj(lhsData, rhsData);
			if (compare == CompareAble_.Same) continue;
			int ascendingVal = item.Ascending() ? 1 : -1;
			return compare * ascendingVal;
		}
		return CompareAble_.Same;
	}
	Sql_order_by_itm[] items;
	public static ComparerAble new_(Sql_order_by_itm[] items) {
		Sql_order_by_sorter rv = new Sql_order_by_sorter();
		rv.items = items;
		return rv;
	}
}