/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx;
import java.lang.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.ios.*;
import gplx.langs.htmls.entitys.*;
public class Bry_ {
	public static final String Cls_val_name = "byte[]";
	public static final    byte[] Empty = new byte[0];
	public static final    byte[][] Ary_empty = new byte[0][];
	public static final    Class<?> Cls_ref_type = byte[].class;
	public static byte[] cast(Object val) {return (byte[])val;}
	public static byte[] New_by_byte(byte b) {return new byte[] {b};}
	public static byte[] New_by_ints(int... ary) {
		int len = ary.length;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = (byte)ary[i];
		return rv;
	}
	public static byte[] New_by_objs(Bry_bfr bfr, Object... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Object itm = ary[i];
			Class<?> type = Type_adp_.ClassOf_obj(itm);
			if		(Type_adp_.Eq(type, int.class))		bfr.Add_byte((byte)Int_.cast(itm));
			else if	(Type_adp_.Eq(type, String.class))	bfr.Add_str_u8((String)itm);
			else if	(Type_adp_.Eq(type, byte[].class))	bfr.Add((byte[])itm);
			else											throw Err_.new_unhandled(Type_adp_.FullNameOf_type(type));
		}
		return bfr.To_bry_and_clear();
	}
	public static byte[] Coalesce_to_empty(byte[] v) {return v == null ? Bry_.Empty : v;}
	public static byte[] Coalesce(byte[] v, byte[] or) {return v == null ? or : v;}
	public static byte[] new_a7(String str) {
		if (str == null) return null;
		int str_len = str.length();						
		if (str_len == 0) return Bry_.Empty;
		byte[] rv = new byte[str_len];
		for (int i = 0; i < str_len; ++i) {
			char c = str.charAt(i);							
			if (c > 128) c = '?';
			rv[i] = (byte)c;
		}
		return rv;
	}
	public static byte[] new_u8_safe(String str) {return str == null ? null : new_u8(str);}
	public static byte[] new_u8(String str) {
		try {
			int str_len = str.length();							
			if (str_len == 0) return Bry_.Empty;
			int bry_len = new_u8__by_len(str, str_len);
			byte[] rv = new byte[bry_len];
			new_u8__write(str, str_len, rv, 0);
			return rv;
		}
		catch (Exception e) {throw Err_.new_exc(e, "core", "invalid UTF-8 sequence", "s", str);}
	}
	public static int new_u8__by_len(String s, int s_len) {
		int rv = 0;
		for (int i = 0; i < s_len; ++i) {
			char c = s.charAt(i);									
			int c_len = 0;
			if		(	 c <      128)		c_len = 1;		// 1 <<  7
			else if	(	 c <     2048)		c_len = 2;		// 1 << 11
			else if	(	(c >    55295)						// 0xD800
					&&	(c <    56320))		c_len = 4;		// 0xDFFF
			else							c_len = 3;		// 1 << 16
			if (c_len == 4) ++i;							// surrogate is 2 wide, not 1
			rv += c_len;
		}
		return rv;
	}
	public static byte[] New_u8_nl_apos(String... lines) {
		Bry_bfr bfr = Bry_bfr_.Get();
		try {
			New_u8_nl_apos(bfr, lines);
			return bfr.To_bry_and_clear();
		}
		finally {bfr.Mkr_rls();}
	}
	public static void New_u8_nl_apos(Bry_bfr bfr, String... lines) {
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; ++i) {
			if (i != 0) bfr.Add_byte_nl();
			byte[] line = Bry_.new_u8(lines[i]);
			boolean dirty = false;
			int prv = 0;
			int line_len = line.length;
			for (int j = 0; j < line_len; ++j) {
				byte b = line[j];
				if (b == Byte_ascii.Apos) {
					bfr.Add_mid(line, prv, j);
					bfr.Add_byte(Byte_ascii.Quote);
					dirty = true;
					prv = j + 1;
				}
			}
			if (dirty)
				bfr.Add_mid(line, prv, line_len);
			else
				bfr.Add(line);
		}
	}
	public static void new_u8__write(String str, int str_len, byte[] bry, int bry_pos) {
		for (int i = 0; i < str_len; ++i) {
			char c = str.charAt(i);								
			if		(	 c <   128) {
				bry[bry_pos++]		= (byte)c;
			}
			else if (	 c <  2048) {
				bry[bry_pos++] 	= (byte)(0xC0 | (c >>   6));
				bry[bry_pos++] 	= (byte)(0x80 | (c & 0x3F));
			}	
			else if	(	(c > 55295)							// 0xD800
					&&	(c < 56320)) {						// 0xDFFF
				if (i >= str_len) throw Err_.new_wo_type("incomplete surrogate pair at end of String", "char", c);
				char nxt_char = str.charAt(i + 1);					
				int v = 0x10000 + (c - 0xD800) * 0x400 + (nxt_char - 0xDC00);
				bry[bry_pos++] 	= (byte)(0xF0 | (v >> 18));
				bry[bry_pos++] 	= (byte)(0x80 | (v >> 12) & 0x3F);
				bry[bry_pos++] 	= (byte)(0x80 | (v >>  6) & 0x3F);
				bry[bry_pos++] 	= (byte)(0x80 | (v        & 0x3F));
				++i;
			}
			else {
				bry[bry_pos++] 	= (byte)(0xE0 | (c >> 12));
				bry[bry_pos++] 	= (byte)(0x80 | (c >>  6) & 0x3F);
				bry[bry_pos++] 	= (byte)(0x80 | (c        & 0x3F));
			}
		}
	}
	public static byte[] Copy(byte[] src) {
		int src_len = src.length;
		byte[] trg = new byte[src_len];
		for (int i = 0; i < src_len; ++i)
			trg[i] = src[i];
		return trg;
	}
	public static byte[] Resize(byte[] src, int trg_len) {return Resize(src, 0, trg_len);}
	public static byte[] Resize(byte[] src, int src_bgn, int trg_len) {
		byte[] trg = new byte[trg_len];
		int src_len = src.length; if (src_len > trg_len) src_len = trg_len;	// trg_len can be less than src_len
		Copy_by_len(src, src_bgn, src_len, trg, 0);
		return trg;
	}
	public static byte[] Repeat_space(int len) {return Repeat(Byte_ascii.Space, len);}
	public static byte[] Repeat(byte b, int len) {
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = b;
		return rv;
	}
	public static byte[] Repeat_bry(byte[] bry, int len) {
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
		Copy_by_pos(src, 0, src_len, rv, 0);
		rv[src_len] = b;
		return rv;
	}
	public static byte[] Add(byte b, byte[] src) {
		int src_len = src.length;
		byte[] rv = new byte[src_len + 1];
		Copy_by_pos(src, 0, src_len, rv, 1);
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
	public static byte[] Add_w_dlm(byte[] dlm, byte[]... ary) {
		int ary_len = ary.length;
		if (ary_len == 0) return Bry_.Empty;
		int dlm_len = dlm.length;
		int rv_len = dlm_len * (ary_len - 1);	// rv will have at least as many dlms as itms - 1	
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
	public static byte[] Add_w_dlm(byte dlm, byte[]... ary) {
		int ary_len = ary.length;
		if (ary_len == 0) return Bry_.Empty;
		int rv_len = ary_len - 1;	// rv will have at least as many dlms as itms - 1	
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
	public static int Len(byte[] v)			{return v == null ? 0 : v.length;}
	public static boolean Len_gt_0(byte[] v)	{return v != null && v.length > 0;}
	public static boolean Len_eq_0(byte[] v)	{return v == null || v.length == 0;}
	public static byte Get_at_end(byte[] bry) {return bry[bry.length - 1];}	// don't bother checking for errors; depend on error trace
	public static boolean Has_at(byte[] src, int src_len, int pos, byte b) {return (pos < src_len) && (src[pos] == b);}
	public static boolean Has(byte[] src, byte[] lkp) {return Bry_find_.Find_fwd(src, lkp) != Bry_find_.Not_found;}
	public static boolean Has(byte[] src, byte lkp) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++)
			if (src[i] == lkp) return true;
		return false;
	}
	public static boolean Has_at_bgn(byte[] src, byte lkp)					{return Has_at_bgn(src, lkp, 0);}
	public static boolean Has_at_bgn(byte[] src, byte lkp, int src_bgn)	{return src_bgn < src.length ? src[src_bgn] == lkp : false;}
	public static boolean Has_at_bgn(byte[] src, byte[] lkp) {return Has_at_bgn(src, lkp, 0, src.length);}
	public static boolean Has_at_bgn(byte[] src, byte[] lkp, int src_bgn, int src_end) {
		int lkp_len = lkp.length;
		if (lkp_len + src_bgn > src_end) return false; // lkp is longer than src
		for (int i = 0; i < lkp_len; i++) {
			if (lkp[i] != src[i + src_bgn]) return false;
		}
		return true;
	}
	public static boolean Has_at_end(byte[] src, byte lkp) {
		if (src == null) return false;
		int src_len = src.length;
		if (src_len == 0) return false;
		return src[src_len - 1] == lkp;
	}
	public static boolean Has_at_end(byte[] src, byte[] lkp) {int src_len = src.length; return Has_at_end(src, lkp, src_len - lkp.length, src_len);}
	public static boolean Has_at_end(byte[] src, byte[] lkp, int src_bgn, int src_end) {
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
	public static void Copy_by_pos(byte[] src, int src_bgn, int src_end, byte[] trg, int trg_bgn) {
		int trg_adj = trg_bgn - src_bgn;
		for (int i = src_bgn; i < src_end; i++)
			trg[i + trg_adj] = src[i];
	}
	public static void Copy_by_pos_reversed(byte[] src, int src_bgn, int src_end, byte[] trg, int trg_bgn) {
		int len = src_end - src_bgn;
		for (int i = 0; i < len; i++)
			trg[trg_bgn + i] = src[src_end - i - 1];
	}
	private static void Copy_by_len(byte[] src, int src_bgn, int src_len, byte[] trg, int trg_bgn) {
		for (int i = 0; i < src_len; i++)
			trg[i + trg_bgn] = src[i + src_bgn];
	}
	public static byte[] Replace_one(byte[] src, byte[] find, byte[] repl) {
		int src_len = src.length;
		int findPos = Bry_find_.Find(src, find, 0, src_len, true); if (findPos == Bry_find_.Not_found) return src;
		int findLen = find.length, replLen = repl.length;
		int rvLen = src_len + replLen - findLen;
		byte[] rv = new byte[rvLen];
		Copy_by_len(src	, 0					, findPos						, rv, 0		);
		Copy_by_len(repl, 0					, replLen						, rv, findPos	);
		Copy_by_len(src	, findPos + findLen	, src_len - findPos - findLen	, rv, findPos + replLen);
		return rv;
	}
	public static void Replace_all_direct(byte[] src, byte find, byte repl) {Replace_all_direct(src, find, repl, 0, src.length);}
	public static void Replace_all_direct(byte[] src, byte find, byte repl, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == find) src[i] = repl;			
		}
	}
	public static byte[] Limit(byte[] src, int len) {
		if (src == null) return null;
		int src_len = src.length;
		return len < src_len ? Bry_.Mid(src, 0, len) : src;
	}
	public static byte[] Mid_by_nearby(byte[] src, int pos, int around) {
		int bgn = pos - around; if (bgn <  0) bgn = 0;
		int src_len = src.length;
		int end = pos + around; if (end > src_len) end = src_len;
		return Mid(src, bgn, end);
	}
	public static byte[] Mid_by_len(byte[] src, int bgn, int len) {return Mid(src, bgn, bgn + len);}
	public static byte[] Mid_by_len_safe(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (bgn < 0) bgn = 0;
		if (len + bgn > src_len) len = (src_len - bgn);
		return Mid(src, bgn, bgn + len);
	}
	public static String MidByLenToStr(byte[] src, int bgn, int len) {
		int end = bgn + len; end = Int_.BoundEnd(end, src.length);
		byte[] ary = Bry_.Mid(src, bgn, end);
		return String_.new_u8(ary);
	}
	public static byte[] Mid_safe(byte[] src, int bgn, int end) {
		if (src == null) return null;
		int src_len = src.length;
		if		(bgn < 0)			bgn = 0;
		if		(end >= src_len)	end = src_len;
		if		(bgn > end) bgn = end;
		else if (end < bgn) end = bgn;
		return Mid(src, bgn, end);
	}
	public static byte[] Mid(byte[] src, int bgn) {return Mid(src, bgn, src.length);}
	public static byte[] Mid_or(byte[] src, int bgn, int end, byte[] or) {
		int src_len = src.length;
		if (	src == null
			||	(bgn < 0 || bgn > src_len)
			||	(end < 0 || end > src_len)
			||	(end < bgn)
			)
			return or;
		return bgn == src_len ? Bry_.Empty : Mid(src, bgn, src_len);
	}
	public static byte[] Mid(byte[] src, int bgn, int end) {
		try {
			int len = end - bgn; if (len == 0) return Bry_.Empty;
			byte[] rv = new byte[len];
			for (int i = bgn; i < end; i++)
				rv[i - bgn] = src[i];
			return rv;
		} catch (Exception e) {Err_.Noop(e); throw Err_.new_("Bry_", "mid failed", "bgn", bgn, "end", end);}
	}
	public static byte[] Mid_w_trim(byte[] src, int bgn, int end) {
		int len = end - bgn; if (len == 0) return Bry_.Empty;
		int actl_bgn = bgn, actl_end = end;
		// trim at bgn
		boolean chars_seen = false;
		for (int i = bgn; i < end; ++i) {
			switch (src[i]) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					break;
				default:
					chars_seen = true;
					actl_bgn = i;
					i = end;
					break;
			}
		}
		if (!chars_seen) return Bry_.Empty;	// all ws
		// trim at end
		for (int i = end - 1; i >= actl_bgn; --i) {
			switch (src[i]) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					break;
				default:
					actl_end = i + 1;
					i = -1;
					break;
			}
		}
		// extract mid
		len = actl_end - actl_bgn; if (len == 0) return Bry_.Empty;
		byte[] rv = new byte[len];
		for (int i = actl_bgn; i < actl_end; ++i)
			rv[i - actl_bgn] = src[i];
		return rv;
	}
	public static byte[] mask_(int len, byte... itms) {
		byte[] rv = new byte[len];
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; i++) {
			byte itm = itms[i];
			rv[itm & 0xFF] = itm; // PATCH.JAVA:need to convert to unsigned byte
		}
		return rv;
	}
	public static final    byte[] Trim_ary_ws = mask_(256, Byte_ascii.Tab, Byte_ascii.Nl, Byte_ascii.Cr, Byte_ascii.Space);
	public static byte[] Trim(byte[] src) {return Trim(src, 0, src.length, true, true, Trim_ary_ws);}
	public static byte[] Trim(byte[] src, int bgn, int end) {return Trim(src, bgn, end, true, true, Trim_ary_ws);}
	public static byte[] Trim(byte[] src, int bgn, int end, boolean trim_bgn, boolean trim_end, byte[] trim_ary) {
		int txt_bgn = bgn, txt_end = end;
		boolean all_ws = true;
		if (trim_bgn) {
			for (int i = bgn; i < end; i++) {
				byte b = src[i];
				if (trim_ary[b & 0xFF] == Byte_ascii.Null) {
					txt_bgn = i;
					i = end;
					all_ws = false;
				}
			}
			if (all_ws) return Bry_.Empty;
		}
		if (trim_end) {
			for (int i = end - 1; i > -1; i--) {
				byte b = src[i];
				if (trim_ary[b & 0xFF] == Byte_ascii.Null) {
					txt_end = i + 1;
					i = -1;
					all_ws = false;
				}
			}
			if (all_ws) return Bry_.Empty;
		}

		if (	bgn == 0       && end == src.length	 // Trim is called on entire bry, not subset
			&&	bgn == txt_bgn && end == txt_end     // Trim hasn't trimmed anything
			) {	
			return src;
		}
		else
			return Bry_.Mid(src, txt_bgn, txt_end);
	}
	public static byte[] Trim_end(byte[] v, byte trim, int end) {
		boolean trimmed = false;
		int pos = end - 1; // NOTE: -1 b/c callers will always be passing pos + 1; EX: src, src_len
		for (; pos > -1; pos--) {	
			if (v[pos] == trim) {
				trimmed = true;
			}
			else
				break;
		}
		return trimmed ? Bry_.Mid(v, 0, pos + 1) : v;
	}
	public static int Compare(byte[] lhs, byte[] rhs) {
		if		(lhs == null)	return CompareAble_.More;
		else if (rhs == null)	return CompareAble_.Less;
		else					return Compare(lhs, 0, lhs.length, rhs, 0, rhs.length);
	}
	public static int Compare(byte[] lhs, int lhs_bgn, int lhs_end, byte[] rhs, int rhs_bgn, int rhs_end) {
		int lhs_len = lhs_end - lhs_bgn, rhs_len = rhs_end - rhs_bgn;
		int min = lhs_len < rhs_len ? lhs_len : rhs_len;
		int rv = CompareAble_.Same;
		for (int i = 0; i < min; i++) {
			rv = (lhs[i + lhs_bgn] & 0xff) - (rhs[i + rhs_bgn] & 0xff);	// PATCH.JAVA:need to convert to unsigned byte
			if (rv != CompareAble_.Same) return rv > CompareAble_.Same ? CompareAble_.More : CompareAble_.Less; // NOTE: changed from if (rv != CompareAble_.Same) return rv; DATE:2013-04-25
		}
		return Int_.Compare(lhs_len, rhs_len);	// lhs and rhs share same beginning bytes; return len comparisons
	}
	public static boolean Eq(byte[] src, byte[] val) {return Eq(src, 0, src == null ? 0 : src.length, val);}
	public static boolean Eq(byte[] src, int src_bgn, int src_end, byte[] val) {
		if		(src == null && val == null) return true;
		else if (src == null || val == null) return false;
		if (src_bgn < 0) return false;
		int val_len = val.length;
		if (val_len != src_end - src_bgn) return false;
		int src_len = src.length;
		for (int i = 0; i < val_len; i++) {
			int src_pos = i + src_bgn;
			if (src_pos == src_len) return false;
			if (val[i] != src[src_pos]) return false;
		}
		return true;
	}
	public static boolean Eq_ci_a7(byte[] lhs, byte[] rhs, int rhs_bgn, int rhs_end) {
		if		(lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		int lhs_len = lhs.length;
		int rhs_len = rhs_end - rhs_bgn;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; i++) {
			byte lhs_b = lhs[i];				if (lhs_b > 64 && lhs_b < 91) lhs_b += 32;	// lowercase
			byte rhs_b = rhs[i + rhs_bgn];		if (rhs_b > 64 && rhs_b < 91) rhs_b += 32;	// lowercase
			if (lhs_b != rhs_b) return false;
		}
		return true;
	}
	public static boolean Match(byte[] src, byte[] find) {return Match(src, 0, src.length, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, byte[] find) {return Match(src, src_bgn, src.length, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, int src_end, byte[] find) {return Match(src, src_bgn, src_end, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, int src_end, byte[] find, int find_bgn, int find_end) {
		if (src_bgn == -1) return false;
		int src_len = src.length;
		if (src_end > src_len) src_end = src_len;			// must limit src_end to src_len, else ArrayIndexOutOfBounds below; DATE:2015-01-31
		int find_len = find_end - find_bgn;
		if (find_len != src_end - src_bgn) return false;
		if (find_len == 0) return src_end - src_bgn == 0;	// "" only matches ""
		for (int i = 0; i < find_len; i++) {
			int pos = src_bgn + i;
			if (pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			if (src[pos] != find[i + find_bgn]) return false;
		}
		return true;
	}
	public static boolean Match_w_swap(byte[] src, int src_bgn, int src_end, byte[] find, int find_bgn, int find_end, byte swap_src, byte swap_trg) {// same as above, but used by XOWA for ttl matches;
		int src_len = src.length;
		if (src_end > src_len) src_end = src_len;			// must limit src_end to src_len, else ArrayIndexOutOfBounds below; DATE:2015-01-31
		int find_len = find_end - find_bgn;
		if (find_len != src_end - src_bgn) return false;
		if (find_len == 0) return src_end - src_bgn == 0;	// "" only matches ""
		for (int i = 0; i < find_len; i++) {
			int pos = src_bgn + i;
			if (pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			byte src_byte = src[pos];			if (src_byte == swap_src) src_byte = swap_trg;
			byte trg_byte = find[i + find_bgn];	if (trg_byte == swap_src) trg_byte = swap_trg;
			if (src_byte != trg_byte) return false;
		}
		return true;
	}
	public static boolean Match_bwd_any(byte[] src, int src_end, int src_bgn, byte[] find) {	// NOTE: utf8 doesn't matter (matching byte for byte)
		int find_len = find.length;
		for (int i = 0; i < find_len; i++) {
			int src_pos = src_end - i;
			int find_pos = find_len - i - 1;
			if (src_pos < src_bgn) return false;	// ran out of src; exit; EX: src=ab; find=abc
			if (src[src_pos] != find[find_pos]) return false;
		}
		return true;
	}
	public static int To_int_by_a7(byte[] v) {
		int v_len = v.length;
		int mod = 8 * (v_len - 1);
		int rv = 0;
		for (int i = 0; i < v_len; i++) {
			rv |= v[i] << mod;
			mod -= 8;
		}
		return rv;
//			return	((0xFF & v[0]) << 24) 
//				|	((0xFF & v[1]) << 16)
//				|	((0xFF & v[2]) <<  8)
//				|	(0xFF  & v[3]);
	}
	public static byte[] To_a7_bry(int val, int pad_len) {return To_a7_bry(val, null, 0, pad_len);}
	public static byte[] To_a7_bry(int val, byte[] ary, int aryPos, int pad_len) {
		int neg = 0;
		if	(val < 0) {
			val *= -1;
			neg = 1;
		}
		int digits = val == 0 ? 0 : Math_.Log10(val);
		digits += 1;						// digits = log + 1; EX: Log(1-9) = 0, Log(10-99) = 1
		int ary_len = digits + neg, aryBgn = aryPos, pad = 0;
		if (ary_len < pad_len) {				// padding specified
			pad = pad_len - ary_len;
			ary_len = pad_len;
		}
		if (ary == null) ary = new byte[ary_len];
		long factor = 1;					// factor needs to be long to handle 1 billion (for which factor would be 10 billion)
		for (int i = 0; i < digits; i++)	// calc maxFactor
			factor *= 10;
		if (neg == 1) ary[0] = Byte_NegSign;

		for (int i = 0; i < pad; i++)		// fill ary with pad
			ary[i + aryBgn] = Byte_ascii.To_a7_str(0);
		aryBgn += pad;						// advance aryBgn by pad
		for (int i = neg; i < ary_len - pad; i++) {
			int denominator = (int)(factor / 10); // cache denominator to check for divide by 0
			int digit = denominator == 0 ? 0 : (int)((val % factor) / denominator);
			ary[aryBgn + i] = Byte_ascii.To_a7_str(digit);
			factor /= 10;
		}
		return ary;
	}
	public static byte[] new_by_int(int v) {
		byte b0 = (byte)(v >> 24);
		byte b1 = (byte)(v >> 16);
		byte b2 = (byte)(v >>  8);
		byte b3 = (byte)(v);
		if		(b0 != 0)	return new byte[] {b0, b1, b2, b3};
		else if	(b1 != 0)	return new byte[] {b1, b2, b3};
		else if	(b2 != 0)	return new byte[] {b2, b3};
		else				return new byte[] {b3};
	}
	public static boolean To_bool_or(byte[] raw, boolean or) {
		return Bry_.Eq(raw, Bool_.True_bry) ? true : or;
	}
	public static boolean To_bool_by_int(byte[] ary) {
		int rv = To_int_or(ary, 0, ary.length, Int_.Min_value, Bool_.Y, null);
		switch (rv) {
			case 0: return false;
			case 1: return true;
			default: throw Err_.new_wo_type("could not parse to boolean int", "val", String_.new_u8(ary));
		}
	}
	public static byte To_int_as_byte(byte[] ary, int bgn, int end, byte or)	{return (byte)To_int_or(ary, bgn, end, or);}
	public static int To_int(byte[] ary) {return To_int_or_fail(ary, 0, ary.length);}
	public static int To_int_or_fail(byte[] ary, int bgn, int end)		{
		int rv = To_int_or(ary, bgn, end, Int_.Min_value, Bool_.Y, null);
		if (rv == Int_.Min_value) throw Err_.new_wo_type("could not parse to int", "val", String_.new_u8(ary, bgn, end));
		return rv;
	}
	public static int To_int_or_neg1(byte[] ary)								{return To_int_or(ary, 0	, ary.length, -1, Bool_.Y, null);}
	public static int To_int_or(byte[] ary, int or)								{return To_int_or(ary, 0	, ary.length, or, Bool_.Y, null);}
	public static int To_int_or(byte[] ary, int bgn, int end, int or)			{return To_int_or(ary, bgn	, end		, or, Bool_.Y, null);}
	public static int To_int_or__strict(byte[] ary, int or)						{return To_int_or(ary, 0	, ary.length, or, Bool_.N, null);}
	private static int To_int_or(byte[] ary, int bgn, int end, int or, boolean sign_is_valid, byte[] ignore_ary) {
		if (	ary == null
			||	end == bgn				// null-len
			)	return or;
		int rv = 0, multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {	// -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv += multiple * (b - Byte_ascii.Num_0);
					multiple *= 10;
					break;
				case Byte_ascii.Dash:
					return i == bgn && sign_is_valid ? rv * -1 : or;
				case Byte_ascii.Plus:
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
	public static int To_int_or__trim_ws(byte[] ary, int bgn, int end, int or) {	// NOTE: same as To_int_or, except trims ws at bgn / end; DATE:2014-02-09
		if (end == bgn) return or;	// null len
		int rv = 0, multiple = 1;
		boolean numbers_seen = false, ws_seen = false;
		for (int i = end - 1; i >= bgn; i--) {	// -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv += multiple * (b - Byte_ascii.Num_0);
					multiple *= 10;
					if (ws_seen)	// "number ws number" pattern; invalid ws in middle; see tests
						return or;
					numbers_seen = true;
					break;
				case Byte_ascii.Dash:
					return i == bgn ? rv * -1 : or;
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					if (numbers_seen)
						ws_seen = true;
					break;						
				default: return or;
			}
		}
		return rv;
	}
	public static int To_int_or__lax(byte[] ary, int bgn, int end, int or) {
		if (end == bgn) return or;	// null-len
		int end_num = end;
		for (int i = bgn; i < end; i++) {
			byte b = ary[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					break;
				case Byte_ascii.Dash:
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
		return To_int_or(ary, bgn, end_num, or);
	}
	public static long To_long_or(byte[] ary, long or) {return To_long_or(ary, null, 0, ary.length, or);}
	public static long To_long_or(byte[] ary, byte[] ignore_ary, int bgn, int end, long or) {
		if (	ary == null
			||	end == bgn				// null-len
			)	return or;
		long rv = 0, multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {	// -1 b/c end will always be next char; EX: {{{1}}}; bgn = 3, end = 4
			byte b = ary[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv += multiple * (b - Byte_ascii.Num_0);
					multiple *= 10;
					break;
				case Byte_ascii.Dash:
					return i == bgn ? rv * -1 : or;
				case Byte_ascii.Plus:
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
	public static double To_double(byte[] ary, int bgn, int end)				{return Double_.parse(String_.new_u8(ary, bgn, end));}
	public static double To_double_or(byte[] bry, double or)					{return Double_.parse_or(String_.new_u8(bry, 0, bry.length), or);}
	public static double To_double_or(byte[] ary, int bgn, int end, double or)	{return Double_.parse_or(String_.new_u8(ary, bgn, end), or);}
	public static Decimal_adp To_decimal(byte[] ary, int bgn, int end)			{return Decimal_adp_.parse(String_.new_u8(ary, bgn, end));}
	public static byte[][] Ary_add(byte[][] lhs, byte[][] rhs) {
		int lhs_len = lhs.length, rhs_len = rhs.length;
		if		(lhs_len == 0) return rhs;
		else if	(rhs_len == 0) return lhs;
		else {
			byte[][] rv = new byte[lhs_len + rhs_len][];
			for (int i = 0; i < lhs_len; i++)
				rv[i]			= lhs[i];
			for (int i = 0; i < rhs_len; i++)
				rv[i + lhs_len] = rhs[i];
			return rv;
		}
	}
	public static byte[][] Ary(byte[]... ary) {return ary;}
	public static byte[][] Ary(String... ary) {
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			rv[i] = itm == null ? null : Bry_.new_u8(itm);
		}
		return rv;
	}
	public static byte[][] Ary_obj(Object... ary) {
		if (ary == null) return Bry_.Ary_empty;
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			Object itm = ary[i];
			rv[i] = itm == null ? null : Bry_.new_u8(Object_.Xto_str_strict_or_empty(itm));
		}
		return rv;
	}
	public static boolean Ary_eq(byte[][] lhs, byte[][] rhs) {
		int lhs_len = lhs.length;
		int rhs_len = rhs.length;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; ++i)
			if (!Bry_.Eq(lhs[i], rhs[i])) return false;
		return true;
	}
	public static final    byte Dlm_fld = (byte)'|', Dlm_row = (byte)'\n', Dlm_quote = (byte)'"', Dlm_null = 0, Ascii_zero = 48;
	public static final    String Fmt_csvDte = "yyyyMMdd HHmmss.fff";
	public static DateAdp ReadCsvDte(byte[] ary, Int_obj_ref posRef, byte lkp) {// ASSUME: fmt = yyyyMMdd HHmmss.fff
		int y = 0, M = 0, d = 0, H = 0, m = 0, s = 0, f = 0;
		int bgn = posRef.Val();
		y += (ary[bgn +  0] - Ascii_zero) * 1000;
		y += (ary[bgn +  1] - Ascii_zero) *  100;
		y += (ary[bgn +  2] - Ascii_zero) *   10;
		y += (ary[bgn +  3] - Ascii_zero);
		M += (ary[bgn +  4] - Ascii_zero) *   10;
		M += (ary[bgn +  5] - Ascii_zero);
		d += (ary[bgn +  6] - Ascii_zero) *   10;
		d += (ary[bgn +  7] - Ascii_zero);
		H += (ary[bgn +  9] - Ascii_zero) *   10;
		H += (ary[bgn + 10] - Ascii_zero);
		m += (ary[bgn + 11] - Ascii_zero) *   10;
		m += (ary[bgn + 12] - Ascii_zero);
		s += (ary[bgn + 13] - Ascii_zero) *   10;
		s += (ary[bgn + 14] - Ascii_zero);
		f += (ary[bgn + 16] - Ascii_zero) *  100;
		f += (ary[bgn + 17] - Ascii_zero) *   10;
		f += (ary[bgn + 18] - Ascii_zero);
		if (ary[bgn + 19] != lkp) throw Err_.new_wo_type("csv date is invalid", "txt", String_.new_u8__by_len(ary, bgn, 20));
		posRef.Val_add(19 + 1); // +1=lkp.len
		return DateAdp_.new_(y, M, d, H, m, s, f);
	}
	public static String ReadCsvStr(byte[] ary, Int_obj_ref posRef, byte lkp)				{return String_.new_u8(ReadCsvBry(ary, posRef, lkp, true));}
	public static byte[] ReadCsvBry(byte[] ary, Int_obj_ref posRef, byte lkp)				{return ReadCsvBry(ary, posRef, lkp, true);}
	public static byte[] ReadCsvBry(byte[] ary, Int_obj_ref posRef, byte lkp, boolean make)	{
		int bgn = posRef.Val(), aryLen = ary.length;
		Bry_bfr bb = null;
		if (aryLen > 0 && ary[0] == Dlm_quote) {
			int pos = bgn + 1;	// +1 to skip quote
			if (make) bb = Bry_bfr_.New();
			while (true) {
				if (pos == aryLen) throw Err_.new_wo_type("endOfAry reached, but no quote found", "txt", String_.new_u8__by_len(ary, bgn, pos));
				byte b = ary[pos];
				if (b == Dlm_quote) {                            
					if (pos == aryLen - 1) throw Err_.new_wo_type("endOfAry reached, quote found but lkp not", "txt", String_.new_u8__by_len(ary, bgn, pos));
					byte next = ary[pos + 1];
					if		(next == Dlm_quote) {	// byte followed by quote
						if (make) bb.Add_byte(b);
						pos += 2;
					}
					else if (next == lkp) {
						posRef.Val_(pos + 2);	// 1=endQuote;1=lkp;
						return make ? bb.To_bry() : Bry_.Empty;
					}
					else throw Err_.new_wo_type("quote found, but not doubled", "txt", String_.new_u8__by_len(ary, bgn, pos + 1));
				}
				else {
					if (make) bb.Add_byte(b);
					pos++;
				}
			}				
		}
		else {
			for (int i = bgn; i < aryLen; i++) {
				if (ary[i] == lkp) {
					posRef.Val_(i + 1);	// +1 = lkp.Len
					return make ? Bry_.Mid(ary, bgn, i) : Bry_.Empty;
				}
			}
			throw Err_.new_wo_type("lkp failed", "lkp", (char)lkp, "txt", String_.new_u8__by_len(ary, bgn, aryLen));
		}
	}
	public static int ReadCsvInt(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_find_.Find_fwd(ary, lkp, bgn, ary.length);
		if (pos == Bry_find_.Not_found) throw Err_.new_wo_type("lkp failed", "lkp", (char)lkp, "bgn", bgn);
		int rv = Bry_.To_int_or(ary, posRef.Val(), pos, -1);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
		return rv;
	}
	public static double ReadCsvDouble(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_find_.Find_fwd(ary, lkp, bgn, ary.length);
		if (pos == Bry_find_.Not_found) throw Err_.new_wo_type("lkp failed", "lkp", (char)lkp, "bgn", bgn);
		double rv = Bry_.To_double(ary, posRef.Val(), pos);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
		return rv;
	}
	public static void ReadCsvNext(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_find_.Find_fwd(ary, lkp, bgn, ary.length);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
	}
	public static byte Byte_NegSign = (byte)'-';
	public static byte[] Replace_create(byte[] src, byte find, byte replace) {
		byte[] rv = Bry_.Copy(src);
		Replace_reuse(rv, find, replace);
		return rv;
	}
	public static void Replace_reuse(byte[] src, byte find, byte replace) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			if (src[i] == find) src[i] = replace;
		}
	}
	public static byte[] Replace(byte[] src, byte find, byte replace) {return Replace(src, 0, src.length, find, replace);}
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
	public static byte[] Replace_safe(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl) {
		if (src == null || find == null || repl == null) return null;
		return Replace(bfr, src, find, repl, 0, src.length);
	}
	public static byte[] Replace(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl) {return Replace(bfr, src, find, repl, 0, src.length);}
	public static byte[] Replace(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl, int src_bgn, int src_end) {return Replace(bfr, src, find, repl, src_bgn, src_end, Int_.Max_value);}
	public static byte[] Replace(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl, int src_bgn, int src_end, int limit) {
		int pos = src_bgn;
		boolean dirty = false;
		int find_len = find.length;
		int bfr_bgn = pos;
		int replace_count = 0;
		while (pos < src_end) {
			int find_pos = Bry_find_.Find_fwd(src, find, pos);
			if (find_pos == Bry_find_.Not_found) break;
			dirty = true;
			bfr.Add_mid(src, bfr_bgn, find_pos);
			bfr.Add(repl);
			pos = find_pos + find_len;
			bfr_bgn = pos;
			++replace_count;
			if (replace_count == limit) break;
		}
		if (dirty)
			bfr.Add_mid(src, bfr_bgn, src_end);
		return dirty ? bfr.To_bry_and_clear() : src;
	}
	public static byte[] Replace(byte[] src, byte[] find, byte[] replace) {return Replace_between(src, find, null, replace);}
	public static byte[] Replace_between(byte[] src, byte[] bgn, byte[] end, byte[] replace) {
		Bry_bfr bfr = Bry_bfr_.New();
		boolean replace_all = end == null;
		int src_len = src.length, bgn_len = bgn.length, end_len = replace_all ? 0 : end.length;
		int pos = 0;
		while (true) {
			if (pos >= src_len) break;
			int bgn_pos = Bry_find_.Find_fwd(src, bgn, pos);
			if (bgn_pos == Bry_find_.Not_found) {
				bfr.Add_mid(src, pos, src_len);
				break;
			}
			else {
				int bgn_rhs = bgn_pos + bgn_len;
				int end_pos = replace_all ? bgn_rhs : Bry_find_.Find_fwd(src, end, bgn_rhs);
				if (end_pos == Bry_find_.Not_found) {
					bfr.Add_mid(src, pos, src_len);
					break;
				}
				else {
					bfr.Add_mid(src, pos, bgn_pos);
					bfr.Add(replace);
					pos = end_pos + end_len;
				}
			}
		}
		return bfr.To_bry_and_clear();
	}
	public static int Trim_end_pos(byte[] src, int end) {
		for (int i = end - 1; i > -1; i--) {
			switch (src[i]) {
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					break;
				default:
					return i + 1;
			}
		}
		return 0;
	}
	public static byte[] Increment_last(byte[] ary) {return Increment_last(ary, ary.length - 1);}
	public static byte[] Increment_last(byte[] ary, int end_idx) {
		for (int i = end_idx; i > -1; i--) {
			byte end_val_old = ary[i];
			byte end_val_new = (byte)(end_val_old + 1);	
			ary[i] = end_val_new;
			if (end_val_new > (end_val_old & 0xff)) break;	// PATCH.JAVA:need to convert to unsigned byte
		}
		return ary;
	}
	public static byte[] Ucase__all(byte[] src)						{return Xcase__all(Bool_.Y, src, 0, -1);}
	public static byte[] Lcase__all(byte[] src)						{return Xcase__all(Bool_.N, src, 0, -1);}
	public static byte[] Lcase__all(byte[] src, int bgn, int end)	{return Xcase__all(Bool_.N, src, bgn, end);}
	private static byte[] Xcase__all(boolean upper, byte[] src, int bgn, int end) {
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
	public static byte[] Xcase__build__all(Bry_bfr tmp, boolean upper, byte[] src) {
		if (src == null) return null;
		int src_bgn = 0;
		int src_end = src.length;
		int lbound = 96, ubound = 123;
		if (!upper) {
			lbound = 64; ubound =  91;
		}

		boolean dirty = false;
		for (int i = src_bgn; i < src_end; i++) {
			byte b = src[i];
			if (b > lbound && b < ubound) {
				if (!dirty) {
					dirty = true;
					tmp.Add_mid(src, src_bgn, i);
				}
				if (upper)
					b -= 32;
				else
					b += 32;
			}
			if (dirty)
				tmp.Add_byte(b);
		}
		return dirty ? tmp.To_bry_and_clear() : src;
	}
	public static byte[] Ucase__1st(byte[] src)						{return Xcase__1st(Bool_.Y, src);}
	public static byte[] Lcase__1st(byte[] src)						{return Xcase__1st(Bool_.N, src);}
	private static byte[] Xcase__1st(boolean upper, byte[] src) {
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
	public static byte[] Null_if_empty(byte[] v) {return Len_eq_0(v) ? null : v;}
	
	public static byte[] Escape_ws(byte[] bry) {Bry_bfr bfr = Bry_bfr_.Get(); byte[] rv = Escape_ws(bfr, bry); bfr.Mkr_rls(); return rv;}
	public static byte[] Escape_ws(Bry_bfr bfr, byte[] src) {
		boolean dirty = false;
		int len = src.length;
		for (int i = 0; i < len; ++i) {
			byte b = src[i];
			byte escape = Byte_.Zero;
			switch (b) {
				case Byte_ascii.Tab:		escape = Byte_ascii.Ltr_t; break;
				case Byte_ascii.Nl:			escape = Byte_ascii.Ltr_n; break;
				case Byte_ascii.Cr:			escape = Byte_ascii.Ltr_r; break;
				default:					if (dirty) bfr.Add_byte(b); break;
			}
			if (escape != Byte_.Zero) {
				if (!dirty) {
					dirty = true;
					bfr.Add_mid(src, 0, i);
				}
				bfr.Add_byte_backslash().Add_byte(escape);
			}
		}
		return dirty ? bfr.To_bry_and_clear() : src;
	}
	public static byte[] Resolve_escape(Bry_bfr bfr, byte escape, byte[] raw, int bgn, int end) {
		int pos = bgn;
		boolean dirty = false;
		while (pos < end) {
			byte b = raw[pos];
			if (b == escape) {
				if (!dirty) {
					dirty = true;
					bfr.Add_mid(raw, bgn, pos);
				}
				++pos;
				if (pos < end) {	// check for eos; note that this ignores trailing "\"; EX: "a\" -> "a"
					bfr.Add_byte(raw[pos]);
					++pos;
				}
			}
			else {
				if (dirty) bfr.Add_byte(b);
				++pos;
			}
		}
		return dirty ? bfr.To_bry_and_clear() : raw;
	}
	public static void Clear(byte[] bry) {
		int len = bry.length;
		for (int i = 0; i < len; ++i)
			bry[i] = Byte_.Zero;
	}
	public static byte[] Replace_nl_w_tab(byte[] src, int bgn, int end) {
		return Bry_.Replace(Bry_.Mid(src, bgn, end), Byte_ascii.Nl, Byte_ascii.Tab);
	}
	public static byte[] Escape_html(byte[] src) {
		return Escape_html(null, Bool_.N, src, 0, src.length);
	}
	public static byte[] Escape_html(Bry_bfr bfr, boolean ws, byte[] src, int src_bgn, int src_end) {	// uses PHP rules for htmlspecialchars; REF.PHP:http://php.net/manual/en/function.htmlspecialchars.php
		boolean dirty = false;
		int cur = src_bgn;
		int prv = cur;
		boolean called_by_bry = bfr == null;

		// loop over chars
		while (true) {
			// if EOS, exit
			if (cur == src_end) {
				if (dirty) {
					bfr.Add_mid(src, prv, src_end);
				}
				break;
			}

			// check current byte if escaped
			byte b = src[cur];
			byte[] escaped = null;
			switch (b) {
				case Byte_ascii.Amp:        escaped = Gfh_entity_.Amp_bry; break;
				case Byte_ascii.Quote:      escaped = Gfh_entity_.Quote_bry; break;
				case Byte_ascii.Apos:       escaped = Gfh_entity_.Apos_num_bry; break;
				case Byte_ascii.Lt:         escaped = Gfh_entity_.Lt_bry; break;
				case Byte_ascii.Gt:         escaped = Gfh_entity_.Gt_bry; break;
				case Byte_ascii.Nl:         if (ws) escaped = Gfh_entity_.Nl_bry; break;
				case Byte_ascii.Cr:         if (ws) escaped = Gfh_entity_.Cr_bry; break;
				case Byte_ascii.Tab:        if (ws) escaped = Gfh_entity_.Tab_bry; break;
			}

			// not escaped; increment and continue
			if (escaped == null) {
				cur++;
				continue;
			}
			// escaped
			else {
				dirty = true;
				if (bfr == null) bfr = Bry_bfr_.New();

				if (prv < cur)
					bfr.Add_mid(src, prv, cur);
				bfr.Add(escaped);
				cur++;
				prv = cur;
			}
		}

		if (dirty) {
			if (called_by_bry)
				return bfr.To_bry_and_clear();
			else
				return null;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
			else {						
				bfr.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}
}
