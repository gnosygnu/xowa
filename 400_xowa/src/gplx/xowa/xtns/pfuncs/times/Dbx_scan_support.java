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
import gplx.core.btries.*;
public class Dbx_scan_support {
	public static int TIMELIB_UNSET  =-99999;

	// timelib_public.h
	public static final int TIMELIB_YEAR     =1; //6; rearrange to same order as DateAdp_
	public static final int TIMELIB_MONTH    =2; //5;
	public static final int TIMELIB_DAY      =3; //4;
	public static final int TIMELIB_HOUR     =4; //3;
	public static final int TIMELIB_MINUTE   =5; //2;
	public static final int TIMELIB_SECOND   =6; //1;
	public static final int TIMELIB_MICROSEC =7; //9;
	public static final int TIMELIB_WEEKDAY  =8; //7;
	public static final int TIMELIB_SPECIAL  =9; //8;

	public static final int TIMELIB_SPECIAL_WEEKDAY                   =0x01;
	public static final int TIMELIB_SPECIAL_DAY_OF_WEEK_IN_MONTH      =0x02;
	public static final int TIMELIB_SPECIAL_LAST_DAY_OF_WEEK_IN_MONTH =0x03;

	public static final int TIMELIB_SPECIAL_FIRST_DAY_OF_MONTH        =0x01;
	public static final int TIMELIB_SPECIAL_LAST_DAY_OF_MONTH         =0x02;

	//#define SECS_PER_ERA   12622780800
	public static final int SECS_PER_DAY   =86400;
	public static final int DAYS_PER_YEAR    =365;
	public static final int DAYS_PER_LYEAR   =366;
	/* 400*365 days + 97 leap days */
	public static final int DAYS_PER_LYEAR_PERIOD =146097;
	public static final int YEARS_PER_LYEAR_PERIOD =400;

	public static final int TIMELIB_TZINFO_PHP       =0x01;
	public static final int TIMELIB_TZINFO_ZONEINFO  =0x02;

	// timelib.h
	public static final int TIMELIB_WARN_MASK                      =0x1ff;
	public static final int TIMELIB_ERR_MASK                       =0x2ff;

	public static final int TIMELIB_WARN_DOUBLE_TZ                 =0x101;
	public static final int TIMELIB_WARN_INVALID_TIME              =0x102;
	public static final int TIMELIB_WARN_INVALID_DATE              =0x103;
	public static final int TIMELIB_WARN_TRAILING_DATA             =0x11a;

	public static final int TIMELIB_ERR_DOUBLE_TZ                  =0x201;
	public static final int TIMELIB_ERR_TZID_NOT_FOUND             =0x202;
	public static final int TIMELIB_ERR_DOUBLE_TIME                =0x203;
	public static final int TIMELIB_ERR_DOUBLE_DATE                =0x204;
	public static final int TIMELIB_ERR_UNEXPECTED_CHARACTER       =0x205;
	public static final int TIMELIB_ERR_EMPTY_STRING               =0x206;
	public static final int TIMELIB_ERR_UNEXPECTED_DATA            =0x207;
	public static final int TIMELIB_ERR_NO_TEXTUAL_DAY             =0x208;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_DAY           =0x209;
	public static final int TIMELIB_ERR_NO_THREE_DIGIT_DAY_OF_YEAR =0x20a;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_MONTH         =0x20b;
	public static final int TIMELIB_ERR_NO_TEXTUAL_MONTH           =0x20c;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_YEAR          =0x20d;
	public static final int TIMELIB_ERR_NO_FOUR_DIGIT_YEAR         =0x20e;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_HOUR          =0x20f;
	public static final int TIMELIB_ERR_HOUR_LARGER_THAN_12        =0x210;
	public static final int TIMELIB_ERR_MERIDIAN_BEFORE_HOUR       =0x211;
	public static final int TIMELIB_ERR_NO_MERIDIAN                =0x212;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_MINUTE        =0x213;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_SECOND        =0x214;
	public static final int TIMELIB_ERR_NO_SIX_DIGIT_MICROSECOND   =0x215;
	public static final int TIMELIB_ERR_NO_SEP_SYMBOL              =0x216;
	public static final int TIMELIB_ERR_EXPECTED_ESCAPE_CHAR       =0x217;
	public static final int TIMELIB_ERR_NO_ESCAPED_CHAR            =0x218;
	public static final int TIMELIB_ERR_WRONG_FORMAT_SEP           =0x219;
	public static final int TIMELIB_ERR_TRAILING_DATA              =0x21a;
	public static final int TIMELIB_ERR_DATA_MISSING               =0x21b;
	public static final int TIMELIB_ERR_NO_THREE_DIGIT_MILLISECOND =0x21c;
	public static final int TIMELIB_ERR_NO_FOUR_DIGIT_YEAR_ISO     =0x21d;
	public static final int TIMELIB_ERR_NO_TWO_DIGIT_WEEK          =0x21e;
	public static final int TIMELIB_ERR_INVALID_WEEK               =0x21f;
	public static final int TIMELIB_ERR_NO_DAY_OF_WEEK             =0x220;
	public static final int TIMELIB_ERR_INVALID_DAY_OF_WEEK        =0x221;
	public static final int TIMELIB_ERR_INVALID_SPECIFIER          =0x222;
	public static final int TIMELIB_ERR_INVALID_TZ_OFFSET          =0x223;
	public static final int TIMELIB_ERR_FORMAT_LITERAL_MISMATCH    =0x224;
	public static final int TIMELIB_ERR_MIX_ISO_WITH_NATURAL       =0x225;

