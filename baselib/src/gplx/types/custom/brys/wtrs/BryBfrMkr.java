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
import gplx.libs.ios.IoConsts;
import gplx.types.errs.ErrUtl;
public class BryBfrMkr {
	public BryWtr GetB128() {return mkrB128.Get();} private final BryBfrMkrMgr mkrB128 = new BryBfrMkrMgr(TidB128, 128);
	public BryWtr GetB512() {return mkrB512.Get();} private final BryBfrMkrMgr mkrB512 = new BryBfrMkrMgr(TidB512, 512);
	public BryWtr GetK004() {return mkrK004.Get();} private final BryBfrMkrMgr mkrK004 = new BryBfrMkrMgr(TidK004, 4 * IoConsts.LenKB);
	public BryWtr GetM001() {return mkrM001.Get();} private final BryBfrMkrMgr mkrM001 = new BryBfrMkrMgr(TidM001, 1 * IoConsts.LenMB);
	public void Clear() {
		for (byte i = TidB128; i <= TidM001; i++)
			NewMkr(i).Clear();
	}
	public void ClearFailCheck() {
		for (byte i = TidB128; i <= TidM001; i++)
			NewMkr(i).Clear_fail_check();
	}
	private BryBfrMkrMgr NewMkr(byte tid) {
		switch (tid) {
			case TidB128:     return mkrB128;
			case TidB512:     return mkrB512;
			case TidK004:     return mkrK004;
			case TidM001:     return mkrM001;
			default:          throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static final byte TidB128 = 0, TidB512 = 1, TidK004 = 2, TidM001 = 3;
}
