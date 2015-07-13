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
	public byte[] Heading_bry() {return heading_bry;} private byte[] heading_bry;
	public byte[] Icon_src() {return icon_src;} private byte[] icon_src = Bry_.Empty;
	public byte[] Icon_title() {return icon_title;} private byte[] icon_title = Bry_.Empty;
	public byte[] Elem_display() {return elem_display;} private byte[] elem_display = Bry_.Empty;
	public byte[] Html_toggle_hdr_cls() {return html_toggle_hdr_cls;} public Xoapi_toggle_itm Html_toggle_hdr_cls_(byte[] v) {html_toggle_hdr_cls = v; return this;} private byte[] html_toggle_hdr_cls = Bry_.Empty;
	public boolean Visible() {return visible;} private boolean visible;
	public Xoapi_toggle_itm Init(Xowe_wiki wiki, byte[] heading_bry) {
		if (Img_src_y == null) {
			Io_url img_dir = wiki.Appe().Usere().Fsys_mgr().App_img_dir().GenSubDir_nest("window", "portal");
			Img_src_y = img_dir.GenSubFil("twisty_down.png").To_http_file_bry();
			Img_src_n = img_dir.GenSubFil("twisty_right.png").To_http_file_bry();
		}
		icon_src = visible ? Img_src_y : Img_src_n;
		byte[] img_title_msg = visible ? Img_title_msg_y : Img_title_msg_n;
		icon_title = wiki.Msg_mgr().Val_by_key_obj(img_title_msg);
		elem_display = visible ? Img_display_y : Img_display_n;
		this.heading_bry = heading_bry;
		Html_toggle_gen();
		return this;
	}
	private byte[] img_title_val_y, img_title_val_n;
	public Xoapi_toggle_itm Init_fsys(Io_url img_dir) {
		if (Img_src_y == null) {
			Img_src_y = img_dir.GenSubFil("twisty_down.png").To_http_file_bry();
			Img_src_n = img_dir.GenSubFil("twisty_right.png").To_http_file_bry();
		}
		return this;
	}
	public void Init_msgs(byte[] img_title_val_y, byte[] img_title_val_n) {
		this.img_title_val_y = img_title_val_y;
		this.img_title_val_n = img_title_val_n;
		Html_toggle_gen();
	}
	public byte[] Html_toggle_btn() {return html_toggle_btn;} private byte[] html_toggle_btn;
	public byte[] Html_toggle_hdr() {return html_toggle_hdr;} private byte[] html_toggle_hdr;
	private void Html_toggle_gen() {
		if (visible) {
			icon_src = Img_src_y;
			icon_title = img_title_val_y;
			elem_display = Img_display_y;
		}
		else {
			icon_src = Img_src_n;
			icon_title = img_title_val_n;
			elem_display = Img_display_n;
		}
		Bry_fmtr fmtr = Bry_fmtr.new_(); Bry_bfr bfr = Bry_bfr.new_(8); 
		html_toggle_btn
			= fmtr.Fmt_("<a href='javascript:xowa_toggle_visible(\"~{key}\");' style='text-decoration: none !important;'>~{heading}<img id='~{key}-toggle-icon' src='~{src}' title='~{title}' /></a>")
			.Keys_("key", "src", "title", "heading").Bld_bry_many(bfr, key_bry, icon_src, icon_title, heading_bry)
			;
		html_toggle_hdr
			= fmtr.Fmt_(" id='~{key}-toggle-elem' style='~{display}~{toggle_hdr_cls}'")
			.Keys_("key", "display", "toggle_hdr_cls").Bld_bry_many(bfr, key_bry, elem_display, html_toggle_hdr_cls)
			;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_visible)) 			return Yn.Xto_str(visible);
		else if	(ctx.Match(k, Invk_visible_)) 			{this.visible = m.ReadYn("v"); Html_toggle_gen();}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_visible = "visible", Invk_visible_ = "visible_";
	private static byte[] Img_src_y, Img_src_n;	// assume these are the same for all itms
	private static final byte[] 
	  Img_title_msg_y = Bry_.new_a7("hide"), Img_title_msg_n = Bry_.new_a7("show")
	, Img_display_y = Bry_.new_a7("display:;"), Img_display_n = Bry_.new_a7("display:none;")
	;
}
