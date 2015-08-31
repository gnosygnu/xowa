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
package gplx.gfui; import gplx.*;
import java.awt.Font;
import java.awt.Toolkit;
import gplx.core.strings.*;
public class FontAdp implements GfoInvkAble {
	public String Name() {return name;} public FontAdp Name_(String val) {name = val; InitUnder(); return this;} private String name;
	public float Size() {return size;} public FontAdp Size_(float val) {size = val; InitUnder(); return this;} float size;
	public FontStyleAdp Style() {return style;} public FontAdp Style_(FontStyleAdp val) {style = val; InitUnder(); return this;} FontStyleAdp style;
	@gplx.Internal protected Font UnderFont() {if (font == null) InitUnder(); return font;}	Font font = null;
	void InitUnder() {
		if (Env_.Mode_testing()) return; // WORKAROUND/.NET: NUnit will randomlyly throw exceptions
		font = FontAdpCache._.GetNativeFont(this);
		if (ownerGxwCore != null) ownerGxwCore.TextFont_set(this);
	}
	@gplx.Internal protected FontAdp OwnerGxwCore_(GxwCore_base v) {ownerGxwCore = v; return this;} GxwCore_base ownerGxwCore;
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
		else return GfoInvkAble_.Rv_unhandled;
	}	static final String Invk_name_ = "name_", Invk_size_ = "size_", Invk_style_ = "style_";
	@Override public String toString() {return String_bldr_.new_().Add_kv("name", name).Add_kv_obj("size", size).Add_kv_obj("style", style).To_str();}

	public static final FontAdp NullPtr = null;
	public static FontAdp as_(Object obj) {return obj instanceof FontAdp ? (FontAdp)obj : null;}
	public static FontAdp cast(Object obj) {try {return (FontAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, FontAdp.class, obj);}}
	public static FontAdp new_(String name, float size, FontStyleAdp style) {
		FontAdp rv = new FontAdp();
		rv.name = name; rv.size = size; rv.style = style;
		return rv;
	}
}
class FontAdpCache {
	public Font GetNativeFont(FontAdp fontAdp) {
		String key = fontAdp.toString();
		Font rv = (Font)hash.Get_by(key); if (rv != null) return rv;
				if (screenResolutionInDpi == -1) ScreenResolution_set();
	    int fontSize = XtoJavaDpi(fontAdp.Size());
		rv = new Font(fontAdp.Name(), fontAdp.Style().Val(), fontSize);		
				hash.Add(key, rv);
		return rv;
	}	Hash_adp hash = Hash_adp_.new_();
		public static void ScreenResolution_set() {screenResolutionInDpi = Toolkit.getDefaultToolkit().getScreenResolution();}	// usually either 96 or 120
	public static int XtoOsDpi(float v) {return Math.round((v * 72) / screenResolutionInDpi);} // WORKAROUND/JAVA: Java needs 72 dpi screen resolution; wnt uses 96 or 120 dpi
	public static int XtoJavaDpi(float v) {return Math.round((v * screenResolutionInDpi) / 72);}
	static int screenResolutionInDpi = -1;
		public static final FontAdpCache _ = new FontAdpCache(); FontAdpCache() {}
}
