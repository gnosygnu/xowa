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