	public static final int TIMELIB_ZONETYPE_OFFSET =1;
	public static final int TIMELIB_ZONETYPE_ABBR   =2;
	public static final int TIMELIB_ZONETYPE_ID     =3;

	//inside parse.re
	public static final int TIMELIB_XMLRPC_SOAP    =260;
	public static final int TIMELIB_TIME12         =261;
	public static final int TIMELIB_TIME24         =262;
	public static final int TIMELIB_GNU_NOCOLON    =263;
	public static final int TIMELIB_GNU_NOCOLON_TZ =264;
	public static final int TIMELIB_ISO_NOCOLON    =265;

	public static final int TIMELIB_AMERICAN       =266;
	public static final int TIMELIB_ISO_DATE       =267;
	public static final int TIMELIB_DATE_FULL      =268;
	public static final int TIMELIB_DATE_TEXT      =269;
	public static final int TIMELIB_DATE_NOCOLON   =270;
	public static final int TIMELIB_PG_YEARDAY     =271;
	public static final int TIMELIB_PG_TEXT        =272;
	public static final int TIMELIB_PG_REVERSE     =273;
	public static final int TIMELIB_CLF            =274;
	public static final int TIMELIB_DATE_NO_DAY    =275;
	public static final int TIMELIB_SHORTDATE_WITH_TIME =276;
	public static final int TIMELIB_DATE_FULL_POINTED =277;
	public static final int TIMELIB_TIME24_WITH_ZONE =278;
	public static final int TIMELIB_ISO_WEEK       =279;
	public static final int TIMELIB_LF_DAY_OF_MONTH =280;
	public static final int TIMELIB_WEEK_DAY_OF_MONTH =281;

	public static final int TIMELIB_TIMEZONE       =300;
	public static final int TIMELIB_AGO            =301;

	public static final int TIMELIB_RELATIVE       =310;

	public static final int TIMELIB_ERROR          =999;

	private static Btrie_slim_mgr trie;
	private static final Btrie_rv trv = new Btrie_rv();
	public static void Init(Btrie_slim_mgr triex) {
		trie = triex;
	}
	public static void add_error(Dbx_scanner s, int errcode, String str) {
		throw Err_.new_unhandled(0); // break early
		//s.errors.warning_count++;
		//System.out.println("err: " + str);
	}
	public static void add_warning(Dbx_scanner s, int errcode, String str) {
		throw Err_.new_unhandled(0); // break early
		//s.errors.error_count++;
		//System.out.println("warn: " + str);
	}

	public static boolean timelib_valid_time(int h, int i, int s)
	{
		if (h < 0 || h > 23 || i < 0 || i > 59 || s < 0 || s > 59) {
			return false;
		}
		return true;
	}

	public static boolean timelib_valid_date(int y, int m, int d)
	{
		if (m < 1 || m > 12 || d < 1 /*|| d > timelib_days_in_month(y, m)*/) {
			return false;
		}
		return true;
	}

