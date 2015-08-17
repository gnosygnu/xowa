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
package gplx.core.logs; import gplx.*; import gplx.core.*;
import gplx.core.btries.*;
class Gfo_log_fmtr {
	private final Btrie_fast_mgr trie = Btrie_fast_mgr.cs()
	.Add("~|"	, Bry_.new_a7("~|<"))
	.Add("|"	, Bry_.new_a7("~||"))
	.Add("\n"	, Bry_.new_a7("~|n"))
	.Add("\t"	, Bry_.new_a7("~|t"))
	; 
	public void Add(Bry_bfr bfr, String msg, Object... vals) {
		Add_bry(bfr, Bry_.new_u8(msg));
		int len = vals.length;
		for (int i = 0; i < len; ++i) {
			bfr.Add_byte(Byte_ascii.Pipe);
			byte[] val_bry = Bry_.new_u8(Object_.Xto_str_strict_or_empty(vals[i]));
			Add_bry(bfr, val_bry);
		}
		bfr.Add_byte_nl();
	}
	private void Add_bry(Bry_bfr bfr, byte[] src) {
		if (src == null) return;
		int len = src.length; if (len == 0) return;
		int pos = 0;
		int add_bgn = -1;
		while (true) {
			if (pos == len) break;
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, len);
			if (o == null) {
				if (add_bgn == -1) add_bgn = pos;
			}
			else {
				if (add_bgn != -1) bfr.Add_mid(src, add_bgn, pos);
				byte[] repl = (byte[])o;
				bfr.Add(repl);
				pos = trie.Match_pos();
			}
		}
		if (add_bgn != -1) bfr.Add_mid(src, add_bgn, len);
	}
}
