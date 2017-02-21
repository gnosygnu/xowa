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
import gplx.core.brys.fmtrs.*;
import gplx.langs.regxs.*;
public class Scrib_regx_converter {
	private final    List_adp capt_list = List_adp_.New(), grps_parens = List_adp_.New(); private final    List_adp grps_open = List_adp_.New();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Scrib_regx_converter() {Init();}
	public String Regx() {return regx;} private String regx;
	public List_adp Capt_list() {return capt_list;}
	public Keyval[] Capt_ary() {return capt_list.Count() == 0 ? null : (Keyval[])capt_list.To_ary(Keyval.class);}
	private Bry_fmtr fmtr_balanced; private Bry_bfr bfr_balanced;
	public String Parse(byte[] src, byte[] anchor) {
		int len = src.length;
		boolean q_flag = false;
		capt_list.Clear(); grps_open.Clear(); grps_parens.Clear();
		int grps_len = 0;
		int bct = 0;
		// bfr.Add_byte(Byte_ascii.Slash); // NOTE: do not add PHP "/" at start
		for (int i = 0; i < len; i++) {
			q_flag = false; // must be reset; REF.MW:UstringLibrary.php|patternToRegex; DATE:2014-02-08
			byte cur = src[i];
			switch (cur) {
				case Byte_ascii.Pow:
					q_flag = i != 0;
					bfr.Add(anchor == Anchor_null || q_flag ? Bry_pow_escaped : anchor);	// NOTE: must add anchor \G when using offsets; EX:cs.n:Category:1._zárí_2008; DATE:2014-05-07
					break;
				case Byte_ascii.Dollar:
					q_flag = i < len - 1;
					bfr.Add(q_flag ? Bry_dollar_escaped : Bry_dollar_literal);
					break;
				case Byte_ascii.Paren_bgn: {
					if (i + 1 >= len) throw Err_.new_wo_type("Unmatched open-paren at pattern character " + Int_.To_str(i));
					boolean capt_itm = src[i + 1] == Byte_ascii.Paren_end;	// current is "()" 						
					++grps_len;
					capt_list.Add(Keyval_.int_(grps_len, capt_itm));
					bfr.Add_byte(Byte_ascii.Paren_bgn);
					grps_open.Add(grps_len);
					grps_parens.Add(i + 1);
					break;
				}
				case Byte_ascii.Paren_end:
					if (grps_open.Count() == 0)
						throw Err_.new_wo_type("Unmatched close-paren at pattern character " + Int_.To_str(i));
					List_adp_.Del_at_last(grps_open);
					bfr.Add_byte(Byte_ascii.Paren_end);
					break;
				case Byte_ascii.Percent:
					++i;
					if (i >= len) throw Err_.new_wo_type("malformed pattern (ends with '%')");
					Object percent_obj = percent_hash.Get_by_mid(src, i, i + 1);
					if (percent_obj != null) {
						bfr.Add((byte[])percent_obj);
						q_flag = true;
					}
					else {
						byte nxt = src[i];
						switch (nxt) {
							case Byte_ascii.Ltr_b:	// EX: "%b()"
								i += 2;
								if (i >= len) throw Err_.new_wo_type("malformed pattern (missing arguments to '%b')");
								byte char_0 = src[i - 1];
								byte char_1 = src[i];
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
										fmtr_balanced = Bry_fmtr.new_("(?<b~{0}>\\~{1}(?:(?>[^\\~{1}\\~{2}]*)|\\~{1}[^\\~{1}\\~{2}]*\\~{2})*\\~{2})", "0", "1", "2");	// NOTE: complicated regex; represents 3 level depth of balanced parens; 4+ won't work; EX:(3(2(1)2)3) PAGE:en.w:Electricity_sector_in_Switzerland DATE:2015-01-23
										bfr_balanced = Bry_bfr_.Reset(255);
									}
									synchronized (fmtr_balanced) {
										++bct;
										fmtr_balanced.Bld_bfr(bfr_balanced, Int_.To_bry(bct), Byte_.Ary(char_0), Byte_.Ary(char_1));
										bfr.Add(bfr_balanced.To_bry_and_clear());
									}
								}
								break;
							case Byte_ascii.Ltr_f: {	// EX: lua frontier pattern; "%f[%a]"; DATE:2015-07-21
								++i;
								if (i + 1 >= len || src[i] != Byte_ascii.Brack_bgn) throw Err_.new_("scribunto", "missing '[' after %f in pattern at pattern character $ii");
								// %f always followed by bracketed term; convert lua bracketed term to regex
								i = bracketedCharSetToRegex(tmp_bfr, src, i, len);
								byte[] bracketed_regx = tmp_bfr.To_bry_and_clear();
								
								// scrib has following comment: 'Because %f considers the beginning and end of the String to be \0, determine if $re2 matches that and take it into account with "^" and "$".'
								// if the bracketed_regx is a negative class it will match \0; so, \W means anything not a word char, which will match \0; \w means word char which will not match \0
								if (Regx_adp_.Match("\0", String_.new_u8(bracketed_regx)))
									bfr.Add_str_a7("(?<!^)(?<!").Add(bracketed_regx).Add_str_a7(")(?=").Add(bracketed_regx).Add_str_a7("|$)");	// match bgn / end of String
								else
									bfr		 .Add_str_a7("(?<!").Add(bracketed_regx).Add_str_a7(")(?=").Add(bracketed_regx).Add_str_a7(  ")");
								break;
							}
							case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
							case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
								grps_len = nxt - Byte_ascii.Num_0;
								if (grps_len == 0 || grps_len > capt_list.Count() || grps_open_Has(grps_open, grps_len))
									throw Err_.new_wo_type("invalid capture index %" + grps_len + " at pattern character " + Int_.To_str(i));
								bfr.Add(Bry_bf2_seg_0).Add_int_variable(grps_len);//.Add(Bry_bf2_seg_1);	// $bfr .= "\\g{m$grps_len}";
								break;
							default:
								Regx_quote(bfr, nxt);
								q_flag = true;
								break;
						}
					}
					break;
				case Byte_ascii.Brack_bgn:
					i = bracketedCharSetToRegex(bfr, src, i, len);
					q_flag = true;
					break;
				case Byte_ascii.Brack_end: throw Err_.new_wo_type("Unmatched close-bracket at pattern character " + Int_.To_str(i));
				case Byte_ascii.Dot:
					q_flag = true;
					bfr.Add_byte(Byte_ascii.Dot);
					break;
				default:
					q_flag = true;
					Regx_quote(bfr, cur);
					break;
			}
			if (q_flag && i + 1 < len) {
				byte tmp_b = src[i + 1];
				switch (tmp_b) {
					case Byte_ascii.Star:
					case Byte_ascii.Plus:
					case Byte_ascii.Question:
						bfr.Add_byte(tmp_b);
						++i;
						break;
					case Byte_ascii.Dash:
						bfr.Add(Bry_regx_dash);
						++i;
						break;
				}
			}			
		}
		if (grps_open.Count() > 0) throw Err_.new_wo_type("Unclosed capture beginning at pattern character " + Int_.cast(grps_open.Get_at(0)));
