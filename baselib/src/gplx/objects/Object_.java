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
package gplx.objects; import gplx.*;
import gplx.objects.brys.*;
import gplx.objects.strings.*;
import gplx.objects.types.*;
public class Object_ {
	public static String To_str_or_null_mark(Object v) {return v == null ? "<<NULL>>": To_str(v);}
	public static String To_str_or(Object v, String or) {return v == null ? or : To_str(v);}
	public static String To_str(Object v) {
		Class<?> c = v.getClass();
		if      (Type_.Eq(c, String_.Cls_ref_type)) return (String)v;
		else if	(Type_.Eq(c, Bry_.Cls_ref_type))    return String_.New_bry_utf8((byte[])v);
		else                                        return v.toString(); 
	}
	public static boolean Eq(Object lhs, Object rhs) {
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		else                                 return lhs.equals(rhs);
	}
}
