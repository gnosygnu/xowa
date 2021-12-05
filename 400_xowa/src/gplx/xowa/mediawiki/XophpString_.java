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
package gplx.xowa.mediawiki;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Bry_find_;
import gplx.Byte_;
import gplx.Char_;
import gplx.Err_;
import gplx.Hash_adp;
import gplx.Hash_adp_;
import gplx.Int_;
import gplx.Object_;
import gplx.String_;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.intls.Utf16_;
import gplx.core.primitives.Byte_obj_ref;
import gplx.core.primitives.Int_obj_ref;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import gplx.objects.strings.bfrs.GfoStrBldr;
import gplx.objects.strings.unicodes.Ustring;
import gplx.objects.strings.unicodes.UstringUtl;
public class XophpString_ implements XophpCallbackOwner {
	public static final String False = null;
	public static boolean is_true (String s) {return s != null;} // handles code like "if ($var)" where var is an Object;
	public static boolean is_false(String s) {return s == null;}
	public static boolean is_null(String s) {return s == null;}
	public static boolean eq(String lhs, String rhs)     {return  String_.Eq(lhs, rhs);}
	public static boolean eq_not(String lhs, String rhs) {return !String_.Eq(lhs, rhs);}

	// REF.PHP: https://www.php.net/manual/en/function.strpos.php
	public static int strpos(String haystack, String needle) {return strpos(haystack, needle, 0);}
	public static int strpos(String haystack, String needle, int offset) {
		if (offset < 0) {
			offset = String_.Len(haystack) + offset;
		}
		return String_.FindFwd(haystack, needle, offset);
	}
	public static int strpos(byte[] src, byte find) {return strpos(src, find, 0, src.length);}
	public static int strpos(byte[] src, byte find, int bgn, int end) {
		return Bry_find_.Find_fwd(src, find, bgn, end);
	}
	public static int strpos_NULL = -1;

