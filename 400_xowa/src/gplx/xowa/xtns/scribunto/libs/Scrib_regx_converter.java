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
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.langs.regxs.Regx_adp_;
import gplx.langs.regxs.Regx_match;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.strings.unicodes.Ustring;
import gplx.types.basics.strings.unicodes.UstringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class Scrib_regx_converter {// THREAD.UNSAFE:MULTIPLE_RETURN_VALUES
	private final Scrib_regx_grp_mgr grp_mgr = new Scrib_regx_grp_mgr();

	public String Regx() {return regx;} private String regx;
	public KeyVal[] Capt_ary() {return grp_mgr.Capt__to_ary();}
	public boolean Any_pos() {return any_pos;} private boolean any_pos;
	public Regx_match[] Adjust_balanced(Regx_match[] rslts) {return grp_mgr.Adjust_balanced_many(rslts);}
	public Regx_match Adjust_balanced_one(Regx_match rslt) {return grp_mgr.Adjust_balanced_one(rslt);}
	public String patternToRegex(String pat_str, byte[] anchor, boolean mode_is_regx) {
		Ustring pat_ucs = UstringUtl.NewCodepoints(pat_str);
		// TODO.CACHE: if (!$this->patternRegexCache->has($cacheKey)) 
		grp_mgr.Clear();
		any_pos = false;
		boolean q_flag = false;
		BryWtr bfr = BryWtr.New();
		BryWtr tmp_bfr = null;
		BryFmtr fmtr_balanced = null;
		BryWtr bfr_balanced = null;
		Lua_cls_to_regx_map percent_map = Lua_cls_matcher.Instance.Percent();
		Lua_cls_to_regx_map brack_map = Lua_cls_matcher.Instance.Brack();

		// bfr.Add_byte(Byte_ascii.Slash); // TOMBSTONE: do not add PHP "/" at start
		int len = pat_ucs.LenInData();
		int grps_len = 0;
		int bct = 0;

		// REF.MW: https://github.com/wikimedia/mediawiki-extensions-Scribunto/blob/master/includes/engines/LuaCommon/UstringLibrary.php#L415
		for (int i = 0; i < len; i++) {
			int i_end = i + 1;
			q_flag = false; // must be reset; REF.MW:UstringLibrary.php|patternToRegex; DATE:2014-02-08
			int cur = pat_ucs.GetData(i);
			switch (cur) {
				case AsciiByte.Pow:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.Pow);
						continue;
					}
					q_flag = i != 0;
					bfr.Add((anchor == Anchor_null || q_flag) ? Bry_pow_escaped : anchor); // NOTE: must add anchor \G when using offsets; EX:cs.n:Category:1._zárí_2008; DATE:2014-05-07
					break;
				case AsciiByte.Dollar:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.Dollar);
						continue;
					}
					q_flag = i < len - 1;
					bfr.Add(q_flag ? Bry_dollar_escaped : Bry_dollar_literal);
					break;
				case AsciiByte.ParenBgn: {
					// fail if "(EOS"
					if (i + 1 >= len)
						throw ErrUtl.NewArgs("Unmatched open-paren at pattern character " + IntUtl.ToStr(i_end));
					int grp_idx = grp_mgr.Capt__len() + 1;

					// check for "()"; enables anypos flag
					boolean is_empty_capture = pat_ucs.GetData(i + 1) == AsciiByte.ParenEnd;
					if (is_empty_capture)
						any_pos = true;
					grp_mgr.Capt__add__real(grp_idx, is_empty_capture);
					bfr.AddByte(AsciiByte.ParenBgn); // $re .= "(?<m$n>";
					break;
				}
				case AsciiByte.ParenEnd:
					// fail if ")" without preceding "("
					if (grp_mgr.Open__len() <= 0)
						throw ErrUtl.NewArgs("Unmatched close-paren at pattern character " + IntUtl.ToStr(i_end));
					grp_mgr.Open__pop();
					bfr.AddByte(AsciiByte.ParenEnd);
					break;
				case AsciiByte.Percent:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.Percent);
						continue;
					}
					i++;
					if (i >= len)
						throw ErrUtl.NewArgs("malformed pattern (ends with '%')");
					byte[] percent_bry = percent_map.Get_or_null(pat_ucs.GetData(i));
					if (percent_bry != null) {
						bfr.Add(percent_bry);
						q_flag = true;
					}
					else {
						int nxt = pat_ucs.GetData(i);
						switch (nxt) {
							case AsciiByte.Ltr_b:	// EX: "%b()"
								i += 2;
								if (i >= len) throw ErrUtl.NewArgs("malformed pattern (missing arguments to '%b')");
								int char_0 = pat_ucs.GetData(i - 1);
								int char_1 = pat_ucs.GetData(i);
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
										fmtr_balanced = BryFmtr.New("(?=\\~{1})(?:(?=.*?\\~{1}(?!.*?\\~{3})(.*\\~{2}(?!.*\\~{4}).*))(?=.*?\\~{2}(?!.*?\\~{4})(.*)).)+?.*?(?=\\~{3})[^\\~{1}]*(?=\\~{4}$)", "unused", "1", "2", "3", "4");
										bfr_balanced = BryWtr.NewAndReset(255);
									}
									synchronized (fmtr_balanced) {
										++bct;
										int balanced_idx = grp_mgr.Full__len();
										fmtr_balanced.BldToBfr(bfr_balanced, IntUtl.ToBry(bct), Utf16Utl.EncodeIntToBry(char_0), Utf16Utl.EncodeIntToBry(char_1), IntUtl.ToBry(balanced_idx + 1), IntUtl.ToBry(balanced_idx + 2));
										if (mode_is_regx)
											grp_mgr.Capt__add__fake(2);
										bfr.Add(bfr_balanced.ToBryAndClear());
									}
								}
								break;
							case AsciiByte.Ltr_f: {	// EX: lua frontier pattern; "%f[%a]"; DATE:2015-07-21
								if (i + 1 >= len || pat_ucs.GetData(++i) != AsciiByte.BrackBgn)
									throw ErrUtl.NewArgs("missing '[' after %f in pattern at pattern character " + IntUtl.ToStr(i_end));
								// %f always followed by bracketed term; convert lua bracketed term to regex
								if (tmp_bfr == null) tmp_bfr = BryWtr.New();
								i = bracketedCharSetToRegex(tmp_bfr, brack_map, pat_ucs, i, len);
								byte[] re2 = tmp_bfr.ToBryAndClear();
								
								// scrib has following comment: 'Because %f considers the beginning and end of the String to be \0, determine if $re2 matches that and take it into account with "^" and "$".'
								// if the re2 is a negative class it will match \0; so, \W means anything not a word char, which will match \0; \w means word char which will not match \0
								if (Regx_adp_.Match("\0", StringUtl.NewU8(re2)))
									bfr.AddStrA7("(?<!^)(?<!").Add(re2).AddStrA7(")(?=").Add(re2).AddStrA7("|$)");	// match bgn / end of String
								else
									bfr.AddStrA7("(?<!"      ).Add(re2).AddStrA7(")(?=").Add(re2).AddStrA7(  ")");
								break;
							}
							case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
							case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9: {
								int grp_idx = nxt - AsciiByte.Num0;
								if (grp_idx == 0 || grp_idx > grp_mgr.Capt__len() || grp_mgr.Open__has(grp_idx))
									throw ErrUtl.NewArgs("invalid capture index %" + grps_len + " at pattern character " + IntUtl.ToStr(i));
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
				case AsciiByte.BrackBgn:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.BrackBgn);
						continue;
					}
					i = bracketedCharSetToRegex(bfr, brack_map, pat_ucs, i, len);
					q_flag = true;
					break;
				case AsciiByte.BrackEnd:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.BrackEnd);
						continue;
					}
					throw ErrUtl.NewArgs("Unmatched close-bracket at pattern character " + IntUtl.ToStr(i_end));
				case AsciiByte.Dot:
					if (!mode_is_regx) {
						bfr.AddByte(AsciiByte.Dot);
						continue;
					}
					bfr.AddByte(AsciiByte.Dot);
					q_flag = true;
					break;
				default:
					if (!mode_is_regx) {
						bfr.AddU8Int(cur);
						continue;
					}
					Regx_quote(bfr, cur);
					q_flag = true;
					break;
			}
			if (q_flag && i + 1 < len) {
				int tmp_b = pat_ucs.GetData(i + 1);
				switch (tmp_b) {
					case AsciiByte.Star:
					case AsciiByte.Plus:
					case AsciiByte.Question:
						bfr.AddByte((byte)tmp_b);
						++i;
						break;
					case AsciiByte.Dash:
						bfr.Add(Bry_star_question);
						i++;
						break;
				}
			}			
		}
		if (grp_mgr.Open__len() > 0) 
			throw ErrUtl.NewArgs("Unclosed capture beginning at pattern character " + grp_mgr.Open__get_at(0));
		// bfr.Add(Bry_regx_end);	// TOMBSTONE: do not add PHP /us at end; u=PCRE_UTF8 which is not needed for Java; s=PCRE_DOTALL which will be specified elsewhere
		regx = bfr.ToStrAndClear();
		return regx;
	}
	private int bracketedCharSetToRegex(BryWtr bfr, Lua_cls_to_regx_map brack_map, Ustring pat_ucs, int i, int len) {
		bfr.AddByte(AsciiByte.BrackBgn);
		i++;
		if (i < len && pat_ucs.GetData(i) == AsciiByte.Pow) {	// ^
			bfr.AddByte(AsciiByte.Pow);
			i++;
		}
		for (int j = i; i < len && (j == i || pat_ucs.GetData(i) != AsciiByte.BrackEnd); i++) {
			if (pat_ucs.GetData(i) == AsciiByte.Percent) {
				i++;
				if (i >= len) {
					break;
				}
				byte[] brack_bry = brack_map.Get_or_null(pat_ucs.GetData(i));
				if (brack_bry != null)
					bfr.Add(brack_bry);
				else
					Regx_quote(bfr, pat_ucs.GetData(i));
			}
			else if (i + 2 < len && pat_ucs.GetData(i + 1) == AsciiByte.Dash && pat_ucs.GetData(i + 2) != AsciiByte.BrackEnd && pat_ucs.GetData(i + 2) != AsciiByte.Hash) {
				if (pat_ucs.GetData(i) <= pat_ucs.GetData(i + 2)) {
					Regx_quote(bfr, pat_ucs.GetData(i));
					bfr.AddByte(AsciiByte.Dash);
					Regx_quote(bfr, pat_ucs.GetData(i + 2));
				}
				i += 2;
			}
			else {
				Regx_quote(bfr, pat_ucs.GetData(i));
			}
		}
		if (i > len) throw ErrUtl.NewArgs("Missing close-bracket for character set beginning at pattern character $nxt_pos");
		bfr.AddByte(AsciiByte.BrackEnd);

		// TOMBSTONE: below code will never run as it's not possible to generate "[]" or "[^]"; DATE:2018-07-01
			// Lua just ignores invalid ranges, while pcre throws an error.
			// We filter them out above, but then we need to special-case empty sets
			int bfr_len = bfr.Len();
			byte[] bfr_bry = bfr.Bry();
			if (bfr_len == 2 && bfr_bry[0] == AsciiByte.BrackBgn && bfr_bry[1] == AsciiByte.BrackEnd) {
				// Can't directly quantify (*FAIL), so wrap it.
				// "(?!)" would be simpler and could be quantified if not for a bug in PCRE 8.13 to 8.33
				bfr.Clear().AddStrA7("(?:(*FAIL))");
			}
			else if (bfr_len == 3 && bfr_bry[0] == AsciiByte.BrackBgn && bfr_bry[1] == AsciiByte.Pow && bfr_bry[2] == AsciiByte.BrackEnd) {
				bfr.Clear().AddStrA7(".");// 's' modifier is always used, so this works
			}
		return i;
	}
	private void Regx_quote(BryWtr bfr, int code) {
		if (Regx_char(code)) bfr.AddByte(AsciiByte.Backslash);
		bfr.AddU8Int(code);
	}
	private boolean Regx_char(int code) {
		switch (code) {
			case AsciiByte.Dot: case AsciiByte.Slash: case AsciiByte.Plus: case AsciiByte.Star: case AsciiByte.Question:
			case AsciiByte.Pow: case AsciiByte.Dollar: case AsciiByte.Eq: case AsciiByte.Bang: case AsciiByte.Pipe:
			case AsciiByte.Colon: case AsciiByte.Dash:
			case AsciiByte.BrackBgn: case AsciiByte.BrackEnd: case AsciiByte.ParenBgn: case AsciiByte.ParenEnd: case AsciiByte.CurlyBgn: case AsciiByte.CurlyEnd:
			case AsciiByte.Lt: case AsciiByte.Gt:
			case AsciiByte.Backslash:	// \ must be preg_quote'd; DATE:2014-01-06
				return true;
			default:
				return false;
		}
	}
	private static final byte[] Bry_pow_escaped = BryUtl.NewA7("\\^")
	, Bry_dollar_literal = BryUtl.NewA7("$"), Bry_dollar_escaped = BryUtl.NewA7("\\$")
	, Bry_bf0_seg_0 = BryUtl.NewA7("{"), Bry_bf0_seg_1 = BryUtl.NewA7("}[^"), Bry_bf0_seg_2 = BryUtl.NewA7("]*")
	, Bry_bf2_seg_0 = BryUtl.NewA7("\\")//, Bry_bf2_seg_1 = Bry_.new_a7("")
	, Bry_star_question = BryUtl.NewA7("*?")	// was *?
	;
	public static final byte[] Anchor_null = null, Anchor_G = BryUtl.NewA7("\\G"), Anchor_pow = BryUtl.NewA7("^");
}
class Lua_cls_matcher {
        public static final Lua_cls_matcher Instance = new Lua_cls_matcher();
	Lua_cls_matcher() {
		String regx_w = "\\w"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		String regx_W = "\\W"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		Init_itm(BoolUtl.Y, "a", "\\p{L}");
		Init_itm(BoolUtl.Y, "c", "\\p{Cc}");
		Init_itm(BoolUtl.Y, "d", "\\p{Nd}");
		Init_itm(BoolUtl.Y, "l", "\\p{Ll}");
		Init_itm(BoolUtl.Y, "p", "\\p{P}");
		Init_itm(BoolUtl.Y, "s", "\\s");						// JAVA: \p{Xps} not valid; REF: https://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
		Init_itm(BoolUtl.Y, "u", "\\p{Lu}");
		Init_itm(BoolUtl.Y, "w", regx_w);
		Init_itm(BoolUtl.Y, "x", "[0-9A-Fa-f０-９Ａ-Ｆａ-ｆ]");
		Init_itm(BoolUtl.Y, "z", "\\x00");

		Init_itm(BoolUtl.Y, "A", "\\P{L}");
		Init_itm(BoolUtl.Y, "C", "\\P{Cc}");
		Init_itm(BoolUtl.Y, "D", "\\P{Nd}");
		Init_itm(BoolUtl.Y, "L", "\\P{Ll}");
		Init_itm(BoolUtl.Y, "P", "\\P{P}");
		Init_itm(BoolUtl.Y, "S", "\\S");						// JAVA: \P{Xps} not valid
		Init_itm(BoolUtl.Y, "U", "\\P{Lu}");
		Init_itm(BoolUtl.Y, "W", regx_W);
		Init_itm(BoolUtl.Y, "X", "[^0-9A-Fa-f０-９Ａ-Ｆａ-ｆ]");
		Init_itm(BoolUtl.Y, "Z", "[^\\x00]");

		Init_itm(BoolUtl.N, "w", regx_w);
		Init_itm(BoolUtl.N, "x", "0-9A-Fa-f０-９Ａ-Ｆａ-ｆ");
		Init_itm(BoolUtl.N, "W", regx_W);
		Init_itm(BoolUtl.N, "X", "\\x00-\\x2f\\x3a-\\x40\\x47-\\x60\\x67-\\x{ff0f}\\x{ff1a}-\\x{ff20}\\x{ff27}-\\x{ff40}\\x{ff47}-\\x{10ffff}");
		Init_itm(BoolUtl.N, "Z", "\\x01-\\x{10ffff}");
	}
	public Lua_cls_to_regx_map Percent() {return percent_map;} private final Lua_cls_to_regx_map percent_map = new Lua_cls_to_regx_map();
	public Lua_cls_to_regx_map Brack() {return brack_map;} private final Lua_cls_to_regx_map brack_map = new Lua_cls_to_regx_map();

	private void Init_itm(boolean add_to_percent_hash, String lua, String php) {
		int lua_len = StringUtl.Len(lua);
		if (lua_len != 1) throw ErrUtl.NewArgs("lua must be 1 char only", "lua", lua);
		int lua_code = (int)StringUtl.CharAt(lua, 0);
		if (lua_code < AsciiByte.Ltr_A || lua_code > AsciiByte.Ltr_z) throw ErrUtl.NewArgs("lua must be between A and z", "lua", lua);

		byte[] php_bry = BryUtl.NewA7(php);
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
	private static final int MAX = AsciiByte.Max7Bit;
	private final byte[][] map = new byte[MAX][];
	public byte[] Get_or_null(int code) {
		return code < MAX ? map[code] : null;
	}
	public void Set(int code, byte[] val) {
		map[code] = val;
	}
}
