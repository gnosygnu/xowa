/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.tests; import gplx.*;
import gplx.objects.*;
import gplx.objects.errs.*;
import gplx.objects.primitives.*; import gplx.objects.brys.*;
import gplx.objects.strings.*; import gplx.objects.strings.bfrs.*;
import gplx.objects.arrays.*; import gplx.objects.types.*;
public class Gftest_fxt {
	private static final String_bfr bfr = new String_bfr();
	public static void Eq__ary(Object[] expd, Object[] actl, String msg_fmt, Object... msg_args)      {Eq__array(Type_ids_.Id__obj, expd, actl, msg_fmt, msg_args);}
	public static void Eq__ary(boolean[] expd, boolean[] actl, String msg_fmt, Object... msg_args)			{Eq__array(Type_ids_.Id__bool, expd, actl, msg_fmt, msg_args);}
	public static void Eq__ary(int[] expd, int[] actl)                                                      {Eq__array(Type_ids_.Id__int, expd, actl, "");}
	public static void Eq__ary(int[] expd, int[] actl, String msg_fmt, Object... msg_args)			{Eq__array(Type_ids_.Id__int, expd, actl, msg_fmt, msg_args);}
	public static void Eq__ary(long[] expd, long[] actl, String msg_fmt, Object... msg_args)			{Eq__array(Type_ids_.Id__long, expd, actl, msg_fmt, msg_args);}
	public static void Eq__ary(byte[] expd, byte[] actl, String msg_fmt, Object... msg_args)			{Eq__array(Type_ids_.Id__byte, expd, actl, msg_fmt, msg_args);}
//		public static void Eq__ary__lines(String expd, String actl)												{Eq__ary__lines(expd, actl, "no_msg");}
//		public static void Eq__ary__lines(String expd, byte[] actl)												{Eq__ary__lines(expd, String_.New_bry_utf8(actl), "no_msg");}
//		public static void Eq__ary__lines(String expd, byte[] actl, String msg_fmt, params Object[] msg_args)	{Eq__ary__lines(expd, String_.New_bry_utf8(actl), msg_fmt, msg_args);}
//		public static void Eq__ary__lines(String expd, String actl, String msg_fmt, params Object[] msg_args)	{Eq__array(Type_ids_.Id__str, Bry_split_.Split_lines(Bry_.New_utf08(expd)), Bry_split_.Split_lines(Bry_.New_utf08(actl)), msg_fmt, msg_args);}
	public static void Eq__ary(String[] expd, String[] actl)												{Eq__array(Type_ids_.Id__bry, Bry_.Ary(expd), Bry_.Ary(actl), "no_msg");}
	public static void Eq__ary(String[] expd, String[] actl, String msg_fmt, Object... msg_args)		{Eq__array(Type_ids_.Id__bry, Bry_.Ary(expd), Bry_.Ary(actl), msg_fmt, msg_args);}
	public static void Eq__ary(String[] expd, byte[][] actl, String msg_fmt, Object... msg_args)		{Eq__array(Type_ids_.Id__bry, Bry_.Ary(expd), actl, msg_fmt, msg_args);}
	public static void Eq__ary(byte[][] expd, byte[][] actl, String msg_fmt, Object... msg_args)		{Eq__array(Type_ids_.Id__bry, expd, actl, msg_fmt, msg_args);}
	private static void Eq__array(int type_tid, Object expd_ary, Object actl_ary, String msg_fmt, Object... msg_args) {
		boolean[] failures = Calc__failures(type_tid, expd_ary, actl_ary);
		if (failures != null) {
			Write_fail_head(bfr, msg_fmt, msg_args);
			Write_fail_ary(bfr, failures, type_tid, expd_ary, actl_ary);
			throw Err_.New_msg(bfr.To_str_and_clear());
		}
	}
	public static void Eq__null(boolean expd, Object actl) {Eq__null(expd, actl, null);}
	public static void Eq__null(boolean expd, Object actl, String msg_fmt, Object... msg_args) {
		if (	expd	&& actl == null
			||	!expd	&& actl != null
			) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		String expd_str = expd ? "null" : "not null";
		String actl_str = actl == null ? "null" : "not null";
		bfr.Add("expd: ").Add(expd_str).Add_char_nl();
		bfr.Add("actl: ").Add(actl_str).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__obj_or_null(Object expd, Object actl) {
		if (expd == null) expd = Null;
		if (actl == null) actl = Null;
		Eq__str(Object_.To_str_or(expd, Null), Object_.To_str_or(actl, null), Null);
	}
	public static void Eq__str(String expd, byte[] actl, String msg_fmt, Object... msg_args) {Eq__str(expd, String_.New_bry_utf8(actl), msg_fmt, msg_args);}
	public static void Eq__str(String expd, byte[] actl) {Eq__str(expd, String_.New_bry_utf8(actl), null);}
	public static void Eq__str(String expd, String actl) {Eq__str(expd, actl, null);}
	public static void Eq__str(String expd, String actl, String msg_fmt, Object... msg_args) {
		if (String_.Eq(expd, actl)) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add(expd).Add_char_nl();
		bfr.Add("actl: ").Add(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__bry(byte[] expd, byte[] actl) {Eq__bry(expd, actl, null);}
	public static void Eq__bry(byte[] expd, byte[] actl, String msg_fmt, Object... msg_args) {
		if (Bry_.Eq(expd, actl)) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add(String_.New_bry_utf8(expd)).Add_char_nl();
		bfr.Add("actl: ").Add(String_.New_bry_utf8(actl)).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__long(long expd, long actl) {Eq__long(expd, actl, null);}
	public static void Eq__long(long expd, long actl, String msg_fmt, Object... msg_args) {
		if (expd == actl) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add_long(expd).Add_char_nl();
		bfr.Add("actl: ").Add_long(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__byte(byte expd, byte actl) {Eq__byte(expd, actl, null);}
	public static void Eq__byte(byte expd, byte actl, String msg_fmt, Object... msg_args) {
		if (expd == actl) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add_byte(expd).Add_char_nl();
		bfr.Add("actl: ").Add_byte(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__int(int expd, int actl) {Eq__int(expd, actl, null);}
	public static void Eq__int(int expd, int actl, String msg_fmt, Object... msg_args) {
		if (expd == actl) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add_int(expd).Add_char_nl();
		bfr.Add("actl: ").Add_int(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__bool_y(boolean actl)			{Eq__bool(Bool_.Y, actl, null);}
	public static void Eq__bool_n(boolean actl)			{Eq__bool(Bool_.N, actl, null);}
	public static void Eq__bool_y(boolean actl, String msg_fmt, Object... msg_args)	{Eq__bool(Bool_.Y, actl, msg_fmt, msg_args);}
	public static void Eq__bool(boolean expd, boolean actl)	{Eq__bool(expd, actl, null);}
	public static void Eq__bool(boolean expd, boolean actl, String msg_fmt, Object... msg_args) {
		if (expd == actl) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add_bool(expd).Add_char_nl();
		bfr.Add("actl: ").Add_bool(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	public static void Eq__double(double expd, double actl) {Eq__double(expd, actl, null);}
	public static void Eq__double(double expd, double actl, String msg_fmt, Object... msg_args) {
		if (expd == actl) return;
		Write_fail_head(bfr, msg_fmt, msg_args);
		bfr.Add("expd: ").Add_double(expd).Add_char_nl();
		bfr.Add("actl: ").Add_double(actl).Add_char_nl();
		bfr.Add(Section_end);
		throw Err_.New_msg(bfr.To_str_and_clear());
	}
	private static void Write_fail_head(String_bfr bfr, String msg_fmt, Object[] msg_args) {
		bfr.Add(Section_bgn);
		if (msg_fmt != null) {
			bfr.Add(String_.Format(msg_fmt, msg_args));
			bfr.Add(Section_mid);
		}
	}
	private static void Write_fail_ary(String_bfr bfr, boolean[] failures, int type_id, Object expd_ary, Object actl_ary) {	
		int len = failures.length;
		int expd_len = Array_.Len(expd_ary);
		int actl_len = Array_.Len(actl_ary);
		for (int i = 0; i < len; ++i) {
			boolean failure = failures[i];
			int pad_len = 5 - Int_.Count_digits(i);
			bfr.Add_int_pad_bgn(Char_code_.Num_0, pad_len, i).Add_char_colon().Add_char_space();
			Write__itm(bfr, type_id, expd_ary, expd_len, i);
			if (failure) {
				bfr.Add(Eq_n).Add_char_repeat(Char_code_.Space, pad_len - 1);
				Write__itm(bfr, type_id, actl_ary, actl_len, i);
			}
		}
		bfr.Add(Section_end);
	}
	private static void Write__itm(String_bfr bfr, int type_id, Object ary, int len, int idx) {
		if (idx < len) {
			Object val = Array_.Get_at(ary, idx);
			switch (type_id) {
				case Type_ids_.Id__bool:	bfr.Add_bool_as_yn(Bool_.Cast(val)); break;
				case Type_ids_.Id__bry:		bfr.Add_bry((byte[])val); break;
				case Type_ids_.Id__long:	bfr.Add_long(Long_.Cast(val)); break;
				case Type_ids_.Id__int:		bfr.Add_int(Int_.Cast(val)); break;
				case Type_ids_.Id__byte:	bfr.Add_int((int)(Byte_.Cast(val))); break;
				case Type_ids_.Id__obj:     bfr.Add(Object_.To_str(val)); break;
				default:					throw Err_.New_unhandled_default(type_id);
			}
		}
		else
			bfr.Add(Null);
		bfr.Add_char_nl();
	}
	private static boolean[] Calc__failures(int tid, Object expd_ary, Object actl_ary) {	
		int expd_len = Array_.Len(expd_ary);
		int actl_len = Array_.Len(actl_ary);
		int max_len = expd_len > actl_len ? expd_len : actl_len; if (max_len == 0) return null;
		boolean[] rv = null;
		for (int i = 0; i < max_len; ++i) {
			Object expd_obj = i < expd_len ? Array_.Get_at(expd_ary, i) : null;
			Object actl_obj = i < actl_len ? Array_.Get_at(actl_ary, i) : null;
			boolean eq = false;
			if		(expd_obj == null && actl_obj == null)	eq = true;
			else if (expd_obj == null || actl_obj == null)	eq = false;
			else {
				switch (tid) {
					case Type_ids_.Id__bool:		eq = Bool_.Cast(expd_obj) == Bool_.Cast(actl_obj); break;
					case Type_ids_.Id__bry:			eq = Bry_.Eq((byte[])expd_obj, (byte[])actl_obj); break;
					case Type_ids_.Id__long:		eq = Long_.Cast(expd_obj) == Long_.Cast(actl_obj); break;
					case Type_ids_.Id__int:			eq = Int_.Cast(expd_obj) == Int_.Cast(actl_obj); break;
					case Type_ids_.Id__byte:		eq = Byte_.Cast(expd_obj) == Byte_.Cast(actl_obj); break;
					case Type_ids_.Id__obj:         eq = Object_.Eq(expd_obj, actl_obj); break;
				}
			}
			if (!eq) {
				if (rv == null) {
					rv = new boolean[max_len];
				}
				rv[i] = true;
			}
		}
		return rv;
	}
	private static final String Null = "<<NULL>>";
	private static final String Eq_n = "!= "
	, Section_bgn = "\n************************************************************************************************\n"
	, Section_mid = "\n------------------------------------------------------------------------------------------------\n"
	, Section_end =   "________________________________________________________________________________________________"
	;

	// public static void Write(byte[] s, int b, int e) {Write(Bry_.Mid(s, b, e));}
	public static void Write() {Write("tmp");}
	public static void Write(Object... ary) {
		String_bfr bfr = new String_bfr();
		int ary_len = Array_.Len(ary);
		for (int i = 0; i < ary_len; i++) {
			bfr.Add("'");
			bfr.Add(Object_.To_str_or_null_mark(ary[i]));
			bfr.Add("' ");
		}
		System.out.println(bfr.To_str() + String_.Lf); 
	}
}
