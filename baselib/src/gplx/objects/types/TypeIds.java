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
package gplx.objects.types;
import gplx.objects.arrays.BryUtl;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.primitives.ByteUtl;
import gplx.objects.primitives.CharUtl;
import gplx.objects.primitives.DoubleUtl;
import gplx.objects.primitives.FloatUtl;
import gplx.objects.primitives.IntUtl;
import gplx.objects.primitives.LongUtl;
import gplx.objects.primitives.ShortUtl;
import gplx.objects.strings.StringUtl;
public class TypeIds {
	public static final int // SERIALIZABLE.N
		IdObj = 0,
		IdNull = 1,
		IdBool = 2,
		IdByte = 3,
		IdShort = 4,
		IdInt = 5,
		IdLong = 6,
		IdFloat = 7,
		IdDouble = 8,
		IdChar = 9,
		IdStr = 10,
		IdBry = 11,
		IdDate = 12,
		IdDecimal = 13,
		IdArray = 14;

	public static int ToIdByObj(Object o) {
		if (o == null) return TypeIds.IdNull;
		Class<?> type = o.getClass();
		return TypeIds.ToIdByCls(type);
	}
	public static int ToIdByCls(Class<?> type) {
		if      (TypeUtl.Eq(type, IntUtl.ClsRefType))     return IdInt;
		else if (TypeUtl.Eq(type, StringUtl.ClsRefType))  return IdStr;
		else if (TypeUtl.Eq(type, BryUtl.ClsRefType))     return IdBry;
		else if (TypeUtl.Eq(type, BoolUtl.ClsRefType))    return IdBool;
		else if (TypeUtl.Eq(type, ByteUtl.ClsRefType))    return IdByte;
		else if (TypeUtl.Eq(type, LongUtl.ClsRefType))    return IdLong;
		else if (TypeUtl.Eq(type, DoubleUtl.ClsRefType))  return IdDouble;
		else if (TypeUtl.Eq(type, FloatUtl.ClsRefType))   return IdFloat;
		else if (TypeUtl.Eq(type, ShortUtl.ClsRefType))   return IdShort;
		else if (TypeUtl.Eq(type, CharUtl.ClsRefType))    return IdChar;
		// else if (TypeUtl.Eq(type, DecimalUtl.ClsRefType)) return IdDecimal;
		// else if (TypeUtl.Eq(type, DateUtl.ClsRefType))    return IdDate;
		else                                            return IdObj;
	}
}
