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
public class Bry_fmtr_eval_mgr_ {
	public static Io_url Eval_url(Bry_fmtr_eval_mgr eval_mgr, byte[] fmt) {
		if (eval_mgr == null) return Io_url_.new_any_(String_.new_u8(fmt));
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		Bry_fmtr fmtr = Bry_fmtr.New__tmp();
		fmtr.Eval_mgr_(eval_mgr).Fmt_(fmt).Bld_bfr_none(bfr);
		return Io_url_.new_any_(bfr.To_str_and_clear());
	}
}
