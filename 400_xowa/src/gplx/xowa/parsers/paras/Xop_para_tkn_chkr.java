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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_para_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_para_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_para;}
	public byte Para_end() {return para_end;} public Xop_para_tkn_chkr Para_end_(byte v) {para_end = v; return this;} private byte para_end = Byte_.Max_value_127;
	public byte Para_bgn() {return para_bgn;} public Xop_para_tkn_chkr Para_bgn_(byte v) {para_bgn = v; return this;} private byte para_bgn = Byte_.Max_value_127;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_para_tkn actl = (Xop_para_tkn)actl_obj;
		err += mgr.Tst_val(para_end == Byte_.Max_value_127, path, "para_end", para_end, actl.Para_end());
		err += mgr.Tst_val(para_bgn == Byte_.Max_value_127, path, "para_bgn", para_bgn, actl.Para_bgn());
		return err;
	}
}
