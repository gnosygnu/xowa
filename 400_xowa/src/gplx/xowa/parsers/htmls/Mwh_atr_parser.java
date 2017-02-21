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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*;
import gplx.xowa.parsers.xndes.*;	// for brys: <nowiki>, <noinclude>, <includeonly>, <onlyinclude>
public class Mwh_atr_parser {	// REF.MW:Sanitizer.php|decodeTagAttributes;MW_ATTRIBS_REGEX
	private static final byte Area__invalid = 0, Area__atr_limbo = 1, Area__key = 2, Area__eql_limbo = 3, Area__val_limbo = 4, Area__val_quote = 5, Area__val_naked = 6;
	private final    Hash_adp_bry repeated_atrs_hash = Hash_adp_bry.ci_a7();		// ASCII:xnde_atrs
	private final    Mwh_atr_mgr atr_mgr = new Mwh_atr_mgr(16);
	private final    Bry_bfr key_bfr = Bry_bfr_.New(), val_bfr = Bry_bfr_.New();
	private byte area = Area__atr_limbo;
	private int atr_bgn = -1, key_bgn = -1, key_end = -1, eql_pos = -1, val_bgn = -1, val_end = -1;
	private byte qte_byte = Byte_ascii.Null;
	private boolean key_bfr_on = false, val_bfr_on = false, ws_is_before_val = false, qte_closed = false;
	private int nde_uid, nde_tid;
	public Bry_obj_ref Bry_obj() {return bry_ref;} private final    Bry_obj_ref bry_ref = Bry_obj_ref.New_empty();
	public int Nde_end_tid() {return nde_end_tid;} private int nde_end_tid;
	public int Parse(Mwh_atr_wkr wkr, int nde_uid, int nde_tid, byte[] src, int src_bgn, int src_end) {
		this.nde_uid = nde_uid; this.nde_tid = nde_tid;
		this.nde_end_tid = Mwh_doc_parser.Nde_end_tid__invalid;
		this.atr_bgn = -1;
		area = Area__atr_limbo;
		boolean prv_is_ws = false;
		int pos = src_bgn;
		boolean loop = true;
		while (loop) {
			if (pos >= src_end) {
				switch (area) {
					case Area__key:			// EX: "a"
					case Area__eql_limbo:	// EX: "a "
					case Area__val_naked:	// EX: "a=b"
						break;				// valid atr
					case Area__val_quote:	// EX: "a='b'"
						if (qte_closed)
							Make(src, src_end);
						else {				// dangling; EX: "a='b c=d"
							int reset_pos = Bry_find_.Find_fwd(src, Byte_ascii.Space, val_bgn, src_end);	// try to find 1st space within quote; EX:"a='b c=d" should try to reset at c=d
							boolean reset_found = reset_pos != Bry_find_.Not_found;
							area = Area__invalid; val_end = reset_found ? reset_pos : src_end;
							Make(src, val_end);	// create invalid atr
							if (reset_found) {	// space found; resume from text after space; EX: "a='b c=d"; PAGE:en.w:Aubervilliers DATE:2014-06-25
								pos = Bry_find_.Find_fwd_while_not_ws(src, reset_pos, src_end);	// skip ws
								atr_bgn = -1;
								area = Area__atr_limbo;
								continue;
							}
						}
						break;
					case Area__invalid: case Area__atr_limbo:
					case Area__val_limbo:
						area = Area__invalid;
						break;
				}
				if (atr_bgn != -1) {
					val_end = src_end;
					Make(src, val_end);
				}
				break;
			}
			byte b = src[pos];
			switch (area) {
				case Area__invalid:
					switch (b) {
						// ws -> end invalid area
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
							Make(src, pos);
							area = Area__atr_limbo;
							break;
						// rest -> continue eating up invalid chars
						default:
							break;
					}
					break;
				case Area__atr_limbo:	// 1st area after (a) node_name, (b) attribute, (c) invalid_area
					switch (b) {
						// ws -> ignore; skip any ws in atr_limbo; note that once a non-ws char is encountered, it will immediately go into another area
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:								
							if (atr_bgn == -1) atr_bgn = pos;	// NOTE: atr_bgn == -1 needed for multiple spaces; ALSO: cannot move above switch b/c of <nowiki>
							break;
						// attribFirst -> enter Area__key; REF.MW: $attribFirst = '[:A-Z_a-z0-9]';
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
						case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
						case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
						case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
						case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
						case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
						case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
						case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
						case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
						case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
						case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
						case Byte_ascii.Colon: case Byte_ascii.Underline:
							area = Area__key;
							if (atr_bgn == -1) atr_bgn = pos;	// NOTE: atr_bgn == -1 needed b/c of spaces
							key_bgn = pos;
							break;
						// angle_bgn -> check for <nowiki>
						case Byte_ascii.Angle_bgn:		// handle "<nowiki>"
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == Bry_find_.Not_found) {
								area = Area__invalid; if (atr_bgn == -1) atr_bgn = pos;
							}
							else
								pos = gt_pos;	// position after ">"; note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)								
							break;
						// rest -> invalid
						default:				// quote and other non-valid key characters are invalid until next space; EX: "<span 'key_cannot_be_quoted' id='123'"
							area = Area__invalid; if (atr_bgn == -1) atr_bgn = pos;
							break;
					}
					break;
				case Area__key:
					switch (b) {
						// alphanum -> valid key chars; REF.MW: $attrib = '[:A-Z_a-z-.0-9]';
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
						case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
						case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
						case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
						case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
						case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
						case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
						case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
						case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
						case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
						case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
						case Byte_ascii.Colon: case Byte_ascii.Underline: case Byte_ascii.Dash: case Byte_ascii.Dot:
							if (key_bfr_on) key_bfr.Add_byte(b);
							break;
						// ws -> end key
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
							area = Area__eql_limbo;
							key_end = pos;
							break;
						// eq -> end key; go to Area_val_limbo
						case Byte_ascii.Eq:
							area = Area__val_limbo;
							key_end = eql_pos = pos;
							break;	
						// angle_bgn -> check for <nowiki>
						case Byte_ascii.Angle_bgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == Bry_find_.Not_found)	// "<" should not be in key; EX: "ke<y"
								area = Area__invalid;
							else {
								if (!key_bfr_on) {key_bfr.Add_mid(src, key_bgn, pos); key_bfr_on = true;}
								pos = gt_pos;	// note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)
							}
							break;
						// rest -> enter invalid
						default:
							area = Area__invalid;
							break;
					}
					break;
				case Area__eql_limbo:
					switch (b) {
						// ws -> skip
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space: // skip ws
							break;
						// eq -> enter Area__val_limbo
						case Byte_ascii.Eq:
							eql_pos = pos;
							area = Area__val_limbo;
							break;
						// attribFirst -> enter Area__key; REF.MW: $attribFirst = '[:A-Z_a-z0-9]';
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
						case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
						case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
						case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
						case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
						case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
						case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
						case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
						case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
						case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
						case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
						case Byte_ascii.Colon: case Byte_ascii.Underline:
							Make(src, pos);
							area = Area__key;
							atr_bgn = key_bgn = pos;
							break;
						// rest -> make atr and enter limbo
						default:
							area = Area__invalid;
							break;
					}
					break;
				case Area__val_limbo:
					switch (b) {
						// ws -> skip
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
							ws_is_before_val = true;
							break;
						// quote -> enter Area_val_quote
						case Byte_ascii.Quote: case Byte_ascii.Apos:
							area = Area__val_quote; qte_byte = b; qte_closed = false;
							prv_is_ws = false;
							val_bgn = pos + 1;
							break;
						// alphanum -> enter Area_val_raw; REF.MW: [a-zA-Z0-9!#$%&()*,\\-.\\/:;<>?@[\\]^_`{|}~]+
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
						case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
						case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
						case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
						case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
						case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
						case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
						case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
						case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
						case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
						case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
						case Byte_ascii.Bang: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Amp:
						case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star: case Byte_ascii.Comma: case Byte_ascii.Dash: case Byte_ascii.Dot:
						case Byte_ascii.Backslash: case Byte_ascii.Slash: case Byte_ascii.Colon: case Byte_ascii.Semic:
						case Byte_ascii.Question: case Byte_ascii.At:
						case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Pow: case Byte_ascii.Underline: case Byte_ascii.Tick:
						case Byte_ascii.Curly_bgn: case Byte_ascii.Curly_end: case Byte_ascii.Pipe: case Byte_ascii.Tilde:
							area = Area__val_naked;
							val_bgn = pos;
							break;
						// case Byte_ascii.Angle_end: NOTE: valid in MW; making invalid now until finding counter-example
						// angle_bgn -> check for <nowiki>
						case Byte_ascii.Angle_bgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == Bry_find_.Not_found)
								area = Area__invalid;	// NOTE: valid in MW; making invalid now until finding counter-example
							else
								pos = gt_pos;	// note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)
							break;
						// rest -> ignore
						default:
							area = Area__invalid;
							break;
					}
					break;
				case Area__val_quote: {	// EX: "'val' " in "key = 'val'"; REF.MW: \"([^<\"]*)\"
					switch (b) {
						// quote: check if same as opening quote
						case Byte_ascii.Quote: case Byte_ascii.Apos:
							if (qte_closed)
								area = Area__invalid;
							else {
								if (qte_byte == b) {	// quote closes val
									qte_closed = true;
									val_end = pos;
								}
								else {					// quote is just char; EX: title="1 o'clock" or title='The "C" way'
									prv_is_ws = false; if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
								}
							}
							break;
						// ws -> convert all ws to \s; only allow 1 ws at any point in time
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:	// REF.MW:Sanitizer.php|decodeTagAttributes $value = preg_replace( '/[\t\r\n ]+/', ' ', $value );
							if (qte_closed) {
								Make(src, pos);	// NOTE: set atr_end *after* quote
								if (atr_bgn == -1) atr_bgn = pos;	// NOTE: process ws just like Area__atr_limbo
							}
							else {
								if (!val_bfr_on) {val_bfr.Add_mid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
								if (prv_is_ws) {}	// noop; only allow one ws at a time; EX: "a  b" -> "a b"; "a\n\nb" -> "a b"
								else {
									prv_is_ws = true; val_bfr.Add_byte(Byte_ascii.Space);									
								}
							}
							break;
						// angle_bgn -> check for <nowiki>; EX: <span title='ab<nowiki>c</nowiki>de'>
						case Byte_ascii.Angle_bgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == Bry_find_.Not_found) {	
								// area = Area__invalid;	// "<" inside quote is invalid; EX: <span title='a<b'>c</span>
								if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
							}
							else {
								if (qte_closed) {}
								else {
									if (!val_bfr_on) {val_bfr.Add_mid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
								}
								pos = gt_pos;	// note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)
							}
							prv_is_ws = false;
							break;
						// rest -> add to val
						default:
							if (qte_closed)
								area = Area__invalid;
							else {
								prv_is_ws = false; if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
							}
							break;
					}
					break;
				}
				case Area__val_naked:	// no quotes; EX:a=bcd; REF.MW:([a-zA-Z0-9!#$%&()*,\\-.\\/:;<>?@[\\]^_`{|}~]+)
					switch (b) {
						// alphanum -> continue reading
						case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
						case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
						case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
						case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
						case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
						case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
						case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
						case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
						case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
						case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
						case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
						case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
						case Byte_ascii.Bang: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Amp:
						case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star: case Byte_ascii.Comma: case Byte_ascii.Dash: case Byte_ascii.Dot:
						case Byte_ascii.Backslash: case Byte_ascii.Slash: case Byte_ascii.Colon: case Byte_ascii.Semic:
						case Byte_ascii.Question: case Byte_ascii.At:
						case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Pow: case Byte_ascii.Underline: case Byte_ascii.Tick:
						case Byte_ascii.Curly_bgn: case Byte_ascii.Curly_end: case Byte_ascii.Pipe: case Byte_ascii.Tilde:
							if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
							break;
						// case Byte_ascii.Angle_end: NOTE: valid in MW; making invalid now until finding counter-example
						// angle_bgn -> check for <nowiki>; EX: a=b<nowiki>c</nowiki>d
						case Byte_ascii.Angle_bgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == Bry_find_.Not_found) {
								area = Area__invalid;	// NOTE: valid in MW; making invalid now until finding counter-example
							}
							else {
								if (!val_bfr_on) {val_bfr.Add_mid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
								pos = gt_pos;	// note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)								
							}
							break;
						// ws -> src_end atr
						case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
							val_end = pos;
							Make(src, pos);
							break;
						case Byte_ascii.Eq:	// EX:"a= b=c" or "a=b=c"; PAGE:en.w:2013_in_American_television
							if (ws_is_before_val) {		// "a= b=c"; discard 1st and resume at 2nd
								int old_val_bgn = val_bgn;
								area = Area__invalid; Make(src, val_bgn);	// invalidate cur atr; EX:"a="
								atr_bgn = key_bgn = old_val_bgn;	// reset atr / key to new atr; EX: "b"
								key_end = pos;
								area = Area__val_limbo;				// set area to val_bgn (basically, put after =)
							}
							else									// "a=b=c"; discard all
								area = Area__invalid;
							break;
						default:
							area = Area__invalid;
							break;
					}
					break;			
			}
			++pos;
		}

		// iterate atrs and notify
		int len = atr_mgr.Len();
		int[] data_ary = atr_mgr.Data_ary();
		byte[][] text_ary = atr_mgr.Text_ary();
		for (int j = 0; j < len; ++j) {
			int itm_idx = j * Mwh_atr_mgr.Idx__mult;
			byte[] key_bry = text_ary[j * Mwh_atr_mgr.Text__mult];
			byte[] val_bry_manual = null;
			int atr_utl = data_ary[itm_idx + Mwh_atr_mgr.Idx_atr_utl];
			boolean atr_valid = (atr_utl & Mwh_atr_itm_.Mask__valid) == Mwh_atr_itm_.Mask__valid;
			boolean repeated = (atr_utl & Mwh_atr_itm_.Mask__repeated) == Mwh_atr_itm_.Mask__repeated;
			boolean key_exists = (atr_utl & Mwh_atr_itm_.Mask__key_exists) == Mwh_atr_itm_.Mask__key_exists;
			boolean val_made = (atr_utl & Mwh_atr_itm_.Mask__val_made) == Mwh_atr_itm_.Mask__val_made;
			if (val_made)
				val_bry_manual = text_ary[(j * Mwh_atr_mgr.Text__mult) + 1];
			wkr.On_atr_each(this, src, nde_tid, atr_valid, repeated, key_exists, key_bry, val_bry_manual, data_ary, itm_idx);
		}
		atr_mgr.Clear();
		repeated_atrs_hash.Clear();

		return pos;
	}
	private void Make(byte[] src, int atr_end) {
		// calc final values for atr
		boolean key_exists = false;
		byte[] key_bry = null, val_bry = null;
		boolean atr_valid = true;
		if (area == Area__invalid) {
			atr_valid = false;
			key_bry = Bry_.Empty;
			key_bfr.Clear();
			if (val_bgn == -1) val_bgn = atr_bgn;
			val_bfr.Clear();
		}
		else {
			if (key_bgn != -1 && val_bgn != -1)				// key && val exists; EX: "<input id='123'>"
				key_exists = true;
			else {											// not a pair; EX: "<input checked>"
				if (key_end == -1) key_end = val_end;		// NOTE: key_end == -1 when eos; EX: "a" would have key_bgn = 0; key_end = -1; val_end = 1 DATE:2014-07-03
				val_bgn = val_end = -1;
			}
			key_bry = key_bfr_on ? key_bfr.To_bry_and_clear() : Bry_.Mid(src, key_bgn, key_end);	// always make key_bry; needed for repeated_atrs as well as key_tid
			if (val_bfr_on) val_bry = val_bfr.To_bry_and_clear();
		}
		int qte_tid = Mwh_atr_itm_.Mask__qte__none;
		if (qte_byte != Byte_ascii.Null)
			qte_tid = qte_byte == Byte_ascii.Quote ? Mwh_atr_itm_.Mask__qte_qute : Mwh_atr_itm_.Mask__qte__apos;
		int atr_uid = atr_mgr.Add(nde_uid, nde_tid, atr_valid, false, key_exists, atr_bgn, atr_end, key_bgn, key_end, key_bry, eql_pos, qte_tid, val_bgn, val_end, val_bry);

		// handle repeated atrs
		if (atr_valid) {
			int repeated_uid = repeated_atrs_hash.Get_as_int_or(key_bry, -1);
			if (repeated_uid != -1) {
				repeated_atrs_hash.Del(key_bry);
				atr_mgr.Set_repeated(repeated_uid);
			}
			repeated_atrs_hash.Add_bry_int(key_bry, atr_uid);
		}
		
		// reset temp variables
		area = Area__atr_limbo; qte_byte = Byte_ascii.Null;
		atr_bgn = key_bgn = val_bgn = key_end = val_end = eql_pos = -1;
		key_bfr_on = val_bfr_on = ws_is_before_val = qte_closed = false;
	}
	public int Xnde_find_gt_find(byte[] src, int pos, int end) {
		bry_ref.Val_(Bry_.Empty);
		byte b = src[pos];
		if (b == Byte_ascii.Slash && pos + 1 < end) {	// if </ move pos to after /
			++pos;
			b = src[pos];
		}
		int gt_pos = Bry_find_.Find_fwd(src, Byte_ascii.Gt, pos, end); if (gt_pos == Bry_find_.Not_found) return Bry_find_.Not_found;
		byte[] bry = (byte[])xnde_hash.Get_by_mid(src, pos, gt_pos); if (bry == null) return Bry_find_.Not_found;
		bry_ref.Val_(bry);
		return bry.length + pos;
	}
	private int Xnde_find_gt(byte[] src, int lt_pos, int end) {
		int pos = lt_pos + 1; if (pos == end) return Bry_find_.Not_found;
		byte b = src[pos];
		if (b == Byte_ascii.Slash && pos + 1 < end) {
			++pos;
			b = src[pos];
		}
		int match_pos = Xnde_find_gt_find(src, pos, end);
		if (match_pos == Bry_find_.Not_found) {return Bry_find_.Not_found;}
		boolean slash_found = false;
		for (int i = match_pos; i < end; i++) {
			b = src[i];
			switch (b) {
				case Byte_ascii.Gt:			return i;
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: // skip any ws
					break;
				case Byte_ascii.Slash:
					if (slash_found)		{return Bry_find_.Not_found;}	// only allow one slash
					else					slash_found = true;
					break;
				default:
					return Bry_find_.Not_found;
			}
		}
		return Bry_find_.Not_found;
	}
	private static final    Hash_adp_bry xnde_hash = Hash_adp_bry.ci_a7()
	.Add_bry_bry(Xop_xnde_tag_.Tag__nowiki.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__noinclude.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__includeonly.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__onlyinclude.Name_bry())
	;
	public static final int Key_tid__unknown = -1;
}
