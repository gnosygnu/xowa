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
public class PointAdp implements To_str_able {
	public int X() {return x;} final    int x;
	public int Y() {return y;} final    int y;
	public		PointAdp Op_add(PointAdp val) {return new PointAdp(x + val.x, y + val.y);}
	public		PointAdp Op_add(int xv, int yv) {return new PointAdp(x + xv, y + yv);}
	public 		PointAdp Op_add(int i) {return new PointAdp(x + i, y + i);}
	public		PointAdp Op_subtract(PointAdp val) {return new PointAdp(x - val.x, y - val.y);}
	public boolean Eq(Object compObj) {
		PointAdp comp = PointAdp_.as_(compObj); if (comp == null) return false;
		return x == comp.x && y == comp.y;
	}
	public String To_str() {return String_.Concat_any(x, ",", y);}
	@Override public String toString() {return To_str();}
	@Override public boolean equals(Object obj) {return Eq(obj);}
	@Override public int hashCode() {return super.hashCode();}
	public PointAdp(int x, int y) {this.x = x; this.y = y;}
}
