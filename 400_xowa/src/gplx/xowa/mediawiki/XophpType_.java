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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class XophpType_ {
	public static int To_type_id(Object o) {
		int type_id = Type_ids_.To_id_by_obj(o);
		switch (type_id) {
			case Type_ids_.Id__bry:
				type_id = Type_ids_.Id__str;
				break;
			case Type_ids_.Id__obj:
				if (Type_.Eq_by_obj(o, XophpArray.class)) {
					type_id = Type_ids_.Id__array;
				}
				break;
		}
		return type_id;
	}
	public static boolean is_scalar(Object o) { // REF.PHP: https://www.php.net/manual/en/function.is-scalar.php
		if (o == null) return false;
		int type_id = To_type_id(o);
		switch (type_id) {
			case Type_ids_.Id__int:
			case Type_ids_.Id__float:
			case Type_ids_.Id__str:
			case Type_ids_.Id__bool:
				return true;
			default:
				return false;
		}
	}
	public static boolean is_string(Object o) {
		return To_type_id(o) == Type_ids_.Id__str;
	}
	public static boolean is_array(Object o) {
		return Type_.Eq_by_obj(o, XophpArray.class);
	}
	public static boolean instance_of(Object o, Class<?> t) {
		return Type_.Eq_by_obj(o, t) || Type_.Is_assignable_from_by_obj(o, t);
	}

	public static Class<?> get_class(Object o) {return o.getClass();}
	public static String To_str(Class c) {return c.getCanonicalName();}
}
