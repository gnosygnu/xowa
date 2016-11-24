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
public class z443_types_parse_keyd_tst {
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"[10 12];"
			)
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("10").Atru_("12")
			)
			);
	}
//		@Test 	// FIX: IGNORE
	public void KeydDefault() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,	"	rect {"
			,	"		point [1 2];"
			,	"	}"
			,	"}"
			,	"rect:;"
			,	"rect:;"
			)
			,	fx.nde_().Typ_("rect").Subs_
			(		fx.nde_().Hnd_("point").Atru_("1").Atru_("2")
			)
			,	fx.nde_().Typ_("rect").Subs_
			(		fx.nde_().Hnd_("point").Atru_("1").Atru_("2")
			)
			);
	}
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
}
