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
package gplx.xowa.gui.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
public class Xog_history_stack {
	private ListAdp list = ListAdp_.new_();
	public int Stack_pos() {return list_pos;} private int list_pos = 0;
	public int Count() {return list.Count();}
	public void Clear() {list.Clear(); list_pos = 0;}
	public Xog_history_itm Cur_itm() {return list.Count() == 0 ? Xog_history_itm.Null : (Xog_history_itm)list.FetchAt(list_pos);}
	public Xog_history_itm Add(Xoa_page page) {
		Xog_history_itm new_itm = new Xog_history_itm(page);
		Xog_history_itm cur_itm = this.Cur_itm(); 
		if (	cur_itm != Xog_history_itm.Null
			&&	cur_itm.Eq_except_bmk(new_itm)
			)
			return Xog_history_itm.Null;			// do not add if last itm is same;
		Del_all_from(list_pos + 1);
		list.Add(new_itm);
		list_pos = list.Count() - 1;
		return new_itm;
	}
	public void Update_html_doc_pos(Xoa_page page, byte history_nav_type) {
		Xog_history_itm itm = Get_recent(page, history_nav_type);
		if (itm != null) itm.Html_doc_pos_(page.Html_data().Bmk_pos());
	}
	private Xog_history_itm Get_recent(Xoa_page page, byte history_nav_type) {
		int pos = -1;
		switch (history_nav_type) {
			case Xog_history_stack.Nav_fwd:			pos = list_pos - 1; break;
			case Xog_history_stack.Nav_bwd:			pos = list_pos + 1; break;
			case Xog_history_stack.Nav_by_anchor:	pos = list_pos; break;
		}
		if (pos < 0 || pos >= list.Count()) return null;
		Xog_history_itm recent = (Xog_history_itm)list.FetchAt(pos);
		Xog_history_itm page_itm = new Xog_history_itm(page);
		return page_itm.Eq_except_bmk(recent) ? recent : null;	// check that recent page actually matches current; DATE:2014-05-10
	}
	public Xog_history_itm Go_bwd() {
		if (list.Count() == 0) return Xog_history_itm.Null;
		--list_pos;
		if (list_pos < 0) list_pos = 0; 
		return this.Cur_itm();
	}
	public Xog_history_itm Go_fwd() {
		int list_count = list.Count();
		if (list_count == 0) return Xog_history_itm.Null;
		++list_pos;
		if (list_pos == list_count) list_pos = list_count - 1;
		return this.Cur_itm();
	}
	private void Del_all_from(int from) {
		int list_count = list.Count();
		if (from <= list_count - 1)
			list.Del_range(from, list_count - 1);
	}
	public static final byte Nav_fwd = 1, Nav_bwd = 2, Nav_by_anchor = 3;
}
