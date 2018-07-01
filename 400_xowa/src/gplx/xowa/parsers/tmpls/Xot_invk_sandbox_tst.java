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
public class Xot_invk_sandbox_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		fxt.Reset();
		fxt.Init_defn_clear();
		fxt.Init_defn_add("concat", "{{{1}}}{{{2}}}");
	}
	@Test  public void Basic() {
		fxt.Test_parse_tmpl_str("{{concat|a|b}}", "ab");
	}
	@Test  public void Basic_too_many() {	// c gets ignored
		fxt.Test_parse_tmpl_str("{{concat|a|b|c}}", "ab");
	}
	@Test  public void Basic_too_few() {
		fxt.Test_parse_tmpl_str("{{concat|a}}", "a{{{2}}}");
	}
	@Test  public void Basic_else() {
		fxt.Init_defn_add("concat", "{{{1}}}{{{2|?}}}");
		fxt.Test_parse_tmpl_str("{{concat|a}}", "a?");
	}
	@Test  public void Eq_2() {
		fxt.Init_defn_add("concat", "{{{lkp1}}}");
		fxt.Test_parse_tmpl_str("{{concat|lkp1=a=b}}", "a=b");
	}
	@Test  public void Recurse()		{fxt.Test_parse_tmpl_str_test("<{{concat|{{{1}}}|{{{2}}}}}>"	, "{{test|a|b}}", "<ab>");}
	@Test  public void Recurse_mix()	{fxt.Test_parse_tmpl_str_test("{{concat|.{{{1}}}.|{{{2}}}}}"	, "{{test|a|b}}", ".a.b");}
	@Test  public void Recurse_err()	{fxt.Test_parse_tmpl_str_test("{{concat|{{{1}}}|{{{2}}}}}"	, "{{test1|a|b}}", "[[:Template:test1]]");} // NOTE: make sure test1 does not match test
	@Test  public void KeyNewLine()		{fxt.Test_parse_tmpl_str_test("{{\n  concat|a|b}}"			, "{{\n  test}}", "ab");}
}
