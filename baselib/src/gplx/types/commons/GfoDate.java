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
import gplx.types.commons.lists.CompareAble;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
public class GfoDate implements CompareAble<GfoDate> {
	GfoDate(Calendar under) {this.under = under;}
	GfoDate(int year, int month, int day, int hour, int minute, int second, int frac) {
		this.under = NewCalendar(year, month, day, hour, minute, second, frac, IntUtl.MinValue, null);
	}
	GfoDate(int year, int month, int day, int hour, int minute, int second, int frac, int tzOffset, byte[] tzAbrv) {
		this.under = NewCalendar(year, month, day, hour, minute, second, frac, tzOffset, tzAbrv);
	}
	private Calendar NewCalendar(int year, int month, int day, int hour, int minute, int second, int frac, int tzOffset, byte[] tzAbrv) {
		GregorianCalendar calendar = new GregorianCalendar(year, month - MonthBase0Adj, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, frac);

		if (tzOffset == IntUtl.MinValue) {
			return calendar;
		}
		else {
			long msFromEpochGmt = calendar.getTime().getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(msFromEpochGmt - tzOffset * 1000);
			return cal;
		}
	}
	public Calendar UnderCalendar() {return under;} private final Calendar under;
	public int Year()       {return under.get(Calendar.YEAR);}
	public int Month()      {return under.get(Calendar.MONTH) + MonthBase0Adj;}
	public int Day()        {return under.get(Calendar.DAY_OF_MONTH);}
	public int Hour()       {return under.get(Calendar.HOUR_OF_DAY);}
	public int Minute()     {return under.get(Calendar.MINUTE);}
	public int Second()     {return under.get(Calendar.SECOND);}
	public int Frac()       {return under.get(Calendar.MILLISECOND);}
	public int DayOfWeek()  {return under.get(Calendar.DAY_OF_WEEK) - WeekBase0Adj;}
	public int DayOfYear()  {return under.get(Calendar.DAY_OF_YEAR);}
	public int WeekOfYear() {return under.get(Calendar.WEEK_OF_YEAR);}
	public String TimezoneId() {
		return "UTC"; // under.getTimeZone().getID(); // NOTE: timezone is always UTC, unless overridden by tests
	}
	public static int TimezoneOffsetTest = IntUtl.MinValue;
	public int TimezoneOffset() {
		return TimezoneOffsetTest == IntUtl.MinValue // Timezone_offset_test not overridden
				? 0
				: TimezoneOffsetTest;
	}
	public long TimestampUnix() {
		long offsetFromUTC = under.getTimeZone().getOffset(0);
		boolean dst = TimeZone.getDefault().inDaylightTime(under.getTime());
		long dstAdj = dst ? 3600000 : 0;
		return (under.getTimeInMillis() + offsetFromUTC + dstAdj) / 1000;
	}
	public int Segment(int idx) {
		switch (idx) {
			case GfoDateUtl.SegIdxYear:       return this.Year();
			case GfoDateUtl.SegIdxMonth:      return this.Month();
			case GfoDateUtl.SegIdxDay:        return this.Day();
			case GfoDateUtl.SegIdxHour:       return this.Hour();
			case GfoDateUtl.SegIdxMinute:     return this.Minute();
			case GfoDateUtl.SegIdxSecond:     return this.Second();
			case GfoDateUtl.SegIdxFrac:       return this.Frac();
			case GfoDateUtl.SegIdxDayOfWeek:  return this.DayOfWeek();
			case GfoDateUtl.SegIdxWeekOfYear: return this.WeekOfYear();
			case GfoDateUtl.SegIdxDayOfYear:  return this.DayOfYear();
			default: throw ErrUtl.NewUnhandled(idx);
		}
	}
	public GfoDate AddFrac(int val)   {return Add(Calendar.MILLISECOND, val);}
	public GfoDate AddSecond(int val) {return Add(Calendar.SECOND, val);}
	public GfoDate AddMinute(int val) {return Add(Calendar.MINUTE, val);}
	public GfoDate AddHour(int val)   {return Add(Calendar.HOUR, val);}
	public GfoDate AddDay(int val)    {return Add(Calendar.DAY_OF_MONTH, val);}
	public GfoDate AddMonth(int val)  {return Add(Calendar.MONTH, val);}
	public GfoDate AddYear(int val)   {return Add(Calendar.YEAR, val);}
	private GfoDate Add(int fld, int val) {
		Calendar clone = (Calendar)under.clone();
		clone.add(fld, val);
		return new GfoDate(clone);
	}
	public int DiffDays(GfoDate prv) {
		long diff = this.under.getTimeInMillis() - prv.under.getTimeInMillis();
		return (int)(diff / (1000 * 60 * 60 * 24));
	}
	public GfoTimeSpan Diff(GfoDate prv) {
		long diff = this.under.getTimeInMillis() - prv.under.getTimeInMillis();
		return GfoTimeSpanUtl.NewFracs(diff);
	}
	public GfoDate ToUtc()   {return ToDate(true);}
	public GfoDate ToLocal() {return ToDate(false);}
	private GfoDate ToDate(boolean isUtc) {
		java.util.Date date = under.getTime();
		java.util.TimeZone tz = under.getTimeZone();
		long msFromEpochGmt = date.getTime();
		int offsetFromUTC = tz.getOffset(msFromEpochGmt);
		Calendar gmtCal = Calendar.getInstance();
		int offset = isUtc ? -offsetFromUTC : offsetFromUTC;
		gmtCal.setTimeInMillis(msFromEpochGmt + offset);
		return new GfoDate(gmtCal);
	}
	public int[] ToSegAry() {
		int[] rv = new int[7];
		rv[GfoDateUtl.SegIdxYear]   = this.Year();
		rv[GfoDateUtl.SegIdxMonth]  = this.Month();
		rv[GfoDateUtl.SegIdxDay]    = this.Day();
		rv[GfoDateUtl.SegIdxHour]   = this.Hour();
		rv[GfoDateUtl.SegIdxMinute] = this.Minute();
		rv[GfoDateUtl.SegIdxSecond] = this.Second();
		rv[GfoDateUtl.SegIdxFrac]   = this.Frac();
		return rv;
	}
	public String ToStrGplx()                    {return ToStrFmt("yyyyMMdd_HHmmss.fff");}
	public String ToStrGplxLong()                {return ToStrFmt("yyyy-MM-dd HH:mm:ss.fff");}
	public String ToStrFmt_HHmmss()              {return ToStrFmt("HH:mm:ss");}
	public String ToStrFmt_HHmm()                {return ToStrFmt("HH:mm");}
	public String ToStrFmt_yyyyMMdd()            {return ToStrFmt("yyyy-MM-dd");}
	public String ToStrFmt_yyyyMMdd_HHmmss()     {return ToStrFmt("yyyyMMdd_HHmmss");}
	public String ToStrFmt_yyyyMMdd_HHmmss_fff() {return ToStrFmt("yyyyMMdd_HHmmss.fff");}
	public String ToStrFmt_yyyy_MM_dd_HH_mm()    {return ToStrFmt("yyyy-MM-dd HH:mm");}
	public String ToStrFmt_yyyy_MM_dd_HH_mm_ss() {return ToStrFmt("yyyy-MM-dd HH:mm:ss");}
	public String ToStrFmtIso8561()              {return ToStrFmt("yyyy-MM-dd HH:mm:ss");}
	public String ToStrFmtIso8561WithTz()        {return ToStrFmt("yyyy-MM-dd'T'HH:mm:ss'Z'");}
	public String ToStrFmt(String fmt) {
		fmt = fmt.replace("f", "S");
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(under.getTime());
	}
	public String ToStrTz() {
		SimpleDateFormat sdf = new SimpleDateFormat("Z");
		String timeZone = sdf.format(under.getTime());
		return StringUtl.Mid(timeZone, 0, 3) + ":" + StringUtl.Mid(timeZone, 3, StringUtl.Len(timeZone));
	}
	public boolean Eq(GfoDate v)  {GfoDate comp = v; return ObjectUtl.Eq(under.getTimeInMillis(), comp.under.getTimeInMillis());}
	@Override public int compareTo(GfoDate comp) {return under.compareTo(comp.under);}
	@Override public String toString()           {return ToStrGplxLong();}
	private static final int
		MonthBase0Adj = 1,
		WeekBase0Adj = 1; // -1 : Base0; NOTE: dotnet/php is also Sunday=0
}
