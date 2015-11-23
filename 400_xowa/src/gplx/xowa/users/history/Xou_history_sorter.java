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
