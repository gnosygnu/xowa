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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.core.brys.fmtrs.*; import gplx.core.intls.*;
import gplx.langs.regxs.*;
public class Scrib_regx_converter {
	private final    Scrib_regx_grp_mgr grp_mgr = new Scrib_regx_grp_mgr();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private Bry_bfr tmp_bfr;
	private Bry_fmtr fmtr_balanced; private Bry_bfr bfr_balanced;
	private final    Lua_cls_to_regx_map percent_map, brack_map;
	public Scrib_regx_converter() {
		percent_map = Lua_cls_matcher.Instance.Percent();
		brack_map = Lua_cls_matcher.Instance.Brack();
	}
	public String Regx() {return regx;} private String regx;
	public Keyval[] Capt_ary() {return grp_mgr.Capt__to_ary();}
	public boolean Any_pos() {return any_pos;} private boolean any_pos;
	public Regx_match[] Adjust_balanced(Regx_match[] rslts) {return grp_mgr.Adjust_balanced(rslts);}
	public String patternToRegex(String pat_str, byte[] anchor) {
		Unicode_string pat_ucs = Unicode_string_.New(pat_str);
		// TODO.CACHE: if (!$this->patternRegexCache->has($cacheKey)) 
		grp_mgr.Clear();
		any_pos = false;
		boolean q_flag = false;

		// bfr.Add_byte(Byte_ascii.Slash); // TOMBSTONE: do not add PHP "/" at start
		int len = pat_ucs.Len_codes();
		int grps_len = 0;
		int bct = 0;

		// REF.MW: https://github.com/wikimedia/mediawiki-extensions-Scribunto/blob/master/includes/engines/LuaCommon/UstringLibrary.php#L415
		for (int i = 0; i < len; i++) {
			int i_end = i + 1;
			q_flag = false; // must be reset; REF.MW:UstringLibrary.php|patternToRegex; DATE:2014-02-08
			int cur = pat_ucs.Val_codes(i);
			switch (cur) {
				case Byte_ascii.Pow:
					q_flag = i != 0;
					bfr.Add((anchor == Anchor_null || q_flag) ? Bry_pow_escaped : anchor); // NOTE: must add anchor \G when using offsets; EX:cs.n:Category:1._zárí_2008; DATE:2014-05-07
					break;
				case Byte_ascii.Dollar:
					q_flag = i < len - 1;
					bfr.Add(q_flag ? Bry_dollar_escaped : Bry_dollar_literal);
					break;
				case Byte_ascii.Paren_bgn: {
					// fail if "(EOS"
					if (i + 1 >= len)
						throw Err_.new_wo_type("Unmatched open-paren at pattern character " + Int_.To_str(i_end));
					int grp_idx = grp_mgr.Capt__len() + 1;

					// check for "()"; enables anypos flag
					boolean is_empty_capture = pat_ucs.Val_codes(i + 1) == Byte_ascii.Paren_end;
					if (is_empty_capture)
						any_pos = true;
					grp_mgr.Capt__add__real(grp_idx, is_empty_capture);
					bfr.Add_byte(Byte_ascii.Paren_bgn); // $re .= "(?<m$n>";
					break;
				}
				case Byte_ascii.Paren_end:
					// fail if ")" without preceding "("
					if (grp_mgr.Open__len() <= 0)
						throw Err_.new_wo_type("Unmatched close-paren at pattern character " + Int_.To_str(i_end));
					grp_mgr.Open__pop();
					bfr.Add_byte(Byte_ascii.Paren_end);
					break;
				case Byte_ascii.Percent:
					i++;
					if (i >= len)
						throw Err_.new_wo_type("malformed pattern (ends with '%')");
					byte[] percent_bry = percent_map.Get_or_null(pat_ucs.Val_codes(i));
					if (percent_bry != null) {
						bfr.Add(percent_bry);
						q_flag = true;
					}
					else {
						int nxt = pat_ucs.Val_codes(i);
						switch (nxt) {
							case Byte_ascii.Ltr_b:	// EX: "%b()"
								i += 2;
								if (i >= len) throw Err_.new_wo_type("malformed pattern (missing arguments to '%b')");
								int char_0 = pat_ucs.Val_codes(i - 1);
								int char_1 = pat_ucs.Val_codes(i);
								if (char_0 == char_1) {		// same char: easier regex; REF.MW: $bfr .= "{$d1}[^$d1]*$d1";
									bfr.Add(Bry_bf0_seg_0);
									Regx_quote(bfr, char_0);
									bfr.Add(Bry_bf0_seg_1);
									Regx_quote(bfr, char_0);
									bfr.Add(Bry_bf0_seg_2);
									Regx_quote(bfr, char_0);
								}
								else {						// diff char: harder regex; REF.MW: $bfr .= "(?<b$bct>$d1(?:(?>[^$d1$d2]+)|(?P>b$bct))*$d2)";
									if (fmtr_balanced == null) {
										// JAVA:recursive regex not possible, so need complicated regex
										// REF: https://stackoverflow.com/questions/47162098/is-it-possible-to-match-nested-brackets-with-regex-without-using-recursion-or-ba/47162099#47162099
										// PAGE:en.w:Portal:Constructed_languages/Intro DATE:2018-07-02											
										fmtr_balanced = Bry_fmtr.new_("(?=\\~{1})(?:(?=.*?\\~{1}(?!.*?\\~{3})(.*\\~{2}(?!.*\\~{4}).*))(?=.*?\\~{2}(?!.*?\\~{4})(.*)).)+?.*?(?=\\~{3})[^\\~{1}]*(?=\\~{4}$)", "unused", "1", "2", "3", "4"); 
										bfr_balanced = Bry_bfr_.Reset(255);
									}
									synchronized (fmtr_balanced) {
										++bct;
										int balanced_idx = grp_mgr.Full__len();
										fmtr_balanced.Bld_bfr(bfr_balanced, Int_.To_bry(bct), Utf16_.Encode_int_to_bry(char_0), Utf16_.Encode_int_to_bry(char_1), Int_.To_bry(balanced_idx + 1), Int_.To_bry(balanced_idx + 2));
										grp_mgr.Capt__add__fake(2);
										bfr.Add(bfr_balanced.To_bry_and_clear());
									}
								}
								break;
							case Byte_ascii.Ltr_f: {	// EX: lua frontier pattern; "%f[%a]"; DATE:2015-07-21
								if (i + 1 >= len || pat_ucs.Val_codes(++i) != Byte_ascii.Brack_bgn)
									throw Err_.new_("scribunto", "missing '[' after %f in pattern at pattern character " + Int_.To_str(i_end));
								// %f always followed by bracketed term; convert lua bracketed term to regex
								if (tmp_bfr == null) tmp_bfr = Bry_bfr_.New();
								i = bracketedCharSetToRegex(tmp_bfr, pat_ucs, i, len);
								byte[] re2 = tmp_bfr.To_bry_and_clear();
								
								// scrib has following comment: 'Because %f considers the beginning and end of the String to be \0, determine if $re2 matches that and take it into account with "^" and "$".'
								// if the re2 is a negative class it will match \0; so, \W means anything not a word char, which will match \0; \w means word char which will not match \0
								if (Regx_adp_.Match("\0", String_.new_u8(re2)))
									bfr.Add_str_a7("(?<!^)(?<!").Add(re2).Add_str_a7(")(?=").Add(re2).Add_str_a7("|$)");	// match bgn / end of String
								else
									bfr.Add_str_a7("(?<!"      ).Add(re2).Add_str_a7(")(?=").Add(re2).Add_str_a7(  ")");
								break;
							}
							case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
							case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9: {
								int grp_idx = nxt - Byte_ascii.Num_0;
								if (grp_idx == 0 || grp_idx > grp_mgr.Capt__len() || grp_mgr.Open__has(grp_idx))
									throw Err_.new_wo_type("invalid capture index %" + grps_len + " at pattern character " + Int_.To_str(i));
								bfr.Add(Bry_bf2_seg_0);
								grp_mgr.Idx__add(bfr, grp_idx);
								break;
							}
							default:
								Regx_quote(bfr, nxt);
								q_flag = true;
								break;
						}
					}
					break;
				case Byte_ascii.Brack_bgn:
					i = bracketedCharSetToRegex(bfr, pat_ucs, i, len);
					q_flag = true;
					break;
				case Byte_ascii.Brack_end:
					throw Err_.new_wo_type("Unmatched close-bracket at pattern character " + Int_.To_str(i_end));
				case Byte_ascii.Dot:
					bfr.Add_byte(Byte_ascii.Dot);
					q_flag = true;
					break;
				default:
					Regx_quote(bfr, cur);
					q_flag = true;
					break;
			}
			if (q_flag && i + 1 < len) {
				int tmp_b = pat_ucs.Val_codes(i + 1);
				switch (tmp_b) {
					case Byte_ascii.Star:
					case Byte_ascii.Plus:
					case Byte_ascii.Question:
						bfr.Add_byte((byte)tmp_b);
						++i;
						break;
					case Byte_ascii.Dash:
						bfr.Add(Bry_star_question);
						i++;
						break;
				}
			}			
		}
		if (grp_mgr.Open__len() > 0) 
			throw Err_.new_wo_type("Unclosed capture beginning at pattern character " + grp_mgr.Open__get_at(0));
		// bfr.Add(Bry_regx_end);	// TOMBSTONE: do not add PHP /us at end; u=PCRE_UTF8 which is not needed for Java; s=PCRE_DOTALL which will be specified elsewhere
		regx = bfr.To_str_and_clear();
		return regx;
	}
	private int bracketedCharSetToRegex(Bry_bfr bfr, Unicode_string pat_ucs, int i, int len) {
		bfr.Add_byte(Byte_ascii.Brack_bgn);
		i++;
		if (i < len && pat_ucs.Val_codes(i) == Byte_ascii.Pow) {	// ^
			bfr.Add_byte(Byte_ascii.Pow);
			i++;
		}
		for (int j = i; i < len && (j == i || pat_ucs.Val_codes(i) != Byte_ascii.Brack_end); i++) {
			if (pat_ucs.Val_codes(i) == Byte_ascii.Percent) {
				i++;
				if (i >= len) {
					break;
				}
				byte[] brack_bry = brack_map.Get_or_null(pat_ucs.Val_codes(i));
				if (brack_bry != null)
					bfr.Add(brack_bry);
				else
					Regx_quote(bfr, pat_ucs.Val_codes(i));
			}
			else if (i + 2 < len && pat_ucs.Val_codes(i + 1) == Byte_ascii.Dash && pat_ucs.Val_codes(i + 2) != Byte_ascii.Brack_end && pat_ucs.Val_codes(i + 2) != Byte_ascii.Hash) {
				if (pat_ucs.Val_codes(i) <= pat_ucs.Val_codes(i + 2)) {
					Regx_quote(bfr, pat_ucs.Val_codes(i));
					bfr.Add_byte(Byte_ascii.Dash);
					Regx_quote(bfr, pat_ucs.Val_codes(i + 2));
				}
				i += 2;
			}
			else {
				Regx_quote(bfr, pat_ucs.Val_codes(i));
			}
		}
		if (i > len) throw Err_.new_wo_type("Missing close-bracket for character set beginning at pattern character $nxt_pos");
		bfr.Add_byte(Byte_ascii.Brack_end);

		// TOMBSTONE: below code will never run as it's not possible to generate "[]" or "[^]"; DATE:2018-07-01
			// Lua just ignores invalid ranges, while pcre throws an error.
			// We filter them out above, but then we need to special-case empty sets
			int bfr_len = bfr.Len();
			byte[] bfr_bry = bfr.Bfr();
			if (bfr_len == 2 && bfr_bry[0] == Byte_ascii.Brack_bgn && bfr_bry[1] == Byte_ascii.Brack_end) {
				// Can't directly quantify (*FAIL), so wrap it.
				// "(?!)" would be simpler and could be quantified if not for a bug in PCRE 8.13 to 8.33
				bfr.Clear().Add_str_a7("(?:(*FAIL))");
			}
			else if (bfr_len == 3 && bfr_bry[0] == Byte_ascii.Brack_bgn && bfr_bry[1] == Byte_ascii.Pow && bfr_bry[2] == Byte_ascii.Brack_end) {
				bfr.Clear().Add_str_a7(".");// 's' modifier is always used, so this works
			}
		return i;
	}
	private void Regx_quote(Bry_bfr bfr, int code) {
		if (Regx_char(code)) bfr.Add_byte(Byte_ascii.Backslash);
		bfr.Add_u8_int(code);
	}
	private boolean Regx_char(int code) {
		switch (code) {
			case Byte_ascii.Dot: case Byte_ascii.Slash: case Byte_ascii.Plus: case Byte_ascii.Star: case Byte_ascii.Question:
			case Byte_ascii.Pow: case Byte_ascii.Dollar: case Byte_ascii.Eq: case Byte_ascii.Bang: case Byte_ascii.Pipe:
			case Byte_ascii.Colon: case Byte_ascii.Dash:
			case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Curly_bgn: case Byte_ascii.Curly_end:
			case Byte_ascii.Lt: case Byte_ascii.Gt:
			case Byte_ascii.Backslash:	// \ must be preg_quote'd; DATE:2014-01-06
				return true;
			default:
				return false;
		}
	}
	private static final    byte[] Bry_pow_escaped = Bry_.new_a7("\\^")
	, Bry_dollar_literal = Bry_.new_a7("$"), Bry_dollar_escaped = Bry_.new_a7("\\$")
	, Bry_bf0_seg_0 = Bry_.new_a7("{"), Bry_bf0_seg_1 = Bry_.new_a7("}[^"), Bry_bf0_seg_2 = Bry_.new_a7("]*")
	, Bry_bf2_seg_0 = Bry_.new_a7("\\")//, Bry_bf2_seg_1 = Bry_.new_a7("")
	, Bry_star_question = Bry_.new_a7("*?")	// was *?
	;
	public static final    byte[] Anchor_null = null, Anchor_G = Bry_.new_a7("\\G"), Anchor_pow = Bry_.new_a7("^");
}
class Lua_cls_matcher {
        public static final    Lua_cls_matcher Instance = new Lua_cls_matcher();
	Lua_cls_matcher() {
		String regx_w = "\\w"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		String regx_W = "\\W"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		Init_itm(Bool_.Y, "a", "\\p{L}");
		Init_itm(Bool_.Y, "c", "\\p{Cc}");
		Init_itm(Bool_.Y, "d", "\\p{Nd}");
		Init_itm(Bool_.Y, "l", "\\p{Ll}");
		Init_itm(Bool_.Y, "p", "\\p{P}");
		Init_itm(Bool_.Y, "s", "\\s");						// JAVA: \p{Xps} not valid; REF: https://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
		Init_itm(Bool_.Y, "u", "\\p{Lu}");
		Init_itm(Bool_.Y, "w", regx_w);
		Init_itm(Bool_.Y, "x", "[0-9A-Fa-f0-9A-Fa-f]");
		Init_itm(Bool_.Y, "z", "\\x00");

		Init_itm(Bool_.Y, "A", "\\P{L}");
		Init_itm(Bool_.Y, "C", "\\P{Cc}");
		Init_itm(Bool_.Y, "D", "\\P{Nd}");
		Init_itm(Bool_.Y, "L", "\\P{Ll}");
		Init_itm(Bool_.Y, "P", "\\P{P}");
		Init_itm(Bool_.Y, "S", "\\S");						// JAVA: \P{Xps} not valid
		Init_itm(Bool_.Y, "U", "\\P{Lu}");
		Init_itm(Bool_.Y, "W", regx_W);
		Init_itm(Bool_.Y, "X", "[^0-9A-Fa-f0-9A-Fa-f]");
		Init_itm(Bool_.Y, "Z", "[^\\x00]");

		Init_itm(Bool_.N, "w", regx_w);
		Init_itm(Bool_.N, "x", "0-9A-Fa-f0-9A-Fa-f");
		Init_itm(Bool_.N, "W", regx_W);
		Init_itm(Bool_.N, "X", "\\x00-\\x2f\\x3a-\\x40\\x47-\\x60\\x67-\\x{ff0f}\\x{ff1a}-\\x{ff20}\\x{ff27}-\\x{ff40}\\x{ff47}-\\x{10ffff}");
		Init_itm(Bool_.N, "Z", "\\x01-\\x{10ffff}");
	}
	public Lua_cls_to_regx_map Percent() {return percent_map;} private final    Lua_cls_to_regx_map percent_map = new Lua_cls_to_regx_map();
	public Lua_cls_to_regx_map Brack() {return brack_map;} private final    Lua_cls_to_regx_map brack_map = new Lua_cls_to_regx_map();

	private void Init_itm(boolean add_to_percent_hash, String lua, String php) {
		int lua_len = String_.Len(lua);
		if (lua_len != 1) throw Err_.new_wo_type("lua must be 1 char only", "lua", lua);
		int lua_code = (int)String_.CharAt(lua, 0);
		if (lua_code < Byte_ascii.Ltr_A || lua_code > Byte_ascii.Ltr_z) throw Err_.new_wo_type("lua must be between A and z", "lua", lua);

		byte[] php_bry = Bry_.new_a7(php);
		if (add_to_percent_hash) {
			percent_map.Set(lua_code, php_bry);
			brack_map.Set(lua_code, php_bry);  // always add to brack_hash; brack_hash = percent_hash + other characters
		}
		else {
			brack_map.Set(lua_code, php_bry); // replace percent_hash definitions
		}
	}
}
class Lua_cls_to_regx_map {
	private static final    int MAX = Byte_ascii.Max_7_bit;
	private final    byte[][] map = new byte[MAX][];
	public byte[] Get_or_null(int code) {
		return code < MAX ? map[code] : null;
	}
	public void Set(int code, byte[] val) {
		map[code] = val;
	}
}
