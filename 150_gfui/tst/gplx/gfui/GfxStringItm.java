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
