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