/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
import java.lang.*;
import gplx.core.primitives.*; import gplx.ios.*;
public class Bry_ {
	public static final String Cls_val_name = "byte[]";
	public static final int NotFound = -1;
	public static final byte[] Empty = new byte[0];
	public static final byte[][] Ary_empty = new byte[0][];
	public static final Class<?> Cls_ref_type = byte[].class;
	public static byte[] bytes_(byte... ary) {return ary;}
	public static byte[] ints_ (int... ary) {
		int len = ary.length;
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = (byte)ary[i];
		return rv;
	}
	public static byte[] new_a7(String str) {
		try {
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
		catch (Exception e) {throw Err_.err_(e, "invalid ASCII sequence; str={0}", str);}
	}
	public static byte[] new_u8_safe(String str) {return str == null ? null : new_u8(str);}
	public static byte[] new_u8(String str) {
		try {
			int str_len = str.length();							
			int bry_len = new_u8_by_len(str, str_len);
			byte[] rv = new byte[bry_len];
			new_u8_write(str, str_len, rv, 0);
			return rv;
		}
		catch (Exception e) {throw Err_.err_(e, "invalid UTF-8 sequence; s={0}", str);}
	}
	public static int new_u8_by_len(String s, int s_len) {
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
	public static void new_u8_write(String str, int str_len, byte[] bry, int bry_pos) {
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
				if (i >= str_len) throw Err_.new_fmt_("incomplete surrogate pair at end of String; char={0}", c);
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
	public static byte[] Coalesce(byte[] orig, byte[] val_if_not_blank) {return Bry_.Len_eq_0(orig) ? val_if_not_blank : orig;}
	public static byte Get_at_end_or_fail(byte[] bry) {
		if (bry == null) throw Err_.new_("bry is null");
		int bry_len = bry.length;
		if (bry_len == 0) throw Err_.new_("bry has 0 len");
		return bry[bry_len - 1];
	}
	public static int While_fwd(byte[] src, byte while_byte, int bgn, int end) {
		for (int i = bgn; i < end; i++)
			if (src[i] != while_byte) return i;
		return end;
	}
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
	public static byte[] Repeat_space(int len) {return Repeat(Byte_ascii.Space, len);}
	public static byte[] Repeat(byte b, int len) {
		byte[] rv = new byte[len];
		for (int i = 0; i < len; i++)
			rv[i] = b;
		return rv;
	}
	public static byte[] Copy(byte[] src) {
		int src_len = src.length;
		byte[] trg = new byte[src_len];
		for (int i = 0; i < src_len; i++)
			trg[i] = src[i];
		return trg;
	}
	public static void Copy_by_pos(byte[] src, int src_bgn, int src_end, byte[] trg, int trg_bgn) {
		int trg_adj = trg_bgn - src_bgn;
		for (int i = src_bgn; i < src_end; i++)
			trg[i + trg_adj] = src[i];
	}
	public static void Copy_by_len(byte[] src, int src_bgn, int src_len, byte[] trg, int trg_bgn) {
		for (int i = 0; i < src_len; i++)
			trg[i + trg_bgn] = src[i + src_bgn];
	}
	public static byte[][] XtoByteAryAry(String... strAry) {
		int strAryLen = strAry.length;
		byte[][] rv = new byte[strAryLen][];
		for (int i = 0; i < strAryLen; i++)
			rv[i] = Bry_.new_u8(strAry[i]);
		return rv;
	}
	public static byte[] Xto_str_lower(byte[] src, int bgn, int end) {
		int len = end - bgn;
		byte[] rv = new byte[len];
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
  					case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
  					case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
  					case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
  					case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
  					case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
					b += 32;
					break;
			}
			rv[i - bgn] = b;
		}
		return rv;
	}
	public static byte[] Replace_one(byte[] src, byte[] find, byte[] repl) {
		int src_len = src.length;
		int findPos = Bry_finder.Find(src, find, 0, src_len, true); if (findPos == Bry_.NotFound) return src;
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
	public static byte[] Add(byte[] ary, byte b) {
		int ary_len = ary.length;
		byte[] rv = new byte[ary_len + 1];
		for (int i = 0; i < ary_len; i++)
			rv[i] = ary[i];
		rv[ary_len] = b;
		return rv;
	}
	public static byte[] Add(byte b, byte[] ary) {
		int ary_len = ary.length + 1;
		byte[] rv = new byte[ary_len];
		for (int i = 1; i < ary_len; i++)
			rv[i] = ary[i - 1];
		rv[0] = b;
		return rv;
	}
	public static byte[] Add(byte[]... all) {
		int all_len = all.length, rv_len = 0;
		for (int i = 0; i < all_len; i++) {
			byte[] cur = all[i]; if (all[i] == null) continue;
			rv_len += cur.length;
		}
		byte[] rv = new byte[rv_len];
		int rv_idx = 0;
		for (int i = 0; i < all_len; i++) {
			byte[] cur = all[i]; if (all[i] == null) continue;
			int cur_len = cur.length;
			for (int j = 0; j < cur_len; j++)
				rv[rv_idx++] = cur[j];
		}
		return rv;
	}
	public static int LastIdx(byte[] src) {return src.length - 1;}
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
		if (len + bgn > src_len) len = (src_len - bgn);
		return Mid(src, bgn, bgn + len);
	}
	public static String MidByLenToStr(byte[] src, int bgn, int len) {
		int end = bgn + len; end = Int_.BoundEnd(end, src.length);
		byte[] ary = Bry_.Mid(src, bgn, end);
		return String_.new_u8(ary);
	}
	public static byte[] Mid_safe(byte[] src, int bgn, int end) {
		try {return Mid(src, bgn, end);}
		catch (Exception e) {Err_.Noop(e); return Bry_.Add_w_dlm(Byte_ascii.Space, Bry_.XbyInt(bgn), Bry_.XbyInt(end));}
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
		return Mid(src, bgn, src.length);
	}
	public static byte[] Mid(byte[] src, int bgn, int end) {
		try {
			int len = end - bgn; if (len == 0) return Bry_.Empty;
			byte[] rv = new byte[len];
			for (int i = bgn; i < end; i++)
				rv[i - bgn] = src[i];
			return rv;
		}	catch (Exception e) {
			Err err = Err_.new_("").Add("bgn", bgn).Add("end", end);
			if (src != null) err.Add("src", String_.new_u8_by_len(src, bgn, 32));
			if		(src == null)					err.Hdr_("src is null");
			else if (bgn < 0 || bgn > src.length)	err.Hdr_("invalid bgn");
			else if (end < 0 || end > src.length)	err.Hdr_("invalid end");
			else if (end < bgn)						err.Hdr_("end < bgn");
			else									err.Hdr_(Err_.Message_lang(e));
			throw err;
		}
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
	public static final byte[] Trim_ary_ws = mask_(256, Byte_ascii.Tab, Byte_ascii.NewLine, Byte_ascii.CarriageReturn, Byte_ascii.Space);
	public static byte[] Trim(byte[] src) {return Trim(src, 0, src.length, true, true, Trim_ary_ws);}
	public static byte[] Trim(byte[] src, int bgn, int end) {return Trim(src, bgn, end, true, true, Trim_ary_ws);}
	public static byte[] Trim(byte[] src, int bgn, int end, boolean trim_bgn, boolean trim_end, byte[] trim_ary) {
		int txt_bgn = bgn, txt_end = end;
		boolean all_ws = true;
		if (trim_bgn) {
			for (int i = bgn; i < end; i++) {
				byte b = src[i];
				if (trim_ary[b & 0xFF] == Byte_ascii.Nil) {
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
				if (trim_ary[b & 0xFF] == Byte_ascii.Nil) {
					txt_end = i + 1;
					i = -1;
					all_ws = false;
				}
			}
			if (all_ws) return Bry_.Empty;
		}
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
	public static boolean Has(byte[] src, byte[] lkp) {return Bry_finder.Find_fwd(src, lkp) != Bry_finder.Not_found;}
	public static boolean Has(byte[] src, byte lkp) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++)
			if (src[i] == lkp) return true;
		return false;
	}
	public static boolean HasAtEnd(byte[] src, byte[] lkp) {int src_len = src.length; return HasAtEnd(src, lkp, src_len - lkp.length, src_len);}
	public static boolean HasAtEnd(byte[] src, byte[] lkp, int src_bgn, int src_end) {
		int lkp_len = lkp.length;
		if (src_bgn < 0) return false;
		int pos = src_end - lkp_len; if (pos < src_bgn) return false; // lkp is longer than src
		for (int i = 0; i < lkp_len; i++) {
			if (lkp[i] != src[i + pos]) return false;
		}
		return true;
	}
	public static boolean HasAtBgn(byte[] src, byte lkp, int src_bgn) {
		return src_bgn < src.length ? src[src_bgn] == lkp : false;
	}
	public static boolean HasAtBgn(byte[] src, byte[] lkp) {return HasAtBgn(src, lkp, 0, src.length);}
	public static boolean HasAtBgn(byte[] src, byte[] lkp, int src_bgn, int src_end) {
		int lkp_len = lkp.length;
		if (lkp_len + src_bgn > src_end) return false; // lkp is longer than src
		for (int i = 0; i < lkp_len; i++) {
			if (lkp[i] != src[i + src_bgn]) return false;
		}
		return true;
	}
	public static int Skip_fwd(byte[] src, int src_bgn, byte skip) {
		int src_len = src.length;
		for (int i = src_bgn; i < src_len; i++) {
			byte b = src[i];
			if (b != skip) return i;
		}
		return 0;
	}
	public static int Skip_bwd(byte[] src, int src_bgn, byte skip) {
		for (int i = src_bgn; i > -1; i--) {
			byte b = src[i];
			if (b != skip) return i;
		}
		return src.length;
	}
	public static int Skip_fwd_nl(byte[] src, int src_bgn) {
		int src_len = src.length;
		for (int i = src_bgn; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn:
					break;
				default:
					return i;
			}
		}
		return 0;
	}
	public static int Skip_bwd_nl(byte[] src, int src_bgn) {
		for (int i = src_bgn; i > -1; i--) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn:
					break;
				default:
					return i;
			}
		}
		return src.length;
	}
	public static byte[] Resize_manual(byte[] src, int rvLen) {
		byte[] rv = new byte[rvLen];
		int src_len = src.length;
		if (src_len > rvLen) src_len = rvLen; // resizing smaller; only copy as many elements as in rvLen
		for (int i = 0; i < src_len; i++)
			rv[i] = src[i];
		return rv;
	}
	public static byte[] Resize(byte[] src, int trgLen) {return Resize(src, 0, trgLen);}
	public static byte[] Resize(byte[] src, int src_bgn, int trgLen) {
		byte[] trg = new byte[trgLen];
		int src_len = src.length > trgLen ? trgLen : src.length;	// trgLen can either expand or shrink
		Copy_by_len(src, src_bgn, src_len, trg, 0);
		return trg;
	}
	public static boolean Match(byte[] src, byte[] find) {return Match(src, 0, src.length, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, byte[] find) {return Match(src, src_bgn, src.length, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, int src_end, byte[] find) {return Match(src, src_bgn, src_end, find, 0, find.length);}
	public static boolean Match(byte[] src, int src_bgn, int src_end, byte[] find, int find_bgn, int find_end) {
		int src_len = src.length;
		if (src_end > src_len) src_end = src_len;			// must limit src_end to src_len, else ArrayIndexOutOfBounds below; DATE:2015-01-31
		int find_len = find_end - find_bgn;
		if (find_len != src_end - src_bgn) return false;
		if (find_len == 0) return src_end - src_bgn == 0; // "" only matches ""
		for (int i = 0; i < find_len; i++) {
			int pos = src_bgn + i;
			if (pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			if (src[pos] != find[i + find_bgn]) return false;
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
	public static int Len(byte[] v) {return v == null ? 0 : v.length;}
	public static boolean Len_gt_0(byte[] v) {return v != null && v.length > 0;}
	public static boolean Len_eq_0(byte[] v) {return v == null || v.length == 0;}
	public static void Set(byte[] src, int bgn, int end, byte[] repl) {
		int repl_len = repl.length;
		for (int i = 0; i < repl_len; i++)
			src[i + bgn] = repl[i];
	}
	public static boolean Eq_itm(byte[] src, int src_len, int pos, byte chk) {
		return	pos < src_len
			&&	src[pos] == chk;
	}
	public static boolean Eq(byte[] lhs, byte[] rhs) {
		if		(lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		int lhs_len = lhs.length;
		if (lhs_len != rhs.length) return false;
		for (int i = 0; i < lhs_len; i++)	// NOTE: lhs_len == rhsLen
			if (lhs[i] != rhs[i]) return false;
		return true;
	}
	public static boolean Eq(byte[] lhs, byte[] rhs, int rhs_bgn, int rhs_end) {
		if		(lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		int lhs_len = lhs.length;
		int rhs_len = rhs_end - rhs_bgn;
		if (lhs_len != rhs_len) return false;
		for (int i = 0; i < lhs_len; i++)
			if (lhs[i] != rhs[i + rhs_bgn]) return false;
		return true;
	}
	public static boolean Eq_ci_ascii(byte[] lhs, byte[] rhs, int rhs_bgn, int rhs_end) {
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
	public static byte[] XtoStrBytesByInt(int val, int padLen) {return XtoStrBytesByInt(val, null, 0, padLen);}
	public static byte[] XtoStrBytesByInt(int val, byte[] ary, int aryPos, int padLen) {
		int neg = 0;
		if	(val < 0) {
			val *= -1;
			neg = 1;
		}
		int digits = val == 0 ? 0 : Math_.Log10(val);
		digits += 1;						// digits = log + 1; EX: Log(1-9) = 0, Log(10-99) = 1
		int aryLen = digits + neg, aryBgn = aryPos, pad = 0;
		if (aryLen < padLen) {				// padding specified
			pad = padLen - aryLen;
			aryLen = padLen;
		}
		if (ary == null) ary = new byte[aryLen];
		long factor = 1;					// factor needs to be long to handle 1 billion (for which factor would be 10 billion)
		for (int i = 0; i < digits; i++)	// calc maxFactor
			factor *= 10;
		if (neg == 1) ary[0] = Byte_NegSign;

		for (int i = 0; i < pad; i++)		// fill ary with pad
			ary[i + aryBgn] = XtoStrByte(0);
		aryBgn += pad;						// advance aryBgn by pad
		for (int i = neg; i < aryLen - pad; i++) {
			int denominator = (int)(factor / 10); // cache denominator to check for divide by 0
			int digit = denominator == 0 ? 0 : (int)((val % factor) / denominator);
			ary[aryBgn + i] = XtoStrByte(digit);
			factor /= 10;
		}
		return ary;
	}
	public static byte Xto_byte_by_int(byte[] ary, int bgn, int end, byte or)	{return (byte)Xto_int_or(ary, bgn, end, or);}
	public static int Xto_int(byte[] ary)										{return Xto_int_or(ary, null, 0, ary.length, -1);}
	public static int Xto_int_or_fail(byte[] ary)								{
		int rv = Xto_int_or(ary, null, 0, ary.length, Int_.MinValue);
		if (rv == Int_.MinValue) throw Err_.new_fmt_("could not parse to int; val={0}", String_.new_u8(ary));
		return rv;
	}
	public static boolean Xto_bool_by_int_or_fail(byte[] ary) {
		int rv = Xto_int_or(ary, null, 0, ary.length, Int_.MinValue);
		switch (rv) {
			case 0: return false;
			case 1: return true;
			default: throw Err_.new_fmt_("could not parse to boolean int; val={0}", String_.new_u8(ary));
		}
	}
	public static int Xto_int_or(byte[] ary, int or)							{return Xto_int_or(ary, null, 0, ary.length, or);}
	public static int Xto_int_or(byte[] ary, int bgn, int end, int or)			{return Xto_int_or(ary, null, bgn, end, or);}
	public static int Xto_int_or(byte[] ary, byte[] ignore_ary, int or)			{return Xto_int_or(ary, ignore_ary, 0, ary.length, or);}
	public static int Xto_int_or(byte[] ary, byte[] ignore_ary, int bgn, int end, int or) {
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
	public static int Xto_int_or_trim(byte[] ary, int bgn, int end, int or) {	// NOTE: same as Xto_int_or, except trims ws at bgn / end; DATE:2014-02-09
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
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn:
					if (numbers_seen)
						ws_seen = true;
					break;						
				default: return or;
			}
		}
		return rv;
	}
	public static int Xto_int_or_lax(byte[] ary, int bgn, int end, int or) {
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
		return Xto_int_or(ary, bgn, end_num, or);
	}
	public static float XtoFloatByPos(byte[] ary, int bgn, int end) {return Float_.parse_(String_.new_u8(ary, bgn, end));}
	public static double Xto_double(byte[] bry) {return Double_.parse_(String_.new_u8(bry, 0, bry.length));}
	public static double Xto_double_or(byte[] bry, double or) {return Double_.parse_or(String_.new_u8(bry, 0, bry.length), or);}
	public static double XtoDoubleByPosOr(byte[] ary, int bgn, int end, double or) {return Double_.parse_or(String_.new_u8(ary, bgn, end), or);}
	public static double XtoDoubleByPos(byte[] ary, int bgn, int end) {return Double_.parse_(String_.new_u8(ary, bgn, end));}
	public static DecimalAdp XtoDecimalByPos(byte[] ary, int bgn, int end) {return DecimalAdp_.parse_(String_.new_u8(ary, bgn, end));}
	public static final byte Dlm_fld = (byte)'|', Dlm_row = (byte)'\n', Dlm_quote = (byte)'"', Dlm_null = 0, Ascii_zero = 48;
	public static final String Fmt_csvDte = "yyyyMMdd HHmmss.fff";
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
		if (ary[bgn + 19] != lkp) throw Err_.new_("csv date is invalid").Add("txt", String_.new_u8_by_len(ary, bgn, 20));
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
			if (make) bb = Bry_bfr.new_(16);
			while (true) {
				if (pos == aryLen) throw Err_.new_("endOfAry reached, but no quote found").Add("txt", String_.new_u8_by_len(ary, bgn, pos));
				byte b = ary[pos];
				if (b == Dlm_quote) {                            
					if (pos == aryLen - 1) throw Err_.new_("endOfAry reached, quote found but lkp not").Add("txt", String_.new_u8_by_len(ary, bgn, pos));
					byte next = ary[pos + 1];
					if		(next == Dlm_quote) {	// byte followed by quote
						if (make) bb.Add_byte(b);
						pos += 2;
					}
					else if (next == lkp) {
						posRef.Val_(pos + 2);	// 1=endQuote;1=lkp;
						return make ? bb.Xto_bry() : Bry_.Empty;
					}
					else throw Err_.new_("quote found, but not doubled").Add("txt", String_.new_u8_by_len(ary, bgn, pos + 1));
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
			throw Err_.new_("lkp failed").Add("lkp", (char)lkp).Add("txt", String_.new_u8_by_len(ary, bgn, aryLen));
		}
	}
	public static int ReadCsvInt(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_finder.Find_fwd(ary, lkp, bgn, ary.length);
		if (pos == Bry_.NotFound) throw Err_.new_("lkp failed").Add("lkp", (char)lkp).Add("bgn", bgn);
		int rv = Bry_.Xto_int_or(ary, posRef.Val(), pos, -1);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
		return rv;
	}
	public static double ReadCsvDouble(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_finder.Find_fwd(ary, lkp, bgn, ary.length);
		if (pos == Bry_.NotFound) throw Err_.new_("lkp failed").Add("lkp", (char)lkp).Add("bgn", bgn);
		double rv = Bry_.XtoDoubleByPos(ary, posRef.Val(), pos);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
		return rv;
	}
	public static void ReadCsvNext(byte[] ary, Int_obj_ref posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = Bry_finder.Find_fwd(ary, lkp, bgn, ary.length);
		posRef.Val_(pos + 1);	// +1 = lkp.Len
	}
	public static byte Byte_NegSign = (byte)'-';
	public static int XtoIntBy4Bytes(byte[] v) {
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
	public static byte[] XbyInt(int v) {
		byte b0 = (byte)(v >> 24);
		byte b1 = (byte)(v >> 16);
		byte b2 = (byte)(v >>  8);
		byte b3 = (byte)(v);
		if		(b0 != 0)	return new byte[] {b0, b1, b2, b3};
		else if	(b1 != 0)	return new byte[] {b1, b2, b3};
		else if	(b2 != 0)	return new byte[] {b2, b3};
		else				return new byte[] {b3};
	}
	public static byte XtoStrByte(int digit) {
		switch (digit) {
			case 0: return Byte_ascii.Num_0; case 1: return Byte_ascii.Num_1; case 2: return Byte_ascii.Num_2; case 3: return Byte_ascii.Num_3; case 4: return Byte_ascii.Num_4;
			case 5: return Byte_ascii.Num_5; case 6: return Byte_ascii.Num_6; case 7: return Byte_ascii.Num_7; case 8: return Byte_ascii.Num_8; case 9: return Byte_ascii.Num_9;
			default: throw Err_.new_("unknown digit").Add("digit", digit);
		}
	}
	public static byte[][] Split(byte[] src, byte dlm) {return Split(src, dlm, false);}
	public static byte[][] Split(byte[] src, byte dlm, boolean trim) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int src_len = src.length, src_pos = 0, fld_bgn = 0;
		List_adp rv = List_adp_.new_();
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? dlm : src[src_pos];
			if (b == dlm) {
				if (last && (src_pos - fld_bgn == 0)) {}
				else {
					byte[] itm = Bry_.Mid(src, fld_bgn, src_pos);
					if (trim) itm = Bry_.Trim(itm);						
					rv.Add(itm);
				}
				fld_bgn = src_pos + 1;
			}
			if (last) break;
			++src_pos;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
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
	public static byte[] Replace(byte[] src, byte find, byte replace) {
		int src_len = src.length;
		byte[] rv = new byte[src_len];
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			rv[i] = b == find ? replace : b;
		}
		return rv;
	}
	public static byte[] Replace_safe(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl) {
		if (src == null || find == null || repl == null) return null;
		return Replace(bfr, src, find, repl, 0, src.length);
	}
	public static byte[] Replace(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl) {return Replace(bfr, src, find, repl, 0, src.length);}
	public static byte[] Replace(Bry_bfr bfr, byte[] src, byte[] find, byte[] repl, int src_bgn, int src_end) {
		int pos = src_bgn;
		boolean dirty = false;
		int find_len = find.length;
		int bfr_bgn = pos;
		while (pos < src_end) {
			int find_pos = Bry_finder.Find_fwd(src, find, pos);
			if (find_pos == Bry_finder.Not_found) break;
			dirty = true;
			bfr.Add_mid(src, bfr_bgn, find_pos);
			bfr.Add(repl);
			pos = find_pos + find_len;
			bfr_bgn = pos;
		}
		if (dirty)
			bfr.Add_mid(src, bfr_bgn, src_end);
		return dirty ? bfr.Xto_bry_and_clear() : src;
	}
	public static byte[] Replace(byte[] src, byte[] find, byte[] replace) {return Replace_between(src, find, null, replace);}
	public static byte[] Replace_between(byte[] src, byte[] bgn, byte[] end, byte[] replace) {
		Bry_bfr bfr = Bry_bfr.new_();
		boolean replace_all = end == null;
		int src_len = src.length, bgn_len = bgn.length, end_len = replace_all ? 0 : end.length;
		int pos = 0;
		while (true) {
			if (pos >= src_len) break;
			int bgn_pos = Bry_finder.Find_fwd(src, bgn, pos);
			if (bgn_pos == Bry_.NotFound) {
				bfr.Add_mid(src, pos, src_len);
				break;
			}
			else {
				int bgn_rhs = bgn_pos + bgn_len;
				int end_pos = replace_all ? bgn_rhs : Bry_finder.Find_fwd(src, end, bgn_rhs);
				if (end_pos == Bry_.NotFound) {
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
		return bfr.Xto_bry_and_clear();
	}
	public static int Trim_end_pos(byte[] src, int end) {
		for (int i = end - 1; i > -1; i--) {
			switch (src[i]) {
				case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.CarriageReturn: case Byte_ascii.Space:
					break;
				default:
					return i + 1;
			}
		}
		return 0;
	}
	public static byte[][] Split(byte[] src, byte[] dlm) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int cur_pos = 0, src_len = src.length, dlm_len = dlm.length;
		List_adp rv = List_adp_.new_();
		while (true) {
			int find_pos = Bry_finder.Find_fwd(src, dlm, cur_pos);
			if (find_pos == Bry_.NotFound) {
				if (cur_pos == src_len) break;	// dlm is last sequence in src; do not create empty itm
				find_pos = src_len;			
			}
			rv.Add(Bry_.Mid(src, cur_pos, find_pos));
			if (find_pos == src_len) break;
			cur_pos = find_pos + dlm_len;
		}
		return (byte[][])rv.To_ary(byte[].class);
	}
	public static byte[][] Split_lines(byte[] src) {
		if (Bry_.Len_eq_0(src)) return Bry_.Ary_empty;
		int src_len = src.length, src_pos = 0, fld_bgn = 0;
		List_adp rv = List_adp_.new_();
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? Byte_ascii.NewLine : src[src_pos];
			int nxt_bgn = src_pos + 1; 
			switch (b) {
				case Byte_ascii.CarriageReturn:
				case Byte_ascii.NewLine:
					if (	b == Byte_ascii.CarriageReturn		// check for crlf
						&& nxt_bgn < src_len && src[nxt_bgn] == Byte_ascii.NewLine) {
							++nxt_bgn;
					}
					if (last && (src_pos - fld_bgn == 0)) {}	// ignore trailing itms
					else
						rv.Add(Bry_.Mid(src, fld_bgn, src_pos));
					fld_bgn = nxt_bgn;
					break;
			}
			if (last) break;
			src_pos = nxt_bgn;
		}
		return (byte[][])rv.To_ary(byte[].class);
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
	public static byte[] Upper_1st(byte[] ary) {
		if (ary == null) return null;
		int len = ary.length;
		if (len == 0) return ary;
		byte b = ary[0];
		if (b > 96 && b < 123)
			ary[0] = (byte)(b - 32);
		return ary;
	}
	public static byte[] Upper_ascii(byte[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte b = ary[i];
			if (b > 96 && b < 123)
				ary[i] = (byte)(b - 32);
		}
		return ary;
	}
	public static byte[] Lower_1st(byte[] ary) {
		if (ary == null) return null;
		int len = ary.length;
		if (len == 0) return ary;
		byte b = ary[0];
		if (b > 64 && b < 91)
			ary[0] = (byte)(b + 32);
		return ary;
	}
	public static byte[] Lower_ascii(byte[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte b = ary[i];
			if (b > 64 && b < 91)
				ary[i] = (byte)(b + 32);
		}
		return ary;
	}
	public static byte[] Null_if_empty(byte[] v) {return Len_eq_0(v) ? null : v;}
	public static byte Get_at_end(byte[] v) {
		int v_len = v.length;
		return v_len == 0 ? Byte_ascii.Nil : v[v_len - 1];
	}		
}
