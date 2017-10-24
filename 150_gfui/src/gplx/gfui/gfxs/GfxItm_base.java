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
public abstract class GfxItm_base implements GfxItm {
	public PointAdp Pos() {return pos;} PointAdp pos = PointAdp_.Zero;
	public SizeAdp Size() {return size;} SizeAdp size = SizeAdp_.Zero;
	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("pos", pos).Add_kv_obj("size", size).To_str();}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxItm_base comp = GfxItm_base.as_(obj); if (comp == null) return false;
		return Object_.Eq(pos, comp.pos) && Object_.Eq(size, comp.size);
	}
	@gplx.Virtual public void ctor_GfxItmBase(PointAdp posVal, SizeAdp sizeVal) {
		pos = posVal; size = sizeVal;
	}
	public static GfxItm_base as_(Object obj) {return obj instanceof GfxItm_base ? (GfxItm_base)obj : null;}
	public static GfxItm_base cast(Object obj) {try {return (GfxItm_base)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfxItm_base.class, obj);}}
}
