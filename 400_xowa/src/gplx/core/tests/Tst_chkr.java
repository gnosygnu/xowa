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
package gplx.core.tests; import gplx.*; import gplx.core.*;
public interface Tst_chkr {
	Class<?> TypeOf();
	int Chk(Tst_mgr mgr, String path, Object actl);
}
class Tst_chkr_null implements Tst_chkr {
	public Class<?> TypeOf() {return Object.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl) {
		mgr.Results().Add(Tst_itm.fail_("!=", path, "<cast type>", "<NULL TYPE>",  Type_adp_.NameOf_obj(actl)));
//			mgr.Results().Add(Tst_itm.fail_("!=", path, "<cast value>", "<NULL VAL>", Object_.Xto_str_strict_or_null(actl)));
		return 1;
	}
}
