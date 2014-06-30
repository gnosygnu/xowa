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
public class z151_ndeSubs_basic_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			);
	}
	@Test public void Basic() {
		fx.tst_Doc("{}", fx.nde_());
		fx.tst_Tkn("{}"
			,	fx.tkn_grp_ary_("{", "}")
			);
	}	
	@Test public void Many() {
		fx.tst_Doc("{}{}", fx.nde_(), fx.nde_());
	}
	@Test public void Nested() {
		fx.tst_Doc("{{}}"
			, fx.nde_().Subs_
			(		fx.nde_())
			);
	}
	@Test public void NestedMany() {
		fx.tst_Doc("{{}{}}"
			, fx.nde_().Subs_
			(		fx.nde_()
			,		fx.nde_()
			));
	}
	@Test public void Complex() {
		fx.tst_Doc(String_.Concat
			(	"{"
			,		"{"
			,			"{}"
			,		"}"
			,		"{}"
			,	"}"
			)
			,	fx.nde_().Subs_
			(		fx.nde_().Subs_
			(			fx.nde_())
			,		fx.nde_()
			));
	}
}
