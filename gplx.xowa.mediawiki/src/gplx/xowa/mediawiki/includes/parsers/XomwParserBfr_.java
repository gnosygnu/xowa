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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class XomwParserBfr_ {
	public static void Replace(XomwParserBfr pbfr, byte[] find, byte[] repl) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();

		if (Replace(bfr, Bool_.N, src, src_bgn, src_end, find, repl) != null)
			pbfr.Switch();
	}
	private static byte[] Replace(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end, byte[] find, byte[] repl) {
		boolean dirty = false;
		int cur = src_bgn;
		boolean called_by_bry = bfr == null;

		while (true) {
			int find_bgn = Bry_find_.Find_fwd(src, find, cur);
			if (find_bgn == Bry_find_.Not_found) {
				if (dirty)
					bfr.Add_mid(src, cur, src_end);
				break;
			}
			if (called_by_bry) bfr = Bry_bfr_.New();
			bfr.Add_mid(src, cur, find_bgn);
			cur += find.length;
			dirty = true;
		}

		if (dirty) {
			if (called_by_bry)
				return bfr.To_bry_and_clear();
			else
				return Bry_.Empty;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
			else {
				if (lone_bfr)
					bfr.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}
}
