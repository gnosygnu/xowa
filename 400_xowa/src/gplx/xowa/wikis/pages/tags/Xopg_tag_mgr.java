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
package gplx.xowa.wikis.pages.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_tag_mgr {
	private final    List_adp list = List_adp_.New();
	public Xopg_tag_mgr(boolean pos_is_head) {this.pos_is_head = pos_is_head;}
	public boolean Pos_is_head() {return pos_is_head;} private final    boolean pos_is_head;
	public int Len() {return list.Len();}
	public Xopg_tag_itm Get_at(int i) {return (Xopg_tag_itm)list.Get_at(i);}
	public void Add(Xopg_tag_itm... ary) {for (Xopg_tag_itm itm : ary) list.Add(itm);}
	public void Copy(Xopg_tag_mgr src) {
		int len = src.Len();
		for (int i = 0; i < len; ++i)
			this.Add(src.Get_at(i));
	}
	public byte[] To_html(Bry_bfr bfr) {
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xopg_tag_itm tag = this.Get_at(i);
			tag.To_html(bfr);
		}
		return bfr.To_bry_and_clear();
	}
	public byte[] To_html__style(Bry_bfr bfr) {
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xopg_tag_itm tag = this.Get_at(i);
			if (	Bry_.Eq(tag.Node(), gplx.langs.htmls.Gfh_tag_.Bry__style)
				&&	tag.Body() != null
				) {
				tag.To_html(bfr);
			}
		}
		return bfr.To_bry_and_clear();
	}
}
