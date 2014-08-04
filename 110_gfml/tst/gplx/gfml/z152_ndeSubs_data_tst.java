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
public class z152_ndeSubs_data_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.Whitespace_lxr()
			);
	}
	@Test  public void ToInline() {
		fx.tst_Doc("{a;}"
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("a"))
			);
	}
	@Test  public void ToInline_many() {
		fx.tst_Doc("{a b;}"
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("a").Atru_("b"))
			);
	}
	@Test  public void ToBody() {
		fx.tst_Doc("{a{}}"
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("a"))
			);
	}	
	@Test  public void ToBody_many() {
		fx.tst_Doc("{a b{}}"
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("a").Atru_("b"))
			);
	}	
	@Test  public void ToBody_manyNest() {
		fx.tst_Doc("a{b;}"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().Atru_("b"))				
			);
	}	
	@Test  public void ToBody_many2() {
		fx.tst_Doc("a{b{c;}}"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().Atru_("b").Subs_
			(			fx.nde_().Atru_("c"))
			)
			);
	}	
}
