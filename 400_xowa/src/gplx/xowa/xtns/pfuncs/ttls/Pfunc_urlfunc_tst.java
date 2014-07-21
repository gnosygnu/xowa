/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_urlfunc_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void Localurl()			{fxt.Test_parse_tmpl_str_test("{{localurl:a&b! c}}"							, "{{test}}", "/wiki/A%26b!_c");}
	@Test   public void Fullurl()			{fxt.Test_parse_tmpl_str_test("{{fullurl:a&b! c}}"							, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurl()		{fxt.Test_parse_tmpl_str_test("{{canonicalurl:a&b! c}}"						, "{{test}}", "http://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurl_case()	{fxt.Test_parse_tmpl_str_test("{{CANONICALURL:a&b! c}}"						, "{{test}}", "http://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Localurle()			{fxt.Test_parse_tmpl_str_test("{{localurle:a&b! c}}"							, "{{test}}", "/wiki/A%26b!_c");}
	@Test   public void Fullurle()			{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c}}"							, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Canonicalurle()		{fxt.Test_parse_tmpl_str_test("{{canonicalurle:a&b! c}}"						, "{{test}}", "http://en.wikipedia.org/wiki/A%26b!_c");}
	@Test   public void Fullurl_arg()		{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c|action=edit}}"				, "{{test}}", "//en.wikipedia.org/wiki/A%26b!_c?action=edit");}
	@Test   public void Random()			{fxt.Test_parse_tmpl_str_test("{{fullurle:a&b! c|action=edit}}"				, "{{test|a|b|c}}", "//en.wikipedia.org/wiki/A%26b!_c?action=edit");}
	@Test   public void Xwiki()	{
		fxt.Wiki().Xwiki_mgr().Add_full("commons", "commons.wikimedia.org");
		fxt.Reset().Test_parse_tmpl_str_test("{{localurl:commons:A}}"		, "{{test}}", "//commons.wikimedia.org/wiki/A");
		fxt.Reset().Test_parse_tmpl_str_test("{{fullurl:commons:A}}"			, "{{test}}", "//commons.wikimedia.org/wiki/A");
		fxt.Reset().Test_parse_tmpl_str_test("{{canonicalurl:commons:A}}"	, "{{test}}", "http://commons.wikimedia.org/wiki/A");
	}
	@Test   public void Xwiki_qarg_fix()	{
		fxt.Wiki().Xwiki_mgr().Add_full("commons", "commons.wikimedia.org");
		fxt.Reset().Test_parse_tmpl_str_test("{{fullurl:commons:A|key=val}}"	, "{{test}}", "//commons.wikimedia.org/wiki/A?key=val");
	}
}
