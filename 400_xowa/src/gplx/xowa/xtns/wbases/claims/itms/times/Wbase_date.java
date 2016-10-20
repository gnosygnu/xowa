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
