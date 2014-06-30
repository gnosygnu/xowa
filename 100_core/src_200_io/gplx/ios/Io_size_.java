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
	public static String Xto_str(long val) {
		long cur = val; int pow = 0;
		while (cur >= 1024) {
			cur /= 1024;
			pow++;
		}
		long div = (long)Math_.Pow((long)1024, (long)pow);
		DecimalAdp valDecimal = DecimalAdp_.divide_(val, div);
		String[] unit = Io_size_.Units[pow];
		return valDecimal.XtoStr("#,###.000") + " " + String_.PadBgn(unit[0], 2, " ");
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
		DecimalAdp comp = val.Op_truncate_decimal();
		if (!val.Eq(comp)) return or;
		return val.XtoLong();
	}
	static int parse_unitPow_(String unitStr) {
		int unitLen = Array_.Len(Units);
		int unitPow = -1;
		for (int i = 0; i < unitLen; i++) {
			if (String_.Eq(unitStr, String_.Upper(Units[i][0]))) return i;
			if (String_.Eq(unitStr, String_.Upper(Units[i][1]))) return i;
		}
		return unitPow;
	}
	static String UnitsXtoStr() {
		String_bldr sb = String_bldr_.new_();
		int len = Array_.Len(Units);
		for (int i = 0; i < len; i++) {
			String[] eny = Units[i];
			sb.Add_fmt("{0},{1};", eny[0], eny[1]);
		}
		return sb.XtoStr();
	}
	static final String[][] Units = new String[][]
	{	String_.Ary("B", "BYTE")
	,	String_.Ary("KB", "KILOBYTE")
	,	String_.Ary("MB", "MEGABYTE")
	,	String_.Ary("GB", "GIGABYTE")
	,	String_.Ary("TB", "TERABYTE")
	,	String_.Ary("PB", "PETABYTE")
	,	String_.Ary("EB", "EXABYTE")
	};
	public static final byte[][] Units_bry = new byte[][] 
	{	Bry_.new_ascii_("B")
	,	Bry_.new_ascii_("KB")
	,	Bry_.new_ascii_("MB")
	,	Bry_.new_ascii_("GB")
	,	Bry_.new_ascii_("TB")
	,	Bry_.new_ascii_("PB")
	,	Bry_.new_ascii_("EB")
	};
	public static int	Load_int_(GfoMsg m) {return (int)Load_long_(m);}
	public static long	Load_long_(GfoMsg m) {
		String v = m.ReadStr("v");
		long rv = parse_or_(v, Long_.MinValue); if (rv == Long_.MinValue) throw Err_.new_fmt_("invalid val: {0}", v);
		return rv;
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
		bfr.Add_str(val_decimal.XtoStr("#,###.000")).Add_byte(Byte_ascii.Space).Add(gplx.ios.Io_size_.Units_bry[pow]);
		if (suffix != null) 
			bfr.Add(suffix);
	}
}
