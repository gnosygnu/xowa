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
package gplx.ios; import gplx.*;
public class Io_size_ {
	public static String To_str(long val) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)Math_.Pow((long)1024, (long)pow);
		DecimalAdp valDecimal = DecimalAdp_.divide_(val, div);
		String[] unit = Io_size_.Units[pow];
		return valDecimal.Xto_str("#,##0.000") + " " + String_.PadBgn(unit[0], 2, " ");
	}
	public static String To_str(long val, int exp_1024, String val_fmt, String unit_pad, boolean round_0_to_1) {
		long exp_val = (long)Math_.Pow(1024, exp_1024);
		DecimalAdp val_as_decimal = DecimalAdp_.divide_(val, exp_val);
		if (round_0_to_1 && val_as_decimal.Comp_lt(1)) val_as_decimal = DecimalAdp_.One;
		String[] unit = Io_size_.Units[exp_1024];
		return val_as_decimal.Xto_str(val_fmt) + " " + String_.PadBgn(unit[0], 2, unit_pad);
	}
	public static long parse_or_(String raw, long or) {
		if (raw == null || raw == String_.Empty) return or;
		String[] terms = String_.Split(raw, " ");
		int termsLen = Array_.Len(terms); if (termsLen > 2) return or;

		DecimalAdp val = null;
		try {val = DecimalAdp_.parse_(terms[0]);} catch (Exception exc) {Err_.Noop(exc); return or;}

		int unitPow = 0;
		if (termsLen > 1) {
			String unitStr = String_.Upper(terms[1]);
			unitPow = parse_unitPow_(unitStr); if (unitPow == -1) return or;
		}
		int curPow = unitPow;
		while (curPow > 0) {
			val = val.Op_mult(1024);
			curPow--;
		}
		// DELETED:do not check for fractional bytes; EX: 10.7 GB DATE:2015-01-06
		// DecimalAdp comp = val.Op_truncate_decimal();
		// if (!val.Eq(comp)) return or;
		return val.Xto_long();
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
	private static final String[][] Units = new String[][]
	{	String_.Ary("B", "BYTE")
	,	String_.Ary("KB", "KILOBYTE")
	,	String_.Ary("MB", "MEGABYTE")
	,	String_.Ary("GB", "GIGABYTE")
	,	String_.Ary("TB", "TERABYTE")
	,	String_.Ary("PB", "PETABYTE")
	,	String_.Ary("EB", "EXABYTE")
	};
	public static final byte[][] Units_bry = new byte[][] 
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
		long rv = parse_or_(v, Long_.MinValue); if (rv == Long_.MinValue) throw Err_.new_wo_type("invalid val", "val", v);
		return rv;
	}
	public static String	To_str_mb(long v)				{return Long_.Xto_str(v / Io_mgr.Len_mb_long);}
	public static long		To_long_by_int_mb(int v)		{return (long)v * Io_mgr.Len_mb_long;}
	public static long		To_long_by_msg_mb(GfoMsg m, long cur) {
		long val = m.ReadLongOr("v", Int_.MinValue);
		return val == Int_.MinValue ? cur : (val * Io_mgr.Len_mb_long);
	}
}
class Io_size_fmtr_arg implements Bry_fmtr_arg {	
	public long Val() {return val;} public Io_size_fmtr_arg Val_(long v) {val = v; return this;} long val;
	public byte[] Suffix() {return suffix;} public Io_size_fmtr_arg Suffix_(byte[] v) {suffix = v; return this;} private byte[] suffix;
	public void XferAry(Bry_bfr bfr, int idx) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)Math_.Pow((long)1024, (long)pow);		
		DecimalAdp val_decimal = DecimalAdp_.divide_(val, div);
		bfr.Add_str(val_decimal.Xto_str("#,###.000")).Add_byte(Byte_ascii.Space).Add(gplx.ios.Io_size_.Units_bry[pow]);
		if (suffix != null) 
			bfr.Add(suffix);
	}
}
