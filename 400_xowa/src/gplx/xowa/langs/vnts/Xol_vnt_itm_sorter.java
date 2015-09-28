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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.vnts.*;
class Xol_vnt_itm_sorter__rule implements gplx.lists.ComparerAble {
	private Hash_adp hash;
	public void Sort(Hash_adp hash, Xop_vnt_rule_tkn[] ary) {
		synchronized (hash) {
			this.hash = hash;
			Array_.Sort(ary, this);
		}
	}
	public int compare(Object lhsObj, Object rhsObj) {
		return -Int_.Compare(To_mask(lhsObj), To_mask(rhsObj));	// - to sort by descending order; "more specific" vnts are at end of list;
	}
	private int To_mask(Object o) {
		int rv = -1;
		Xop_vnt_rule_tkn rule = (Xop_vnt_rule_tkn)o; if (rule == null) return rv;
		Xol_vnt_itm itm = (Xol_vnt_itm)hash.Get_by(rule.Rule_lang()); if (itm == null) return rv;
		return itm.Mask__vnt();
	}
        public static final Xol_vnt_itm_sorter__rule I = new Xol_vnt_itm_sorter__rule(); Xol_vnt_itm_sorter__rule() {}
}
