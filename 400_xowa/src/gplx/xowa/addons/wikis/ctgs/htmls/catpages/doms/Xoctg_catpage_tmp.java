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
public class Xoctg_catpage_tmp {
	private final    List_adp subc_list = List_adp_.New(), page_list = List_adp_.New(), file_list = List_adp_.New();
	public void Add(Xoctg_catpage_itm itm) {
		List_adp list = Get_by_tid(itm.Grp_tid());
		list.Add(itm);
	}
	public void Make_by_ctg(Xoctg_catpage_ctg ctg) {	// TEST:
		for (byte tid = 0; tid < Xoa_ctg_mgr.Tid___max; ++tid)
			Make_by_grp(ctg.Grp_by_tid(tid));
	}
	public void Make_by_grp(Xoctg_catpage_grp grp) {
		byte tid = grp.Tid();
		List_adp list = Get_by_tid(tid);
		if (list.Len() == 0) return;
		grp.Itms_((Xoctg_catpage_itm[])list.To_ary_and_clear(Xoctg_catpage_itm.class));
	}
	private List_adp Get_by_tid(byte tid) {
		switch (tid) {
			case Xoa_ctg_mgr.Tid__subc: return subc_list;
			case Xoa_ctg_mgr.Tid__page: return page_list;
			case Xoa_ctg_mgr.Tid__file: return file_list;
			default: throw Err_.new_unhandled_default(tid);
		}
	}
}
