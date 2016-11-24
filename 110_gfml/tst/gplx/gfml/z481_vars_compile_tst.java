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
public class z481_vars_compile_tst {
	@Before public void setup() {
		regy = fx.Regy();
		pragma = GfmlPragmaVar.new_();
		fx.run_InitPragma(regy, pragma);
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_(); GfmlTypRegy regy; GfmlPragmaVar pragma;
	@Test  public void Text1() {
		GfmlNde gnde = fx.run_Resolve(regy, "_var/text"
			, fx.nde_().Atru_("key_test").Atru_("val_test").Atru_("context_test"));

		GfmlVarItm itm = pragma.CompileItmNde(gnde);
		Tfds.Eq_rev(itm.Key(),			"key_test");
		Tfds.Eq_rev(itm.TknVal(),		"val_test");
		Tfds.Eq_rev(itm.CtxKey(),		"context_test");
	}
}
