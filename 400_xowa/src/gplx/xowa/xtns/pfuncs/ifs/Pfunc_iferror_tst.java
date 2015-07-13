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
public class Pfunc_iferror_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_pass()				{fxt.Test_parse_tmpl_str_test("{{#iferror: {{#expr: 1 + 2 }} | error | ok }}"					, "{{test}}"	, "ok");}
	@Test  public void Basic_fail()				{fxt.Test_parse_tmpl_str_test("{{#iferror: {{#expr: 1 + X }} | error | ok }}"					, "{{test}}"	, "error");}
	@Test  public void Basic_omit()				{fxt.Test_parse_tmpl_str_test("{{#iferror: ok | error}}"											, "{{test}}"	, "ok");}
	@Test  public void NoMatch_0()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong>error</strong> | error | ok }}"				, "{{test}}"	, "ok");}
	@Test  public void NoMatch_1()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong test=\"error\"></strong> | error | ok }}"		, "{{test}}"	, "ok");}
	@Test  public void NoMatch_2()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong class=\"errora\"></strong> | error | ok }}"	, "{{test}}"	, "ok");}
	//@Test  public void NoMatch_3()				{fxt.Test_parse_tmpl_str_test("{{#iferror: <strong class=\"error a| error | ok }}"				, "{{test}}"	, "ok");} // FUTURE: match for ">
}
