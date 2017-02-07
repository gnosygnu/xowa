/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
