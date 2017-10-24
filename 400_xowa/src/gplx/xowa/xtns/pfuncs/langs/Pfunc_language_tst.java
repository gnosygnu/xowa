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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_language_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void English()				{fxt.Test_parse_tmpl_str_test("{{#language:en}}"						, "{{test}}"	, "English");}
	@Test  public void English_case()			{fxt.Test_parse_tmpl_str_test("{{#language:eN}}"						, "{{test}}"	, "English");}
	@Test  public void Arabic()					{fxt.Test_parse_tmpl_str_test("{{#language:ar}}"						, "{{test}}"	, "العربية");}
	@Test  public void Unknown()				{fxt.Test_parse_tmpl_str_test("{{#language:unknown}}"					, "{{test}}"	, "unknown");}
	@Test  public void Foreign()				{fxt.Test_parse_tmpl_str_test("{{#language:anp}}"						, "{{test}}"	, "अङ्गिका");}
	@Test  public void Foreign_2()				{fxt.Test_parse_tmpl_str_test("{{#language:no}}"						, "{{test}}"	, "‪Norsk (bokmål)‬");}	// PURPOSE: Names.php have bookend "pipes" (\xE2\x80\xAA)
	@Test  public void Empty()					{fxt.Test_parse_tmpl_str_test("{{#language:}}"							, "{{test}}"	, "");}
}
