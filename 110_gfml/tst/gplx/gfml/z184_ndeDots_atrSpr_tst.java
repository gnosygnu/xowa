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
	@Test  public void NestMult() {
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
