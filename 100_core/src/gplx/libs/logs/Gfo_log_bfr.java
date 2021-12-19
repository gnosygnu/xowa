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
package gplx.libs.logs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDateNow;
public class Gfo_log_bfr {
	private BryWtr bfr = BryWtr.NewAndReset(255);
	public Gfo_log_bfr Add(String s) {
		bfr.AddStrA7(GfoDateNow.Get().ToUtc().ToStrFmt_yyyyMMdd_HHmmss_fff());
		bfr.AddByteSpace();
		bfr.AddStrU8(s);
		bfr.AddByteNl();
		return this;
	}
	public String Xto_str() {return bfr.ToStrAndClear();}
}
