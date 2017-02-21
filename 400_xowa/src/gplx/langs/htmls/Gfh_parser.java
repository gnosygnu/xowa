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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
import gplx.core.brys.*;
public class Gfh_parser {
	public Gfh_parser() {
		Bry_bldr bry_bldr = new Bry_bldr();
		bry_xnde_name = bry_bldr.New_256().Set_rng_xml_identifier(Scan_valid).Set_rng_ws(Scan_stop).Val();
		bry_atr_key = bry_bldr.New_256().Set_rng_xml_identifier(Scan_valid).Set_rng_ws(Scan_stop).Set_many(Scan_stop, Byte_ascii.Eq).Val();
	}
	byte[] src; int pos, end; byte[] bry_xnde_name, bry_atr_key;
	int cur_atrs_idx = 0; int[] cur_atrs = new int[250];// define max of 50 atrs;	
	public Gfh_nde[] Parse_as_ary(byte[] src) {return Parse_as_ary(src, 0, src.length, Wildcard, Wildcard);}
	public Gfh_nde[] Parse_as_ary(byte[] src, int bgn, int end) {return Parse_as_ary(src, bgn, end, Wildcard, Wildcard);}
	public Gfh_nde[] Parse_as_ary(byte[] src, int bgn, int end, byte[] find_key, byte[] find_val) {	// flattens html into a list of hndes; only used for Options
		this.src = src; pos = bgn; this.end = end;
		List_adp rv = List_adp_.New();
		while (pos < end) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Lt:
					if (xnde_init) {
						if (Parse_xnde_lhs()) {
							if (tag_tid_is_inline)
								rv.Add(new Gfh_nde(src, tag_tid_is_inline, cur_lhs_bgn, cur_lhs_end, cur_rhs_bgn, pos, cur_name_bgn, cur_name_end, cur_atrs, cur_atrs_idx));
							else
								xnde_init = false;
						}
					}
					else {
						if (Parse_xnde_rhs()) {
							rv.Add(new Gfh_nde(src, tag_tid_is_inline, cur_lhs_bgn, cur_lhs_end, cur_rhs_bgn, pos, cur_name_bgn, cur_name_end, cur_atrs, cur_atrs_idx));
						}
						xnde_init = true;
					}
					break;
				default:
					break;
			}
		}
		return (Gfh_nde[])rv.To_ary(Gfh_nde.class);
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
	private static final    byte Scan_invalid = 0, Scan_valid = 1, Scan_stop = 2;
	public static final    byte[] Wildcard = null;
	public static final    String Wildcard_str = null;
}
