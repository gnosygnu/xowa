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
public class ObjectUtl extends ObjectLni {
	public static Object[] Ary(Object... ary) {return ary;}
	public static Object[] AryAdd(Object[] lhs, Object[] rhs) {
		int lhsLen = lhs.length, rhsLen = rhs.length;
		if      (lhsLen == 0) return rhs;
		else if (rhsLen == 0) return lhs;
		int rvLen = lhsLen + rhsLen;
		Object[] rv = new Object[rvLen];
		for (int i = 0; i < lhsLen; ++i)
			rv[i] = lhs[i];
		for (int i = lhsLen; i < rvLen; ++i)
			rv[i] = rhs[i - lhsLen];
		return rv;
	}
	public static String ToStrOr(Object v, String or) {return v == null ? or                 : ToStr(v);}
	public static String ToStrOrNull(Object v)        {return v == null ? null               : ToStr(v);}
	public static String ToStrOrEmpty(Object v)       {return v == null ? StringUtl.Empty    : ToStr(v);}
	public static String ToStrLooseOr(Object v, String or) {    // tries to pretty-print doubles; also standardizes true/false; DATE:2014-07-14
		if (v == null) return null;
		Class<?> c = ClassUtl.TypeByObj(v);
		if      (ClassUtl.Eq(c, StringUtl.ClsRefType)) return (String)v;
		else if (ClassUtl.Eq(c, BryUtl.ClsRefType))    return StringUtl.NewU8((byte[])v);
		else if (ClassUtl.Eq(c, BoolUtl.ClsRefType))   return BoolUtl.Cast(v) ? BoolUtl.TrueStr : BoolUtl.FalseStr; // always return "true" / "false"
		else if (ClassUtl.Eq(c, DoubleUtl.ClsRefType)) return DoubleUtl.ToStrLoose(DoubleUtl.Cast(v));
		else                                          return v.toString();
	}
}
