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
package gplx;
import java.util.GregorianCalendar;
public class Datetime_now {
	private static final    DateAdp dflt = DateAdp_.parse_gplx("2001-01-01 00:00:00.000");
	private static DateAdp manual;
	private static boolean autoincrement = true;
	public static void Manual_y_() {
		manual = dflt;
	}
	public static void Manual_n_() {
		manual = null;
		autoincrement = true;
	}
	public static void Manual_and_freeze_(DateAdp v) {
		manual = v;
		autoincrement = false;
	}
	public static void Manual_(DateAdp v) {
		manual = v;
	}
	public static void Autoincrement_n_() {
		autoincrement = false;
	}
	public static DateAdp Dflt_add_min_(int v) {
		return dflt.Add_minute(v);
	}

	public static DateAdp Get() {
		if (manual == null) return new DateAdp(new GregorianCalendar());
		DateAdp rv = manual;
		if (autoincrement) manual = rv.Add_minute(1);		// simulate passage of manual by increasing manual by 1 minute with each call
		return rv;
	}
	public static DateAdp Get_force() {	// ignore manual and force get of real time
		return new DateAdp(new GregorianCalendar());
	}
//		private static final    DateAdp manual_time_dflt = DateAdp_.parse_gplx("2001-01-01 00:00:00.000");
//		private static DateAdp manual_time;
//		static boolean Now_enabled() {return now_enabled;} private static boolean now_enabled;
//		static void Now_enabled_y_() {now_enabled = Bool_.Y; manual_time = manual_time_dflt;}
//		static void Now_enabled_n_() {now_enabled = Bool_.N; now_freeze = false;}
	// public static void Now_set(DateAdp date) {now_enabled = true; manual_time = date;}
	// public static void Now_freeze_y_() {now_freeze = true;} 
//		private static boolean now_freeze;
	// public static DateAdp Now_time0_add_min(int minutes) {return manual_time_dflt.Add_minute(minutes);}
//		@gplx.Internal protected static DateAdp Now() {
//			DateAdp rv = manual_time;
//			if (!now_freeze) manual_time = rv.Add_minute(1);
//			return rv;
//		}

}