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
import org.junit.*;
public class TimeSpanAdp_xtoStr_tst {
	@Test public void Zero() {
		tst_Default(0, "0");
	}
	@Test public void MinuteSeconds() {
		tst_Default(77000, "1:17");
	}
	@Test public void ZeroSuppression() {		 	
		tst_Default(660000, "11:00");	//fractional 0 and leading 0s are suppressed; i.e.: not 00:11:00.000
	}
	@Test public void HourTest() {
		tst_Default(3723987, "1:02:03.987");
	}
	@Test public void NegSeconds() {
		tst_Default(-2000, "-2");
	}
	@Test public void NegMins() {
		tst_Default(-60000, "-1:00");
	}
	@Test public void NegHours() {
		tst_Default(-3723981, "-1:02:03.981");
	}
	@Test public void ZeroPadding() {
		tst_ZeroPadding("0", "00:00:00.000");
		tst_ZeroPadding("1:02:03.123", "01:02:03.123");
		tst_ZeroPadding("1", "00:00:01.000");
		tst_ZeroPadding(".987", "00:00:00.987");
		tst_ZeroPadding("2:01.456", "00:02:01.456");
	}
	void tst_Default(long fractionals, String expd) {
		TimeSpanAdp ts = TimeSpanAdp_.fracs_(fractionals);
		String actl = ts.XtoStr(TimeSpanAdp_.Fmt_Default);
		Tfds.Eq(expd, actl);
	}
	void tst_ZeroPadding(String val, String expd) {
		TimeSpanAdp timeSpan = TimeSpanAdp_.parse_(val);
		String actl = timeSpan.XtoStr(TimeSpanAdp_.Fmt_PadZeros);
		Tfds.Eq(expd, actl);
	}
}
