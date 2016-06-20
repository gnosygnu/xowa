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
public class SizeAdp {
	public int Width() {return width;} int width;
	public int Height() {return height;} int height;
	public int AxisLength(GfuiAxisType axis) {return axis == GfuiAxisType.X ? width : height;}
	public		SizeAdp Op_add(int w, int h) {return SizeAdp_.new_(width + w, height + h);}
	public		SizeAdp Op_add(SizeAdp s) {return SizeAdp_.new_(width + s.width, height + s.height);}
	public		SizeAdp Op_subtract(int val) {return SizeAdp_.new_(width - val, height - val);}
	public 		SizeAdp Op_subtract(int w, int h) {return SizeAdp_.new_(width - w, height - h);}
	public String To_str() {return String_.Concat_any(width, ",", height);}
	public boolean Eq(Object o) {
		SizeAdp comp = (SizeAdp)o; if (comp == null) return false;
		return width == comp.width && height == comp.height;
	}
	@Override public String toString() {return To_str();}
	@Override public boolean equals(Object obj) {return Eq(obj);}
	@Override public int hashCode() {return super.hashCode();}
	public SizeAdp(int width, int height) {this.width = width; this.height = height;}
}
