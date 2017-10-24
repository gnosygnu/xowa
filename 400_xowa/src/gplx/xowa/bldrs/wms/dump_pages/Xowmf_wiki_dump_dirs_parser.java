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
package gplx.xowa.bldrs.wms.dump_pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
public class Xowmf_wiki_dump_dirs_parser {
	public static String[] Parse(byte[] wiki, byte[] src) {
		List_adp rv = List_adp_.New();
		int pos = 0;
		while (true) {
			int href_bgn = Bry_find_.Move_fwd(src, Tkn_href		, pos);			if (href_bgn == Bry_find_.Not_found) break;
			int href_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, href_bgn);	if (href_end == Bry_find_.Not_found) throw Err_.new_wo_type("unable to find date_end", "wiki", wiki, "snip", Bry_.Mid_by_len_safe(src, href_bgn - 3, 25));
			if (src[href_end - 1] != Byte_ascii.Slash) throw Err_.new_wo_type("href should end in slash", "wiki", wiki, "snip", Bry_.Mid_by_len_safe(src, href_bgn - 3, 25));
			pos = href_end + 1;
			byte[] href_bry = Bry_.Mid(src, href_bgn, href_end - 1);
			if (Bry_.Eq(href_bry, Tkn_owner)) continue;	// ignore <a href="../">
			rv.Add(String_.new_u8(href_bry));
		}
		return (String[])rv.To_ary_and_clear(String.class);
	}
	private static final    byte[] Tkn_href = Bry_.new_a7(" href=\""), Tkn_owner = Bry_.new_a7("..");
}
