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
public class Xomw_regex_boundary {	// THREAD.SAFE: trv is only for consistent interface
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	public Xomw_regex_boundary(Xomw_regex_space space) {
		// naive implementation of is_boundary; ignore all ws and underscore
		byte[][] ary = space.Ws();
		for (byte[] bry : ary)
			trie.Add_bry_byte(bry, Byte_.Zero);
		ary = space.Zs();
		for (byte[] bry : ary)
			trie.Add_bry_byte(bry, Byte_.Zero);
	}
	public boolean Is_boundary_prv(byte[] src, int pos) {
		if (pos == 0) return true; // BOS is true
		int bgn = gplx.core.intls.Utf8_.Get_prv_char_pos0(src, pos);
		byte b = src[bgn];
		Object o = trie.Match_at_w_b0(trv, b, src, bgn, pos);
		return o != null;
	}
}
