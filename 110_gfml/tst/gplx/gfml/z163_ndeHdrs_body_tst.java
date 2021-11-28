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
public class z163_ndeHdrs_body_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeHeader_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			);
	}
	@Test public void Basic() {
		fx.tst_Doc("a:{}", fx.nde_().Hnd_("a"));
	}	
	@Test public void Many() {
		fx.tst_Doc("a:{}b:{}"
			,	fx.nde_().Hnd_("a")
			,	fx.nde_().Hnd_("b")
			);
	}	
	@Test public void Nested() {
		fx.tst_Doc("a:{b:{}}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().Hnd_("b"))
			);
	}	
	@Test public void NestedMany() {
		fx.tst_Doc("a:{b:{}c:{}}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().Hnd_("b")
			,		fx.nde_().Hnd_("c")
			)
			);
	}	
}
