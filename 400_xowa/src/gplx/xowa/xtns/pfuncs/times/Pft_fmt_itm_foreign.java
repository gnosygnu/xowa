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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
class Pft_fmt_itm_roman implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_roman;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int nxt_idx = bldr.Idx_cur() + 1;
		Pft_fmt_itm[] ary = bldr.Fmt_itms();
		if (nxt_idx < ary.length) {
			Pft_fmt_itm itm = (Pft_fmt_itm)ary[nxt_idx];
			if (itm.TypeId() == Pft_fmt_itm_.Tid_seg_int) {
				Pft_fmt_itm_seg_int nxt_int = (Pft_fmt_itm_seg_int)ary[nxt_idx];	// FUTURE: should check tkn type
				int v = date.Segment(nxt_int.SegIdx());
				Pfxtp_roman.ToRoman(v, bfr);
				bldr.Idx_nxt_(nxt_idx + 1);
				return;
			}
		}
		bfr.Add_str_a7("xf");
	}
}
class Pft_fmt_itm_thai implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_thai;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_int_variable(date.Year() + 543);
	}
}
class Pft_fmt_itm_minguo implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_minguo;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_int_variable(date.Year() - 1911);
	}
}
class Pft_fmt_itm_hebrew_year_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_year_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_year_num]);
	}
}
class Pft_fmt_itm_hebrew_month_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_month_num]);
	}
}
class Pft_fmt_itm_hebrew_day_num implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_day_num;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_day_num]);
	}
}
class Pft_fmt_itm_hebrew_month_days_count implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_days_count;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		bfr.Add_int_variable(hebrew_date[Pft_fmt_itm_hebrew_.Rslt_month_days_count]);
	}
}
class Pft_fmt_itm_hebrew_month_name_full implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_name_full;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_hebrew_.Get_hebrew_month_name_full(wiki, date));
	}
}
class Pft_fmt_itm_hebrew_month_name_gen implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_month_name_gen;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add(Pft_fmt_itm_hebrew_.Get_hebrew_month_name_gen(wiki, date));
	}
}
class Pft_fmt_itm_hebrew_numeral implements Pft_fmt_itm {
	public int TypeId() {return Pft_fmt_itm_.Tid_hebrew_numeral;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_str(Pft_fmt_itm_hebrew_.Calc_hebrew_numeral(date.Year()));
	}
}
class Pft_fmt_itm_hebrew_ {
	public static int Calc_hebrew_year_num_start(int year) {
		int year_minus_1 = year - 1;
		int a = (12 * year_minus_1 + 17) % 19;
		int b = year_minus_1 % 4;
		double m = 32.044093161144d + 1.5542417966212d * a + b / 4.0 - 0.0031777940220923d * year_minus_1;
		if (m < 0)
			m--;
		int mar = (int)m;
		if (m < 0)
			m++;
		m -= mar;
		int c = (mar + 3 * year_minus_1 + 5 * b + 5) % 7;
		if		(c == 0 && a > 11 && m >= 0.89772376543210d)
			mar++;
		else if	(c == 1 && a >  6 && m >= 0.63287037037037d)
			mar += 2;
		else if	(c == 2 || c == 4 || c == 6)
			mar++;
		double year_minus_3761 = year - 3761;
		mar += (int)(year_minus_3761 / 100 ) - (int)(year_minus_3761 / 400) - 24;
		return mar;
	}
	private static final int[] Hebrew_date_rslt = new int[4];
	public static int[] Calc_hebrew_date(DateAdp date) {
		synchronized (Hebrew_date_rslt) {
			Calc_hebrew_date(Hebrew_date_rslt, date.Year(), date.Month(), date.Day());
			return Hebrew_date_rslt;
		}
	}
	public static boolean Calc_hebrew_date(int[] rv, int year, int month, int day) {	// REF.MW:Language.php|tsToHebrew
		// Calculate Hebrew year
		int hebrewYear = year + 3760;

		// Month number when September = 1, August = 12
		month += 4;
		if (month > 12) {
			// Next year
			month -= 12;
			year++;
			hebrewYear++;
		}

		// Calculate day of year from 1 September
		int dayOfYear = day;
		for (int i = 1; i < month; i++) {
			if (i == 6) {
				// February
				dayOfYear += 28;
				// Check if the year is leap
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 > 0)) {
					dayOfYear++;
				}
			} else if (i == 8 || i == 10 || i == 1 || i == 3) {
				dayOfYear += 30;
			} else {
				dayOfYear += 31;
			}
		}

		// Calculate the start of the Hebrew year
		int start = Calc_hebrew_year_num_start(hebrewYear);

		// Calculate next year's start
		int nextStart = 0;
		if (dayOfYear <= start) {
			// Day is before the start of the year - it is the previous year
			// Next year's start
			nextStart = start;
			// Previous year
			year--;
			hebrewYear--;
			// Add days since previous year's 1 September
			dayOfYear += 365;
			if ((year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) {
				// Leap year
				dayOfYear++;
			}
			// Start of the new (previous) year
			start = Calc_hebrew_year_num_start(hebrewYear);
		} else {
			// Next year's start
			nextStart = Calc_hebrew_year_num_start(hebrewYear + 1);
		}

		// Calculate Hebrew day of year
		int hebrewDayOfYear = dayOfYear - start;

		// Difference between year's days
		int diff = nextStart - start;
		// Add 12 (or 13 for leap years) days to ignore the difference between
		// Hebrew and Gregorian year (353 at least vs. 365/6) - now the
		// difference is only about the year type
		if ((year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) {
			diff += 13;
		} else {
			diff += 12;
		}

		// Check the year pattern, and is leap year
		// 0 means an incomplete year, 1 means a regular year, 2 means a complete year
		// This is mod 30, to work on both leap years (which add 30 days of Adar I)
		// and non-leap years
		int yearPattern = diff % 30;
		// Check if leap year
		boolean isLeap = diff >= 30;

		// Calculate day in the month from number of day in the Hebrew year
		// Don't check Adar - if the day is not in Adar, we will stop before;
		// if it is in Adar, we will use it to check if it is Adar I or Adar II
		int hebrewDay = hebrewDayOfYear;
		int hebrewMonth = 1;
		int days = 0;

		while (hebrewMonth <= 12) {
			// Calculate days in this month
			if (isLeap && hebrewMonth == 6) {
				// Adar in a leap year
				if (isLeap) {
					// Leap year - has Adar I, with 30 days, and Adar II, with 29 days
					days = 30;
					if (hebrewDay <= days) {
						// Day in Adar I
						hebrewMonth = 13;
					} else {
						// Subtract the days of Adar I
						hebrewDay -= days;
						// Try Adar II
						days = 29;
						if (hebrewDay <= days) {
							// Day in Adar II
							hebrewMonth = 14;
						}
					}
				}
			} else if (hebrewMonth == 2 && yearPattern == 2) {
				// Cheshvan in a complete year (otherwise as the rule below)
				days = 30;
			} else if (hebrewMonth == 3 && yearPattern == 0) {
				// Kislev in an incomplete year (otherwise as the rule below)
				days = 29;
			} else {
				// Odd months have 30 days, even have 29
				days = 30 - (hebrewMonth - 1) % 2;
			}
			if (hebrewDay <= days) {
				// In the current month
				break;
			} else {
				// Subtract the days of the current month
				hebrewDay -= days;
				// Try in the next month
				hebrewMonth++;
			}
		}
		rv[0] = hebrewYear;
		rv[1] = hebrewMonth;
		rv[2] = hebrewDay;
		rv[3] = days;
		return true;
	}
	public static byte[] Get_hebrew_month_name_full(Xowe_wiki wiki, DateAdp date) {return Get_hebrew_month_name(wiki, date, Month_name_full_ary);}
	public static byte[] Get_hebrew_month_name_gen(Xowe_wiki wiki, DateAdp date) {return Get_hebrew_month_name(wiki, date, Month_name_gen_ary);}
	private static byte[] Get_hebrew_month_name(Xowe_wiki wiki, DateAdp date, byte[][] name_ary) {
		int[] hebrew_date = Pft_fmt_itm_hebrew_.Calc_hebrew_date(date);
		int hebrew_month = hebrew_date[Pft_fmt_itm_hebrew_.Rslt_month_num] - List_adp_.Base1;
		byte[] msg_key = name_ary[hebrew_month];
		return wiki.Msg_mgr().Val_by_key_obj(msg_key);
	}
	private static final byte[][] Month_name_full_ary = new byte[][] 
	{ Bry_.new_a7("hebrew-calendar-m1"), Bry_.new_a7("hebrew-calendar-m2"), Bry_.new_a7("hebrew-calendar-m3")
	, Bry_.new_a7("hebrew-calendar-m4"), Bry_.new_a7("hebrew-calendar-m5"), Bry_.new_a7("hebrew-calendar-m6")
	, Bry_.new_a7("hebrew-calendar-m7"), Bry_.new_a7("hebrew-calendar-m8"), Bry_.new_a7("hebrew-calendar-m9")
	, Bry_.new_a7("hebrew-calendar-m10"), Bry_.new_a7("hebrew-calendar-m11"), Bry_.new_a7("hebrew-calendar-m12")
	, Bry_.new_a7("hebrew-calendar-m6a"), Bry_.new_a7("hebrew-calendar-m6b")
	};
	private static final byte[][] Month_name_gen_ary = new byte[][] 
	{ Bry_.new_a7("hebrew-calendar-m1-gen"), Bry_.new_a7("hebrew-calendar-m2-gen"), Bry_.new_a7("hebrew-calendar-m3-gen")
	, Bry_.new_a7("hebrew-calendar-m4-gen"), Bry_.new_a7("hebrew-calendar-m5-gen"), Bry_.new_a7("hebrew-calendar-m6-gen")
	, Bry_.new_a7("hebrew-calendar-m7-gen"), Bry_.new_a7("hebrew-calendar-m8-gen"), Bry_.new_a7("hebrew-calendar-m9-gen")
	, Bry_.new_a7("hebrew-calendar-m10-gen"), Bry_.new_a7("hebrew-calendar-m11-gen"), Bry_.new_a7("hebrew-calendar-m12-gen")
	, Bry_.new_a7("hebrew-calendar-m6a-gen"), Bry_.new_a7("hebrew-calendar-m6b-gen")
	};
	public static final int
	  Rslt_year_num				= 0
	, Rslt_month_num			= 1
	, Rslt_day_num				= 2
	, Rslt_month_days_count		= 3
	;

	private static final String[][] Numeral_tbls = new String[][]
	{ new String[] {"", "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט", "י"}
	, new String[] {"", "י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ", "ק"}
	, new String[] {"", "ק", "ר", "ש", "ת", "תק", "תר", "תש", "תת", "תתק", "תתר"}
	, new String[] {"", "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט", "י"}
	};
	public static String Calc_hebrew_numeral(int num) {
		if (num > 9999 || num <= 0)
			return Int_.Xto_str(num);
		
		String tmp = "";
		int pow10 = 1000; 
		for (int i = 3; i >= 0; pow10 /= 10, i--) {
			if (num >= pow10) {
				if (num == 15 || num == 16) {
					tmp += Numeral_tbls[0][9] + Numeral_tbls[0][num - 9];
					num = 0;
				} else {
					tmp += Numeral_tbls[i][(int)(num / pow10)];
					if (pow10 == 1000)
						tmp += "'";
				}
			}
			num = num % pow10;
		}
		String rv = "";
		int tmp_len = String_.Len(tmp);
		if (tmp_len == 2) {
			rv = tmp + "'";
		}
		else {
			rv  = String_.Mid(tmp, 0, tmp_len - 1) + "\"";
			rv += String_.Mid(tmp, tmp_len - 1);
		}
		int rv_len = String_.Len(rv);
		String start = String_.Mid(rv, 0, rv_len - 1);
		String end = String_.Mid(rv, rv_len - 1);
		if		(String_.Eq(end, "כ"))
			rv = start + "ך";
		else if	(String_.Eq(end, "מ"))
			rv = start + "ם";
		else if	(String_.Eq(end, "נ"))
			rv = start + "ן";
		else if	(String_.Eq(end, "פ"))
			rv = start + "ף";
		else if	(String_.Eq(end, "צ"))
			rv = start + "ץ";
		return rv;
	}
}
