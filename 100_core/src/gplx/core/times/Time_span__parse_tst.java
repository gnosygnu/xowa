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
public class Time_span__parse_tst {
	@Test  public void Zero() {
		tst_Parse("0", 0);
	}
	@Test  public void Milliseconds() {
		tst_Parse("0.987", 987);
		tst_Parse("0.00199", 1);		// do not parse as 199
		tst_Parse("0.1", 100);			// do not parse as 1
	}
	@Test  public void Seconds() {
		tst_Parse("1.987", 1987);
	}
	@Test  public void Minutes() {
		tst_Parse("1:02.987", 62987);
	}
	@Test  public void MinuteSecondOnly() {
		tst_Parse("1:02", 62000);
	}
	@Test  public void Hour() {
		tst_Parse("1:02:03.987", 3723987);
	}
	@Test  public void Negative() {
		tst_Parse("-1:02:03.987", -3723987);
	}
	@Test  public void Loopholes() {
		tst_Parse("001:02", 62000);					// multiple leading zeroes
		tst_Parse("1.2.3.4", 1200);					// ignore all decimals except first
		tst_Parse("60:60.9999", 3660999);			// value does not need to be bounded to limits (except fracs, which is always < 1000)
		tst_Parse(" 01 : 02 : 03 . 987", 3723987);	// whitespace
	}
	void tst_Parse(String text, long expd) {
		Time_span val = Time_span_.parse(text);
		Tfds.Eq(expd, val.Fracs());
	}
}
