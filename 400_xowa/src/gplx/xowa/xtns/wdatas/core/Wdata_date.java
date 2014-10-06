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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Wdata_date {
	public Wdata_date(long year, int month, int day, int hour, int minute, int second) {this.year = year; this.month = month; this.day = day; this.hour = hour; this.minute = minute; this.second = second;}
	public long Year() {return year;} private final long year;
	public int Month() {return month;} private final int month;
	public int Day() {return day;} private final int day;
	public int Hour() {return hour;} private final int hour;
	public int Minute() {return minute;} private final int minute;
	public int Second() {return second;} private final int second;
	public static Wdata_date parse(byte[] bry) {// EX:+00000002001-02-03T04:05:06Z
		int year_sign = 1;
		switch (bry[0]) {
			case Byte_ascii.Plus:	break;
			case Byte_ascii.Dash:	year_sign = -1; break;
			default:				throw Err_.unhandled(bry[0]);
		}
		int year_end = Bry_finder.Find_fwd(bry, Byte_ascii.Dash, 1);
		long year	= Long_.parse_or_(String_.new_ascii_(bry, 1, year_end), -1); if (year == -1) throw Err_.new_("parse failed; raw={0}", String_.new_ascii_(bry));
		int month	= Bry_.Xto_int_or(bry, year_end +  1, year_end +  3, -1);
		int day		= Bry_.Xto_int_or(bry, year_end +  4, year_end +  6, -1);
		int hour	= Bry_.Xto_int_or(bry, year_end +  7, year_end +  9, -1);
		int minute	= Bry_.Xto_int_or(bry, year_end + 10, year_end + 12, -1);
		int second	= Bry_.Xto_int_or(bry, year_end + 13, year_end + 15, -1);
		return new Wdata_date(year * year_sign, month, day, hour, minute, second);
	}
	public static Wdata_date Xto_julian(Wdata_date date) {
		int a = (int)Math_.Floor((14 - date.Month() / 12));
		int y = (int)date.Year() + 4800 - a;
		int m = date.Month() + 12 * a - 3;
		int julian = date.Day() + (int)Math_.Floor((153 * m + 2) / 5) + 365 * y + (int)Math_.Floor(y / 4) - (int)Math_.Floor(y / 100) + (int)Math_.Floor(y / 400) - 32045;
		int c = julian + 32082;
		int d = (int)Math_.Floor((4 * c + 3) / 1461);
		int e = c - (int)Math_.Floor((1461 * d) / 4);
		int n = (int)Math_.Floor((5 * e + 2) / 153);
		int new_y = d - 4800 + (int)Math_.Floor(n / 10);
		int new_m = n + 3 - 12 * (int)Math_.Floor(n / 10);
		int new_d = e - (int)Math_.Floor((153 * n + 2) / 5) + 1;
		return new Wdata_date(new_y, new_m, new_d, date.Hour(), date.Minute(), date.Second());
	}
	public static void Xto_str(Bry_bfr bfr, Wdata_date date, int precision, byte[][] months, int months_bgn, byte[] dt_spr, byte[] time_spr) {
		long year = date.Year();
		switch (precision) {
			case Wdata_date.Fmt_ym:					// EX: "Feb 2001"
				bfr.Add(months[months_bgn + date.Month() - ListAdp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(year);
				break;
			case Wdata_date.Fmt_ymd: 				// EX: "3 Feb 2001"
				bfr.Add_int_variable(date.Day());
				bfr.Add_byte_space();
				bfr.Add(months[months_bgn + date.Month() - ListAdp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(date.Year());
				break;
			case Wdata_date.Fmt_ymdh:				// EX: "4:00 3 Feb 2011"
				bfr.Add_int_variable(date.Hour());
				bfr.Add(time_spr);
				bfr.Add_int_fixed(0, 2);
				bfr.Add_byte_space();
				bfr.Add_int_variable(date.Day());
				bfr.Add_byte_space();
				bfr.Add(months[months_bgn + date.Month() - ListAdp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(date.Year());
				break;
			case Wdata_date.Fmt_ymdhn:				// EX: "4:05 3 Feb 2011"
				bfr.Add_int_variable(date.Hour());
				bfr.Add(time_spr);
				bfr.Add_int_fixed(date.Minute(), 2);
				bfr.Add_byte_space();
				bfr.Add_int_variable(date.Day());
				bfr.Add_byte_space();
				bfr.Add(months[months_bgn + date.Month() - ListAdp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(date.Year());
				break;
			default: 
				if (precision <= 9)		// y, round to (9 - prec)
					bfr.Add_long_variable(date.Year());
				else {									// EX: "4:05:06 3 Feb 2011"
					bfr.Add_int_variable(date.Hour());
					bfr.Add(time_spr);
					bfr.Add_int_fixed(date.Minute(), 2);
					bfr.Add(time_spr);
					bfr.Add_int_fixed(date.Second(), 2);
					bfr.Add_byte_space();
					bfr.Add_int_variable(date.Day());
					bfr.Add_byte_space();
					bfr.Add(months[months_bgn + date.Month() - ListAdp_.Base1]);
					bfr.Add_byte_space();
					bfr.Add_long_variable(date.Year());
				}
				break;
		}
	}
	public static final int
	  Fmt_y				=  9
	, Fmt_ym			= 10
	, Fmt_ymd			= 11
	, Fmt_ymdh			= 12
	, Fmt_ymdhn			= 13
	, Fmt_ymdhns		= 14	// anything >13 ?
	;
}
