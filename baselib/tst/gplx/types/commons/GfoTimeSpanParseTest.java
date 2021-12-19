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
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoTimeSpanParseTest {
	@Test public void Zero() {
		TestParse("0", 0);
	}
	@Test public void Milliseconds() {
		TestParse("0.987", 987);
		TestParse("0.00199", 1);        // do not parse as 199
		TestParse("0.1", 100);            // do not parse as 1
	}
	@Test public void Seconds() {
		TestParse("1.987", 1987);
	}
	@Test public void Minutes() {
		TestParse("1:02.987", 62987);
	}
	@Test public void MinuteSecondOnly() {
		TestParse("1:02", 62000);
	}
	@Test public void Hour() {
		TestParse("1:02:03.987", 3723987);
	}
	@Test public void Negative() {
		TestParse("-1:02:03.987", -3723987);
	}
	@Test public void Loopholes() {
		TestParse("001:02", 62000);                    // multiple leading zeroes
		TestParse("1.2.3.4", 1200);                    // ignore all decimals except first
		TestParse("60:60.9999", 3660999);            // value does not need to be bounded to limits (except fracs, which is always < 1000)
		TestParse(" 01 : 02 : 03 . 987", 3723987);    // whitespace
	}
	private void TestParse(String text, long expd) {
		GfoTimeSpan val = GfoTimeSpanUtl.Parse(text);
		GfoTstr.EqLong(expd, val.Fracs());
	}
}
