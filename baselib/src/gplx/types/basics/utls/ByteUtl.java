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
package gplx.types.basics.utls;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.errs.ErrUtl;
public class ByteUtl {
	public static final String ClsValName = "byte";
	public static final Class<?> ClsRefType = Byte.class;
	public static final byte Zero = 0, MinValue = Byte.MIN_VALUE, MaxValue127 = 127;
	public static byte Cast(Object o) {try {return (Byte)o;} catch (Exception e) {throw ErrUtl.NewCast(byte.class, o);}}
	public static byte ByInt(int v)    {return v > 127 ? (byte)(v - 256) : (byte)v;} // PERF?: (byte)(v & 0xff)
	public static int ToInt(byte v)    {return v < 0 ? (int)v + 256 : v;}
	public static String ToStr(byte v) {return new Byte(v).toString();}
	public static byte[] ToBry(byte v) {return new byte[] {v};}
	public static byte Parse(String raw) {return Byte.parseByte(raw);}
	public static byte ParseOr(String raw, byte or) {
		try {
			return raw == null
				? or
				: ByteUtl.Parse(raw);
		}
		catch (Exception e) {return or;}
	}
	public static boolean EqAny(byte v, byte... ary) {
		for (byte itm : ary)
			if (v == itm) return true;
		return false;
	}
	public static boolean EqAll(byte v, byte... ary) {
		for (byte itm : ary)
			if (v != itm) return false;
		return true;
	}
	public static int Compare(byte lhs, byte rhs) {
		if      (lhs == rhs) return CompareAbleUtl.Same;
		else if (lhs <  rhs) return CompareAbleUtl.Less;
		else                 return CompareAbleUtl.More;
	}
	public static byte[] AryByInts(int... ary) {
		int ary_len = ary.length;
		byte[] rv = new byte[ary_len];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = ByteUtl.ByInt(ary[i]);
		}
		return rv;
	}
}
