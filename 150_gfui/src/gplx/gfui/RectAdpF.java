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
public class RectAdpF {	//_20101206 // supports Graphics.MeasureString
	public float X() {return x;} float x; public float Y() {return y;} float y;
	public float Width() {return width;} float width; public float Height() {return height;} float height;
	public SizeAdpF Size() {if (size == null) size = SizeAdpF_.new_(width, height); return size;} SizeAdpF size;
	public boolean Eq(RectAdpF comp) {
		return comp.x == x && comp.y == y && comp.width == width && comp.height == height;
	}

	public static final    RectAdpF Null = new_(Int_.Min_value, Int_.Min_value, Int_.Min_value, Int_.Min_value);
	public static RectAdpF new_(float x, float y, float width, float height) {
		RectAdpF rv = new RectAdpF();
		rv.x = x; rv.y = y; rv.width = width; rv.height = height;
		return rv;
	}
}