//			bfr.Add(Bry_regx_end);	// NOTE: do not add PHP /us at end; u=PCRE_UTF8 which is not needed for Java; s=PCRE_DOTALL which will be specified elsewhere
		regx = bfr.To_str_and_clear();
		return regx;
	}	private Bry_bfr bfr = Bry_bfr_.New();
	private int bracketedCharSetToRegex(Bry_bfr bfr, byte[] src, int i, int len) {
		bfr.Add_byte(Byte_ascii.Brack_bgn);
		++i;
		if (i < len && src[i] == Byte_ascii.Pow) {	// ^
			bfr.Add_byte(Byte_ascii.Pow);
			++i;						
		}
		boolean stop = false;
		for (; i < len; i++) {
			byte tmp_b = src[i];
			switch (tmp_b) {
				case Byte_ascii.Brack_end:
					stop = true;
					break;
				case Byte_ascii.Percent:
					++i;
					if (i >= len)
						stop = true;
					else {
						Object brack_obj = brack_hash.Get_by_mid(src, i, i + 1);
						if (brack_obj != null)
							bfr.Add((byte[])brack_obj);
						else
							Regx_quote(bfr, src[i]);
					}
					break;
				default:
					boolean normal = true;
					int lhs_pos = i;	// NOTE: following block handles MBCS; EX:[𠀀-𯨟] PAGE:en.d:どう DATE:2016-01-22
					int lhs_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(src[lhs_pos]);
					int dash_pos = i + lhs_len;
					if (dash_pos < len) {
						byte dash_char = src[dash_pos];
						if (dash_char == Byte_ascii.Dash) {
							int rhs_pos = dash_pos + 1;
							if (rhs_pos < len) {
								byte rhs_byte = src[rhs_pos];
								if (rhs_byte != Byte_ascii.Brack_end) {// ignore dash if followed by brack_end; EX: [a-]; PAGE:en.d:frei; DATE:2016-01-23
									int rhs_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(rhs_byte);
									if (lhs_len == 1)
										Regx_quote(bfr, src[i]);
									else
										bfr.Add_mid(src, i, i + lhs_len);								
									bfr.Add_byte(Byte_ascii.Dash);
									if (rhs_len == 1)
										Regx_quote(bfr, src[rhs_pos]);
									else
										bfr.Add_mid(src, rhs_pos, rhs_pos + rhs_len);
									i = rhs_pos + rhs_len - 1;	// -1 b/c for() will do ++i
									normal = false;
								}
							}
						}
					}
					if (normal)
						Regx_quote(bfr, src[i]);
					break;
			}
			if (stop) break;
		}
		if (i >= len) throw Err_.new_wo_type("Missing close-bracket for character set beginning at pattern character $nxt_pos");
		bfr.Add_byte(Byte_ascii.Brack_end);
		return i;
	}
	boolean grps_open_Has(List_adp list, int v) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Object o = list.Get_at(i);
			if (Int_.cast(o) == v) return true;
		}
		return false;
	}
	private void Regx_quote(Bry_bfr bfr, byte b) {
		if (Regx_char(b)) bfr.Add_byte(Byte_ascii.Backslash);
		bfr.Add_byte(b);
	}
	private boolean Regx_char(byte b) {
		switch (b) {
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
	, Bry_regx_dash = Bry_.new_a7("*?")	// was *?
	;
	public static final    byte[] Anchor_null = null, Anchor_G = Bry_.new_a7("\\G"), Anchor_pow = Bry_.new_a7("^");
	private void Init() {
		String regx_w = "\\w"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		String regx_W = "\\W"; // JRE.7: \w not support in JRE.6; PAGE:en.w:A♯_(musical_note) DATE:2015-06-10
		Init_itm(Bool_.Y, "d", "\\p{Nd}");
		Init_itm(Bool_.Y, "l", "\\p{Ll}");
		Init_itm(Bool_.Y, "u", "\\p{Lu}");
		Init_itm(Bool_.Y, "a", "\\p{L}");
		Init_itm(Bool_.Y, "c", "\\p{Cc}");
		Init_itm(Bool_.Y, "p", "\\p{P}");
		Init_itm(Bool_.Y, "s", "\\s");
		Init_itm(Bool_.Y, "w", regx_w);
		Init_itm(Bool_.Y, "x", "[0-9A-Fa-f0-9A-Fa-f]");
		Init_itm(Bool_.Y, "z", "\\x00");
		Init_itm(Bool_.Y, "D", "\\P{Nd}");
		Init_itm(Bool_.Y, "L", "\\P{Ll}");
		Init_itm(Bool_.Y, "U", "\\P{Lu}");
		Init_itm(Bool_.Y, "A", "\\P{L}");
		Init_itm(Bool_.Y, "C", "\\P{Cc}");
		Init_itm(Bool_.Y, "P", "\\P{P}");
		Init_itm(Bool_.Y, "S", "\\S");						// JAVA: \P{Xps} not valid
		Init_itm(Bool_.Y, "W", regx_W);
		Init_itm(Bool_.Y, "X", "[^0-9A-Fa-f0-9A-Fa-f]");
		Init_itm(Bool_.Y, "Z", "[^\\x00]");
		Init_itm(Bool_.N, "w", regx_w);
		Init_itm(Bool_.N, "x", "0-9A-Fa-f0-9A-Fa-f");
		Init_itm(Bool_.N, "W", regx_W);
		Init_itm(Bool_.N, "X", "\\x00-\\x2f\\x3a-\\x40\\x47-\\x60\\x67-\\x{ff0f}\\x{ff1a}-\\x{ff20}\\x{ff27}-\\x{ff40}\\x{ff47}-\\x{10ffff}");
		Init_itm(Bool_.N, "Z", "\\x01-\\x{10ffff}");
	}
	private void Init_itm(boolean add_to_percent_hash, String lua, String php) {
		byte[] lua_bry = Bry_.new_a7(lua);
		byte[] php_bry = Bry_.new_a7(php);
		if (add_to_percent_hash) {
			percent_hash.Add_bry_obj(lua_bry, php_bry);
			brack_hash.Add_bry_obj(lua_bry, php_bry);	// always add to brack_hash; brack_hash = percent_hash + other characters
		}
		else {
			brack_hash.Add_if_dupe_use_nth(lua_bry, php_bry);	// replace percent_hash definitions
		}
	}
	private final    Hash_adp_bry percent_hash = Hash_adp_bry.cs(), brack_hash = Hash_adp_bry.cs();
}
