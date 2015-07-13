/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public class ClassAdp_ {
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
	public static final byte Tid_bool = 1, Tid_byte = 2, Tid_int = 3, Tid_long = 4, Tid_float = 5, Tid_double = 6, Tid_char = 7, Tid_str = 8, Tid_date = 9, Tid_decimal = 10;
}
