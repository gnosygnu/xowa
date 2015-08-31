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
import gplx.core.strings.*;
public class GfxLineItm implements GfxItm {
	public PointAdp Src() {return src;} PointAdp src = PointAdp_.Zero;
	public PointAdp Trg() {return trg;} PointAdp trg = PointAdp_.Zero;
	public float Width() {return width;} float width;
	public ColorAdp Color() {return color;} ColorAdp color;

	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("src", src).Add_kv_obj("trg", trg).Add_kv_obj("width", width).Add_kv_obj("color", color.XtoHexStr()).To_str();}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxLineItm comp = GfxLineItm.as_(obj); if (comp == null) return false;
		return src.Eq(comp.src) && trg.Eq(comp.trg) && width == comp.width && color.Eq(comp.color);
	}
	public static GfxLineItm new_(PointAdp src, PointAdp trg, float width, ColorAdp color) {
		GfxLineItm rv = new GfxLineItm();
		rv.src = src; rv.trg = trg;
		rv.width = width; rv.color = color;
		return rv;
	}	GfxLineItm() {}
	public static GfxLineItm as_(Object obj) {return obj instanceof GfxLineItm ? (GfxLineItm)obj : null;}
	public static GfxLineItm cast(Object obj) {try {return (GfxLineItm)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfxLineItm.class, obj);}}
}
