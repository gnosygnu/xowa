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
package gplx.core.primitives;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class Int_2_val {
	public Int_2_val(int v0, int v1) {val_0 = v0; val_1 = v1;}
	public int Val_0() {return val_0;} final int val_0;
	public int Val_1() {return val_1;} final int val_1;
	public String Xto_str(BryWtr bfr) {return Xto_str(bfr, val_0, val_1);}
	public static final Int_2_val Null_ptr = null;
	public static Int_2_val parse(String raw) {
		String[] itms = StringUtl.Split(raw, ',');
		if (itms.length != 2) return Null_ptr;
		int v0 = IntUtl.ParseOr(itms[0], IntUtl.MinValue); if (v0 == IntUtl.MinValue) return Null_ptr;
		int v1 = IntUtl.ParseOr(itms[1], IntUtl.MinValue); if (v1 == IntUtl.MinValue) return Null_ptr;
		return new Int_2_val(v0, v1);
	}
	public static String Xto_str(BryWtr bfr, int x, int y) {return bfr.AddIntVariable(x).AddByteComma().AddIntVariable(y).ToStrAndClear();}
}
