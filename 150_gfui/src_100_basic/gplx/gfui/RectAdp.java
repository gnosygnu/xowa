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
public class RectAdp {
	public SizeAdp Size() {return size;} SizeAdp size = SizeAdp_.Zero;
	public PointAdp Pos() {return pos;} PointAdp pos = PointAdp_.Zero;
	public int Width() {return size.Width();} public int Height() {return size.Height();}
	public int X() {return pos.X();} public int Y() {return pos.Y();}
	public PointAdp CornerTL() {return pos;}
	public PointAdp CornerTR() {return PointAdp_.new_(pos.X() + size.Width(), pos.Y());}
	public PointAdp CornerBL() {return PointAdp_.new_(pos.X(), pos.Y() + size.Height());}
	public PointAdp CornerBR() {return PointAdp_.new_(pos.X() + size.Width(), pos.Y() + size.Height());}
	@gplx.Internal protected boolean ContainsPoint(PointAdp point) {
		return point.X() >= pos.X() && point.X() <= pos.X() + size.Width()
			&& point.Y() >= pos.Y() && point.Y() <= pos.Y() + size.Height();
	}
	public RectAdp Op_add(RectAdp v) {
		return new RectAdp(pos.Op_add(v.Pos()), size.Op_add(v.Size()));		
	}
	@Override public String toString() {return String_.Concat_any(pos, ";", size);}
	@Override public boolean equals(Object obj) {
		RectAdp comp = (RectAdp)obj;
		return size.Eq(comp.size) && pos.Eq(comp.pos);
	}
	@Override public int hashCode() {return super.hashCode();}
	public String Xto_str() {return String_.Concat_any(pos, ",", size);}

	@gplx.Internal protected RectAdp(PointAdp pos, SizeAdp size) {this.pos = pos; this.size = size;}
}
