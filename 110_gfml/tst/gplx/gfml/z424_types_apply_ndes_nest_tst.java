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
public class z424_types_apply_ndes_nest_tst {
	@Before public void setup() {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		makr.MakeRootType("gfml.item", "item");
		makr.MakeSubType("pos", "x", "y");
		fx.Regy().Add_ary(makr.Xto_bry());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void InvokeByHnd() {
		fx.tst_Resolve
			(	fx.nde_().Hnd_("gfml.item").Subs_
			(		fx.nde_().Hnd_("pos").Atrs_("10", "20"))
			,	fx.nde_().Typ_("gfml.item").Subs_
			(		fx.nde_().Typ_("gfml.item/pos").Atrk_("x", "10").Atrk_("y", "20")
			));
	}
	@Test  public void InvokeByTyp() {
		fx.tst_Resolve
			(	fx.nde_().Hnd_("gfml.item").Subs_
			(		fx.nde_().Hnd_("gfml.item/pos").Atrs_("10", "20"))
			,	fx.nde_().Typ_("gfml.item").Subs_
			(		fx.nde_().Typ_("gfml.item/pos").Atrk_("x", "10").Atrk_("y", "20")
			));
	}
	@Test  public void NoInvokeByName() {
		fx.tst_Resolve
			(	fx.nde_().Hnd_("gfml.item").Subs_
			(		fx.nde_().Hnd_("gfml.item").Atrs_("10", "20"))	// item is not in .SubFlds, but is in regy
			,	fx.nde_().Typ_("gfml.item").Subs_
			(		fx.nde_().Typ_("gfml.item").Atrs_("10", "20")
			));
	}
	@Test  public void Name_subLevel() {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		makr.MakeRootType("font", "size");
		makr.MakeSubType("color", "name");
		fx.Regy().Add_ary(makr.Xto_bry());
		fx.tst_Resolve
			(	fx.nde_().Hnd_("color").Atru_("blue")
			,	fx.nde_().Typ_(GfmlType_.AnyKey).Atru_("blue")// confirm that subFlds in other types are not selectable by name; i.e.: must be font/color, not just color
			);
	}
	@Test  public void Unresolved() {
		fx.tst_Resolve
			(	fx.nde_().Hnd_("").Atrs_("10", "20")
			,	fx.nde_().Typ_(GfmlType_.AnyKey).Atrs_("10", "20")
			);
	}
}
