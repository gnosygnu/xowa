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
import gplx.core.btries.*;
public class Xomw_regex_ {
	public static int Find_fwd_while(Btrie_slim_mgr trie, Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		int cur = src_bgn;
		while (cur < src_end) {
			byte b = src[cur];
			Object o = trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null)
				break;
			else
				cur += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
		}
		return cur;
	}
	public static int Find_fwd_until(Btrie_slim_mgr trie, Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		int cur = src_bgn;
		while (cur < src_end) {
			byte b = src[cur];
			Object o = trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null)
				cur += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
			else
				break;
		}
		return cur;
	}
}
