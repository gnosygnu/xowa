/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.frameworks.tests;
import gplx.frameworks.objects.ToStrAble;
import gplx.frameworks.objects.ToStrAbleUtl;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.basics.constants.CharCode;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDecimal;
import gplx.types.errs.ErrUtl;
public class GfoTstr {
	private static final GfoStringBldr bfr = new GfoStringBldr();
	private static final String
		EqN = "!= ",
		Null = "<<NULL>>",
		SectionBgn = "\n************************************************************************************************\n",
		SectionMid = "\n------------------------------------------------------------------------------------------------\n",
		SectionEnd =   "________________________________________________________________________________________________";
	
	public static void Debug(Object... ary) {Write(ary);}
	public static void Write(byte[] s, int b, int e) {Write(BryLni.Mid(s, b, e));}
	public static void Write() {Write("tmp");}
	public static void Write(Object... ary) {
		GfoStringBldr bfr = new GfoStringBldr();
		int aryLen = ArrayUtl.Len(ary);
		for (int i = 0; i < aryLen; i++) {
			bfr.Add("'");
			bfr.Add(ObjectUtl.ToStrOrNullMark(ary[i]));
			bfr.Add("' ");
		}
		System.out.println(bfr.ToStr() + StringUtl.Nl);
	}
	public static void EqNull(Object actl)                                 {EqNull(true , actl, null);}
	public static void EqNull(Object actl, String fmt, Object... args)     {EqNull(true , actl, StringUtl.Format(fmt, args));}
	public static void EqNotNull(Object actl)                              {EqNull(false, actl, null);}
	public static void EqNotNull(Object actl, String fmt, Object... args)  {EqNull(false, actl, StringUtl.Format(fmt, args));}
	private static final void EqNull(boolean expd, Object actl, String fmt, Object... args) {
		if  (  expd  && actl != null
			|| !expd && actl == null
			) {
			String expdStr = expd         ? "null" : "not null";
			String actlStr = actl == null ? "null" : "not null";
			WriteAndThrow(expdStr, actlStr, fmt, args);
		}
	}
	public static void EqBoolY(boolean actl)                               {Eq(true , actl, null);}
	public static void EqBoolY(boolean actl, String fmt, Object... args)   {Eq(true , actl, StringUtl.Format(fmt, args));}
	public static void EqBoolN(boolean actl)                               {Eq(false, actl, null);}
	public static void EqBoolN(boolean actl, String fmt, Object... args)   {Eq(false, actl, StringUtl.Format(fmt, args));}
	public static void Eq(boolean expd, boolean actl) {Eq(expd, actl, null);}
	public static void Eq(boolean expd, boolean actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(BoolUtl.ToStrLower(expd), BoolUtl.ToStrLower(actl), fmt, args);
	}
	public static void EqByte(byte expd, byte actl) {Eq(expd, actl, null);}
	public static void EqByte(byte expd, byte actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(ByteUtl.ToStr(expd), ByteUtl.ToStr(actl), fmt, args);
	}
	public static void Eq(int expd, int actl) {Eq(expd, actl, null);}
	public static void Eq(int expd, int actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(IntUtl.ToStr(expd), IntUtl.ToStr(actl), fmt, args);
	}
	public static void EqLong(long expd, long actl) {EqLong(expd, actl, null);}
	public static void EqLong(long expd, long actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(LongUtl.ToStr(expd), LongUtl.ToStr(actl), fmt, args);
	}
	public static void EqFloat(float expd, float actl) {EqFloat(expd, actl, null);}
	public static void EqFloat(float expd, float actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(FloatUtl.ToStr(expd), FloatUtl.ToStr(actl), fmt, args);
	}
	public static void EqDouble(double expd, double actl) {EqDouble(expd, actl, null);}
	public static void EqDouble(double expd, double actl, String fmt, Object... args) {
		if (expd != actl)
			WriteAndThrow(DoubleUtl.ToStr(expd), DoubleUtl.ToStr(actl), fmt, args);
	}
	public static void Eq(byte[] expd, byte[] actl)                             {Eq(StringUtl.NewU8(expd), StringUtl.NewU8(actl), null);}
	public static void Eq(byte[] expd, byte[] actl, String fmt, Object... args) {Eq(StringUtl.NewU8(expd), StringUtl.NewU8(actl), fmt, args);}
	public static void Eq(byte[] expd, String actl)                             {Eq(StringUtl.NewU8(expd), actl, null);}
	public static void Eq(byte[] expd, String actl, String fmt, Object... args) {Eq(StringUtl.NewU8(expd), actl, fmt, args);}
	public static void Eq(String expd, byte[] actl)                             {Eq(expd, StringUtl.NewU8(actl), null);}
	public static void Eq(String expd, byte[] actl, String fmt, Object... args) {Eq(expd, StringUtl.NewU8(actl), fmt, args);}
	public static void Eq(String expd, String actl)                             {Eq(expd, actl, null);}
	public static void Eq(String expd, String actl, String fmt, Object... args) {
		if (!StringUtl.Eq(expd, actl))
			WriteAndThrow(expd, actl, fmt, args);
	}
	public static void Eq(GfoDate expd, GfoDate actl)                             {Eq(expd.ToStrGplx(), actl.ToStrGplx());}
	public static void Eq(GfoDate expd, GfoDate actl, String fmt, Object... args) {Eq(expd.ToStrGplx(), actl.ToStrGplx(), fmt, args);}
	public static void Eq(GfoDecimal expd, GfoDecimal actl)                       {EqDouble(expd.ToDouble(), actl.ToDouble());}
	public static void EqErr(Exception exc, Class<?> type) {
		if (!ClassUtl.Eq(exc.getClass(), type))
			throw ErrUtl.NewArgs("error types do not match", "expdType", ClassUtl.CanonicalName(type), "actlType", ClassUtl.NameByObj(exc), "actlMsg", ErrUtl.Message(exc));
	}
	public static void ErrHas(Exception e, String hdr) {
		if (!StringUtl.Has(ErrUtl.ToStrFull(e), hdr))
			throw ErrUtl.NewFmt("could not find '{0}' in '{1}'", hdr, ErrUtl.ToStrFull(e));
	}
	public static void Eq(ToStrAble expd, ToStrAble actl)                             {Eq(expd.ToStr(), actl.ToStr());}
	public static void Eq(ToStrAble expd, ToStrAble actl, String fmt, Object... args) {Eq(expd.ToStr(), actl.ToStr(), fmt, args);}
	public static void EqObj(Object expd, Object actl) {EqObj(expd, actl, "");}
	public static void EqObj(Object expd, Object actl, String msgFmt, Object... msgArgs) {
		if (!ObjectUtl.Eq(expd, actl))
			WriteAndThrow(ObjectUtl.ToStr(expd), ObjectUtl.ToStr(actl), msgFmt, msgArgs);
	}
	public static void EqObjToStr(Object expd, Object actl) {
		Eq(ObjectUtl.ToStrOrNullMark(expd), ObjectUtl.ToStrOrNullMark(actl));
	}
	// NOTE: EqLines will ignore differences in last trailing line; EX: expd="a" == actl ="a\n"
	public static void EqLines(String expd, String actl)                                 {EqAry(TypeIds.IdStr, SplitLines(expd), SplitLines(actl), null);}
	public static void EqLines(String expd, String actl, String fmt, Object... args)     {EqAry(TypeIds.IdStr, SplitLines(expd), SplitLines(actl), fmt, args);}
	public static void EqLines(String expd, byte[] actl)                                 {EqAry(TypeIds.IdStr, SplitLines(expd), SplitLines(StringUtl.NewU8(actl)), null);}
	public static void EqLines(String expd, byte[] actl, String fmt, Object... args)     {EqAry(TypeIds.IdStr, SplitLines(expd), SplitLines(StringUtl.NewU8(actl)), fmt, args);}
	public static void EqLines(byte[][] expd, byte[][] actl)                             {EqAry(TypeIds.IdBry, expd, actl, null);}
	public static void EqLines(byte[][] expd, byte[][] actl, String fmt, Object... args) {EqAry(TypeIds.IdBry, expd, actl, fmt, args);}
	public static void EqLines(String[] expd, String[] actl)                             {EqAry(TypeIds.IdBry, BryUtl.Ary(expd), BryUtl.Ary(actl), null);}
	public static void EqLines(String[] expd, String[] actl, String fmt, Object... args) {EqAry(TypeIds.IdBry, BryUtl.Ary(expd), BryUtl.Ary(actl), fmt, args);}
	public static void EqLines(String[] expd, byte[][] actl)                             {EqAry(TypeIds.IdBry, BryUtl.Ary(expd), actl, null);}
	public static void EqLines(String[] expd, byte[][] actl, String fmt, Object... args) {EqAry(TypeIds.IdBry, BryUtl.Ary(expd), actl, fmt, args);}
	public static void EqAry(boolean[] expd, boolean[] actl)                             {EqAry(TypeIds.IdBool, expd, actl, null);}
	public static void EqAry(boolean[] expd, boolean[] actl, String fmt, Object... args) {EqAry(TypeIds.IdBool, expd, actl, fmt, args);}
	public static void EqAry(byte[] expd, byte[] actl)                                   {EqAry(TypeIds.IdByte, expd, actl, null);}
	public static void EqAry(byte[] expd, byte[] actl, String fmt, Object... args)       {EqAry(TypeIds.IdByte, expd, actl, fmt, args);}
	public static void EqAry(int[] expd, int[] actl)                                     {EqAry(TypeIds.IdInt, expd, actl, null);}
	public static void EqAry(int[] expd, int[] actl, String msg)                         {EqAry(TypeIds.IdInt, expd, actl, msg);}
	public static void EqAry(long[] expd, long[] actl)                                   {EqAry(TypeIds.IdLong, expd, actl, null);}
	public static void EqAry(long[] expd, long[] actl, String msg)                       {EqAry(TypeIds.IdLong, expd, actl, msg);}
	public static void EqAry(int[] expd, int[] actl, String msg, Object... args)         {EqAry(TypeIds.IdInt, expd, actl, msg, args);}
	public static void EqAryObj(Object expd, Object actl)                                {EqAry(TypeIds.IdObj, expd, actl, null);}
	public static void EqAryObj(Object expd, Object actl, String fmt, Object... args)    {EqAry(TypeIds.IdObj, expd, actl, fmt, args);}
	private static String[] SplitLines(String s) {
		if (s == null) s = "";
		return StringUtl.Split(s, CharUtl.NewLine);
	}
	// NOTE: EqAryObjAry compares on .toString, not .equals; this should be changed to compare on .equals, but need to add .equals to .hashcode to multiple types
	private static final int IdToStr = -1;
	public static void EqAryObjAry(Object[] expd, Object[] actl)                         {EqAry(IdToStr, expd, actl, null);}
	public static void EqAryObjAry(Object[] expd, Object[] actl, String fmt, Object... args) {EqAry(IdToStr, expd, actl, fmt, args);}
	private static final void EqAry(int typeTid, Object expdAry, Object actlAry, String msgFmt, Object... msgArgs) {
		boolean[] failures = CalcFailures(typeTid, expdAry, actlAry);
		if (failures != null) {
			WriteFailHead(bfr, msgFmt, msgArgs);
			WriteFailBody(bfr, failures, typeTid, expdAry, actlAry);
			throw ErrUtl.NewMsg(bfr.ToStrAndClear());
		}
	}
	public static void Fail(String fmt, Object... args) {
		throw ErrUtl.NewMsg(StringUtl.Format(fmt, args));
	}
	public static void FailBcExpdError() {Eq(false, true, "fail expd error");}
	private static boolean[] CalcFailures(int tid, Object expdAry, Object actlAry) {
		int expdLen = ArrayUtl.Len(expdAry);
		int actlLen = ArrayUtl.Len(actlAry);
		int maxLen = expdLen > actlLen ? expdLen : actlLen;
		if (maxLen == 0) return null; // exit early if both arys are empty

		boolean[] rv = null;
		for (int i = 0; i < maxLen; i++) {
			Object expdObj = i < expdLen ? ArrayUtl.GetAt(expdAry, i) : null;
			Object actlObj = i < actlLen ? ArrayUtl.GetAt(actlAry, i) : null;
			boolean eq = false;
			if      (expdObj == null && actlObj == null) eq = true;
			else if (expdObj == null || actlObj == null) eq = false;
			else {
				switch (tid) {
					case TypeIds.IdBool: eq = BoolUtl.Cast(expdObj) == BoolUtl.Cast(actlObj); break;
					case TypeIds.IdLong: eq = LongUtl.Cast(expdObj) == LongUtl.Cast(actlObj); break;
					case TypeIds.IdInt:  eq = IntUtl.Cast(expdObj) == IntUtl.Cast(actlObj); break;
					case TypeIds.IdByte: eq = ByteUtl.Cast(expdObj) == ByteUtl.Cast(actlObj); break;
					case TypeIds.IdBry:  eq = BryLni.Eq((byte[])expdObj, (byte[])actlObj); break;
					case TypeIds.IdObj:  eq = ObjectUtl.Eq(expdObj, actlObj); break;
					case TypeIds.IdStr:  eq = StringUtl.Eq(StringUtl.Cast(expdObj), StringUtl.Cast(actlObj)); break;
					case IdToStr:        eq = StringUtl.Eq(ToStrOrNativeToString(expdObj), ToStrOrNativeToString(actlObj)); break;
				}
			}
			if (!eq) {
				if (rv == null) {
					rv = new boolean[maxLen];
				}
				rv[i] = true;
			}
		}
		return rv;
	}
	private static void WriteFailHead(GfoStringBldr bfr, String msgFmt, Object[] msgArgs) {
		bfr.Add(SectionBgn);
		if (msgFmt != null) {
			bfr.Add(StringUtl.Format(msgFmt, msgArgs));
			bfr.Add(SectionMid);
		}
	}
	private static void WriteFailBody(GfoStringBldr bfr, boolean[] failures, int typeId, Object expdAry, Object actlAry) {
		int len = failures.length;
		int expdLen = ArrayUtl.Len(expdAry);
		int actlLen = ArrayUtl.Len(actlAry);
		for (int i = 0; i < len; i++) {
			boolean failure = failures[i];
			int padLen = 5 - IntUtl.CountDigits(i); // 5=assuming no more than 9999 failures in one test
			bfr.AddIntPadBgn(CharCode.Num0, padLen, i).AddCharColon().AddCharSpace();
			WriteFailItm(bfr, typeId, expdAry, expdLen, i);
			if (failure) {
				bfr.Add(EqN).AddCharRepeat(CharCode.Space, padLen - 1);
				WriteFailItm(bfr, typeId, actlAry, actlLen, i);
			}
		}
		bfr.Add(SectionEnd);
	}
	private static void WriteFailItm(GfoStringBldr bfr, int typeId, Object ary, int len, int idx) {
		if (idx < len) {
			Object val = ArrayUtl.GetAt(ary, idx);
			switch (typeId) {
				case TypeIds.IdBool: bfr.AddBoolAsYn(BoolUtl.Cast(val)); break;
				case TypeIds.IdBry:  bfr.Add((byte[])val); break;
				case TypeIds.IdLong: bfr.AddLong(LongUtl.Cast(val)); break;
				case TypeIds.IdInt:  bfr.Add(IntUtl.Cast(val)); break;
				case TypeIds.IdByte: bfr.Add(ByteUtl.Cast(val)); break;
				case TypeIds.IdObj:  bfr.Add(ObjectUtl.ToStr(val)); break;
				case TypeIds.IdStr:  bfr.Add(StringUtl.Cast(val)); break;
				case IdToStr:        bfr.Add(ToStrOrNativeToString(val)); break;
				default:             throw ErrUtl.NewUnhandled(typeId);
			}
		}
		else
			bfr.Add(Null);
		bfr.AddCharNl();
	}
	protected static void WriteAndThrow(String expd, String actl, String msgFmt, Object... msgArgs) {
		WriteFailHead(bfr, msgFmt, msgArgs);
		bfr.Add("expd: ").Add(expd).AddCharNl();
		bfr.Add("actl: ").Add(actl).AddCharNl();
		bfr.Add(SectionEnd);
		throw ErrUtl.NewMsg(bfr.ToStrAndClear());
	}
	private static String ToStrOrNativeToString(Object o) {
		ToStrAble strAble = ToStrAbleUtl.CastOrNull(o);
		return strAble == null
			? ObjectUtl.ToStrOrNullMark(o)
			: strAble.ToStr();
	}
}
