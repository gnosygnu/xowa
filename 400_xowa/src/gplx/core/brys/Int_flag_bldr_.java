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
public class Int_flag_bldr_ {
	public static int[] Bld_pow_ary(int... ary) {
		int len = ary.length;
		int[] rv = new int[len];
		int pow = 0;
		for (int i = len - 1; i > -1; --i) {
			rv[i] = Int_flag_bldr_.Base2_ary[pow];
			pow += ary[i];			
		}
		return rv;
	}
	public static int To_int(int[] pow_ary, int[] val_ary) {
		int pow_ary_last = pow_ary.length - 1;
		int val = 0;
		for (int i = pow_ary_last; i > -1; i--)
			val += pow_ary[i] * val_ary[i];
		return val;
	}
	public static int[] To_int_ary(int[] pow_ary, int v) {
		int[] rv = new int[pow_ary.length];
		To_int_ary(rv, pow_ary, v);
		return rv;
	}
	public static void To_int_ary(int[] rv, int[] pow_ary, int v) {
		int pow_ary_len = pow_ary.length;
		int rv_len = rv.length;
		for (int i = 0; i < pow_ary_len; i++) {
			if (i >= rv_len) break;
			rv[i] = v / pow_ary[i];
			int factor = pow_ary[i] * rv[i];
			v = factor == 0 ? v : (v % factor);	// NOTE: if 0, do not do modulus or else div by zero
		}
	}
	public static byte To_byte(byte[] pow_ary, byte... val_ary) {
		int pow_ary_last = pow_ary.length - 1;
		int val = 0;
		for (int i = pow_ary_last; i > -1; --i)
			val += pow_ary[i] * val_ary[i];
		return (byte)val;
	}
	public static void To_bry(byte[] rv, byte[] pow_ary, byte val) {
		int pow_ary_len = pow_ary.length;
		int rv_len = rv.length;
		for (int i = 0; i < pow_ary_len; i++) {
			if (i >= rv_len) break;
			rv[i] = (byte)(val / pow_ary[i]);
			int factor = pow_ary[i] * rv[i];
			val = (byte)(factor == 0 ? val : (val % factor));	// NOTE: if 0, do not do modulus or else div by zero
		}
	}
	public static int To_int_date_short(int[] val_ary) {
		val_ary[0] -= 1900;
		return Int_flag_bldr_.To_int(Pow_ary_date_short, val_ary);
	}
	public static void To_date_short_int_ary(int[] rv, int v) {
		Int_flag_bldr_.To_int_ary(rv, Pow_ary_date_short, v);
		rv[0] += 1900;
	}
	public static DateAdp To_date_short(int v) {
		int[] rv = new int[Pow_ary_date_short.length];
		To_date_short_int_ary(rv, v);
		return DateAdp_.seg_(rv);
	}		
	private static final int[] Pow_ary_date_short = new int[] {1048576, 65536, 2048, 64, 1};	// yndhm -> 12,4,5,5,6
	public static final int[] Base2_ary = new int[] 
		{          1,          2,          4,          8,         16,         32,         64,        128
		,        256,        512,       1024,       2048,       4096,       8192,      16384,      32768
		,      65536,     131072,     262144,     524288,    1048576,    2097152,    4194304,    8388608
		,   16777216,   33554432,   67108864,  134217728,  268435456,  536870912, 1073741824,          0
		};
}
