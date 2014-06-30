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
public class z183_ndeDots_parens_tst {
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeDot_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			,	GfmlDocLxrs.NdeHdrBgn_lxr()
			,	GfmlDocLxrs.NdeHdrEnd_lxr()
			);
	}	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Test public void Basic() {
		fx.tst_Doc("{a.b(c);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Atru_("c")
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
	@Test public void Basic_tkn() {
		//A_('1');
		fx.tst_Tkn("a(c);"
			,	fx.tkn_grp_
			(	fx.tkn_itm_("a"), fx.tkn_itm_("(")
			,		fx.tkn_grp_(fx.tkn_itm_("c"))
			,	fx.tkn_itm_(")"), fx.tkn_itm_(";")
			)
			);
	}
	@Test public void Basic2_tkn() {
		fx.tst_Tkn("a.b(c);"
			,	fx.tkn_grp_
			(	fx.tkn_itm_("a"), fx.tkn_itm_(".")
			,		fx.tkn_grp_
			(			fx.tkn_itm_("b"), fx.tkn_itm_("("), fx.tkn_grp_ary_("c"), fx.tkn_itm_(")"), fx.tkn_itm_(";")
			)
			)
			);
	}
	@Test public void Many() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.Whitespace_lxr());
		fx.tst_Doc("{a.b(c d e);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Atru_("c").Atru_("d").Atru_("e")
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
//		@Test public void Many2() {
//			fx.ini_RootLxr_Add(GfmlDocLxrs.Whitespace_lxr());
//			fx.tst_Doc("{a.b(c){d();}}"
//				,	fx.nde_().ChainId_(0).Subs_
//				(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
//				(			fx.nde_().Hnd_("b").ChainId_(1).Atru_("c").Subs_
//				(				fx.nde_().Hnd_("d")
//				)
//				)
//				));
//		}
	@Test public void Chain() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.Whitespace_lxr());
		fx.tst_Doc("{a.b(c).d(e);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Atru_("c").Subs_
			(				fx.nde_().Hnd_("d").ChainId_(1).Atru_("e")
			)
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
	@Test public void Nest() {
		fx.tst_Doc("{a.b(c.d);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).KeyedSubObj_(false).Subs_
			(				fx.nde_().Hnd_("c").ChainId_(2).KeyedSubObj_(true).Subs_
			(					fx.nde_().Hnd_("d").ChainId_(2).KeyedSubObj_(false)
			)
			)
			)
			,		fx.nde_().Atru_("z")
			));
	}
	@Test public void Nest_longer() {
		fx.tst_Doc("{a.b.c(d.e.f);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").KeyedSubObj_(false).ChainId_(1).Subs_
			(				fx.nde_().Hnd_("c").KeyedSubObj_(false).ChainId_(1).Subs_
			(					fx.nde_().Hnd_("d").KeyedSubObj_(true).ChainId_(2).Subs_
			(						fx.nde_().Hnd_("e").KeyedSubObj_(false).ChainId_(2).Subs_
			(							fx.nde_().Hnd_("f").KeyedSubObj_(false).ChainId_(2)
			)
			)
			)
			)
			)
			,		fx.nde_().Atru_("z")
			));
	}
	@Test public void Nest_deeper() {
		fx.tst_Doc("{a.b(c.d(e.f));z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").KeyedSubObj_(false).ChainId_(1).Subs_
			(				fx.nde_().Hnd_("c").KeyedSubObj_(true).ChainId_(2).Subs_
			(					fx.nde_().Hnd_("d").KeyedSubObj_(false).ChainId_(2).Subs_
			(						fx.nde_().Hnd_("e").KeyedSubObj_(true).ChainId_(3).Subs_
			(							fx.nde_().Hnd_("f").KeyedSubObj_(false).ChainId_(3)
			)
			)
			)
			)
			)
			,		fx.nde_().Atru_("z")
			));
	}
}
