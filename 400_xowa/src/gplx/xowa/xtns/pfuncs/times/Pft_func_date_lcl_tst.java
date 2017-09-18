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
public class Pft_func_date_lcl_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void teardown()				{Datetime_now.Manual_n_();}
	@Test   public void Lcl_year()					{fxt.Test_parse_tmpl_str_test("{{LOCALYEAR}}"			, "{{test}}", "2012");}
	@Test   public void Lcl_month_int()				{fxt.Test_parse_tmpl_str_test("{{LOCALMONTH1}}"			, "{{test}}", "1");}
	@Test   public void Lcl_month_int_len2()		{fxt.Test_parse_tmpl_str_test("{{LOCALMONTH}}"			, "{{test}}", "01");}
	@Test   public void Lcl_day_int()				{fxt.Test_parse_tmpl_str_test("{{LOCALDAY}}"			, "{{test}}", "2");}
	@Test   public void Lcl_day_int_len2()			{fxt.Test_parse_tmpl_str_test("{{LOCALDAY2}}"			, "{{test}}", "02");}
	@Test   public void Lcl_day_hour_len2()			{fxt.Test_parse_tmpl_str_test("{{LOCALHOUR}}"			, "{{test}}", "03");}
	@Test   public void Lcl_dow_int()				{fxt.Test_parse_tmpl_str_test("{{LOCALDOW}}"			, "{{test}}", "1");}
	@Test   public void Lcl_week_int()				{fxt.Test_parse_tmpl_str_test("{{LOCALWEEK}}"			, "{{test}}", "1");}
	@Test   public void Lcl_month_name()			{fxt.Test_parse_tmpl_str_test("{{LOCALMONTHNAME}}"		, "{{test}}", "January");}
	@Test   public void Lcl_month_gen()				{fxt.Test_parse_tmpl_str_test("{{LOCALMONTHNAMEGEN}}"	, "{{test}}", "January");}
	@Test   public void Lcl_day_name()				{fxt.Test_parse_tmpl_str_test("{{LOCALDAYNAME}}"		, "{{test}}", "Monday");}
	@Test   public void Lcl_time()					{fxt.Test_parse_tmpl_str_test("{{LOCALTIME}}"			, "{{test}}", "03:04");}
	@Test   public void Lcl_timestamp()				{fxt.Test_parse_tmpl_str_test("{{LOCALTIMESTAMP}}"		, "{{test}}", "20120102030405");}
}
