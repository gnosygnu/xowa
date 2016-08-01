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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pft_func_time__uncommon__tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void init()					{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void term()				{Datetime_now.Manual_n_();}
	@Test   public void Year_5_digits()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|00123-4-5}}"			, "{{test}}"			, "2003-04-05");} // PURPOSE: emulate PHP's incorrect date parsing; EX:ca.w:Nicolau_de_Mira; DATE:2014-04-17
}
