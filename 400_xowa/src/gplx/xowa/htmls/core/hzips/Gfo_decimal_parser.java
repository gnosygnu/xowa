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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.encoders.*;
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
		byte b = Byte_ascii.Null;
		boolean loop = true;
		while (loop) {
			b = src[pos++];
			switch (b) {
				case Byte_ascii.Dash:
					if (!sign) return false;	// fail if "--"
					sign = false;				// keep looping; handle "-0.12"
					++dot_offset;
					break;
				case Byte_ascii.Num_0:
					b = src[pos++];
					if (b != Byte_ascii.Dot) return false;	// fail if not "0."
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
				case Byte_ascii.Dot:
					if (dot_pos != -1) return false;	// fail if multiple dots
					dot_pos = pos - src_bgn - dot_offset - 1;	// pos already advanced forward one
					break;
				case Byte_ascii.Num_0:
					if (num_seen)
						number *= 10;
					break;
				case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					if (!num_seen) num_seen = true;	// save first "number"; for "0.00001" where num will be 0 and left shift will be 5
					number *= 10;
					number += b - Byte_ascii.Num_0;
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
