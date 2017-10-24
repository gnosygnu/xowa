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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
//	public class Dpl_page {
//		public byte[] Ttl_bry() {return ttl_bry;} public Dpl_page Ttl_bry_(byte[] v) {ttl_bry = v; return this;} private byte[] ttl_bry;
//	}
import gplx.xowa.wikis.data.tbls.*;
class Dpl_page_sorter implements gplx.core.lists.ComparerAble {
	public Dpl_page_sorter(Dpl_itm itm) {this.itm = itm;} private Dpl_itm itm;
	public int compare(Object lhsObj, Object rhsObj) {
		Xowd_page_itm lhs = (Xowd_page_itm)lhsObj;
		Xowd_page_itm rhs = (Xowd_page_itm)rhsObj;
		int multiplier = itm.Sort_ascending() == Bool_.Y_byte ? 1 : -1;
		switch (itm.Sort_tid()) {
			case Dpl_sort.Tid_categorysortkey: 		
			case Dpl_sort.Tid_categoryadd: 			return multiplier * Bry_.Compare(lhs.Ttl_page_db(), rhs.Ttl_page_db()); 
		}
		return CompareAble_.Same;
	}
}
