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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xou_history_sorter implements gplx.core.lists.ComparerAble {
	public boolean Ascending() {return ascending;} public Xou_history_sorter Ascending_(boolean v) {ascending = v; return this;} private boolean ascending = false;
	public int Sort_fld() {return sort_fld;} public Xou_history_sorter Sort_fld_(int v) {sort_fld = v; return this;} private int sort_fld = Xou_history_itm.Fld_view_end;
	public int compare(Object lhsObj, Object rhsObj) {
		Xou_history_itm lhs = (Xou_history_itm)lhsObj, rhs = (Xou_history_itm)rhsObj;
		int comp = CompareAble_.Compare_obj(lhs.Fld(sort_fld), rhs.Fld(sort_fld));
		if (!ascending) comp *= -1;
		return comp;
	}
}
