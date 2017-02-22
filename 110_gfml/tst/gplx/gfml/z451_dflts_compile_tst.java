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
public class z451_dflts_compile_tst {
	@Before public void setup() {
		regy = fx_typ.Regy();
		pragma = GfmlPragmaDefault.new_();
		fx_typ.run_InitPragma(regy, pragma);
	}	GfmlTypRegy regy; GfmlPragmaDefault pragma; GfmlDefaultItem_fxt fx = GfmlDefaultItem_fxt.new_(); GfmlTypeCompiler_fxt fx_typ = GfmlTypeCompiler_fxt.new_(); 
	@Test  public void Basic() {
		GfmlNde gnde = fx_typ.run_Resolve(regy, "_default/type/atr"
			, fx_typ.nde_().Atru_("x").Atru_("10")
			);
		GfmlDefaultItem actl = pragma.CompileItem(gnde, "point");
		fx.tst_Item(actl, fx.make_("point", "x", "10"));
	}
	@Test  public void Many() {
		GfmlNde gnde = fx_typ.run_Resolve(regy, "_default/type"
			, 	fx_typ.nde_().Atru_("point").Subs_
			(		fx_typ.nde_().Atrs_("x", "10")
			,		fx_typ.nde_().Atrs_("y", "20")
			));
		List_adp list = List_adp_.New();
		pragma.CompileSubNde(gnde, list);
		fx.tst_List(list
			, fx.make_("point", "x", "10")
			, fx.make_("point", "y", "20")
			);
	}
}
