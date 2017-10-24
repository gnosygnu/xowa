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
public class Pxd_parser_tst {
	Bry_bfr bfr = Bry_bfr_.New_w_size(16); Pxd_parser parser = new Pxd_parser();
	@Before public void init() {Datetime_now.Manual_y_(); Datetime_now.Manual_(test_date);} DateAdp test_date = DateAdp_.parse_fmt("2012-02-27", "yyyy-MM-dd");
	@After public void teardown() {Datetime_now.Manual_n_();}
	@Test  public void Month_name_0__day__year()					{tst_date_("Mar 2 2001"				, "2001-03-02");}			// y:Mar-02-2001;Mar.02.2001;Mar 02, 2001 n:Mar/02/2001;Feb,05,2011 
	@Test  public void Month_name_0__day__year__bad_day()			{tst_date_("Mar 32 2001"			, "Invalid day: 32");}
	@Test  public void Month_name_0__day__year__bad_year()			{tst_date_("Mar 3 999"				, "0999-03-03");}
	@Test  public void Month_name_0__day__year__bad_day_year()		{tst_date_("Mar 32 999"				, "Invalid day: 32");}
	@Test  public void Month_name_0__year()							{tst_date_("Mar 2001"				, "2001-03-27");}
	@Test  public void Month_name_0__day()							{tst_date_("Mar 02"					, "2012-03-02");}
	@Test  public void Month_name_0__day_fail()						{tst_date_("Mar 32"					, "Invalid day: 32");}
	@Test  public void Month_name_0()								{tst_date_("Mar"					, "2012-03-27");}	// y: ,.,.. Feb;  n: / Feb, - Feb
	@Test  public void Month_name_1__day__year()					{tst_date_("2 Mar 2001"				, "2001-03-02");}	// y: 3-Feb-2012; 3 Feb 2012;
	@Test  public void Month_name_1__year__day()					{tst_date_("2001 Mar 02"			, "2001-03-02");}	// y: 2012-Feb-3 n:2012.Feb.3;2012 Feb 3; 2012,Feb,3;2012/Feb/3
	@Test  public void Month_name_1__day__year__guess()				{tst_date_("02 Mar 01"				, "2001-03-02");}
	@Test  public void Month_name_1__day()							{tst_date_("02 Mar"					, "2012-03-02");}
	@Test  public void Month_name_1__year()							{tst_date_("2003 Mar"				, "2003-03-27");}
	@Test  public void Month_name_2__day__year()					{tst_date_("2001.02.Mar"			, "2001-03-02");}	// y: 2012.5.Feb; 2012 5 Feb n: 2012-5-Feb; 2012/5/Feb
	@Test  public void Year_0__month__day()							{tst_date_("2001-03-31"				, "2001-03-31");}
	@Test  public void Year_0__day__month__fail()					{tst_date_("2001-31-03"				, "Invalid month: 31");}	// n:2012.31.03; 2012/31/03
	@Test  public void Year_0__month()								{tst_date_("2001-03"				, "2001-03-27");}
	@Test  public void Year_0()										{tst_date_("2001"					, "2001-02-27");}
	@Test  public void Year_1()										{tst_date_("31-2001"				, "Invalid date: 4 digit year must be either yyyy-##-## or ##-##-yyyy");}	// n:31-2012; 31.2012; 31/2012; 03-2012; 03.2012; 03/2012
	@Test  public void Year_2__month__day()							{tst_date_("03/31/2001"				, "2001-03-31");}	// n: 03.31.2012;03-31-2012
	@Test  public void Year_2__day__month()							{tst_date_("31-03-2001"				, "2001-03-31");}	// y: 31.03.2012; n:31/03/2012
	@Test  public void Day_0__month__day()							{tst_date_("31-03-02"				, "2031-03-02");}
	@Test  public void Day_0__month__day_dot()						{tst_date_("22.05.70"				, "1970-05-22");}	// PURPOSE: dmy when delimiter is dot
	@Test  public void Day_1__month__year()							{tst_date_("03-31-02"				, "2002-03-31");}
	@Test  public void Day_2__month__day()							{tst_date_("03-02-31"				, "2031-03-02");}
	@Test  public void Dmy_is_default()								{tst_date_("03-04-05"				, "2005-04-03");}
	@Test  public void Dmy_is_default_y4()							{tst_date_("2-5-2000"				, "2000-05-02");}
	@Test  public void Colon_hour_min()								{tst_time_("01:02"					, "01:02:00.000");}
	@Test  public void Colon_hour_min_second()						{tst_time_("01:02:03"				, "01:02:03.000");}
	@Test  public void Time_all()									{tst_both_("04-03-05 06:07:08"		, "2005-03-04 06:07:08.000");}
	@Test  public void Unit_day_pos()								{tst_date_("+ 3 days"				, "2012-03-01");}
	@Test  public void Unit_day_neg()								{tst_date_("- 3 days"				, "2012-02-24");}
	@Test  public void Unit_day_neg_w_day()							{tst_date_("30 May 2012 -1 days"	, "2012-05-29");}	// PAGE:en.w:Main Page
	@Test  public void Unit_week()									{tst_date_("- 1 week"				, "2012-02-26");}  // PURPOSE.FIX: "week" was not being handled; error on main Page; EX:da.wikipedia.org/Main_Page
	@Test  public void Time_len_6()									{tst_time_("041526"					, "04:15:26.000");}
	@Test  public void Time_len_12()								{tst_both_("201601020304"			, "2016-01-02 03:04:00.000");}	// PURPOSE: handle 12 digit datetime; PAGE:en.w:Boron; DATE:2015-07-29
	@Test  public void Err_one_num()								{tst_time_("2"						, "Invalid year: 2");}	// occurs on some templates; PAGE:en.w:Voyager 1 and {{date}}
	@Test  public void Dmy_at_y_dot()								{tst_date_("1.2.70"					, "1970-02-27");}	// PURPOSE: dmy when delimiter is dot
	@Test  public void Mdy_at_y_slash()								{tst_date_("1/2/70"					, "1970-01-02");}	// PURPOSE: mdy when delimiter is slash
	@Test  public void Ago()										{tst_date_("1 month ago"			, "2012-01-27");}

