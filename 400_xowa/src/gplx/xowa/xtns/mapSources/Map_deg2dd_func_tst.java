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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Map_deg2dd_func_tst {
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Prec_basic()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 1.2345|2}}"									, "{{test}}"	, "1.23");}
	@Test  public void Prec_round()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 1.2345|3}}"									, "{{test}}"	, "1.235");}
	@Test  public void Example()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 14° 23' 45'' S|precision=3}}"					, "{{test}}"	, "-14.396");}
	@Test  public void Example_N()			{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 14° 23' 45'' N|precision=3}}"					, "{{test}}"	, "14.396");}
	@Test  public void Apos()				{fxt.Test_parse_tmpl_str_test("{{#deg2dd: 42°39’49’’N   |precision=2}}"					, "{{test}}"	, "42.66");}	// PURPOSE: handle ’’ to "; PAGE:it.v:Morro_d'Oro DATE:2015-12-06
}
