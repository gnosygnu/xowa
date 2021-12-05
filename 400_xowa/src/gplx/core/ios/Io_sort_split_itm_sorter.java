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
package gplx.core.ios; import gplx.*;
import gplx.objects.lists.ComparerAble;
public class Io_sort_split_itm_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Io_sort_split_itm lhs = (Io_sort_split_itm)lhsObj, rhs = (Io_sort_split_itm)rhsObj;
		return Bry_.Compare(lhs.Bfr(), lhs.Key_bgn(), lhs.Key_end(), rhs.Bfr(), rhs.Key_bgn(), rhs.Key_end());
	}
	public static final Io_sort_split_itm_sorter Instance = new Io_sort_split_itm_sorter(); Io_sort_split_itm_sorter() {}	// TS.static
}
