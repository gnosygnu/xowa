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
public class Err_ {
	private static String Type__gplx = "gplx"; @gplx.Internal protected static String Trace_null = null;
	public static void Noop(Exception e) {}
	public static Err as_(Object obj) {return obj instanceof Err ? (Err)obj : null;}
	public static Err new_(String type, String msg, Object... args)			{return new Err(Bool_.Y, Trace_null, type, msg, args);}
	public static Err new_wo_type(String msg, Object... args)					{return new Err(Bool_.Y, Trace_null, Type__gplx, msg, args);}
	public static Err new_exc(Exception e, String type, String msg, Object... args) {
		Err rv = cast_or_make(e);
		rv.Msgs_add(type, msg, args);
		return rv;
	}
	public static Err new_unhandled(Object val)										{return new Err(Bool_.Y, Trace_null, Type__gplx, "val is not in switch/if", "val", val);}
	public static Err new_unimplemented()											{return new Err(Bool_.Y, Trace_null, Type__gplx, "method not implemented");}
	public static Err new_unimplemented_w_msg(String msg, Object... args)		{return new Err(Bool_.Y, Trace_null, Type__gplx, msg, args);}
	public static Err new_deprecated(String s)										{return new Err(Bool_.Y, Trace_null, Type__gplx, "deprecated", "method", s);}
	public static Err new_parse_type(Class<?> c, String raw)						{return new_parse(ClassAdp_.FullNameOf_type(c), raw);}
	public static Err new_parse_exc(Exception e, Class<?> c, String raw)	{return new_parse(ClassAdp_.FullNameOf_type(c), raw).Args_add("e", Err_.Message_lang(e));}
	public static Err new_parse(String type, String raw)							{return new Err(Bool_.Y, Trace_null, Type__gplx, "parse failed", "type", type, "raw", raw);}
	public static Err new_null()													{return new Err(Bool_.Y, Trace_null, Type__gplx, "null obj");}
	public static Err new_missing_idx(int idx, int len)								{return new Err(Bool_.Y, Trace_null, Type__gplx, "index is out of bounds", "idx", idx, "len", len);}
	public static Err new_missing_key(String key)									{return new Err(Bool_.Y, Trace_null, Type__gplx, "key not found", "key", key);}
	public static Err new_invalid_op(String msg)									{return new Err(Bool_.Y, Trace_null, Type__gplx, msg);}
	public static Err new_invalid_arg(String msg, Object... args)				{return new Err(Bool_.Y, Trace_null, Type__gplx, msg, args);}
	public static Err new_op_canceled()												{return new Err(Bool_.Y, Trace_null, Type__op_canceled, "canceled by usr");}
	public static Err new_type_mismatch_w_exc(Exception ignore, Class<?> t, Object o) {return new_type_mismatch(t, o);}
	public static Err new_type_mismatch(Class<?> t, Object o)								{return new Err(Bool_.Y, Trace_null, Type__gplx, "type mismatch", "expdType", ClassAdp_.FullNameOf_type(t), "actlType", ClassAdp_.NameOf_obj(o), "actlObj", Object_.Xto_str_strict_or_null_mark(o));}
	public static Err new_cast(Exception ignore, Class<?> t, Object o) {
		String o_str = "";
		try							{o_str = Object_.Xto_str_strict_or_null_mark(o);}
		catch (Exception e)	{o_str = "<ERROR>"; Err_.Noop(e);}
		return new Err(Bool_.Y, Trace_null, Type__gplx, "cast failed", "type", ClassAdp_.NameOf_type(t), "obj", o_str);
	}

	public static String Message_lang(Exception e)		{return e.getMessage();} 
	public static String Trace_lang(Exception e) {
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
		Err exc = Err_.as_(e);
		return exc == null ? false : exc.Type_match(type);
	}
	public static String Message_gplx_full(Exception e)	{return cast_or_make(e).To_str__full();}
	public static String Message_gplx_log(Exception e)	{return cast_or_make(e).To_str__log();}
	public static Err cast_or_make(Exception e) {return ClassAdp_.Eq_typeSafe(e, Err.class) ? (Err)e : new Err(Bool_.N, Err_.Trace_lang(e), ClassAdp_.NameOf_obj(e), Err_.Message_lang(e));}
	public static final String Type__op_canceled = "gplx.op_canceled";
}
