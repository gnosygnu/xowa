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
public class z401_types_compile_basic_tst {
	@Before public void setup() {
		fx.run_InitPragma(fx.Regy(), GfmlPragmaType.new_());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Basic() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("point", "gfml.point").Subs_
			(		fx.nde_().Atru_("x")
			,		fx.nde_().Atru_("y")
			)
			,	fx.typ_().Name_("point").Key_("gfml.point").Atrs_("x", "y")
			);
	}
	@Test  public void Nest() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("item", "gfml.item").Subs_
			(		fx.nde_().Atru_("pos").Atrk_("type", "gfml.item.point")
			)
			,	fx.typ_().Name_("item").Key_("gfml.item").Subs_
			(		fx.fld_().Name_("pos").TypeKey_("gfml.item.point")
			)
			);
	}
	@Test  public void NestMany() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("item", "gfml.item").Subs_
			(		fx.nde_().Atru_("pos").Atrk_("type", "gfml.item.point")
			,		fx.nde_().Atru_("size").Atrk_("type", "gfml.item.size")
			)
			,	fx.typ_().Name_("item").Key_("gfml.item").Subs_
			(		fx.fld_().Name_("pos").TypeKey_("gfml.item.point")
			,		fx.fld_().Name_("size").TypeKey_("gfml.item.size")
			)
			);
	}
	@Test  public void Recurse() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("widget", "gfml.widget").Subs_
			(		fx.nde_().Atru_("widget").Atrk_("type", "gfml.widget")
			)
			,	fx.typ_().Name_("widget").Key_("gfml.widget").Subs_
			(		fx.fld_().Name_("widget").TypeKey_("gfml.widget")
			)
			);
	}
}
