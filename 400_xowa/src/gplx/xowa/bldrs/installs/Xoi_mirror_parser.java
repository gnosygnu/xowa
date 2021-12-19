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
package gplx.xowa.bldrs.installs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
public class Xoi_mirror_parser {
	public String[] Parse(String raw_str) {
		if (StringUtl.IsNullOrEmpty(raw_str)) return StringUtl.AryEmpty;
		byte[] raw = BryUtl.NewU8(raw_str);
		List_adp rv = List_adp_.New();
		int pos = 0;
		while (true) {
			int bgn = BryFind.FindFwd(raw, CONST_href_bgn, pos); if (bgn == BryFind.NotFound) break;
			bgn += CONST_href_bgn.length;			
			int end = BryFind.FindFwd(raw, CONST_href_end, bgn); if (end == BryFind.NotFound) return StringUtl.AryEmpty;
			byte[] date = BryLni.Mid(raw, bgn, end);
			pos = end + CONST_href_end.length;			
			if (BryLni.Eq(date, CONST_date_parent_dir)) continue;
			int date_pos_last = date.length - 1;
			if (date_pos_last == -1) return StringUtl.AryEmpty;
			if (date[date_pos_last] == AsciiByte.Slash) date = BryLni.Mid(date, 0, date_pos_last);	// trim trailing /; EX: "20130101/" -> "20130101"
			rv.Add(StringUtl.NewU8(date));
		}
		return rv.ToStrAry();
	}	private static final byte[] CONST_href_bgn = BryUtl.NewA7("<a href=\""), CONST_href_end = BryUtl.NewA7("\""), CONST_date_parent_dir = BryUtl.NewA7("../");
	public static String Find_last_lte(String[] ary, String comp) {	// assuming sorted ary, find last entry that is lte comp
		int len = ary.length;
		for (int i = len - 1; i > -1; i--) {
			String itm = ary[i];			
			if (CompareAbleUtl.Is(CompareAbleUtl.Less_or_same, itm, comp)) return itm;
		}
		return "";
	}
}
