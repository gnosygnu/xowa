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
import gplx.types.basics.utls.IntUtl;
public class BryBfrArgInt implements BryBfrArg {
	private int val, valDigits;
	public BryBfrArgInt(int v) {Set(v);}
	public void Set(int v) {
		this.val = IntUtl.Cast(v);
		this.valDigits = IntUtl.CountDigits(val);
	}
	@Override public void AddToBfr(BryWtr bfr) {bfr.AddIntDigits(valDigits, val);}
}
