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
import org.junit.*;
public class z423_types_apply_ndes_misc_tst {
	@Before public void setup() {
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Recurse() {
		fx.Regy().Add(
			fx.typ_().Key_("item").Subs_
			(	GfmlFld_mok.new_().ini_atr_("key")
			,	GfmlFld_mok.new_().ini_ndk_("item", "item")
			).XtoGfmlType());
		fx.tst_Resolve
			(	fx.nde_().Hnd_("item").Atru_("1").Subs_
			(		fx.nde_().Hnd_("item").Atru_("2"))
			,	fx.nde_().Hnd_("item").Atrk_("key", "1").Subs_
			(		fx.nde_().Hnd_("item").Atrk_("key", "2")				
			));
	}
	@Test  public void OwnerTypePrecedesTopLevel() {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		GfmlType topLevelSize = makr.MakeSubType("size", "width", "height");
		GfmlType rect = makr.MakeSubTypeAsOwner("rect");
		GfmlType rectSize = makr.MakeSubType("size", "w", "h");
		fx.Regy().Add(topLevelSize).Add(rect).Add(rectSize);
		fx.tst_Resolve
			(	fx.nde_().Hnd_("rect").Subs_
			(		fx.nde_().Hnd_("size").Atru_("1").Atru_("2"))
			,	fx.nde_().Hnd_("rect").Subs_
			(		fx.nde_().Typ_("rect/size").Atrk_("w", "1").Atrk_("h", "2")
			));
	}
}
