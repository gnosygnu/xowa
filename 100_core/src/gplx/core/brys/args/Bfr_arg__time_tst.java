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
package gplx.core.brys.args; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*;
public class Bfr_arg__time_tst {
	@Test   public void Basic() {
		Time_fmtr_arg_fxt fxt = new Time_fmtr_arg_fxt().Clear();
		fxt.XferAry(      1,             "01s");	// seconds
		fxt.XferAry(     62,         "01m 02s");	// minutes
		fxt.XferAry(   3723,     "01h 02m 03s");	// hours
		fxt.XferAry(  93784, "01d 02h 03m 04s");	// days
		fxt.XferAry(      0,             "00s");	// handle 0 seconds
		fxt.XferAry(   3601,     "01h 00m 01s");	// handle 0 in middle unit
	}
}
class Time_fmtr_arg_fxt {
	public Time_fmtr_arg_fxt Clear() {
		if (arg == null) arg = new Bfr_arg__time();
		return this;
	}	Bfr_arg__time arg;
	public void XferAry(int seconds, String expd) {
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		arg.Seconds_(seconds);
		arg.Bfr_arg__add(bfr);
		Tfds.Eq(expd, bfr.To_str());
	}
}
