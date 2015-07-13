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
public class GfuiAlign_ implements ParseAble {
	public static GfuiAlign as_(Object obj) {return obj instanceof GfuiAlign ? (GfuiAlign)obj : null;}
	public static GfuiAlign cast_(Object obj) {try {return (GfuiAlign)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, GfuiAlign.class, obj);}}
	public static final GfuiAlign 
		  Null	= new_(0, "nil")
		, Lo	= new_(1, "lo")
		, Mid	= new_(2, "mid")
		, Hi	= new_(3, "hi");
	public static final GfuiAlign 
		  Top	= Lo
		, Bot	= Hi
		, Left	= Lo
		, Right	= Hi;
	static GfuiAlign new_(int v, String s) {return new GfuiAlign(v, s);}
	public static final GfuiAlign_ Parser = new GfuiAlign_();
	public Object ParseAsObj(String raw) {return parse_(raw);}
	public static GfuiAlign val_(int v) {
		if		(v == Lo.Val())		return Lo;
		else if	(v == Mid.Val())	return Mid;
		else if	(v == Hi.Val())		return Hi;
		else						return Null;
	}
	public static GfuiAlign parse_(String raw) {
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
		if		(posEnm == GfuiAlign_.Null.Val())	rv = Int_.MinValue;
		else if (posEnm == GfuiAlign_.Lo.Val())		rv = 0;
		else if (posEnm == GfuiAlign_.Mid.Val())	rv = (outerSize - innerSize) / 2;
		else if (posEnm == GfuiAlign_.Hi.Val())		rv = outerSize - innerSize;
		else										throw Exc_.new_unhandled(posEnm);
		if (rv < 0) rv = 0;
		return rv;
	}
}
