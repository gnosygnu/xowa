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
package gplx.xowa.langs.vnts.converts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
import gplx.core.btries.*; import gplx.core.intls.*;
public class Xol_convert_wkr {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs(); private final    Btrie_rv trv = new Btrie_rv();
	public Xol_convert_wkr(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private final    byte[] key;
	public void Add(byte[] src, byte[] trg) {trie.Add_obj(src, trg);}	// called by -{H}-
	public void Del(byte[] src)				{trie.Del(src);}			// called by -{-}-
	public boolean Convert_text(Bry_bfr bfr, byte[] src) {return Convert_text(bfr, src, 0, src.length);}
	public boolean Convert_text(Bry_bfr bfr, byte[] src, int bgn, int end) {
		int pos = bgn;
		boolean matched = false;
		while (pos < end) {
			byte b = src[pos];
			Object o = trie.Match_at_w_b0(trv, b, src, pos, end);
			if (o == null) {										// no match; skip to next char
				int char_len = Utf8_.Len_of_char_by_1st_byte(b);	// NOTE: must increment by char_len, not +1
				if (matched) {
					if (char_len == 1)
						bfr.Add_byte(b);
					else
						bfr.Add_mid(src, pos, pos + char_len);
				}
				pos += char_len;
			}
			else {
				if (!matched) {
					bfr.Add_mid(src, bgn, pos);	// add everything up to pos
					matched = true;
				}
				bfr.Add((byte[])o);
				pos = trv.Pos();
			}
		}
		if (!matched) bfr.Add_mid(src, bgn, end);	// no convert; make sure to add back src, else bfr will be blank
		return matched;
	}
	public void Init(Xol_convert_regy regy, byte[][] vnt_ary) {	// EX: "zh-cn" should add all converts from "zh-hans" "zh-cn" to its wkr
		trie.Clear();
		int len = vnt_ary.length;
		for (int i = 0; i < len; ++i) {
			byte[] key = vnt_ary[i];
			Xol_convert_grp grp = regy.Get_or_null(key); if (grp == null) continue;	// vnt may not have convert mapping; EX: zh-my
			Init_grp(grp);
		}
	}
	private void Init_grp(Xol_convert_grp grp) {
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Xol_convert_itm itm = grp.Get_at(i);
			trie.Add_obj(itm.Src(), itm.Trg());	// NOTE: for dupes, latest value wins
		}
	}
}
