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
public class z422_types_apply_ndes_multi_tst {
	@Before public void setup() {
		fx.Regy().Add
			(	fx.typ_().Key_("pos").Subs_
			(		GfmlFld_mok.new_().ini_atr_("x")
			,		GfmlFld_mok.new_().ini_atr_("y")
			).XtoGfmlType());
		fx.Regy().Add
			(	fx.typ_().Key_("size").Subs_
			(		GfmlFld_mok.new_().ini_atr_("w")
			,		GfmlFld_mok.new_().ini_atr_("h")
			).XtoGfmlType());
		fx.Regy().Add
			(	fx.typ_().Key_("rect").Subs_
			(		GfmlFld_mok.new_().ini_ndk_("pos", "pos")
			,		GfmlFld_mok.new_().ini_ndk_("size", "size")
			).XtoGfmlType());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Unnamed() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("1")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("3")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			,	fx.nde_().Typ_("size").Atrk_("w", "3")
			)
		);
	}
	@Test  public void Partial() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_("pos").Atrs_("1")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("3")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			,	fx.nde_().Typ_("size").Atrk_("w", "3")
			)
		);
	}
	@Test  public void OutOfOrder() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_("size").Atrs_("3")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("1")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("size").Atrk_("w", "3")
			,	fx.nde_().Typ_("pos").Atrk_("x", "1")
			)
		);
	}
}
