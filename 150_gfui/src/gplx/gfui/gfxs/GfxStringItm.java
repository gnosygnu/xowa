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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
import gplx.gfui.draws.*;
public class GfxStringItm extends GfxItm_base {
	public String Text() {return text;} private String text;
	public FontAdp Font() {return font;} FontAdp font;
	public SolidBrushAdp Brush() {return brush;} SolidBrushAdp brush;
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxStringItm comp = GfxStringItm.as_(obj); if (comp == null) return false;
		return super.equals(obj) && String_.Eq(text, comp.text) && font.Eq(comp.font) && brush.Eq(comp.brush);	
	}
	public static GfxStringItm new_(PointAdp pos, SizeAdp size, String text, FontAdp font, SolidBrushAdp brush) {
		GfxStringItm rv = new GfxStringItm();
		rv.ctor_GfxItmBase(pos, size);
		rv.text = text; rv.font = font; rv.brush = brush;
		return rv;
	}	GfxStringItm() {}
	public static GfxStringItm test_(String text, FontAdp font, SolidBrushAdp brush) {
		return GfxStringItm.new_(PointAdp_.Null, SizeAdp_.Null, text, font, brush);
	}
	@gplx.New public static GfxStringItm as_(Object obj) {return obj instanceof GfxStringItm ? (GfxStringItm)obj : null;}
}
