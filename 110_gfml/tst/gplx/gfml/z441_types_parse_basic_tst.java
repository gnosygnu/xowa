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
public class z441_types_parse_basic_tst {
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Null() {
		fx.tst_Parse(String_.Concat
			(	"point:1;"
			)
			,	fx.nde_().Hnd_("point").Typ_(GfmlType_.AnyKey).Atru_("1")
			);
	}
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x;"
			,		"}"
			,	"}"
			,	"point:1;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1")
			);
	}
	@Test  public void MultipleAtrs() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,		"point {"
			,			"x; y;"
			,		"}"
			,	"}"
			,	"point:1 2;"
			)
			,	fx.nde_().Hnd_("point").Typ_("point").Atrk_("x", "1").Atrk_("y", "2")
			);
	}
}
