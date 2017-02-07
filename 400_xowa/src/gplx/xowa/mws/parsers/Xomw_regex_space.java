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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
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
