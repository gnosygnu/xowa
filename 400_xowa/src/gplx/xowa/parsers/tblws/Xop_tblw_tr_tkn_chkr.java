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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_tblw_tr_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_tblw_tr_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tblw_tr;}
	public Xop_tblw_tr_tkn_chkr Atrs_rng_(int bgn, int end) {this.atrs_bgn = bgn; this.atrs_end = end; return this;} private int atrs_bgn = Xop_tblw_wkr.Atrs_null, atrs_end = Xop_tblw_wkr.Atrs_null;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_tblw_tr_tkn actl = (Xop_tblw_tr_tkn)actl_obj;
		err += mgr.Tst_val(atrs_bgn == -1, path, "atrs_bgn", atrs_bgn, actl.Atrs_bgn());
		err += mgr.Tst_val(atrs_end == -1, path, "atrs_end", atrs_end, actl.Atrs_end());
		return err;
	}
}
