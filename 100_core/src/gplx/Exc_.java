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
public class Exc_ {
	private static String Type__none = ""; @gplx.Internal protected static String Stack_null = null;
	public static final String Type__op_canceled = "gplx.op_canceled";
	public static void Noop(Exception e) {}
	public static Exc as_(Object obj) {return obj instanceof Exc ? (Exc)obj : null;}
	public static Exc new_(String msg, Object... args)						{return new Exc(Stack_null, Type__none, msg, args);}
	public static Exc new_w_type(String type, String msg, Object... args)		{return new Exc(Stack_null, type, msg, args);}
	public static Exc new_exc(Exception e, String type, String msg, Object... args) {
		Exc rv = ClassAdp_.Eq_typeSafe(e, Exc.class) ? (Exc)e : new Exc(Exc_.Stack_lang(e), Type__none, Exc_.Message_lang(e));
		rv.Msgs_add(type, msg, args);
		return rv;
	}
	public static Exc new_unhandled(Object val)										{return new Exc(Stack_null, Type__none, "val is not in switch/if", "val", val);}
	public static Exc new_unimplemented()											{return new Exc(Stack_null, Type__none, "method not implemented");}
	public static Exc new_unimplemented_w_msg(String msg, Object... args)		{return new Exc(Stack_null, Type__none, msg, args);}
	public static Exc new_deprecated(String s)										{return new Exc(Stack_null, Type__none, "deprecated", "method", s);}
	public static Exc new_parse_type(Class<?> c, String raw)						{return new_parse(ClassAdp_.FullNameOf_type(c), raw);}
	public static Exc new_parse_exc(Exception e, Class<?> c, String raw)	{return new_parse(ClassAdp_.FullNameOf_type(c), raw).Args_add("e", Err_.Message_lang(e));}
	public static Exc new_parse(String type, String raw)							{return new Exc(Stack_null, Type__none, "parse failed", "type", type, "raw", raw);}
	public static Exc new_null(String s)											{return new Exc(Stack_null, Type__none, "null obj", "obj", s);}
	public static Exc new_missing_idx(int idx, int len)								{return new Exc(Stack_null, Type__none, "index is out of bounds", "idx", idx, "len", len);}
	public static Exc new_missing_key(String key)									{return new Exc(Stack_null, Type__none, "key not found", "key", key);}
	public static Exc new_invalid_op(String msg)									{return new Exc(Stack_null, Type__none, msg);}
	public static Exc new_op_canceled()												{return new Exc(Stack_null, Type__op_canceled, "canceled by usr");}
	public static Exc new_type_mismatch_w_exc(Exception ignore, Class<?> t, Object o) {return new_type_mismatch(t, o);}
	public static Exc new_type_mismatch(Class<?> t, Object o)								{return new Exc(Stack_null, Type__none, "type mismatch", "expdType", ClassAdp_.FullNameOf_type(t), "actlType", ClassAdp_.NameOf_obj(o), "actlObj", Object_.Xto_str_strict_or_null_mark(o));}
	public static Exc new_cast(Exception ignore, Class<?> t, Object o) {
		String o_str = "";
		try							{o_str = Object_.Xto_str_strict_or_null_mark(o);}
		catch (Exception e)	{o_str = "<ERROR>"; Exc_.Noop(e);}
		return new Exc(Stack_null, Type__none, "cast failed", "type", ClassAdp_.NameOf_type(t), "obj", o_str);
	}

	public static String Message_lang(Exception e)		{return e.getMessage();} 
	public static String Stack_lang(Exception e) {
				String rv = "";
		StackTraceElement[] ary = e.getStackTrace();		
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) rv += "\n";
			rv += ary[i].toString();
		}
		return rv;
			}
	public static boolean Type_match(Exception e, String type) {
		Exc exc = Exc_.as_(e);
		return exc == null ? false : exc.Type_match(type);
	}
}
