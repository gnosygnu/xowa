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
