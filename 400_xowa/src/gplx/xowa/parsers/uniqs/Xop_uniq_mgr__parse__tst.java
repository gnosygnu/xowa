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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_uniq_mgr__parse__tst {
	private final    Xop_fxt fxt = Xop_fxt.New_app_html();
	@Before public void init() {fxt.Reset();}
	@Test  public void Ref_becomes_UNIQ() {
		String wikitext = "<ref>b</ref>";
		fxt.Init_defn_add("test", "{{#ifeq:{{{1}}}|a" + wikitext + "c|fail|pass}}"); // fail if {{{1}}} is still wikitext; should be UNIQ
		fxt.Test__parse__tmpl_to_html("{{test|a" + wikitext + "c}}", "pass");
	}
}
