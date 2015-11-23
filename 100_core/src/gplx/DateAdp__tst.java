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
package gplx;
import org.junit.*;
public class DateAdp__tst {
	@Test  public void Parse_gplx() {
		tst_Parse_gplx("99991231_235959.999", "99991231_235959.999");
		tst_Parse_gplx("20090430_213200.123", "20090430_213200.123");
		tst_Parse_gplx("20090430_213200"	, "20090430_213200.000");
		tst_Parse_gplx("20090430"			, "20090430_000000.000");
	}
	@Test  public void Parse_separators() {
		tst_Parse_gplx("2009-04-30 21:32:00.123", "20090430_213200.123");
		tst_Parse_gplx("2009-04-30 21:32:00"	, "20090430_213200.000");
		tst_Parse_gplx("2009-04-30"				, "20090430_000000.000");
	}
	@Test  public void DayOfWeek() {
		tst_DayOfWeek("2012-01-18", 3);	//3=Wed
	}	void tst_DayOfWeek(String raw, int expd) {Tfds.Eq(expd, DateAdp_.parse_gplx(raw).DayOfWeek());}
	@Test  public void WeekOfYear() {
		tst_WeekOfYear("2006-02-01", 5);	// 1-1:Sun;2-1:Wed
		tst_WeekOfYear("2007-02-01", 5);	// 1-1:Mon;2-1:Thu
		tst_WeekOfYear("2008-02-01", 5);	// 1-1:Tue;2-1:Fri
		tst_WeekOfYear("2009-02-01", 6);	// 1-1:Thu;2-1:Sun
		tst_WeekOfYear("2010-02-01", 6);	// 1-1:Fri;2-1:Mon
		tst_WeekOfYear("2011-02-01", 6);	// 1-1:Sat;2-1:Tue
	}	void tst_WeekOfYear(String raw, int expd) {Tfds.Eq(expd, DateAdp_.parse_gplx(raw).WeekOfYear());}
	@Test  public void DayOfYear() {
		tst_DayOfYear("2012-01-01", 1);
		tst_DayOfYear("2012-02-29", 60);
		tst_DayOfYear("2012-12-31", 366);
	}	void tst_DayOfYear(String raw, int expd) {Tfds.Eq(expd, DateAdp_.parse_gplx(raw).DayOfYear());}
	@Test  public void Timestamp_unix() {
		tst_Timestamp_unix("1970-01-01 00:00:00", 0);
		tst_Timestamp_unix("2012-01-01 00:00:00", 1325376000);
	}	void tst_Timestamp_unix(String raw, long expd) {Tfds.Eq(expd, DateAdp_.parse_gplx(raw).Timestamp_unix());}
	@Test  public void DaysInMonth() {
		tst_DaysInMonth("2012-01-01", 31);
		tst_DaysInMonth("2012-02-01", 29);
		tst_DaysInMonth("2012-04-01", 30);
		tst_DaysInMonth("2011-02-01", 28);
	}	void tst_DaysInMonth(String raw, int expd) {Tfds.Eq(expd, DateAdp_.DaysInMonth(DateAdp_.parse_gplx(raw)));}
	@Test  public void XtoUtc() {
		tst_XtoUtc("2012-01-01 00:00", "2012-01-01 05:00");	//4=Wed
	}	void tst_XtoUtc(String raw, String expd) {Tfds.Eq(expd, DateAdp_.parse_gplx(raw).XtoUtc().XtoStr_fmt_yyyy_MM_dd_HH_mm());}

	void tst_Parse_gplx(String raw, String expd) {
		DateAdp date = DateAdp_.parse_gplx(raw);
		String actl = date.XtoStr_gplx();
		Tfds.Eq(expd, actl);
	}
	DateAdp_parser bldr = DateAdp_parser.new_();
}
