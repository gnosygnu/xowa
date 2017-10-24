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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
class Scrib_err {
	public static Err Make__err__arg(String proc, int arg_idx, String actl, String expd) {
		String rv = String_.Format("bad argument #{0} to {1} ({2} expected, got {3})", arg_idx, proc, expd, actl);	// "bad argument #$argIdx to '$name' ($expectType expected, got $type)"
		return Err_.new_("scribunto", rv);
	}
}
