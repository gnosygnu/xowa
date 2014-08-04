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
public class z181_ndeDots_basic_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeDot_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			);
	}
	@Test  public void One() {
		fx.tst_Doc("{a.b;c;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1)
			)
			,		fx.nde_().Atru_("c").ChainId_(0)
			));
		fx.tst_Tkn("{a.b;c;}"
			,	fx.tkn_grp_
			(		fx.tkn_itm_("{")
			,		fx.tkn_grp_
			(			fx.tkn_itm_("a")
			,			fx.tkn_itm_(".")
			,			fx.tkn_grp_
			(				fx.tkn_itm_("b")
			,				fx.tkn_itm_(";"))
			)
			,		fx.tkn_grp_
			(			fx.tkn_grp_ary_("c")
			,			fx.tkn_itm_(";"))
			,		fx.tkn_itm_("}")
			));
	}
	@Test  public void Many() {
		fx.tst_Doc("{a.b.c.d;e;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Subs_
			(				fx.nde_().Hnd_("c").ChainId_(1).Subs_
			(					fx.nde_().Hnd_("d").ChainId_(1)
			)))
			,		fx.nde_().ChainId_(0).Atru_("e")
			));
	}
}
