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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_wiki_stats_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset();}
	@Test  public void SiteName()					{fxt.Test_parse_tmpl_str_test("{{SITENAME}}"			, "{{test}}", "Wikipedia");}
	@Test  public void ServerName()					{fxt.Test_parse_tmpl_str_test("{{SERVERNAME}}"			, "{{test}}", "en.wikipedia.org");}
	@Test  public void Server()						{fxt.Test_parse_tmpl_str_test("{{SERVER}}"				, "{{test}}", "https://en.wikipedia.org");}
	@Test  public void ArticlePath()				{fxt.Test_parse_tmpl_str_test("{{ARTICLEPATH}}"			, "{{test}}", "/wiki/");}	// FUTURE: should be /wiki/$1
	@Test  public void ScriptPath()					{fxt.Test_parse_tmpl_str_test("{{SCRIPTPATH}}"			, "{{test}}", "/wiki");}
	@Test  public void StylePath()					{fxt.Test_parse_tmpl_str_test("{{STYLEPATH}}"			, "{{test}}", "/wiki/skins");}
	@Test  public void ContentLanguage()			{fxt.Test_parse_tmpl_str_test("{{CONTENTLANG}}"			, "{{test}}", "en");}
	@Test  public void DirectionMark()				{fxt.Test_parse_tmpl_str_test("{{DIRECTIONMARK}}"		, "{{test}}", "");}
	@Test  public void CurrentVersion()				{fxt.Test_parse_tmpl_str_test("{{CURRENTVERSION}}"		, "{{test}}", "1.21wmf11");}
}
