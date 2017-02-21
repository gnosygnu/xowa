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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_ignore_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_ignore_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_ignore;}
	public byte Ignore_type() {return ignore_type;} public Xop_ignore_tkn_chkr Ignore_tid_(byte v) {ignore_type = v; return this;} private byte ignore_type = Xop_ignore_tkn.Ignore_tid_null;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_ignore_tkn actl = (Xop_ignore_tkn)actl_obj;
		err += mgr.Tst_val(ignore_type == Xop_ignore_tkn.Ignore_tid_null, path, "ignore_type", ignore_type, actl.Ignore_type());
		return err;
	}
}
