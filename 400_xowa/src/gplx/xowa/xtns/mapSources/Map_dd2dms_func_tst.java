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
public class Map_dd2dms_func_tst {
	@Before public void init()				{fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Example()			{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58|precision=4}}"					, "{{test}}"	, "14° 34' 48&quot;");}
	@Test  public void Plus()				{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58|precision=4|plus=pos}}"			, "{{test}}"	, "14° 34' 48&quot; pos");}
	@Test  public void Ws()					{fxt.Test_parse_tmpl_str_test("{{#dd2dms: 14.58| precision = 4 | plus = pos }}"	, "{{test}}"	, "14° 34' 48&quot; pos");}
}
