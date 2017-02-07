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
public class z423_types_apply_ndes_misc_tst {
	@Before public void setup() {
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Recurse() {
		fx.Regy().Add(
			fx.typ_().Key_("item").Subs_
			(	GfmlFld_mok.new_().ini_atr_("key")
			,	GfmlFld_mok.new_().ini_ndk_("item", "item")
			).XtoGfmlType());
		fx.tst_Resolve
			(	fx.nde_().Hnd_("item").Atru_("1").Subs_
			(		fx.nde_().Hnd_("item").Atru_("2"))
			,	fx.nde_().Hnd_("item").Atrk_("key", "1").Subs_
			(		fx.nde_().Hnd_("item").Atrk_("key", "2")				
			));
	}
	@Test  public void OwnerTypePrecedesTopLevel() {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		GfmlType topLevelSize = makr.MakeSubType("size", "width", "height");
		GfmlType rect = makr.MakeSubTypeAsOwner("rect");
		GfmlType rectSize = makr.MakeSubType("size", "w", "h");
		fx.Regy().Add(topLevelSize).Add(rect).Add(rectSize);
		fx.tst_Resolve
			(	fx.nde_().Hnd_("rect").Subs_
			(		fx.nde_().Hnd_("size").Atru_("1").Atru_("2"))
			,	fx.nde_().Hnd_("rect").Subs_
			(		fx.nde_().Typ_("rect/size").Atrk_("w", "1").Atrk_("h", "2")
			));
	}
}
