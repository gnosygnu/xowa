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
public class z411_types_apply_atrs_basic_tst {
	@Before public void setup() {
		pointType = fx.typ_().Key_("point").Atrs_("x", "y", "z");	
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_(); GfmlTyp_mok pointType;
	@Test  public void Unnamed_one() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atru_("10")
		,	fx.nde_().Typ_("point").Atrk_("x", "10")
		);
	}
	@Test  public void Unnamed_three() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atrs_("10", "20", "30")
		,	fx.nde_().Typ_("point").Atrk_("x", "10").Atrk_("y", "20").Atrk_("z", "30")
		);
	}
	@Test  public void Keys_partial1() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atrk_("x", "10").Atrs_("20", "30")
		,	fx.nde_().Typ_("point").Atrk_("x", "10").Atrk_("y", "20").Atrk_("z", "30")
		);
	}
	@Test  public void Keys_partial2() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atrk_("x", "10").Atrk_("y", "20").Atru_("30")
		,	fx.nde_().Typ_("point").Atrk_("x", "10").Atrk_("y", "20").Atrk_("z", "30")
		);
	}
	@Test  public void OutOfOrder_z_1() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atrk_("z", "30").Atrs_("10", "20")
		,	fx.nde_().Typ_("point").Atrk_("z", "30").Atrk_("x", "10").Atrk_("y", "20")
		);
	}
	@Test  public void OutOfOrder_z_2() {
		fx.tst_Resolve
		(	pointType
		,	fx.nde_().Hnd_("point").Atru_("10").Atrk_("z", "30").Atru_("20")
		,	fx.nde_().Typ_("point").Atrk_("x", "10").Atrk_("z", "30").Atrk_("y", "20")
		);
	}
}
