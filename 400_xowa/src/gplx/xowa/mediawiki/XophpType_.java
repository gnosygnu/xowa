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
package gplx.xowa.mediawiki;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
public class XophpType_ {
	public static int To_type_id(Object o) {
		int type_id = TypeIds.ToIdByObj(o);
		switch (type_id) {
			case TypeIds.IdBry:
				type_id = TypeIds.IdStr;
				break;
			case TypeIds.IdObj:
				if (ClassUtl.EqByObj(XophpArray.class, o)) {
					type_id = TypeIds.IdArray;
				}
				break;
		}
		return type_id;
	}
	public static boolean is_scalar(Object o) { // REF.PHP: https://www.php.net/manual/en/function.is-scalar.php
		if (o == null) return false;
		int type_id = To_type_id(o);
		switch (type_id) {
			case TypeIds.IdInt:
			case TypeIds.IdFloat:
			case TypeIds.IdStr:
			case TypeIds.IdBool:
				return true;
			default:
				return false;
		}
	}
	public static boolean is_string(Object o) {
		return To_type_id(o) == TypeIds.IdStr;
	}
	public static boolean is_array(Object o) {
		return ClassUtl.EqByObj(XophpArray.class, o);
	}
	public static boolean instance_of(Object o, Class<?> t) {
		return ClassUtl.EqByObj(t, o) || ClassUtl.IsAssignableFromByObj(o, t);
	}

	public static Class<?> get_class(Object o) {return o.getClass();}
	public static String To_str(Class c) {return c.getCanonicalName();}
}
