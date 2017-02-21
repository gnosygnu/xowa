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
package gplx;
import org.junit.*; import gplx.core.tests.*;
public class DateAdp__tst {		
	private final    DateAdp__fxt fxt = new DateAdp__fxt();
	@Test  public void Parse_gplx() {
		fxt.Test__parse_gplx("99991231_235959.999"	, "99991231_235959.999");
		fxt.Test__parse_gplx("20090430_213200.123"	, "20090430_213200.123");
		fxt.Test__parse_gplx("20090430_213200"		, "20090430_213200.000");
		fxt.Test__parse_gplx("20090430"				, "20090430_000000.000");
	}
	@Test  public void Parse_separators() {
		fxt.Test__parse_gplx("2009-04-30 21:32:00.123"	, "20090430_213200.123");
		fxt.Test__parse_gplx("2009-04-30 21:32:00"		, "20090430_213200.000");
		fxt.Test__parse_gplx("2009-04-30"				, "20090430_000000.000");
	}
	@Test  public void Parse_utc() {
		fxt.Test__parse_gplx("2015-12-26T10:03:53Z"		, "20151226_100353.000");
	}
	@Test  public void DayOfWeek() {
		fxt.Test__day_of_week("2012-01-18", 3);	//3=Wed
	}
	@Test  public void WeekOfYear() {
		fxt.Test__week_of_year("2006-02-01", 5);	// 1-1:Sun;2-1:Wed
		fxt.Test__week_of_year("2007-02-01", 5);	// 1-1:Mon;2-1:Thu
		fxt.Test__week_of_year("2008-02-01", 5);	// 1-1:Tue;2-1:Fri
		fxt.Test__week_of_year("2009-02-01", 6);	// 1-1:Thu;2-1:Sun
		fxt.Test__week_of_year("2010-02-01", 6);	// 1-1:Fri;2-1:Mon
		fxt.Test__week_of_year("2011-02-01", 6);	// 1-1:Sat;2-1:Tue
	}
	@Test  public void DayOfYear() {
		fxt.Test__day_of_year("2012-01-01", 1);
		fxt.Test__day_of_year("2012-02-29", 60);
		fxt.Test__day_of_year("2012-12-31", 366);
	}
	@Test  public void Timestamp_unix() {
		fxt.Test__timestamp_unix("1970-01-01 00:00:00", 0);
		fxt.Test__timestamp_unix("2012-01-01 00:00:00", 1325376000);
	}
	@Test  public void DaysInMonth() {
		fxt.Test__days_in_month("2012-01-01", 31);
		fxt.Test__days_in_month("2012-02-01", 29);
		fxt.Test__days_in_month("2012-04-01", 30);
		fxt.Test__days_in_month("2011-02-01", 28);
	}
	@Test  public void XtoUtc() {
		fxt.Test__to_utc("2012-01-01 00:00", "2012-01-01 05:00");	//4=Wed
	}
}
class DateAdp__fxt {
	public void Test__parse_gplx(String raw, String expd) {
		Gftest.Eq__str(expd, DateAdp_.parse_gplx(raw).XtoStr_gplx());
	}
	public void Test__day_of_week(String raw, int expd) {
		Gftest.Eq__int(expd, DateAdp_.parse_gplx(raw).DayOfWeek());
	}
	public void Test__week_of_year(String raw, int expd) {
		Gftest.Eq__int(expd, DateAdp_.parse_gplx(raw).WeekOfYear());
	}
	public void Test__day_of_year(String raw, int expd) {
		Gftest.Eq__int(expd, DateAdp_.parse_gplx(raw).DayOfYear());
	}
	public void Test__days_in_month(String raw, int expd) {
		Gftest.Eq__int(expd, DateAdp_.DaysInMonth(DateAdp_.parse_gplx(raw)));
	}
	public void Test__timestamp_unix(String raw, long expd) {
		Gftest.Eq__long(expd, DateAdp_.parse_gplx(raw).Timestamp_unix());
	}
	public void Test__to_utc(String raw, String expd) {
		Tfds.Eq(expd, DateAdp_.parse_gplx(raw).XtoUtc().XtoStr_fmt_yyyy_MM_dd_HH_mm());
	}
}
