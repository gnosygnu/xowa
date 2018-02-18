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
package gplx;
public class Type_ids_ {//RF:2017-10-08
	public static final int // SERIALIZABLE.N
	  Id__obj            =  0
	, Id__null           =  1
	, Id__bool           =  2
	, Id__byte           =  3
	, Id__short          =  4
	, Id__int            =  5
	, Id__long           =  6
	, Id__float          =  7
	, Id__double         =  8
	, Id__char           =  9
	, Id__str            = 10
	, Id__bry            = 11
	, Id__date           = 12
	, Id__decimal        = 13
	, Id__array          = 14
	;

	public static int To_id_by_obj(Object o) {
		if (o == null) return Type_ids_.Id__null;
		Class<?> type = o.getClass();
		return Type_ids_.To_id_by_type(type);
	}

	public static int To_id_by_type(Class<?> type) {
		if		(Type_.Eq(type, Int_.Cls_ref_type))                 return Id__int;
		else if (Type_.Eq(type, String_.Cls_ref_type))              return Id__str;
		else if (Type_.Eq(type, byte[].class))                    return Id__bry;
		else if (Type_.Eq(type, Bool_.Cls_ref_type))                return Id__bool;
		else if (Type_.Eq(type, Byte_.Cls_ref_type))                return Id__byte;
		else if (Type_.Eq(type, Long_.Cls_ref_type))                return Id__long;
		else if (Type_.Eq(type, Double_.Cls_ref_type))              return Id__double;
		else if (Type_.Eq(type, Decimal_adp_.Cls_ref_type))         return Id__decimal;
		else if (Type_.Eq(type, DateAdp_.Cls_ref_type))             return Id__date;
		else if (Type_.Eq(type, Float_.Cls_ref_type))               return Id__float;
		else if (Type_.Eq(type, Short_.Cls_ref_type))               return Id__short;
		else if (Type_.Eq(type, Char_.Cls_ref_type))                return Id__char;
		else                                                        return Id__obj;
	}
}
