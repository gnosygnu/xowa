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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xoctg_catpage_itm_sorter__sort_key implements gplx.core.lists.ComparerAble, gplx.core.lists.binary_searches.Binary_comparer {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_catpage_itm lhs = (Xoctg_catpage_itm)lhsObj;
		Xoctg_catpage_itm rhs = (Xoctg_catpage_itm)rhsObj;
		return Bry_.Compare(lhs.Sort_key(), rhs.Sort_key());
	}
	public int Compare_val_to_obj(Object val, Object obj) {
		Xoctg_catpage_itm itm = (Xoctg_catpage_itm)obj;
		return Bry_.Compare((byte[])val, itm.Sort_key());
	}
	public static final    Xoctg_catpage_itm_sorter__sort_key Sorter = new Xoctg_catpage_itm_sorter__sort_key(); 
}
