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
public class RectAdp_ {
	public static final    RectAdp Zero = new RectAdp(PointAdp_.Zero, SizeAdp_.Zero);
	public static RectAdp new_(int x, int y, int width, int height) {return new RectAdp(PointAdp_.new_(x, y), SizeAdp_.new_(width, height));}
	public static RectAdp corners_(PointAdp upperLeft, PointAdp bottomRight) {return new RectAdp(upperLeft, SizeAdp_.corners_(upperLeft, bottomRight));}
	public static RectAdp vector_(PointAdp pos, SizeAdp size) {return new RectAdp(pos, size);}
	public static RectAdp size_(int w, int h) {return new_(0, 0, w, h);}
	public static RectAdp size_(SizeAdp size) {return new RectAdp(PointAdp_.Zero, size);}
	public static RectAdp parse_ws_(String raw) {return parse(String_.Replace(raw, " ", ""));}
	public static RectAdp parse(String raw) {
		try {
			String[] ary = String_.Split(raw, ",");
			return RectAdp_.new_(Int_.parse(ary[0]), Int_.parse(ary[1]), Int_.parse(ary[2]), Int_.parse(ary[3]));
		}	catch(Exception exc) {throw Err_.new_parse_exc(exc, RectAdp.class, raw);}
	}
	public static String Xto_str(RectAdp rect) {return String_.Format("{0},{1},{2},{3}", rect.X(), rect.Y(), rect.Width(), rect.Height());}
}
