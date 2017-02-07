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
public class z124_quotes_quoteFold_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.QuoteFold_lxr()
			);
	}
	@Test  public void Quote() {
		fx.tst_Doc("^'a b'^;", fx.nde_().Atru_("a b"));
	}
	@Test  public void Tab() {
		fx.tst_Doc("^'a\tb'^;", fx.nde_().Atru_("ab"));
		fx.tst_Tkn("^'a\tb'^;"
			,	fx.tkn_grp_
			(		fx.tkn_grp_
			(			fx.tkn_grp_ary_("^'", "a", "\t", "b", "'^"))
			,		fx.tkn_itm_(";"))
			);
	}
	@Test  public void NewLine() {
		fx.tst_Doc(String_.Concat("^'a", String_.CrLf, "b'^;"), fx.nde_().Atru_("ab"));
	}
	@Test  public void Eval() {
		fx.tst_Doc("^'a<~t>b'^;", fx.nde_().Atru_("a\tb"));
	}
	@Test  public void Nest() {
		fx.tst_Doc("^'a^'-'^b'^;", fx.nde_().Atru_("a-b"));
	}
	@Test  public void EscapeBgn() {
		fx.tst_Doc("^'a^'^'b'^;", fx.nde_().Atru_("a^'b"));
	}
	@Test  public void EscapeEnd() {
		fx.tst_Doc("^'a'^'^b'^;", fx.nde_().Atru_("a'^b"));
	}
	@Test  public void Comment0() {
		fx.tst_Doc(String_.Concat("^'a//comment", String_.CrLf, "b'^;"), fx.nde_().Atru_("ab"));
	}
	@Test  public void Comment1() {
		fx.tst_Doc("^'a/*comment*/b'^;", fx.nde_().Atru_("ab"));
	}
}
