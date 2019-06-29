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
public class Xomw_regex_parser {
	private Bry_bfr tmp;	
	public byte[][] Rslt() {return rslt;} private byte[][] rslt;
	public Xomw_regex_parser Add_ary(String... ary) {return Set_or_add(Parse_ary(ary));}
	private byte[][] Parse_ary(String... ary) {
		if (tmp == null) tmp = Bry_bfr_.New();
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = Compile_itm(tmp, Bry_.new_u8(ary[i]));
		}
		return rv;
	}
	public Xomw_regex_parser Add_rng(String bgn, String end) {return Set_or_add(Parse_rng(bgn, end));}
	private byte[][] Parse_rng(String bgn, String end) {
		if (tmp == null) tmp = Bry_bfr_.New();
		byte[] bgn_bry = Compile_itm(tmp, Bry_.new_u8(bgn));
		int bgn_val = gplx.core.intls.Utf16_.Decode_to_int(bgn_bry, 0);
		byte[] end_bry = Compile_itm(tmp, Bry_.new_u8(end));
		int end_val = gplx.core.intls.Utf16_.Decode_to_int(end_bry, 0);

		int rv_len = end_val - bgn_val + 1;
		byte[][] rv = new byte[rv_len][];
		for (int i = 0; i < rv_len; i++) {
			rv[i] = gplx.core.intls.Utf16_.Encode_int_to_bry(i + bgn_val);
		}
		return rv;
	}
	private Xomw_regex_parser Set_or_add(byte[][] val) {
		rslt = rslt == null ? val : Bry_.Ary_add(rslt, val);
		return this;
	}
	private static byte[] Compile_itm(Bry_bfr tmp, byte[] src) {
		// parse each itm
		int src_end = src.length;
		int cur = 0;
		int prv = cur;
		boolean dirty = false;
		while (true) {
			// eos
			if (cur == src_end) {
				if (dirty)
					tmp.Add_mid(src, prv, src_end);
				break;
			}

			// look at byte
			byte b = src[cur];
			switch (b) {	// escape
				case Byte_ascii.Backslash:
					int nxt = cur + 1;
					if (nxt >= src_end) throw Err_.new_wo_type("regex escape failed: no more chars left", "src", src, "pos", nxt);
					byte nxt_byte = src[nxt];
					switch (nxt_byte) {
						case Byte_ascii.Ltr_s: // \s -> " "
							src = Byte_ascii.Space_bry;
							cur = src_end;
							break;
						case Byte_ascii.Ltr_x: // \ u -> utf8 sequence in hex-dec; EX: "\xc2\xad" -> new byte[] {194, 160}
							// read next two bytes
							dirty = true;
							nxt++;
							if (nxt + 2 > src_end) throw Err_.new_wo_type("utf8 escape failed: no more chars left", "src", src, "pos", nxt);
							tmp.Add_byte((byte)gplx.core.encoders.Hex_utl_.Parse_or(src, nxt, nxt + 2, -1));
							cur = nxt + 2;
							prv = cur;
							break;
						default:
							throw Err_.new_wo_type("regex escape failed: unknown char", "src", src, "pos", nxt);
					}
					break;
				default: // handles ascii only
					if (b > 127)
						throw Err_.new_wo_type("regex compiled failed: unknown char", "src", src, "pos", cur);
					cur++;
					break;
			}
		}

		// set item
		return dirty ? tmp.To_bry_and_clear() : src;
	}
}
