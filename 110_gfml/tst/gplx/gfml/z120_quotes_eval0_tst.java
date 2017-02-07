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
public class z120_quotes_eval0_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.Eval0_lxr()
			);
	}
	@Test  public void Basic() {
		fx.tst_Doc("<~t>;", fx.nde_().Atru_("\t"));
		fx.tst_Tkn("<~t>;"
			,	fx.tkn_grp_
			(		fx.tkn_grp_
			(			fx.tkn_grp_ary_("<~", "t", ">"))
			,		fx.tkn_itm_(";")
			)
			);
	}
	@Test  public void DoublingIsNotEscaping() {
		fx.tst_Doc("<~t>>>;", fx.nde_().Atru_("\t").Atru_(">>")); // >> does not resolve to >
	}
}
