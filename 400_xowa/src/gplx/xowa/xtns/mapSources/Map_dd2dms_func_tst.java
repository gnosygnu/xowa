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
public class Map_dd2dms_func_tst {
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Example()			{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58|precision=4}}"					, "{{test}}"	, "14° 34' 48&quot;");}
	@Test  public void Plus()				{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58|precision=4|plus=pos}}"			, "{{test}}"	, "14° 34' 48&quot; pos");}
	@Test  public void Ws()					{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58| precision = 4 | plus = pos }}"	, "{{test}}"	, "14° 34' 48&quot; pos");}
	@Test  public void Nested_pfunc()		{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58|{{#if:2|precision=2}}}}"		, "{{test|3}}"	, "14° 34'");}	// handle "{{#if:2|precision=2}}" -> "precision=2"
}
