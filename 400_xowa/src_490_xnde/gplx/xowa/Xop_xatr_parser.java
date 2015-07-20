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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*;
public class Xop_xatr_parser {	// REF.MW:Sanitizer.php|decodeTagAttributes;MW_ATTRIBS_REGEX
	private final List_adp xatrs = List_adp_.new_();
	private static final byte Mode_atr_bgn = 1, Mode_invalid = 2, Mode_key = 3, Mode_eq = 4, Mode_val_bgn = 5, Mode_val_quote = 6, Mode_val_raw = 7;
	private byte mode = Mode_atr_bgn;
	private int atr_bgn = -1, key_bgn = -1, key_end = -1, eq_pos = -1, val_bgn = -1, val_end = -1; boolean valid = true;
	private byte quote_byte = Byte_ascii.Null;
	private final Hash_adp_bry repeated_atrs_hash = Hash_adp_bry.ci_ascii_();		// ASCII:xnde_atrs
	private final Bry_bfr key_bfr = Bry_bfr.new_(), val_bfr = Bry_bfr.new_();
	private boolean key_bfr_on = false, val_bfr_on = false, ws_is_before_val = false;
	public Bry_obj_ref Bry_obj() {return bry_ref;} private final Bry_obj_ref bry_ref = Bry_obj_ref.null_();
	public int Xnde_find_gt_find(byte[] src, int pos, int end) {
		bry_ref.Val_(null);
		byte b = src[pos];
		if (b == Byte_ascii.Slash && pos + 1 < end) {	// if </ move pos to after /
			++pos;
			b = src[pos];
		}
		int gt_pos = Bry_finder.Find_fwd(src, Byte_ascii.Gt, pos, end); if (gt_pos == Bry_.NotFound) return String_.Find_none;
		byte[] bry = (byte[])xnde_hash.Get_by_mid(src, pos, gt_pos);
		bry_ref.Val_(bry);
		return bry == null ? String_.Find_none : bry.length + pos;
	}
	private int Xnde_find_gt(Gfo_msg_log log_mgr, byte[] src, int lt_pos, int end) {
		int pos = lt_pos + 1;
		byte b = src[pos];
		if (b == Byte_ascii.Slash && pos + 1 < end) {
			++pos;
			b = src[pos];
		}
		int match_pos = Xnde_find_gt_find(src, pos, end);
		if (match_pos == String_.Find_none) {log_mgr.Add_str_warn_key_none(Msg_mgr, "invalid lt", src, lt_pos); return String_.Find_none;}
		boolean slash_found = false;
		for (int i = match_pos; i < end; i++) {
			b = src[i];
			switch (b) {
				case Byte_ascii.Gt:			return i;
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: // skip any ws
					break;
				case Byte_ascii.Slash:
					if (slash_found)		{log_mgr.Add_str_warn_key_none(Msg_mgr, "multiple slashes not allowed", src, i); return String_.Find_none;}	// only allow one slash
					else					slash_found = true;
					break;
				default:
					log_mgr.Add_str_warn_key_none(Msg_mgr, "invalid character", src, i); 
					return String_.Find_none;
			}
		}
		log_mgr.Add_str_warn_key_none(Msg_mgr, "eos", src, lt_pos);
		return String_.Find_none;
	}
	public Xop_xatr_itm[] Parse(Gfo_msg_log log_mgr, byte[] src, int bgn, int end) {
		xatrs.Clear();
		repeated_atrs_hash.Clear();
		int i = bgn;
		mode = Mode_atr_bgn;
		boolean prv_is_ws = false;
		while (true) {
			if (i == end) {
				if (mode == Mode_val_quote) {		// quote still open
					int reset_pos = Bry_finder.Find_fwd(src, Byte_ascii.Space, atr_bgn, end);	// try to find 1st space within quote; EX:"a='b c=d" should try to reset at c=d
					boolean reset_found = reset_pos != Bry_finder.Not_found;
					valid = false; val_end = reset_found ? reset_pos : end;
					Make(log_mgr, src, val_end);	// create invalid atr
					if (reset_found) {				// space found; resume from text after space; EX: "a='b c=d"; PAGE:en.w:Aubervilliers DATE:2014-06-25
						i = Bry_finder.Find_fwd_while_not_ws(src, reset_pos, end);	// skip ws
						atr_bgn = -1;
						mode = Mode_atr_bgn;
						val_bfr.Clear();
						val_bfr_on = false;
						ws_is_before_val = false;
						continue;
					}
					else
						break;
				}
				else {
					if (mode == Mode_val_bgn)		// NOTE: handle dangling "k=" else will be "k"; EX: <a b=> x> <a b>; PAGE:en.s:Notes_by_the_Way/Chapter_2; DATE:2015-01-31
						valid = false;
					if (atr_bgn != -1) {			// atr_bgn will be -1 if atrs ends on quoted (EX:"a='b'"); else, pending atr that needs to be processed; EX: "a=b" b wil be in bfr
						val_end = end;
						Make(log_mgr, src, end);
					}
					break;
				}
			}
			else if (i > end)
				break;
			byte b = src[i];
			switch (mode) {
				case Mode_atr_bgn:
					switch (b) {
						case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: // skip any ws at bgn; note that once a non-ws char is encountered, it will immediately go into another mode
							break;
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
						case Byte_ascii.Colon:
							if (atr_bgn == -1) atr_bgn = i;
							mode = Mode_key;
							key_bgn = i;
							break;
						case Byte_ascii.Lt:
							int gt_pos = Xnde_find_gt(log_mgr, src, i, end);
							if (gt_pos == String_.Find_none) {
								valid = false; mode = Mode_invalid; if (atr_bgn == -1) atr_bgn = i; 
							}
							else {
								i = gt_pos;	// note that there is ++i below and loop will continue at gt_pos + 1 (next character after)
							}
							break;
						default:
							valid = false; mode = Mode_invalid; if (atr_bgn == -1) atr_bgn = i; 
							break;
					}
					break;
				case Mode_invalid:
					switch (b) {
						case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab:
							Make(log_mgr, src, i);
							mode = Mode_atr_bgn;
							break;
						default:
							break;
					}
					break;
				case Mode_key:
					switch (b) {
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
						case Byte_ascii.Colon: case Byte_ascii.Dash: case Byte_ascii.Dot: case Byte_ascii.Underline:
							if (key_bfr_on) key_bfr.Add_byte(b);
							break;
						case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab:
							if (valid) {
								key_end = i;
								mode = Mode_eq;
							}
							else
								Make(log_mgr, src, i);
							break;
						case Byte_ascii.Eq:
							if (valid) {
								key_end = i;
								mode = Mode_val_bgn;
								eq_pos = i;
							}
							break;						
						case Byte_ascii.Lt:
							int gt_pos = Xnde_find_gt(log_mgr, src, i, end);
							if (gt_pos == String_.Find_none) {
								valid = false; mode = Mode_invalid;
							}
							else {
								if (!key_bfr_on) key_bfr.Add_mid(src, key_bgn, i);
								i = gt_pos;	// note that there is ++i below and loop will continue at gt_pos + 1 (next character after)
								key_bfr_on = true;
							}
							break;
						default:
							valid = false; mode = Mode_invalid;
							break;
					}
					break;
				case Mode_eq:
					switch (b) {
						case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: // skip ws
							if (key_end == -1) {		// EX: "a = b"; key_end != -1 b/c 1st \s sets key_end; EX: "a b = c"; key_end
								val_end = i - 1;
								Make(log_mgr, src, i);
								mode = Mode_atr_bgn;
								continue;
							}
							break;
						case Byte_ascii.Eq:
							eq_pos = i;
							mode = Mode_val_bgn;
							break;
						case Byte_ascii.Quote: case Byte_ascii.Apos: // FUTURE: previous word was key
						default:	// NOTE: added this late; xml_parser was not handling "line start=3" DATE:2013-07-03
							val_end = i - 1;
							Make(log_mgr, src, i);
							mode = Mode_atr_bgn;
							continue;
					}
					break;
				case Mode_val_bgn:
					switch (b) {
						case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: // skip-ws
							ws_is_before_val = true;
							break;
						case Byte_ascii.Quote: case Byte_ascii.Apos:
							mode = Mode_val_quote; quote_byte = b; prv_is_ws = false;
							break;
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
						case Byte_ascii.Colon:
						case Byte_ascii.Hash:
							mode = Mode_val_raw;
							val_bgn = i;
							break;
						case Byte_ascii.Lt:
							int gt_pos = Xnde_find_gt(log_mgr, src, i, end);
							if (gt_pos == String_.Find_none) {
								valid = false; mode = Mode_invalid;
							}
							else {
								i = gt_pos;	// note that there is ++i below and loop will continue at gt_pos + 1 (next character after)
							}
							break;
						default:
							break;
					}
					break;
				case Mode_val_quote:
					if (val_bgn == -1) val_bgn = i;
					switch (b) {
						case Byte_ascii.Quote: case Byte_ascii.Apos:
							if (quote_byte == b) {
								val_end = i;
								Make(log_mgr, src, i + 1);	// NOTE: set atr_end *after* quote
							}
							prv_is_ws = false; if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
							break;
						case Byte_ascii.Lt:	// "<" try to find nowiki inside atr
							int gt_pos = Xnde_find_gt(log_mgr, src, i, end);
							if (gt_pos == String_.Find_none) {
								// valid = false; mode = Mode_invalid;	// DELETE: 2012-11-13; unpaired < should not mark atr invalid; EX: style='margin:1em<f'
								if (!val_bfr_on) val_bfr.Add_mid(src, val_bgn, i + 1);	// +1 to include <
								val_bfr_on = true;
							}
							else {
								if (!val_bfr_on) val_bfr.Add_mid(src, val_bgn, i);
								i = gt_pos;	// note that there is ++i below and loop will continue at gt_pos + 1 (next character after)
								val_bfr_on = true;
							}
							prv_is_ws = false;
							break;
						case Byte_ascii.Nl: case Byte_ascii.Tab: case Byte_ascii.Cr:	// REF.MW:Sanitizer.php|decodeTagAttributes $value = preg_replace( '/[\t\r\n ]+/', ' ', $value );
						case Byte_ascii.Space:
							if (!val_bfr_on) {
								val_bfr.Add_mid(src, val_bgn, i);
								val_bfr_on = true;
							}
							if (prv_is_ws) {}	// noop; only allow one ws at a time
							else {
								prv_is_ws = true; val_bfr.Add_byte(Byte_ascii.Space);									
							}
							break;
						default:
							prv_is_ws = false; if (val_bfr_on) val_bfr.Add_byte(b);		// INLINE: add char
							break;
					}
					break;
				case Mode_val_raw:	// no quotes; EX:a=bcd
					switch (b) {
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
						case Byte_ascii.Bang: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent:
						case Byte_ascii.Amp: case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star:
						case Byte_ascii.Comma: case Byte_ascii.Dash: case Byte_ascii.Dot: case Byte_ascii.Slash:
						case Byte_ascii.Colon: case Byte_ascii.Semic: case Byte_ascii.Gt:
						case Byte_ascii.Question: case Byte_ascii.At: case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end:
						case Byte_ascii.Pow: case Byte_ascii.Underline: case Byte_ascii.Tick:
						case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe: case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
							break;
						case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
							val_end = i;
							Make(log_mgr, src, i);
							break;
						case Byte_ascii.Eq:	// EX:"a= b=c" or "a=b=c"; PAGE:en.w:2013_in_American_television
							if (ws_is_before_val) {		// "a= b=c"; discard 1st and resume at 2nd
								int old_val_bgn = val_bgn;
								valid = false; mode = Mode_invalid; Make(log_mgr, src, val_bgn);	// invalidate cur atr; EX:"a="
								atr_bgn = key_bgn = old_val_bgn;	// reset atr / key to new atr; EX: "b"
								key_end = i;
								mode = Mode_val_bgn;				// set mode to val_bgn (basically, put after =)
							}
							else {						// "a=b=c"; discard all
								valid = false; mode = Mode_invalid;
							}
							break;
						case Byte_ascii.Lt:
							val_end = i;
							Make(log_mgr, src, i);
							--i;	// NOTE: --i to include "<" as part of next atr; above ws excludes from next atr
							break;
						default:
							valid = false; mode = Mode_invalid;
							break;
					}
					break;			
			}
			++i;
		}
		repeated_atrs_hash.Clear();
		return (Xop_xatr_itm[])xatrs.To_ary(Xop_xatr_itm.class);
	}
	private void Make(Gfo_msg_log log_mgr, byte[] src, int atr_end) {
		Xop_xatr_itm xatr = null;
		boolean key_bgn_exists = key_bgn != -1;
		boolean val_bgn_exists = val_bgn != -1;
		if (valid) {
			if (key_bgn_exists && val_bgn_exists)
				xatr = new Xop_xatr_itm(quote_byte, atr_bgn, atr_end, key_bgn, key_end, val_bgn, val_end, eq_pos);
			else {
				if (key_end == -1) key_end = val_end;		// NOTE: key_end == -1 when eos; EX: "a" would have key_bgn = 0; key_end = -1; val_end = 1 DATE:2014-07-03
				xatr = new Xop_xatr_itm(quote_byte, atr_bgn, atr_end, key_bgn, key_end);
			}
		}
		else {
			xatr = new Xop_xatr_itm(atr_bgn, atr_end);
			log_mgr.Add_itm_none(Log_invalid_atr, src, atr_bgn, atr_end);
		}
		if (valid) {	// note that invalid will have no key_bgn / key_end
			byte[] key_bry = key_bfr_on ? key_bfr.Xto_bry_and_clear() : Bry_.Mid(src, xatr.Key_bgn(), xatr.Key_end());
			xatr.Key_bry_(key_bry);
			Invalidate_repeated_atr(xatr, key_bry);
		}
		if (val_bfr_on) xatr.Val_bry_(val_bfr.Xto_bry_and_clear());
		xatrs.Add(xatr);
		mode = Mode_atr_bgn; quote_byte = Byte_ascii.Null; valid = true;
		atr_bgn = key_bgn = val_bgn = key_end = val_end = eq_pos = -1;
		val_bfr_on = key_bfr_on = ws_is_before_val = false;
	}
	private void Invalidate_repeated_atr(Xop_xatr_itm cur, byte[] key_bry) {
		Xop_xatr_itm prv = (Xop_xatr_itm)repeated_atrs_hash.Get_by(key_bry);
		if (prv != null) {
			prv.Tid_to_repeat_();
			repeated_atrs_hash.Del(key_bry);
		}
		repeated_atrs_hash.Add(key_bry, cur);
	}
	private static final Hash_adp_bry xnde_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_bry(Xop_xnde_tag_.Tag_nowiki.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag_noinclude.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag_includeonly.Name_bry())
	.Add_bry_bry(Xop_xnde_tag_.Tag_onlyinclude.Name_bry())
	;
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "xatr_parser");
	public static final Gfo_msg_itm
		Log_invalid_atr			= Gfo_msg_itm_.new_warn_(owner, "invalid_atr")
		;
	private static final String Msg_mgr = "gplx.xowa.wiki.parser.xatr";
}
/*
NOTE: this parser can be done with a trie and hooks on Quote,Apos,Eq,NewLine,Space,Tab, but...
- multi-byte lookup is not needed (main advantage of trie)
- less performant
- logic is indirect (b/c different chars are valid if first letter of key, raw mode, quoted)
*/
