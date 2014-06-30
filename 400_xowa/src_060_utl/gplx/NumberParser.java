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
package gplx;
public class NumberParser {
	public int AsInt() {return (int)int_val;} long int_val = 0;
	public DecimalAdp AsDec() {return dec_val == null ? DecimalAdp_.long_(int_val) : dec_val;} DecimalAdp dec_val = null;
	public boolean HasErr()  {return hasErr;} private boolean hasErr;
	public boolean HasFrac() {return hasFrac;} private boolean hasFrac;
	public boolean Hex_enabled() {return hex_enabled;} public NumberParser Hex_enabled_(boolean v) {hex_enabled = v; return this;} private boolean hex_enabled;
	public NumberParser Parse(byte[] src) {return Parse(src, 0, src.length);}
	public NumberParser Parse(byte[] ary, int bgn, int end) {
		int loop_bgn = end - 1, loop_end = bgn - 1, exp_multiplier = 1, factor = 10;
		long multiplier = 1, frc_multiplier = 1;
		int_val = 0; dec_val = null; boolean comma_nil = true;
		long frc_int = 0;
		hasErr = false; hasFrac = false; boolean has_exp = false, has_neg = false, exp_neg = false, has_plus = false, has_num = false;
		boolean input_is_hex = false;
		if (hex_enabled) {
			if (loop_end + 2 < end) {		// ArrayOutOfBounds check
				byte b_2 = ary[loop_end + 2];
				switch (b_2) {
					case Byte_ascii.Ltr_x:
					case Byte_ascii.Ltr_X:								// is 2nd char x?
						if (ary[loop_end + 1] == Byte_ascii.Num_0) {	// is 1st char 0?
							factor = 16;
							input_is_hex = true;
						}
						break;
					default:
						break;
				}
			}
		}
		for (int i = loop_bgn; i > loop_end; i--) {
			byte cur = ary[i];
			switch (cur) {
				case Byte_ascii.Num_0:
				case Byte_ascii.Num_1:
				case Byte_ascii.Num_2:
				case Byte_ascii.Num_3:
				case Byte_ascii.Num_4:
				case Byte_ascii.Num_5:
				case Byte_ascii.Num_6:
				case Byte_ascii.Num_7:
				case Byte_ascii.Num_8:
				case Byte_ascii.Num_9:
					int_val += (cur - Byte_ascii.Num_0) * multiplier;
					multiplier *= factor;
					has_num = true;
					break;
				case Byte_ascii.Dot:
					if (hasFrac) return IsErr_();
					frc_int = int_val;
					int_val = 0;
					frc_multiplier = multiplier;
					multiplier = 1;
					hasFrac = true;
					break;
				case Byte_ascii.Comma:
					if (comma_nil)
						comma_nil = false;
					else
						return IsErr_();
					break;
				case Byte_ascii.Dash:
					if (has_neg) return IsErr_();
					has_neg = true;
					break;
				case Byte_ascii.Space:
					if (i == bgn) {}	// space at bgn
					else
						return IsErr_();
					break;
				case Byte_ascii.Plus:
					if (has_plus) return IsErr_();
					has_plus = true;
					break;
				case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_E:
					if (input_is_hex) {
						int_val += 14 * multiplier;	// NOTE: 14=value of e/E
						multiplier *= factor;
						has_num = true;						
					}
					else {
						if (has_exp) return IsErr_();
						exp_neg = has_neg;
						exp_multiplier = (int)Math_.Pow(10, int_val);
						int_val = 0;
						multiplier = 1;
						has_exp = true;
						has_neg = false;
						has_plus = false;	// allow +1E+2
					}
					break;
				case Byte_ascii.Ltr_A:
				case Byte_ascii.Ltr_B:
				case Byte_ascii.Ltr_C:
				case Byte_ascii.Ltr_D:
				case Byte_ascii.Ltr_F:
					if (input_is_hex) {
						int_val += (cur - Byte_ascii.Ltr_A + 10) * multiplier;
						multiplier *= factor;
						has_num = true;
					}
					else
						return IsErr_();
					break;
				case Byte_ascii.Ltr_a:
				case Byte_ascii.Ltr_b:
				case Byte_ascii.Ltr_c:
				case Byte_ascii.Ltr_d:
				case Byte_ascii.Ltr_f:
					if (input_is_hex) {
						int_val += (cur - Byte_ascii.Ltr_a + 10) * multiplier;
						multiplier *= factor;
						has_num = true;
					}
					else
						return IsErr_();
					break;
				case Byte_ascii.Ltr_x:
				case Byte_ascii.Ltr_X:
					if (input_is_hex)
						return (factor == 16) ? this : IsErr_();	// check for '0x'
					else
						return IsErr_();
				default:
					return IsErr_();
			}
		}			
		if (!has_num) return IsErr_();	// handles situations wherein just symbols; EX: "+", ".", "-.", " ,  " etc.
		if (hasFrac) {
			long full_val = (((int_val * frc_multiplier) + frc_int));
			if (has_neg) full_val *= -1;
			if (has_exp) {
				if (exp_neg)	frc_multiplier	*= exp_multiplier;	// divide, so apply to frc
				else			full_val		*= exp_multiplier;	// multiply, so apply to full_val
			}
			dec_val = DecimalAdp_.divide_(full_val, frc_multiplier);
		}
		else {
			if (has_neg) int_val *= -1;
			if (has_exp) int_val = exp_neg ? int_val / exp_multiplier : int_val * exp_multiplier;
		}
		return this;
	}
	NumberParser IsErr_() {hasErr = true; return this;}
}
