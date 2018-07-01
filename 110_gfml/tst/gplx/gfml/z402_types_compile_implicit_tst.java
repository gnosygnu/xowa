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
public class z402_types_compile_implicit_tst {
	@Before public void setup() {
		fx.run_InitPragma(fx.Regy(), GfmlPragmaType.new_());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Fld_typeKey_leaf() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("point", "gfml.point").Subs_
			(		fx.nde_().Atru_("x").Atrk_("type", "gfml.int")		// explicit
			,		fx.nde_().Atru_("y")								// implicit: GfmlType_.String
			)
			,	fx.typ_().Name_("point").Key_("gfml.point").Subs_
			(		fx.fld_().Name_("x").TypeKey_("gfml.int")
			,		fx.fld_().Name_("y").TypeKey_("gfml.String")
			));
	}
	@Test  public void Fld_typeKey_nest() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("rect", "gfml.rect").Subs_
			(		fx.nde_().Atru_("pos").Atrk_("type", "gfml.point").Subs_	// explicit
			(			fx.nde_().Atru_("x")
			,			fx.nde_().Atru_("y")
			)
			,		fx.nde_().Atru_("size").Subs_								// implicit: gfml.rect.size; (needs to have subs)
			(			fx.nde_().Atru_("w")
			,			fx.nde_().Atru_("h")
			)
			)
			,	fx.typ_().Name_("rect").Key_("gfml.rect").Subs_
			(		fx.fld_().Name_("pos").TypeKey_("gfml.point")
			,		fx.fld_().Name_("size").TypeKey_("gfml.rect/size")
			));
	}
	@Test  public void Typ_key() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("point", "gfml.point")				// explicit: gfml.point
			,	fx.typ_().Name_("point").Key_("gfml.point")
			);

		fx.tst_Compile
			(	fx.nde_().Atrs_("point")							// implicit: point
			,	fx.typ_().Name_("point").Key_("point")
			);
	}
}
