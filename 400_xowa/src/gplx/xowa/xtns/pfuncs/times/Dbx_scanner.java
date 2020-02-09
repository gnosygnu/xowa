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
class Dbx_scanner {
	//int           fd;
	//uchar        *lim, *str, *ptr, *cur, *tok, *pos;
	public int lim;
	public byte[] src;
	public int src_len;
	public int ptr;
	public int cur;
	public int tok;
	public int pos;
	public int  line, len;
        
        public int state;
        public int cursor;
        public int yyaccept;
        public int yych;
        
	public timelib_error_container errors;

	public timelib_time        time;
	//const timelib_tzdb  *tzdb;

	int length;
	int behavior;
	public int tz_not_found;

	public Dbx_scanner(byte[] srcx, int maxfill) {
		errors = new timelib_error_container();
		time = new timelib_time();
		int slen = srcx.length;
		lim = slen + maxfill;
		src = new byte[lim];
		int i;
		for (i = 0; i < lim; i++)
			src[i] = 0;
		for (i = 0; i < slen; i++)
			src[i] = srcx[i];
		
		//src = Bry_.Add(srcx, Byte_ascii.Null);
		src_len = slen;
		ptr = 0;
		cur = 0;
		tok = 0;
		pos = 0;
		line = 0;
	}
}

class timelib_error_container {
	public byte[] error_messages;
	public byte[] warning_messages;
	public int                    error_count;
	public int                    warning_count;
	public timelib_error_container() {
		warning_count = 0;
		warning_messages = null;
		error_count = 0;
		error_messages = null;
	}
}

class timelib_time {
	public int      y, m, d;     /* Year, Month, Day */
	public int      h, i, s;     /* Hour, mInute, Second */
	public int      us;          /* Microseconds */
	public int              z;           /* UTC offset in seconds */
	public byte[]            tz_abbr;     /* Timezone abbreviation (display only) */
	//timelib_tzinfo  *tz_info;     /* Timezone structure */
	public int       dst;         /* Flag if we were parsing a DST zone */
	public timelib_rel_time relative;

	public int      sse;         /* Seconds since epoch */

	public boolean   have_date, have_relative, have_weeknr_day;
	public int have_time;
	public int have_zone;

	public boolean   sse_uptodate; /* !0 if the sse member is up to date with the date/time members */
	public boolean   tim_uptodate; /* !0 if the date/time members are up to date with the sse member */
	public boolean   is_localtime; /*  1 if the current struct represents localtime, 0 if it is in GMT */
	public int   zone_type;    /*  1 time offset,
	                              *  3 TimeZone identifier,
	                              *  2 TimeZone abbreviation */
	public timelib_time() {
		//in.time = timelib_time_ctor();
		y = Dbx_scan_support.TIMELIB_UNSET;
		d = Dbx_scan_support.TIMELIB_UNSET;
		m = Dbx_scan_support.TIMELIB_UNSET;
		h = Dbx_scan_support.TIMELIB_UNSET;
		i = Dbx_scan_support.TIMELIB_UNSET;
		s = Dbx_scan_support.TIMELIB_UNSET;
		us = Dbx_scan_support.TIMELIB_UNSET;
		z = Dbx_scan_support.TIMELIB_UNSET;
		dst = Dbx_scan_support.TIMELIB_UNSET;
		//in.tzdb = tzdb;
		is_localtime = false;
		zone_type = 0;
		relative = new timelib_rel_time();
		relative.days = Dbx_scan_support.TIMELIB_UNSET;
	}
}
class timelib_rel_time {
	public int y, m, d; /* Years, Months and Days */
	public int h, i, s; /* Hours, mInutes and Seconds */
	public int us;      /* Microseconds */

	public int weekday; /* Stores the day in 'next monday' */
	public int weekday_behavior; /* 0: the current day should *not* be counted when advancing forwards; 1: the current day *should* be counted */

	public int first_last_day_of;
	public int invert; /* Whether the difference should be inverted */
	public int days; /* Contains the number of *days*, instead of Y-M-D differences */

	public int special_type;
	public int special_amount;

	public boolean   have_weekday_relative, have_special_relative;
}
class timelib_relunit {
	public byte[] name;
	public int         unit;
	public int         multiplier;
}
