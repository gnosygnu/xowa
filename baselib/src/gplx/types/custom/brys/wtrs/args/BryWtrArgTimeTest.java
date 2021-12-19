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
package gplx.types.custom.brys.wtrs.args;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class BryWtrArgTimeTest {
	@Test public void Basic() {
		BfrArgTimeTstr tstr = new BfrArgTimeTstr().Clear();
		tstr.AddToBfr(      1,             "01s");    // seconds
		tstr.AddToBfr(     62,         "01m 02s");    // minutes
		tstr.AddToBfr(   3723,     "01h 02m 03s");    // hours
		tstr.AddToBfr(  93784, "01d 02h 03m 04s");    // days
		tstr.AddToBfr(      0,             "00s");    // handle 0 seconds
		tstr.AddToBfr(   3601,     "01h 00m 01s");    // handle 0 in middle unit
	}
}
class BfrArgTimeTstr {
	private BryBfrArgTime arg;
	public BfrArgTimeTstr Clear() {
		if (arg == null) arg = new BryBfrArgTime();
		return this;
	}
	public void AddToBfr(int seconds, String expd) {
		BryWtr bfr = BryWtr.NewAndReset(255);
		arg.SecondsSet(seconds);
		arg.AddToBfr(bfr);
		GfoTstr.Eq(expd, bfr.ToStr());
	}
}
