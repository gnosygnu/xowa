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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Map_deg2dd_func_tst {
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Prec_basic()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 1.2345|2}}"									, "{{test}}"	, "1.23");}
	@Test  public void Prec_round()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 1.2345|3}}"									, "{{test}}"	, "1.235");}
	@Test  public void Example()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 14° 23' 45'' S|precision=3}}"					, "{{test}}"	, "-14.396");}
	@Test  public void Example_N()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 14° 23' 45'' N|precision=3}}"					, "{{test}}"	, "14.396");}
	@Test  public void Apos()				{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 42°39’49’’N   |precision=2}}"					, "{{test}}"	, "42.66");}	// PURPOSE: handle ’’ to "; PAGE:it.v:Morro_d'Oro DATE:2015-12-06
}
