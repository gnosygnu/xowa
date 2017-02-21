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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import java.awt.Font;
import java.awt.Toolkit;
import gplx.core.strings.*; import gplx.core.envs.*; import gplx.gfui.controls.gxws.*;
public class FontAdp implements Gfo_invk {
	public String Name() {return name;} public FontAdp Name_(String val) {name = val; InitUnder(); return this;} private String name;
	public float Size() {return size;} public FontAdp Size_(float val) {size = val; InitUnder(); return this;} float size;
	public FontStyleAdp Style() {return style;} public FontAdp Style_(FontStyleAdp val) {style = val; InitUnder(); return this;} FontStyleAdp style;
	public Font UnderFont() {if (font == null) InitUnder(); return font;}	Font font = null;
	void InitUnder() {
		if (Env_.Mode_testing()) return; // WORKAROUND/.NET: NUnit will randomlyly throw exceptions
		font = FontAdpCache.Instance.GetNativeFont(this);
		if (ownerGxwCore != null) ownerGxwCore.TextFont_set(this);
	}
	public FontAdp OwnerGxwCore_(GxwCore_base v) {ownerGxwCore = v; return this;} GxwCore_base ownerGxwCore;
	public boolean Eq(Object obj) {
		FontAdp comp = FontAdp.as_(obj); if (comp == null) return false;
		return name == comp.name && size == comp.size && style == comp.style;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "Name"))	{return ctx.Deny() ? (Object)this : Name();}
		else if	(ctx.Match(k, "Size"))	{return ctx.Deny() ? (Object)this : Size();}
		else if	(ctx.Match(k, "Style")) {return ctx.Deny() ? (Object)this : Style();}
		else if	(ctx.Match(k, Invk_name_)) {
			String v = m.ReadStr("v");
			return ctx.Deny() ? (Object)this : Name_(v);
		}
		else if	(ctx.Match(k, Invk_size_)) {
			float v = m.ReadFloat("v");
			return ctx.Deny() ? (Object)this : Size_(v);
		}
		else if	(ctx.Match(k, Invk_style_)) {
			Object v = m.CastObj("v");
			return ctx.Deny() ? (Object)this : Style_(FontStyleAdp_.read_(v));
		}
		else return Gfo_invk_.Rv_unhandled;
	}	static final String Invk_name_ = "name_", Invk_size_ = "size_", Invk_style_ = "style_";
	@Override public String toString() {return String_bldr_.new_().Add_kv("name", name).Add_kv_obj("size", size).Add_kv_obj("style", style).To_str();}

	public static final    FontAdp NullPtr = null;
	public static FontAdp as_(Object obj) {return obj instanceof FontAdp ? (FontAdp)obj : null;}
	public static FontAdp cast(Object obj) {try {return (FontAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, FontAdp.class, obj);}}
	public static FontAdp new_(String name, float size, FontStyleAdp style) {
		FontAdp rv = new FontAdp();
		rv.name = name; rv.size = size; rv.style = style;
		return rv;
	}
}
