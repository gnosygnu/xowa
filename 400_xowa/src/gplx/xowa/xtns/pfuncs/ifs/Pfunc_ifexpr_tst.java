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
public class Pfunc_ifexpr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_y()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 > 0 |y|n}}"					, "{{test}}"	, "y");}
	@Test  public void Basic_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 < 0 |y|n}}"					, "{{test}}"	, "n");}
	@Test  public void Blank_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: |y|n}}"						, "{{test}}"	, "n");}
	@Test  public void Args_0_n()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 1 > 0}}"						, "{{test}}"	, "");}
	@Test  public void Args_0_y()				{fxt.Test_parse_tmpl_str_test("{{#ifexpr: 0 > 1}}"						, "{{test}}"	, "");}
	@Test  public void Err()					{fxt.Test_parse_tmpl_str_test("{{#ifexpr:20abc >1|y|n}}"					, "{{test}}"	, "<strong class=\"error\">Expression error: Unrecognised word \"abc \"</strong>");}	// HACK: shouldn't be "abc " 
}
/*
*/