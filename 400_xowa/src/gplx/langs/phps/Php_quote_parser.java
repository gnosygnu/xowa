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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.encoders.*;
class Php_quote_parser { // REF: https://www.php.net/manual/en/language.types.String.php
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public byte[] Parse(byte[] src, int src_pos, int src_end) {
		try {
			while (src_pos < src_end) {
				int val = 0;
				byte b = src[src_pos++];
				if (b == Byte_ascii.Backslash) {
					b = src[src_pos++];
					switch(b) {
						case Byte_ascii.Ltr_n:
							val = Byte_ascii.Nl;
							break;
						case Byte_ascii.Ltr_r:
							val = Byte_ascii.Cr;
							break;
						case Byte_ascii.Ltr_t:
							val = Byte_ascii.Tab;
							break;
						case Byte_ascii.Ltr_v:
							val = Byte_ascii.Vertical_tab;
							break;
						case Byte_ascii.Ltr_e:
							val = Byte_ascii.Escape;
							break;
						case Byte_ascii.Ltr_f:
							val = Byte_ascii.Formfeed;
							break;
						case Byte_ascii.Dollar:
						case Byte_ascii.Backslash:
						case Byte_ascii.Quote:
							val = b;
							break;
						// octal
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9: {
							int num_bgn = src_pos - 1; // - 1 b/c pos++ above
							int num_end = src_pos;
							for (int i = 0; i < 3; i++) {// per REF, octal is {1,3}
								byte n = src[src_pos];
								num_end = src_pos;
								if (Byte_ascii.Is_num(n)) {
									++src_pos;
								}
								else {
									break;
								}
							}

							val = Oct_utl_.Parse_or(src, num_bgn, num_end, -1);
							break;
						}
						// hexdec
						case Byte_ascii.Ltr_x: {
							// REF: changed from \xFF to \u1234; https://github.com/wikimedia/mediawiki/commit/0313128b1038de8f2ee52a181eafdee8c5e430f7#diff-1b04277d170b32db7f92ce812744ef6b
							int num_bgn = src_pos;
							int num_end = src_pos++;
							for (int i = 0; i < 2; i++) { // per REF, hex is {1,2}
								byte n = src[src_pos];
								num_end = src_pos;
								if (Hex_utl_.Is_hex(n)) {
									++src_pos;
								}
								else {
									break;
								}
							}
							val = Hex_utl_.Parse_or(src, num_bgn, num_end, -1);
							break;
						}
						// unicode
						case Byte_ascii.Ltr_u: {
							if (src[src_pos] == Byte_ascii.Curly_bgn) { // ignore braces in u{1234}
								src_pos++;
							}

							int num_bgn = src_pos;
							int num_end = src_pos;
							for (int i = 0; i < 8; i++) { // assume max of 8 hexdecimals
								byte n = src[src_pos];
								num_end = src_pos;
								if (Byte_ascii.Is_num(n)) {
									++src_pos;
								}
								else {
									break;
								}
							}

							if (src[src_pos] == Byte_ascii.Curly_end) { // ignore braces in u{1234}
								++src_pos;
							}

							val = Hex_utl_.Parse_or(src, num_bgn, num_end, -1);
							break;
						}
						default:
							val = b;
							break;
					}
				}
				else {
					val = b;
				}
				if (val < 255)
					bfr.Add_byte((byte)val);
				else
					bfr.Add_u8_int(val);
			}
			return bfr.To_bry_and_clear();
		} catch (Exception e) {
			throw Err_.new_exc(e, "Ustring_parser", "unable to parse ustring", "src", Bry_.Mid(src, src_pos, src_end));
		}
	}
}