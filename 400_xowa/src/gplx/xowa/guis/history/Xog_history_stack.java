/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.guis.history; import gplx.*;
public class Xog_history_stack {
	private final List_adp list = List_adp_.New();
	public int Len() {return list.Len();}
	public void Clear() {list.Clear(); cur_pos = 0;}
	public Xog_history_itm Get_at(int i) {return (Xog_history_itm)list.Get_at(i);}
	public int Cur_pos() {return cur_pos;} private int cur_pos = 0;
	public Xog_history_itm Cur_itm() {return list.Len() == 0 ? Xog_history_itm.Null : (Xog_history_itm)list.Get_at(cur_pos);}
	public void Add(Xog_history_itm new_itm) {
		Xog_history_itm cur_itm = this.Cur_itm(); 
		if (cur_itm != Xog_history_itm.Null && cur_itm.Eq_wo_bmk_pos(new_itm)) return;		// do not add if last itm is same;
		this.Del_from(cur_pos + 1);
		list.Add(new_itm);
		cur_pos = list.Len() - 1;
	}
	public Xog_history_itm Go_bwd() {
		if (list.Len() == 0) return Xog_history_itm.Null;
		if (cur_pos == 0) return Xog_history_itm.Null;
		--cur_pos;
		return this.Cur_itm();
	}
	public Xog_history_itm Go_fwd() {
		int list_count = list.Len();
		if (list_count == 0) return Xog_history_itm.Null;
		if (cur_pos == list_count - 1) return Xog_history_itm.Null;
		++cur_pos;
		return this.Cur_itm();
	}
	private void Del_from(int from) {
		int len = list.Len();
		if (from <= len - 1)
			list.DelRange(from, len - 1);
	}
	public void Cur_pos_(int v) {this.cur_pos = v;}
	public static final byte Nav_fwd = 1, Nav_bwd = 2, Nav_by_anchor = 3;
}
