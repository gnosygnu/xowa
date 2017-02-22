/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
