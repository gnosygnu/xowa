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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import java.awt.BasicStroke;
import java.awt.Stroke;
import gplx.core.strings.*;
public class PenAdp implements Gfo_invk {
	public float Width() {return width;} public void Width_set(float v) {width = v; InitUnder();} float width;
	public ColorAdp Color() {return color;} public void Color_set(ColorAdp v) {color = v; InitUnder();} ColorAdp color;
		public BasicStroke UnderStroke() {if (underStroke == null) InitUnder(); return underStroke;} BasicStroke underStroke; 
	void InitUnder() {underStroke = PenAdpCache.Instance.Fetch(width);}
		public PenAdp Clone() {return PenAdp_.new_(color, width);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Width_))	Width_set(m.ReadFloat(Invk_Width_));
		else if	(ctx.Match(k, Invk_Color_))	Color_set((ColorAdp)m.ReadObj(Invk_Color_, ColorAdp_.Parser));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	static final    String Invk_Width_ = "Width_", Invk_Color_ = "Color_";
	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("width", width).Add_kv("color", color.XtoHexStr()).To_str();}
	@Override public int hashCode() {return color.Value() ^ (int)width;}
	@Override public boolean equals(Object obj) {	// cannot use Eq b/c of difficulty in comparing null instances
		PenAdp comp = PenAdp_.as_(obj); if (comp == null) return false;
		return color.Eq(comp.color) && width == comp.width;
	}		
	@gplx.Internal protected PenAdp(ColorAdp color, float width) {this.color = color; this.width = width;}
}
class PenAdpCache {
		public BasicStroke Fetch(float width) {
		Object rv = hash.Get_by(width);
		if (rv == null) {
			rv = new BasicStroke(width);
			hash.Add(width, rv);
		}
		return (BasicStroke)rv;
	}
		Hash_adp hash = Hash_adp_.New();
	public static final    PenAdpCache Instance = new PenAdpCache(); PenAdpCache() {}
}
