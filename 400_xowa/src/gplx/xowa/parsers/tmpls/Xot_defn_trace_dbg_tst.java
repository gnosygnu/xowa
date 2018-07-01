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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_defn_trace_dbg_tst {
	Xot_defn_trace_fxt fx = new Xot_defn_trace_fxt();
	@Before public void init() {
		fx.Init_defn_clear();
		fx.Init_defn_add("print", "{{{1}}}");
		fx.Init_defn_add("concat", "{{{1}}}{{{2}}}");
		fx.Init_defn_add("bool_str", "{{#ifeq:{{{1}}}|1|y|n}}");
		fx.Init_defn_add("mid_1", "{{print|[ {{concat|{{{1}}}|{{{2}}}}} ]}}");
		fx.Ctx().Defn_trace_(Xot_defn_trace_dbg.Instance);
	}
	@Test  public void Tmpl() {
		fx.tst_
			( "{{print|a|key1=b}}"
			, "*source"
			, "{{print|a|key1=b}}"
			, "  *invk"
			, "  {{print|a|key1=b}}"
			, "  *lnk: [[Template:print]]"
			, "  *args"
			, "       1: a"
			, "       2: b"
			, "    key1: b"
			, "  *eval"
			, "  a"
			, "*result"
			, "a"
			);
	}
	//@Test  public void Basic_b()		{fx.tst_("{{mwo_b|2}}"						, "[[Test page]]", "00 11 {{mwo_b|1}} -> b");}
}
