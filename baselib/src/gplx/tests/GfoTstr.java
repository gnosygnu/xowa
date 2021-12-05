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
package gplx.tests;
import gplx.objects.ObjectUtl;
import gplx.objects.arrays.ArrayUtl;
import gplx.objects.arrays.BryUtl;
import gplx.objects.errs.ErrUtl;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.primitives.ByteUtl;
import gplx.objects.primitives.CharCode;
import gplx.objects.primitives.IntUtl;
import gplx.objects.primitives.LongUtl;
import gplx.objects.strings.StringUtl;
import gplx.objects.strings.bfrs.GfoStrBldr;
import gplx.objects.types.TypeIds;
public class GfoTstr {
	private static final GfoStrBldr bfr = new GfoStrBldr();
	private static final String
		EqN = "!= ",
		Null = "<<NULL>>",
		SectionBgn = "\n************************************************************************************************\n",
		SectionMid = "\n------------------------------------------------------------------------------------------------\n",
		SectionEnd =   "________________________________________________________________________________________________";
	
	public static void Write(Object... ary) {
		GfoStrBldr bfr = new GfoStrBldr();
		int aryLen = ArrayUtl.Len(ary);
		for (int i = 0; i < aryLen; i++) {
			bfr.Add("'");
			bfr.Add(ObjectUtl.ToStrOrNullMark(ary[i]));
			bfr.Add("' ");
		}
		System.out.println(bfr.ToStr() + StringUtl.Lf);
	}
	public static void EqNull(boolean expd, Object val) {EqNull(expd, val, null);}
	public static void EqNull(boolean expd, Object val, String msgFmt, Object... msgArgs) {
		if  (  expd  && val != null
			|| !expd && val == null
			) {
			String expdStr = expd        ? "null" : "not null";
			String actlStr = val == null ? "null" : "not null";
			WriteAndThrow(expdStr, actlStr, msgFmt, msgArgs);
		}
	}
	public static void EqBoolY(boolean actl)              {EqBool(BoolUtl.Y, actl, null);}
	public static void EqBool(boolean expd, boolean actl) {EqBool(expd, actl, null);}
	public static void EqBool(boolean expd, boolean actl, String msgFmt, Object... msgArgs) {
		if (expd != actl)
			WriteAndThrow(BoolUtl.ToStrLower(expd), BoolUtl.ToStrLower(actl), msgFmt, msgArgs);
	}
	public static void EqInt(int expd, int actl) {EqInt(expd, actl, null);}
	public static void EqInt(int expd, int actl, String msgFmt, Object... msgArgs) {
		if (expd != actl)
			WriteAndThrow(IntUtl.ToStr(expd), IntUtl.ToStr(actl), msgFmt, msgArgs);
	}
	public static void EqLong(long expd, long actl) {EqLong(expd, actl, null);}
	public static void EqLong(long expd, long actl, String msgFmt, Object... msgArgs) {
		if (expd != actl)
			WriteAndThrow(LongUtl.ToStr(expd), LongUtl.ToStr(actl), msgFmt, msgArgs);
	}
	public static void EqStr(String expd, byte[] actl, String msgFmt, Object... msgArgs) {EqStr(expd, StringUtl.NewByBryUtf8(actl), msgFmt, msgArgs);}
	public static void EqStr(String expd, byte[] actl) {EqStr(expd, StringUtl.NewByBryUtf8(actl), null);}
	public static void EqStr(String expd, String actl) {EqStr(expd, actl, null);}
	public static void EqStr(String expd, String actl, String msgFmt, Object... msgArgs) {
		if (!StringUtl.Eq(expd, actl))
			WriteAndThrow(expd, actl, msgFmt, msgArgs);
	}
	public static void EqAry(Object[] expd, Object[] actl) {EqAry(TypeIds.IdObj, expd, actl, "");}
	public static void EqAry(int[] expd, int[] actl)       {EqAry(TypeIds.IdInt, expd, actl, "");}
	public static void EqAry(String[] expd, String[] actl) {EqAry(TypeIds.IdBry, BryUtl.Ary(expd), BryUtl.Ary(actl), "noMsg");}
	private static void EqAry(int typeTid, Object expdAry, Object actlAry, String msgFmt, Object... msgArgs) {
		boolean[] failures = CalcFailures(typeTid, expdAry, actlAry);
		if (failures != null) {
			WriteFailHead(bfr, msgFmt, msgArgs);
			WriteFailBody(bfr, failures, typeTid, expdAry, actlAry);
			throw ErrUtl.NewMsg(bfr.ToStrAndClear());
		}
	}
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
					case TypeIds.IdBry:  eq = BryUtl.Eq((byte[])expdObj, (byte[])actlObj); break;
					case TypeIds.IdObj:  eq = ObjectUtl.Eq(expdObj, actlObj); break;
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
	private static void WriteFailHead(GfoStrBldr bfr, String msgFmt, Object[] msgArgs) {
		bfr.Add(SectionBgn);
		if (msgFmt != null) {
			bfr.Add(StringUtl.Format(msgFmt, msgArgs));
			bfr.Add(SectionMid);
		}
	}
	private static void WriteFailBody(GfoStrBldr bfr, boolean[] failures, int typeId, Object expdAry, Object actlAry) {
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
	private static void WriteFailItm(GfoStrBldr bfr, int typeId, Object ary, int len, int idx) {
		if (idx < len) {
			Object val = ArrayUtl.GetAt(ary, idx);
			switch (typeId) {
				case TypeIds.IdBool: bfr.AddBoolAsYn(BoolUtl.Cast(val)); break;
				case TypeIds.IdBry:  bfr.AddBry((byte[])val); break;
				case TypeIds.IdLong: bfr.AddLong(LongUtl.Cast(val)); break;
				case TypeIds.IdInt:  bfr.AddInt(IntUtl.Cast(val)); break;
				case TypeIds.IdByte: bfr.AddInt((int)(ByteUtl.Cast(val))); break;
				case TypeIds.IdObj:  bfr.Add(ObjectUtl.ToStr(val)); break;
				default:             throw ErrUtl.NewUnhandledDefault(typeId);
			}
		}
		else
			bfr.Add(Null);
		bfr.AddCharNl();
	}
	private static void WriteAndThrow(String expd, String actl, String msgFmt, Object... msgArgs) {
		WriteFailHead(bfr, msgFmt, msgArgs);
		bfr.Add("expd: ").Add(expd).AddCharNl();
		bfr.Add("actl: ").Add(actl).AddCharNl();
		bfr.Add(SectionEnd);
		throw ErrUtl.NewMsg(bfr.ToStrAndClear());
	}
}
