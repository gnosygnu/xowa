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
import gplx.core.strings.*;
public class Err_ {	//_20110415
	public static Err as_(Object obj) {return obj instanceof Err ? (Err)obj : null;}
	public static Err new_(String fmt, Object... args)	{return Err.hdr_(String_.Format(fmt, args));}
	public static Err new_fmt_(String fmt, Object... args){return Err.hdr_(String_.Format(fmt, args));}
	public static Err new_(String hdr)							{return Err.hdr_(hdr);}
	public static Err new_key_(String key, String hdr)			{return Err.hdr_(hdr).Key_(key);}
	public static Err err_key_(Exception exc, String key, String hdr) {return Err.exc_(exc, hdr).Key_(key);}
	public static Err err_(Exception exc, String hdr)	{return Err.exc_(exc, hdr);}
	public static Err err_(Exception e, String fmt, Object... args) {return Err.exc_(e, String_.Format(fmt, args));}
	public static Err cast_(Exception ignore, Class<?> t, Object o) {
		String o_str = "";
		try {o_str = Object_.Xto_str_strict_or_null_mark(o);}
		catch (Exception e) {Err_.Noop(e); o_str = "<ERROR>";}
		return cast_manual_msg_(ignore, t, o_str);
	}
	public static Err cast_manual_msg_(Exception ignore, Class<?> t, String s) {
		String msg = String_.Format("cast failed; type={0} obj={1}", ClassAdp_.NameOf_type(t), s);
		return new_(msg);
	}
	public static void FailIfNotFound(int v, String m)			{if (v == String_.Find_none) throw find_failed_(m);}
	public static Err find_failed_(String find)					{return Err.hdr_("find failed").Add("find", find);}

	public static Err null_(String obj)							{return Err.hdr_("null obj").Add("obj", obj);}
	public static Err deprecated(String s)						{return Err.hdr_("deprecated:" + s);}
	public static Err not_implemented_()						{return not_implemented_msg_("method not implemented");}
	public static Err not_implemented_msg_(String hdr)			{return Err.hdr_(hdr);}
	public static Err type_mismatch_exc_(Exception e, Class<?> t, Object o) {return type_mismatch_(t, o);} // NOTE: e passed to "soak" up variable for IDE
	public static Err type_mismatch_(Class<?> t, Object o) {
		return Err.hdr_("type mismatch")
			.Add("expdType", ClassAdp_.FullNameOf_type(t))
			.Add("actlType", ClassAdp_.NameOf_obj(o))
			.Add("actlObj", Object_.Xto_str_strict_or_null_mark(o))
			;
	}
	public static Err missing_idx_(int idx, int len)				{return Err.hdr_("index is out of bounds").Add("idx", idx).Add("len", len);}
	public static Err missing_key_(String key)						{return Err.hdr_("key not found").Add("key", key);}
	public static Err unhandled(Object val)							{return Err.hdr_("val is not in switch/if").Add("val", val);}
	public static Err invalid_op_(String hdr)						{return Err.hdr_(hdr);}
	public static Err parse_type_(Class<?> c, String raw)							{return parse_(ClassAdp_.FullNameOf_type(c), raw);}
	public static Err parse_type_exc_(Exception e, Class<?> c, String raw)	{return parse_(ClassAdp_.FullNameOf_type(c), raw).Add("e", Err_.Message_lang(e));}
	public static Err parse_msg_(String type, String raw, String cause)					{return parse_(type, raw).Add("cause", cause);}
	public static Err parse_(String typeName, String raw)			{return Err.hdr_("parse failed").Add("type", typeName).Add("raw", raw);}

	public static final String op_canceled__const = "gplx.op_canceled";
	public static Err op_canceled_usr_()							{return Err_.new_key_(op_canceled__const, "canceled by usr");}
	public static Err rethrow_(Exception exc)				{return Err_.new_key_("rethrow", Err_.Message_lang(exc));}	// NOTE: needed because throw exc does not work in java (exc must be RuntimeException, not Exception)

	public static void Noop(Exception e) {}
	public static String Message_lang(Exception e)			{return e.getClass() + " " + e.getMessage();} 
	public static String Message_gplx(Exception e)			{return ErrMsgWtr._.Message_gplx(e);}
	public static String Message_gplx_brief(Exception e)		{return ErrMsgWtr._.Message_gplx_brief(e);}
	public static String Message_hdr_or_message(Exception e) {
		if (e == null) return "exception is null";
		return ClassAdp_.Eq(e.getClass(), Err.class)
			? ((Err)e).Hdr()
			: Message_lang(e);
	}
	@gplx.Internal protected static String StackTrace_lang(Exception e) {
				String_bldr sb = String_bldr_.new_();
		StackTraceElement[] stackTraceAry = e.getStackTrace();
		int len = stackTraceAry.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add_char_crlf();
			sb.Add(stackTraceAry[i].toString());
		}
		return sb.XtoStr();
			}
	public static boolean MatchKey(Exception exc, String key) {
		Err err = Err_.as_(exc); if (err == null) return false;
		String msg = err.Key();
		return String_.Has(msg, key);
	}
}
