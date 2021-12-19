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
package gplx.xowa.wikis.pages.tags;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
public class Xopg_tag_mgr {
	private final List_adp list = List_adp_.New();
	public Xopg_tag_mgr(boolean pos_is_head) {this.pos_is_head = pos_is_head;}
	public boolean Pos_is_head() {return pos_is_head;} private final boolean pos_is_head;
	public int Len() {return list.Len();}
	public void Clear() {list.Clear();}
	public Xopg_tag_itm Get_at(int i) {return (Xopg_tag_itm)list.GetAt(i);}
	public Xopg_tag_itm[] To_ary() {return (Xopg_tag_itm[])list.ToAry(Xopg_tag_itm.class);}
	public void Add(Xopg_tag_itm... ary) {for (Xopg_tag_itm itm : ary) list.Add(itm);}
	public void Copy(Xopg_tag_mgr src) {
		int len = src.Len();
		for (int i = 0; i < len; i++)
			this.Add(src.Get_at(i));
	}
	public byte[] To_html(BryWtr bfr) {
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			Xopg_tag_itm tag = this.Get_at(i);
			tag.To_html(bfr);
		}
		return bfr.ToBryAndClear();
	}
	public byte[] To_html__style(BryWtr bfr) {
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			Xopg_tag_itm tag = this.Get_at(i);
			if (	BryLni.Eq(tag.Node(), gplx.langs.htmls.Gfh_tag_.Bry__style)
				&&	tag.Body() != null
				) {
				tag.To_html(bfr);
			}
		}
		return bfr.ToBryAndClear();
	}
}
