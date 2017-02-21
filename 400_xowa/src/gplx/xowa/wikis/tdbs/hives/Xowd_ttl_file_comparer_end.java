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
