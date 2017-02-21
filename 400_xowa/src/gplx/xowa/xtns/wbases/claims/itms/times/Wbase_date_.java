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
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wbases.hwtrs.*;
public class Wbase_date_ {
	public static Wbase_date Parse(byte[] date, int precision, int before, int after, boolean calendar_is_julian) {// EX:+00000002001-02-03T04:05:06Z
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
		return new Wbase_date(year * year_sign, month, day, hour, minute, second, precision, before, after, calendar_is_julian);
	}
	public static Wbase_date To_julian(Wbase_date date) {
		long a = (long)Math_.Floor((14 - date.Month() / 12));
		long y = date.Year() + 4800 - a;
		long m = date.Month() + 12 * a - 3;
		long julian = date.Day() + (long)Math_.Floor((153 * m + 2) / 5) + 365 * y + (long)Math_.Floor(y / 4) - (long)Math_.Floor(y / 100) + (long)Math_.Floor(y / 400) - 32045;
		long c = julian + 32082;
		long d = (long)Math_.Floor((4 * c + 3) / 1461);
		long e = c - (long)Math_.Floor((1461 * d) / 4);
		long n = (long)Math_.Floor((5 * e + 2) / 153);
		long new_y = d - 4800 + (long)Math_.Floor(n / 10);
		int new_m = (int)(n + 3 - 12 * (long)Math_.Floor(n / 10));
		int new_d = (int)(e - (long)Math_.Floor((153 * n + 2) / 5) + 1);
		return new Wbase_date(new_y, new_m, new_d, date.Hour(), date.Minute(), date.Second(), date.Precision(), date.Before(), date.After(), date.Calendar_is_julian());
	}
	public static void To_bfr(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wbase_date date) {
		// TOMBSTONE: use "actual" date; do not do conversion to julian; DATE:2016-11-10
		// boolean calendar_is_julian = date.Calendar_is_julian();
		// if (calendar_is_julian) date = To_julian(date);
		long year = date.Year();
		int months_bgn = msgs.Month_bgn_idx();
		byte[][] months = msgs.Ary();
		int precision = date.Precision();
		byte[] time_spr = msgs.Sym_time_spr();
		switch (precision) {
			case Wbase_date.Fmt_ym:					// EX: "Feb 2001"
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(year);
				break;
			case Wbase_date.Fmt_ymd: 				// EX: "3 Feb 2001"
				bfr.Add_int_variable(date.Day());
				bfr.Add_byte_space();
				bfr.Add(months[months_bgn + date.Month() - List_adp_.Base1]);
				bfr.Add_byte_space();
				bfr.Add_long_variable(date.Year());
				break;
			case Wbase_date.Fmt_ymdh:				// EX: "4:00 3 Feb 2011"
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
			case Wbase_date.Fmt_ymdhn:				// EX: "4:05 3 Feb 2011"
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
		// TOMBSTONE: use "actual" date; do not do conversion to julian; DATE:2016-11-10
		// if (calendar_is_julian)
		// 	bfr.Add(msgs.Time_julian());
		Xto_str_beforeafter(bfr, tmp_fmtr, tmp_bfr, msgs, date);
	}
	private static void Xto_str_beforeafter(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wbase_date date) {
		byte[] bry = null;
		int before = date.Before();
		int after = date.After();
		if (before == 0) {
			if (after != 0)
				bry = tmp_bfr.Add(msgs.Sym_plus()).Add_int_variable(after).To_bry_and_clear();
		}
		else {
			if		(after == 0)
				bry = tmp_bfr.Add(msgs.Sym_minus()).Add_int_variable(before).To_bry_and_clear();
			else if (before == after)
				bry = tmp_bfr.Add(msgs.Sym_plusminus()).Add_int_variable(before).To_bry_and_clear();
			else
				bry = tmp_bfr.Add(msgs.Sym_minus()).Add_int_variable(before).Add(msgs.Sym_list_comma()).Add(msgs.Sym_plus()).Add_int_variable(after).To_bry_and_clear();
		}
		if (bry != null) {
			bry = tmp_fmtr.Fmt_(msgs.Sym_fmt_parentheses()).Bld_bry_many(tmp_bfr, bry);
			bfr.Add_byte_space().Add(bry);
		}
	}
	private static void Xto_str_fmt_y(Bry_bfr bfr, Bry_fmtr tmp_fmtr, Bry_bfr tmp_bfr, Wdata_hwtr_msgs msgs, Wbase_date date, int precision) {
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
}
