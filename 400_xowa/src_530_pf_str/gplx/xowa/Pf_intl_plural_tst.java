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
public class Pf_intl_plural_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Singular()				{fxt.Test_parse_tmpl_str_test("{{plural:1|wiki|wikis}}"				, "{{test}}"	, "wiki");}
	@Test  public void Plural()					{fxt.Test_parse_tmpl_str_test("{{plural:2|wiki|wikis}}"				, "{{test}}"	, "wikis");}
	@Test  public void Plural_but_one_arg()		{fxt.Test_parse_tmpl_str_test("{{plural:2|wiki}}"					, "{{test}}"	, "wiki");}
	@Test  public void Null()					{fxt.Test_parse_tmpl_str_test("{{plural:|wiki|wikis}}"				, "{{test}}"	, "wikis");}
}
