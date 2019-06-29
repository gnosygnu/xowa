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
public class Xomw_regex_space {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Xomw_regex_space() {
		byte[] space = Bry_.New_by_ints(32);
		ws = new byte[][]
		{ space
		, Bry_.New_by_ints(9)
		, Bry_.New_by_ints(10)
		, Bry_.New_by_ints(13)
		};
		// Zs; REF:http://www.fileformat.info/info/unicode/category/Zs/list.htm
		zs = new byte[][]
		{ space
		, Bry_.New_by_ints(194, 160)
		, Bry_.New_by_ints(225, 154, 128)
		, Bry_.New_by_ints(226, 128, 129)
		, Bry_.New_by_ints(226, 128, 130)
		, Bry_.New_by_ints(226, 128, 131)
		, Bry_.New_by_ints(226, 128, 132)
		, Bry_.New_by_ints(226, 128, 133)
		, Bry_.New_by_ints(226, 128, 134)
		, Bry_.New_by_ints(226, 128, 135)
		, Bry_.New_by_ints(226, 128, 136)
		, Bry_.New_by_ints(226, 128, 137)
		, Bry_.New_by_ints(226, 128, 138)
		, Bry_.New_by_ints(226, 128, 175)
		, Bry_.New_by_ints(226, 129, 159)
		, Bry_.New_by_ints(227, 128, 128)
		};

		byte[][] ary = ws;
		for (byte[] bry : ary) {
			trie.Add_bry_byte(bry, Byte_.Zero);
		}
		ary = zs;
		for (byte[] bry : ary) {
			trie.Add_bry_byte(bry, Byte_.Zero);
		}
	}
	public byte[][] Ws() {return ws;} private byte[][] ws;
	public byte[][] Zs() {return zs;} private byte[][] zs;
	public int Find_fwd_while(Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		return Xomw_regex_.Find_fwd_while(trie, trv, src, src_bgn, src_end);
	}
}
