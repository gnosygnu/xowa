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
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.IntUtl;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
public class GfoDateUtl {
	public static final String ClsRefName = "Date";
	public static final Class<?> ClsRefType = GfoDate.class;
	public static final GfoDate
		MinValue = new GfoDate(   1,  1,  1,  0,  0,  0,   0),
		MaxValue = new GfoDate(9999, 12, 31, 23, 59, 59, 999);
	private static int [] DaysInMonthAry = {31,28,31,30,31,30,31,31,30,31,30,31};
	public static int DaysInMonth(int year, int month) {
		int rv = DaysInMonthAry[month - IntUtl.Base1];
		if (rv == 28 && IsLeapYear(year)) rv = 29;
		return rv;
	}
	public static boolean IsLeapYear(int year) {
		if      (year % 4   != 0) return false;
		else if (year % 400 == 0) return true;
		else if (year % 100 == 0) return false;
		else                      return true;
	}
	public static GfoDate New(int year, int month, int day, int hour, int minute, int second, int frac) {return new GfoDate(year, month, day, hour, minute, second, frac);}
	public static GfoDate NewBySegs(int[] ary) {
		int ary_len = ary.length;
		int y = ary_len > 0 ? ary[0] : 1;
		int M = ary_len > 1 ? ary[1] : 1;
		int d = ary_len > 2 ? ary[2] : 1;
		int h = ary_len > 3 ? ary[3] : 0;
		int m = ary_len > 4 ? ary[4] : 0;
		int s = ary_len > 5 ? ary[5] : 0;
		int f = ary_len > 6 ? ary[6] : 0;
		return new GfoDate(y, M, d, h, m, s, f);
	}
	public static GfoDate NewDateByBits(int y, int m, int d, int h, int i, int s, int us, int tzOffset, byte[] tzAbrv) {
		return new GfoDate(y, m, d, h, i, s, us/1000, tzOffset, tzAbrv);
	}
	public static GfoDate NewByCalendar(GregorianCalendar v) {return new GfoDate(v);}
	public static GfoDate NewUnixtimeUtcSeconds(long v) {return NewUnixtimeUtcMs(v * 1000);}
	public static GfoDate NewUnixtimeUtcMs(long v)      {return NewUnixtimeLclMs(v).ToUtc();}
	public static GfoDate NewUnixtimeLclMs(long v) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(v);
		return new GfoDate(c);
	}
	public static GfoDate NewByDb(Object v) {
		if (v instanceof String) {
			return GfoDateUtl.ParseIso8561((String)v);
		}
		Timestamp ts = (Timestamp)v;
		Calendar gc = Calendar.getInstance();
		gc.setTimeInMillis(ts.getTime());
		return new GfoDate(gc);
	}
	public static GfoDate Parse(String raw) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date d;
		try {d = sdf.parse(raw);}
		catch (ParseException e) {throw ErrUtl.NewArgs("failed to parse to DateAdp", "raw", raw);}
		GregorianCalendar cal = (GregorianCalendar)Calendar.getInstance();
		cal.setTime(d);
		return NewByCalendar(cal);
	}
	public static GfoDate ParseFmtOr(String raw, String fmt, GfoDate or) {
		try {return ParseFmt(raw, fmt);}
		catch (Exception e) {return or;}
	}
	public static GfoDate ParseFmt(String raw, String fmt) {
		fmt = fmt.replace('t', 'a');    // AM/PM
		fmt = fmt.replace('f', 'S');    // milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.US);
		Date d = null;
		try     {d = sdf.parse(raw);}
		catch     (ParseException e) {throw ErrUtl.NewArgs("failed to parse to DateAdp", "raw", raw, "fmt", fmt);}
		GregorianCalendar cal = (GregorianCalendar)Calendar.getInstance();
		cal.setTime(d);
		return NewByCalendar(cal);
	}
	public static GfoDate ParseIso8561Or(String raw, GfoDate or) {
		try {return ParseIso8561(raw);}
		catch (Exception e) {return or;}
	}
	public static GfoDate ParseGplx(String raw) {return ParseIso8561(raw);} // NOTE: for now, same as ParseIso8561
	public static GfoDate ParseIso8561(String raw) {
		GfoDateParser dateParser = new GfoDateParser();
		int[] ary = dateParser.ParseIso8651Like(raw);
		if (ary[1] < 1 || ary[1] > 12) return GfoDateUtl.MinValue; // guard against invalid month
		if (ary[2] < 1 || ary[2] > 31) return GfoDateUtl.MinValue; // guard against invalid day
		return new GfoDate(ary[0], ary[1], ary[2], ary[3], ary[4], ary[5], ary[6]);
	}
	public static final int
		SegIdxYear = 0, SegIdxMonth = 1, SegIdxDay = 2, SegIdxHour = 3, SegIdxMinute = 4, SegIdxSecond = 5,
		SegIdxFrac = 6, SegIdxDayOfWeek = 7, SegIdxWeekOfYear = 8, SegIdxDayOfYear = 9, SegIdxTz = 10, SegIdxMaxLen = 11;
	public static final String
		FmtIso8561DateTime = "yyyy-MM-dd HH:mm:ss",
		Fmt_yyyyMMdd = "yyyyMMdd";
}
