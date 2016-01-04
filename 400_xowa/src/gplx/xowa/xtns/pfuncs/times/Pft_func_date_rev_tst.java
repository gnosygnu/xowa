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
public class Pft_func_date_rev_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); fxt.Page().Revision_data().Modified_on_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void teardown()				{}
	@Test   public void Rev_year()					{fxt.Test_parse_tmpl_str_test("{{REVISIONYEAR}}"		, "{{test}}", "2012");}
	@Test   public void Rev_month_int()				{fxt.Test_parse_tmpl_str_test("{{REVISIONMONTH1}}"		, "{{test}}", "1");}
	@Test   public void Rev_month_int_len2()		{fxt.Test_parse_tmpl_str_test("{{REVISIONMONTH}}"		, "{{test}}", "01");}
	@Test   public void Rev_day_int()				{fxt.Test_parse_tmpl_str_test("{{REVISIONDAY}}"			, "{{test}}", "2");}
	@Test   public void Rev_day_int_len2()			{fxt.Test_parse_tmpl_str_test("{{REVISIONDAY2}}"		, "{{test}}", "02");}
	@Test   public void Rev_timestamp()				{fxt.Test_parse_tmpl_str_test("{{REVISIONTIMESTAMP}}"	, "{{test}}", "20120102030405");}
}
