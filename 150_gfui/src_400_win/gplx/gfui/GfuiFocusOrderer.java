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
package gplx.gfui; import gplx.*;
import gplx.core.lists.*; /*ComparerAble*/
class GfuiFocusOrderer {
	public static void OrderByX(GfuiElem owner) {Order(owner, xcomparer, 0);}
	public static void OrderByY(GfuiElem owner) {Order(owner, ycomparer, 0);}
	static int Order(GfuiElem owner, ComparerAble comparer, int order) {
		List_adp list = List_adp_.new_();
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
	public static final int NullVal = -1;
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
