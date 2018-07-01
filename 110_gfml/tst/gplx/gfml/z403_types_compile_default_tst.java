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
public class z403_types_compile_default_tst {
	@Before public void setup() {
		fx.run_InitPragma(fx.Regy(), GfmlPragmaType.new_());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Basic() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("point", "gfml.point").Subs_
			(		fx.nde_().Atru_("x").Atrk_("default", "10")	
			,		fx.nde_().Atru_("y")
			)
			,	fx.typ_().Name_("point").Key_("gfml.point").Subs_
			(		fx.fld_().Name_("x").Default_("10")
			,		fx.fld_().Name_("y").Default_(GfmlTkn_.NullRaw)
			));
	}
	@Test  public void Nde() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("rect", "gfml.rect").Subs_
			(		fx.nde_().Atru_("point").Subs_
			(			fx.nde_().Key_("default").Atrk_("x", "1").Atrk_("y", "2")		// NOTE: naming b/c unnamed attribs are not returned in SubKeys
			))
			,	fx.typ_().Name_("rect").Key_("gfml.rect").Subs_
			(		fx.fld_().Name_("point").DefaultTkn_
			(			ndek_("point", atr_("x", "1"), atr_("y", "2")))
			));
	}
	@Test  public void Nde_unnamed() {
		fx.tst_Compile
			(	fx.nde_().Atrs_("rect", "gfml.rect").Subs_
			(		fx.nde_().Atru_("point").Subs_
			(			fx.nde_().Key_("").Atru_("1").Atru_("2")
			))
			,	fx.typ_().Name_("rect").Key_("gfml.rect").Subs_
			(		fx.fld_().Name_("point").DefaultTkn_
			(			ndek_("point", atr_("", "1"), atr_("", "2")))
			));
	}
	static GfmlAtr atr_(String key, String val)		{return GfmlAtr.string_(GfmlTkn_.raw_(key), GfmlTkn_.raw_(val));}
	static GfmlNde ndek_(String key, GfmlItm... subs) {
		GfmlNde rv = GfmlNde.new_(GfmlTkn_.raw_(key), GfmlType_.Null, true);
		for (GfmlItm sub : subs)
			rv.SubObjs_Add(sub);
		return rv;
	}
}
