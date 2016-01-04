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
