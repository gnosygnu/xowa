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
	public static int To_tid(Object o) {
		if (o == null) return Tid__null;
		Class<?> type = o.getClass();
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
		else if (Type_adp_.Eq(type, Char_.Cls_ref_type))			return Tid__char;
		else														return Tid__obj;
	}
	public static final int
		Tid__obj		=  0
	,	Tid__null		=  1
	,	Tid__bool		=  2
	,	Tid__byte		=  3
	,	Tid__int		=  4
	,	Tid__long		=  5
	,	Tid__float		=  6
	,	Tid__double		=  7
	,	Tid__char		=  8
	,	Tid__str		=  9
	,	Tid__date		= 10
	,	Tid__decimal	= 11
	,	Tid__bry		= 12
	;
}
