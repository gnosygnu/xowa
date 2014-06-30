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
public class z184_ndeDots_atrSpr_tst {
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeDot_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			,	GfmlDocLxrs.NdeHdrBgn_lxr()
			,	GfmlDocLxrs.NdeHdrEnd_lxr()
			,	GfmlDocLxrs.AtrSpr_lxr()
			);
	}	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Test public void NestMult() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.AtrSpr_lxr());
		fx.tst_Doc("{a.b(c.d,e.f);z;}"
			,	fx.nde_().ChainId_(0).Subs_
			(		fx.nde_().Hnd_("a").ChainId_(1).Subs_
			(			fx.nde_().Hnd_("b").ChainId_(1).KeyedSubObj_(false).Subs_
			(				fx.nde_().Hnd_("c").ChainId_(2).KeyedSubObj_(true).Subs_
			(					fx.nde_().Hnd_("d").ChainId_(2).KeyedSubObj_(false))
			,				fx.nde_().Hnd_("e").ChainId_(3).KeyedSubObj_(true).Subs_
			(					fx.nde_().Hnd_("f").ChainId_(3).KeyedSubObj_(false))				
			)
			)
			,		fx.nde_().ChainId_(0).Atru_("z")
			));
	}
}
