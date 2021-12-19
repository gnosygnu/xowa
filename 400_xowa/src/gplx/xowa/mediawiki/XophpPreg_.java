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
package gplx.xowa.mediawiki;
import gplx.types.basics.strings.unicodes.Utf8Utl;
import gplx.types.basics.utls.BryLni;
import gplx.core.btries.*; import gplx.core.brys.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.lists.IntList;
public class XophpPreg_ {
	public static byte[][] split(IntList list, byte[] src, int src_bgn, int src_end, byte[] dlm, boolean extend) {
		// find delimiters
		int dlm_len = dlm.length;
		byte dlm_nth = dlm[dlm_len - 1];
		int i = src_bgn;
		list.Add(src_bgn);
		while (true) {
			if (i == src_end) break;
			int dlm_end = i + dlm_len;
			if (dlm_end <= src_end && BryLni.Eq(src, i, dlm_end, dlm)) {
				if (extend) {
					dlm_end = BryFind.FindFwdWhile(src, i, src_end, dlm_nth);
				}
				list.Add(i);
				list.Add(dlm_end);
				i = dlm_end;
			}
			else
				i++;
		}
		list.Add(src_end);

		// create brys
		int rv_len = list.Len() - 1;
		if (rv_len == 1) {
			list.Clear();
			return null;
		}
		if (list.GetAt(list.Len() - 2) == src_end) {	// if 2nd to last elem == src_end, then last item is Bry_.Empty; ignore it; EX: "a''" -> "a", "''" x> "a", "''", ""
			rv_len--;
		}
		byte[][] rv = new byte[rv_len][];
		for (i = 0; i < rv_len; i += 2) {
			rv[i    ] = BryLni.Mid(src, list.GetAt(i + 0), list.GetAt(i + 1));
			if (i + 1 == rv_len) break;
			rv[i + 1] = BryLni.Mid(src, list.GetAt(i + 1), list.GetAt(i + 2));
		}
		list.Clear();
		return rv;
	}
	public static Object match(Btrie_slim_mgr trie, Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		trv.Match_bgn = -1;
		int cur = src_bgn;
		while (cur < src_end) {
			byte b = src[cur];
			Object o = trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null)
				cur += Utf8Utl.LenOfCharBy1stByte(b);
			else {
				trv.Match_bgn = cur;
				return o;
			}
		}
		return null;
	}
	
	public static void replace(Bry_tmp bry, BryWtr tmp, Btrie_slim_mgr find_trie, Btrie_rv trv, byte[] repl_bry) {
		byte[] src = bry.src;
		int src_bgn = bry.src_bgn;
		int src_end = bry.src_end;
 
		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;

		while (true) {
			// eos
			if (cur == src_end) {
				if (dirty) {
					tmp.AddMid(src, prv, src_end);
				}
				break;
			}

			byte b = src[cur];
			Object o = find_trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null) {
				cur += Utf8Utl.LenOfCharBy1stByte(b);
			}
			else {
				dirty = true;
				tmp.AddMid(src, prv, cur);
				tmp.Add(repl_bry);
				cur = trv.Pos();
				prv = cur;
			}
		}

		if (dirty) {
			bry.Set_by_bfr(tmp);
		}
	}
}
