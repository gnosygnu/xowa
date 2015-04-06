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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xosrh_rslt_grp {
	public Xosrh_rslt_grp(int idx) {this.idx = idx;} private Xowd_page_itm[] itms = Xowd_page_itm.Ary_empty;
	public int Idx() {return idx;} private int idx;
	public int Itms_len() {return itms_len;} private int itms_len; int itms_max;
	public int Itms_total() {return itms_total;} public Xosrh_rslt_grp Itms_total_(int v) {itms_total = v; return this;} private int itms_total;
	public Xowd_page_itm Itms_get_at(int i) {return itms[i];}
	public void Itms_add(Xowd_page_itm itm) {
		int new_itms_len = itms_len + 1;
		if (new_itms_len > itms_max) {
			itms_max = itms_max == 0 ? 2 : itms_max * 2;
			itms = (Xowd_page_itm[])Array_.Resize(itms, itms_max);
		}
		itms[itms_len] = itm;
		itms_len = new_itms_len;
	}
	public boolean Itms_full() {return itms_len >= itms_max;}
	public void Itms_clear() {
		for (int i = 0; i < itms_len; i++)
			itms[i] = null;
		itms = Xowd_page_itm.Ary_empty;
		itms_len = itms_max = 0;
	}
	public void Itms_sort(Xowd_page_itm_sorter sorter) {Array_.Sort(itms, sorter);}
	public static final Xosrh_rslt_grp[] Ary_empty = new Xosrh_rslt_grp[0];
}
