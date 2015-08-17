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
public class PointAdp implements To_str_able {
	public int X() {return x;} final int x;
	public int Y() {return y;} final int y;
	public		PointAdp Op_add(PointAdp val) {return new PointAdp(x + val.x, y + val.y);}
	@gplx.Internal protected	PointAdp Op_add(int xv, int yv) {return new PointAdp(x + xv, y + yv);}
	@gplx.Internal protected	PointAdp Op_add(int i) {return new PointAdp(x + i, y + i);}
	public		PointAdp Op_subtract(PointAdp val) {return new PointAdp(x - val.x, y - val.y);}
	public boolean Eq(Object compObj) {
		PointAdp comp = PointAdp_.as_(compObj); if (comp == null) return false;
		return x == comp.x && y == comp.y;
	}
	public String To_str() {return String_.Concat_any(x, ",", y);}
	@Override public String toString() {return To_str();}
	@Override public boolean equals(Object obj) {return Eq(obj);}
	@Override public int hashCode() {return super.hashCode();}
	@gplx.Internal protected PointAdp(int x, int y) {this.x = x; this.y = y;}
}
