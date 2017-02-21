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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Rect_ref {
	public Rect_ref(int x, int y, int w, int h) {this.x = x; this.y = y; this.w = w; this.h = h;}
	public int X() {return x;} public Rect_ref X_(int v) {x = v; return this;} private int x;
	public int Y() {return y;} public Rect_ref Y_(int v) {y = v; return this;} private int y;
	public int W() {return w;} public Rect_ref W_(int v) {w = v; return this;} private int w;
	public int H() {return h;} public Rect_ref H_(int v) {h = v; return this;} private int h;
	public int X_max() {return x + w;}
	public int Y_max() {return y + h;}
	public gplx.gfui.RectAdp XtoRectAdp() {return gplx.gfui.RectAdp_.new_(x, y, w, h);}
	public gplx.gfui.RectAdp XtoRectAdp_add(Rect_ref v) {return gplx.gfui.RectAdp_.new_(x + v.x, y + v.y, w + v.w, h + v.h);}
	@Override public String toString() {return String_.Format("{0},{1},{2},{3}", x, y, w, h);}
	public static final Rect_ref Zero = new Rect_ref(0, 0, 0, 0);
	public static Rect_ref rectAdp_(gplx.gfui.RectAdp v) {return new Rect_ref(v.X(), v.Y(), v.Width(), v.Height());}
	public static Rect_ref parse(String raw) {
		try {
			String[] ary = String_.Split(raw, ",");
			return new Rect_ref(Int_.parse(ary[0]), Int_.parse(ary[1]), Int_.parse(ary[2]), Int_.parse(ary[3]));
		}	catch(Exception exc) {throw Err_.new_parse_exc(exc, Rect_ref.class, raw);}
	}
}
