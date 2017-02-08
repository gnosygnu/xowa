/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
