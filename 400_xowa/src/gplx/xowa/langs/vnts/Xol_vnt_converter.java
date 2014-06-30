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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.intl.*;
import gplx.xowa.langs.cnvs.*;
public class Xol_vnt_converter {
	private ByteTrieMgr_slim trie = ByteTrieMgr_slim.cs_();
	public Xol_vnt_converter(Xol_vnt_itm owner) {this.owner = owner;}
	public byte[] Owner_key() {return owner.Key();}
	public Xol_vnt_itm Owner() {return owner;} private Xol_vnt_itm owner;
	public boolean Convert_text(Bry_bfr bfr, byte[] src) {return Convert_text(bfr, src, 0, src.length);}
	public boolean Convert_text(Bry_bfr bfr, byte[] src, int bgn, int end) {
		int pos = bgn;
		boolean matched = false;
		while (pos < end) {
			byte b = src[pos];
			Object o = trie.Match(b, src, pos, end);
			if (o == null) {						// no match; skip to next char
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
				pos = trie.Match_pos();
			}
		}
		return matched;
	}
	public void Rebuild() {Clear(); Build();}
	private void Clear() {trie.Clear();}
	private void Build() {
		Xol_lang lang = owner.Lang();
		byte[][] convert_ary = owner.Convert_ary();
		int convert_ary_len = convert_ary.length;
		for (int i = 0; i < convert_ary_len; i++) {
			byte[] convert_grp_key = convert_ary[i];
			Xol_cnv_grp convert_grp = lang.Cnv_mgr().Get_or_null(convert_grp_key);
			if (convert_grp == null) continue;	// vnts may not have convert mapping; EX: zh-my
			Build_grp(convert_grp);
		}
	}
	private void Build_grp(Xol_cnv_grp convert_grp) {
		int len = convert_grp.Len();
		for (int i = 0; i < len; i++) {
			Xol_cnv_itm convert_itm = convert_grp.Get_at(i);
			trie.Add(convert_itm.Src(), convert_itm.Trg());	// NOTE: for dupes, latest value wins
		}
	}
}
