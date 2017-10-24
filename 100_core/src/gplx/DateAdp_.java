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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import gplx.core.times.*;
public class DateAdp_ implements Gfo_invk {
	public static final String Cls_ref_name = "Date";
	public static final    Class<?> Cls_ref_type = DateAdp.class;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Now))		return Datetime_now.Get();
		else									return Gfo_invk_.Rv_unhandled;			
	}	public static final    String Invk_Now = "Now";
	public static final    DateAdp MinValue		= new DateAdp(   1,  1,  1,  0,  0,  0,   0); 
	public static final    DateAdp MaxValue		= new DateAdp(9999, 12, 31, 23, 59, 59, 999); 
//		public static DateAdp Now() {return Tfds.Now_enabled() ? Tfds.Now() : new DateAdp(new GregorianCalendar());}
	public static DateAdp new_(int year, int month, int day, int hour, int minute, int second, int frac) {return new DateAdp(year, month, day, hour, minute, second, frac);}
	public static DateAdp seg_(int[] ary) {
		int ary_len = ary.length;
		int y = ary_len > 0 ? ary[0] : 1;
		int M = ary_len > 1 ? ary[1] : 1;
		int d = ary_len > 2 ? ary[2] : 1;
		int h = ary_len > 3 ? ary[3] : 0;
		int m = ary_len > 4 ? ary[4] : 0;
		int s = ary_len > 5 ? ary[5] : 0;
		int f = ary_len > 6 ? ary[6] : 0;
		return new DateAdp(y, M, d, h, m, s, f);
	}
	public static DateAdp cast(Object arg) {try {return (DateAdp)arg;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, DateAdp.class, arg);}}
	public static DateAdp parse_iso8561_or(String raw, DateAdp or) {
		try {return parse_iso8561(raw);}
		catch (Exception e) {Err_.Noop(e); return or;}
	}
	public static DateAdp parse_iso8561(String raw) {	// NOTE: for now, same as parse_gplx
		int[] ary = date_parser.Parse_iso8651_like(raw);
		if (ary[1] < 1 || ary[1] > 12) return DateAdp_.MinValue;	// guard against invalid month
		if (ary[2] < 1 || ary[2] > 31) return DateAdp_.MinValue;
		return new DateAdp(ary[0], ary[1], ary[2], ary[3], ary[4], ary[5], ary[6]);
	}
	public static DateAdp parse_gplx(String raw)		{
		int[] ary = date_parser.Parse_iso8651_like(raw);
		if (ary[1] < 1 || ary[1] > 12) return DateAdp_.MinValue;	// guard against invalid month
		if (ary[2] < 1 || ary[2] > 31) return DateAdp_.MinValue;
		return new DateAdp(ary[0], ary[1], ary[2], ary[3], ary[4], ary[5], ary[6]);
	}	static DateAdp_parser date_parser = DateAdp_parser.new_();
	public static DateAdp dateTime_(GregorianCalendar v) {return new DateAdp(v);}
	public static DateAdp dateTime_obj_(Object v) {return new DateAdp((GregorianCalendar)v);}
	public static final    DateAdp_ Gfs = new DateAdp_();

	public static int DaysInMonth(DateAdp date) {			
		int rv = DaysInMonth_ary[date.Month() - Int_.Base1];
		if (rv == 28 && IsLeapYear(date.Year())) rv = 29;
		return rv;
	}	static int [] DaysInMonth_ary = {31,28,31,30,31,30,31,31,30,31,30,31};
	public static boolean IsLeapYear(int year) {
		if		(year % 4   != 0)	return false;
		else if (year % 400 == 0)	return true;
		else if (year % 100 == 0)	return false;
		else						return true;
	}
	public static DateAdp unixtime_utc_seconds_(long v) {return unixtime_utc_ms_(v * 1000);}
	public static DateAdp parse_fmt_or(String raw, String fmt, DateAdp or) {
		try {return parse_fmt(raw, fmt);}
		catch (Exception e) {Err_.Noop(e); return or;}
	}
		public static DateAdp db_(Object v) {
		Timestamp ts = (Timestamp)v;		
		Calendar gc = Calendar.getInstance();
		gc.setTimeInMillis(ts.getTime());
		return new DateAdp(gc);
	}
	public static DateAdp parse_(String raw) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date d = null;
		try 	{d = sdf.parse(raw);}
		catch 	(ParseException e) {throw Err_.new_("parse", "failed to parse to DateAdp", "raw", raw);}
		GregorianCalendar cal = (GregorianCalendar)Calendar.getInstance();
		cal.setTime(d);
		return dateTime_(cal);
	}
	public static DateAdp parse_fmt(String raw, String fmt) {
		fmt = fmt.replace('t', 'a');	// AM/PM
		fmt = fmt.replace('f', 'S');	// milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.US);
		Date d = null;
		try 	{d = sdf.parse(raw);}
		catch 	(ParseException e) {throw Err_.new_("parse", "failed to parse to DateAdp", "raw", raw, "fmt", fmt);}
		GregorianCalendar cal = (GregorianCalendar)Calendar.getInstance();
		cal.setTime(d);
		return dateTime_(cal);
	}
	public static DateAdp unixtime_utc_ms_(long v) {return unixtime_lcl_ms_(v).XtoUtc();}
	public static DateAdp unixtime_lcl_ms_(long v) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(v);
		return new DateAdp(c);
	}
		public static final int SegIdx_year = 0, SegIdx_month = 1, SegIdx_day = 2, SegIdx_hour = 3, SegIdx_minute = 4, SegIdx_second = 5, SegIdx_frac = 6, SegIdx_dayOfWeek = 7, SegIdx_weekOfYear = 8, SegIdx_dayOfYear = 9, SegIdx__max = 10;
	public static String Xto_str_fmt_or(DateAdp v, String fmt, String or) {
		return v == null ? or : v.XtoStr_fmt(fmt);
	}
	public static final String 
	  Fmt_iso8561_date_time = "yyyy-MM-dd HH:mm:ss"
	, Fmt__yyyyMMdd			= "yyyyMMdd";
}
