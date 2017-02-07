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
package gplx.gfml; import gplx.*;
class GfmlDefaultItem_fxt {
	@gplx.Internal protected GfmlDefaultItem make_(String typeKey, String key, GfmlTkn val) {return GfmlDefaultItem.new_(typeKey, key, val);}
	@gplx.Internal protected GfmlDefaultItem make_(String typeKey, String key, String val) {return GfmlDefaultItem.new_(typeKey, key, GfmlTkn_.raw_(val));}
	@gplx.Internal protected void tst_Item(GfmlDefaultItem actl, GfmlDefaultItem expd) {
		Tfds.Eq(expd.TypeKey(), actl.TypeKey());
		Tfds.Eq(expd.Key(), actl.Key());
		Tfds.Eq(GfmlFld_mok.XtoRaw(expd.Val()), GfmlFld_mok.XtoRaw(actl.Val()));
		Tfds.Eq(Val_to_str_or_null(expd.ValPrev()), Val_to_str_or_null(actl.ValPrev()));
	}
	@gplx.Internal protected void tst_List(List_adp list, GfmlDefaultItem... expdAry) {
		for (int i = 0; i < expdAry.length; i++) {
			GfmlDefaultItem actl = (GfmlDefaultItem)list.Get_at(i);
			GfmlDefaultItem expd = expdAry[i];
			tst_Item(actl, expd);
		}
		Tfds.Eq(expdAry.length, list.Count());
	}
	String Val_to_str_or_null(GfmlObj gobj) {
		GfmlTkn tkn = GfmlTkn_.as_(gobj);
		return tkn == null ? null : tkn.Val();
	}
	public static GfmlDefaultItem_fxt new_() {return new GfmlDefaultItem_fxt();} GfmlDefaultItem_fxt() {}
}
