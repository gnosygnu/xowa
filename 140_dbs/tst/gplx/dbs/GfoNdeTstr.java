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
package gplx.dbs; import gplx.*;
import gplx.core.gfo_ndes.*;
public class GfoNdeTstr {
	public static void tst_ValsByCol(GfoNde nde, String fld, Object... expdAry) {
		List_adp expd = List_adp_.New();
		for (int i = 0; i < expdAry.length; i++) {
			expd.Add(Object_.Xto_str_strict_or_empty(expdAry[i]));
		}
		List_adp actl = List_adp_.New();
		for (int i = 0; i < nde.Subs().Count(); i++) {
			GfoNde sub = nde.Subs().FetchAt_asGfoNde(i);
			actl.Add(Object_.Xto_str_strict_or_empty(sub.Read(fld)));
		}
		Tfds.Eq_ary(expd.To_str_ary(), actl.To_str_ary());
	}
}