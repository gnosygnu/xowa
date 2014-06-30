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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_ifexist_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_pass()				{fxt.Test_parse_tmpl_str_test("{{#ifexist: Abc | exists | doesn't exist }}"		, "{{test}}"	, "doesn't exist");}
	@Test  public void Empty()					{fxt.Test_parse_tmpl_str_test("{{#ifexist:|y|n}}"								, "{{test}}"	, "n");}	// NOTE: {{autolink}} can pass in ""
	@Test  public void Db_key() {	// PURPOSE: test that (1) & is encoded; (2) " " becomes "_"; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		fxt.Init_page_create("A_&_b", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:A & b|y|n}}", "{{test}}", "y");
	}
}
