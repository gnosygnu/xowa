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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
public class DateAdp implements CompareAble, Gfo_invk {
	public int compareTo(Object obj)		{DateAdp comp = (DateAdp)obj; return under.compareTo(comp.under);}
	@Override public String toString()		{return XtoStr_gplx_long();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m)  {
		if		(ctx.Match(k, Invk_XtoStr_fmt))		return XtoStr_fmt("yyyy-MM-dd HH:mm:ss");
		else if (ctx.Match(k, Invk_AddDays)) {
			int days = m.ReadInt("days");
			if (ctx.Deny()) return this;
			return this.Add_day(days);
		}
		else										return Gfo_invk_.Rv_unhandled;			
	}	public static final    String Invk_XtoStr_fmt = "XtoStr_fmt", Invk_AddDays = "Add_day";
	public int Segment(int segmentIdx) {
		switch (segmentIdx) {
			case DateAdp_.SegIdx_year:			return this.Year();
			case DateAdp_.SegIdx_month:			return this.Month();
			case DateAdp_.SegIdx_day:			return this.Day();
			case DateAdp_.SegIdx_hour:			return this.Hour();
			case DateAdp_.SegIdx_minute:		return this.Minute();
			case DateAdp_.SegIdx_second:		return this.Second();
			case DateAdp_.SegIdx_frac:			return this.Frac();
			case DateAdp_.SegIdx_dayOfWeek:		return this.DayOfWeek();
			case DateAdp_.SegIdx_weekOfYear:	return this.WeekOfYear();
			case DateAdp_.SegIdx_dayOfYear:		return this.DayOfYear();
			default: throw Err_.new_unhandled(segmentIdx);
		}
	}
	public int[] XtoSegAry() {
		int[] rv = new int[7];
		rv[DateAdp_.SegIdx_year]	= this.Year();
		rv[DateAdp_.SegIdx_month]	= this.Month();
		rv[DateAdp_.SegIdx_day]		= this.Day();
		rv[DateAdp_.SegIdx_hour]	= this.Hour();
		rv[DateAdp_.SegIdx_minute]	= this.Minute();
		rv[DateAdp_.SegIdx_second]	= this.Second();
		rv[DateAdp_.SegIdx_frac]	= this.Frac();
		return rv;
	}
	public String XtoStr_gplx()						{return XtoStr_fmt("yyyyMMdd_HHmmss.fff");}
	public String XtoStr_gplx_long()				{return XtoStr_fmt("yyyy-MM-dd HH:mm:ss.fff");}
	public String XtoStr_fmt_HHmmss()				{return XtoStr_fmt("HH:mm:ss");}
	public String XtoStr_fmt_HHmm()					{return XtoStr_fmt("HH:mm");}
	public String XtoStr_fmt_yyyy_MM_dd()			{return XtoStr_fmt("yyyy-MM-dd");}
	public String XtoStr_fmt_yyyyMMdd_HHmmss()		{return XtoStr_fmt("yyyyMMdd_HHmmss");}
	public String XtoStr_fmt_yyyyMMdd_HHmmss_fff()	{return XtoStr_fmt("yyyyMMdd_HHmmss.fff");}
	public String XtoStr_fmt_yyyyMMdd_HHmm()		{return XtoStr_fmt("yyyyMMdd_HHmm");}
	public String XtoStr_fmt_yyyy_MM_dd_HH_mm()		{return XtoStr_fmt("yyyy-MM-dd HH:mm");}
	public String XtoStr_fmt_yyyy_MM_dd_HH_mm_ss()	{return XtoStr_fmt("yyyy-MM-dd HH:mm:ss");}
	public String XtoStr_fmt_iso_8561()				{return XtoStr_fmt("yyyy-MM-dd HH:mm:ss");}
	public String XtoStr_fmt_iso_8561_w_tz()		{return XtoStr_fmt("yyyy-MM-dd'T'HH:mm:ss'Z'");}
	public static int Timezone_offset_test = Int_.Min_value;
		public Calendar UnderDateTime() 		{return under;} Calendar under;
	public int Year() {return under.get(Calendar.YEAR);}
	public int Month() {return under.get(Calendar.MONTH) + Month_base0adj;}
	public int Day() {return under.get(Calendar.DAY_OF_MONTH);}
	public int Hour() {return under.get(Calendar.HOUR_OF_DAY);}
	public int Minute() {return under.get(Calendar.MINUTE);}
	public int Second() {return under.get(Calendar.SECOND);}
	public int DayOfWeek() {return under.get(Calendar.DAY_OF_WEEK) - 1;}	// -1 : Base0; NOTE: dotnet/php is also Sunday=0
	public int DayOfYear() {return under.get(Calendar.DAY_OF_YEAR);}
	public int Timezone_offset() {
		return Timezone_offset_test == Int_.Min_value							// Timezone_offset_test not over-ridden
				? 0
		//		? under.getTimeZone().getOffset(this.Timestamp_unix()) / 1000	// divide by 1000 to convert from ms to seconds
				: Timezone_offset_test
				;
	}
	public DateAdp XtoUtc() {
		java.util.Date date = under.getTime();
		java.util.TimeZone tz = under.getTimeZone();
		long msFromEpochGmt = date.getTime();
		int offsetFromUTC = tz.getOffset(msFromEpochGmt);
		Calendar gmtCal = Calendar.getInstance();
		gmtCal.setTimeInMillis(msFromEpochGmt + -offsetFromUTC);
		return new DateAdp(gmtCal);
	}
	public DateAdp XtoLocal() {
		java.util.Date date = under.getTime();
		java.util.TimeZone tz = under.getTimeZone();
		long msFromEpochGmt = date.getTime();
		int offsetFromUTC = tz.getOffset(msFromEpochGmt);
		Calendar gmtCal = Calendar.getInstance();
		gmtCal.setTimeInMillis(msFromEpochGmt + offsetFromUTC);
		return new DateAdp(gmtCal);
	}
	public long Timestamp_unix() {
		long offsetFromUTC = (under.getTimeZone().getOffset(0));
		boolean dst = TimeZone.getDefault().inDaylightTime(under.getTime());
		long dst_adj = dst ? 3600000 : 0;
		return (under.getTimeInMillis() + offsetFromUTC + dst_adj) / 1000;
	}
	public int WeekOfYear() {return under.get(Calendar.WEEK_OF_YEAR);}
	public int Frac() {return under.get(Calendar.MILLISECOND);}
	public DateAdp Add_frac(int val) {return CloneAndAdd(Calendar.MILLISECOND, val);}
	public DateAdp Add_second(int val) {return CloneAndAdd(Calendar.SECOND, val);}
	public DateAdp Add_minute(int val) {return CloneAndAdd(Calendar.MINUTE, val);}
	public DateAdp Add_hour(int val) {return CloneAndAdd(Calendar.HOUR, val);}
	public DateAdp Add_day(int val) {return CloneAndAdd(Calendar.DAY_OF_MONTH, val);}
	public DateAdp Add_month(int val) {return CloneAndAdd(Calendar.MONTH, val);}
	public DateAdp Add_year(int val) {return CloneAndAdd(Calendar.YEAR, val);}
	DateAdp CloneAndAdd(int field, int val) {
		Calendar clone = (Calendar)under.clone();
		clone.add(field, val);
		return new DateAdp(clone);
	}
	public String XtoStr_fmt(String fmt)	{
		fmt = fmt.replace("f", "S");
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(under.getTime());
	}
	public String XtoStr_tz()	{
        SimpleDateFormat sdf = new SimpleDateFormat("Z");
		String time_zone = sdf.format(under.getTime());
		return String_.Mid(time_zone, 0, 3) + ":" + String_.Mid(time_zone, 3, String_.Len(time_zone));
	}
	public boolean Eq(DateAdp v)			{DateAdp comp = v; return Object_.Eq(under.getTimeInMillis(), comp.under.getTimeInMillis());}
	public int Diff_days(DateAdp prev)		{
		long diff = this.under.getTimeInMillis() - prev.under.getTimeInMillis();
		return (int)(diff / (1000 * 60 * 60 * 24));
	}
	public Time_span Diff(DateAdp earlier) {
		long diff = this.under.getTimeInMillis() - earlier.under.getTimeInMillis();
		return Time_span_.fracs_(diff);
	}
	protected DateAdp(Calendar under) {this.under = under;}
	protected DateAdp(int year, int month, int day, int hour, int minute, int second, int frac) {
		this.under = new GregorianCalendar(year, month - Month_base0adj, day, hour, minute, second);
		under.set(Calendar.MILLISECOND, frac);
	}
	public static final int Month_base0adj = 1;
	}
