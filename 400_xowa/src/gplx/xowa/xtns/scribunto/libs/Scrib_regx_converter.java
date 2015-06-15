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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public class Scrib_regx_converter {
	private List_adp capt_list = List_adp_.new_(), grps_parens = List_adp_.new_(); private List_adp grps_open = List_adp_.new_();
	public Scrib_regx_converter() {Init();}
	public String Regx() {return regx;} private String regx;
	public List_adp Capt_list() {return capt_list;}
	public KeyVal[] Capt_ary() {return capt_list.Count() == 0 ? null : (KeyVal[])capt_list.To_ary(KeyVal.class);}
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
					if (i + 1 >= len) throw Err_.new_("Unmatched open-paren at pattern character " + Int_.Xto_str(i));
					boolean capt_itm = src[i + 1] == Byte_ascii.Paren_end;	// current is "()" 						
					++grps_len;
					capt_list.Add(KeyVal_.int_(grps_len, capt_itm));
					bfr.Add_byte(Byte_ascii.Paren_bgn);
					grps_open.Add(grps_len);
					grps_parens.Add(i + 1);
					break;
				}
				case Byte_ascii.Paren_end:
					if (grps_open.Count() == 0)
						throw Err_.new_("Unmatched close-paren at pattern character " + Int_.Xto_str(i));
					List_adp_.DelAt_last(grps_open);
					bfr.Add_byte(Byte_ascii.Paren_end);
					break;
				case Byte_ascii.Percent:
					++i;
					if (i >= len) throw Err_.new_("malformed pattern (ends with '%')");
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
								if (i >= len) throw Err_.new_("malformed pattern (missing arguments to \'%b\')");
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
										bfr_balanced = Bry_bfr.reset_(255);
									}
									synchronized (fmtr_balanced) {
										++bct;
										fmtr_balanced.Bld_bfr(bfr_balanced, Int_.Xto_bry(bct), Byte_.Ary(char_0), Byte_.Ary(char_1));
										bfr.Add(bfr_balanced.Xto_bry_and_clear());
									}
								}
								break;
							case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
							case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
								grps_len = nxt - Byte_ascii.Num_0;
								if (grps_len == 0 || grps_len > capt_list.Count() || grps_open_Has(grps_open, grps_len))
									throw Err_.new_("invalid capture index %" + grps_len + " at pattern character " + Int_.Xto_str(i));
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
								if (i + 2 < len) {
									byte dash_1 = src[i + 1];
									byte dash_2 = src[i + 2];
									if (dash_1 == Byte_ascii.Dash && dash_2 != Byte_ascii.Brack_end) {
										Regx_quote(bfr, tmp_b);
										bfr.Add_byte(Byte_ascii.Dash);
										Regx_quote(bfr, dash_2);
										i += 2;
										normal = false;
									}
								}
								if (normal)
									Regx_quote(bfr, src[i]);
								break;
						}
						if (stop) break;
					}
					if (i >= len) throw Err_.new_("Missing close-bracket for character set beginning at pattern character $nxt_pos");
					bfr.Add_byte(Byte_ascii.Brack_end);
					q_flag = true;
					break;
				case Byte_ascii.Brack_end: throw Err_.new_("Unmatched close-bracket at pattern character " + Int_.Xto_str(i));
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
					case Byte_ascii.Asterisk:
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
		if (grps_open.Count() > 0) throw Err_.new_("Unclosed capture beginning at pattern character " + Int_.cast_(grps_open.Get_at(0)));
//			bfr.Add(Bry_regx_end);	// NOTE: do not add PHP /us at end; u=PCRE_UTF8 which is not needed for Java; s=PCRE_DOTALL which will be specified elsewhere
		regx = bfr.Xto_str_and_clear();
		return regx;
	}	private Bry_bfr bfr = Bry_bfr.new_();
	boolean grps_open_Has(List_adp list, int v) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Object o = list.Get_at(i);
			if (Int_.cast_(o) == v) return true;
		}
		return false;
	}
	private void Regx_quote(Bry_bfr bfr, byte b) {
		if (Regx_char(b)) bfr.Add_byte(Byte_ascii.Backslash);
		bfr.Add_byte(b);
	}
	private boolean Regx_char(byte b) {
		switch (b) {
			case Byte_ascii.Dot: case Byte_ascii.Slash: case Byte_ascii.Plus: case Byte_ascii.Asterisk: case Byte_ascii.Question:
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
	private static final byte[] Bry_pow_escaped = Bry_.new_a7("\\^")
	, Bry_dollar_literal = Bry_.new_a7("$"), Bry_dollar_escaped = Bry_.new_a7("\\$")
	, Bry_bf0_seg_0 = Bry_.new_a7("{"), Bry_bf0_seg_1 = Bry_.new_a7("}[^"), Bry_bf0_seg_2 = Bry_.new_a7("]*")
	, Bry_bf2_seg_0 = Bry_.new_a7("\\")//, Bry_bf2_seg_1 = Bry_.new_a7("")
	, Bry_regx_dash = Bry_.new_a7("*?")	// was *?
	;
	public static final byte[] Anchor_null = null, Anchor_G = Bry_.new_a7("\\G"), Anchor_pow = Bry_.new_a7("^");
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
	private final Hash_adp_bry percent_hash = Hash_adp_bry.cs_(), brack_hash = Hash_adp_bry.cs_();
}
