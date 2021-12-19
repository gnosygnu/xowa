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
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
public class Xoh_hzip_int_tst {
	private final Xoh_hzip_int_fxt fxt = new Xoh_hzip_int_fxt();
	@Test public void B256__reqd__1() {
		fxt.Init__b256();
		fxt.Test__b256(1,          0		,   0);
		fxt.Test__b256(1,        252		, 252, 252);
		fxt.Test__b256(1,        253		, 252, 253);
		fxt.Test__b256(1,        254		, 252, 254);
		fxt.Test__b256(1,        255		, 252, 255);
		fxt.Test__b256(1,        256		, 253,   1,   0);
		fxt.Test__b256(1,      65535		, 253, 255, 255);
		fxt.Test__b256(1,      65536		, 254,   1,   0,   0);
		fxt.Test__b256(1,   16777216		, 255,   1,   0,   0,   0);
	}
	@Test public void B256__reqd__2() {
		fxt.Init__b256();
		fxt.Test__b256(2,          0		,   0,   0);
		fxt.Test__b256(2,        252		,   0, 252);
		fxt.Test__b256(2,        253		,   0, 253);
		fxt.Test__b256(2,        254		,   0, 254);
		fxt.Test__b256(2,        255		,   0, 255);
		fxt.Test__b256(2,        256		,   1,   0);
		fxt.Test__b256(2,      64511		, 251, 255);
		fxt.Test__b256(2,      64512		, 253, 252,   0);
		fxt.Test__b256(2,      65535		, 253, 255, 255);
		fxt.Test__b256(2,      65536		, 254,   1,   0,   0);
		fxt.Test__b256(2,   16777216		, 255,   1,   0,   0,   0);
	}
	@Test public void B085__reqd__1() {
		fxt.Init__b085();
		fxt.Test__b085(1,          0,       "!");
		fxt.Test__b085(1,         84,       "u");
		fxt.Test__b085(1,         85,    "{\"!");
		fxt.Test__b085(1,       7225,   "|\"!!");
		fxt.Test__b085(1,     614125,  "}\"!!!");
		fxt.Test__b085(1,   52200625, "~\"!!!!");
	}
	@Test public void B085__reqd__2() {
		fxt.Init__b085();
		fxt.Test__b085(2,          0,      "!!");
		fxt.Test__b085(2,         84,      "!u");
		fxt.Test__b085(2,         85,     "\"!");
		fxt.Test__b085(2,       7225,   "|\"!!");
		fxt.Test__b085(2,     614125,  "}\"!!!");
		fxt.Test__b085(2,   52200625, "~\"!!!!");
	}
}
class Xoh_hzip_int_fxt {
	private final BryWtr bfr = BryWtr.New();
	private final IntRef count_ref = IntRef.NewNeg1();
	private final Xoh_hzip_int hzint = new Xoh_hzip_int();
	public void Init__b256() {hzint.Mode_is_b256_(BoolUtl.Y);}
	public void Init__b085() {hzint.Mode_is_b256_(BoolUtl.N);}
	public void Test__b256(int reqd, int val, int... expd_ints) {
		hzint.Encode(reqd, bfr, val);
		byte[] actl = bfr.ToBryAndClear();
		byte[] expd = ByteUtl.AryByInts(expd_ints);
		GfoTstr.EqAry(expd, actl, IntUtl.ToStr(val));
		GfoTstr.EqObj(val, hzint.Decode(reqd, actl, actl.length, 0, count_ref));
	}
	public void Test__b085(int reqd, int val, String expd) {
		hzint.Encode(reqd, bfr, val);
		byte[] actl = bfr.ToBryAndClear();
            GfoTstr.EqObj(expd, StringUtl.NewU8(actl));
		GfoTstr.EqObj(val, hzint.Decode(reqd, actl, actl.length, 0, count_ref));
	}
}
