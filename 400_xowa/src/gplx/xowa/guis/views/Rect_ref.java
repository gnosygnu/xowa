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
