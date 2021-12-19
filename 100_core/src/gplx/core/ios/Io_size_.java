/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.frameworks.invks.GfoMsg;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
public class Io_size_ {
	public static String To_str(long val) {return To_str(val, "#,##0.000");}
	public static String To_str(long val, String val_fmt) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)MathUtl.Pow((long)1024, (long)pow);
		GfoDecimal valDecimal = GfoDecimalUtl.NewByDivide(val, div);
		String[] unit = Io_size_.Units[pow];
		return valDecimal.ToStr(val_fmt) + " " + StringUtl.PadBgn(unit[0], 2, " ");
	}
	public static String To_str(long val, int exp_1024, String val_fmt, String unit_pad, boolean round_0_to_1) {
		long exp_val = (long)MathUtl.Pow(1024, exp_1024);
		GfoDecimal val_as_decimal = GfoDecimalUtl.NewByDivide(val, exp_val);
		if (round_0_to_1 && val_as_decimal.CompLt(1)) val_as_decimal = GfoDecimalUtl.One;
		String[] unit = Io_size_.Units[exp_1024];
		return val_as_decimal.ToStr(val_fmt) + " " + StringUtl.PadBgn(unit[0], 2, unit_pad);
	}
	public static String To_str_new(BryWtr bfr, long val, int decimal_places) {To_bfr_new(bfr, val, decimal_places); return bfr.ToStrAndClear();}
	public static void To_bfr_new(BryWtr bfr, long val, int decimal_places) {
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
		if (decimal_places == 0) {    // if 0 decimal places, round up
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
			case 0:        unit_str = " B";  break;
			case 1:        unit_str = " KB"; break;
			case 2:        unit_str = " MB"; break;
			case 3:        unit_str = " GB"; break;
			case 4:        unit_str = " PB"; break;
			case 5: 
			default:    unit_str = " EB"; break;
		}

		// build String
		bfr.AddLongVariable(int_val);
		if (decimal_places > 0 && unit_idx != 0) {
			bfr.AddByteDot();
			bfr.AddLongVariable(dec_val);
		}
		bfr.AddStrA7(unit_str);
	}
	public static long parse_or(String raw, long or) {
		if (raw == null || raw == StringUtl.Empty) return or;
		String[] terms = StringUtl.Split(raw, " ");
		int termsLen = ArrayUtl.Len(terms); if (termsLen > 2) return or;

		GfoDecimal val = null;
		try {val = GfoDecimalUtl.Parse(terms[0]);} catch (Exception exc) {return or;}

		int unitPow = 0;
		if (termsLen > 1) {
			String unitStr = StringUtl.Upper(terms[1]);
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
		return val.ToLong();
	}
	private static int parse_unitPow_(String unitStr) {
		int unitLen = ArrayUtl.Len(Units);
		int unitPow = -1;
		for (int i = 0; i < unitLen; i++) {
			if (StringUtl.Eq(unitStr, StringUtl.Upper(Units[i][0]))) return i;
			if (StringUtl.Eq(unitStr, StringUtl.Upper(Units[i][1]))) return i;
		}
		return unitPow;
	}
	private static final String[][] Units = new String[][]
	{    StringUtl.Ary("B", "BYTE")
	,    StringUtl.Ary("KB", "KILOBYTE")
	,    StringUtl.Ary("MB", "MEGABYTE")
	,    StringUtl.Ary("GB", "GIGABYTE")
	,    StringUtl.Ary("TB", "TERABYTE")
	,    StringUtl.Ary("PB", "PETABYTE")
	,    StringUtl.Ary("EB", "EXABYTE")
	};
	public static final byte[][] Units_bry = new byte[][]
	{    BryUtl.NewA7("B")
	,    BryUtl.NewA7("KB")
	,    BryUtl.NewA7("MB")
	,    BryUtl.NewA7("GB")
	,    BryUtl.NewA7("TB")
	,    BryUtl.NewA7("PB")
	,    BryUtl.NewA7("EB")
	};
	public static int    Load_int_(GfoMsg m) {return (int)Load_long_(m);}
	public static long    Load_long_(GfoMsg m) {
		String v = m.ReadStr("v");
		long rv = parse_or(v, LongUtl.MinValue); if (rv == LongUtl.MinValue) throw ErrUtl.NewArgs("invalid val", "val", v);
		return rv;
	}
	public static String    To_str_mb(long v)                {return LongUtl.ToStr(v / IoConsts.LenMBAsLong);}
	public static long        To_long_by_int_mb(int v)        {return (long)v * IoConsts.LenMBAsLong;}
	public static long        To_long_by_msg_mb(GfoMsg m, long cur) {
		long val = m.ReadLongOr("v", IntUtl.MinValue);
		return val == IntUtl.MinValue ? cur : (val * IoConsts.LenMBAsLong);
	}
}
class Io_size_fmtr_arg implements BryBfrArg {
	public long Val() {return val;} public Io_size_fmtr_arg Val_(long v) {val = v; return this;} long val;
	public byte[] Suffix() {return suffix;} public Io_size_fmtr_arg Suffix_(byte[] v) {suffix = v; return this;} private byte[] suffix;
	public void AddToBfr(BryWtr bfr) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)MathUtl.Pow((long)1024, (long)pow);
		GfoDecimal val_decimal = GfoDecimalUtl.NewByDivide(val, div);
		bfr.AddStrA7(val_decimal.ToStr("#,###.000")).AddByte(AsciiByte.Space).Add(gplx.core.ios.Io_size_.Units_bry[pow]);
		if (suffix != null) 
			bfr.Add(suffix);
	}
}
