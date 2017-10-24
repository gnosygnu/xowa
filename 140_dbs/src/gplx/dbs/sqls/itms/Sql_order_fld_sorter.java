/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.core.gfo_ndes.*;
import gplx.core.lists.*;
public class Sql_order_fld_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		GfoNde lhs = (GfoNde)lhsObj; GfoNde rhs = (GfoNde)rhsObj;
		Sql_order_fld item = null; Object lhsData = null, rhsData = null;
		for (int i = 0; i < items.length; i++) {
			item = items[i];
			lhsData = lhs.Read(item.Name); rhsData = rhs.Read(item.Name);
			int compare = CompareAble_.Compare_obj(lhsData, rhsData);
			if (compare == CompareAble_.Same) continue;
			int ascendingVal = item.Sort == Sql_order_fld.Sort__dsc ? -1 : 1;
			return compare * ascendingVal;
		}
		return CompareAble_.Same;
	}
	Sql_order_fld[] items;
	public static ComparerAble new_(Sql_order_fld[] items) {
		Sql_order_fld_sorter rv = new Sql_order_fld_sorter();
		rv.items = items;
		return rv;
	}
}
