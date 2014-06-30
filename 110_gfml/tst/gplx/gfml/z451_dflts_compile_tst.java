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
		ListAdp list = ListAdp_.new_();
		pragma.CompileSubNde(gnde, list);
		fx.tst_List(list
			, fx.make_("point", "x", "10")
			, fx.make_("point", "y", "20")
			);
	}
}
