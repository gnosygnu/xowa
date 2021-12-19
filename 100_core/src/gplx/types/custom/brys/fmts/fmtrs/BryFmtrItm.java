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
package gplx.types.custom.brys.fmts.fmtrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
class BryFmtrItm {
	private BryFmtrItm(boolean isIdx, int idx, byte[] data) {
		this.isIdx = isIdx;
		this.idx = idx;
		this.data = data;
	}
	public boolean IsIdx() {return isIdx;} private final boolean isIdx;
	public int Idx() {return idx;} private final int idx;
	public byte[] Data() {return data;} private final byte[] data;
	public static BryFmtrItm NewIdx(int idx)                   {return new BryFmtrItm(true , idx   , BryUtl.Empty);}
	public static BryFmtrItm NewData(byte[] bry)               {return new BryFmtrItm(false, -1, bry);}
	public static BryFmtrItm NewDataByMid(byte[] dat, int len) {return new BryFmtrItm(false, -1, BryLni.Mid(dat, 0, len));}
}
