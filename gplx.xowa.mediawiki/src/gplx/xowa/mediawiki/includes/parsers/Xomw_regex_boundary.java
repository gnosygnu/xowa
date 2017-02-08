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
		int bgn = gplx.core.intls.Utf8_.Get_pos0_of_char_bwd(src, pos - 1);
		byte b = src[bgn];
		Object o = trie.Match_at_w_b0(trv, b, src, bgn, pos);
		return o != null;
	}
}