	public static void TIMELIB_HAVE_TIME(Dbx_scanner s) {
		if (s.time.have_time > 0) {
			add_error(s, TIMELIB_ERR_DOUBLE_TIME, "Double time specification");
			//timelib_string_free(str);
			//throw return TIMELIB_ERROR;
		} else {
			s.time.have_time = 1;
			s.time.h = 0;
			s.time.i = 0;
			s.time.s = 0;
			s.time.us = 0;
		}
	}
	public static void TIMELIB_UNHAVE_TIME(Dbx_scanner s) { s.time.have_time = 0; s.time.h = 0; s.time.i = 0; s.time.s = 0; s.time.us = 0; }
	public static void TIMELIB_HAVE_DATE(Dbx_scanner s) {
		if (s.time.have_date) {
			add_error(s, TIMELIB_ERR_DOUBLE_DATE, "Double date specification");
			//timelib_string_free(str);
			//throw return TIMELIB_ERROR;
		} else {
			s.time.have_date = true;
		}
	}
	public static void TIMELIB_UNHAVE_DATE(Dbx_scanner s) { s.time.have_date = false; s.time.d = 0; s.time.m = 0; s.time.y = 0; }
	public static void TIMELIB_HAVE_RELATIVE(Dbx_scanner s) { s.time.have_relative = true; }
	public static void TIMELIB_HAVE_WEEKDAY_RELATIVE(Dbx_scanner s) { s.time.have_relative = true; s.time.relative.have_weekday_relative = true; }
	public static void TIMELIB_HAVE_SPECIAL_RELATIVE(Dbx_scanner s) { s.time.have_relative = true; s.time.relative.have_special_relative = true; }
	public static void TIMELIB_HAVE_TZ(Dbx_scanner s) {
		s.cur = s.cursor;
		if (s.time.have_zone > 0) {
			if (s.time.have_zone > 1)
				add_error(s, TIMELIB_ERR_DOUBLE_TZ, "Double timezone specification");
			else
				add_warning(s, TIMELIB_WARN_DOUBLE_TZ, "Double timezone specification");
			//timelib_string_free(str);
			s.time.have_zone++;
			//throw return TIMELIB_ERROR;
		} else {
			s.time.have_zone++;
		}
	}
	public static void TIMELIB_PROCESS_YEAR(Dbx_scanner s) {
		if ((s.time.y == TIMELIB_UNSET) || (s.length >= 4)) {
		/*	(s.time.y) = 0; */
		} else if ((s.time.y) < 100) {
			if ((s.time.y) < 70) {
				(s.time.y) += 2000;
			} else {
				s.time.y += 1900;
			}
		}
	}

