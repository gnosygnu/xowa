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
public class Pfunc_ifexpr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_y()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 > 0 |y|n}}"					, "{{test}}"	, "y");}
	@Test  public void Basic_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 < 0 |y|n}}"					, "{{test}}"	, "n");}
	@Test  public void Blank_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: |y|n}}"						, "{{test}}"	, "n");}
	@Test  public void Args_0_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 > 0}}"						, "{{test}}"	, "");}
	@Test  public void Args_0_y()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 0 > 1}}"						, "{{test}}"	, "");}
	@Test  public void Err()					{fxt.Test_parse_tmpl_str_test("{{#ifexpr:20abc >1|y|n}}"					, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"abc \"</strong>");}	// HACK: shouldn't be "abc " 
}
/*
*/