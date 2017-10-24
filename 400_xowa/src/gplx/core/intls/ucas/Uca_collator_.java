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
package gplx.core.intls.ucas; import gplx.*; import gplx.core.*; import gplx.core.intls.*;
public class Uca_collator_ {
	public static Uca_collator New(String locale, boolean numeric_ordering) {
		Uca_collator rv = new Uca_collator__icu__4_8();
		rv.Init(locale, numeric_ordering);
		return rv;
	}
}