	public static void timelib_skip_day_suffix(Dbx_scanner s)
	{
		byte b = s.src[s.ptr];
		if (b == ' ' || b == '\t') {
			return;
		}
		byte b2 = s.src[s.ptr + 1];
		if (b == 'n' && b2 == 'd' || b == 'r' && b2 == 'd' || b == 's' && b2 == 't' || b == 't' && b2 == 'h') {
			s.ptr += 2;
		}
	}
	public static void timelib_eat_spaces(Dbx_scanner s)
	{
		while (true) {
			byte b = s.src[s.ptr];
			if (b == ' ' || b == '\t') {
				s.ptr++;
			}
			else
				break;
		}
	}
	//public static int timelib_get_nr(Dbx_scanner s, int size) { return 0; }
	public static int timelib_get_nr_ex(Dbx_scanner s, int max_length) // sets s.length
	{
		int begin, end;
		int len = 0;
	
		while (true) {
			byte b = s.src[s.ptr];
			if (b < '0' || (b > '9')) {
				if (b == '\0')
					return TIMELIB_UNSET;
				s.ptr++;
			}
			else
				break;
		}
		begin = s.ptr;
		while (true) {
			byte b = s.src[s.ptr];
			if (b >= '0' && (b <= '9') && len < max_length) {
				++s.ptr;
				++len;
			}
			else
				break;
		}
		end = s.ptr;
		s.length = end - begin;
	
		int tmp_nr = TIMELIB_UNSET;
		tmp_nr = Bry_.To_int_or(s.src, begin, end, Int_.Min_value);
		return tmp_nr;
	}
	public static int timelib_get_month(Dbx_scanner s)
	{
		while (true) {
			byte b = s.src[s.ptr];
			if (b == ' ' || b == '\t' || b == '-' || b == '.' || b == '/')
				++s.ptr;
			else
				break;
		}
		return timelib_lookup_month(s);
	}
	public static int timelib_lookup_month(Dbx_scanner s)
	{
		byte b = s.src[s.ptr];
		Object o = trie.Match_at_w_b0(trv, b, s.src, s.ptr, s.src_len);	// now match String against tkn
		if (o == null) return TIMELIB_UNSET;
		s.ptr = trv.Pos();
		if (o instanceof Pxd_itm_month_name)
			return ((Pxd_itm_month_name)o).Seg_val();
		else
			return TIMELIB_UNSET;
	}
	public static int timelib_meridian(Dbx_scanner s, int h)
	{
		int retval = 0;
		byte b;
	
		while (true) {
			b = s.src[s.ptr];
			if (b != 'A' && b != 'a' && b != 'P' && b != 'p') //!strchr("AaPp", **ptr))
				++s.ptr;
			else
				break;
		}
		if (b == 'a' || b == 'A') {
			if (h == 12) {
				retval = -12;
			}
		} else if (h != 12) {
			retval = 12;
		}
		b = s.src[++s.ptr];
		if (b == '.') {
			b = s.src[++s.ptr];
		}
		if (b == 'M' || b == 'm') {
			b = s.src[++s.ptr];
		}
		if (b == '.') {
			++s.ptr;
		}
		return retval;
	}
	public static timelib_relunit timelib_lookup_relunit(Dbx_scanner s)
//static const timelib_relunit* timelib_lookup_relunit(char **ptr)
	{
		byte b = s.src[s.ptr];
		Object o = trie.Match_at_w_b0(trv, b, s.src, s.ptr, s.src_len);	// now match String against tkn
		if (o == null) return null;
		s.ptr = trv.Pos();
		if (o instanceof Pxd_itm_unit) {
			timelib_relunit rv = new timelib_relunit();
			Pxd_itm_unit itm = (Pxd_itm_unit)o;
			rv.unit = itm.Seg_idx() + 1; // HACK to convert DateAdp_ constants to timelib constants
			rv.multiplier = itm.Seg_multiple();
			return rv;
		}
		else if (o instanceof Pxd_itm_dow_name) {
			timelib_relunit rv = new timelib_relunit();
			Pxd_itm_dow_name itm = (Pxd_itm_dow_name)o;
			rv.unit = TIMELIB_WEEKDAY;
			rv.multiplier = itm.Dow_idx();
			return rv;
		}
		else if (o instanceof Pxd_itm_weekdays) {
			timelib_relunit rv = new timelib_relunit();
			rv.unit = TIMELIB_SPECIAL;
			rv.multiplier = TIMELIB_SPECIAL_WEEKDAY;
			return rv;
		}
		else
			return null;
	}
	public static void timelib_set_relative(Dbx_scanner s, int amount, int behavior)
//static void timelib_set_relative(char **ptr, timelib_sll amount, int behavior, Scanner *s)
	{
		timelib_relunit relunit;
	
		relunit = timelib_lookup_relunit(s);
		if (relunit == null) {
			return;
		}
	
		switch (relunit.unit) {
			case TIMELIB_MICROSEC: s.time.relative.us += amount * relunit.multiplier; break;
			case TIMELIB_SECOND:   s.time.relative.s += amount * relunit.multiplier; break;
			case TIMELIB_MINUTE:   s.time.relative.i += amount * relunit.multiplier; break;
			case TIMELIB_HOUR:     s.time.relative.h += amount * relunit.multiplier; break;
			case TIMELIB_DAY:      s.time.relative.d += amount * relunit.multiplier; break;
			case TIMELIB_MONTH:    s.time.relative.m += amount * relunit.multiplier; break;
			case TIMELIB_YEAR:     s.time.relative.y += amount * relunit.multiplier; break;
	
			case TIMELIB_WEEKDAY:
				TIMELIB_HAVE_WEEKDAY_RELATIVE(s);
				TIMELIB_UNHAVE_TIME(s);
				s.time.relative.d += (amount > 0 ? amount - 1 : amount) * 7;
				s.time.relative.weekday = relunit.multiplier;
				s.time.relative.weekday_behavior = behavior;
				break;
	
			case TIMELIB_SPECIAL:
				TIMELIB_HAVE_SPECIAL_RELATIVE(s);
				TIMELIB_UNHAVE_TIME(s);
				s.time.relative.special_type = relunit.multiplier;
				s.time.relative.special_amount = amount;
		}
	}
	public static int timelib_get_unsigned_nr(Dbx_scanner s, int max_length)
//static timelib_ull timelib_get_unsigned_nr(char **ptr, int max_length)
	{
		int dir = 1;

		byte b;
		while (true) {
			b = s.src[s.ptr];
			if ((b < '0' || b > '9') && b != '+' && b != '-') {
				if (b == '\0')
					return TIMELIB_UNSET;
				s.ptr++;
			}
			else
				break;
		}

		while (true) {
			b = s.src[s.ptr];
			if (b == '+' || b == '-') {
				if (b == '-')
					dir *= -1;
				++s.ptr;
			}
			else
				break;
		}
		return dir * timelib_get_nr_ex(s, max_length);
	}
	public static int timelib_get_relative_text(Dbx_scanner s)  // sets s.behavior
//static timelib_sll timelib_get_relative_text(char **ptr, int *behavior)
	{
		while (true) {
			byte b = s.src[s.ptr];
			if (b == ' ' || b == '\t' || b == '-' || b == '/')
				++s.ptr;
			else
				break;
		}
		return timelib_lookup_relative_text(s);
	}
	public static int timelib_lookup_relative_text(Dbx_scanner s)  // sets s.behavior
//static timelib_sll timelib_lookup_relative_text(char **ptr, int *behavior)
	{
		byte b = s.src[s.ptr];
		Object o = trie.Match_at_w_b0(trv, b, s.src, s.ptr, s.src_len);	// now match String against tkn
		if (o == null) return TIMELIB_UNSET;
		s.ptr = trv.Pos();
		s.behavior = 0;
		if (o instanceof Pxd_itm_unit) //"second" is a unit!!!!
			return 2;
		if (o instanceof Pxd_itm_ordinal)
			return ((Pxd_itm_ordinal)o).Ord_val();
			
		if (o instanceof Pxd_itm_unit_relative) {
			// need to handle 'this, next, previous, last'
			// 'this' set behavior to 1
			// what does NULL do???
			int adj = ((Pxd_itm_unit_relative)o).Adj();
			if (adj == 0) // 'this'
				s.behavior = 1;
			return adj;
		}
		else
			return TIMELIB_UNSET;
	}
	public static int timelib_daynr_from_weeknr(int y, int w, int d)
	{
		/* Figure out the dayofweek for y-1-1 */
		DateAdp yearstart = DateAdp_.FirstDayofYear(y); // should timezone be involved?
		int dow = yearstart.DayOfWeek();
		//dow = timelib_day_of_week(iy, 1, 1);
		/* then use that to figure out the offset for day 1 of week 1 */
		int day = 0 - (dow > 4 ? dow - 7 : dow);
	
		/* Add weeks and days */
		return day + ((w - 1) * 7) + d;
	}
	public static int timelib_get_frac_nr(Dbx_scanner s, int max_length)
//static timelib_sll timelib_get_frac_nr(char **ptr, int max_length)
	{
		int begin, end;
		double tmp_nr = TIMELIB_UNSET;
		int len = 0;

		while (true) {
			byte b = s.src[s.ptr];
			if ((b != '.') && (b != ':') && ((b < '0') || (b > '9'))) {
				if (b == '\0')
					return TIMELIB_UNSET;
				s.ptr++;
			}
			else
				break;
		}

		begin = s.ptr;
		while (true) {
			byte b = s.src[s.ptr];
			if (((b == '.') || (b == ':') || ((b >= '0') && (b <= '9'))) && len < max_length) {
				++s.ptr;
				++len;
			}
			else
				break;
		}
		end = s.ptr;
		s.length = end - begin;

		tmp_nr = Bry_.To_double_or(s.src, begin, end, Int_.Min_value) * Math.pow(10, 7 - (end - begin));
                
		return (int)tmp_nr;
	}
	public static int timelib_parse_zone(Dbx_scanner s) // sets tz_not_found
//timelib_parse_zone((char **) &ptr, &s->time->dst, s->time, &tz_not_found, s->tzdb, tz_get_wrapper);
//timelib_long timelib_parse_zone(char **ptr, int *dst, timelib_time *t, int *tz_not_found, const timelib_tzdb *tzdb, timelib_tz_get_wrapper tz_wrapper)
	{
	//timelib_tzinfo *res;
		int retval = 0;

		timelib_time t = s.time;
		s.tz_not_found = 0;
		byte b;
		while (true) {
			b = s.src[s.ptr];
			if (b == ' ' || b == '\t' || b == '(') {
				s.ptr++;
			}
			else
				break;
		}
		s.tz_not_found = 1;
		byte[] lsrc = s.src;
		int pos = s.ptr;
		if (lsrc[pos + 0] == 'G' && lsrc[pos + 1] == 'M' && lsrc[pos + 2] == 'T' && (lsrc[pos + 3] == '+' || lsrc[pos + 3] == '-')) {
			s.ptr += 3;
		}
		b = s.src[s.ptr];
		if (b == '+') {
			++s.ptr;
			t.is_localtime = true;
			t.zone_type = TIMELIB_ZONETYPE_OFFSET;
			s.tz_not_found = 0;
			t.dst = 0;
	
			retval = timelib_parse_tz_cor(s);
		} else if (b == '-') {
			++s.ptr;
			t.is_localtime = true;
			t.zone_type = TIMELIB_ZONETYPE_OFFSET;
			s.tz_not_found = 0;
			t.dst = 0;
	
			retval = -1 * timelib_parse_tz_cor(s);
		} else {
			int found = 0;
			int offset = 0;
			//char *tz_abbr;
	
			t.is_localtime = true;
	
			/* First, we lookup by abbreviation only */
			b = s.src[s.ptr];
			Object o = trie.Match_at_w_b0(trv, b, s.src, s.ptr, s.src_len);	// now match String against tkn
			if (o != null && o instanceof Pxd_itm_tz_abbr) {
				s.ptr = trv.Pos();
				t.zone_type = TIMELIB_ZONETYPE_ABBR;
				retval = ((Pxd_itm_tz_abbr)o).Tz_offset();
				t.tz_abbr = ((Pxd_itm_tz_abbr)o).Tz_abbr();
				s.tz_not_found = 0;
			}
//			offset = timelib_lookup_abbr(ptr, dst, &tz_abbr, &found);
//			if (found) {
//				t.zone_type = TIMELIB_ZONETYPE_ABBR;
//				timelib_time_tz_abbr_update(t, tz_abbr);
//			}
//	
//			/* Otherwise, we look if we have a TimeZone identifier */
//			if (!found || strcmp("UTC", tz_abbr) == 0) {
//				int dummy_error_code;
//	
//				if ((res = tz_wrapper(tz_abbr, tzdb, &dummy_error_code)) != NULL) {
//					t.tz_info = res;
//					t.zone_type = TIMELIB_ZONETYPE_ID;
//					found++;
//				}
//			}
//			timelib_free(tz_abbr);
//			s.tz_not_found = (found == 0);
//			retval = offset;
		}
		while (true) {
			b = s.src[s.ptr];
			if (b == ')') {
				s.ptr++;
			}
			else
				break;
		}
		return retval;
	}

