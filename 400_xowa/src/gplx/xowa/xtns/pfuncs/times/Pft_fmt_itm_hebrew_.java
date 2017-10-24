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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
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
	public static int[] Calc_hebrew_date(DateAdp date) {
		int[] rv = new int[4];	// MEM:cache
		Calc_hebrew_date(rv, date.Year(), date.Month(), date.Day());
		return rv;
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
	private static final    byte[][] Month_name_full_ary = new byte[][] 
	{ Bry_.new_a7("hebrew-calendar-m1"), Bry_.new_a7("hebrew-calendar-m2"), Bry_.new_a7("hebrew-calendar-m3")
	, Bry_.new_a7("hebrew-calendar-m4"), Bry_.new_a7("hebrew-calendar-m5"), Bry_.new_a7("hebrew-calendar-m6")
	, Bry_.new_a7("hebrew-calendar-m7"), Bry_.new_a7("hebrew-calendar-m8"), Bry_.new_a7("hebrew-calendar-m9")
	, Bry_.new_a7("hebrew-calendar-m10"), Bry_.new_a7("hebrew-calendar-m11"), Bry_.new_a7("hebrew-calendar-m12")
	, Bry_.new_a7("hebrew-calendar-m6a"), Bry_.new_a7("hebrew-calendar-m6b")
	};
	private static final    byte[][] Month_name_gen_ary = new byte[][] 
	{ Bry_.new_a7("hebrew-calendar-m1-gen"), Bry_.new_a7("hebrew-calendar-m2-gen"), Bry_.new_a7("hebrew-calendar-m3-gen")
	, Bry_.new_a7("hebrew-calendar-m4-gen"), Bry_.new_a7("hebrew-calendar-m5-gen"), Bry_.new_a7("hebrew-calendar-m6-gen")
	, Bry_.new_a7("hebrew-calendar-m7-gen"), Bry_.new_a7("hebrew-calendar-m8-gen"), Bry_.new_a7("hebrew-calendar-m9-gen")
	, Bry_.new_a7("hebrew-calendar-m10-gen"), Bry_.new_a7("hebrew-calendar-m11-gen"), Bry_.new_a7("hebrew-calendar-m12-gen")
	, Bry_.new_a7("hebrew-calendar-m6a-gen"), Bry_.new_a7("hebrew-calendar-m6b-gen")
	};
	public static final    int
	  Rslt_year_num				= 0
	, Rslt_month_num			= 1
	, Rslt_day_num				= 2
	, Rslt_month_days_count		= 3
	;
	private static final    byte[][][] Numeral_tbls = new byte[][][]
	{ new byte[][] {Bry_.Empty, Bry_.new_u8("א"), Bry_.new_u8("ב"), Bry_.new_u8("ג"), Bry_.new_u8("ד"), Bry_.new_u8("ה")	, Bry_.new_u8("ו")	, Bry_.new_u8("ז")	, Bry_.new_u8("ח")	, Bry_.new_u8("ט")	, Bry_.new_u8("י")}
	, new byte[][] {Bry_.Empty, Bry_.new_u8("י"), Bry_.new_u8("כ"), Bry_.new_u8("ל"), Bry_.new_u8("מ"), Bry_.new_u8("נ")	, Bry_.new_u8("ס")	, Bry_.new_u8("ע")	, Bry_.new_u8("פ")	, Bry_.new_u8("צ")	, Bry_.new_u8("ק")}
	, new byte[][] {Bry_.Empty, Bry_.new_u8("ק"), Bry_.new_u8("ר"), Bry_.new_u8("ש"), Bry_.new_u8("ת"), Bry_.new_u8("תק")	, Bry_.new_u8("תר")	, Bry_.new_u8("תש")	, Bry_.new_u8("תת")	, Bry_.new_u8("תתק"), Bry_.new_u8("תתר")}
	, new byte[][] {Bry_.Empty, Bry_.new_u8("א"), Bry_.new_u8("ב"), Bry_.new_u8("ג"), Bry_.new_u8("ד"), Bry_.new_u8("ה")	, Bry_.new_u8("ו")	, Bry_.new_u8("ז")	, Bry_.new_u8("ח")	, Bry_.new_u8("ט")	, Bry_.new_u8("י")}
	};
	public static byte[] Calc_hebrew_numeral(int num) {
		if (num > 9999 || num <= 0) return Int_.To_bry(num);
		
		byte[] tmp = Bry_.Empty;
		int pow10 = 1000; 
		for (int i = 3; i >= 0; pow10 /= 10, i--) {
			if (num >= pow10) {
				if (num == 15 || num == 16) {
					tmp = Bry_.Add(tmp, Numeral_tbls[0][9], Numeral_tbls[0][num - 9]);
					num = 0;
				} else {
					tmp = Bry_.Add(tmp, Numeral_tbls[i][(int)(num / pow10)]);
					if (pow10 == 1000)
						tmp = Bry_.Add(tmp, Byte_ascii.Apos_bry);
				}
			}
			num = num % pow10;
		}
		byte[] rv = Bry_.Empty;
		int tmp_len = tmp.length;
		if (tmp_len == 2) {
			rv = Bry_.Add(tmp, Byte_ascii.Apos_bry);
		}
		else {
			rv  = Bry_.Add(Bry_.Mid(tmp, 0, tmp_len - 2), Byte_ascii.Quote_bry);
			rv  = Bry_.Add(rv, Bry_.Mid(tmp, tmp_len - 2, tmp_len));
		}
		int rv_len = rv.length;
		Object end_obj = end_trie.Match_bgn(rv, rv_len - 2, rv_len);
		if (end_obj != null) {
			byte[] end = (byte[])end_obj;
			byte[] start = Bry_.Mid(rv, 0, rv_len - 2);
			rv = Bry_.Add(start, end);
		}
		return rv;
	}
	private static final    gplx.core.btries.Btrie_slim_mgr end_trie = gplx.core.btries.Btrie_slim_mgr.cs()
	.Add_str_str("כ", "ך")
	.Add_str_str("מ", "ם")
	.Add_str_str("נ", "ן")
	.Add_str_str("פ", "ף")
	.Add_str_str("צ", "ץ")
	;
}
