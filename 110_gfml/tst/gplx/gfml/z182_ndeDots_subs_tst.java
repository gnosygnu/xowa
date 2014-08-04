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
public class z182_ndeDots_subs_tst {
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeDot_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			);
	}	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Test  public void Basic() {
		fx.tst_Doc("{a.b{}z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1)
			)
			,		fx.nde_().Atru_("z").ChainId_(0)
			));
	}
	@Test  public void Nest() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.NdeHeader_lxr());
		fx.tst_Doc("{a.b.c{d:e;}z;}"	// shorthand of {a{b{c{d:e;}}}}
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Subs_
			(				fx.nde_().Hnd_("c").ChainId_(1).Subs_
			(					fx.nde_().Hnd_("d").ChainId_(0).Atru_("e")
			)
			)
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
	@Test  public void Chain() {
		fx.tst_Doc("{a.b.c;z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).Subs_
			(				fx.nde_().Hnd_("c").ChainId_(1))
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
	@Test  public void NdeHdr() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.NdeHeader_lxr());
		fx.tst_Doc("{a:b.c;z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").Atru_("b").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("c").ChainId_(1)//.Subs_
//				(				fx.nde_().Hnd_("c"))
			)
			,		fx.nde_().Atru_("z").ChainId_(0)
			));
	}
}