	public static int TIMELIB_OVERRIDE_TIME = 1;
	public static void timelib_fill_holes(timelib_time parsed, timelib_time now, int options)
	{
		if ((options & TIMELIB_OVERRIDE_TIME) != TIMELIB_OVERRIDE_TIME && parsed.have_date && parsed.have_time == 0) {
			parsed.h = 0;
			parsed.i = 0;
			parsed.s = 0;
			parsed.us = 0;
		}
		if (
			parsed.y != TIMELIB_UNSET || parsed.m != TIMELIB_UNSET || parsed.d != TIMELIB_UNSET ||
			parsed.h != TIMELIB_UNSET || parsed.i != TIMELIB_UNSET || parsed.s != TIMELIB_UNSET
		) {
			if (parsed.us == TIMELIB_UNSET) parsed.us = 0;
		} else {
			if (parsed.us == TIMELIB_UNSET) parsed.us = now.us != TIMELIB_UNSET ? now.us : 0;
		}
		if (parsed.y == TIMELIB_UNSET) parsed.y = now.y != TIMELIB_UNSET ? now.y : 0;
		if (parsed.m == TIMELIB_UNSET) parsed.m = now.m != TIMELIB_UNSET ? now.m : 0;
		if (parsed.d == TIMELIB_UNSET) parsed.d = now.d != TIMELIB_UNSET ? now.d : 0;
		if (parsed.h == TIMELIB_UNSET) parsed.h = now.h != TIMELIB_UNSET ? now.h : 0;
		if (parsed.i == TIMELIB_UNSET) parsed.i = now.i != TIMELIB_UNSET ? now.i : 0;
		if (parsed.s == TIMELIB_UNSET) parsed.s = now.s != TIMELIB_UNSET ? now.s : 0;
		if (parsed.z == TIMELIB_UNSET) parsed.z = now.z != TIMELIB_UNSET ? now.z : 0;
		if (parsed.dst == TIMELIB_UNSET) parsed.dst = now.dst != TIMELIB_UNSET ? now.dst : 0;
	
		if (parsed.tz_abbr != null) {
			//parsed.tz_abbr = now.tz_abbr != null ? timelib_strdup(now.tz_abbr) : null;
		}
		//if (parsed.tz_info != null) {
		//	parsed.tz_info = now.tz_info ? (!(options & TIMELIB_NO_CLONE) ? timelib_tzinfo_clone(now.tz_info) : now.tz_info) : NULL;
		//}
		if (parsed.zone_type == 0 && now.zone_type != 0) {
			parsed.zone_type = now.zone_type;
	/*		parsed.tz_abbr = now.tz_abbr ? timelib_strdup(now.tz_abbr) : NULL;
			parsed.tz_info = now.tz_info ? timelib_tzinfo_clone(now.tz_info) : NULL;
	*/		parsed.is_localtime = true;
		}
	/*	timelib_dump_date(parsed, 2);
		timelib_dump_date(now, 2);
	*/
	}

