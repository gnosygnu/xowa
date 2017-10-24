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
public class Xomw_regex_url {
	private final    Btrie_slim_mgr trie;
	public Xomw_regex_url(Xomw_regex_space regex_space) {
		//       [^][<>"\\x00-\\x20\\x7F\|]
		// REGEX:[^][<>"\\x00-\\x20\\x7F\p{Zs}]; NOTE: val is just a marker
		this.trie = Btrie_slim_mgr.cs();
		trie.Add_str_byte__many(Byte_.Zero, "[", "]", "<", ">", "\"");
		for (byte i = 0; i < 33; i++) {
			trie.Add_bry_byte(new byte[] {i}, Byte_.Zero);
		}
		trie.Add_bry_byte(Bry_.New_by_ints(127), Byte_.Zero);	// x7F

		byte[][] zs_ary = regex_space.Zs();
		for (byte[] zs : zs_ary) {
			trie.Add_bry_byte(zs, Byte_.Zero);
		}
	}
	public int Find_fwd_while(Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		return Xomw_regex_.Find_fwd_until(trie, trv, src, src_bgn, src_end);
	}
}
