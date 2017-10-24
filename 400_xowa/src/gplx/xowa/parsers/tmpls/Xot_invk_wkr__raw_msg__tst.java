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
public class Xot_invk_wkr__raw_msg__tst {
	@Before		public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@After	public void term() {fxt.Init_defn_clear();}
	@Test  public void Raw() { // PURPOSE: {{raw:A}} is same as {{A}}; EX.WIKT:android; {{raw:ja/script}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Test 1", "{{#if:|y|{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{raw:Test 1|a}}", "a");
		fxt.Init_defn_clear();
	}
	@Test  public void Raw_spanish() { // PURPOSE.fix: {{raw}} should not fail; EX:es.s:Carta_a_Silvia; DATE:2014-02-11
		fxt.Test_parse_tmpl_str("{{raw}}", "[[:Template:raw]]");	// used to fail; now tries to get Template:Raw which doesn't exist
	}
	@Test  public void Special() { // PURPOSE: {{Special:Whatlinkshere}} is same as [[:Special:Whatlinkshere]]; EX.WIKT:android; isValidPageName
		fxt.Test_parse_page_tmpl_str("{{Special:Whatlinkshere}}", "[[:Special:Whatlinkshere]]");
	}
	@Test  public void Special_arg() { // PURPOSE: make sure Special still works with {{{1}}}
		fxt.Init_defn_clear();
		fxt.Init_defn_add("Test1", "{{Special:Whatlinkshere/{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{Test1|-1}}", "[[:Special:Whatlinkshere/-1]]");
		fxt.Init_defn_clear();
	}
	@Test  public void Raw_special() { // PURPOSE: {{raw:A}} is same as {{A}}; EX.WIKT:android; {{raw:ja/script}}
		fxt.Test_parse_tmpl_str("{{raw:Special:Whatlinkshere}}", "[[:Special:Whatlinkshere]]");
		fxt.Init_defn_clear();
	}
	@Test  public void Msg() {
		fxt.Init_defn_add("CURRENTMONTH", "a");
		fxt.Test_parse_tmpl_str("{{msg:CURRENTMONTH}}", "a");
	}
}
