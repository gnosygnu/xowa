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
package gplx.types.basics.strings.bfrs;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.constants.CharCode;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class GfoStringBldr extends StringBuilderLni {
	public boolean HasNone() {return this.Len() == 0;}
	public boolean HasSome() {return this.Len() > 0;}
	public GfoStringBldr Clear() {LniDel(0, this.Len()); return this;}
	public GfoStringBldr AddFmt(String format, Object... args) {LniAdd(StringUtl.Format(format, args)); return this;}
	public GfoStringBldr Add(byte[] v) {
		if (v != null)
			this.LniAdd(StringUtl.NewU8(v));
		return this;
	}
	public GfoStringBldr AddChar(char v)                    {this.LniAddChar(v); return this;}
	public GfoStringBldr AddByte(byte v)                    {this.LniAddByte(v); return this;}
	public GfoStringBldr Add(int v)                         {this.LniAdd(v); return this;}
	public GfoStringBldr Add(String v)                      {this.LniAdd(v); return this;}
	public GfoStringBldr AddLong(long v)                    {this.LniAddLong(v); return this;}
	public GfoStringBldr AddObj(Object v)                   {this.LniAddObj(v); return this;}
	public GfoStringBldr AddMid(String v, int bgn)          {this.LniAddMid(v, bgn, v.length()); return this;}
	public GfoStringBldr AddMid(String v, int bgn, int len) {this.LniAddMid(v, bgn, len); return this;}
	public GfoStringBldr AddBool(boolean val) {
		this.LniAdd(val ? BoolUtl.TrueStr : BoolUtl.FalseStr);
		return this;
	}
	public GfoStringBldr AddBoolAsYn(boolean val) {
		this.LniAdd(val ? "y" : "n");
		return this;
	}
	public GfoStringBldr AddCharNl()    {return AddChar(CharCode.NewLine);}
	public GfoStringBldr AddCharSpace() {return AddChar(CharCode.Space);}
	public GfoStringBldr AddCharColon() {return AddChar(CharCode.Colon);}
	public GfoStringBldr AddCharRepeat(char c, int repeat) {
		this.LniEnsureCapacity(this.Len() + repeat);
		for (int i = 0; i < repeat; i++)
			AddChar(c);
		return this;
	}
	public GfoStringBldr AddByteRepeat(byte c, int repeat) {
		this.LniEnsureCapacity(this.Len() + repeat);
		for (int i = 0; i < repeat; i++)
			AddByte(c);
		return this;
	}
	public GfoStringBldr AddCharByCode(int code) {
		if (code >= Utf16Utl.SurrogateCpBgn && code <= Utf16Utl.SurrogateCpEnd) {
			this.LniAddChar((char)((code - 0x10000) / 0x400 + 0xD800));
			this.LniAddChar((char)((code - 0x10000) % 0x400 + 0xDC00));
		}
		else {
			this.LniAddChar((char)code);
		}
		return this;
	}
	public GfoStringBldr AddIntPadBgn(char padChar, int strLen, int val) {
		int digitLen = IntUtl.CountDigits(val);
		int padLen = strLen - digitLen;
		if (padLen > 0)    // note that this skips padLen == 0, as well as guarding against negative padLen; EX: pad(" ", 3, 1234) -> "1234"
			AddCharRepeat(padChar, padLen);
		Add(val);
		return this;
	}
	public GfoStringBldr AddIntFixed(int val, int digits) {return AddIntPad(val, IntUtl.Log10(val), digits);}
	public GfoStringBldr AddIntPad(int val, int valLog, int arySlots) {
		if (val < 0) {
			AddCharByCode(AsciiByte.Dash);
			val *= -1;        // make positive
			valLog *= -1;    // valLog will be negative; make positive
			arySlots -= 1;    // reduce slot by 1
		}
		if (valLog >= arySlots) {
			val %= IntUtl.Log10Vals[arySlots];
		}
		for (int i = 0; i < arySlots; i++) {
			int logIdx = arySlots - i - 1;
			int div = logIdx < IntUtl.Log10ValsLen ? IntUtl.Log10Vals[logIdx] : IntUtl.MaxValue;
			AddCharByCode((byte)((val / div) + 48));
			val %= div;
		}
		return this;
	}
	public String ToStrAndClear() {
		String rv = ToStr();
		Clear();
		return rv;
	}
}
