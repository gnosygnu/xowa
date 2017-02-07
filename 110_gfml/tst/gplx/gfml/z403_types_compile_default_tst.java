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
