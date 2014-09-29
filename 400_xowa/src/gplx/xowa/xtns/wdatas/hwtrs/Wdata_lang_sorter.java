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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*;
class Wdata_lang_sorter implements gplx.lists.ComparerAble {
	private Hash_adp_bry hash = Hash_adp_bry.cs_();
	public void Langs_(byte[][] langs) {
		hash.Clear();
		int len = langs.length;
		for (int i = 0; i < len; ++i) {
			byte[] lang = langs[i];
			Wdata_lang_sorter_itm itm = new Wdata_lang_sorter_itm(i, lang);
			hash.Add_if_new(lang, itm);
		}
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Wdata_sortable lhs = (Wdata_sortable)lhsObj; if (lhs.Sort() == -1) Sort_calc(lhs);
		Wdata_sortable rhs = (Wdata_sortable)rhsObj; if (rhs.Sort() == -1) Sort_calc(rhs);
		return Int_.Compare(lhs.Sort(), rhs.Sort());
	}
	private void Sort_calc(Wdata_sortable data_itm) {
		Wdata_lang_sorter_itm sort_itm = (Wdata_lang_sorter_itm)hash.Fetch(data_itm.Lang());
		if (sort_itm != null) data_itm.Sort_(sort_itm.Sort());
	}
}
class Wdata_lang_sorter_itm {
	public Wdata_lang_sorter_itm(int sort, byte[] lang) {this.sort = sort; this.lang = lang;}
	public int Sort() {return sort;} private final  int sort;
	public byte[] Lang() {return lang;} private final byte[] lang;
}
