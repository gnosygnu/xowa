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
package gplx.types.custom.brys.wtrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
public class BryRef implements BryBfrArg {
	public int Bgn() {return bgn;} private int bgn;
	public int End() {return end;} private int end;
	public byte[] Val() {return val;} private byte[] val;
	public boolean ValIsEmpty() {return bgn == end;}
	public BryRef ValSet(byte[] val)                                {this.val = val; this.bgn = 0;       this.end = val == null ? 0 : val.length; return this;}
	public BryRef ValSetByMid(byte[] val, int val_bgn, int val_end) {this.val = val; this.bgn = val_bgn; this.end = val_end; return this;}
	public void AddToBfr(BryWtr bfr) {bfr.AddMid(val, bgn, end);}
	@Override public int hashCode() {return CalcHashCode(val, bgn, end);}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;    // NOTE: strange, but null check needed; throws null error; EX.WP: File:Eug�ne Delacroix - La libert� guidant le peuple.jpg
		BryRef comp = (BryRef)obj;
		return BryLni.Eq(val, bgn, end, comp.val, comp.bgn, comp.end);
	}

	public static BryRef NewEmpty()      {return New(BryUtl.Empty);}
	public static BryRef New(byte[] val) {return new BryRef().ValSet(val);}
	public static BryRef New(String val) {return new BryRef().ValSet(BryUtl.NewU8(val));}
	public static int CalcHashCode(byte[] ary, int bgn, int end) {
		int rv = 0;
		for (int i = bgn; i < end; i++)
			rv = (31 * rv) + ary[i];
		return rv;
	}
}
