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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import gplx.core.strings.*; import gplx.core.encoders.*;
public class ColorAdp {
	public int Value()	{return val;} int val;
	public int Alpha()	{return (255 & val >> 24);}
	public int Red	()	{return (255 & val >> 16);}
	public int Green()	{return (255 & val >>  8);}
	public int Blue()	{return (255 & val);}
	public String XtoHexStr() {
		String_bldr sb = String_bldr_.new_();
		sb.Add("#");
		sb.Add(Hex_utl_.To_str(Alpha(), 2));
		sb.Add(Hex_utl_.To_str(Red(), 2));
		sb.Add(Hex_utl_.To_str(Green(), 2));
		sb.Add(Hex_utl_.To_str(Blue(), 2));
		return sb.To_str();
	}
	public boolean Eq(Object obj) {
		ColorAdp comp = ColorAdp_.as_(obj); if (comp == null) return false;
		return Object_.Eq(val, comp.val);
	}
	@Override public String toString() {return XtoHexStr();}
	public Object CloneNew() {return this;} // NOTE: 'return this' works b/c ColorAdp is read-only class; needed for comparisons; ex: ColorAdp_.Null == ColorAdp_.Null.CloneNew(); alternative would fail: return ColorAdp.new_(this.Alpha(), this.Red(), this.Green(), this.Blue());}
	public static ColorAdp new_(int alpha, int red, int green, int blue) {
		ColorAdp rv = new ColorAdp();
		rv.val = (int)((alpha << 24) | (red << 16) | (green << 8) | (blue));
		return rv;
	}	ColorAdp() {}
}
