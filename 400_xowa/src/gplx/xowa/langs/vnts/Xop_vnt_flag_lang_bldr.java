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
class Xop_vnt_flag_lang_bldr {
	private Xop_vnt_flag_lang_itm[] ary; private int ary_len;
	private int ary_count;
	public Xop_vnt_flag_lang_bldr(Xol_vnt_mgr vnt_mgr) {
		Xol_vnt_converter[] converter_ary = vnt_mgr.Converter_ary();
		int len = converter_ary.length;
		for (int i = 0; i < len; i++) {
			byte[] lang = converter_ary[i].Owner().Key();
			Xop_vnt_flag_lang_itm itm = new Xop_vnt_flag_lang_itm(i, lang);
			trie.Add(lang, itm);
		}
		ary = new Xop_vnt_flag_lang_itm[len];
		ary_len = len;
	}
	public ByteTrieMgr_slim Trie() {return trie;} private ByteTrieMgr_slim trie = ByteTrieMgr_slim.ci_ascii_();	// NOTE:ci.ascii:MW_const.en; lang variant name; EX:zh-hans
	public void Add(Xop_vnt_flag_lang_itm itm) {
		int idx = itm.Idx();
		if (ary[idx] == null) {
			ary[idx] = itm;
			++ary_count;
		}
	}
	public void Clear() {
		for (int i = 0; i < ary_len; i++)
			ary[i] = null;
		ary_count = 0;
	}
	public Xop_vnt_flag Bld() {
		if (ary_count == 0) return Xop_vnt_flag_.Flag_unknown;
		byte[][] langs = new byte[ary_count][];
		int ary_idx = 0;
		for (int i = 0; i < ary_len; i++) {
			Xop_vnt_flag_lang_itm itm = ary[i];
			if (itm != null)
				langs[ary_idx++] = itm.Key();
		}
		return Xop_vnt_flag_.new_langs_(langs);
	}
}
class Xop_vnt_flag_lang_itm {
	public Xop_vnt_flag_lang_itm(int idx, byte[] key) {this.idx = idx; this.key = key; this.key_len = key.length;}
	public int Idx() {return idx;} private int idx;
	public byte[] Key() {return key;} private byte[] key;
	public int Key_len() {return key_len;} private int key_len;
}
