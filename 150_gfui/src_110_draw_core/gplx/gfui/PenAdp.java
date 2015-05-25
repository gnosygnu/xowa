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
import java.awt.BasicStroke;
import java.awt.Stroke;
import gplx.core.strings.*;
public class PenAdp implements GfoInvkAble {
	public float Width() {return width;} public void Width_set(float v) {width = v; InitUnder();} float width;
	public ColorAdp Color() {return color;} public void Color_set(ColorAdp v) {color = v; InitUnder();} ColorAdp color;
		public BasicStroke UnderStroke() {if (underStroke == null) InitUnder(); return underStroke;} BasicStroke underStroke; 
	void InitUnder() {underStroke = PenAdpCache._.Fetch(width);}
		public PenAdp Clone() {return PenAdp_.new_(color, width);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Width_))	Width_set(m.ReadFloat(Invk_Width_));
		else if	(ctx.Match(k, Invk_Color_))	Color_set((ColorAdp)m.ReadObj(Invk_Color_, ColorAdp_.Parser));
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	static final String Invk_Width_ = "Width_", Invk_Color_ = "Color_";
	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("width", width).Add_kv("color", color.XtoHexStr()).XtoStr();}
	@Override public int hashCode() {return color.Value() ^ (int)width;}
	@Override public boolean equals(Object obj) {	// cannot use Eq b/c of difficulty in comparing null instances
		PenAdp comp = PenAdp_.as_(obj); if (comp == null) return false;
		return color.Eq(comp.color) && width == comp.width;
	}		
	@gplx.Internal protected PenAdp(ColorAdp color, float width) {this.color = color; this.width = width;}
}
class PenAdpCache {
		public BasicStroke Fetch(float width) {
		Object rv = hash.Get_by(width);
		if (rv == null) {
			rv = new BasicStroke(width);
			hash.Add(width, rv);
		}
		return (BasicStroke)rv;
	}
		Hash_adp hash = Hash_adp_.new_();
	public static final PenAdpCache _ = new PenAdpCache(); PenAdpCache() {}
}
