/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoDateUtlTest {
	private final GfoDateUtlTstr tstr = new GfoDateUtlTstr();
	@Test public void ParseGplx() {
		tstr.TestParseGplx("99991231_235959.999"    , "99991231_235959.999");
		tstr.TestParseGplx("20090430_213200.123"    , "20090430_213200.123");
		tstr.TestParseGplx("20090430_213200"        , "20090430_213200.000");
		tstr.TestParseGplx("20090430"               , "20090430_000000.000");
	}
	@Test public void ParseSeparators() {
		tstr.TestParseGplx("2009-04-30 21:32:00.123", "20090430_213200.123");
		tstr.TestParseGplx("2009-04-30 21:32:00"    , "20090430_213200.000");
		tstr.TestParseGplx("2009-04-30"             , "20090430_000000.000");
	}
	@Test public void ParseUtc() {
		tstr.TestParseGplx("2015-12-26T10:03:53Z"        , "20151226_100353.000");
	}
	@Test public void DayOfWeek() {
		tstr.TestDayOfWeek("2012-01-18", 3);    //3=Wed
	}
	@Test public void WeekOfYear() {
		tstr.TestWeekOfYear("2006-02-01", 5);    // 1-1:Sun;2-1:Wed
		tstr.TestWeekOfYear("2007-02-01", 5);    // 1-1:Mon;2-1:Thu
		tstr.TestWeekOfYear("2008-02-01", 5);    // 1-1:Tue;2-1:Fri
		tstr.TestWeekOfYear("2009-02-01", 6);    // 1-1:Thu;2-1:Sun
		tstr.TestWeekOfYear("2010-02-01", 6);    // 1-1:Fri;2-1:Mon
		tstr.TestWeekOfYear("2011-02-01", 6);    // 1-1:Sat;2-1:Tue
	}
	@Test public void DayOfYear() {
		tstr.TestDayOfYear("2012-01-01", 1);
		tstr.TestDayOfYear("2012-02-29", 60);
		tstr.TestDayOfYear("2012-12-31", 366);
	}
	@Test public void Timestamp_unix() {
		tstr.TestTimestampUnix("1970-01-01 00:00:00", 0);
		tstr.TestTimestampUnix("2012-01-01 00:00:00", 1325376000);
	}
	@Test public void DaysInMonth() {
		tstr.TestDaysInMonth("2012-01-01", 31);
		tstr.TestDaysInMonth("2012-02-01", 29);
		tstr.TestDaysInMonth("2012-04-01", 30);
		tstr.TestDaysInMonth("2011-02-01", 28);
	}
	@Test public void ToUtc() {
		tstr.TestToUtc("2012-01-01 00:00", "2012-01-01 05:00");    //4=Wed
	}
	@Test public void TimezoneId() {
		tstr.TestTimezoneId("2015-12-26T10:03:53Z", "UTC");
	}
}
class GfoDateUtlTstr {
	public void TestParseGplx(String raw, String expd)  {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).ToStrGplx());}
	public void TestDayOfWeek(String raw, int expd)     {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).DayOfWeek());}
	public void TestWeekOfYear(String raw, int expd)    {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).WeekOfYear());}
	public void TestDayOfYear(String raw, int expd)     {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).DayOfYear());}
	public void TestDaysInMonth(String raw, int expd)   {
		GfoDate date = GfoDateUtl.ParseGplx(raw);
		GfoTstr.Eq(expd, GfoDateUtl.DaysInMonth(date.Year(), date.Month()));
	}
	public void TestTimestampUnix(String raw, long expd) {GfoTstr.EqLong(expd, GfoDateUtl.ParseGplx(raw).TimestampUnix());}
	public void TestToUtc(String raw, String expd)       {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).ToUtc().ToStrFmt_yyyy_MM_dd_HH_mm());}
	public void TestTimezoneId(String raw, String expd)  {GfoTstr.Eq(expd, GfoDateUtl.ParseGplx(raw).ToUtc().TimezoneId());}
}
