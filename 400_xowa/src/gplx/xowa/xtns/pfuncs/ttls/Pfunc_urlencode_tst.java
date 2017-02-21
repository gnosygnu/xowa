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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_urlencode_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void Numbers()			{fxt.Test_parse_tmpl_str_test("{{urlencode:0123456789}}"						, "{{test}}", "0123456789");}
	@Test   public void Ltrs_lower()		{fxt.Test_parse_tmpl_str_test("{{urlencode:abcdefghijklmnopqrstuvwxyz}}"		, "{{test}}", "abcdefghijklmnopqrstuvwxyz");}
	@Test   public void Ltrs_upper()		{fxt.Test_parse_tmpl_str_test("{{urlencode:ABCDEFGHIJKLMNOPQRSTUVWXYZ}}"		, "{{test}}", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");}
	@Test   public void Syms_allowed()		{fxt.Test_parse_tmpl_str_test("{{urlencode:-_.}}"								, "{{test}}", "-_.");}
	@Test   public void Space()				{fxt.Test_parse_tmpl_str_test("{{urlencode:a b}}"								, "{{test}}", "a+b");}
	@Test   public void Syms()				{fxt.Test_parse_tmpl_str_test("{{urlencode:!?^~:}}"								, "{{test}}", "%21%3F%5E%7E%3A");}
	@Test   public void Extended()			{fxt.Test_parse_tmpl_str_test("{{urlencode:aéb}}"								, "{{test}}", "a%C3%A9b");}
}
