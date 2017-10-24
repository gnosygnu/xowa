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
public class z501_lxr_parse_tst {
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void SymCreate() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_sym:key='gfml.elm_key_1' raw='<-' cmd='gfml.elm_key';"
			,	"a<-1;"
			)
			,	fx.nde_().Atrk_("a", "1")
			);
	}
	@Test  public void SymUpdate() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_sym:key='gfml.elm_key_0' raw='<-';"
			,	"a<-1 b=1;"
			)
			,	fx.nde_().Atrk_("a", "1").Atru_("b=1")
			);
	}
	@Test  public void SwapCreate() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_sym:key='gfml.swap_0' raw='/?/?' val='/?';"
			,	"/?/?;"
			)
			,	fx.nde_().Atru_("/?")
			);
	}
	@Test  public void FrameCreate() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_frame:key='gfml.comment_2' type='comment' bgn='/-' end='-/';"
			,	"a=/-ignore-/b;"
			)
			,	fx.nde_().Atrk_("a", "b")
			);
	}
	@Test  public void FrameUpdate() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_frame:key='gfml.comment_0' bgn='--' end='!';"
			,	"a=--ignore!"
			,	"b;"
			)
			,	fx.nde_().Atrk_("a", "b")
			);
	}
	//@Test 
	public void FrameCreateNest() {
		fx.tst_Parse(String_.Concat
			(	"_lxr_frame:key='gfml.comment_2' type='comment' bgn='/-' end='-/' {"
			,		"sym:key='gfml.comment_2_escape_bgn' raw='/-/-' val='/-' cmd='gfml.elm_data';"
			,		"sym:key='gfml.comment_2_escape_end' raw='-/-/' val='-/' cmd='gfml.elm_data';"
			,	"}"
			,	"a=/-/-/-ignore-/b;"
			)
			,	fx.nde_().Atrk_("a", "b")
			);
		// todo:
		//		cmd should be waitingTkns add, not data (makes invisible
		//		should resolve type on sym to lxr_sym (since _lxr_sym is not invoked) or create _lxr_frame/sym type
		//		how to change inner lxrs (lookup by key?)
	}

//		@Test  public void FrameUpdateEval() {
//			raw = String_.Concat
//				(	"_lxr_frame:key='gfml.eval_0' bgn='~<' end='>';"	// how to handle '<~' where <~ (block quote)
//				,	"a=~[t];"
//				);
//			gdoc = GfmlDoc_.parse_any_eol_(raw);
//			fx_nde.tst_SubKeys(gdoc, 0, atr_("a", "\t"));
//		}
}