	private static timelib_time timelib_now() {
		DateAdp now = Datetime_now.Get();
		timelib_time nowtime = new timelib_time();
		nowtime.y = now.Year();
		nowtime.m = now.Month();
		nowtime.d = now.Day();
		nowtime.h = now.Hour();
		nowtime.i = now.Minute();
		nowtime.s = now.Second();
		//us = 0; //Dbx_scan_support.TIMELIB_UNSET;
		//z = 0; //Dbx_scan_support.TIMELIB_UNSET;
		//dst = 0; //Dbx_scan_support.TIMELIB_UNSET;
		return nowtime;
	}
	private static int timelib_day_of_week(int y, int m, int d) {
		DateAdp dte = DateAdp_.DateByBits(y, m, d, 0, 0, 0, 0, 0, null);
		return dte.DayOfWeek();
	}

	private static void do_adjust_for_weekday(timelib_time time)
	{
		int current_dow, difference;

		current_dow = timelib_day_of_week(time.y, time.m, time.d);
		if (time.relative.weekday_behavior == 2)
		{
			/* To make "this week" work, where the current DOW is a "sunday" */
			if (current_dow == 0 && time.relative.weekday != 0) {
				time.relative.weekday -= 7;
			}

			/* To make "sunday this week" work, where the current DOW is not a
			 * "sunday" */
			if (time.relative.weekday == 0 && current_dow != 0) {
				time.relative.weekday = 7;
			}

			time.d -= current_dow;
			time.d += time.relative.weekday;
			return;
		}
		difference = time.relative.weekday - current_dow;
		if ((time.relative.d < 0 && difference < 0) || (time.relative.d >= 0 && difference <= -time.relative.weekday_behavior)) {
			difference += 7;
		}
		if (time.relative.weekday >= 0) {
			time.d += difference;
		} else {
			time.d -= (7 - (Math.abs(time.relative.weekday) - current_dow));
		}
		time.relative.have_weekday_relative = false;
	}

