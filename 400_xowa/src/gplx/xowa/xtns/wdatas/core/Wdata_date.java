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
import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_date {
	public Wdata_date(long year, int month, int day, int hour, int minute, int second, int precision, int before, int after, boolean calendar_is_julian) {
		this.year = year; this.month = month; this.day = day; this.hour = hour; this.minute = minute; this.second = second;
		this.precision = precision; this.before = before; this.after = after; this.calendar_is_julian = calendar_is_julian;
	}
	public long Year() {return year;} private final long year;
	public int Month() {return month;} private final int month;
	public int Day() {return day;} private final int day;
	public int Hour() {return hour;} private final int hour;
	public int Minute() {return minute;} private final int minute;
	public int Second() {return second;} private final int second;
	public int Precision() {return precision;} private final int precision;
	public int Before() {return before;} private final int before;
	public int After() {return after;} private final int after;
	public boolean Calendar_is_julian() {return calendar_is_julian;} private final boolean calendar_is_julian;
	public static Wdata_date parse(byte[] date, int precision, int before, int after, boolean calendar_is_julian) {// EX:+00000002001-02-03T04:05:06Z
		int year_sign = 1;
		switch (date[0]) {
			case Byte_ascii.Plus:	break;
			case Byte_ascii.Dash:	year_sign = -1; break;
			default:				throw Err_.new_unhandled(date[0]);
		}
		int year_end = Bry_find_.Find_fwd(date, Byte_ascii.Dash, 1);
		long year		= Long_.parse_or(String_.new_a7(date, 1, year_end), -1); if (year == -1) throw Err_.new_wo_type("parse failed", "raw", String_.new_a7(date));
		int month		= Bry_.To_int_or(date, year_end +  1, year_end +  3, -1);
		int day			= Bry_.To_int_or(date, year_end +  4, year_end +  6, -1);
		int hour		= Bry_.To_int_or(date, year_end +  7, year_end +  9, -1);
		int minute		= Bry_.To_int_or(date, year_end + 10, year_end + 12, -1);
		int second		= Bry_.To_int_or(date, year_end + 13, year_end + 15, -1);
		return new Wdata_date(year * year_sign, month, day, hour, minute, second, precision, before, after, calendar_is_julian);
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
		return new Wdata_date(new_y, new_m, new_d, date.Hour(), date.Minute(), date.Second(), date.precision, date.before, date.after, date.calendar_is_julian);
	}
	public static void Xto_str(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wdata_date date) {
		boolean calendar_is_julian = date.calendar_is_julian;
		if (calendar_is_julian)
			date = Xto_julian(date);
		long year = date.Year();
		int months_bgn = msgs.Month_bgn_idx();
		byte[][] months = msgs.Ary();
		int precision = date.precision;
		byte[] time_spr = msgs.Sym_time_spr();
		switch (precision) {
			case Wdata_date.Fmt_ym:					// EX: "Feb 2001"
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(year);
				break;
			case Wdata_date.Fmt_ymd: 				// EX: "3 Feb 2001"
				bfr.Add_int_variable(date.Day());
				bfr.Add_byte_space();
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
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
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
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
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(date.Year());
				break;
			default: 
				if (precision <= 9)					// y, round to (9 - prec)
					Xto_str_fmt_y(bfr, tmp_fmtr, tmp_bfr, msgs, date, precision);
				else {								// EX: "4:05:06 3 Feb 2011"
					bfr.Add_int_variable(date.Hour());
					bfr.Add(time_spr);
					bfr.Add_int_fixed(date.Minute(), 2);
					bfr.Add(time_spr);
					bfr.Add_int_fixed(date.Second(), 2);
					bfr.Add_byte_space();
					bfr.Add_int_variable(date.Day());
					bfr.Add_byte_space();
					bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
					bfr.Add_byte_space();
					bfr.Add_long_variable(date.Year());
				}
				break;
		}
		if (calendar_is_julian)
			bfr.Add(msgs.Time_julian());
		Xto_str_beforeafter(bfr, tmp_fmtr, tmp_bfr, msgs, date);
	}
	private static void Xto_str_beforeafter(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wdata_date date) {
		byte[] bry = null;
		int before = date.before;
		int after = date.after;
		if (before == 0) {
			if (after != 0)
				bry = tmp_bfr.Add(msgs.Sym_plus()).Add_int_variable(after).Xto_bry_and_clear();
		}
		else {
			if		(after == 0)
				bry = tmp_bfr.Add(msgs.Sym_minus()).Add_int_variable(before).Xto_bry_and_clear();
			else if (before == after)
				bry = tmp_bfr.Add(msgs.Sym_plusminus()).Add_int_variable(before).Xto_bry_and_clear();
			else
				bry = tmp_bfr.Add(msgs.Sym_minus()).Add_int_variable(before).Add(msgs.Sym_list_comma()).Add(msgs.Sym_plus()).Add_int_variable(after).Xto_bry_and_clear();
		}
		if (bry != null) {
			bry = tmp_fmtr.Fmt_(msgs.Sym_fmt_parentheses()).Bld_bry_many(tmp_bfr, bry);
			bfr.Add_byte_space().Add(bry);
		}
	}
	private static void Xto_str_fmt_y(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wdata_date date, int precision) {
		int year_pow = 9 - precision;
		byte[] year_fmt = msgs.Ary()[msgs.Time_year_idx() + year_pow];
		long year = date.Year();
		byte[] repl_fmt = null;
		if (year <= 0) {					// negative
			if (year_pow < 4)				// negative years < 999 get "BC"
				repl_fmt = msgs.Time_relative_bc();
			else 							// negative years > 999 get "ago"
				repl_fmt = msgs.Time_relative_ago();
		}
		else {
			if (year_pow > 4)				// positive years > 999 get "in time"
				repl_fmt = msgs.Time_relative_in();
		}
		if (repl_fmt != null)
			year_fmt = tmp_fmtr.Fmt_(repl_fmt).Bld_bry_many(tmp_bfr, year_fmt);
		if (year <= 0)
			year *= -1;						// convert negative to positive; note that negative year will be reported with "BC" / "ago"
		switch (year_pow) {
			case 0:		break; // noop
			default:
				year = (int)(year / Math_.Pow(10, year_pow));
				break;
		}
		byte[] year_bry = tmp_fmtr.Fmt_(year_fmt).Bld_bry_many(tmp_bfr, year);
		bfr.Add(year_bry);
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
