/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import java.util.GregorianCalendar;
public class GfoDateNow {
	private static final GfoDate dflt = GfoDateUtl.ParseGplx("2001-01-01 00:00:00.000");
	public static GfoDate DfltAddMin(int v) {return dflt.AddMinute(v);}
	public static void AutoincrementSetN() {autoincrement = false;} private static boolean autoincrement = true;
	public static void ManualSet(GfoDate v) {manual = v;} private static GfoDate manual;
	public static void ManualSetY() {manual = dflt;}
	public static void ManualSetN() {
		manual = null;
		autoincrement = true;
	}
	public static void ManualSetAndFreeze(GfoDate v) {
		manual = v;
		autoincrement = false;
	}
	public static GfoDate Get() {
		if (manual == null)
			return new GfoDate(new GregorianCalendar());
		GfoDate rv = manual;
		if (autoincrement) manual = rv.AddMinute(1); // simulate passage of time by increasing manual by 1 minute with each call
		return rv;
	}
	public static GfoDate GetForce() {return new GfoDate(new GregorianCalendar());} // ignore manual and force get of real time
}
