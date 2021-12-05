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
package gplx.langs.phps; import gplx.*;
import gplx.core.encoders.*;
import gplx.objects.strings.AsciiByte;
class Php_quote_parser { // REF: https://www.php.net/manual/en/language.types.String.php
	private final Bry_bfr bfr = Bry_bfr_.New();
	public byte[] Parse(byte[] src, int src_pos, int src_end) {
		try {
			while (src_pos < src_end) {
				int val = 0;
				byte b = src[src_pos++];
				if (b == AsciiByte.Backslash) {
					b = src[src_pos++];
					switch(b) {
						case AsciiByte.Ltr_n:
							val = AsciiByte.Nl;
							break;
						case AsciiByte.Ltr_r:
							val = AsciiByte.Cr;
							break;
						case AsciiByte.Ltr_t:
							val = AsciiByte.Tab;
							break;
						case AsciiByte.Ltr_v:
							val = AsciiByte.VerticalTab;
							break;
						case AsciiByte.Ltr_e:
							val = AsciiByte.Escape;
							break;
						case AsciiByte.Ltr_f:
							val = AsciiByte.Formfeed;
							break;
						case AsciiByte.Dollar:
						case AsciiByte.Backslash:
						case AsciiByte.Quote:
							val = b;
							break;
						// octal
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9: {
							int num_bgn = src_pos - 1; // - 1 b/c pos++ above
							int num_end = src_pos;
							for (int i = 0; i < 3; i++) {// per REF, octal is {1,3}
								byte n = src[src_pos];
								num_end = src_pos;
								if (AsciiByte.IsNum(n)) {
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
						case AsciiByte.Ltr_x: {
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
						case AsciiByte.Ltr_u: {
							if (src[src_pos] == AsciiByte.CurlyBgn) { // ignore braces in u{1234}
								src_pos++;
							}

							int num_bgn = src_pos;
							int num_end = src_pos;
							for (int i = 0; i < 8; i++) { // assume max of 8 hexdecimals
								byte n = src[src_pos];
								num_end = src_pos;
								if (AsciiByte.IsNum(n)) {
									++src_pos;
								}
								else {
									break;
								}
							}

							if (src[src_pos] == AsciiByte.CurlyEnd) { // ignore braces in u{1234}
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