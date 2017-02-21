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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bit_ {
	public static String ToBitStr(int val) {
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
    		return String_.new_a7(rv);
	}
	public static int Get_flag(int i) {return Int_flag_bldr_.Base2_ary[i];}
	public static int Shift_lhs(int val, int shift) {return val << shift;}
	public static int Shift_rhs(int val, int shift) {return val >> shift;}
	public static int Shift_lhs_to_int(int[] shift_ary, int... val_ary) {
		int val_len = val_ary.length; if (val_len > shift_ary.length) throw Err_.new_wo_type("vals must be less than shifts", "vals", val_len, "shifts", shift_ary.length);
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
}