	private static void do_adjust_relative(timelib_time time)
	{
		if (time.relative.have_weekday_relative) {
			do_adjust_for_weekday(time);
		}
		//timelib_do_normalize(time);
	
		if (time.have_relative) {
			time.us += time.relative.us;
	
			time.s += time.relative.s;
			time.i += time.relative.i;
			time.h += time.relative.h;
	
			time.d += time.relative.d;
			time.m += time.relative.m;
			time.y += time.relative.y;
		}
	
		switch (time.relative.first_last_day_of) {
			case TIMELIB_SPECIAL_FIRST_DAY_OF_MONTH: /* first */
				time.d = 1;
				break;
			case TIMELIB_SPECIAL_LAST_DAY_OF_MONTH: /* last */
				time.d = 0;
				time.m++;
				break;
		}
	
	//??	timelib_do_normalize(time);
	}

	private static void do_adjust_special_weekday(timelib_time time)
	{
		int count, dow, rem;
	
		count = time.relative.special_amount;
		dow = timelib_day_of_week(time.y, time.m, time.d);
	
		/* Add increments of 5 weekdays as a week, leaving the DOW unchanged. */
		time.d += (count / 5) * 7;
	
		/* Deal with the remainder. */
		rem = (count % 5);
	
		if (count > 0) {
			if (rem == 0) {
				/* Head back to Friday if we stop on the weekend. */
				if (dow == 0) {
					time.d -= 2;
				} else if (dow == 6) {
					time.d -= 1;
				}
			} else if (dow == 6) {
				/* We ended up on Saturday, but there's still work to do, so move
				 * to Sunday and continue from there. */
				time.d += 1;
			} else if (dow + rem > 5) {
				/* We're on a weekday, but we're going past Friday, so skip right
				 * over the weekend. */
				time.d += 2;
			}
		} else {
			/* Completely mirror the forward direction. This also covers the 0
			 * case, since if we start on the weekend, we want to move forward as
			 * if we stopped there while going backwards. */
			if (rem == 0) {
				if (dow == 6) {
					time.d += 2;
				} else if (dow == 0) {
					time.d += 1;
				}
			} else if (dow == 0) {
				time.d -= 1;
			} else if (dow + rem < 1) {
				time.d -= 2;
			}
		}
	
		time.d += rem;
	}

