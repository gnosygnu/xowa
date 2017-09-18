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
public class z191_ndeProps_basic_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdePropBgn_lxr()
			,	GfmlDocLxrs.NdePropEnd_lxr()
			,	GfmlDocLxrs.NdeBodyBgn_lxr()
			,	GfmlDocLxrs.NdeBodyEnd_lxr()
			,	GfmlDocLxrs.NdeHeader_lxr()
			,	GfmlDocLxrs.NdeInline_lxr()
			);
	}
	@Test  public void Basic() {
		fx.tst_Doc("a:[b]{}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b"))
			);
	}
	@Test  public void Basic_empty() {
		fx.tst_Tkn("[];"
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("[", "]")
			,		fx.tkn_itm_(";")
			));
	}	
	@Test  public void Hdr() {
		fx.tst_Tkn("a[];"
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("a")
			,		fx.tkn_grp_ary_("[", "]")
			,		fx.tkn_itm_(";")
			));
	}	
	@Test  public void WithInnerNde() {
		fx.tst_Doc("a:[b]{c;}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b")
			,		fx.nde_().Atru_("c")
			));
	}	
	@Test  public void DoesNotUsurpDatTknForName() {
		fx.tst_Doc("a:b[c]{}"
			,	fx.nde_().Hnd_("a").Atru_("b").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("c")
			));
	}	
	@Test  public void NoHeader_body() {
		fx.tst_Doc("a[b]{}"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b")
			));
	}
	@Test  public void NoHeader_inline() {
		fx.tst_Doc("a[b];"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b")
			));
	}	
	@Test  public void Nesting() {
		fx.tst_Doc("a:[b:;]{}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Subs_
			(			fx.nde_().Hnd_("b"))
			));
	}	
	@Test  public void Nesting2() {
		fx.tst_Doc("a:[b{}]{}"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b")
			));
	}	
	@Test  public void CanBeKeyed_header() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.ElmKey_lxr());
		fx.tst_Doc("a:b=[c];"
			,	fx.nde_().Hnd_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Key_("b").Atru_("c")
			));
	}	
	@Test  public void CanBeKeyed2_inline() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.Whitespace_lxr()
			,	GfmlDocLxrs.ElmKey_lxr()
			);
		fx.tst_Doc("a b=[c];"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Key_("b").Atru_("c")
			));
	}	
	@Test  public void Sole() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.Whitespace_lxr());
		fx.tst_Doc("[a b]"
			,	fx.nde_().KeyedSubObj_().Atru_("a").Atru_("b"));
	}	
	@Test  public void Nest1() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.Whitespace_lxr());
		fx.tst_Doc("[a [b]]"
			,	fx.nde_().Atru_("a").Subs_
			(		fx.nde_().KeyedSubObj_().Atru_("b")						
			));
	}	
}
