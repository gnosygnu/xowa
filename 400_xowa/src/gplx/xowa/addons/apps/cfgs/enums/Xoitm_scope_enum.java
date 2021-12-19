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
package gplx.xowa.addons.apps.cfgs.enums;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Xoitm_scope_enum {	// SERIALIZED
	public static final int Tid__app = 1, Tid__wiki = 2;
	public static int To_int(String raw) {
		if		(StringUtl.Eq(raw, "app"))	return Tid__app;
		else if (StringUtl.Eq(raw, "wiki"))	return Tid__wiki;
		else								throw ErrUtl.NewUnhandled(raw);
	}
}
