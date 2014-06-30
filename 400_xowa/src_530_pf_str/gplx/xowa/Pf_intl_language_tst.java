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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Pf_intl_language_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void English()				{fxt.Test_parse_tmpl_str_test("{{#language:en}}"						, "{{test}}"	, "English");}
	@Test  public void English_case()			{fxt.Test_parse_tmpl_str_test("{{#language:eN}}"						, "{{test}}"	, "English");}
	@Test  public void Arabic()					{fxt.Test_parse_tmpl_str_test("{{#language:ar}}"						, "{{test}}"	, "العربية");}
	@Test  public void Unknown()				{fxt.Test_parse_tmpl_str_test("{{#language:unknown}}"					, "{{test}}"	, "unknown");}
	@Test  public void Foreign()				{fxt.Test_parse_tmpl_str_test("{{#language:anp}}"						, "{{test}}"	, "अङ्गिका");}
	@Test  public void Foreign_2()				{fxt.Test_parse_tmpl_str_test("{{#language:no}}"						, "{{test}}"	, "‪Norsk (bokmål)‬");}	// PURPOSE: Names.php have bookend "pipes" (\xE2\x80\xAA)
	@Test  public void Empty()					{fxt.Test_parse_tmpl_str_test("{{#language:}}"							, "{{test}}"	, "");}
}
