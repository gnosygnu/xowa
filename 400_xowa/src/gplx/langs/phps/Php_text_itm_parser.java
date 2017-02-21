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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*;
public class Php_text_itm_parser {
	public static final byte Rslt_orig = 0, Rslt_dirty = 1, Rslt_fmt = 2;
	public boolean Quote_is_single() {return quote_is_single;} public Php_text_itm_parser Quote_is_single_(boolean v) {quote_is_single = v; return this;} private boolean quote_is_single;
	public byte[] Parse_as_bry(List_adp tmp_list, byte[] raw, Byte_obj_ref rslt_ref, Bry_bfr tmp_bfr) {
		Parse(tmp_list, raw, rslt_ref);
		byte[] rv = raw;
		switch (rslt_ref.Val()) {
			case Rslt_orig: break;
			case Rslt_dirty:
			case Rslt_fmt:
				tmp_bfr.Clear();
				int tmp_list_len = tmp_list.Count();
				for (int i = 0; i < tmp_list_len; i++) {
					Php_text_itm itm = (Php_text_itm)tmp_list.Get_at(i);
					itm.Bld(tmp_bfr, raw);
				}
				rv = tmp_bfr.To_bry_and_clear();
				break;
		}
		return rv;
	}
	public void Parse(List_adp tmp_list, byte[] raw) {
		Parse(tmp_list, raw, Byte_obj_ref.zero_());
	}
	public void Parse(List_adp tmp_list, byte[] raw, Byte_obj_ref rslt) {
		tmp_list.Clear();
		int raw_len = raw.length; int raw_last = raw_len - 1; 
		int txt_bgn = -1;
		byte rslt_val = Rslt_orig;
		for (int i = 0; i < raw_len; i++) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Backslash:
					if (txt_bgn != -1) {tmp_list.Add(new Php_text_itm_text(txt_bgn, i)); txt_bgn = -1; rslt_val = Rslt_dirty;}
					boolean pos_is_last = i == raw_last;
					int next_pos = i + 1;
					byte next_char = pos_is_last ? Byte_ascii.Null : raw[next_pos];
					if (quote_is_single) {	// NOTE: q1 is simpler than q2; REF.MW:http://php.net/manual/en/language.types.String.php; DATE:2014-08-06
						switch (next_char) {
							case Byte_ascii.Apos:		next_char = Byte_ascii.Apos; break;
							case Byte_ascii.Backslash:	next_char = Byte_ascii.Backslash; break;
							default:					next_char = Byte_ascii.Null; break;
						}
					}
					else {
						if (pos_is_last) throw Err_.new_wo_type("backslash_is_last_char", "raw", String_.new_u8(raw));
						switch (next_char) {
							case Byte_ascii.Backslash:	next_char = Byte_ascii.Backslash; break;
							case Byte_ascii.Quote:		next_char = Byte_ascii.Quote; break;
							case Byte_ascii.Ltr_N:
							case Byte_ascii.Ltr_n:		next_char = Byte_ascii.Nl; break;
							case Byte_ascii.Ltr_T:
							case Byte_ascii.Ltr_t:		next_char = Byte_ascii.Tab; break;
							case Byte_ascii.Ltr_R:
							case Byte_ascii.Ltr_r:		next_char = Byte_ascii.Cr; break;					
							case Byte_ascii.Ltr_U:
							case Byte_ascii.Ltr_u:	{	// EX: "\u007C"
								rslt_val = Rslt_dirty;
								Parse_utf16(tmp_list, raw, next_pos + 1, raw_len);	// +1 to skip u
								i = next_pos + 4;	// +4 to skip utf16 seq; EX: \u007C; +4 for 007C
								continue;
							}
							case Byte_ascii.Ltr_X:
							case Byte_ascii.Ltr_x:	{	// EX: "\xc2"
								rslt_val = Rslt_dirty;
								byte[] literal = Bry_.Add(CONST_utf_prefix, Bry_.Mid(raw, next_pos + 1, next_pos + 3));
								tmp_list.Add(new Php_text_itm_utf16(i, i + 4, literal));
								i = next_pos + 2;	// +2 to skip rest; EX: \xc2; +2 for c2
								continue;
							}
							default:					next_char = Byte_ascii.Null; break;
						}
					}
					if (next_char == Byte_ascii.Null) {
						if (txt_bgn == -1) txt_bgn = i;
					}
					else {
						tmp_list.Add(new Php_text_itm_escaped(i, next_pos, next_char)); rslt_val = Rslt_dirty;
						i = next_pos;
					}
					break;
				case Byte_ascii.Dollar:
					if (txt_bgn != -1) {tmp_list.Add(new Php_text_itm_text(txt_bgn, i)); txt_bgn = -1;}
					if (i == raw_last) {
						//throw Err_mgr.Instance.fmt_auto_(GRP_KEY, "dollar_is_last_char", String_.new_u8(raw));
					}
					int int_end = Find_fwd_non_int(raw, i + 1, raw_len);	// +1 to search after $
					int int_val = Bry_.To_int_or(raw, i + 1, int_end, -1); // +1 to search after $
					if (int_val == -1) {
						tmp_list.Add(new Php_text_itm_text(i, i + 1)); 
						continue;
					}
					//throw Err_mgr.Instance.fmt_auto_(GRP_KEY, "invalid_arg", String_.new_u8(raw));
					tmp_list.Add(new Php_text_itm_arg(i, int_end, int_val));
					rslt_val = Rslt_fmt;
					i = int_end - 1;	// -1 b/c i++ in for loop 
					break;
				default:
					if (txt_bgn == -1) txt_bgn = i;
					break;
			}
		}	
		if (txt_bgn != -1) {tmp_list.Add(new Php_text_itm_text(txt_bgn, raw_len)); txt_bgn = -1; rslt_val = Rslt_dirty;}
		rslt.Val_(rslt_val);
	}	private static final byte[] CONST_utf_prefix = Bry_.new_a7("\\u00");
	private void Parse_utf16(List_adp rv, byte[] src, int bgn, int src_len) {
		int end = bgn + 4;
		if (end >= src_len) throw Err_.new_wo_type("utf16_parse", "src", String_.new_u8(src));
		int v = Int_.To_int_hex(src, bgn, end);	// +2; skip "\" + "u"
		byte[] literal = gplx.core.intls.Utf16_.Encode_int_to_bry(v);
		rv.Add(new Php_text_itm_utf16(bgn, end, literal));
	}
	public static int Find_fwd_non_int(byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					break;
				default:
					return i;
			}
		}
		return end;
	}
}
