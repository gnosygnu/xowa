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
package gplx.types.custom.brys.fmts.itms;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
public class BryBfrArgFmtr implements BryBfrArg {
	private BryFmtr fmtr; private Object[] arg_ary;
	public BryBfrArgFmtr(BryFmtr fmtr, Object[] arg_ary) {SetByArgs(fmtr, arg_ary);}
	public BryBfrArgFmtr SetByArgs(BryFmtr fmtr, Object... arg_ary) {
		this.fmtr = fmtr;
		this.arg_ary = arg_ary;
		return this;
	}
	@Override public void AddToBfr(BryWtr bfr) {fmtr.BldToBfrMany(bfr, arg_ary);}

	public static BryBfrArgFmtr New()    {return new BryBfrArgFmtr(null, null);}
}
