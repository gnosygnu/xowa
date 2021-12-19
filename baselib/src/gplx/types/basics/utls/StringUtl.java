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
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.errs.ErrUtl;
public class StringUtl extends StringLni {
	public static boolean EqAny(String lhs, String... rhsAry) {
		for (int i = 0; i < rhsAry.length; i++)
			if (Eq(lhs, rhsAry[i])) return true;
		return false;
	}
	public static boolean In(String s, String... ary) {
		for (String itm : ary)
			if (Eq(s, itm)) return true;
		return false;
	}
	public static int Compare(String lhs, String rhs) {
		int compare = lhs.compareTo(rhs); // NOTE: should this do null check?
		// NOTE: need to do explicit comparison b/c String.compare returns magnitude of difference; "k".compareTo("a") == 10
		if      (compare == CompareAbleUtl.Same)    return CompareAbleUtl.Same;
		else if (compare <  CompareAbleUtl.Same)    return CompareAbleUtl.Less;
		else /* (compare >  CompareAbleUtl.Same) */ return CompareAbleUtl.More;
	}
	public static int CompareIgnoreCase(String lhs, String rhs) {
		if      (lhs == null && rhs != null) return CompareAbleUtl.Less;
		else if (lhs != null && rhs == null) return CompareAbleUtl.More;
		else if (lhs == null && rhs == null) return CompareAbleUtl.Same;
		else                                 return lhs.compareToIgnoreCase(rhs);
	}
	public static int Count(String s, String part) {
		int count = 0, pos = -1;    // -1 b/c first pass must be 0 (see pos + 1 below)
		do {
			pos = FindFwd(s, part, pos + 1);
			if (pos == FindNone) break;
			count++;
		}   while (true);
		return count;
	}
	public static String LowerFirst(String s) {
		int len = Len(s); if (len == 0) return Empty;
		String char0 = Lower(MidByLen(s, 0, 1));
		return len == 1 ? char0 : char0 + Mid(s, 1);
	}
	public static String UpperFirst(String s) {
		int len = Len(s); if (len == 0) return Empty;
		String char0 = Upper(MidByLen(s, 0, 1));
		return len == 1 ? char0 : char0 + Mid(s, 1);
	}
	public static String Repeat(String s, int count) {
		if (count < 0) throw ErrUtl.NewArgs("count cannot be negative", "count", count, "s", s);
		GfoStringBldr sb = new GfoStringBldr();
		for (int i = 0; i < count; i++)
			sb.Add(s);
		return sb.ToStr();
	}
	public static String PadBgn(String s, int totalLen, String pad) {return Pad(s, totalLen, pad, true);}
	public static String PadEnd(String s, int totalLen, String pad) {return Pad(s, totalLen, pad, false);}
	public static String Pad(String s, int totalLen, String pad, boolean bgn) {
		int sLen = Len(s);
		int padLen = totalLen - sLen; if (padLen < 0) return s;
		GfoStringBldr sb = new GfoStringBldr();
		if (!bgn) sb.Add(s);
		for (int i = 0; i < padLen; i++)
			sb.Add(pad);
		if (bgn) sb.Add(s);
		return sb.ToStr();
	}
	public static String Mid(String s, int bgn, int end) {
		try {return MidByLen(s, bgn, end - bgn);}
		catch (Exception e) {
			int len = s == null ? 0 : Len(s);
			String msg = "";
			if        (s == null)                  msg = "s is null";
			else if (bgn > end)                    msg = "@bgn > @end";
			else if (bgn < 0 || bgn >= len)        msg = "@bgn is invalid";
			else if (end < 0 || end >  len)        msg = "@end is invalid";
			throw ErrUtl.NewArgs(msg, "s", s, "bgn", bgn, "end", end, "len", len);
		}
	}
	public static String MidByLenSafe(String s, int bgn, int len) {
		if (bgn + len >= Len(s)) len = Len(s) - bgn;
		return MidByLen(s, bgn, len);
	}
	public static String GetStrBefore(String s, String spr) {
		int sprPos = FindFwd(s, spr); if (sprPos == FindNone) throw ErrUtl.NewArgs("could not find spr", "s", s, "spr", spr);
		return Mid(s, 0, sprPos);
	}
	public static String GetStrAfter(String s, String spr) {
		int sprPos = FindFwd(s, spr); if (sprPos == FindNone) throw ErrUtl.NewArgs("could not find spr", "s", s, "spr", spr);
		return Mid(s, sprPos + 1);
	}
	public static String ExtractAfterBwd(String src, String dlm) {
		int dlm_pos = FindBwd(src, dlm); if (dlm_pos == FindNone) return Empty;
		int src_len = Len(src); if (dlm_pos == src_len - 1) return Empty;
		return Mid(src, dlm_pos + 1, src_len);
	}
	public static String LimitToFirst(String s, int len) {
		if (len < 0) throw ErrUtl.NewFmt("len cannot be < 0; len={0}", len);
		int sLen = Len(s); if (len > sLen) return s;
		return MidByLen(s, 0, len);
	}
	public static String DelBgn(String s, int count) {
		if (count < 0) throw ErrUtl.NewFmt("count cannot be < 0; count={0}", count);
		if (s == null) throw ErrUtl.NewNull("s");
		int len = Len(s); if (count > len) throw ErrUtl.NewArgs("count cannot be > @len", "count", count, "len", len);
		return Mid(s, count);
	}
	public static String DelEnd(String s, int count) {
		if (count < 0) throw ErrUtl.NewFmt("len cannot be < 0; len={0}", count);
		if (s == null) throw ErrUtl.NewNull("s");
		int len = Len(s); if (count > len) throw ErrUtl.NewArgs("count cannot be > len", "count", count, "len", len);
		return MidByLen(s, 0, len + -count);
	}
	public static String DelEndIf(String s, String find) {
		if (s == null) throw ErrUtl.NewNull("s");
		if (find == null) throw ErrUtl.NewNull("find");
		return HasAtEnd(s, find) ? MidByLen(s, 0, Len(s) - Len(find)) : s;
	}
	public static String Insert(String s, int pos, String toInsert) {
		if (pos < 0 || pos >= Len(s)) throw ErrUtl.NewArgs("insert failed; pos invalid", "pos", pos, "s", s, "toInsert", toInsert);
		return s.substring(0, pos) + toInsert + s.substring(pos);
	}
	public static String FormatOrEmptyStrIfNull(String fmt, Object arg) {return arg == null ? "" : Format(fmt, arg);}
	public static String Format(String fmt, Object... args) {return StringFormatLni.Format(fmt, args);}
	public static String Concat(char... ary) {return new String(ary);}
	public static String Concat(String s1, String s2, String s3) {return s1 + s2 + s3;}
	public static String Concat(String... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		for (String val : ary)
			sb.Add(val);
		return sb.ToStr();
	}
	public static String ConcatObjs(Object... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		for (Object val : ary)
			sb.AddObj(val);
		return sb.ToStr();
	}
	public static String ConcatWithObj(String spr, Object... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		int len = ArrayUtl.Len(ary);
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add(spr);
			Object val = ary[i];
			sb.AddObj(ObjectUtl.ToStrOrEmpty(val));
		}
		return sb.ToStr();
	}
	public static String ConcatWith(String spr, String... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add(spr);
			sb.Add(ary[i]);
		}
		return sb.ToStr();
	}
	public static String ConcatLinesNl(String... values) {
		GfoStringBldr sb = new GfoStringBldr();
		for (String val : values)
			sb.Add(val).Add("\n");
		return sb.ToStr();
	}
	public static String ConcatLinesNlSkipLast(String... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		int ary_len = ary.length; int ary_end = ary_len - 1;
		for (int i = 0; i < ary_len; i++) {
			sb.Add(ary[i]);
			if (i != ary_end) sb.Add("\n");
		}
		return sb.ToStr();
	}
	public static String ConcatLinesCrlf(String... values) {
		GfoStringBldr sb = new GfoStringBldr();
		for (String val : values)
			sb.Add(val).Add(CrLf);
		return sb.ToStr();
	}
	public static String ConcatLinesCrlfSkipLast(String... values) {
		GfoStringBldr sb = new GfoStringBldr();
		for (String val : values) {
			if (sb.Len() != 0) sb.Add(CrLf);
			sb.Add(val);
		}
		return sb.ToStr();
	}
	public static String[] Ary(String... ary) {return ary;}
	public static String[] Ary(byte[]... ary) {
		if (ary == null) return AryEmpty;
		int ary_len = ary.length;
		String[] rv = new String[ary_len];
		for (int i = 0; i < ary_len; i++) {
			byte[] itm = ary[i];
			rv[i] = itm == null ? null : NewU8(itm);
		}
		return rv;
	}
	public static String[] AryWoNull(String... ary) {
		GfoListBase<String> list = new GfoListBase<>();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			if (itm == null) continue;
			list.Add(itm);
		}
		return list.ToAry(String.class);
	}
	public static String AryToStr(String... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		for (String s : ary)
			sb.Add(s).Add(";");
		return sb.ToStr();
	}
	public static String[] AryAdd(String[]... arys) {
		if (arys == null) return AryEmpty;
		int arys_len = arys.length;
		int rv_len = 0;
		for (int i = 0; i < arys_len; i++) {
			String[] ary = arys[i];
			rv_len += ary.length;
		}
		int rv_idx = 0;
		String[] rv = new String[rv_len];
		for (int i = 0; i < arys_len; i++) {
			String[] ary = arys[i];
			int ary_len = ary.length;
			for (int j = 0; j < ary_len; j++)
				rv[rv_idx++] = ary[j];
		}
		return rv;
	}
	public static String[] AryFlatten(String[][] src_ary) {
		int trg_len = 0;
		int src_len = ArrayUtl.Len(src_ary);
		for (int i = 0; i < src_len; i++) {
			String[] itm = src_ary[i];
			if (itm != null) trg_len += ArrayUtl.Len(itm);
		}
		String[] trg_ary = new String[trg_len];
		trg_len = 0;
		for (int i = 0; i < src_len; i++) {
			String[] itm = src_ary[i];
			if (itm == null) continue;
			int itm_len = ArrayUtl.Len(itm);
			for (int j = 0; j < itm_len; j++)
				trg_ary[trg_len++] = itm[j];
		}
		return trg_ary;
	}
	public static String[] Split(String raw, char dlm) {return Split(raw, dlm, false);}
	public static String[] Split(String raw, char dlm, boolean addEmptyIfDlmIsLast) {
		GfoListBase<String> list = new GfoListBase<>();
		GfoStringBldr sb = new GfoStringBldr();
		int rawLen = Len(raw); char c = '\0';
		for (int i = 0; i < rawLen; i++) {
			c = CharAt(raw, i);
			if (c == dlm) {
				if (!addEmptyIfDlmIsLast && sb.Len() == 0 && i == rawLen - 1) {}
				else list.Add(sb.ToStrAndClear());
			}
			else
				sb.AddChar(c);
		}
		if (sb.Len() > 0)
			list.Add(sb.ToStrAndClear());
		return list.ToAry(String.class);
	}
	public static String[] Split(String s, String separator) {return SplitDo(s, separator, false);}
	public static String[] SplitLinesNl(String s) {return Split(s, Nl);}
	public static String[] SplitLinesCrLf(String s) {return Split(s, CrLf);}
	public static String[] SplitLinesAny(String s) {return SplitDo(s, Nl, true);}
	public static String[] SplitLang(String s, char c) {return s.split(Character.toString(c));}
	private static String[] SplitDo(String raw, String spr, boolean skipChar13) {
		if (   Eq(raw, "")            // "".Split('a') return array with one member: ""
			|| Eq(spr, ""))        // "a".Split('\0') returns array with one member: "a"
			return new String[] {raw};
		GfoListBase<String> list = new GfoListBase<>();
		GfoStringBldr sb = new GfoStringBldr();
		int i = 0, sprPos = 0; boolean sprMatched = false; char spr0 = CharAt(spr, 0);
		int textLength = Len(raw); int sprLength = Len(spr);
		while (true) {
			if (sprMatched
				|| i == textLength) {    // last pass; add whatever's in sb to list
				list.Add(sb.ToStrAndClear());
				if (sprMatched && i == textLength) list.Add(""); // if raw ends with spr and last pass, add emptyString as last
				sprMatched = false;
			}
			if (i == textLength) break;
			char c = CharAt(raw, i);
			if (skipChar13 && c == (char)13) {i++; continue;}
			if (c == spr0) {    // matches first char of spr
				sprPos = 1;
				while (true) {
					if (sprPos == sprLength) { // made it to end, must be match
						sprMatched = true;
						break;
					}
					if (i + sprPos == textLength) break; // ran out of raw; handles partial match at end of String; ab+, +-
					if (CharAt(raw, i + sprPos) != CharAt(spr, sprPos)) break; // no match
					sprPos++;
				}
				if (!sprMatched)    // add partial match to sb
					sb.Add(MidByLen(raw, i, sprPos));
				i += sprPos;
			}
			else {    // no spr match; just add char, increment pos
				sb.AddChar(c);
				i++;
			}
		}
		return list.ToAry(String.class);
	}
	public static String NewCharAry(char[] ary, int bgn, int len) {return new String(ary, bgn, len);}
}
