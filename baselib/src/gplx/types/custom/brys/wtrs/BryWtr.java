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
package gplx.types.custom.brys.wtrs;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.errs.ErrUtl;
public class BryWtr extends BryWtrLni {
	public BryWtr(int bfrMax) {
		super(bfrMax);
	}
	private int reset;
	public void BfrInit(byte[] bfr, int bfr_len) {
		synchronized (this) {
			this.bfr = bfr;
			this.bfrLen = bfr_len;
			this.bfrMax = bfr.length;    // NOTE: must sync bfr_max, else will fail later during add; bfr will think bfr has .length of bfr_max, when it actually has .length of bfr_len; DATE:2014-03-09
		}
	}
	public void ClearAndRls() {
		this.Clear();
		this.MkrRls();
	}
	public String ToStrAndRls() {return StringUtl.NewU8(ToBryAndRls());}
	public byte[] ToBryAndRls() {
		byte[] rv = null;
		synchronized (bfr) {
			rv = ToBry();
			this.Clear();
			if (reset > 0) ResetIfGt(reset);
			synchronized (this) {    // SAME: Mkr_rls()
				mkrMgr.Rls(mkr_idx);
				mkr_idx = -1;    // TS: DATE:2016-07-06
				mkrMgr = null;
			}
		}
		return rv;
	}
	public BryWtr ResetSet(int v) {reset = v; return this;}
	public BryWtr ResetIfGt(int limit) {
		if (bfrMax > limit) {
			this.bfrMax = limit;
			this.bfr = new byte[limit];
		}
		bfrLen = 0;
		return this;
	}
	public BryWtr Clear() {
		synchronized (this) {
			this.bfrLen = 0;
		}
		return this;
	}
	public BryWtr ClearAndReset() {bfrLen = 0; if (reset > 0) ResetIfGt(reset); return this;}
	public byte GetAtLastOrNilIfEmpty() {return bfrLen == 0 ? AsciiByte.Null : bfr[bfrLen - 1];}
	public BryWtr AddSafe(byte[] val) {return val == null ? this : Add(val);}
	public BryWtr Add(byte[] val) {LniAdd(val); return this;}
	public BryWtr AddMid(byte[] val, int bgn, int end) {
		int len = end - bgn;
		if (len < 0) throw ErrUtl.NewArgs("negative len", "bgn", bgn, "end", end, "excerpt", StringUtl.NewU8ByLen(val, bgn, bgn + 16));    // NOTE: check for invalid end < bgn, else difficult to debug errors later; DATE:2014-05-11
		if (bfrLen + len > bfrMax) Resize((bfrMax + len) * 2);
		BryLni.CopyTo(val, bgn, end, bfr, bfrLen);
		// ArrayUtl.Copy_to(val, bgn, bfr, bfr_len, len);
		bfrLen += len;
		return this;
	}
	public BryWtr AddReverseMid(byte[] val, int bgn, int end) {
		int len = end - bgn;
		if (len < 0) throw ErrUtl.NewArgs("negative len", "bgn", bgn, "end", end, "excerpt", StringUtl.NewU8ByLen(val, bgn, bgn + 16));    // NOTE: check for invalid end < bgn, else difficult to debug errors later; DATE:2014-05-11
		if (bfrLen + len > bfrMax) Resize((bfrMax + len) * 2);
		BryUtl.CopyToReversed(val, bgn, end, bfr, bfrLen);
		// ArrayUtl.Copy_to(val, bgn, bfr, bfr_len, len);
		bfrLen += len;
		return this;
	}
	public BryWtr AddMidWithSwap(byte[] val, int bgn, int end, byte swap_src, byte swap_trg) {
		int len = end - bgn;
		if (len < 0) throw ErrUtl.NewArgs("negative len", "bgn", bgn, "end", end, "excerpt", StringUtl.NewU8ByLen(val, bgn, bgn + 16));    // NOTE: check for invalid end < bgn, else difficult to debug errors later; DATE:2014-05-11
		if (bfrLen + len > bfrMax) Resize((bfrMax + len) * 2);
		int val_len = end - bgn;
		for (int i = 0; i < val_len; ++i) {
			byte b = val[i + bgn]; if (b == swap_src) b = swap_trg;
			bfr[i + bfrLen] = b;
		}
		bfrLen += len;
		return this;
	}
	public BryWtr AddBfrAndPreserve(BryWtr src) {
		int len = src.bfrLen;
		if (bfrLen + len > bfrMax) Resize((bfrMax + len) * 2);
		BryLni.CopyTo(src.bfr, 0, len, bfr, bfrLen);
		// ArrayUtl.Copy_to(src.bfr, 0, bfr, bfr_len, len);
		bfrLen += len;
		return this;
	}
	public BryWtr AddBfrAndClear(BryWtr src) {
		AddBfrAndPreserve(src);
		src.ClearAndReset();
		return this;
	}
	public BryWtr AddBfrOrMid(boolean escaped, BryWtr tmp_bfr, byte[] src, int src_bgn, int src_end) {
		return escaped
			? this.AddBfrAndClear(tmp_bfr)
			: this.AddMid(src, src_bgn, src_end);
	}
	public BryWtr AddBfrTrimAndClear(BryWtr src, boolean trim_bgn, boolean trim_end) {return AddBfrTrimAndClear(src, trim_bgn, trim_end, BryUtl.TrimAryWs);}
	public BryWtr AddBfrTrimAndClear(BryWtr src, boolean trim_bgn, boolean trim_end, byte[] trim_ary) {
		int src_len = src.bfrLen;
		if (bfrLen + src_len > bfrMax) Resize((bfrMax + src_len) * 2);
		byte[] src_bry = src.Bry();
		int src_bgn = 0, src_end = src_len;
		boolean all_ws = true;
		if (trim_bgn) {
			for (int i = 0; i < src_len; i++) {
				byte b = src_bry[i];
				if (trim_ary[b & 0xFF] == AsciiByte.Null) {
					src_bgn = i;
					i = src_len;
					all_ws = false;
				}
			}
			if (all_ws) return this;
		}
		if (trim_end) {
			for (int i = src_len - 1; i > -1; i--) {
				byte b = src_bry[i];
				if (trim_ary[b & 0xFF] == AsciiByte.Null) {
					src_end = i + 1;
					i = -1;
					all_ws = false;
				}
			}
			if (all_ws) return this;
		}
		src_len = src_end - src_bgn;
		BryLni.CopyTo(src.bfr, src_bgn, src_end, bfr, bfrLen);
		// ArrayUtl.Copy_to(src.bfr, src_bgn, bfr, bfr_len, src_len);
		bfrLen += src_len;
		src.Clear();
		return this;
	}
	public BryWtr AddByteEq()          {return AddByte(AsciiByte.Eq);}
	public BryWtr AddBytePipe()        {return AddByte(AsciiByte.Pipe);}
	public BryWtr AddByteComma()       {return AddByte(AsciiByte.Comma);}
	public BryWtr AddByteSemic()       {return AddByte(AsciiByte.Semic);}
	public BryWtr AddByteApos()        {return AddByte(AsciiByte.Apos);}
	public BryWtr AddByteSlash()       {return AddByte(AsciiByte.Slash);}
	public BryWtr AddByteBackslash()   {return AddByte(AsciiByte.Backslash);}
	public BryWtr AddByteQuote()       {return AddByte(AsciiByte.Quote);}
	public BryWtr AddByteSpace()       {return AddByte(AsciiByte.Space);}
	public BryWtr AddByteNl()          {return AddByte(AsciiByte.Nl);}
	public BryWtr AddByteDot()         {return AddByte(AsciiByte.Dot);}
	public BryWtr AddByteColon()       {return AddByte(AsciiByte.Colon);}
	public BryWtr AddByte(byte val)    {this.LniAddByte(val); return this;}
	public BryWtr AddByteRepeat(byte b, int len) {
		if (bfrLen + len > bfrMax) Resize((bfrMax + len) * 2);
		for (int i = 0; i < len; i++)
			bfr[i + bfrLen] = b;
		bfrLen += len;
		return this;
	}
	public BryWtr AddByteIfNotLast(byte b) {
		if (bfrLen == 0 || (bfrLen > 0 && bfr[bfrLen - 1] == b)) return this;
		this.AddByte(b);
		return this;
	}
	public BryWtr AddByteVariable(byte v)    {return AddIntVariable(v);}
	public BryWtr AddShortVariable(short v)    {return AddIntVariable(v);}
	public BryWtr AddU8Int(int val) {
		if (bfrLen + 4 > bfrMax) Resize((bfrMax + 4) * 2);
		int valLen = Utf16Utl.EncodeInt(val, bfr, bfrLen);
		bfrLen += valLen;
		return this;
	}
	public BryWtr AddBool(boolean v) {return Add(v ? BoolUtl.TrueBry : BoolUtl.FalseBry);}
	public BryWtr AddIntBool(boolean v) {return AddIntFixed(v ? 1 : 0, 1);}
	public BryWtr AddIntVariable(int val) {
		if (val < 0) {
			this.Add(IntUtl.ToBry(val));
			return this;
		}
		int log10 = IntUtl.Log10(val);
		int slots = val > -1 ? log10 + 1 : log10 * -1 + 2;
		return AddInt(val, log10, slots);
	}
	public BryWtr AddIntPadBgn(byte pad_byte, int str_len, int val) {
		int digit_len    = IntUtl.CountDigits(val);
		int pad_len        = str_len - digit_len;
		if (pad_len > 0)    // note that this skips pad_len == 0, as well as guarding against negative pad_len; EX: pad(" ", 3, 1234) -> "1234"
			AddByteRepeat(pad_byte, pad_len);
		AddIntFixed(val, digit_len);
		return this;
	}
	public BryWtr AddIntDigits(int digits, int val)    {return AddInt(val, IntUtl.Log10(val), digits);}
	public BryWtr AddIntFixed(int val, int digits) {return AddInt(val, IntUtl.Log10(val), digits);}
	public BryWtr AddInt(int val, int valLog, int arySlots) {
		int aryBgn = bfrLen, aryEnd = bfrLen + arySlots;
		if (aryEnd > bfrMax) Resize((aryEnd) * 2);
		if (val < 0) {
			bfr[aryBgn++] = AsciiByte.Dash;
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
			bfr[aryBgn + i] = (byte)((val / div) + 48);
			val %= div;
		}
		bfrLen = aryEnd;
		return this;
	}
	public BryWtr AddLongVariable(long v) {int digitCount = LongUtl.DigitCount(v); return AddLong(v, digitCount, digitCount);}
	public BryWtr AddLongFixed(long val, int digits) {return AddLong(val, LongUtl.DigitCount(val), digits);}
	protected BryWtr AddLong(long val, int digitCount, int arySlots) {
		int aryBgn = bfrLen, aryEnd = bfrLen + arySlots;
		if (aryEnd > bfrMax) Resize((aryEnd) * 2);
		if (val < 0) {
			bfr[aryBgn++] = AsciiByte.Dash;
			val *= -1;        // make positive
			arySlots -= 1;    // reduce slot by 1
		}
		if (digitCount >= arySlots) {
			val %= LongUtl.Log10Ary[arySlots];
		}
		for (int i = 0; i < arySlots; i++) {
			int logIdx = arySlots - i - 1;
			long div = logIdx < LongUtl.Log10AryLen ? LongUtl.Log10Ary[logIdx] : LongUtl.MaxValue;
			bfr[aryBgn + i] = (byte)((val / div) + 48);
			val %= div;
		}
		bfrLen = aryEnd;
		return this;
	}
	public BryWtr AddBry(byte dlm, byte[] v) {
		if (v == null) return this;
		int v_len = v.length;
		for (int i = 0; i < v_len; i++) {
			if (i != 0) this.AddByte(dlm);
			this.AddIntVariable(v[i]);
		}
		return this;
	}
	public BryWtr AddBryEscape(byte quote_byte, byte[] escape, byte[] val, int bgn, int end) {    // used for xml_wtr; DATE:2015-04-09
		boolean clean = true;    // add with chunks of bytes instead of one-by-one
		for (int i = bgn; i < end; ++i) {
			byte b = val[i];
			if (clean) {
				if    (b == quote_byte) {
					clean = false;
					this.AddMid(val, bgn, i);
					this.Add(escape);
				}
				else {}
			}
			else {
				if (b == quote_byte)    this.Add(escape);
				else                    this.AddByte(b);
			}
		}
		if (clean)
			AddMid(val, bgn, end);
		return this;
	}
	public BryWtr AddBryMany(byte[]... val) {
		int len = val.length;
		for (int i = 0; i < len; i++) {
			byte[] bry = val[i];
			if (bry != null && bry.length > 0)
				this.Add(bry);
		}
		return this;
	}
	public BryWtr AddBryEscapeHtml(byte[] val) {
		if (val == null) return this;
		return AddBryEscapeHtml(val, 0, val.length);
	}
	public BryWtr AddBryEscapeHtml(byte[] val, int bgn, int end) {
		HtmlBryBfr.EscapeHtml(this, BoolUtl.N, val, bgn, end);
		return this;
	}
	public BryWtr AddBryEscapeXml(byte[] val, int bgn, int end) {
		HtmlBryBfr.EscapeHtml(this, BoolUtl.Y, val, bgn, end);
		return this;
	}
	public BryWtr AddStrU8Nl(String s) {AddStrU8(s); return AddByteNl();}
	public BryWtr AddStrU8Null(String s) {return AddStrU8(s == null ? StringUtl.NullMark : s);}
	public BryWtr AddStrU8(String str) {
		try {
			this.LniAddStrU8(str);
			return this;
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "invalid UTF-8 sequence", "s", str);}
	}
	public BryWtr AddStrU8Many(String... val) {
		int len = val.length;
		for (int i = 0; i < len; ++i)
			AddStrU8(val[i]);
		return this;
	}
	public BryWtr AddStrU8Fmt(String fmt, Object... args) {
		AddStrU8(StringUtl.Format(fmt, args));
		return this;
	}
	public BryWtr AddStrA7Null(String s) {return AddStrA7(s == null ? StringUtl.NullMark : s);}
	public BryWtr AddStrA7Nl(String s) {AddStrA7(s); return AddByteNl();}
	public BryWtr AddStrA7(String str) {
		try {
			int bry_len = str.length();                        
			if (bfrLen + bry_len > bfrMax) Resize((bfrMax + bry_len) * 2);
			for (int i = 0; i < bry_len; ++i) {
				char c = str.charAt(i);                            
				if (c > 128) c = '?';
				bfr[i + bfrLen] = (byte)c;
			}
			bfrLen += bry_len;
			return this;
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "invalid UTF-8 sequence", "s", str);}
	}
	public BryWtr AddKvDlm(boolean line, String key, Object val) {
		this.AddStrA7(key).AddByteColon().AddByteSpace();
		this.Add(BryUtl.NewU8(ObjectUtl.ToStrOrNullMark(val)));
		this.AddByte(line ? AsciiByte.Nl : AsciiByte.Tab);
		return this;
	}
	public BryWtr AddFloat(float f) {AddStrA7(FloatUtl.ToStr(f)); return this;}
	public BryWtr AddDouble(double v) {AddStrA7(DoubleUtl.ToStr(v)); return this;}
	public BryWtr AddDate(GfoDate val)            {return AddDateSegs(AsciiByte.Space     , val.Year(), val.Month(),val.Day(), val.Hour(), val.Minute(), val.Second(), val.Frac());}
	public BryWtr AddDateUnderline(GfoDate val)    {return AddDateSegs(AsciiByte.Underline, val.Year(), val.Month(),val.Day(), val.Hour(), val.Minute(), val.Second(), val.Frac());}
	private BryWtr AddDateSegs(byte spr, int y, int M, int d, int H, int m, int s, int f) {        // yyyyMMdd HHmmss.fff
		if (bfrLen + 19      > bfrMax) Resize((bfrLen + 19) * 2);
		bfr[bfrLen +  0] = (byte)((y / 1000) + BryUtl.AsciiZero); y %= 1000;
		bfr[bfrLen +  1] = (byte)((y /  100) + BryUtl.AsciiZero); y %=  100;
		bfr[bfrLen +  2] = (byte)((y /   10) + BryUtl.AsciiZero); y %=   10;
		bfr[bfrLen +  3] = (byte)( y          + BryUtl.AsciiZero);
		bfr[bfrLen +  4] = (byte)((M /   10) + BryUtl.AsciiZero); M %=  10;
		bfr[bfrLen +  5] = (byte)( M          + BryUtl.AsciiZero);
		bfr[bfrLen +  6] = (byte)((d /   10) + BryUtl.AsciiZero); d %=  10;
		bfr[bfrLen +  7] = (byte)( d          + BryUtl.AsciiZero);
		bfr[bfrLen +  8] = spr;
		bfr[bfrLen +  9] = (byte)((H /   10) + BryUtl.AsciiZero); H %=  10;
		bfr[bfrLen + 10] = (byte)( H          + BryUtl.AsciiZero);
		bfr[bfrLen + 11] = (byte)((m /   10) + BryUtl.AsciiZero); m %=  10;
		bfr[bfrLen + 12] = (byte)( m          + BryUtl.AsciiZero);
		bfr[bfrLen + 13] = (byte)((s /   10) + BryUtl.AsciiZero); s %=  10;
		bfr[bfrLen + 14] = (byte)( s          + BryUtl.AsciiZero);
		bfr[bfrLen + 15] = AsciiByte.Dot;
		bfr[bfrLen + 16] = (byte)((f /  100) + BryUtl.AsciiZero); f %= 100;
		bfr[bfrLen + 17] = (byte)((f /   10) + BryUtl.AsciiZero); f %=  10;
		bfr[bfrLen + 18] = (byte)( f          + BryUtl.AsciiZero);
		bfrLen += 19;
		return this;
	}
	public BryWtr AddObj(Object o) {
		if (o == null) return this;    // treat null as empty String;
		Class<?> cls = o.getClass();
		if      (cls == byte[].class)  Add((byte[])o);
		else if (cls == Integer.class) AddIntVariable(IntUtl.Cast(o));
		else if (cls == Byte.class)    AddByte(ByteUtl.Cast(o));
		else if (cls == Long.class)    AddLongVariable(LongUtl.Cast(o));
		else if (cls == String.class)  AddStrU8((String)o);
		else if (cls == BryWtr.class)  AddBfrAndPreserve((BryWtr)o);
		else if (cls == GfoDate.class) AddDate((GfoDate)o);
		else if (cls == Boolean.class) AddYn(BoolUtl.Cast(o));
		else if (cls == Double.class)  AddDouble(DoubleUtl.Cast(o));
		else if (cls == Float.class)   AddFloat(FloatUtl.Cast(o));
		else {
			if (ClassUtl.IsAssignableFrom(BryBfrArg.class, cls))
				((BryBfrArg)o).AddToBfr(this);
			else
				AddStrU8(ObjectUtl.ToStr(o));
		}
		return this;
	}
	public BryWtr AddObjStrict(Object o) {// same as AddObj, except bool is saved as true/false instead of y/n
		if (o == null) return this;    // treat null as empty String;
		Class<?> cls = o.getClass();
		if      (cls == byte[].class)  Add((byte[])o);
		else if (cls == Integer.class) AddIntVariable(IntUtl.Cast(o));
		else if (cls == Byte.class)    AddByte(ByteUtl.Cast(o));
		else if (cls == Long.class)    AddLongVariable(LongUtl.Cast(o));
		else if (cls == String.class)  AddStrU8((String)o);
		else if (cls == BryWtr.class)  AddBfrAndPreserve((BryWtr)o);
		else if (cls == GfoDate.class) AddDate((GfoDate)o);
		else if (cls == Boolean.class) AddBool(BoolUtl.Cast(o));
		else if (cls == Double.class)  AddDouble(DoubleUtl.Cast(o));
		else if (cls == Float.class)   AddFloat(FloatUtl.Cast(o));
		else {
			if (ClassUtl.IsAssignableFrom(BryBfrArg.class, cls))
				((BryBfrArg)o).AddToBfr(this);
			else
				AddStrU8(ObjectUtl.ToStr(o));
		}
		return this;
	}
	public BryWtr AddYn(boolean v) {AddByte(v ? AsciiByte.Ltr_y : AsciiByte.Ltr_n); return this;}
	public boolean MatchEndByte(byte b)        {return bfrLen == 0 ? false : bfr[bfrLen - 1] == b;}
	public boolean MatchEndByteNlOrBos()    {return bfrLen == 0 ? true : bfr[bfrLen - 1] == AsciiByte.Nl;}
	public BryWtr InsertAt(int add_pos, byte[] add_bry) {return InsertAt(add_pos, add_bry, 0, add_bry.length);}
	public BryWtr InsertAt(int add_pos, byte[] add_bry, int add_bgn, int add_end) {
		int add_len = add_end - add_bgn;
		int new_max = bfrMax + add_len;
		byte[] new_bfr = new byte[new_max];
		if (add_pos > 0)
			BryLni.CopyTo(bfr    ,        0, add_pos, new_bfr, 0);
		BryLni.CopyTo(add_bry, add_bgn, add_end, new_bfr, add_pos);
		BryLni.CopyTo(bfr    , add_pos, bfrLen, new_bfr, add_pos + add_len);
		bfr = new_bfr;
		bfrLen += add_len;
		bfrMax = new_max;
		return this;
	}
	public BryWtr DelRngToBgn(int pos) {return DelRng(0, pos);}
	public BryWtr DelRngToEnd(int pos) {return DelRng(pos, bfrLen);}
	public BryWtr DelRng(int rng_bgn, int rng_end) {
		int rng_len = rng_end - rng_bgn;
		BryLni.CopyTo(bfr, rng_end, bfrLen, bfr, rng_bgn);
		bfrLen -= rng_len;
		return this;
	}
	public BryWtr DelBy1() {
		bfrLen -= 1; bfr[bfrLen] = 0; return this;
	}
	public BryWtr DelBy(int count) {
		int new_len = bfrLen - count;
		if (new_len > -1) bfrLen = new_len;
		return this;
	}
	public BryWtr TrimEnd(byte trim_byte) {
		if (bfrLen == 0) return this;
		int count = 0;
		for (int i = bfrLen - 1; i > -1; --i) {
			byte b = bfr[i];
			if (b == trim_byte)
				++count;
			else
				break;
		}
		if (count > 0)
			this.DelBy(count);
		return this;
	}
	public BryWtr TrimEndWs() {
		if (bfrLen == 0) return this;
		int count = 0;
		for (int i = bfrLen - 1; i > -1; --i) {
			byte b = bfr[i];
			if (TrimEndWsAry[b])
				++count;
			else
				break;
		}
		if (count > 0)
			this.DelBy(count);
		return this;
	}
	private static final boolean[] TrimEndWsAry = TrimEndWsNew();
	private static boolean[] TrimEndWsNew() {
		boolean[] rv = new boolean[256];
		rv[32] = true;
		rv[ 9] = true;
		rv[10] = true;
		rv[13] = true;
		rv[11] = true;
		return rv;
	}
	public boolean Eq(byte b) {return bfrLen == 1 && bfr[0] == b;}
	public byte[] ToBry(int bgn, int end) {return bfrLen == 0 ? BryUtl.Empty : BryLni.Mid(bfr, bgn, end);}
	public byte[] ToBryAndClearAndTrim() {return ToBryAndClearAndTrim(true, true, BryUtl.TrimAryWs);}
	public byte[] ToBryAndClearAndTrim(boolean trim_bgn, boolean trim_end, byte[] trim_bry) {
		byte[] rv = BryUtl.Trim(bfr, 0, bfrLen, trim_bgn, trim_end, trim_bry, false); // NOTE: must not reuse bry; ISSUE#:562; DATE:2019-09-02
		this.Clear();
		return rv;
	}
	public byte[] ToBryAndClear() {
		byte[] rv = ToBry();
		this.Clear();
		if (reset > 0) ResetIfGt(reset);
		return rv;
	}
	public byte[] ToBryAndClearAndRls() {
		byte[] rv = ToBryAndClear();
		this.MkrRls();
		return rv;
	}
	public byte[] ToReversedBryAndClear() {
		int len = this.Len();
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++) {
			rv[len - i - 1] = bfr[i];
		}
		this.Clear();
		if (reset > 0) ResetIfGt(reset);
		return rv;
	}
	public String ToStrAndClear()                   {return StringUtl.NewU8(ToBryAndClear());}
	public String ToStrAndClearAndTrim()            {return StringUtl.NewU8(ToBryAndClearAndTrim());}
	public int ToIntAndClear(int or) {int rv = ToInt(or); this.Clear(); return rv;}
	public int ToInt(int or) {
		switch (bfrLen) {
			case 0: return or;
			case 1: {
				byte b = bfr[0];
				return AsciiByte.IsNum(b) ? b - AsciiByte.Num0 : or;
			}
			default:
				long rv = 0, mult = 1;
				for (int i = bfrLen - 1; i > -1; i--) {
					byte b = bfr[i];
					if (!AsciiByte.IsNum(b)) return or;
					long dif = (b - AsciiByte.Num0) * mult;
					long new_val = rv + dif;
					if (new_val > IntUtl.MaxValue) return or;    // if number is > 2^32 consider error (int overflow); return or; DATE:2014-06-10
					rv = new_val;
					mult *= 10;
				}
				return (int)rv;
		}
	}
	public void Rls() {
		bfr = null;
		this.MkrRls();
	}
	public byte[][] ToBryAryAndClear() {
		if (bfrLen == 0) return BryUtl.AryEmpty;
		GfoListBase<Integer> line_ends = FindAll(AsciiByte.Nl);

		// create lines
		int lines_len = line_ends.Len();
		byte[][] rv = new byte[lines_len][];
		int line_bgn = 0;
		for (int i = 0; i < lines_len; ++i) {
			int line_end = line_ends.GetAt(i);
			rv[i] = BryLni.Mid(bfr, line_bgn, line_end);
			line_bgn = line_end + 1;
		}
		this.ClearAndReset();
		return rv;
	}
	public String[] ToStrAryAndClear() {
		if (bfrLen == 0) return StringUtl.AryEmpty;
		GfoListBase<Integer> line_ends = FindAll(AsciiByte.Nl);

		// create lines
		int lines_len = line_ends.Len();
		String[] rv = new String[lines_len];
		int line_bgn = 0;
		for (int i = 0; i < lines_len; ++i) {
			int line_end = line_ends.GetAt(i);
			rv[i] = StringUtl.NewU8(bfr, line_bgn, line_end);
			line_bgn = line_end + 1;
		}
		this.ClearAndReset();
		return rv;
	}
	private GfoListBase<Integer> FindAll(byte find) {
		GfoListBase<Integer> rv = new GfoListBase<Integer>();
		// scan bfr for nl
		int line_bgn = 0, line_end = 0;
		while (line_bgn < bfrLen) {
			line_end = BryFind.FindFwd(bfr, find, line_bgn, bfrLen);
			if (line_end == BryFind.NotFound) {    // no more \n; add bfr_end
				rv.Add(bfrLen);
				break;
			}
			else {                                    // \n found; add it
				rv.Add(line_end);
				line_bgn = line_end + 1;
			}
		}
		return rv;
	}
	@Override public int hashCode() {return BryRef.CalcHashCode(bfr, 0, bfrLen);}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;    // NOTE: strange, but null check needed; throws null error; EX.WP: File:Eug�ne Delacroix - La libert� guidant le peuple.jpg
		BryRef comp = (BryRef)obj; // NOTE: this should be removed; BryWtr should be compared to itself, not another class
		return BryLni.Eq(bfr, 0, bfrLen, comp.Val(), comp.Bgn(), comp.End());
	}
	public int MkrIdx()            {return mkr_idx;} private int mkr_idx = -1;
	public boolean MkrIdxIsNull()    {return mkr_idx == -1;}
	public BryWtr MkrInit(BryBfrMkrMgr mkrMgr, int itm) {
		synchronized (this) {
			this.mkrMgr = mkrMgr; this.mkr_idx = itm;
		}
		return this;
	}
	private BryBfrMkrMgr mkrMgr;
	public BryWtr MkrRls() {
		if (mkrMgr != null) {
			synchronized (this) {
				mkrMgr.Rls(mkr_idx);
				this.mkrMgr = null;
				this.mkr_idx = -1;
			}
		}
		return this;
	}

	public static final BryWtr[] AryEmpty = new BryWtr[0];
	public static BryWtr New()              {return new BryWtr(16);}
	public static BryWtr NewWithSize(int v) {return new BryWtr(v);}
	public static BryWtr NewAndReset(int v) {return new BryWtr(16).ResetSet(v);} // PERF: set initial size to 16, not reset val; allows for faster "startup"; DATE:2014-06-14
}
