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
package gplx.xowa.bldrs.wms.dump_pages;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Xowmf_wiki_dump_dirs_parser {
	public static String[] Parse(byte[] wiki, byte[] src) {
		List_adp rv = List_adp_.New();
		int pos = 0;
		while (true) {
			int href_bgn = BryFind.MoveFwd(src, Tkn_href		, pos);			if (href_bgn == BryFind.NotFound) break;
			int href_end = BryFind.FindFwd(src, AsciiByte.Quote, href_bgn);	if (href_end == BryFind.NotFound) throw ErrUtl.NewArgs("unable to find date_end", "wiki", wiki, "snip", BryUtl.MidByLenSafe(src, href_bgn - 3, 25));
			if (src[href_end - 1] != AsciiByte.Slash) throw ErrUtl.NewArgs("href should end in slash", "wiki", wiki, "snip", BryUtl.MidByLenSafe(src, href_bgn - 3, 25));
			pos = href_end + 1;
			byte[] href_bry = BryLni.Mid(src, href_bgn, href_end - 1);
			if (BryLni.Eq(href_bry, Tkn_owner)) continue;	// ignore <a href="../">
			rv.Add(StringUtl.NewU8(href_bry));
		}
		return (String[])rv.ToAryAndClear(String.class);
	}
	private static final byte[] Tkn_href = BryUtl.NewA7(" href=\""), Tkn_owner = BryUtl.NewA7("..");
}
