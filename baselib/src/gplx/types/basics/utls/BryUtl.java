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
import gplx.types.custom.brys.BryFind;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
public class BryUtl extends BryLni {
	public static final Class<?> ClsRefType = byte[].class;
	public static final String ClsValName = "byte[]";
	public static final byte[][] AryEmpty = new byte[0][];
	public static final byte[] TrimAryWs = Mask(256, AsciiByte.Tab, AsciiByte.Nl, AsciiByte.Cr, AsciiByte.Space);
	public static final byte DlmFld = (byte)'|';
	public static final byte DlmRow = (byte)'\n';
	public static final byte DlmQuote = (byte)'"';
	public static final byte AsciiZero = 48;
	public static final String FmtCsvDte = "yyyyMMdd HHmmss.fff";
	public static byte ByteNegSign = (byte)'-';
	public static int Len(byte[] v)            {return v == null ? 0 : v.length;}
	public static boolean IsNotNullOrEmpty(byte[] v)    {return v != null && v.length > 0;}
	public static boolean IsNullOrEmpty(byte[] v)    {return v == null || v.length == 0;}
	public static boolean HasAtEnd(byte[] src, byte[] lkp, int src_bgn, int src_end) {
		int lkp_len = lkp.length;
		if (src_bgn < 0) return false;
		int pos = src_end - lkp_len; if (pos < src_bgn) return false; // lkp is longer than src
		for (int i = 0; i < lkp_len; i++) {
			if (lkp[i] != src[i + pos]) return false;
		}
		return true;
	}
	public static void Set(byte[] src, int bgn, int end, byte[] repl) {
		int repl_len = repl.length;
		for (int i = 0; i < repl_len; i++)
			src[i + bgn] = repl[i];
	}
	public static void CopyToReversed(byte[] src, int src_bgn, int src_end, byte[] trg, int trg_bgn) {
		// copies src to trg, but in reverse order; EX: trg="1" src="432." -> "1.234"
		int len = src_end - src_bgn;
		for (int i = 0; i < len; i++)
			trg[trg_bgn + i] = src[src_end - i - 1];
	}
	public static boolean HasAtBgn(byte[] src, byte lkp)                    {return BryUtl.HasAtBgn(src, lkp, 0);}
	public static boolean HasAtBgn(byte[] src, byte lkp, int src_bgn)    {return src_bgn < src.length ? src[src_bgn] == lkp : false;}
	public static boolean HasAtBgn(byte[] src, byte[] lkp) {return BryUtl.HasAtBgn(src, lkp, 0, src.length);}
	public static boolean HasAtBgn(byte[] src, byte[] lkp, int src_bgn, int src_end) {
		int lkp_len = lkp.length;
		if (lkp_len + src_bgn > src_end) return false; // lkp is longer than src
		for (int i = 0; i < lkp_len; i++) {
			if (lkp[i] != src[i + src_bgn]) return false;
		}
		return true;
	}
	public static boolean HasAtEnd(byte[] src, byte lkp) {
		if (src == null) return false;
		int src_len = src.length;
		if (src_len == 0) return false;
		return src[src_len - 1] == lkp;
	}
	public static boolean HasAtEnd(byte[] src, byte[] lkp) {
		int src_len = src.length;
		return HasAtEnd(src, lkp, src_len - lkp.length, src_len);
	}
	public static byte[] Copy(byte[] src) {
		int src_len = src.length;
		byte[] trg = new byte[src_len];
		for (int i = 0; i < src_len; ++i)
			trg[i] = src[i];
		return trg;
	}
	public static byte[] RepeatSpace(int len) {return BryUtl.Repeat(AsciiByte.Space, len);}
	public static byte[] Repeat(byte b, int len) {
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = b;
		return rv;
	}
	public static byte[] RepeatBry(byte[] bry, int len) {
		int bry_len = bry.length;
		int rv_len = len * bry_len;
		byte[] rv = new byte[rv_len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < bry_len; j++) {
				rv[(i * bry_len) + j] = bry[j];
			}
		}
		return rv;
	}
	public static byte[] Add(byte[] src, byte b) {
		int src_len = src.length;
		byte[] rv = new byte[src_len + 1];
		CopyTo(src, 0, src_len, rv, 0);
		rv[src_len] = b;
		return rv;
	}
	public static byte[] Add(byte b, byte[] src) {
		int src_len = src.length;
		byte[] rv = new byte[src_len + 1];
		CopyTo(src, 0, src_len, rv, 1);
		rv[0] = b;
		return rv;
	}
	public static byte[] Add(byte[]... all) {
		int all_len = all.length, rv_len = 0;
		for (int i = 0; i < all_len; ++i) {
			byte[] cur = all[i]; if (cur == null) continue;
			rv_len += cur.length;
		}
		byte[] rv = new byte[rv_len];
		int rv_idx = 0;
		for (int i = 0; i < all_len; ++i) {
			byte[] cur = all[i]; if (cur == null) continue;
			int cur_len = cur.length;
			for (int j = 0; j < cur_len; ++j)
				rv[rv_idx++] = cur[j];
		}
		return rv;
	}
	public static byte[] AddWithDlm(byte[] dlm, byte[]... ary) {
		int ary_len = ary.length;
		if (ary_len == 0) return BryUtl.Empty;
		int dlm_len = dlm.length;
		int rv_len = dlm_len * (ary_len - 1);    // rv will have at least as many dlms as itms - 1
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			if (itm != null) rv_len += itm.length;
		}
		int rv_pos = 0;
		byte[] rv = new byte[rv_len];
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			if (i != 0) {
				for (int j = 0; j < dlm_len; j++) {
					rv[rv_pos++] = dlm[j];
				}
			}
			if (itm == null) continue;
			int itm_len = itm.length;
			for (int j = 0; j < itm_len; j++) {
				rv[rv_pos++] = itm[j];
			}
		}
		return rv;
	}
	public static byte[] AddWithDlm(byte dlm, byte[]... ary) {
		int ary_len = ary.length;
		if (ary_len == 0) return BryUtl.Empty;
		int rv_len = ary_len - 1;    // rv will have at least as many dlms as itms - 1
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			if (itm != null) rv_len += itm.length;
		}
		int rv_pos = 0;
		byte[] rv = new byte[rv_len];
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			if (i != 0) rv[rv_pos++] = dlm;
			if (itm == null) continue;
			int itm_len = itm.length;
			for (int j = 0; j < itm_len; j++) {
				rv[rv_pos++] = itm[j];
			}
		}
		return rv;
	}
	public static byte GetAtEnd(byte[] bry) {return bry[bry.length - 1];}    // don't bother checking for errors; depend on error trace
	public static boolean HasAt(byte[] src, int src_len, int pos, byte b) {return (pos < src_len) && (src[pos] == b);}
	public static boolean Has(byte[] src, byte lkp) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++)
			if (src[i] == lkp) return true;
		return false;
	}
	public static void ReplaceAllDirect(byte[] src, byte find, byte repl) {BryUtl.ReplaceAllDirect(src, find, repl, 0, src.length);}
	public static void ReplaceAllDirect(byte[] src, byte find, byte repl, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == find) src[i] = repl;
		}
	}
	public static byte[] TrimBgn(byte[] v, byte trim, int bgn) {
		boolean trimmed = false;
		int len = v.length;
		int pos = bgn;
		for (; pos < len; pos++) {
			if (v[pos] == trim) {
				trimmed = true;
			}
			else
				break;
		}
		return trimmed ? BryLni.Mid(v, pos, len) : v;
	}
	public static byte[] TrimEnd(byte[] v, byte trim, int end) {
		boolean trimmed = false;
		int pos = end - 1; // NOTE: -1 b/c callers will always be passing pos + 1; EX: src, src_len
		for (; pos > -1; pos--) {
			if (v[pos] == trim) {
				trimmed = true;
			}
			else
				break;
		}
		return trimmed ? BryLni.Mid(v, 0, pos + 1) : v;
	}
	public static int Compare(byte[] lhs, byte[] rhs) {
		if        (lhs == null)    return CompareAbleUtl.More;
		else if (rhs == null)    return CompareAbleUtl.Less;
		else                    return BryUtl.Compare(lhs, 0, lhs.length, rhs, 0, rhs.length);
	}
	public static int Compare(byte[] lhs, int lhs_bgn, int lhs_end, byte[] rhs, int rhs_bgn, int rhs_end) {
		int lhs_len = lhs_end - lhs_bgn, rhs_len = rhs_end - rhs_bgn;
		int min = lhs_len < rhs_len ? lhs_len : rhs_len;
		int rv = CompareAbleUtl.Same;
		for (int i = 0; i < min; i++) {
			rv = (lhs[i + lhs_bgn] & 0xff) - (rhs[i + rhs_bgn] & 0xff);    // PATCH.JAVA:need to convert to unsigned byte
			if (rv != CompareAbleUtl.Same) return rv > CompareAbleUtl.Same ? CompareAbleUtl.More : CompareAbleUtl.Less; // NOTE: changed from if (rv != CompareAble_.Same) return rv; DATE:2013-04-25
		}
		return IntUtl.Compare(lhs_len, rhs_len);    // lhs and rhs share same beginning bytes; return len comparisons
	}
	public static boolean EqCiA7(byte[] lhs, byte[] rhs, int rhs_bgn, int rhs_end) {
		if        (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		int lhs_len = lhs.length;
		int rhs_len = rhs_end - rhs_bgn;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; i++) {
			byte lhs_b = lhs[i];                if (lhs_b > 64 && lhs_b < 91) lhs_b += 32;    // lowercase
			byte rhs_b = rhs[i + rhs_bgn];        if (rhs_b > 64 && rhs_b < 91) rhs_b += 32;    // lowercase
			if (lhs_b != rhs_b) return false;
		}
		return true;
	}
	public static boolean MatchWithSwap(byte[] src, int src_bgn, int src_end, byte[] find, int find_bgn, int find_end, byte swap_src, byte swap_trg) {// same as above, but used by XOWA for ttl matches;
		int src_len = src.length;
		if (src_end > src_len) src_end = src_len;            // must limit src_end to src_len, else ArrayIndexOutOfBounds below; DATE:2015-01-31
		int find_len = find_end - find_bgn;
		if (find_len != src_end - src_bgn) return false;
		if (find_len == 0) return src_end - src_bgn == 0;    // "" only matches ""
		for (int i = 0; i < find_len; i++) {
			int pos = src_bgn + i;
			if (pos >= src_end) return false;    // ran out of src; exit; EX: src=ab; find=abc
			byte src_byte = src[pos];            if (src_byte == swap_src) src_byte = swap_trg;
			byte trg_byte = find[i + find_bgn];    if (trg_byte == swap_src) trg_byte = swap_trg;
			if (src_byte != trg_byte) return false;
		}
		return true;
	}
	public static byte[] Limit(byte[] src, int len) {
		if (src == null) return null;
		int src_len = src.length;
		return len < src_len ? BryLni.Mid(src, 0, len) : src;
	}
	public static byte[] MidByLen(byte[] src, int bgn, int len) {return Mid(src, bgn, bgn + len);}
	public static byte[] MidByLenSafe(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (bgn < 0) bgn = 0;
		if (len + bgn > src_len) len = (src_len - bgn);
		return Mid(src, bgn, bgn + len);
	}
	public static byte[] MidSafe(byte[] src, int bgn, int end) {
		if (src == null) return null;
		int src_len = src.length;
		if (bgn < 0)
			bgn = 0;
		else if (bgn >= src_len)
			bgn = src_len;

		if (end < 0)
			end = 0;
		else if (end >= src_len)
			end = src_len;

		if (bgn > end)
			bgn = end;
		else if (end < bgn)
			end = bgn;

		return Mid(src, bgn, end);
	}
	public static byte[] MidWithTrim(byte[] src, int bgn, int end) {
		int len = end - bgn; if (len == 0) return BryUtl.Empty;
		int actl_bgn = bgn, actl_end = end;
		// trim at bgn
		boolean chars_seen = false;
		for (int i = bgn; i < end; ++i) {
			switch (src[i]) {
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:
					break;
				default:
					chars_seen = true;
					actl_bgn = i;
					i = end;
					break;
			}
		}
		if (!chars_seen) return BryUtl.Empty;    // all ws
		// trim at end
		for (int i = end - 1; i >= actl_bgn; --i) {
			switch (src[i]) {
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:
					break;
				default:
					actl_end = i + 1;
					i = -1;
					break;
			}
		}
		// extract mid
		len = actl_end - actl_bgn; if (len == 0) return BryUtl.Empty;
		byte[] rv = new byte[len];
		for (int i = actl_bgn; i < actl_end; ++i)
			rv[i - actl_bgn] = src[i];
		return rv;
	}
	public static byte[] Mask(int len, byte... itms) {
		byte[] rv = new byte[len];
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; i++) {
			byte itm = itms[i];
			rv[itm & 0xFF] = itm; // PATCH.JAVA:need to convert to unsigned byte
		}
		return rv;
	}
	public static byte[] Trim(byte[] src) {return BryUtl.Trim(src, 0, src.length, true, true, TrimAryWs, true);}
	public static byte[] Trim(byte[] src, int bgn, int end) {return BryUtl.Trim(src, bgn, end, true, true, TrimAryWs, true);}
	public static byte[] Trim(byte[] src, int bgn, int end, boolean trim_bgn, boolean trim_end, byte[] trim_ary, boolean reuse_bry_if_noop) {
		int txt_bgn = bgn, txt_end = end;
		boolean all_ws = true;
		if (trim_bgn) {
			for (int i = bgn; i < end; i++) {
				byte b = src[i];
				if (trim_ary[b & 0xFF] == AsciiByte.Null) {
					txt_bgn = i;
					i = end;
					all_ws = false;
				}
			}
			if (all_ws) return BryUtl.Empty;
		}
		if (trim_end) {
			for (int i = end - 1; i > -1; i--) {
				byte b = src[i];
				if (trim_ary[b & 0xFF] == AsciiByte.Null) {
					txt_end = i + 1;
					i = -1;
					all_ws = false;
				}
			}
			if (all_ws) return BryUtl.Empty;
		}

		if (    reuse_bry_if_noop
			&&  bgn == 0       && end == src.length     // Trim is called on entire bry, not subset
			&&    bgn == txt_bgn && end == txt_end     // Trim hasn't trimmed anything
			) {
			return src;
		}
		else
			return BryLni.Mid(src, txt_bgn, txt_end);
	}
	public static boolean MatchBwdAny(byte[] src, int src_end, int src_bgn, byte[] find) {    // NOTE: utf8 doesn't matter (matching byte for byte)
		int find_len = find.length;
		for (int i = 0; i < find_len; i++) {
			int src_pos = src_end - i;
			int find_pos = find_len - i - 1;
			if (src_pos < src_bgn) return false;    // ran out of src; exit; EX: src=ab; find=abc
			if (src[src_pos] != find[find_pos]) return false;
		}
		return true;
	}
	public static byte[][] Ary(byte[]... ary) {return ary;}
	public static byte[][] Ary(String... ary) {
		int aryLen = ary.length;
		byte[][] rv = new byte[aryLen][];
		for (int i = 0; i < aryLen; i++) {
			String itm = ary[i];
			rv[i] = itm == null ? null : BryUtl.NewU8(itm);
		}
		return rv;
	}
	public static byte[] Cast(Object val) {return (byte[])val;}
	public static byte[] NewByByte(byte b) {return new byte[] {b};}
	public static byte[] CoalesceToEmpty(byte[] v) {return v == null ? BryUtl.Empty : v;}
	public static byte[] Coalesce(byte[] v, byte[] or) {return v == null ? or : v;}
	public static byte[] NewU8Safe(String str) {return str == null ? null : BryUtl.NewU8(str);}
	public static byte[] NewU8(String src) {
		try {
			int srcLen = src.length();
			if (srcLen == 0) return BryUtl.Empty;
			int bryLen = NewU8ByLen(src, srcLen);
			byte[] bry = new byte[bryLen];
			NewU8Write(src, srcLen, bry, 0);
			return bry;
		}
		catch (Exception e) {throw ErrUtl.NewFmt(e, "invalid UTF-8 sequence; src={0}", src);}
	}
	public static byte[][] AryObj(Object... ary) {
		if (ary == null) return BryUtl.AryEmpty;
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			Object itm = ary[i];
			rv[i] = itm == null ? null : BryUtl.NewU8(ObjectUtl.ToStrOrEmpty(itm));
		}
		return rv;
	}
	public static boolean AryEq(byte[][] lhs, byte[][] rhs) {
		int lhs_len = lhs.length;
		int rhs_len = rhs.length;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; ++i)
			if (!BryLni.Eq(lhs[i], rhs[i])) return false;
		return true;
	}
	public static byte[] IncrementLast(byte[] ary) {return BryUtl.IncrementLast(ary, ary.length - 1);}
	public static byte[] IncrementLast(byte[] ary, int end_idx) {
		for (int i = end_idx; i > -1; i--) {
			byte end_val_old = ary[i];
			byte end_val_new = (byte)(end_val_old + 1);
			ary[i] = end_val_new;
			if (end_val_new > (end_val_old & 0xff)) break;    // PATCH.JAVA:need to convert to unsigned byte
		}
		return ary;
	}
	public static byte[] UcaseAll(byte[] src)                        {return BryUtl.XcaseAll(BoolUtl.Y, src, 0, -1);}
	public static byte[] LcaseAll(byte[] src)                        {return BryUtl.XcaseAll(BoolUtl.N, src, 0, -1);}
	public static byte[] LcaseAll(byte[] src, int bgn, int end)    {return BryUtl.XcaseAll(BoolUtl.N, src, bgn, end);}
	private static byte[] XcaseAll(boolean upper, byte[] src, int bgn, int end) {
		if (src == null) return null;
		int len = end == -1 ? src.length : end - bgn; if (len == 0) return src;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; ++i) {
			byte b = src[i + bgn];
			if (upper) {
				if (b > 96 && b < 123) b -= 32;
			}
			else {
				if (b > 64 && b <  91) b += 32;
			}
			rv[i] = b;
		}
		return rv;
	}
	public static byte[] Ucase1st(byte[] src)                        {return BryUtl.Xcase1st(BoolUtl.Y, src);}
	public static byte[] Lcase1st(byte[] src)                        {return BryUtl.Xcase1st(BoolUtl.N, src);}
	private static byte[] Xcase1st(boolean upper, byte[] src) {
		if (src == null) return null;
		int len = src.length; if (len == 0) return src;
		byte[] rv = new byte[len];
		byte b = src[0];
		if (upper) {
			if (b > 96 && b < 123) b -= 32;
		}
		else {
			if (b > 64 && b <  91) b += 32;
		}
		rv[0] = b;
		for (int i = 1; i < len; ++i) {
			rv[i] = src[i];
		}
		return rv;
	}
	public static byte[] ReplaceCreate(byte[] src, byte find, byte replace) {
		byte[] rv = BryUtl.Copy(src);
		BryUtl.ReplaceReuse(rv, find, replace);
		return rv;
	}
	public static void ReplaceReuse(byte[] src, byte find, byte replace) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			if (src[i] == find) src[i] = replace;
		}
	}
	public static byte[] Replace(byte[] src, byte find, byte replace) {return BryUtl.Replace(src, 0, src.length, find, replace);}
	public static byte[] Replace(byte[] src, int bgn, int end, byte find, byte replace) {
		int src_len = src.length;
		byte[] rv = new byte[src_len];
		for (int i = bgn; i < end; ++i) {
			byte b = src[i];
			rv[i] = b == find ? replace : b;
		}
		for (int i = end; i < src_len; ++i)
			rv[i] = src[i];
		return rv;
	}
	public static byte[] NewByInts(int... ary) {
		int len = ary.length;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = (byte)ary[i];
		return rv;
	}
	public static int ToIntByA7(byte[] v) {
		int v_len = v.length;
		int mod = 8 * (v_len - 1);
		int rv = 0;
		for (int i = 0; i < v_len; i++) {
			rv |= v[i] << mod;
			mod -= 8;
		}
		return rv;
//            return    ((0xFF & v[0]) << 24)
//                |    ((0xFF & v[1]) << 16)
//                |    ((0xFF & v[2]) <<  8)
//                |    (0xFF  & v[3]);
	}
	public static byte[] NewByInt(int v) {
		byte b0 = (byte)(v >> 24);
		byte b1 = (byte)(v >> 16);
		byte b2 = (byte)(v >>  8);
		byte b3 = (byte)(v);
		if        (b0 != 0)    return new byte[] {b0, b1, b2, b3};
		else if    (b1 != 0)    return new byte[] {b1, b2, b3};
		else if    (b2 != 0)    return new byte[] {b2, b3};
		else                return new byte[] {b3};
	}
	public static boolean ToBoolOr(byte[] raw, boolean or) {
		return BryLni.Eq(raw, BoolUtl.TrueBry) ? true : or;
	}
	public static boolean ToBoolByInt(byte[] ary) {
		int rv = BryUtl.ToIntOr(ary, 0, ary.length, IntUtl.MinValue, BoolUtl.Y, null);
		switch (rv) {
			case 0: return false;
			case 1: return true;
			default: throw ErrUtl.NewArgs("could not parse to boolean int", "val", StringUtl.NewU8(ary));
		}
	}
	public static int ToInt(byte[] ary) {return BryUtl.ToIntOrFail(ary, 0, ary.length);}
	public static int ToIntOrFail(byte[] ary, int bgn, int end)        {
		int rv = BryUtl.ToIntOr(ary, bgn, end, IntUtl.MinValue, BoolUtl.Y, null);
		if (rv == IntUtl.MinValue) throw ErrUtl.NewArgs("could not parse to int", "val", StringUtl.NewU8(ary, bgn, end));
		return rv;
	}
	public static int ToIntOrNeg1(byte[] ary)                                {return BryUtl.ToIntOr(ary, 0    , ary.length, -1, BoolUtl.Y, null);}
	public static int ToIntOr(byte[] ary, int or)                                {return BryUtl.ToIntOr(ary, 0    , ary.length, or, BoolUtl.Y, null);}
	public static int ToIntOr(byte[] ary, int bgn, int end, int or)            {return BryUtl.ToIntOr(ary, bgn    , end        , or, BoolUtl.Y, null);}
	public static int ToIntOrStrict(byte[] ary, int or)                        {return BryUtl.ToIntOr(ary, 0    , ary.length, or, BoolUtl.N, null);}
	private static int ToIntOr(byte[] ary, int bgn, int end, int or, boolean sign_is_valid, byte[] ignore_ary) {
		if (    ary == null
			||    end == bgn                // null-len
			)    return or;
		int rv = 0, multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {    // -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv += multiple * (b - AsciiByte.Num0);
					multiple *= 10;
					break;
				case AsciiByte.Dash:
					return i == bgn && sign_is_valid ? rv * -1 : or;
				case AsciiByte.Plus:
					return i == bgn && sign_is_valid ? rv : or;
				default:
					boolean invalid = true;
					if (ignore_ary != null) {
						int ignore_ary_len = ignore_ary.length;
						for (int j = 0; j < ignore_ary_len; j++) {
							if (b == ignore_ary[j]) {
								invalid = false;
								break;
							}
						}
					}
					if (invalid) return or;
					break;
			}
		}
		return rv;
	}
	public static int ToIntOrTrimWs(byte[] ary, int bgn, int end, int or) {    // NOTE: same as To_int_or, except trims ws at bgn / end; DATE:2014-02-09
		if (end == bgn) return or;    // null len
		int rv = 0, multiple = 1;
		boolean numbers_seen = false, ws_seen = false;
		for (int i = end - 1; i >= bgn; i--) {    // -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv += multiple * (b - AsciiByte.Num0);
					multiple *= 10;
					if (ws_seen)    // "number ws number" pattern; invalid ws in middle; see tests
						return or;
					numbers_seen = true;
					break;
				case AsciiByte.Dash:
					return i == bgn ? rv * -1 : or;
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:
					if (numbers_seen)
						ws_seen = true;
					break;
				default: return or;
			}
		}
		return rv;
	}
	public static int ToIntOrLax(byte[] ary, int bgn, int end, int or) {
		if (end == bgn) return or;    // null-len
		int end_num = end;
		for (int i = bgn; i < end; i++) {
			byte b = ary[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					break;
				case AsciiByte.Dash:
					if (i != bgn) {
						end_num = i;
						i = end;
					}
					break;
				default:
					end_num = i;
					i = end;
					break;
			}
		}
		return BryUtl.ToIntOr(ary, bgn, end_num, or);
	}
	public static long ToLongOr(byte[] ary, long or) {return BryUtl.ToLongOr(ary, null, 0, ary.length, or);}
	public static long ToLongOr(byte[] ary, byte[] ignore_ary, int bgn, int end, long or) {
		if (    ary == null
			||    end == bgn                // null-len
			)    return or;
		long rv = 0, multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {    // -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv += multiple * (b - AsciiByte.Num0);
					multiple *= 10;
					break;
				case AsciiByte.Dash:
					return i == bgn ? rv * -1 : or;
				case AsciiByte.Plus:
					return i == bgn ? rv : or;
				default:
					boolean invalid = true;
					if (ignore_ary != null) {
						int ignore_ary_len = ignore_ary.length;
						for (int j = 0; j < ignore_ary_len; j++) {
							if (b == ignore_ary[j]) {
								invalid = false;
								break;
							}
						}
					}
					if (invalid) return or;
					break;
			}
		}
		return rv;
	}
	public static double ToDouble(byte[] ary, int bgn, int end)                {return DoubleUtl.Parse(StringUtl.NewU8(ary, bgn, end));}
	public static double ToDoubleOr(byte[] bry, double or)                    {return DoubleUtl.ParseOr(StringUtl.NewU8(bry, 0, bry.length), or);}
	public static double ToDoubleOr(byte[] ary, int bgn, int end, double or)    {return DoubleUtl.ParseOr(StringUtl.NewU8(ary, bgn, end), or);}
	public static byte[][] AryAdd(byte[][] lhs, byte[][] rhs) {
		int lhs_len = lhs.length, rhs_len = rhs.length;
		if        (lhs_len == 0) return rhs;
		else if    (rhs_len == 0) return lhs;
		else {
			byte[][] rv = new byte[lhs_len + rhs_len][];
			for (int i = 0; i < lhs_len; i++)
				rv[i]            = lhs[i];
			for (int i = 0; i < rhs_len; i++)
				rv[i + lhs_len] = rhs[i];
			return rv;
		}
	}
	public static int TrimEndPos(byte[] src, int end) {
		for (int i = end - 1; i > -1; i--) {
			switch (src[i]) {
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
					break;
				default:
					return i + 1;
			}
		}
		return 0;
	}
	public static void Clear(byte[] bry) {
		int len = bry.length;
		for (int i = 0; i < len; ++i)
			bry[i] = ByteUtl.Zero;
	}
	public static byte[] ToA7Bry(int val, int pad_len) {return BryUtl.ToA7Bry(val, null, 0, pad_len);}
	public static byte[] ToA7Bry(int val, byte[] ary, int aryPos, int pad_len) {
		int neg = 0;
		if    (val < 0) {
			val *= -1;
			neg = 1;
		}
		int digits = val == 0 ? 0 : MathUtl.Log10(val);
		digits += 1;                        // digits = log + 1; EX: Log(1-9) = 0, Log(10-99) = 1
		int ary_len = digits + neg, aryBgn = aryPos, pad = 0;
		if (ary_len < pad_len) {                // padding specified
			pad = pad_len - ary_len;
			ary_len = pad_len;
		}
		if (ary == null) ary = new byte[ary_len];
		long factor = 1;                    // factor needs to be long to handle 1 billion (for which factor would be 10 billion)
		for (int i = 0; i < digits; i++)    // calc maxFactor
			factor *= 10;
		if (neg == 1) ary[0] = ByteNegSign;

		for (int i = 0; i < pad; i++)        // fill ary with pad
			ary[i + aryBgn] = AsciiByte.ToA7Str(0);
		aryBgn += pad;                        // advance aryBgn by pad
		for (int i = neg; i < ary_len - pad; i++) {
			int denominator = (int)(factor / 10); // cache denominator to check for divide by 0
			int digit = denominator == 0 ? 0 : (int)((val % factor) / denominator);
			ary[aryBgn + i] = AsciiByte.ToA7Str(digit);
			factor /= 10;
		}
		return ary;
	}
	public static GfoDecimal ToDecimal(byte[] ary, int bgn, int end)            {return GfoDecimalUtl.Parse(StringUtl.NewU8(ary, bgn, end));}
	public static boolean Has(byte[] src, byte[] lkp) {return BryFind.FindFwd(src, lkp) != BryFind.NotFound;}
	public static byte[] ReplaceOne(byte[] orig, byte[] find, byte[] repl) {
		// find val
		int orig_len = orig.length;
		int find_pos = BryFind.Find(orig, find, 0, orig_len, true);
		if (find_pos == BryFind.NotFound) return orig; // nothing found; exit

		// do copy
		int find_len = find.length, repl_len = repl.length;
		int rv_len = orig_len + repl_len - find_len;
		byte[] rv = new byte[rv_len];
		CopyTo(orig, 0                  , find_pos, rv, 0                  ); // copy orig before repl
		CopyTo(repl, 0                  , repl_len, rv, find_pos           ); // copy repl
		CopyTo(orig, find_pos + find_len, orig_len, rv, find_pos + repl_len); // copy orig after repl
		return rv;
	}
}