	private static void do_adjust_special(timelib_time time)
	{
		if (time.relative.have_special_relative) {
			switch (time.relative.special_type) {
				case TIMELIB_SPECIAL_WEEKDAY:
					do_adjust_special_weekday(time);
					break;
			}
		}
	//	timelib_do_normalize(time);
	//	memset(&(time.relative.special), 0, sizeof(time.relative.special));
	}

	private static void do_adjust_special_early(timelib_time time)
	{
		if (time.relative.have_special_relative) {
			switch (time.relative.special_type) {
				case TIMELIB_SPECIAL_DAY_OF_WEEK_IN_MONTH:
					time.d = 1;
					time.m += time.relative.m;
					time.relative.m = 0;
					break;
				case TIMELIB_SPECIAL_LAST_DAY_OF_WEEK_IN_MONTH:
					time.d = 1;
					time.m += time.relative.m + 1;
					time.relative.m = 0;
					break;
			}
		}
		switch (time.relative.first_last_day_of) {
			case TIMELIB_SPECIAL_FIRST_DAY_OF_MONTH: /* first */
				time.d = 1;
				break;
			case TIMELIB_SPECIAL_LAST_DAY_OF_MONTH: /* last */
				time.d = 0;
				time.m++;
				break;
		}
	//	timelib_do_normalize(time);
	}

	private static int timelib_parse_tz_cor(Dbx_scanner s)
	{
		int begin, end;
		int tmp = 0;
	
		begin = s.ptr;
		while (true) {
			byte b = s.src[s.ptr];
			if ((b >= '0' && b <= '9') || b == ':') {
				++s.ptr;
			}
			else
				break;
		}
		end = s.ptr;
	
		switch (end - begin) {
			case 1: /* H */
			case 2: /* HH */
				tmp = Bry_.To_int_or(s.src, begin, end, Int_.Min_value);
				return tmp * 3600;
			case 3: /* H:M */
			case 4: /* H:MM, HH:M, HHMM */
				if (s.src[begin + 1] == ':') {
					tmp = Bry_.To_int_or(s.src, begin, begin+1, Int_.Min_value) * 3600 +
						Bry_.To_int_or(s.src, begin+2, end, Int_.Min_value) * 60;
					return tmp;
				} else if (s.src[begin + 2] == ':') {
					tmp = Bry_.To_int_or(s.src, begin, begin+2, Int_.Min_value) * 3600 +
						Bry_.To_int_or(s.src, begin+3, end, Int_.Min_value) * 60;
					return tmp;
				} else {
					tmp = Bry_.To_int_or(s.src, begin, end, Int_.Min_value);
					return tmp / 100 * 3600 + (tmp % 100) * 60;
				}
			case 5: /* HH:MM */
				tmp = Bry_.To_int_or(s.src, begin, begin+2, Int_.Min_value) * 3600 +
					Bry_.To_int_or(s.src, begin+3, end, Int_.Min_value) * 60;
				return tmp;
		}
		return 0;
	}

	private static void timelib_update_ts(timelib_time time)
	{
		do_adjust_special_early(time);
		do_adjust_relative(time);
		do_adjust_special(time);
	}

	public static DateAdp Parse(byte[] src) {
		timelib_time parsed = Dbx_strtotime.timelib_strtotime(src);
		// check for errors

		timelib_time now = timelib_now();
		timelib_fill_holes(parsed, now, 0);
		timelib_update_ts(parsed);

		DateAdp dte = DateAdp_.DateByBits(parsed.y, parsed.m, parsed.d, parsed.h, parsed.i, parsed.s, parsed.us, parsed.z, parsed.tz_abbr);
		return dte;
	}
}
