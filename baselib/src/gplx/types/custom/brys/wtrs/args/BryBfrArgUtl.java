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
import gplx.types.basics.utls.BryUtl;
public class BryBfrArgUtl {
	public static final BryBfrArg Noop = new BryBfrArgNoop();
	public static BryBfrArgInt NewInt(int v)    {return new BryBfrArgInt(v);}
	public static BryBfrArgBry NewBry(String v) {return BryBfrArgBry.New(BryUtl.NewU8(v));}
	public static BryBfrArgBry NewBry(byte[] v) {return BryBfrArgBry.New(v);}
	public static void Clear(BryBfrArgClearable... ary) {
		for (BryBfrArgClearable arg : ary)
			arg.BfrArgClear();
	}
}
class BryBfrArgNoop implements BryBfrArg {
	public void AddToBfr(BryWtr bfr) {}
}
