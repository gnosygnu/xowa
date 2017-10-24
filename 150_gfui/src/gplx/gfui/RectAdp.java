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

	public RectAdp(PointAdp pos, SizeAdp size) {this.pos = pos; this.size = size;}
}
