/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx;

public class Type_ {//RF:2017-10-08
	public static Class<?> Type_by_obj(Object o) {return o.getClass();}
	public static Class<?> Type_by_primitive(Object o) {
				Class<?> rv = o.getClass();
		if 		(rv == Integer.class) rv = int.class;
		else if	(rv == Long.class) rv = long.class;
		else if	(rv == Byte.class) rv = byte.class;
		else if	(rv == Short.class) rv = short.class;
		return rv;
			}

	public static boolean Eq_by_obj(Object lhs_obj, Class<?> rhs_type) {
		Class<?> lhs_type = lhs_obj == null ? null : lhs_obj.getClass();
		return Type_.Eq(lhs_type, rhs_type);
	}
	public static boolean Eq(Class<?> lhs, Class<?> rhs) {// DUPE_FOR_TRACKING: same as Object_.Eq
		if		(lhs == null && rhs == null)	return true;
		else if (lhs == null || rhs == null)	return false;
		else									return lhs.equals(rhs);		
	}

	public static String Canonical_name_by_obj(Object o) {return Canonical_name(o.getClass());}
	public static String Canonical_name(Class<?> type) {
		return type.getCanonicalName(); 
	}

	public static String Name_by_obj(Object obj) {return obj == null ? String_.Null_mark : Name(Type_by_obj(obj));}
	public static String Name(Class<?> type) {
		return type.getName();
	}

	public static String SimpleName_by_obj(Object obj) {return obj == null ? String_.Null_mark : SimpleName(Type_by_obj(obj));}
	public static String SimpleName(Class<?> type) {
		return type.getSimpleName();
	}

	public static boolean Is_array(Class<?> t) {
		return t.isArray(); 
	}

	public static boolean Is_assignable_from_by_obj(Object o, Class<?> generic) {return o == null ? false : Is_assignable_from(generic, o.getClass());}
	public static boolean Is_assignable_from(Class<?> generic, Class<?> specific) {
		return generic.isAssignableFrom(specific); 
	}
}
