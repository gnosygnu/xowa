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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.core.btries.*;
public class Xomw_regex_space {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Xomw_regex_space() {
		byte[] space = BryUtl.NewByInts(32);
		ws = new byte[][]
		{ space
		, BryUtl.NewByInts(9)
		, BryUtl.NewByInts(10)
		, BryUtl.NewByInts(13)
		};
		// Zs; REF:http://www.fileformat.info/info/unicode/category/Zs/list.htm
		zs = new byte[][]
		{ space
		, BryUtl.NewByInts(194, 160)
		, BryUtl.NewByInts(225, 154, 128)
		, BryUtl.NewByInts(226, 128, 129)
		, BryUtl.NewByInts(226, 128, 130)
		, BryUtl.NewByInts(226, 128, 131)
		, BryUtl.NewByInts(226, 128, 132)
		, BryUtl.NewByInts(226, 128, 133)
		, BryUtl.NewByInts(226, 128, 134)
		, BryUtl.NewByInts(226, 128, 135)
		, BryUtl.NewByInts(226, 128, 136)
		, BryUtl.NewByInts(226, 128, 137)
		, BryUtl.NewByInts(226, 128, 138)
		, BryUtl.NewByInts(226, 128, 175)
		, BryUtl.NewByInts(226, 129, 159)
		, BryUtl.NewByInts(227, 128, 128)
		};

		byte[][] ary = ws;
		for (byte[] bry : ary) {
			trie.Add_bry_byte(bry, ByteUtl.Zero);
		}
		ary = zs;
		for (byte[] bry : ary) {
			trie.Add_bry_byte(bry, ByteUtl.Zero);
		}
	}
	public byte[][] Ws() {return ws;} private byte[][] ws;
	public byte[][] Zs() {return zs;} private byte[][] zs;
	public int Find_fwd_while(Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		return Xomw_regex_.Find_fwd_while(trie, trv, src, src_bgn, src_end);
	}
}
