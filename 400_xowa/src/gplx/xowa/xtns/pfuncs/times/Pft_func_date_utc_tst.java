/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
