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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_iferror_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_pass()				{fxt.Test_parse_tmpl_str_test("{{#iferror: {{#expr: 1 + 2 }} | error | ok }}"					, "{{test}}"	, "ok");}
	@Test  public void Basic_fail()				{fxt.Test_parse_tmpl_str_test("{{#iferror: {{#expr: 1 + X }} | error | ok }}"					, "{{test}}"	, "error");}
	@Test  public void Basic_omit()				{fxt.Test_parse_tmpl_str_test("{{#iferror: ok | error}}"											, "{{test}}"	, "ok");}
	@Test  public void NoMatch_0()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong>error</strong> | error | ok }}"				, "{{test}}"	, "ok");}
	@Test  public void NoMatch_1()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong test=\"error\"></strong> | error | ok }}"		, "{{test}}"	, "ok");}
	@Test  public void NoMatch_2()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong class=\"errora\"></strong> | error | ok }}"	, "{{test}}"	, "ok");}
	//@Test  public void NoMatch_3()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong class=\"error a| error | ok }}"				, "{{test}}"	, "ok");} // FUTURE: match for ">
}
