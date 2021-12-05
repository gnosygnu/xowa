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
package gplx.objects.primitives;
import gplx.objects.ObjectUtl;
import gplx.objects.arrays.BryUtl;
import gplx.objects.errs.ErrUtl;
import gplx.objects.lists.CompareAbleUtl;
import gplx.objects.strings.AsciiByte;
import gplx.objects.strings.StringUtl;
public class BoolUtl {
	public static final String ClsValName = "bool";
	public static final Class<?> ClsRefType = Boolean.class;

	public static final boolean    N        = false , Y        = true;
	public static final byte       NByte    = 0     , YByte    = 1      , NullByte = 127;
	public static final int        NInt     = 0     , YInt     = 1      , NullInt =  -1;
	public static final String     TrueStr  = "true", FalseStr = "false";
	public static final byte[]     NBry     = new byte[] {AsciiByte.Ltr_n}, YBry     = new byte[] {AsciiByte.Ltr_y};
	public static final byte[]     TrueBry  = BryUtl.NewA7(TrueStr)       , FalseBry = BryUtl.NewA7(FalseStr);
	public static boolean ByInt(int v)    {return v == YInt;}
	public static int ToInt(boolean v)    {return v ? YInt : NInt;}
	public static byte ToByte(boolean v)  {return v ? YByte : NByte;}
	public static String ToStrLower(boolean v)    {return v ? TrueStr : FalseStr;}
	public static boolean Cast(Object o) {
		try {
			return (Boolean)o;
		} 
		catch (Exception e) {
			throw ErrUtl.NewFmt(e, "failed to cast to boolean; obj={0}", ObjectUtl.ToStr(o));
		}
	}
	public static boolean Parse(String raw) {
		if      (  StringUtl.Eq(raw, TrueStr)
				|| StringUtl.Eq(raw, "True")    // needed for Store_Wtr(){boolVal.toString();}
				)
			return true;
		else if (  StringUtl.Eq(raw, FalseStr)
				|| StringUtl.Eq(raw, "False")
				)
			return false;
		else
			throw ErrUtl.NewParse(boolean.class, raw);
	}
	public static int Compare(boolean lhs, boolean rhs) {
		if      ( lhs ==  rhs)  return CompareAbleUtl.Same;
		else if (!lhs &&  rhs)  return CompareAbleUtl.Less;
		else    /*lhs && !rhs*/ return CompareAbleUtl.More;
	}
}
