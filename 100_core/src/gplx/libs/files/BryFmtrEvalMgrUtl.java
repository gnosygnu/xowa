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
package gplx.libs.files;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtrEvalMgr;
import gplx.types.basics.utls.StringUtl;
public class BryFmtrEvalMgrUtl {
	public static Io_url Eval_url(BryFmtrEvalMgr eval_mgr, byte[] fmt) {
		if (eval_mgr == null) return Io_url_.new_any_(StringUtl.NewU8(fmt));
		BryWtr bfr = BryWtr.NewAndReset(255);
		BryFmtr fmtr = BryFmtr.NewTmp();
		fmtr.EvalMgrSet(eval_mgr).FmtSet(fmt).BldToBfrNone(bfr);
		return Io_url_.new_any_(bfr.ToStrAndClear());
	}
}
