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
package gplx.html; import gplx.*;
import gplx.core.brys.*;
public class Html_parser {
	public Html_parser() {
		Bry_bldr bry_bldr = new Bry_bldr();
		bry_xnde_name = bry_bldr.New_256().Set_rng_xml_identifier(Scan_valid).Set_rng_ws(Scan_stop).Val();
		bry_atr_key = bry_bldr.New_256().Set_rng_xml_identifier(Scan_valid).Set_rng_ws(Scan_stop).Set_many(Scan_stop, Byte_ascii.Eq).Val();
	}
	byte[] src; int pos, end; byte[] bry_xnde_name, bry_atr_key;
	int cur_atrs_idx = 0; int[] cur_atrs = new int[250];// define max of 50 atrs;	
	public Html_nde[] Parse_as_ary(byte[] src) {return Parse_as_ary(src, 0, src.length, Wildcard, Wildcard);}
	public Html_nde[] Parse_as_ary(byte[] src, int bgn, int end) {return Parse_as_ary(src, bgn, end, Wildcard, Wildcard);}
	public Html_nde[] Parse_as_ary(byte[] src, int bgn, int end, byte[] find_key, byte[] find_val) {	// flattens html into a list of hndes; only used for Options
		this.src = src; pos = bgn; this.end = end;
		List_adp rv = List_adp_.new_();
		while (pos < end) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Lt:
					if (xnde_init) {
						if (Parse_xnde_lhs()) {
							if (tag_tid_is_inline)
								rv.Add(new Html_nde(src, tag_tid_is_inline, cur_lhs_bgn, cur_lhs_end, cur_rhs_bgn, pos, cur_name_bgn, cur_name_end, cur_atrs, cur_atrs_idx));
							else
								xnde_init = false;
						}
					}
					else {
						if (Parse_xnde_rhs()) {
							rv.Add(new Html_nde(src, tag_tid_is_inline, cur_lhs_bgn, cur_lhs_end, cur_rhs_bgn, pos, cur_name_bgn, cur_name_end, cur_atrs, cur_atrs_idx));
						}
						xnde_init = true;
					}
					break;
				default:
					break;
			}
		}
		return (Html_nde[])rv.To_ary(Html_nde.class);
	}
	int cur_lhs_bgn, cur_lhs_end, cur_name_bgn, cur_name_end, cur_rhs_bgn; boolean xnde_init = true, tag_tid_is_inline = false;
	private boolean Parse_xnde_rhs() {
		cur_rhs_bgn = pos - 1;	// -1 b/c "<" is already read
		byte b = src[pos];
		if (b != Byte_ascii.Slash) return false;
		++pos;
		int name_len = cur_name_end - cur_name_bgn;
		if (pos + name_len >= end) return false;
		if (!Bry_.Match(src, pos, pos + name_len, src, cur_name_bgn,  cur_name_end)) return false;
		pos += name_len;
		if (src[pos] != Byte_ascii.Gt) return false;
		++pos;
		return true;
	}
	private boolean Parse_xnde_lhs() {
		cur_atrs_idx = 0;	
		cur_lhs_bgn = pos - 1;
		cur_name_bgn = pos;
		tag_tid_is_inline = false;
		byte rslt = Skip_while_valid(this.bry_atr_key);
		if (rslt == Scan_invalid) return false;
		cur_name_end = pos;
		int key_bgn, key_end, val_bgn, quote_type;
		while (true) {
			if (pos >= end) return false;
			key_bgn = key_end = val_bgn = quote_type = -1;
			Skip_ws();
			byte b = src[pos];
			if 		(b == Byte_ascii.Slash) {
				++pos;
				if (pos == end) return false;
				byte next = src[pos];
				if (next == Byte_ascii.Gt) {
					tag_tid_is_inline = true;
					++pos;
					break;
				}
				else return false;	// NOTE: don't consume byte b/c false
			}
			else if (b == Byte_ascii.Gt) {
				++pos;
				break;
			}
			key_bgn = pos;
			rslt = Skip_while_valid(this.bry_atr_key);
			if (rslt == Scan_invalid) return false;
			key_end = pos;
			Skip_ws();
			if (src[pos++] != Byte_ascii.Eq) return false;
			Skip_ws();
			byte quote_byte = src[pos];
			switch (quote_byte) {
				case Byte_ascii.Quote: 	quote_type = quote_byte; break;
				case Byte_ascii.Apos: 	quote_type = quote_byte; break;
				default:				return false;
			}
			val_bgn = ++pos;	// ++pos: start val after quote
			if (!Skip_to_quote_end(quote_byte)) return false;
			cur_atrs[cur_atrs_idx + 0] = quote_type;
			cur_atrs[cur_atrs_idx + 1] = key_bgn;
			cur_atrs[cur_atrs_idx + 2] = key_end;
			cur_atrs[cur_atrs_idx + 3] = val_bgn;
			cur_atrs[cur_atrs_idx + 4] = pos - 1;	// NOTE: Skip_to_quote_end positions after quote
			cur_atrs_idx += 5;
		}
		cur_lhs_end = pos;
		return true;
	}
	private void Skip_ws() {
		while (pos < end) {
			switch (src[pos]) {
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					++pos;
					break;
				default:
					return;
			}
		}
	}
	boolean Skip_to_quote_end(byte v) {
		while (pos < end) {
			byte b = src[pos++];
			if (b == v) {
				if (pos == end) return false;
				byte next = src[pos];
				if (next != v) 	return true;
				else			++pos;
			}
			else if (b == Byte_ascii.Backslash) {
				++pos;
			}
		}
		return false;
	}
	byte Skip_while_valid(byte[] comp) {
		while (pos < end) {
			byte rv = comp[src[pos]];
			if (rv == Scan_valid)
				++pos;
			else
				return rv;
		}
		return Scan_invalid;
	}
	private static final byte Scan_invalid = 0, Scan_valid = 1, Scan_stop = 2;
	public static final byte[] Wildcard = null;
	public static final String Wildcard_str = null;
}
