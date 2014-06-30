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
public class GfxRectItm extends GfxItm_base {
	public float Width() {return width;} float width;
	public ColorAdp Color() {return color;} ColorAdp color;

	@Override public String toString() {return String_.Concat(super.toString(), String_bldr_.new_().Add_kv_obj("width", width).Add_kv("color", color.XtoHexStr()).XtoStr());}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxRectItm comp = GfxRectItm.as_(obj); if (comp == null) return false;
		return super.equals(comp) && width == comp.width && color.Eq(comp.color);	
	}
	public static GfxRectItm new_(PointAdp pos, SizeAdp size, float width, ColorAdp color) {
		GfxRectItm rv = new GfxRectItm();
		rv.ctor_GfxItmBase(pos, size);
		rv.width = width; rv.color = color;
		return rv;
	}	GfxRectItm() {}
	@gplx.New public static GfxRectItm as_(Object obj) {return obj instanceof GfxRectItm ? (GfxRectItm)obj : null;}
}
