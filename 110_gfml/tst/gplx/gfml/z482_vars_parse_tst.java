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
public class z482_vars_parse_tst {
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20';"
			,	"}"
			,	"'<~size>';"
			)
			,	fx.nde_().Atru_("20,20")
			);
	}
	@Test  public void Many() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20';"
			,	"	pos '30,30';"
			,	"}"
			,	"'<~size>' '<~pos>';"
			)
			,	fx.nde_().Atru_("20,20").Atru_("30,30")
			);
	}
	@Test  public void Deferred() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	key0 '<~key1>';"
			,	"}"
			,	"_var:{"
			,	"	key1 val1;"
			,	"}"
			,	"'<~key0>';"
			)
			,	fx.nde_().Atru_("val1")
			);
	}
	@Test  public void Swap() {
		fx.tst_Parse(String_.Concat
			(	"{"
			,	"	_var:{"
			,	"		size '20,20';"
			,	"	}"
			,	"	'<~size>';"
			,	"	_var:{"
			,	"		size '30,30';"
			,	"	}"
			,	"	'<~size>';"
			,	"}"
			)
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("20,20")
			,		fx.nde_().Atru_("30,30")
			)
			);
	}
	@Test  public void Context() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20' gui;"
			,	"}"
			,	"'<~gui.size>' <~size>;"
			)
			,	fx.nde_().Atru_("20,20").Atru_("<~size>")
			);
	}
}
