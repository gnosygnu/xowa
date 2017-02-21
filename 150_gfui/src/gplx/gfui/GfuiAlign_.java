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
import gplx.core.interfaces.*;
public class GfuiAlign_ implements ParseAble {
	public static GfuiAlign as_(Object obj) {return obj instanceof GfuiAlign ? (GfuiAlign)obj : null;}
	public static GfuiAlign cast(Object obj) {try {return (GfuiAlign)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiAlign.class, obj);}}
	public static final    GfuiAlign 
		  Null	= new_(0, "nil")
		, Lo	= new_(1, "lo")
		, Mid	= new_(2, "mid")
		, Hi	= new_(3, "hi");
	public static final    GfuiAlign 
		  Top	= Lo
		, Bot	= Hi
		, Left	= Lo
		, Right	= Hi;
	static GfuiAlign new_(int v, String s) {return new GfuiAlign(v, s);}
	public static final    GfuiAlign_ Parser = new GfuiAlign_();
	public Object ParseAsObj(String raw) {return parse(raw);}
	public static GfuiAlign val_(int v) {
		if		(v == Lo.Val())		return Lo;
		else if	(v == Mid.Val())	return Mid;
		else if	(v == Hi.Val())		return Hi;
		else						return Null;
	}
	public static GfuiAlign parse(String raw) {
		if		(String_.Eq(raw, "bot")) return Bot;
		else if	(String_.Eq(raw, "mid")) return Mid;
		else if	(String_.Eq(raw, "top")) return Top;
		return Null;
	}
	public static PointAdp CalcInsideOf(GfuiAlign h, GfuiAlign v, SizeAdp inner, SizeAdp outer, PointAdp adjust) {
		int x = CalcInsideOfAxis(h.Val(), inner.Width(), outer.Width());
		int y = CalcInsideOfAxis(v.Val(), inner.Height(), outer.Height());
		return PointAdp_.new_(x + adjust.X(), y + adjust.Y());			
	}
	public static int CalcInsideOfAxis(int posEnm, int innerSize, int outerSize) {
		int rv = 0;
		if		(posEnm == GfuiAlign_.Null.Val())	rv = Int_.Min_value;
		else if (posEnm == GfuiAlign_.Lo.Val())		rv = 0;
		else if (posEnm == GfuiAlign_.Mid.Val())	rv = (outerSize - innerSize) / 2;
		else if (posEnm == GfuiAlign_.Hi.Val())		rv = outerSize - innerSize;
		else										throw Err_.new_unhandled(posEnm);
		if (rv < 0) rv = 0;
		return rv;
	}
}
