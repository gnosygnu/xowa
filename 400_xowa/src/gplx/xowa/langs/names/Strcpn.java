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
package gplx.xowa.langs.names; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*;
import gplx.core.intls.*;
class Strcpn {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Strcpn(byte[][] ary) {
		for (byte[] val : ary) {
			trie.Add_bry(val);
		}
	}
	public int Exec(byte[] val) {
		int val_len = val.length;
		Btrie_rv trv = new Btrie_rv();
		int pos_in_chars = 0;
		int pos_in_bytes = 0;
		while (pos_in_bytes < val_len) {
			byte b = val[pos_in_bytes];
			trie.Match_at_w_b0(trv, b, val, pos_in_bytes, val_len);

			// no match; try next pos_in_bytes
			if (trv.Pos() == pos_in_bytes) {
				pos_in_bytes += Utf8_.Len_of_char_by_1st_byte(b);
				pos_in_chars++;
			}
			// match; return pos_in_bytes
			else {
				return pos_in_chars;
			}
		}
		return pos_in_chars;
	}

	public static byte[][] Split_concatenated_ascii(String str) {
		byte[] bry = Bry_.new_u8(str);
		int len = bry.length;
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; i++) {
			rv[i] = new byte[] {bry[i]};
		}
		return rv;
	}
	public static Strcpn New_by_concatenated_ascii(String s) {
		return new Strcpn(Split_concatenated_ascii(s));
	}
}