	// REF.PHP: https://www.php.net/manual/en/function.substr.php
	public static String substr(String src, int bgn, int len) {return String_.new_u8(substr(Bry_.new_u8(src), bgn, len));}
	public static String substr(String src, int bgn) {return String_.new_u8(substr(Bry_.new_u8(src), bgn, String_.Len(src)));}
	public static byte[] substr(byte[] src, int bgn) {return substr(src, bgn, src.length);}
	public static byte[] substr(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (bgn < 0) bgn = src_len + bgn; // handle negative
		if (bgn < 0) bgn = 0;	// handle out of bounds; EX: ("a", -1, -1)
		int end = len < 0 ? src_len + len : bgn + len;
		if (end > src.length) end = src.length;; // handle out of bounds;
		return Bry_.Mid(src, bgn, end);
	}
	public static byte substr_byte(byte[] src, int bgn) {return substr_byte(src, bgn, src.length);}
	public static byte substr_byte(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (src_len == 0) return AsciiByte.Null;
		if (bgn < 0) bgn = src_len + bgn; // handle negative
		if (bgn < 0) bgn = 0;	// handle out of bounds; EX: ("a", -1, -1)
		int end = len < 0 ? src_len + len : bgn + len;
		if (end > src.length) end = src.length;; // handle out of bounds;
		return src[bgn];
	}
	// REF.PHP: https://www.php.net/manual/en/function.strspn.php
	public static Hash_adp strspn_hash(String mask) {
		Hash_adp rv = Hash_adp_.New();
		int mask_len = String_.Len(mask);
		int i = 0;
		while (i < mask_len) {
			char hi_char = String_.CharAt(mask, i);
			String key = "";
			if (Utf16_.Len_by_char(hi_char) == 2) {
				i++;
				char lo_char = String_.CharAt(mask, i);
				int surrogate_char = Utf16_.Surrogate_merge(Char_.To_int(hi_char), Char_.To_int(lo_char));
				key = String_.new_u8(Utf16_.Encode_int_to_bry(surrogate_char));
			}
			else {
				key = Char_.To_str(hi_char);
			}
			rv.AddIfDupeUse1st(key, key);
			i++;
		}
		return rv;
	}
	public static int strspn(String subject, Hash_adp mask, int start) {return strspn(subject, mask, start, Int_.Zero);}
	public static int strspn(String subject, Hash_adp mask, int start, int length) {
		int subject_len = String_.Len(subject);
		start = strspn__start(start, subject_len);
		int subject_end = strspn__subject_end(start, length, subject_len);
		return strspn__rslt(BoolUtl.Y, subject, mask, start, subject_end);
	}
	// REF.PHP:https://www.php.net/manual/en/function.strcspn.php
	public static int strcspn(String subject, Hash_adp mask)                        {return strcspn(subject, mask, Int_.Zero, Int_.Zero);}
	public static int strcspn(String subject, Hash_adp mask, int start)             {return strcspn(subject, mask,     start, Int_.Zero);}
	public static int strcspn(String subject, Hash_adp mask, int start, int length) {
		int subject_len = String_.Len(subject);
		start = strspn__start(start, subject_len);
		int subject_end = strspn__subject_end(start, length, subject_len);
		return strspn__rslt(BoolUtl.N, subject, mask, start, subject_end);
	}
	private static int strspn__start(int start, int subject_len) {
		if (start < 0) { // adjust start if -1
			start = subject_len + start;
			if (start < 0)
				start = 0;
		}
		return start;
	}
	private static int strspn__subject_end(int start, int length, int subject_len) {
		int subject_end = 0;
		if (length == Int_.Zero) {
			subject_end = subject_len;
		}
		else if (length < Int_.Zero) {
			subject_end = subject_len + length; // If length is given and is negative, then subject will be examined from the starting position up to length characters from the end of subject.
			if (subject_end < start)
				subject_end = start;
		}
		else {
			subject_end = start + length; // If length is given and is non-negative, then subject will be examined for length characters after the starting position.
			if (subject_end > subject_len)
				subject_end = subject_len;
		}
		return subject_end;
	}
	private static int strspn__rslt(boolean is_strspn, String subject, Hash_adp mask, int start, int subject_end) {
		// loop subject until encountering character not in mask
		int strspn_rv = 0;
		int i = start;
		while (i < subject_end) {
			char subject_char = String_.CharAt(subject, i);
			String mask_key = "";
			if (Utf16_.Len_by_char(subject_char) == 2) {
				i++;
				char lo_char = String_.CharAt(subject, i);
				int surrogate_char = Utf16_.Surrogate_merge(Char_.To_int(subject_char), Char_.To_int(lo_char));
				mask_key = String_.new_u8(Utf16_.Encode_int_to_bry(surrogate_char));
			}
			else {
				mask_key = Char_.To_str(subject_char);
			}

			if (mask.Has(mask_key)) {
				if (is_strspn) {
					strspn_rv++;
				}
				else {
					break;
				}
			}
			else {
				if (is_strspn) {
					break;
				}
			}
			i++;
		}
		return is_strspn ? strspn_rv : i - start;
	}
	public static int strspn_fwd__ary(byte[] src, boolean[] find, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			if (find[src[i] & 0xFF] && rv < max) // PATCH.JAVA:need to convert to unsigned byte
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int strspn_fwd__byte(byte[] src, byte find, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			if (find == src[i] && rv < max) 
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int strspn_fwd__space_or_tab(byte[] src, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			switch (src[i]) {
				case AsciiByte.Space:
				case AsciiByte.Tab:
					if (rv < max) {
						rv++;
						continue;
					}
					break;
			}
			break;
		}
		return rv;
	}
	public static int strspn_bwd__byte(byte[] src, byte find, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			if (find == src[i] && rv < max)
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int strspn_bwd__ary(byte[] src, boolean[] find, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			if (find[src[i & 0xFF]] && rv < max)  // PATCH.JAVA:need to convert to unsigned byte
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int strspn_bwd__space_or_tab(byte[] src, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			switch (src[i]) {
				case AsciiByte.Space:
				case AsciiByte.Tab:
					if (rv < max) {
						rv++;
						continue;
					}
					break;
			}
			break;
		}
		return rv;
	}
	public static byte[] strtr(byte[] src, Btrie_slim_mgr trie, Bry_bfr tmp, Btrie_rv trv) {
		boolean dirty = false;
		int src_bgn = 0;
		int src_end = src.length;
		int i = src_bgn;

		while (true) {
			if (i == src_end) break;
			byte b = src[i];
			Object o = trie.Match_at_w_b0(trv, b, src, i, src_end);
			if (o == null) {
				if (dirty) {
					tmp.Add_byte(b);
				}
				i++;
			}
			else {
				if (!dirty) {
					dirty = true;
					tmp.Add_mid(src, 0, i);
				}
				tmp.Add((byte[])o);
				i = trv.Pos();
			}
		}
		return dirty ? tmp.To_bry_and_clear() : src;
	}
	public static byte[] strtr(byte[] src, byte find, byte repl) {
		return Bry_.Replace(src, 0, src.length, find, repl);
	}
	public static String strtr(String src, String find, String repl) {
		return String_.Replace(src, find, repl);
	}
	public static byte[] str_replace(byte find, byte repl, byte[] src) {
		return Bry_.Replace(src, 0, src.length, find, repl);
	}
	public static byte[] str_replace(byte[] find, byte[] repl, byte[] src) {
		return Bry_.Replace(src, find, repl);
	}
	public static String str_replace(String find, String repl, String src) {
		return String_.Replace(src, find, repl);
	}
	public static byte[] strstr(byte[] src, byte[] find) {
		int pos = Bry_find_.Find_fwd(src, find);
		return pos == Bry_find_.Not_found ? null : Bry_.Mid(src, pos, src.length);
	}
	public static int strlen(String src) {return String_.Len(src);}
	public static int strlen(byte[] src) {return src.length;}

	// REF.PHP: https://www.php.net/manual/en/function.rtrim.php
	private static final Hash_adp trim_ws_hash = Hash_adp_.New().AddManyAsKeyAndVal
		( Int_obj_ref.New(AsciiByte.Space)
		, Int_obj_ref.New(AsciiByte.Tab)
		, Int_obj_ref.New(AsciiByte.Nl)
		, Int_obj_ref.New(AsciiByte.Cr)
		, Int_obj_ref.New(AsciiByte.Null)
		, Int_obj_ref.New(AsciiByte.VerticalTab)
		);
	public static String trim (String src)             {return trim_outer( 0, src, null);}
	public static String trim (String src, String pad) {return trim_outer( 0, src, pad);}
	public static String ltrim(String src)             {return trim_outer( 1, src, null);}
	public static String ltrim(String src, String pad) {return trim_outer( 1, src, pad);}
	public static String rtrim(String src)             {return trim_outer(-1, src, null);}
	public static String rtrim(String src, String pad) {return trim_outer(-1, src, pad);}
	private static String trim_outer(int type, String src_str, String pad_str) {
		// init brys / lens
		byte[] src_bry = Bry_.new_u8(src_str);
		int src_len = src_bry.length;
		byte[] pad_bry = Bry_.new_u8(pad_str);
		int pad_len = pad_bry.length;

		// create pad_hash if not ws_hash
		// NOTE: this does not support mutlibyte chars, and PHP does not support multibyte chars; see TEST
		Hash_adp pad_hash = null;
		if (pad_len > 1) {
			if (pad_str == null) {
				pad_hash = trim_ws_hash;
			}
			else {
				pad_hash = Hash_adp_.New();
				byte prv_byte = Byte_.Zero;
				for (int i = 0; i < pad_len; i++) {
					byte pad_byte = pad_bry[i];
					if (pad_byte == AsciiByte.Dot && i < pad_len - 1) {
						byte nxt_byte = pad_bry[i + 1];
						if (nxt_byte == AsciiByte.Dot) {
							if (i == 0) {
								throw new XophpException(".. found but at start of String; src=" + pad_str);
							}
							else if (i == pad_len - 2) {
								throw new XophpException(".. found but at end of String; src=" + pad_str);
							}
							else {
								nxt_byte = pad_bry[i + 2];
								if (nxt_byte > prv_byte) {
									for (byte j = prv_byte; j < nxt_byte; j++) {
										Byte_obj_ref rng_obj = Byte_obj_ref.new_(j);
										if (!pad_hash.Has(rng_obj))
											pad_hash.AddAsKeyAndVal(rng_obj);
									}
									i += 2;
									continue;
								}
								else {
									throw new XophpException(".. found but next byte must be greater than previous byte; src=" + pad_str);
								}
							}
						}
					}
					prv_byte = pad_byte;
					Byte_obj_ref pad_obj = Byte_obj_ref.new_(pad_byte);
					if (!pad_hash.Has(pad_obj))
						pad_hash.AddAsKeyAndVal(pad_obj);
				}
			}
		}

		// do trim
		int[] rv = new int[2];
		rv[0] = 0;
		rv[1] = src_len;
		if (type <= 0) { // trim or rtrim
			trim_inner(BoolUtl.N, rv, src_bry, src_len, pad_bry, pad_len, pad_hash);
		}
		if (type >= 0) { // trim or ltrim
			trim_inner(BoolUtl.Y, rv, src_bry, src_len, pad_bry, pad_len, pad_hash);
		}

		// return String
		int trim_bgn = rv[0];
		int trim_end = rv[1];
		return trim_bgn == 0 && trim_end == src_len
			? src_str
			: String_.new_u8(Bry_.Mid(src_bry, trim_bgn, trim_end));
	}
	private static void trim_inner(boolean is_bos, int[] rv, byte[] src_bry, int src_len, byte[] pad_bry, int pad_len, Hash_adp pad_hash) {
		// ----------------------
		// init vars
		// ----------------------
		int rv_idx = 1;
		int trim_bgn = src_len - 1;
		int trim_end = -1;
		int trim_add = -1;
		int trim_adj = 1;
		if (is_bos) {
			rv_idx = 0;
			trim_bgn = 0;
			trim_end = src_len;
			trim_add = 1;
			trim_adj = 0;
		}
		int trim_pos = trim_end;

		switch (pad_len) {
			// pad is 0 char; aka: ""
			case 0:
				break;
			// pad is 1 char
			case 1:
				byte pad_byte = pad_bry[0];
				for (int i = trim_bgn; i != trim_end; i += trim_add) {
					byte cur = src_bry[i];
					trim_pos = i + trim_adj;
					if (cur != pad_byte) {
						break;
					}
				}
				break;
			// pad is 2+ chars
			default:
				// loop src until non-matching pad int
				Byte_obj_ref temp = Byte_obj_ref.zero_();
				trim_pos = src_len;
				for (int i = trim_bgn; i != trim_end; i += trim_add) {
					temp.Val_(src_bry[i]);
					trim_pos = i + trim_adj;
					if (!pad_hash.Has(temp)) {
						break;
					}
				}
				break;
		}

		// set return
		if (trim_pos != trim_end) {
			rv[rv_idx] = trim_pos;
		}
	}
	// REF.PHP: https://www.php.net/manual/en/function.str-repeat.php
	public static String str_repeat(String input, int multiplier) {
		GfoStrBldr sb = new GfoStrBldr();
		for (int i = 0; i < multiplier; i++) {
			sb.Add(input);
		}
		return sb.ToStrAndClear();
	}

	public static boolean is_string(Object o) {
		return String_.as_(o) != null;
	}

	// REF.PHP: https://www.php.net/manual/en/function.strtoupper.php
	public static String strtoupper(String s) {
		return String_.Upper(s);
	}
	public static String strtolower(String s) {
		return String_.Lower(s);
	}
	// REF.PHP: https://www.php.net/manual/en/function.ord.php
	public static int ord(String s) {
		return String_.Len_eq_0(s) ? 0 : Char_.To_int(String_.CharAt(s, 0));
	}
	public static String[] explode(String delimiter, String str) {
		return String_.Split(str, delimiter);
	}
	// NOTE: support simple syntax only
	// REF.PHP: https://www.php.net/manual/en/language.types.String.php#language.types.String.parsing
	public static String Fmt(String fmt_str, Object... args) {
		byte[] fmt = Bry_.new_u8(fmt_str);
		int len = fmt.length;
		Bry_bfr bfr = Bry_bfr_.New();
		int pos = 0;
		int arg_idx = 0;
		while (pos < len) {
			// find next $
			int dollar_pos = Bry_find_.Find_fwd(fmt, AsciiByte.Dollar, pos);

			// no more $
			if (dollar_pos == Bry_find_.Not_found) {
				// add rest of fmt
				bfr.Add_mid(fmt, pos, len);
				break;
			}

			int key_bgn = dollar_pos + 1;
			// if $ at end, then just add it literally; also bound-check
			if (key_bgn == len) {
				bfr.Add_mid(fmt, pos, len);
				break;
			}

			int key_end = len;
			byte key_bgn_byte = fmt[key_bgn];
			// if { after $, then search forward for }
			if (key_bgn_byte == AsciiByte.CurlyBgn) {
				key_end = Bry_find_.Find_fwd(fmt, AsciiByte.CurlyEnd, key_bgn + 1, len);

				// no } found; fail; EX: $b = 'z'; echo("a${b");
				if (key_end == Bry_find_.Not_found) {
					throw Err_.new_wo_type("invalid fmt; fmt=" + fmt);
				}

				// skip past "}"
				key_end++;
			}
			// no "{"
			else {
				// search forward according to regex; ^[a-zA-Z_\x80-\xff][a-zA-Z0-9_\x80-\xff]*$; REF.PHP: https://www.php.net/manual/en/language.variables.basics.php
				for (int i = key_bgn; i < key_end; i++) {
					byte key_cur = fmt[i];
					if (!Is_identifier_char(key_cur, i == key_bgn)) {
						key_end = i;
						break;
					}
				}
			}

			// invalid key; EX: $0
			if (key_bgn == key_end) {
				bfr.Add_mid(fmt, pos, key_bgn);
				pos = key_bgn;
				continue;
			}

			// valid key; add everything before key_bgn
			bfr.Add_mid(fmt, pos, dollar_pos);

			// add arg_idx
			bfr.Add_str_u8(Object_.Xto_str_strict_or_empty(args[arg_idx++]));

			// update pos
			pos = key_end;
		}
		return bfr.To_str_and_clear();
	}
	private static boolean Is_identifier_char(byte b, boolean is_first) {
		switch (b) {
			// alpha and _ is always valid
			case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
			case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
			case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
			case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
			case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
			case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
			case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
			case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
			case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
			case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
			case AsciiByte.Underline:
				return true;
			// number is only valid if !is_first
			case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
			case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				return !is_first;
			default:
				// \x80-\xff is always true;
				return b >= 128 && b <= 255;
		}
	}

	// REF.PHP: https://www.php.net/manual/en/function.strrev.php
	public static String strrev(String src) {
		GfoStrBldr sb = new GfoStrBldr();
		Ustring usrc = UstringUtl.NewCodepoints(src);
		int usrc_len = usrc.LenInData();
		for (int i = usrc_len - 1; i > -1; i--) {
			int c = usrc.GetData(i);
			sb.AddCharByCode(c);
		}
		return sb.ToStrAndClear();
	}

	public static String Char_as_str(String s, int idx) {
		return Char_.To_str(String_.CharAt(s, idx));
	}
	public static boolean Char_eq(String s, int idx, String comp) {
		return String_.Eq(Char_as_str(s, idx), comp);
	}

	public Object Call(String method, Object... args) {
		if (String_.Eq(method, "strtoupper")) {
			String val = (String)args[0];
			return strtoupper(val);
		}
		else {
			throw Err_.new_unhandled_default(method);
		}
	}
	public static final XophpCallbackOwner Callback_owner = new XophpString_();
}
