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
package gplx.objects.strings.bfrs;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.primitives.CharCode;
import gplx.objects.primitives.IntUtl;
import gplx.objects.strings.StringUtl;
import gplx.objects.strings.unicodes.UstringUtl;
public class GfoStrBldr {
	private StringBuilder sb = new StringBuilder();
	public int Len() {return sb.length();}
	public boolean HasNone() {return this.Len() == 0;}
	public boolean HasSome() {return this.Len() > 0;}
	public GfoStrBldr Clear() {Del(0, this.Len()); return this;}
	public GfoStrBldr Del(int bgn, int len) {sb.delete(bgn, len); return this;}
	public GfoStrBldr AddFmt(String format, Object... args) {Add(StringUtl.Format(format, args)); return this;}
	public GfoStrBldr AddAt(int idx, String s) {sb.insert(idx, s); return this;}
	public GfoStrBldr Add(String s) {sb.append(s); return this;}
	public GfoStrBldr AddChar(char c) {sb.append(c); return this;}
	public GfoStrBldr AddByte(byte i) {sb.append(i); return this;}
	public GfoStrBldr AddInt(int i) {sb.append(i); return this;}
	public GfoStrBldr AddLong(long i) {sb.append(i); return this;}
	public GfoStrBldr AddDouble(double i) {sb.append(i); return this;}
	public GfoStrBldr AddObj(Object o) {sb.append(o); return this;}
	public GfoStrBldr AddBry(byte[] v) {
		if (v != null)
			sb.append(StringUtl.NewByBryUtf8(v));
		return this;
	}
	public GfoStrBldr AddMid(String s, int bgn)              {sb.append(s, bgn, s.length()); return this;}
	public GfoStrBldr AddMid(String s, int bgn, int count)   {sb.append(s, bgn, count); return this;}
	public GfoStrBldr AddBool(boolean val) {
		this.Add(val ? BoolUtl.TrueStr : BoolUtl.FalseStr);
		return this;
	}
	public GfoStrBldr AddBoolAsYn(boolean val) {
		this.Add(val ? "y" : "n");
		return this;
	}
	public GfoStrBldr AddCharNl()    {return AddChar(CharCode.NewLine);}
	public GfoStrBldr AddCharSpace() {return AddChar(CharCode.Space);}
	public GfoStrBldr AddCharColon() {return AddChar(CharCode.Colon);}
	public GfoStrBldr AddCharRepeat(char c, int repeat) {
		this.EnsureCapacity(this.Len() + repeat);
		for (int i = 0; i < repeat; i++)
			AddChar(c);
		return this;
	}
	public GfoStrBldr AddCharByCode(int code) {
		if (code >= UstringUtl.SurrogateCpBgn && code <= UstringUtl.SurrogateCpEnd) {
			sb.append((char)((code - 0x10000) / 0x400 + 0xD800));
			sb.append((char)((code - 0x10000) % 0x400 + 0xDC00));
		}
		else {
			sb.append((char)code);
		}
		return this;
	}
	public GfoStrBldr AddIntPadBgn(char padChar, int strLen, int val) {
		int digitLen = IntUtl.CountDigits(val);
		int padLen = strLen - digitLen;
		if (padLen > 0)    // note that this skips padLen == 0, as well as guarding against negative padLen; EX: pad(" ", 3, 1234) -> "1234"
			AddCharRepeat(padChar, padLen);
		AddInt(val);
		return this;
	}
	public String ToStr() {return sb.toString();}
	public String ToStrAndClear() {
		String rv = ToStr();
		Clear();
		return rv;
	}
	@Override public String toString() {return ToStr();}
	private void EnsureCapacity(int capacity) {sb.ensureCapacity(capacity);}
}
