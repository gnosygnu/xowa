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
