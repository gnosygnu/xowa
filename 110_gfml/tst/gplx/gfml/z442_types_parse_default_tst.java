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
public class z442_types_parse_default_tst {
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"
			,	"}"
			,	"1;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			);
	}
	@Test  public void Pinned() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"
			,	"}"
			,	"1;"			// uses point, but should pin
			,	"2;"			// uses pin
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "2")
			);
	}
	@Test  public void Many() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"	// point is always pinned
			,		"size {w;}"		// size is defined, but can only be invoked by hnd
			,	"}"
			,	"1;"
			,	"2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "2")
			);
	}
	@Test  public void Nested() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"item {"
			,			"point {x; y;}"
			,		"}"
			,	"}"
			,	"{1 2;}"
			)
			,	fx.nde_().Hnd_("item").Typ_("item").Subs_
			(		fx.nde_().Hnd_("point").Typ_("item/point").Atrk_("x", "1").Atrk_("y", "2")
			)
			);
	}
	@Test  public void NestedWithAtr() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"item {"
			,			"name;"
			,			"point {x; y;}"
			,		"}"
			,	"}"
			,	"1 {1 2;}"
//				,	"1 {point:{1 2}}"		// breaks b/c point is not resolved as item/point
			)
			,	fx.nde_().Hnd_("item").Typ_("item").Atrk_("name", "1").Subs_
			(		fx.nde_().Hnd_("point").Typ_("item/point").Atrk_("x", "1").Atrk_("y", "2")
			)
			);
	}
	//@Test 
	public void WithDefault() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x 1;"
			,			"y;"
			,		"}"
			,	"}"
			,	"2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1").Atrk_("y", "2")
			);
	}
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
}
