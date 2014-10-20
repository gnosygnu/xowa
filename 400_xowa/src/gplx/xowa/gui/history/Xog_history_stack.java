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
	private final ListAdp list = ListAdp_.new_();
	public int Len() {return list.Count();}
	public void Clear() {list.Clear(); cur_pos = 0;}
	public Xog_history_itm Get_at(int i) {return (Xog_history_itm)list.FetchAt(i);}
	public int Cur_pos() {return cur_pos;} private int cur_pos = 0;
	public Xog_history_itm Cur_itm() {return list.Count() == 0 ? Xog_history_itm.Null : (Xog_history_itm)list.FetchAt(cur_pos);}
	public void Add(Xog_history_itm new_itm) {
		Xog_history_itm cur_itm = this.Cur_itm(); 
		if (cur_itm != Xog_history_itm.Null && cur_itm.Eq_wo_bmk_pos(new_itm)) return;		// do not add if last itm is same;
		this.Del_from(cur_pos + 1);
		list.Add(new_itm);
		cur_pos = list.Count() - 1;
	}
	public Xog_history_itm Go_bwd() {
		if (list.Count() == 0) return Xog_history_itm.Null;
		--cur_pos;
		if (cur_pos < 0) cur_pos = 0; 
		return this.Cur_itm();
	}
	public Xog_history_itm Go_fwd() {
		int list_count = list.Count();
		if (list_count == 0) return Xog_history_itm.Null;
		++cur_pos;
		if (cur_pos == list_count) cur_pos = list_count - 1;
		return this.Cur_itm();
	}
	private void Del_from(int from) {
		int len = list.Count();
		if (from <= len - 1)
			list.Del_range(from, len - 1);
	}
	public static final byte Nav_fwd = 1, Nav_bwd = 2, Nav_by_anchor = 3;
}
