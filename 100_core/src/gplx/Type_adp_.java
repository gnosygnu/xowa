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
public class Type_adp_ {
	public static boolean Eq(Class<?> lhs, Class<?> rhs) {
		if		(lhs == null && rhs == null)	return true;
		else if (lhs == null || rhs == null)	return false;
		else									return lhs.equals(rhs);		
	}
	public static boolean Eq_typeSafe(Object o, Class<?> expd) {if (o == null) return false;
		Class<?> actl = o.getClass();
		return Object_.Eq(expd, actl);
	}
	public static boolean IsAssignableFrom(Class<?> lhs, Class<?> rhs) {return lhs.isAssignableFrom(rhs);}	
	public static boolean Implements_intf_obj(Object cur, Class<?> type) {return cur == null ? false : IsAssignableFrom(type, cur.getClass());}
	public static boolean Is_array(Class<?> t) {return t.isArray();}		
	public static Class<?> ClassOf_obj(Object o) {return o.getClass();}
	public static Class<?> ClassOf_primitive(Object o) {
				Class<?> rv = o.getClass();
		if 		(rv == Integer.class) rv = int.class;
		else if	(rv == Long.class) rv = long.class;
		else if	(rv == Byte.class) rv = byte.class;
		else if	(rv == Short.class) rv = short.class;
		return rv;
			}
	public static String FullNameOf_obj(Object o) {return FullNameOf_type(o.getClass());}	
	public static String FullNameOf_type(Class<?> type) {return type.getCanonicalName();}	
	public static String NameOf_type(Class<?> type) {return type.getName();}	
	public static String NameOf_obj(Object obj) {return obj == null ? String_.Null_mark : obj.getClass().getName();}	
	public static int To_tid_obj(Object o) {
		if (o == null) return Tid__null;
		Class<?> type = o.getClass();
		return To_tid_type(type);
	}
	public static int To_tid_type(Class<?> type) {
		if		(Type_adp_.Eq(type, Int_.Cls_ref_type))				return Tid__int;
		else if (Type_adp_.Eq(type, String_.Cls_ref_type))			return Tid__str;
		else if (Type_adp_.Eq(type, byte[].class))				return Tid__bry;
		else if (Type_adp_.Eq(type, Bool_.Cls_ref_type))			return Tid__bool;
		else if (Type_adp_.Eq(type, Byte_.Cls_ref_type))			return Tid__byte;
		else if (Type_adp_.Eq(type, Long_.Cls_ref_type))			return Tid__long;
		else if (Type_adp_.Eq(type, Double_.Cls_ref_type))			return Tid__double;
		else if (Type_adp_.Eq(type, Decimal_adp_.Cls_ref_type))		return Tid__decimal;
		else if (Type_adp_.Eq(type, DateAdp_.Cls_ref_type))			return Tid__date;
		else if (Type_adp_.Eq(type, Float_.Cls_ref_type))			return Tid__float;
		else if (Type_adp_.Eq(type, Short_.Cls_ref_type))			return Tid__short;
		else if (Type_adp_.Eq(type, Char_.Cls_ref_type))			return Tid__char;
		else														return Tid__obj;
	}
	public static int To_tid(String name) {
		if		(String_.Eq(name, Int_.Cls_val_name))				return Tid__int;
		else if (String_.Eq(name, String_.Cls_val_name))			return Tid__str;
		else if (String_.Eq(name, Bry_.Cls_val_name))				return Tid__bry;
		else if (String_.Eq(name, Bool_.Cls_val_name))				return Tid__bool;
		else if (String_.Eq(name, Byte_.Cls_val_name))				return Tid__byte;
		else if (String_.Eq(name, Long_.Cls_val_name))				return Tid__long;
		else if (String_.Eq(name, Double_.Cls_val_name))			return Tid__double;
		else if (String_.Eq(name, Decimal_adp_.Cls_val_name))		return Tid__decimal;
		else if (String_.Eq(name, DateAdp_.Cls_ref_name))			return Tid__date;
		else if (String_.Eq(name, Float_.Cls_val_name))				return Tid__float;
		else if (String_.Eq(name, Short_.Cls_val_name))				return Tid__short;
		else if (String_.Eq(name, Char_.Cls_val_name))				return Tid__char;
		else														return Tid__obj;
	}
	public static final int
	  Tid__obj		=  0
	, Tid__null		=  1
	, Tid__bool		=  2
	, Tid__byte		=  3
	, Tid__short	=  4
	, Tid__int		=  5
	, Tid__long		=  6
	, Tid__float	=  7
	, Tid__double	=  8
	, Tid__char		=  9
	, Tid__str		= 10
	, Tid__bry		= 11
	, Tid__date		= 12
	, Tid__decimal	= 13
	;
}
