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
