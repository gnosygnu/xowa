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
package gplx.xowa.htmls.core.hzips;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_decimal_parser {
	public boolean Sign() {return sign;} private boolean sign; 
	public int Exponent() {return exponent;} private int exponent;
	public long Number() {return number;} private long number;
	public boolean Parse(byte[] src, int src_bgn, int src_end) {
		sign = true;
		number = 0;
		exponent = 0;
		if (src_end - src_bgn < 1) return false;
		int pos = src_bgn;
		int dot_offset = 0;
		byte b = AsciiByte.Null;
		boolean loop = true;
		while (loop) {
			b = src[pos++];
			switch (b) {
				case AsciiByte.Dash:
					if (!sign) return false;	// fail if "--"
					sign = false;				// keep looping; handle "-0.12"
					++dot_offset;
					break;
				case AsciiByte.Num0:
					b = src[pos++];
					if (b != AsciiByte.Dot) return false;	// fail if not "0."
					++dot_offset;
					loop = false;
					break;
				default:
					loop = false;
					break;
			}
		}
		int dot_pos = -1;
		boolean num_seen = false;
		while (true) {
			switch (b) {
				case AsciiByte.Dot:
					if (dot_pos != -1) return false;	// fail if multiple dots
					dot_pos = pos - src_bgn - dot_offset - 1;	// pos already advanced forward one
					break;
				case AsciiByte.Num0:
					if (num_seen)
						number *= 10;
					break;
				case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					if (!num_seen) num_seen = true;	// save first "number"; for "0.00001" where num will be 0 and left shift will be 5
					number *= 10;
					number += b - AsciiByte.Num0;
					break;
			}
			if (pos == src_end) break;
			b = src[pos];
			++pos;
		}
		switch (dot_pos) {
			case -1: exponent = 0; break;
			default: exponent = dot_pos + dot_offset - src_end + 1; break;
		}
		return true;
	}
}
