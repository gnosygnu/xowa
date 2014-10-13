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
package gplx.xowa; import gplx.*;
public class Xol_csv_parser {
	public void Save(Bry_bfr bfr, byte[] src) {
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.CarriageReturn: 		bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Ltr_r); break;
				case Byte_ascii.NewLine: 				bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Ltr_n); break;
				case Byte_ascii.Tab: 					bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Ltr_t); break;
				case Byte_ascii.Backslash: 				bfr.Add_byte(Byte_ascii.Backslash); bfr.Add_byte(Byte_ascii.Backslash); break;
				case Byte_ascii.Pipe: 					bfr.Add(Bry_pipe); break;
				default:								bfr.Add_byte(b); break;
			}
		}
	}
	public byte[] Load(byte[] src, int bgn, int end) {Load(tmp_bfr, src, bgn, end); return tmp_bfr.Xto_bry_and_clear();}
	public void Load(Bry_bfr bfr, byte[] src) {Load(bfr, src, 0, src.length);}
	public void Load(Bry_bfr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Backslash: {
					int nxt_pos = i + 1; if (nxt_pos == end) throw Err_mgr._.fmt_(GRP_KEY, "parse_backslash_is_last", "backslash cannot be last character");
					byte nxt_byte = src[nxt_pos];
					switch (nxt_byte) {
						case Byte_ascii.Backslash:		bfr.Add_byte(Byte_ascii.Backslash); break;
						case Byte_ascii.Ltr_r: 			bfr.Add_byte(Byte_ascii.CarriageReturn); break;
						case Byte_ascii.Ltr_n:			bfr.Add_byte(Byte_ascii.NewLine); break;
						case Byte_ascii.Ltr_t:			bfr.Add_byte(Byte_ascii.Tab); break;
						case Byte_ascii.Ltr_u:
							int utf_len = 1;
							for (int j = i + 6; j < end; j += 6) {	// iterate over rest of String; EX: \u0123
								if (j + 1 < end && src[j] == Byte_ascii.Backslash && src[j + 1] == Byte_ascii.Ltr_u)
									++utf_len;
								else
									break;
							}
							byte[] utf_bytes = new byte[utf_len]; int utf_idx = 0;
							int utf_pos = i + 2;
							for (int j = 0; j < utf_len; j++) {
								int utf_int = Int_.Xto_int_hex(src, utf_pos, utf_pos + 4);
								if (utf_int == -1) throw Err_mgr._.fmt_(GRP_KEY, "parse_invalid_escape_u", "invalid value for \\u: ~{0}", String_.new_utf8_(src, bgn, end));
								utf_bytes[utf_idx++] = (byte)utf_int;
								utf_pos += 6;
							}
							int utf_int_decoded = gplx.intl.Utf16_.Decode_to_int(utf_bytes, 0);
							bfr.Add(gplx.intl.Utf16_.Encode_int_to_bry(utf_int_decoded));
							nxt_pos = i + (utf_len * 6) - 1;	// -1 b/c "for" will ++
							break;
						default:
							bfr.Add_byte(b).Add_byte(nxt_byte);
							break;
					}
					i = nxt_pos;
					break;
				}
				default:								bfr.Add_byte(b); break;
			}
		}
	}
	private static final String GRP_KEY = "Xol_csv_parser";
	private static final byte[] Bry_pipe = Bry_.new_ascii_("\\u007C");
	private static final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public static final Xol_csv_parser _ = new Xol_csv_parser(); Xol_csv_parser() {}
}
