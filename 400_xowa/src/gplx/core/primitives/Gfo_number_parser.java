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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Gfo_number_parser {
	public int				Rv_as_int() {return (int)num_val;} private long num_val = 0;
	public long				Rv_as_long() {return num_val;}
	public Decimal_adp		Rv_as_dec() {return dec_val == null ? Decimal_adp_.long_(num_val) : dec_val;} private Decimal_adp dec_val = null;
	public boolean				Is_int()	{return dec_val == null && (num_val >= Int_.Min_value && num_val <= Int_.Max_value);}
	public boolean				Has_err()	{return has_err;} private boolean has_err;
	public boolean				Has_frac()	{return has_frac;} private boolean has_frac;
	public boolean				Hex_enabled() {return hex_enabled;} public Gfo_number_parser Hex_enabled_(boolean v) {hex_enabled = v; return this;} private boolean hex_enabled;
	public Gfo_number_parser	Ignore_chars_(byte[] v) {this.ignore_chars = v; return this;} private byte[] ignore_chars;
	public Gfo_number_parser	Ignore_space_at_end_y_() {this.ignore_space_at_end = true; return this;} private boolean ignore_space_at_end;
	public void	Clear() {
		ignore_chars = null;
	}
	public Gfo_number_parser Parse(byte[] src) {return Parse(src, 0, src.length);}
	public Gfo_number_parser Parse(byte[] ary, int bgn, int end) {
		int loop_bgn = end - 1, loop_end = bgn - 1, exp_multiplier = 1, factor = 10;
		long multiplier = 1, frc_multiplier = 1;
		num_val = 0; dec_val = null; boolean comma_nil = true;
		long frc_int = 0;
		has_err = false; has_frac = false; boolean has_exp = false, has_neg = false, exp_neg = false, has_plus = false, has_num = false;
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
					num_val += (cur - Byte_ascii.Num_0) * multiplier;
					multiplier *= factor;
					has_num = true;
					break;
				case Byte_ascii.Dot:
					if (has_frac) return Has_err_y_();
					frc_int = num_val;
					num_val = 0;
					frc_multiplier = multiplier;
					multiplier = 1;
					has_frac = true;
					break;
				case Byte_ascii.Comma:
					if (comma_nil)
						comma_nil = false;
					else
						return Has_err_y_();
					break;
				case Byte_ascii.Dash:
					if (has_neg) return Has_err_y_();
					has_neg = true;
					break;
				case Byte_ascii.Space:
					if		(i == bgn) {}	// space at bgn
					else if (i == end - 1 && ignore_space_at_end) {}	// ignore space at end; DATE:2015-04-29
					else
						return Has_err_y_();
					break;
				case Byte_ascii.Plus:
					if (has_plus) return Has_err_y_();
					has_plus = true;
					break;
				case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_E:
					if (input_is_hex) {
						num_val += 14 * multiplier;	// NOTE: 14=value of e/E
						multiplier *= factor;
						has_num = true;						
					}
					else {
						if (has_exp) return Has_err_y_();
						exp_neg = has_neg;
						exp_multiplier = (int)Math_.Pow(10, num_val);
						num_val = 0;
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
						num_val += (cur - Byte_ascii.Ltr_A + 10) * multiplier;
						multiplier *= factor;
						has_num = true;
					}
					else
						return Has_err_y_();
					break;
				case Byte_ascii.Ltr_a:
				case Byte_ascii.Ltr_b:
				case Byte_ascii.Ltr_c:
				case Byte_ascii.Ltr_d:
				case Byte_ascii.Ltr_f:
					if (input_is_hex) {
						num_val += (cur - Byte_ascii.Ltr_a + 10) * multiplier;
						multiplier *= factor;
						has_num = true;
					}
					else
						return Has_err_y_();
					break;
				case Byte_ascii.Ltr_x:
				case Byte_ascii.Ltr_X:
					if (input_is_hex)
						return (factor == 16) ? this : Has_err_y_();	// check for '0x'
					else
						return Has_err_y_();
				default:
					if (ignore_chars != null) {
						int ignore_chars_len = ignore_chars.length;
						boolean ignored = false;
						for (int j = 0; j < ignore_chars_len; ++j) {
							if (cur == ignore_chars[j]) {
								ignored = true;
								break;
							}
						}
						if (ignored) continue;
					}
					return Has_err_y_();
			}
		}			
		if (!has_num) return Has_err_y_();	// handles situations wherein just symbols; EX: "+", ".", "-.", " ,  " etc.
		if (has_frac) {
			long full_val = (((num_val * frc_multiplier) + frc_int));
			if (has_neg) full_val *= -1;
			if (has_exp) {
				if (exp_neg)	frc_multiplier	*= exp_multiplier;	// divide, so apply to frc
				else			full_val		*= exp_multiplier;	// multiply, so apply to full_val
			}
			dec_val = Decimal_adp_.divide_(full_val, frc_multiplier);
		}
		else {
			if (has_neg) num_val *= -1;
			if (has_exp) {
				num_val = exp_neg 
					? num_val / exp_multiplier 
					: num_val * exp_multiplier;
			}
		}
		return this;
	}
	private Gfo_number_parser Has_err_y_() {has_err = true; return this;}
}
