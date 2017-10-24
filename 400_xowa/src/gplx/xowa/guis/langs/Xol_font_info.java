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
package gplx.xowa.guis.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.draws.*;
public class Xol_font_info implements Gfo_invk, Gfo_evt_mgr_owner {
	private FontStyleAdp style;
	public Xol_font_info(String name, float size, FontStyleAdp style) {
		this.name = name; this.size = size; this.style = style;
	}
	public Gfo_evt_mgr		Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public String			Name() {return name;} public Xol_font_info Name_(String v) {name = v; Font_changed_pub(); return this;} private String name;
	public float			Size() {return size;} public Xol_font_info Size_(float v) {size = v; Font_changed_pub(); return this;} private float size;
	public FontAdp To_font() {return FontAdp.new_(name, size, style);}
	public boolean Eq(FontAdp font) {return String_.Eq(name, font.Name()) && size == font.Size() && style.Val() == font.Style().Val();}
	public void Init_by_app(Xoae_app app) {
		app.Cfg().Bind_many_app(this, Cfg__font_name, Cfg__font_size);
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__font_name))		Name_(m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__font_size))		Size_(m.ReadFloat("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Font_changed = "font_changed";
	private void Font_changed_pub() {Gfo_evt_mgr_.Pub_obj(this, Font_changed, "font", this);}

	private static final String Cfg__font_name = "xowa.gui.app.font.name";
	public static final String Cfg__font_size = "xowa.gui.app.font.size";
}	
