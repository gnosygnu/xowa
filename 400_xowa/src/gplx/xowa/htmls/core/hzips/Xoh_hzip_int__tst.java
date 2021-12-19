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
package gplx.xowa.htmls.core.hzips;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*; import gplx.core.encoders.*;
public class Xoh_hzip_int__tst {
	private final Xoh_hzip_int__fxt fxt = new Xoh_hzip_int__fxt();
	@Test public void Reqd__1() {
		fxt.Test__encode(1,          0,       "!");
		fxt.Test__encode(1,         84,       "u");
		fxt.Test__encode(1,         85,    "{\"!");
		fxt.Test__encode(1,       7225,   "|\"!!");
		fxt.Test__encode(1,     614125,  "}\"!!!");
		fxt.Test__encode(1,   52200625, "~\"!!!!");
	}
	@Test public void Reqd__2() {
		fxt.Test__encode(2,          0,      "!!");
		fxt.Test__encode(2,         84,      "!u");
		fxt.Test__encode(2,         85,     "\"!");
		fxt.Test__encode(2,       7225,   "|\"!!");
		fxt.Test__encode(2,     614125,  "}\"!!!");
		fxt.Test__encode(2,   52200625, "~\"!!!!");
	}
}
class Xoh_hzip_int__fxt {
	private final BryWtr bfr = BryWtr.New();
	private final IntRef count_ref = IntRef.NewNeg1();
	public void Test__encode(int reqd, int val, String expd) {
		Gfo_hzip_int_.Encode(reqd, bfr, val);
		byte[] actl = bfr.ToBryAndClear();
            GfoTstr.EqObj(expd, StringUtl.NewU8(actl));
		GfoTstr.EqObj(val, Gfo_hzip_int_.Decode(reqd, actl, actl.length, 0, count_ref));
	}
}
