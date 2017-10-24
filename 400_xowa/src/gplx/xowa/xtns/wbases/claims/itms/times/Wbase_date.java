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
package gplx.xowa.xtns.wbases.claims.itms.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wbase_date {
	public Wbase_date(long year, int month, int day, int hour, int minute, int second, int precision, int before, int after, boolean calendar_is_julian) {
		this.year = year; this.month = month; this.day = day; this.hour = hour; this.minute = minute; this.second = second;
		this.precision = precision; this.before = before; this.after = after; this.calendar_is_julian = calendar_is_julian;
	}
	public long Year() {return year;} private final    long year;
	public int Month() {return month;} private final    int month;
	public int Day() {return day;} private final    int day;
	public int Hour() {return hour;} private final    int hour;
	public int Minute() {return minute;} private final    int minute;
	public int Second() {return second;} private final    int second;
	public int Precision() {return precision;} private final    int precision;
	public int Before() {return before;} private final    int before;
	public int After() {return after;} private final    int after;
	public boolean Calendar_is_julian() {return calendar_is_julian;} private final    boolean calendar_is_julian;
	public static final int
	  Fmt_y				=  9
	, Fmt_ym			= 10
	, Fmt_ymd			= 11
	, Fmt_ymdh			= 12
	, Fmt_ymdhn			= 13
	, Fmt_ymdhns		= 14	// anything >13 ?
	;
}
