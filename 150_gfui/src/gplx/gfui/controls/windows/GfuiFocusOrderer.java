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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.lists.*; /*ComparerAble*/
import gplx.gfui.controls.elems.*;
public class GfuiFocusOrderer {
	public static void OrderByX(GfuiElem owner) {Order(owner, xcomparer, 0);}
	public static void OrderByY(GfuiElem owner) {Order(owner, ycomparer, 0);}
	static int Order(GfuiElem owner, ComparerAble comparer, int order) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElem sub = (GfuiElem)owner.SubElems().Get_at(i);
			if (sub.Focus_idx() != NullVal) continue;
			list.Add(sub);
		}
		list.Sort_by(comparer);

		for (Object subObj : list) {
			GfuiElem sub = (GfuiElem)subObj;
			sub.Focus_idx_(order++);
			if (owner.SubElems().Count() > 0)		// subs available; recurse;
				order = Order(sub, comparer, order);
		}
		return order;
	}
	static GfuiFocusOrderer_cls_x xcomparer = new GfuiFocusOrderer_cls_x(); static GfuiFocusOrderer_cls_y ycomparer = new GfuiFocusOrderer_cls_y();
	public static final    int NullVal = -1;
}
class GfuiFocusOrderer_cls_x implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		GfuiElem lhs = (GfuiElem)lhsObj, rhs = (GfuiElem)rhsObj;
		if (lhs.Y() < rhs.Y())				return CompareAble_.Less;
		else if (lhs.Y() > rhs.Y())			return CompareAble_.More;
		else								return Int_.Compare(lhs.X(), rhs.X());
	}
}
class GfuiFocusOrderer_cls_y implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		GfuiElem lhs = (GfuiElem)lhsObj, rhs = (GfuiElem)rhsObj;
		if (lhs.X() < rhs.X())				return CompareAble_.Less;
		else if (lhs.X() > rhs.X())			return CompareAble_.More;
		else								return Int_.Compare(lhs.Y(), rhs.Y());
	}
}
