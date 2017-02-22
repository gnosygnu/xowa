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
public class z111_core_comment0_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.Whitespace_lxr()	// add whitespace to make sure it has no effect
			,	GfmlDocLxrs.Comment0_lxr()		// bgn=// end=\n
			);
	}
	@Test  public void Basic() {
		fx.tst_Doc("//a" + String_.Lf);
		fx.tst_Tkn("//a" + String_.Lf
			, fx.tkn_grp_ary_("//", "a", String_.Lf)
			);
	}
	@Test  public void Data() {
		fx.tst_Doc("a;//b" + String_.Lf, fx.nde_().Atru_("a"));
		fx.tst_Tkn("a;//b" + String_.Lf
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("a")
			,		fx.tkn_itm_(";"))
			,	fx.tkn_grp_ary_("//", "b", String_.Lf)
			);
	}
//		@Test  public void DanglingBgn() {
//			try {
//				fx.tst_Err("//", GfmlOutCmds.Frame_danglingBgn_Err_());
//				Tfds.Fail_expdError();
//			}
//			catch (Exception exc) {Err_.Noop(exc);}
//		}
}
