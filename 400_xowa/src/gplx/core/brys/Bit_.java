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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bit_ {
	public static String XtoBitStr(int val) {
    		boolean[] bits = new boolean[8];
    		int idx = 7;
    		while (val > 0) {
    			if ((val & 1) == 1) bits[idx] = true;
    			idx--;
    			val >>= 1;
    		}
		byte[] rv = new byte[8];
    		for (int i = 0; i < 8; i++)
		rv[i] = bits[i] ? Byte_ascii.Num_1 : Byte_ascii.Num_0;
    		return String_.new_ascii_(rv);
	}
	public static int[] Bld_pow_ary(int... seg_ary) {
		int seg_ary_len = seg_ary.length;
		int pow = 0;
		int[] rv = new int[seg_ary_len];
		for (int i = seg_ary_len - 1; i > -1; i--) {
			rv[i] = Base2_ary[pow];
			pow += seg_ary[i];			
		}
		return rv;
	}
	public static int Xto_int(int[] pow_ary, int[] val_ary) {
		int pow_ary_last = pow_ary.length - 1;
		int val = 0;
		for (int i = pow_ary_last; i > -1; i--)
			val += pow_ary[i] * val_ary[i];
		return val;
	}
	public static int[] Xto_intAry(int[] pow_ary, int v) {
		int[] rv = new int[pow_ary.length];
		Xto_intAry(rv, pow_ary, v);
		return rv;
	}
	public static void Xto_intAry(int[] rv, int[] pow_ary, int v) {
		int pow_ary_len = pow_ary.length;
		int rv_len = rv.length;
		for (int i = 0; i < pow_ary_len; i++) {
			if (i >= rv_len) break;
			rv[i] = v / pow_ary[i];
			int factor = pow_ary[i] * rv[i];
			v = factor == 0 ? v : (v % factor);	// NOTE: if 0, do not do modulus or else div by zero
		}
	}
	public static byte Xto_byte(byte[] pow_ary, byte... val_ary) {
		int pow_ary_last = pow_ary.length - 1;
		int val = 0;
		for (int i = pow_ary_last; i > -1; --i)
			val += pow_ary[i] * val_ary[i];
		return (byte)val;
	}
	public static void Xto_bry(byte[] rv, byte[] pow_ary, byte val) {
		int pow_ary_len = pow_ary.length;
		int rv_len = rv.length;
		for (int i = 0; i < pow_ary_len; i++) {
			if (i >= rv_len) break;
			rv[i] = (byte)(val / pow_ary[i]);
			int factor = pow_ary[i] * rv[i];
			val = (byte)(factor == 0 ? val : (val % factor));	// NOTE: if 0, do not do modulus or else div by zero
		}
	}
	public static int Shift_lhs(int val, int shift) {return val << shift;}
	public static int Shift_rhs(int val, int shift) {return val >> shift;}
	public static int Shift_lhs_to_int(int[] shift_ary, int... val_ary) {
		int val_len = val_ary.length; if (val_len > shift_ary.length) throw Err_.new_("vals must be less than shifts; vals={0} shifts=~{1}");
		int rv = 0;
		for (int i = 0; i < val_len; ++i) {
			int val = val_ary[i];
			int shift = shift_ary[i];
			rv += val << shift;
		}
		return rv;
	}
	public static void Shift_rhs_to_ary(int[] rv, int[] shift_ary, int val) {
		int shift_len = shift_ary.length;
		for (int i = shift_len - 1; i > - 1; --i) {
			int shift = shift_ary[i];
			int itm = val >> shift;				
			rv[i] = itm;
			val -= (itm << shift);
		}
	}
	public static int Xto_int_date_short(int[] val_ary) {
		val_ary[0] -= 1900;
		return Xto_int(Pow_ary_date_short, val_ary);
	}
	public static void Xto_date_short_int_ary(int[] rv, int v) {
		Xto_intAry(rv, Pow_ary_date_short, v);
		rv[0] += 1900;
	}
	public static DateAdp Xto_date_short(int v) {
		int[] rv = new int[Pow_ary_date_short.length];
		Xto_date_short_int_ary(rv, v);
		return DateAdp_.seg_(rv);
	}
	private static final int[] Pow_ary_date_short = new int[] {1048576, 65536, 2048, 64, 1};
	private static final int[] Base2_ary = new int[] 
		{          1,          2,          4,          8,         16,         32,         64,        128
		,        256,        512,       1024,       2048,       4096,       8192,      16384,      32768
		,      65536,     131072,     262144,     524288,    1048576,    2097152,    4194304,    8388608
		,   16777216,   33554432,   67108864,  134217728,  268435456,  536870912, 1073741824,          0
		};
}
