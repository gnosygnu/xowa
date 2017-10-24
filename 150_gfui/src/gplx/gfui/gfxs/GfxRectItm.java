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
public class GfxRectItm extends GfxItm_base {
	public float Width() {return width;} float width;
	public ColorAdp Color() {return color;} ColorAdp color;

	@Override public String toString() {return String_.Concat(super.toString(), String_bldr_.new_().Add_kv_obj("width", width).Add_kv("color", color.XtoHexStr()).To_str());}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxRectItm comp = GfxRectItm.as_(obj); if (comp == null) return false;
		return super.equals(comp) && width == comp.width && color.Eq(comp.color);	
	}
	public static GfxRectItm new_(PointAdp pos, SizeAdp size, float width, ColorAdp color) {
		GfxRectItm rv = new GfxRectItm();
		rv.ctor_GfxItmBase(pos, size);
		rv.width = width; rv.color = color;
		return rv;
	}	GfxRectItm() {}
	@gplx.New public static GfxRectItm as_(Object obj) {return obj instanceof GfxRectItm ? (GfxRectItm)obj : null;}
}
