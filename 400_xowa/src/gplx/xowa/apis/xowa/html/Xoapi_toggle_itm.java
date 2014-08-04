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
package gplx.xowa.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
public class Xoapi_toggle_itm implements GfoInvkAble {
	public Xoapi_toggle_itm(byte[] key_bry) {this.key_bry = key_bry;}
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Icon_src() {return icon_src;} private byte[] icon_src;
	public byte[] Icon_title() {return icon_title;} private byte[] icon_title;
	public byte[] Elem_display() {return elem_display;} private byte[] elem_display;
	public boolean Visible() {return visible;} private boolean visible;
	public Xoapi_toggle_itm Init(Xow_wiki wiki) {
		if (Img_src_y == null) {
			Io_url img_dir = wiki.App().User().Fsys_mgr().App_img_dir().GenSubDir_nest("window", "portal");
			Img_src_y = img_dir.GenSubFil("twisty_down.png").To_http_file_bry();
			Img_src_n = img_dir.GenSubFil("twisty_right.png").To_http_file_bry();
		}
		icon_src = visible ? Img_src_y : Img_src_n;
		byte[] img_title_msg = visible ? Img_title_msg_y : Img_title_msg_n;
		icon_title = wiki.Msg_mgr().Val_by_key_obj(img_title_msg);
		elem_display = visible ? Img_display_y : Img_display_n;
		return this;
	}
	private static byte[] Img_src_y, Img_src_n;
	private static final byte[] 
	  Img_title_msg_y = Bry_.new_ascii_("hide"), Img_title_msg_n = Bry_.new_ascii_("show")
	, Img_display_y = Bry_.new_ascii_("display:;"), Img_display_n = Bry_.new_ascii_("display:none;")
	;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_visible)) 			return Yn.Xto_str(visible);
		else if	(ctx.Match(k, Invk_visible_)) 			visible = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_visible = "visible", Invk_visible_ = "visible_";
}
