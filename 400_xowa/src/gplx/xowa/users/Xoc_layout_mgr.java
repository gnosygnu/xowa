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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.gfui.*;
public class Xoc_layout_mgr implements GfoInvkAble {
	public Xoc_layout_mgr(Xoae_app app) {
		this.app = app;
		Ctor_by_os();
	}	private Xoae_app app;
	public byte Html_box_adj_type() {return html_box_adj_type;} private byte html_box_adj_type;
	public RectAdp Html_box_adj_rect() {return html_box_adj_rect;} private RectAdp html_box_adj_rect = RectAdp_.Zero;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_box_adj_type))			return html_box_adj_type_enm.Get_str(html_box_adj_type);
		else if	(ctx.Match(k, Invk_html_box_adj_type_))			{html_box_adj_type = Byte_.parse_(m.ReadStr("v")); this.Refresh_window();}
		else if	(ctx.Match(k, Invk_html_box_adj_type_list))		return html_box_adj_type_enm.Get_kv_ary();
		else if	(ctx.Match(k, Invk_html_box_adj_rect))			return html_box_adj_rect.Xto_str();
		else if	(ctx.Match(k, Invk_html_box_adj_rect_))			{html_box_adj_rect = gplx.gfui.RectAdp_.parse_ws_(m.ReadStr("v")); this.Refresh_window();}
		return this;
	}
	private void Refresh_window() {
		app.Gui_mgr().Browser_win().Refresh_win_size();
	}
	public static final String 
	  Invk_html_box_adj_type = "html_box_adj_type", Invk_html_box_adj_type_ = "html_box_adj_type_", Invk_html_box_adj_type_list = "html_box_adj_type_list"
	, Invk_html_box_adj_rect = "html_box_adj_rect", Invk_html_box_adj_rect_ = "html_box_adj_rect_";
	public static final byte Html_box_adj_type_none_byte = 0, Html_box_adj_type_rel_byte = 1, Html_box_adj_type_abs_byte = 2;
	private static final Enm_mgr html_box_adj_type_enm = new Enm_mgr().Add(Html_box_adj_type_none_byte, "none").Add(Html_box_adj_type_rel_byte, "relative").Add(Html_box_adj_type_abs_byte, "absolute");
	private void Ctor_by_os() {
		if (Op_sys.Cur().Tid_is_osx()) {
			html_box_adj_type = Html_box_adj_type_rel_byte;
			html_box_adj_rect = RectAdp_.new_(0, 0, 5, 30);
		}
	}
}
class Enm_mgr {
	private Ordered_hash str_hash = Ordered_hash_.new_(); private Hash_adp val_hash = Hash_adp_.new_();
	private Int_obj_ref tmp_val_ref = Int_obj_ref.zero_(); 
	public Enm_mgr Add(byte val, String str) {
		Int_obj_ref val_ref = Int_obj_ref.new_(val);
		KeyVal kv = KeyVal_.new_(str, val_ref);
		str_hash.Add(str, kv);
		val_hash.Add(val_ref, kv);
		return this;
	}
	public String Get_str(byte val) {
		Object o = val_hash.Get_by(tmp_val_ref.Val_(val));
		if (o == null) return null;
		return ((KeyVal)o).Val_to_str_or_null();
	}
	public KeyVal[] Get_kv_ary() {
		int len = val_hash.Count();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			KeyVal trg = (KeyVal)str_hash.Get_at(i);
			rv[i] = KeyVal_.new_(trg.Val_to_str_or_null(), trg.Key());
		}
		return rv;
	}
}

