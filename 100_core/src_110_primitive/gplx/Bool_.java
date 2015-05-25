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
public class Bool_ implements GfoInvkAble {
	public static final String Cls_val_name = "boolean";
	public static final Class<?> Cls_ref_type = Boolean.class; 
	public static final boolean		N		= false	, Y			= true;
	public static final byte		N_byte	= 0		, Y_byte	= 1		, __byte = 127;
	public static final int		N_int	= 0		, Y_int		= 1		, __int  =  -1;
	public static final String		N_str	= "n"	, Y_str		= "y";
	public static final byte[] Y_bry = new byte[] {Byte_ascii.Ltr_y}, N_bry = new byte[] {Byte_ascii.Ltr_n};
	public static final String		True_str = "true", False_str = "false";
	public static final byte[] True_bry = Bry_.new_a7(True_str), False_bry = Bry_.new_a7(False_str);
	public static boolean		cast_(Object obj)				{try {return (Boolean)obj;} catch (Exception e) {throw Err_.type_mismatch_exc_(e, boolean.class, obj);}}
	public static boolean		cast_or_(Object obj, boolean v)	{try {return (Boolean)obj;} catch (Exception e) {Err_.Noop(e); return v;}}
	public static boolean		By_int(int v) {return v != 0;}
	public static boolean		parse_(String raw) {
		if		(	String_.Eq(raw, "true")
				||	String_.Eq(raw, "True")	// needed for Store_Wtr(){boolVal.toString();}
				)
			return true;
		else if (	String_.Eq(raw, "false")
				||	String_.Eq(raw, "False")
			)
			return false;
		throw Err_.parse_type_(boolean.class, raw);
	}
	public static byte		Xto_byte(boolean v)		{return v ? Y_byte : N_byte;}
	public static int		Xto_int(boolean v)			{return v ? 1 : 0;}
	public static String	Xto_str_lower(boolean v)	{return v ? "true" : "false";}
	public static final boolean[] Ary_empty = new boolean[0];
	public static final Bool_ Gfs = new Bool_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_to_str))	{
			boolean v = m.ReadBool(GfsCore_.Arg_primitive);
			String fmt = m.ReadStrOr("fmt", null);
			if		(fmt == null)				return v ? "true" : "false";
			else if (String_.Eq(fmt, "yn"))		return v ? "y" : "n";
			else if (String_.Eq(fmt, "yes_no"))	return v ? "yes" : "no";
			else								return v ? "true" : "false";
		}
		else return GfoInvkAble_.Rv_unhandled;
	}	public static final String Invk_to_str = "to_str";
}
