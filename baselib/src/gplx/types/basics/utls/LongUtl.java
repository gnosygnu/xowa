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
package gplx.types.basics.utls;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.errs.ErrUtl;
public class LongUtl {
	public static final String ClsValName = "long";
	public static final Class<?> ClsRefType = Long.class;
	public static final int Log10AryLen = 21;
	public static final long
		MinValue = Long.MIN_VALUE;
	public static final long MaxValue = Long.MAX_VALUE
	;
	public static long[] Log10Ary = new long[]
			{ 1, 10, 100, 1000, 10000
		, 100000, 1000000, 10000000, 100000000, 1000000000
			, LongUtl.Pow(10, 10), LongUtl.Pow(10, 11), LongUtl.Pow(10, 12), LongUtl.Pow(10, 13), LongUtl.Pow(10, 14)
			, LongUtl.Pow(10, 15), LongUtl.Pow(10, 16), LongUtl.Pow(10, 17), LongUtl.Pow(10, 18), LongUtl.Pow(10, 19)
			, LongUtl.MaxValue
			};
	public static long Cast(Object obj) {try {return (Long)obj;} catch(Exception e) {throw ErrUtl.NewCast(long.class, obj);}}
	public static String ToStr(long v) {return new Long(v).toString();}
	public static long Parse(String raw)            {try {return Long.parseLong(raw);} catch(Exception e) {throw ErrUtl.NewParse(long.class, raw);}}
	public static String ToStrPadBgn(long v, int reqdPlaces) {return StringUtl.Pad(ToStr(v), reqdPlaces, "0", true);}    // ex: 1, 3 returns 001
	public static long ParseOr(String raw, long or) {
		if (raw == null) return or;
		try {
			int rawLen = StringUtl.Len(raw);
			if (raw == null || rawLen == 0) return or;
			long rv = 0, factor = 1; int tmp = 0;
			for (int i = rawLen; i > 0; i--) {
				tmp = CharUtl.ToDigitOr(StringUtl.CharAt(raw, i - 1), IntUtl.MinValue);
				if (tmp == IntUtl.MinValue) return or;
				rv += (tmp * factor);
				factor *= 10;
			}
			return rv;
		} catch (Exception e) {return or;}
	}
	public static int Compare(long lhs, long rhs) {
		if        (lhs == rhs)     return CompareAbleUtl.Same;
		else if (lhs < rhs)        return CompareAbleUtl.Less;
		else                     return CompareAbleUtl.More;
	}
	private static int FindIdx(long[] ary, long find_val) {
		int ary_len = ary.length;
		int adj = 1;
		int prv_pos = 0;
		int prv_len = ary_len;
		int cur_len = 0;
		int cur_idx = 0;
		long cur_val = 0;
		while (true) {
			cur_len = prv_len / 2;
			if (prv_len % 2 == 1) ++cur_len;
			cur_idx = prv_pos + (cur_len * adj);
			if        (cur_idx < 0)            cur_idx = 0;
			else if (cur_idx >= ary_len)    cur_idx = ary_len - 1;
			cur_val = ary[cur_idx];
			if        (find_val <     cur_val) adj = -1;
			else if (find_val >     cur_val) adj =    1;
			else if (find_val == cur_val) return cur_idx;
			if (cur_len == 1) {
				if (adj == -1 && cur_idx > 0)
					return --cur_idx;
				return cur_idx;
			}
			prv_len = cur_len;
			prv_pos = cur_idx;
		}
	}
	public static int DigitCount(long v) {
		int adj = IntUtl.Base1;
		if (v < 0) {
			if (v == LongUtl.MinValue) return 19;    // NOTE: LongUtl.Min_value * -1 = LongUtl.Min_value
			v *= -1;
			++adj;
		}
		return LongUtl.FindIdx(Log10Ary, v) + adj;
	}
	public static long Pow(int val, int exp) {
		long rv = val;
		for (int i = 1; i < exp; i++)
			rv *= val;
		return rv;
	}
	public static long IntMerge(int hi, int lo)    {return (long)hi << 32 | (lo & 0xFFFFFFFFL);}
	public static int IntSplitLo(long v)            {return (int)(v);}
	public static int IntSplitHi(long v)            {return (int)(v >> 32);}
}
/* alternate for Int_merge; does not work in java
		public static long MergeInts(int lo, int hi)    {return (uint)(hi << 32) | (lo & 0xffffffff);}
		public static int  SplitLo(long v)                {return (int)(((ulong)v & 0x00000000ffffffff));}
		public static int  SplitHi(long v)                {return (int)(((ulong)v & 0xffffffff00000000)) >> 32;}
*/
