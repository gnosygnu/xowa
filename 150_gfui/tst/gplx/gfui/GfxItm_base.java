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
import gplx.core.strings.*;
public abstract class GfxItm_base implements GfxItm {
	public PointAdp Pos() {return pos;} PointAdp pos = PointAdp_.Zero;
	public SizeAdp Size() {return size;} SizeAdp size = SizeAdp_.Zero;
	@Override public String toString() {return String_bldr_.new_().Add_kv_obj("pos", pos).Add_kv_obj("size", size).XtoStr();}
	@Override public int hashCode() {return this.toString().hashCode();}
	@Override public boolean equals(Object obj) {
		GfxItm_base comp = GfxItm_base.as_(obj); if (comp == null) return false;
		return Object_.Eq(pos, comp.pos) && Object_.Eq(size, comp.size);
	}
	@gplx.Virtual public void ctor_GfxItmBase(PointAdp posVal, SizeAdp sizeVal) {
		pos = posVal; size = sizeVal;
	}
	public static GfxItm_base as_(Object obj) {return obj instanceof GfxItm_base ? (GfxItm_base)obj : null;}
	public static GfxItm_base cast_(Object obj) {try {return (GfxItm_base)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfxItm_base.class, obj);}}
}
