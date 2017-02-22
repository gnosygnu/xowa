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
public class z442_types_parse_default_tst {
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"
			,	"}"
			,	"1;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			);
	}
	@Test  public void Pinned() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"
			,	"}"
			,	"1;"			// uses point, but should pin
			,	"2;"			// uses pin
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "2")
			);
	}
	@Test  public void Many() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {x;}"	// point is always pinned
			,		"size {w;}"		// size is defined, but can only be invoked by hnd
			,	"}"
			,	"1;"
			,	"2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "2")
			);
	}
	@Test  public void Nested() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"item {"
			,			"point {x; y;}"
			,		"}"
			,	"}"
			,	"{1 2;}"
			)
			,	fx.nde_().Hnd_("item").Typ_("item").Subs_
			(		fx.nde_().Hnd_("point").Typ_("item/point").Atrk_("x", "1").Atrk_("y", "2")
			)
			);
	}
	@Test  public void NestedWithAtr() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"item {"
			,			"name;"
			,			"point {x; y;}"
			,		"}"
			,	"}"
			,	"1 {1 2;}"
//				,	"1 {point:{1 2}}"		// breaks b/c point is not resolved as item/point
			)
			,	fx.nde_().Hnd_("item").Typ_("item").Atrk_("name", "1").Subs_
			(		fx.nde_().Hnd_("point").Typ_("item/point").Atrk_("x", "1").Atrk_("y", "2")
			)
			);
	}
	//@Test 
	public void WithDefault() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x 1;"
			,			"y;"
			,		"}"
			,	"}"
			,	"2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1").Atrk_("y", "2")
			);
	}
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
}
