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
public class SizeAdp_ {
	public static final    SizeAdp Null = new SizeAdp(Int_.Min_value, Int_.Min_value);
	public static final    SizeAdp Zero = new SizeAdp(0, 0);
	public static final    SizeAdp[] Ary_empty = new SizeAdp[0];
	public static SizeAdp cast(Object obj) {try {return (SizeAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, SizeAdp.class, obj);}}
	public static SizeAdp new_(int width, int height) {return new SizeAdp(width, height);}
	public static SizeAdp parse(String raw) {return parse_or(raw, SizeAdp_.Null);}
	public static SizeAdp parse_or(String raw, SizeAdp or) {
		String[] ary = String_.Split(raw, ","); if (ary.length != 2) return or;
		int w = Int_.parse_or(ary[0], Int_.Min_value); if (w == Int_.Min_value) return or;
		int h = Int_.parse_or(ary[1], Int_.Min_value); if (h == Int_.Min_value) return or;
		return new SizeAdp(w, h);
	}
	public static SizeAdp corners_(PointAdp topLeft, PointAdp bottomRight) {
		int width = bottomRight.X() - topLeft.X();
		int height = bottomRight.Y() - topLeft.Y();
		return new_(width, height);
	}
	public static PointAdp center_(SizeAdp outer, SizeAdp inner) {
		int x = (outer.Width() - inner.Width()) / 2; if (x < 0) x = 0;
		int y = (outer.Height() - inner.Height()) / 2; if (y < 0) y = 0;
		return PointAdp_.new_(x, y);
	}
}
