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
package gplx.core.brys.fmtrs; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
public class Bry_fmtr_itm {
	Bry_fmtr_itm(boolean arg, int argIdx, byte[] dat) {
		this.Arg = arg; this.ArgIdx = argIdx; this.Dat = dat;
	}
	public boolean Arg;
	public int ArgIdx;
	public byte[] Dat;
	public String DatStr() {
		if (datStr == null) datStr = String_.new_u8(Dat);
		return datStr;
	}	String datStr;
	public static Bry_fmtr_itm arg_(int idx) {return new Bry_fmtr_itm(true, idx, Bry_.Empty);}
	public static Bry_fmtr_itm dat_(byte[] dat, int len) {return new Bry_fmtr_itm(false, -1, Bry_.Mid(dat, 0, len));}
	public static Bry_fmtr_itm dat_bry_(byte[] bry) {return new Bry_fmtr_itm(false, -1, bry);}
}
