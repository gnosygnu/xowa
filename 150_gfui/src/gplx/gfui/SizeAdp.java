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