	private void tst_date_(String raw, String expd) {
		byte[] raw_ary = Bry_.new_u8(raw);
		DateAdp date = parser.Parse(raw_ary, bfr);
		if (date == DateAdp_.MinValue)
			Tfds.Eq(expd, bfr.To_str_and_clear(), raw);
		else
			Tfds.Eq(expd, date.XtoStr_fmt_yyyy_MM_dd());
	}
	private void tst_time_(String raw, String expd) {
		byte[] raw_ary = Bry_.new_u8(raw);
		DateAdp date = parser.Parse(raw_ary, bfr);
		if (date == DateAdp_.MinValue)
			Tfds.Eq(expd, bfr.To_str_and_clear(), raw);
		else
			Tfds.Eq(expd, date.XtoStr_fmt("HH:mm:ss.fff"));
	}
	private void tst_both_(String raw, String expd) {
		byte[] raw_ary = Bry_.new_u8(raw);
		DateAdp date = parser.Parse(raw_ary, bfr);
		if (date == DateAdp_.MinValue)
			Tfds.Eq(expd, bfr.To_str_and_clear(), raw);
		else
			Tfds.Eq(expd, date.XtoStr_fmt("yyyy-MM-dd HH:mm:ss.fff"));
	}
}
/*	
12:12 2012 5 Feb: pass
2012 5 Feb 12:12: fail
2012-Feb-5 12:12: pass (but 2012-02-01)
2012-Feb-05 12:12: pass (and 2012-02-05)
{{#time:Y-m-d H:i:s A|.....2012.-.-.Feb-09....}}
*/
