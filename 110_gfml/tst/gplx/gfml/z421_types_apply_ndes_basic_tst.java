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
public class z421_types_apply_ndes_basic_tst {
	@Before public void setup() {
		fx.Regy().Add(
			fx.typ_().Key_("pos").Subs_
			(		GfmlFld_mok.new_().ini_atr_("x")
			,		GfmlFld_mok.new_().ini_atr_("y")
			).XtoGfmlType());
		fx.Regy().Add(
			fx.typ_().Key_("rect").Subs_
			(		GfmlFld_mok.new_().ini_ndk_("pos", "pos")
			).XtoGfmlType());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void NamedNde_but_UnnamedAtr() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_("pos").Atrs_("1")
			)
		,	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			)
		);
	}
	@Test  public void UnnamedNde() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("1")
			)
		,	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			)
		);
	}
}
