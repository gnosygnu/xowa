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
package gplx.gfml;
import gplx.frameworks.tests.GfoTstr;
import org.junit.*;
public class z481_vars_compile_tst {
	@Before public void setup() {
		regy = fx.Regy();
		pragma = GfmlPragmaVar.new_();
		fx.run_InitPragma(regy, pragma);
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_(); GfmlTypRegy regy; GfmlPragmaVar pragma;
	@Test public void Text1() {
		GfmlNde gnde = fx.run_Resolve(regy, "_var/text"
			, fx.nde_().Atru_("key_test").Atru_("val_test").Atru_("context_test"));

		GfmlVarItm itm = pragma.CompileItmNde(gnde);
		GfoTstr.EqObj("key_test", itm.Key());
		GfoTstr.EqObj("val_test", itm.TknVal());
		GfoTstr.EqObj("context_test", itm.CtxKey());
	}
}
