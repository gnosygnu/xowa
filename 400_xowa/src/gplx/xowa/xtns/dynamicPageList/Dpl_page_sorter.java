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
package gplx.xowa.xtns.dynamicPageList;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
class Dpl_page_sorter implements ComparerAble {
	public Dpl_page_sorter(Dpl_itm itm) {this.itm = itm;} private Dpl_itm itm;
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_page_itm lhs = (Xowd_page_itm)lhsObj;
		Xowd_page_itm rhs = (Xowd_page_itm)rhsObj;
		int multiplier = itm.Sort_ascending() == BoolUtl.YByte ? 1 : -1;
		switch (itm.Sort_tid()) {
			case Dpl_sort.Tid_categorysortkey:
			case Dpl_sort.Tid_categoryadd: 			return multiplier * BryUtl.Compare(lhs.Ttl_page_db(), rhs.Ttl_page_db());
		}
		return CompareAbleUtl.Same;
	}
}
