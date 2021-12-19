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
package gplx.xowa.langs.parsers;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class Xol_csv_parser {
	public void Save(BryWtr bfr, byte[] src) {
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Cr: 					bfr.AddByte(AsciiByte.Backslash); bfr.AddByte(AsciiByte.Ltr_r); break;
				case AsciiByte.Nl: 					bfr.AddByte(AsciiByte.Backslash); bfr.AddByte(AsciiByte.Ltr_n); break;
				case AsciiByte.Tab: 					bfr.AddByte(AsciiByte.Backslash); bfr.AddByte(AsciiByte.Ltr_t); break;
				case AsciiByte.Backslash: 				bfr.AddByte(AsciiByte.Backslash); bfr.AddByte(AsciiByte.Backslash); break;
				case AsciiByte.Pipe: 					bfr.Add(Bry_pipe); break;
				default:								bfr.AddByte(b); break;
			}
		}
	}
	public byte[] Load(byte[] src, int bgn, int end) {Load(tmp_bfr, src, bgn, end); return tmp_bfr.ToBryAndClear();}
	public void Load(BryWtr bfr, byte[] src) {Load(bfr, src, 0, src.length);}
	public void Load(BryWtr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Backslash: {
					int nxt_pos = i + 1; if (nxt_pos == end) throw ErrUtl.NewArgs("backslash cannot be last character");
					byte nxt_byte = src[nxt_pos];
					switch (nxt_byte) {
						case AsciiByte.Backslash:		bfr.AddByte(AsciiByte.Backslash); break;
						case AsciiByte.Ltr_r: 			bfr.AddByte(AsciiByte.Cr); break;
						case AsciiByte.Ltr_n:			bfr.AddByte(AsciiByte.Nl); break;
						case AsciiByte.Ltr_t:			bfr.AddByte(AsciiByte.Tab); break;
						case AsciiByte.Ltr_u:
							int utf_len = 1;
							for (int j = i + 6; j < end; j += 6) {	// iterate over rest of String; EX: \u0123
								if (j + 1 < end && src[j] == AsciiByte.Backslash && src[j + 1] == AsciiByte.Ltr_u)
									++utf_len;
								else
									break;
							}
							byte[] utf_bytes = new byte[utf_len]; int utf_idx = 0;
							int utf_pos = i + 2;
							for (int j = 0; j < utf_len; j++) {
								int utf_int = IntUtl.ByHexBry(src, utf_pos, utf_pos + 4);
								if (utf_int == -1) throw ErrUtl.NewArgs("invalid value for \\u", "val", StringUtl.NewU8(src, bgn, end));
								utf_bytes[utf_idx++] = (byte)utf_int;
								utf_pos += 6;
							}
							int utf_int_decoded = Utf16Utl.DecodeToInt(utf_bytes, 0);
							bfr.Add(Utf16Utl.EncodeIntToBry(utf_int_decoded));
							nxt_pos = i + (utf_len * 6) - 1;	// -1 b/c "for" will ++
							break;
						default:
							bfr.AddByte(b).AddByte(nxt_byte);
							break;
					}
					i = nxt_pos;
					break;
				}
				default:								bfr.AddByte(b); break;
			}
		}
	}
	private static final byte[] Bry_pipe = BryUtl.NewA7("\\u007C");
	private static final BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public static final Xol_csv_parser Instance = new Xol_csv_parser(); Xol_csv_parser() {}
}
