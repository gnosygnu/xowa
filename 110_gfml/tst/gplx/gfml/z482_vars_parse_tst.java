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
public class z482_vars_parse_tst {
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Basic() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20';"
			,	"}"
			,	"'<~size>';"
			)
			,	fx.nde_().Atru_("20,20")
			);
	}
	@Test  public void Many() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20';"
			,	"	pos '30,30';"
			,	"}"
			,	"'<~size>' '<~pos>';"
			)
			,	fx.nde_().Atru_("20,20").Atru_("30,30")
			);
	}
	@Test  public void Deferred() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	key0 '<~key1>';"
			,	"}"
			,	"_var:{"
			,	"	key1 val1;"
			,	"}"
			,	"'<~key0>';"
			)
			,	fx.nde_().Atru_("val1")
			);
	}
	@Test  public void Swap() {
		fx.tst_Parse(String_.Concat
			(	"{"
			,	"	_var:{"
			,	"		size '20,20';"
			,	"	}"
			,	"	'<~size>';"
			,	"	_var:{"
			,	"		size '30,30';"
			,	"	}"
			,	"	'<~size>';"
			,	"}"
			)
			,	fx.nde_().Subs_
			(		fx.nde_().Atru_("20,20")
			,		fx.nde_().Atru_("30,30")
			)
			);
	}
	@Test  public void Context() {
		fx.tst_Parse(String_.Concat
			(	"_var:{"
			,	"	size '20,20' gui;"
			,	"}"
			,	"'<~gui.size>' <~size>;"
			)
			,	fx.nde_().Atru_("20,20").Atru_("<~size>")
			);
	}
}
