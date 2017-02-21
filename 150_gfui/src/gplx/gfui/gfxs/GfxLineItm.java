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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
import gplx.core.strings.*;
import gplx.gfui.draws.*;
public class GfxLineItm implements GfxItm {
	public PointAdp Src() {return src;} PointAdp src = PointAdp_.Zero;
	public PointAdp Trg() {return trg;} PointAdp trg = PointAdp_.Zero;
	public float Width() {return width;} float width;
	public ColorAdp Color() {return color;} ColorAdp color;

	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("src", src).Add_kv_obj("trg", trg).Add_kv_obj("width", width).Add_kv_obj("color", color.XtoHexStr()).To_str();}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxLineItm comp = GfxLineItm.as_(obj); if (comp == null) return false;
		return src.Eq(comp.src) && trg.Eq(comp.trg) && width == comp.width && color.Eq(comp.color);
	}
	public static GfxLineItm new_(PointAdp src, PointAdp trg, float width, ColorAdp color) {
		GfxLineItm rv = new GfxLineItm();
		rv.src = src; rv.trg = trg;
		rv.width = width; rv.color = color;
		return rv;
	}	GfxLineItm() {}
	public static GfxLineItm as_(Object obj) {return obj instanceof GfxLineItm ? (GfxLineItm)obj : null;}
	public static GfxLineItm cast(Object obj) {try {return (GfxLineItm)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfxLineItm.class, obj);}}
}
