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
package gplx.core.times; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Time_span__to_str_tst {
	@Test  public void Zero() {
		tst_Default(0, "0");
	}
	@Test  public void MinuteSeconds() {
		tst_Default(77000, "1:17");
	}
	@Test  public void ZeroSuppression() {		 	
		tst_Default(660000, "11:00");	//fractional 0 and leading 0s are suppressed; i.e.: not 00:11:00.000
	}
	@Test  public void HourTest() {
		tst_Default(3723987, "1:02:03.987");
	}
	@Test  public void NegSeconds() {
		tst_Default(-2000, "-2");
	}
	@Test  public void NegMins() {
		tst_Default(-60000, "-1:00");
	}
	@Test  public void NegHours() {
		tst_Default(-3723981, "-1:02:03.981");
	}
	@Test  public void ZeroPadding() {
		tst_ZeroPadding("0", "00:00:00.000");
		tst_ZeroPadding("1:02:03.123", "01:02:03.123");
		tst_ZeroPadding("1", "00:00:01.000");
		tst_ZeroPadding(".987", "00:00:00.987");
		tst_ZeroPadding("2:01.456", "00:02:01.456");
	}
	void tst_Default(long fractionals, String expd) {
		Time_span ts = Time_span_.fracs_(fractionals);
		String actl = ts.To_str(Time_span_.Fmt_Default);
		Tfds.Eq(expd, actl);
	}
	void tst_ZeroPadding(String val, String expd) {
		Time_span timeSpan = Time_span_.parse(val);
		String actl = timeSpan.To_str(Time_span_.Fmt_PadZeros);
		Tfds.Eq(expd, actl);
	}
}
