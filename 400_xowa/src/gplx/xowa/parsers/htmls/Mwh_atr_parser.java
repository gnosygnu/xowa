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
package gplx.xowa.parsers.htmls;
import gplx.types.basics.strings.unicodes.Utf8Utl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryRef;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.parsers.xndes.Xop_xnde_tag_;
public class Mwh_atr_parser {	// REF.MW:Sanitizer.php|decodeTagAttributes;MW_ATTRIBS_REGEX
	private static final byte Area__invalid = 0, Area__atr_limbo = 1, Area__key = 2, Area__eql_limbo = 3, Area__val_limbo = 4, Area__val_quote = 5, Area__val_naked = 6;
	private final Hash_adp_bry repeated_atrs_hash = Hash_adp_bry.ci_a7();		// ASCII:xnde_atrs
	private final Mwh_atr_mgr atr_mgr = new Mwh_atr_mgr(16);
	private final BryWtr key_bfr = BryWtr.New(), val_bfr = BryWtr.New();
	private byte area = Area__atr_limbo;
	private int atr_bgn = -1, key_bgn = -1, key_end = -1, eql_pos = -1, val_bgn = -1, val_end = -1;
	private byte qte_byte = AsciiByte.Null;
	private boolean key_bfr_on = false, val_bfr_on = false, ws_is_before_val = false, qte_closed = false;
	private int nde_uid, nde_tid;
	public BryRef Bry_obj() {return bry_ref;} private final BryRef bry_ref = BryRef.NewEmpty();
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
							int reset_pos = BryFind.FindFwd(src, AsciiByte.Space, val_bgn, src_end);	// try to find 1st space within quote; EX:"a='b c=d" should try to reset at c=d
							boolean reset_found = reset_pos != BryFind.NotFound;
							area = Area__invalid; val_end = reset_found ? reset_pos : src_end;
							Make(src, val_end);	// create invalid atr
							if (reset_found) {	// space found; resume from text after space; EX: "a='b c=d"; PAGE:en.w:Aubervilliers DATE:2014-06-25
								pos = BryFind.FindFwdWhileNotWs(src, reset_pos, src_end);	// skip ws
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
			int b_len = Utf8Utl.LenOfCharBy1stByte(b);
			if (b == AsciiByte.Null) throw ErrUtl.NewArgs("null byte is invalid in byte array; src=", "src", StringUtl.NewU8(src, src_bgn, src_end));
			if (b_len > 1) {
				b = AsciiByte.Null; // NOTE: hacky, but if there is a Byte_ascii.Null, then it will have a b_len of 1
			}
			switch (area) {
				case Area__invalid:
					switch (b) {
						// ws -> end invalid area
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
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
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
							if (atr_bgn == -1) atr_bgn = pos;	// NOTE: atr_bgn == -1 needed for multiple spaces; ALSO: cannot move above switch b/c of <nowiki>
							break;
						// attribFirst -> enter Area__key; REF.MW: $attribFirst = '[:A-Z_a-z0-9]';
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
						case AsciiByte.Colon: case AsciiByte.Underline:
						case AsciiByte.Null:
							area = Area__key;
							if (atr_bgn == -1) atr_bgn = pos;	// NOTE: atr_bgn == -1 needed b/c of spaces
							key_bgn = pos;
							break;
						// angle_bgn -> check for <nowiki>
						case AsciiByte.AngleBgn:		// handle "<nowiki>"
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == BryFind.NotFound) {
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
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
						case AsciiByte.Colon: case AsciiByte.Underline: case AsciiByte.Dash: case AsciiByte.Dot:
							if (key_bfr_on) key_bfr.AddByte(b);
							break;
						case AsciiByte.Null:
							if (key_bfr_on) key_bfr.AddMid(src, pos, pos + b_len);
							break;
						// ws -> end key
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
							area = Area__eql_limbo;
							key_end = pos;
							break;
						// eq -> end key; go to Area_val_limbo
						case AsciiByte.Eq:
							area = Area__val_limbo;
							key_end = eql_pos = pos;
							break;	
						// angle_bgn -> check for <nowiki>
						case AsciiByte.AngleBgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == BryFind.NotFound)	// "<" should not be in key; EX: "ke<y"
								area = Area__invalid;
							else {
								if (!key_bfr_on) {key_bfr.AddMid(src, key_bgn, pos); key_bfr_on = true;}
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
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space: // skip ws
							break;
						// eq -> enter Area__val_limbo
						case AsciiByte.Eq:
							eql_pos = pos;
							area = Area__val_limbo;
							break;
						// attribFirst -> enter Area__key; REF.MW: $attribFirst = '[:A-Z_a-z0-9]';
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
						case AsciiByte.Colon: case AsciiByte.Underline:
						case AsciiByte.Null:
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
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
							ws_is_before_val = true;
							break;
						// quote -> enter Area_val_quote
						case AsciiByte.Quote: case AsciiByte.Apos:
							area = Area__val_quote; qte_byte = b; qte_closed = false;
							prv_is_ws = false;
							val_bgn = pos + 1;
							break;
						// alphanum -> enter Area_val_raw; REF.MW: [a-zA-Z0-9!#$%&()*,\\-.\\/:;<>?@[\\]^_`{|}~]+
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
						case AsciiByte.Bang: case AsciiByte.Hash: case AsciiByte.Dollar: case AsciiByte.Percent: case AsciiByte.Amp:
						case AsciiByte.ParenBgn: case AsciiByte.ParenEnd: case AsciiByte.Star: case AsciiByte.Comma: case AsciiByte.Dash: case AsciiByte.Dot:
						case AsciiByte.Backslash: case AsciiByte.Slash: case AsciiByte.Colon: case AsciiByte.Semic:
						case AsciiByte.Question: case AsciiByte.At:
						case AsciiByte.BrackBgn: case AsciiByte.BrackEnd: case AsciiByte.Pow: case AsciiByte.Underline: case AsciiByte.Tick:
						case AsciiByte.CurlyBgn: case AsciiByte.CurlyEnd: case AsciiByte.Pipe: case AsciiByte.Tilde:
						case AsciiByte.Null:
							area = Area__val_naked;
							val_bgn = pos;
							break;
						// case Byte_ascii.Angle_end: NOTE: valid in MW; making invalid now until finding counter-example
						// angle_bgn -> check for <nowiki>
						case AsciiByte.AngleBgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == BryFind.NotFound)
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
						case AsciiByte.Quote: case AsciiByte.Apos:
							if (qte_closed)
								area = Area__invalid;
							else {
								if (qte_byte == b) {	// quote closes val
									qte_closed = true;
									val_end = pos;
								}
								else {					// quote is just char; EX: title="1 o'clock" or title='The "C" way'
									prv_is_ws = false;
									if (val_bfr_on) val_bfr.AddByte(b);
								}
							}
							break;
						// ws -> convert all ws to \s; only allow 1 ws at any point in time
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:	// REF.MW:Sanitizer.php|decodeTagAttributes $value = preg_replace( '/[\t\r\n ]+/', ' ', $value );
							if (qte_closed) {
								Make(src, pos);	// NOTE: set atr_end *after* quote
								if (atr_bgn == -1) atr_bgn = pos;	// NOTE: process ws just like Area__atr_limbo
							}
							else {
								if (!val_bfr_on) {val_bfr.AddMid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
								if (prv_is_ws) {}	// noop; only allow one ws at a time; EX: "a  b" -> "a b"; "a\n\nb" -> "a b"
								else {
									prv_is_ws = true; val_bfr.AddByte(AsciiByte.Space);
								}
							}
							break;
						// angle_bgn -> check for <nowiki>; EX: <span title='ab<nowiki>c</nowiki>de'>
						case AsciiByte.AngleBgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == BryFind.NotFound) {
								// area = Area__invalid;	// "<" inside quote is invalid; EX: <span title='a<b'>c</span>
								if (val_bfr_on) val_bfr.AddByte(b);
							}
							else {
								if (qte_closed) {}
								else {
									if (!val_bfr_on) {val_bfr.AddMid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
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
								prv_is_ws = false;
								if (val_bfr_on) val_bfr.AddMid(src, pos, pos + b_len);
							}
							break;
					}
					break;
				}
				case Area__val_naked:	// no quotes; EX:a=bcd; REF.MW:([a-zA-Z0-9!#$%&()*,\\-.\\/:;<>?@[\\]^_`{|}~]+)
					switch (b) {
						// alphanum -> continue reading
						case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
						case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
						case AsciiByte.Bang: case AsciiByte.Hash: case AsciiByte.Dollar: case AsciiByte.Percent: case AsciiByte.Amp:
						case AsciiByte.ParenBgn: case AsciiByte.ParenEnd: case AsciiByte.Star: case AsciiByte.Comma: case AsciiByte.Dash: case AsciiByte.Dot:
						case AsciiByte.Backslash: case AsciiByte.Slash: case AsciiByte.Colon: case AsciiByte.Semic:
						case AsciiByte.Question: case AsciiByte.At:
						case AsciiByte.BrackBgn: case AsciiByte.BrackEnd: case AsciiByte.Pow: case AsciiByte.Underline: case AsciiByte.Tick:
						case AsciiByte.CurlyBgn: case AsciiByte.CurlyEnd: case AsciiByte.Pipe: case AsciiByte.Tilde:
							if (val_bfr_on) val_bfr.AddByte(b);
							break;
						case AsciiByte.Null:
							if (val_bfr_on) val_bfr.AddMid(src, pos, pos + b_len);
							break;
						// case Byte_ascii.Angle_end: NOTE: valid in MW; making invalid now until finding counter-example
						// angle_bgn -> check for <nowiki>; EX: a=b<nowiki>c</nowiki>d
						case AsciiByte.AngleBgn:
							int gt_pos = Xnde_find_gt(src, pos, src_end);
							if (gt_pos == BryFind.NotFound) {
								area = Area__invalid;	// NOTE: valid in MW; making invalid now until finding counter-example
							}
							else {
								if (!val_bfr_on) {val_bfr.AddMid(src, val_bgn, pos); val_bfr_on = true;}	// INLINE: val_bfr.init
								pos = gt_pos;	// note that there is ++pos below and loop will continue at gt_pos + 1 (next character after)								
							}
							break;
						// ws -> src_end atr
						case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
							val_end = pos;
							Make(src, pos);
							break;
						case AsciiByte.Eq:	// EX:"a= b=c" or "a=b=c"; PAGE:en.w:2013_in_American_television
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
			pos += b_len;
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
			key_bry = BryUtl.Empty;
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
			key_bry = key_bfr_on ? key_bfr.ToBryAndClear() : BryLni.Mid(src, key_bgn, key_end);	// always make key_bry; needed for repeated_atrs as well as key_tid
			if (val_bfr_on) val_bry = val_bfr.ToBryAndClear();
		}
		int qte_tid = Mwh_atr_itm_.Mask__qte__none;
		if (qte_byte != AsciiByte.Null)
			qte_tid = qte_byte == AsciiByte.Quote ? Mwh_atr_itm_.Mask__qte_qute : Mwh_atr_itm_.Mask__qte__apos;
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
		area = Area__atr_limbo; qte_byte = AsciiByte.Null;
		atr_bgn = key_bgn = val_bgn = key_end = val_end = eql_pos = -1;
		key_bfr_on = val_bfr_on = ws_is_before_val = qte_closed = false;
	}
	public int Xnde_find_gt_find(byte[] src, int pos, int end) {
		bry_ref.ValSet(BryUtl.Empty);
		byte b = src[pos];
		if (b == AsciiByte.Slash && pos + 1 < end) {	// if </ move pos to after /
			++pos;
			b = src[pos];
		}
		int gt_pos = BryFind.FindFwd(src, AsciiByte.Gt, pos, end); if (gt_pos == BryFind.NotFound) return BryFind.NotFound;
		byte[] bry = (byte[])xnde_hash.Get_by_mid(src, pos, gt_pos); if (bry == null) return BryFind.NotFound;
		bry_ref.ValSet(bry);
		return bry.length + pos;
	}
	private int Xnde_find_gt(byte[] src, int lt_pos, int end) {
		int pos = lt_pos + 1; if (pos == end) return BryFind.NotFound;
		byte b = src[pos];
		if (b == AsciiByte.Slash && pos + 1 < end) {
			++pos;
			b = src[pos];
		}
		int match_pos = Xnde_find_gt_find(src, pos, end);
		if (match_pos == BryFind.NotFound) {return BryFind.NotFound;}
		boolean slash_found = false;
		for (int i = match_pos; i < end; i++) {
			b = src[i];
			switch (b) {
				case AsciiByte.Gt:			return i;
				case AsciiByte.Space: case AsciiByte.Nl: case AsciiByte.Tab: // skip any ws
					break;
				case AsciiByte.Slash:
					if (slash_found)		{return BryFind.NotFound;}	// only allow one slash
					else					slash_found = true;
					break;
				default:
					return BryFind.NotFound;
			}
		}
		return BryFind.NotFound;
	}
	private static final Hash_adp_bry xnde_hash = Hash_adp_bry.ci_a7()
	.Add_bry_bry(Xop_xnde_tag_.Tag__nowiki.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__noinclude.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__includeonly.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag__onlyinclude.Name_bry())
	;
	public static final int Key_tid__unknown = -1;
}
