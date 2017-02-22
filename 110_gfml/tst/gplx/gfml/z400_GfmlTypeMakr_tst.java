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
public class z400_GfmlTypeMakr_tst {
	@Before public void setup() {
		makr = GfmlTypeMakr.new_();
	}	GfmlTypeMakr makr; GfmlType type; GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void MakeSubType() {
		type = makr.MakeSubType("point", "x", "y");			
		fx.tst_Type
			(	fx.typ_().Key_("point").Subs_
			(		fx.fld_().ini_atr_("x").TypeKey_(GfmlType_.StringKey)
			,		fx.fld_().ini_atr_("y").TypeKey_(GfmlType_.StringKey)		
			)
			,	GfmlTyp_mok.type_(type)
			);
		tst_XtoAry(makr, "point");
	}
	@Test  public void MakeSubTypeAsOwner() {
		type = makr.MakeSubTypeAsOwner("item");
		fx.tst_Type
			(	fx.typ_().Key_("item")
			,	GfmlTyp_mok.type_(type)
			);
		tst_Owner(makr, "item");
		tst_XtoAry(makr, "item");
	}
	@Test  public void MakeSubTypeAsOwner_MakeSubType() {
		type = makr.MakeSubTypeAsOwner("item");
		makr.MakeSubType("pos", "x", "y");
		fx.tst_Type
			(	fx.typ_().Key_("item").Subs_
			(		fx.fld_().Name_("pos").TypeKey_("item/pos"))
			,	GfmlTyp_mok.type_(type)
			);
		tst_Owner(makr, "item");
		tst_XtoAry(makr, "item", "item/pos");
	}		
	void tst_Owner(GfmlTypeMakr typeMakr, String expdKey) {
		Tfds.Eq(expdKey, typeMakr.Owner().Key());
	}
	void tst_XtoAry(GfmlTypeMakr typeMakr, String... expdAry) {
		GfmlType[] actlTypeAry = typeMakr.Xto_bry();
		String[] actlAry = new String[actlTypeAry.length];
		for (int i = 0; i < actlAry.length; i++) {
			actlAry[i] = actlTypeAry[i].Key();
		}
		Tfds.Eq_ary(expdAry, actlAry);
	}
}
