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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.types.basics.encoders.HexUtl;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
public class Xomw_regex_parser {
	private BryWtr tmp;
	public byte[][] Rslt() {return rslt;} private byte[][] rslt;
	public Xomw_regex_parser Add_ary(String... ary) {return Set_or_add(Parse_ary(ary));}
	private byte[][] Parse_ary(String... ary) {
		if (tmp == null) tmp = BryWtr.New();
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = Compile_itm(tmp, BryUtl.NewU8(ary[i]));
		}
		return rv;
	}
	public Xomw_regex_parser Add_rng(String bgn, String end) {return Set_or_add(Parse_rng(bgn, end));}
	private byte[][] Parse_rng(String bgn, String end) {
		if (tmp == null) tmp = BryWtr.New();
		byte[] bgn_bry = Compile_itm(tmp, BryUtl.NewU8(bgn));
		int bgn_val = Utf16Utl.DecodeToInt(bgn_bry, 0);
		byte[] end_bry = Compile_itm(tmp, BryUtl.NewU8(end));
		int end_val = Utf16Utl.DecodeToInt(end_bry, 0);

		int rv_len = end_val - bgn_val + 1;
		byte[][] rv = new byte[rv_len][];
		for (int i = 0; i < rv_len; i++) {
			rv[i] = Utf16Utl.EncodeIntToBry(i + bgn_val);
		}
		return rv;
	}
	private Xomw_regex_parser Set_or_add(byte[][] val) {
		rslt = rslt == null ? val : BryUtl.AryAdd(rslt, val);
		return this;
	}
	private static byte[] Compile_itm(BryWtr tmp, byte[] src) {
		// parse each itm
		int src_end = src.length;
		int cur = 0;
		int prv = cur;
		boolean dirty = false;
		while (true) {
			// eos
			if (cur == src_end) {
				if (dirty)
					tmp.AddMid(src, prv, src_end);
				break;
			}

			// look at byte
			byte b = src[cur];
			switch (b) {	// escape
				case AsciiByte.Backslash:
					int nxt = cur + 1;
					if (nxt >= src_end) throw ErrUtl.NewArgs("regex escape failed: no more chars left", "src", src, "pos", nxt);
					byte nxt_byte = src[nxt];
					switch (nxt_byte) {
						case AsciiByte.Ltr_s: // \s -> " "
							src = AsciiByte.SpaceBry;
							cur = src_end;
							break;
						case AsciiByte.Ltr_x: // \ u -> utf8 sequence in hex-dec; EX: "\xc2\xad" -> new byte[] {194, 160}
							// read next two bytes
							dirty = true;
							nxt++;
							if (nxt + 2 > src_end) throw ErrUtl.NewArgs("utf8 escape failed: no more chars left", "src", src, "pos", nxt);
							tmp.AddByte((byte)HexUtl.ParseOr(src, nxt, nxt + 2, -1));
							cur = nxt + 2;
							prv = cur;
							break;
						default:
							throw ErrUtl.NewArgs("regex escape failed: unknown char", "src", src, "pos", nxt);
					}
					break;
				default: // handles ascii only
					if (b > 127)
						throw ErrUtl.NewArgs("regex compiled failed: unknown char", "src", src, "pos", cur);
					cur++;
					break;
			}
		}

		// set item
		return dirty ? tmp.ToBryAndClear() : src;
	}
}
