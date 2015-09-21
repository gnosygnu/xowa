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
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import gplx.gfui.*;
public class Xol_font_info implements GfoInvkAble, GfoEvMgrOwner {
	public Xol_font_info(String name, float size, FontStyleAdp style) {this.name = name; this.size = size; this.style = style;}
	public GfoEvMgr			EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public String Name() {return name;} public Xol_font_info Name_(String v) {name = v; Font_changed_pub(); return this;} private String name;
	public float Size() {return size;} public Xol_font_info Size_(float v) {size = v; Font_changed_pub(); return this;} private float size;
	public FontStyleAdp Style() {return style;} public Xol_font_info Style_(FontStyleAdp v) {style = v; Font_changed_pub(); return this;} private FontStyleAdp style;
	public Xol_font_info CloneNew() {return new Xol_font_info(name, size, style);}
	public FontAdp XtoFontAdp() {return FontAdp.new_(name, size, style);}
	public boolean Eq(FontAdp font) {return String_.Eq(name, font.Name()) && size == font.Size() && style.Val() == font.Style().Val();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_name))			return name;
		else if (ctx.Match(k, Invk_name_))			Name_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_size))			return size;
		else if	(ctx.Match(k, Invk_size_))			Size_(m.ReadFloat("v"));
		else if	(ctx.Match(k, Invk_style_))			Style_(FontStyleAdp_.parse(m.ReadStr("v")));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_name = "name", Invk_name_ = "name_", Invk_size = "size", Invk_size_ = "size_", Invk_style_ = "style_";
	public static final String Font_changed = "font_changed";
	private void Font_changed_pub() {GfoEvMgr_.PubObj(this, Font_changed, "font", this);}
}	
