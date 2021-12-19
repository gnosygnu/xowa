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
package gplx.xowa.xtns.pfuncs.times;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDateNow;
import gplx.xowa.*;
import gplx.xowa.langs.msgs.*;
class Pft_func_time_foreign_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		GfoDateNow.ManualSet(GfoDateUtl.New(2012, 1, 2, 3, 4, 5, 6));
	}
	public void Term() {
		GfoDateNow.ManualSetN();
	}
	public Pft_func_time_foreign_fxt Init_msg(String key, String val) {
		Xol_msg_itm msg = fxt.Wiki().Msg_mgr().Get_or_make(BryUtl.NewU8(key));
		msg.Atrs_set(BryUtl.NewU8(val), false, false);
		return this;
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", expd);
	}
	public void Test_Roman(int v, String expd) {
		BryWtr bfr = BryWtr.NewWithSize(16);
		Pfxtp_roman.ToRoman(v, bfr);
		String actl = bfr.ToStrAndClear();
		GfoTstr.EqObj(expd, actl);
	}
}
