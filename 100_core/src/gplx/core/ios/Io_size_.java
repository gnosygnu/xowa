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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.brys.*;
public class Io_size_ {
	public static String To_str(long val) {return To_str(val, "#,##0.000");}
	public static String To_str(long val, String val_fmt) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)Math_.Pow((long)1024, (long)pow);
		Decimal_adp valDecimal = Decimal_adp_.divide_(val, div);
		String[] unit = Io_size_.Units[pow];
		return valDecimal.To_str(val_fmt) + " " + String_.PadBgn(unit[0], 2, " ");
	}
	public static String To_str(long val, int exp_1024, String val_fmt, String unit_pad, boolean round_0_to_1) {
		long exp_val = (long)Math_.Pow(1024, exp_1024);
		Decimal_adp val_as_decimal = Decimal_adp_.divide_(val, exp_val);
		if (round_0_to_1 && val_as_decimal.Comp_lt(1)) val_as_decimal = Decimal_adp_.One;
		String[] unit = Io_size_.Units[exp_1024];
		return val_as_decimal.To_str(val_fmt) + " " + String_.PadBgn(unit[0], 2, unit_pad);
	}
	public static String To_str_new(Bry_bfr bfr, long val, int decimal_places) {To_bfr_new(bfr, val, decimal_places); return bfr.To_str_and_clear();}
	public static void To_bfr_new(Bry_bfr bfr, long val, int decimal_places) {
		// init
		int unit_idx = 0;
		int mult = 1024;
		long cur_val = val;
		long cur_exp = 1;
		long nxt_exp = mult;

		// get 1024 mult; EX: 1549 -> 1024
		for (unit_idx = 0; unit_idx < 6; ++unit_idx) {
			if (cur_val < nxt_exp) break;
			cur_exp = nxt_exp;
			nxt_exp *= mult;
		}

		// calc integer / decimal values
		int int_val = (int)(val / cur_exp);
		int dec_val = (int)(val % cur_exp);
		if (decimal_places == 0) {	// if 0 decimal places, round up
			if (dec_val >= .5) ++int_val;
			dec_val = 0;
		}
		else {// else, calculate decimal value as integer; EX: 549 -> .512 -> 512
			long dec_factor = 0;
			switch (decimal_places) {
				case 1: dec_factor =   10; break;
				case 2: dec_factor =  100; break;
				default:
				case 3: dec_factor = 1000; break;
			}
			dec_val = (int)((dec_val * dec_factor) / cur_exp);
		}

		// calc unit_str
		String unit_str = "";
		switch (unit_idx) {
			case 0:		unit_str = " B";  break;
			case 1:		unit_str = " KB"; break;
			case 2:		unit_str = " MB"; break;
			case 3:		unit_str = " GB"; break;
			case 4:		unit_str = " PB"; break;
			case 5: 
			default:	unit_str = " EB"; break;
		}

		// build String
		bfr.Add_long_variable(int_val);
		if (decimal_places > 0 && unit_idx != 0) {
			bfr.Add_byte_dot();
			bfr.Add_long_variable(dec_val);
		}
		bfr.Add_str_a7(unit_str);
	}
	public static long parse_or(String raw, long or) {
		if (raw == null || raw == String_.Empty) return or;
		String[] terms = String_.Split(raw, " ");
		int termsLen = Array_.Len(terms); if (termsLen > 2) return or;

		Decimal_adp val = null;
		try {val = Decimal_adp_.parse(terms[0]);} catch (Exception exc) {Err_.Noop(exc); return or;}

		int unitPow = 0;
		if (termsLen > 1) {
			String unitStr = String_.Upper(terms[1]);
			unitPow = parse_unitPow_(unitStr); if (unitPow == -1) return or;
		}
		int curPow = unitPow;
		while (curPow > 0) {
			val = val.Multiply(1024);
			curPow--;
		}
		// DELETED:do not check for fractional bytes; EX: 10.7 GB DATE:2015-01-06
		// Decimal_adp comp = val.Op_truncate_decimal();
		// if (!val.Eq(comp)) return or;
		return val.To_long();
	}
	private static int parse_unitPow_(String unitStr) {
		int unitLen = Array_.Len(Units);
		int unitPow = -1;
		for (int i = 0; i < unitLen; i++) {
			if (String_.Eq(unitStr, String_.Upper(Units[i][0]))) return i;
			if (String_.Eq(unitStr, String_.Upper(Units[i][1]))) return i;
		}
		return unitPow;
	}
	private static final    String[][] Units = new String[][]
	{	String_.Ary("B", "BYTE")
	,	String_.Ary("KB", "KILOBYTE")
	,	String_.Ary("MB", "MEGABYTE")
	,	String_.Ary("GB", "GIGABYTE")
	,	String_.Ary("TB", "TERABYTE")
	,	String_.Ary("PB", "PETABYTE")
	,	String_.Ary("EB", "EXABYTE")
	};
	public static final    byte[][] Units_bry = new byte[][] 
	{	Bry_.new_a7("B")
	,	Bry_.new_a7("KB")
	,	Bry_.new_a7("MB")
	,	Bry_.new_a7("GB")
	,	Bry_.new_a7("TB")
	,	Bry_.new_a7("PB")
	,	Bry_.new_a7("EB")
	};
	public static int	Load_int_(GfoMsg m) {return (int)Load_long_(m);}
	public static long	Load_long_(GfoMsg m) {
		String v = m.ReadStr("v");
		long rv = parse_or(v, Long_.Min_value); if (rv == Long_.Min_value) throw Err_.new_wo_type("invalid val", "val", v);
		return rv;
	}
	public static String	To_str_mb(long v)				{return Long_.To_str(v / Io_mgr.Len_mb_long);}
	public static long		To_long_by_int_mb(int v)		{return (long)v * Io_mgr.Len_mb_long;}
	public static long		To_long_by_msg_mb(GfoMsg m, long cur) {
		long val = m.ReadLongOr("v", Int_.Min_value);
		return val == Int_.Min_value ? cur : (val * Io_mgr.Len_mb_long);
	}
}
class Io_size_fmtr_arg implements Bfr_arg {
	public long Val() {return val;} public Io_size_fmtr_arg Val_(long v) {val = v; return this;} long val;
	public byte[] Suffix() {return suffix;} public Io_size_fmtr_arg Suffix_(byte[] v) {suffix = v; return this;} private byte[] suffix;
	public void Bfr_arg__add(Bry_bfr bfr) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)Math_.Pow((long)1024, (long)pow);		
		Decimal_adp val_decimal = Decimal_adp_.divide_(val, div);
		bfr.Add_str_a7(val_decimal.To_str("#,###.000")).Add_byte(Byte_ascii.Space).Add(gplx.core.ios.Io_size_.Units_bry[pow]);
		if (suffix != null) 
			bfr.Add(suffix);
	}
}
