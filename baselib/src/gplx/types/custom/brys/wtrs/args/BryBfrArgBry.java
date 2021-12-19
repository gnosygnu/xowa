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
public class BryBfrArgBry implements BryBfrArgClearable {
	private int tid;
	private byte[] src; private int src_bgn, src_end;
	private BryBfrArg arg;
	public void SetByMid(byte[] src, int bgn, int end) {this.tid = TidMid; this.src = src; this.src_bgn = bgn; this.src_end = end;}
	public void SetByVal(byte[] src)                   {this.tid = TidVal; this.src = src;}
	public void SetByArg(BryBfrArg arg)                   {this.tid = TidArg; this.arg = arg;}
	@Override public boolean BfrArgIsMissing() {return tid == TidNil;}
	@Override public void BfrArgClear() {
		tid = TidNil;
		src = null; src_bgn = src_end = -1;
		arg = null;
	}
	@Override public void AddToBfr(BryWtr bfr) {
		switch (tid) {
			case TidVal: bfr.Add(src); break;
			case TidMid: bfr.AddMid(src, src_bgn, src_end); break;
			case TidArg: arg.AddToBfr(bfr); break;
			case TidNil: break;
		}
	}

	public static BryBfrArgBry NewEmpty()    {return new BryBfrArgBry();}
	public static BryBfrArgBry New(byte[] v) {BryBfrArgBry rv = new BryBfrArgBry(); rv.SetByVal(v); return rv;}
	private static final int TidNil = 0, TidVal = 1, TidMid = 2, TidArg = 3;
}
