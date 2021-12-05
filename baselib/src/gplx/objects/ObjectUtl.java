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
package gplx.objects;
import gplx.objects.arrays.BryUtl;
import gplx.objects.strings.StringUtl;
import gplx.objects.types.TypeUtl;
public class ObjectUtl {
	public static String ToStrOrNullMark(Object v) {return v == null ? "<<NULL>>": ToStr(v);}
	public static String ToStrOr(Object v, String or) {return v == null ? or : ToStr(v);}
	public static String ToStr(Object v) {
		Class<?> c = v.getClass();
		if      (TypeUtl.Eq(c, StringUtl.ClsRefType)) return (String)v;
		else if (TypeUtl.Eq(c, BryUtl.ClsRefType))    return StringUtl.NewByBryUtf8((byte[])v);
		else                                          return v.toString();
	}
	public static boolean Eq(Object lhs, Object rhs) {
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		else                                 return lhs.equals(rhs);
	}
}
