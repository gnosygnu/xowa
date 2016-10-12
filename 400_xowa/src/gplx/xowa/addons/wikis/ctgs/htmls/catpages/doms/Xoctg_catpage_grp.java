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
public class Xoctg_catpage_grp {
	private Xoctg_catpage_itm[] itms = Xoctg_catpage_itm.Ary_empty;
	private byte[] next_sortkey = Bry_.Empty;
	public Xoctg_catpage_grp(byte tid) {this.tid = tid;}
	public byte					Tid()				{return tid;} private byte tid;						// subc|page|file
	public int					Count_all()			{return count_all;} private int count_all;			// count of items in entire category; EX: 456
	public boolean					Prev_disable()		{return prev_disable;} private boolean prev_disable;	// prev_link: disable link
	public byte[]				Next_sortkey()		{return next_sortkey;}								// next_link: set sortkey
	public int					Itms__len()			{return itms.length;}
	public Xoctg_catpage_itm	Itms__get_at(int i) {return itms[i];}

	public void Count_all_(int v) {this.count_all = v;}
	public void Prev_disable_(boolean v) {this.prev_disable = v;}
	public void Next_sortkey_(byte[] v) {this.next_sortkey = v;}
	public void Itms_(Xoctg_catpage_itm[] v) {
		this.itms = v;
		Array_.Sort(itms, new Xoctg_catpage_itm_sorter()); // NOTE: need to reorder for page_until b/c ORDER BY DESC

		// make sortkey_handle
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		int itms_len = itms.length;
		byte[] prv_sortkey_handle = Bry_.Empty;
		for (int i = 0; i < itms_len; ++i) {
			Xoctg_catpage_itm itm = itms[i];
			prv_sortkey_handle = itm.Sortkey_handle_make(tmp_bfr, prv_sortkey_handle);
		}
	}

}
class Xoctg_catpage_itm_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_catpage_itm lhs = (Xoctg_catpage_itm)lhsObj;
		Xoctg_catpage_itm rhs = (Xoctg_catpage_itm)rhsObj;
		return Bry_.Compare(lhs.Sortkey_binary(), rhs.Sortkey_binary());
	}
}
