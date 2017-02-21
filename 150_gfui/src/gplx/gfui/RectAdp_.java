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
