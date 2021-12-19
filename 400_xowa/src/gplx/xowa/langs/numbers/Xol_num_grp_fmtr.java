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
package gplx.xowa.langs.numbers;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
public class Xol_num_grp_fmtr {
	public boolean Mode_is_regx() {return digit_grouping_pattern == null || BryLni.Eq(digit_grouping_pattern, Digit_grouping_pattern_normal);}
	public byte[] Digit_grouping_pattern() {return digit_grouping_pattern;} public void Digit_grouping_pattern_(byte[] v) {digit_grouping_pattern = v;} private byte[] digit_grouping_pattern;
	public void Clear() {digit_grouping_pattern = null;}
	public byte[] Fmt_regx(BryWtr bfr, byte[] src) {// NOTE: specific code to handle preg_replace( '/(\d{3})(?=\d)(?!\d*\.)/', '$1,', strrev( $number ) ) );"; DATE:2014-04-15
		int src_len = src.length;
		int bgn = 0;
		int pos = bgn;
		boolean dirty = false;
		int grp_len = 3;
		while (true) {
			if (pos == src_len) break;
			byte b = src[pos];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9: {
					int num_end = BryFind.FindFwdWhileNum(src, pos, src_len);
					int num_len = num_end - pos;
					if (num_len > grp_len) {
						if (!dirty) {
							bfr.AddMid(src, bgn, pos);
							dirty = true;
						}
						Fmt_grp(bfr, src, pos, num_end, num_len, grp_len);
					}
					else {
						if (dirty)
							bfr.AddMid(src, pos, num_end);
					}
					pos = num_end;
					break;
				}
				case AsciiByte.Dot: {
					int num_end = BryFind.FindFwdWhileNum(src, pos + 1, src_len);	// +1 to skip dot
					if (dirty)
						bfr.AddMid(src, pos, num_end);
					pos = num_end;
					break;
				}
				default:
					if (dirty)
						bfr.AddByte(b);
					++pos;
					break;
			}
		}
		return dirty ? bfr.ToBryAndClear() : src;
	}
	private void Fmt_grp(BryWtr bfr, byte[] src, int bgn, int end, int len, int grp_len) {
		int seg_0 = bgn + (len % grp_len);	// 5 digit number will have seg_0 of 2; 12345 -> 12,345
		for (int i = bgn; i < end; i++) {
			if (	i != bgn						// never format at bgn; necessary for even multiples of grp_len (6, 9)
				&& (	i == seg_0					// seg_0
					|| (i - seg_0) % grp_len == 0	// seg_n
					)
				) {
				bfr.AddByte(AsciiByte.Comma);	// MW: hard-coded
			}
			bfr.AddByte(src[i]);
		}
	}
	private static final byte[] Digit_grouping_pattern_normal = BryUtl.NewA7("###,###,###");
}
