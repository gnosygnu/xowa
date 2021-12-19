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
package gplx.xowa.parsers.htmls;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.xowa.parsers.xndes.*;
public class Mwh_doc_wkr_ {
	public static Hash_adp_bry Nde_regy__mw() {
		Xop_xnde_tag[] ary = Xop_xnde_tag_.Ary;
		int len = ary.length;
		Hash_adp_bry rv = Hash_adp_bry.ci_a7();
		for (int i = 0; i < len; ++i) {
			Xop_xnde_tag itm = ary[i];
			rv.Add(itm.Name_bry(), itm);
		}
		return rv;
	}
}
