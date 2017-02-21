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
public class Pfunc_urlfunc_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void Localurl()			{fxt.Test_parse_tmpl_str_test("{{localurl:a&b! c}}"							, "{{test}}", "/wiki/A%26b!_c");}
	@Test   public void Fullurl()			{fxt.Test_parse_tmpl_str_test("{{fullurl:a&b! c}}"							, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurl()		{fxt.Test_parse_tmpl_str_test("{{canonicalurl:a&b! c}}"						, "{{test}}", "https://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurl_case()	{fxt.Test_parse_tmpl_str_test("{{CANONICALURL:a&b! c}}"						, "{{test}}", "https://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Localurle()			{fxt.Test_parse_tmpl_str_test("{{localurle:a&b! c}}"						, "{{test}}", "/wiki/A%26b!_c");}
	@Test   public void Fullurle()			{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c}}"							, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurle()		{fxt.Test_parse_tmpl_str_test("{{canonicalurle:a&b! c}}"					, "{{test}}", "https://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Fullurl_arg()		{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c|action=edit}}"				, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c?action=edit");}
	@Test   public void Random()			{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c|action=edit}}"				, "{{test|a|b|c}}", "//en.wikipedia.org/wiki/A%26b!_c?action=edit");}
	@Test   public void Xwiki()	{
		fxt.Wiki().Xwiki_mgr().Add_by_atrs("commons", "commons.wikimedia.org");
		fxt.Reset().Test_parse_tmpl_str_test("{{localurl:commons:A}}"		, "{{test}}", "//commons.wikimedia.org/wiki/A");
		fxt.Reset().Test_parse_tmpl_str_test("{{fullurl:commons:A}}"		, "{{test}}", "//commons.wikimedia.org/wiki/A");
		fxt.Reset().Test_parse_tmpl_str_test("{{canonicalurl:commons:A}}"	, "{{test}}", "https://commons.wikimedia.org/wiki/A");
	}
	@Test   public void Xwiki_qarg_fix()	{
		fxt.Wiki().Xwiki_mgr().Add_by_atrs("commons", "commons.wikimedia.org");
		fxt.Reset().Test_parse_tmpl_str_test("{{fullurl:commons:A|key=val}}"	, "{{test}}", "//commons.wikimedia.org/wiki/A?key=val");
	}
}
