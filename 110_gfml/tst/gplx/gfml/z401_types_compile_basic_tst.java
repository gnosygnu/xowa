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
