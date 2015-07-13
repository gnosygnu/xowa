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
public class z441_types_parse_basic_tst {
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Null() {
		fx.tst_Parse(String_.Concat
			(	"point:1;"
			)
			,	fx.nde_().Hnd_("point").Typ_(GfmlType_.AnyKey).Atru_("1")
			);
	}
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x;"
			,		"}"
			,	"}"
			,	"point:1;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			);
	}
	@Test  public void MultipleAtrs() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x; y;"
			,		"}"
			,	"}"
			,	"point:1 2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1").Atrk_("y", "2")
			);
	}
}
