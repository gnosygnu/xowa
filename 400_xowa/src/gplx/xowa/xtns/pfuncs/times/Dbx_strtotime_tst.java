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
import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.tests.*;
import gplx.core.btries.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Dbx_strtotime_tst {
//	private final Dbx_strtotime_fxt fxt = new Dbx_strtotime_fxt();
	@Before	public void setup()	  {
		Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));
	}
	@After public void teardown() {
		Datetime_now.Manual_n_();
	}

	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/strtotime.phpt
	@Test public void strtotime() {
//		fxt.Init__format();
//		fxt.Test__strtotime("2005-07-14 22:30:41"    , "2005-07-14T22:30:41+0200");
//		fxt.Test__strtotime("2005-07-14 22:30:41 GMT", "2005-07-15T00:30:41+0200");
//		fxt.Test__strtotime("@1121373041"            , "2005-07-14T22:30:41+0200");
//		fxt.Test__strtotime("@1121373041 CEST"       , "2005-07-14T22:30:41+0200");
//		long x = DateAdp_.parse_iso8561("2005-07-14T22:30:41+0200").Timestamp_unix();
	}

	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/strtotime_basic.phpt
	@Test public void strtotime_basic() {
//		fxt.Init__format("yyyy-MM-dd");
//		fxt.Test__strtotime("1 Monday December 2008"            , "2008-12-01");
//		fxt.Test__strtotime("2 Monday December 2008"            , "2008-12-08");
//		fxt.Test__strtotime("3 Monday December 2008"            , "2008-12-15");
//		fxt.Test__strtotime("first Monday December 2008"        , "2008-12-08");
//		fxt.Test__strtotime("second Monday December 2008"       , "2008-12-15");
//		fxt.Test__strtotime("third Monday December 2008"        , "2008-12-22");
	}
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/date_default_timezone_set-1.phpt
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/timezone-configuration.phpt
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/intl/tests/calendar_isWeekend_basic.phpt
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/intl/tests/calendar_fromDateTime_basic.phpt
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/strtotime_basic2.phpt
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/date/tests/002.phpt
/*
var_dump(date('Y-m-d', strtotime('1 Monday December 2008')));
var_dump(date('Y-m-d', strtotime('2 Monday December 2008')));
var_dump(date('Y-m-d', strtotime('3 Monday December 2008')));
var_dump(date('Y-m-d', strtotime('first Monday December 2008')));
var_dump(date('Y-m-d', strtotime('second Monday December 2008')));
var_dump(date('Y-m-d', strtotime('third Monday December 2008')));
?>
--EXPECT--
string(10) "2008-12-01"
string(10) "2008-12-08"
string(10) "2008-12-15"
string(10) "2008-12-08"
string(10) "2008-12-15"
string(10) "2008-12-22"
 */
	@Test public void Basic() {
//		"{{#time:r|n.d.}}", "Sat, 08 Feb 2020 22:57:12 +0000");
//		fxt.Test__date("n.d.", "Sat, 08 Feb 2020 22:57:12 +0000");
//		fxt.Test__date("30.6.2011-06-30", "Sat, 08 Feb 2020 22:57:12 +0000");		
//		fxt.Test__date("7 May 2013", "2013-05-07 00:00:00");
	}

}
class Dbx_strtotime_fxt {
//	private SimpleDateFormat sdf;
//	private String fmt, tz;
//	public Dbx_strtotime_fxt() {
//		Btrie_slim_mgr trie = Pxd_parser_.Trie();
//        Dbx_scan_support.Init(trie);		
//	}
//	public Dbx_strtotime_fxt Init__format() {return Init__format("yyyy-MM-dd'T'HH:mm:ssZ", "CET");}
//	public Dbx_strtotime_fxt Init__format(String fmt) {return Init__format(fmt, "UTC");}
//	public Dbx_strtotime_fxt Init__format(String fmt, String tz) {
//		this.sdf = new SimpleDateFormat(fmt);
//		sdf.setTimeZone(TimeZone.getTimeZone(tz));
//		this.fmt = fmt;
//		this.tz = tz;
//		return this;
//	}
//	public void Test__date(String raw, String expd) {
//		DateAdp date = Dbx_scan_support.Parse(Bry_.new_u8(raw));
//		Gftest.Eq__str(expd, date.XtoStr_fmt_iso_8561());
//	}	
//	public void Test__strtotime__ary(String[] raw, String[] expd) {
//		int len = raw.length;
//		String[] actl = new String[len];
//		for (int i = 0; i < len; i++) {
//			DateAdp date = Dbx_scan_support.Parse(Bry_.new_u8(raw[i]));
//			actl[i] = To_str(date, sdf);
//		}
//		Gftest.Eq__ary(expd, actl);
//	}
//	public void Test__strtotime(String orig, String expd) {
//		DateAdp date = Dbx_scan_support.Parse(Bry_.new_u8(orig));
//		String actl = To_str(date, sdf);
//		Gftest.Eq__str(expd, actl);
//	}
//	private String To_str(DateAdp date, SimpleDateFormat sdf) {
//		Calendar calendar = date.UnderDateTime();
//
//		// change TZ to same as sdf
////		calendar.setTimeZone(TimeZone.getTimeZone(sdf.getTimeZone().getID()));
//		return sdf.format(calendar.getTime());
///*
//		Instant instant = calendar.toInstant();
//		ZoneId zoneId = ZoneId.of(tz);
//		ZonedDateTime zdt = instant.atZone( zoneId );
//		DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern(fmt).toFormatter();
//		return zdt.format(dtf);
//*/
//	}
}
