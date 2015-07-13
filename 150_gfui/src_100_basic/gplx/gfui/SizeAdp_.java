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
public class SizeAdp_ {
	public static final SizeAdp Null = new SizeAdp(Int_.MinValue, Int_.MinValue);
	public static final SizeAdp Zero = new SizeAdp(0, 0);
	public static final SizeAdp[] Ary_empty = new SizeAdp[0];
	public static SizeAdp cast_(Object obj) {try {return (SizeAdp)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, SizeAdp.class, obj);}}
	public static SizeAdp new_(int width, int height) {return new SizeAdp(width, height);}
	public static SizeAdp parse_(String raw) {return parse_or_(raw, SizeAdp_.Null);}
	public static SizeAdp parse_or_(String raw, SizeAdp or) {
		String[] ary = String_.Split(raw, ","); if (ary.length != 2) return or;
		int w = Int_.parse_or_(ary[0], Int_.MinValue); if (w == Int_.MinValue) return or;
		int h = Int_.parse_or_(ary[1], Int_.MinValue); if (h == Int_.MinValue) return or;
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
