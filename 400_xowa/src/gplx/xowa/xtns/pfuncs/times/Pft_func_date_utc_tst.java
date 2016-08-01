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
public class Pft_func_date_utc_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2011, 12, 31, 22, 4, 5, 6));}	// ENV:Assumes Eastern Standard Time (-5)
	@After public void teardown()				{Datetime_now.Manual_n_();}
	@Test   public void Utc_year()					{fxt.Test_parse_tmpl_str_test("{{CURRENTYEAR}}"			, "{{test}}", "2012");}
	@Test   public void Utc_month_int()				{fxt.Test_parse_tmpl_str_test("{{CURRENTMONTH1}}"		, "{{test}}", "1");}
	@Test   public void Utc_month_int_len2()		{fxt.Test_parse_tmpl_str_test("{{CURRENTMONTH}}"		, "{{test}}", "01");}
	@Test   public void Utc_day_int()				{fxt.Test_parse_tmpl_str_test("{{CURRENTDAY}}"			, "{{test}}", "1");}
	@Test   public void Utc_day_int_len2()			{fxt.Test_parse_tmpl_str_test("{{CURRENTDAY2}}"			, "{{test}}", "01");}
	@Test   public void Utc_day_hour_len2()			{fxt.Test_parse_tmpl_str_test("{{CURRENTHOUR}}"			, "{{test}}", "03");}
	@Test   public void Utc_dow_int()				{fxt.Test_parse_tmpl_str_test("{{CURRENTDOW}}"			, "{{test}}", "0");}
	@Test   public void Utc_week_int()				{fxt.Test_parse_tmpl_str_test("{{CURRENTWEEK}}"			, "{{test}}", "1");}
	@Test   public void Utc_month_abrv()			{fxt.Test_parse_tmpl_str_test("{{CURRENTMONTHABBREV}}"	, "{{test}}", "Jan");}
	@Test   public void Utc_month_name()			{fxt.Test_parse_tmpl_str_test("{{CURRENTMONTHNAME}}"	, "{{test}}", "January");}
	@Test   public void Utc_month_gen()				{fxt.Test_parse_tmpl_str_test("{{CURRENTMONTHNAMEGEN}}"	, "{{test}}", "January");}
	@Test   public void Utc_day_name()				{fxt.Test_parse_tmpl_str_test("{{CURRENTDAYNAME}}"		, "{{test}}", "Sunday");}
	@Test   public void Utc_time()					{fxt.Test_parse_tmpl_str_test("{{CURRENTTIME}}"			, "{{test}}", "03:04");}
	@Test   public void Utc_timestamp()				{fxt.Test_parse_tmpl_str_test("{{CURRENTTIMESTAMP}}"	, "{{test}}", "20120101030405");}
}
