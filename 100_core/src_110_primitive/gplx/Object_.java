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
public class Object_ {
	public static final Object[] Ary_empty = new Object[0];
	public static Object[] Ary(Object... ary) {return ary;}
	public static boolean Eq(Object lhs, Object rhs) {
		if		(lhs == null && rhs == null)	return true;
		else if (lhs == null || rhs == null)	return false;
		else									return lhs.equals(rhs);		
	}
	public static Object Parse(String val, String valType) {
		if		(String_.Eq(valType, IntClassXtn.Key_const)) return Int_.parse_(val);
		else	return val;
	}
	public static String XtoStr_OrNull(Object v)		{return v == null ? null			: ToString_lang(v);}
	public static String XtoStr_OrNullStr(Object v)		{return v == null ? String_.Null_mark	: ToString_lang(v);}
	public static String XtoStr_OrEmpty(Object v)		{return v == null ? String_.Empty	: ToString_lang(v);}
	static String ToString_lang(Object v) {
		if (v == null) return null;
		Class<?> c = v.getClass();
		if		(ClassAdp_.Eq(c, Bry_.ClassOf))	return String_.new_utf8_((byte[])v);
		else if (ClassAdp_.Eq(c, String_.ClassOf))	return (String)v;
		else										return v.toString();	
	}
}