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
public class z443_types_parse_keyd_tst {
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"[10 12];"
			)
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("10").Atru_("12")
			)
			);
	}
//		@Test 	// FIX: IGNORE
	public void KeydDefault() {
		fx.tst_Parse(String_.Concat
			(	"_type:{"
			,	"	rect {"
			,	"		point [1 2];"
			,	"	}"
			,	"}"
			,	"rect:;"
			,	"rect:;"
			)
			,	fx.nde_().Typ_("rect").Subs_
			(		fx.nde_().Hnd_("point").Atru_("1").Atru_("2")
			)
			,	fx.nde_().Typ_("rect").Subs_
			(		fx.nde_().Hnd_("point").Atru_("1").Atru_("2")
			)
			);
	}
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
}
