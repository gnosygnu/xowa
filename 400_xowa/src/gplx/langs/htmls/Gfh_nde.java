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
public class Gfh_nde {
	public Gfh_nde(byte[] src, boolean tag_tid_is_inline, int tag_lhs_bgn, int tag_lhs_end, int tag_rhs_bgn, int tag_rhs_end, int name_bgn, int name_end, int[] cur_atrs, int atrs_idx) {
		this.src = src;
		this.tag_tid_is_inline = tag_tid_is_inline;
		this.tag_lhs_bgn = tag_lhs_bgn; this.tag_lhs_end = tag_lhs_end; this.tag_rhs_bgn = tag_rhs_bgn; this.tag_rhs_end = tag_rhs_end; this.name_bgn = name_bgn; this.name_end = name_end;
		if (atrs_idx > 0) {
			atrs = new int[atrs_idx];
			for (int i = 0; i < atrs_idx; i++)
				atrs[i] =  cur_atrs[i];
			atrs_len = atrs_idx / 5;
		}
	}
	public byte[] Src() {return src;} private byte[] src;
	public int[] Atrs() {return atrs;} private int[] atrs = Int_.Ary_empty;
	public int Atrs_len() {return atrs_len;} private int atrs_len;
	public boolean Tag_tid_is_inline() {return tag_tid_is_inline;} private boolean tag_tid_is_inline;
	public int Tag_lhs_bgn() {return tag_lhs_bgn;} public Gfh_nde Tag_lhs_bgn_(int v) {tag_lhs_bgn = v; return this;} private int tag_lhs_bgn;
	public int Tag_lhs_end() {return tag_lhs_end;} public Gfh_nde Tag_lhs_end_(int v) {tag_lhs_end = v; return this;} private int tag_lhs_end;
	public int Tag_rhs_bgn() {return tag_rhs_bgn;} public Gfh_nde Tag_rhs_bgn_(int v) {tag_rhs_bgn = v; return this;} private int tag_rhs_bgn;
	public int Tag_rhs_end() {return tag_rhs_end;} public Gfh_nde Tag_rhs_end_(int v) {tag_rhs_end = v; return this;} private int tag_rhs_end;
	public int Name_bgn() {return name_bgn;} public Gfh_nde Name_bgn_(int v) {name_bgn = v; return this;} private int name_bgn;
	public int Name_end() {return name_end;} public Gfh_nde Name_end_(int v) {name_end = v; return this;} private int name_end;
	public void Clear() {tag_lhs_bgn = tag_rhs_bgn = -1;}
	public String Atrs_val_by_key_str(String find_key_str) {return String_.new_u8(Atrs_val_by_key_bry(Bry_.new_u8(find_key_str)));}
	public byte[] Atrs_val_by_key_bry(byte[] find_key_bry) {
		for (int i = 0; i < atrs_len; i ++) {
			int atrs_idx = i * 5;
			int atr_key_bgn = atrs[atrs_idx + 1];
			int atr_key_end = atrs[atrs_idx + 2];
			if (Bry_.Match(src, atr_key_bgn, atr_key_end, find_key_bry))
				return Atrs_vals_by_pos(src, atrs[atrs_idx + 0], atrs[atrs_idx + 3], atrs[atrs_idx + 4]);
		}
		return null;
	}
	byte[] Atrs_vals_by_pos(byte[] src, int quote_byte, int bgn, int end) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		boolean dirty = false;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Backslash:
					if (!dirty) {dirty = true; tmp_bfr.Add_mid(src, bgn, i);}
					++i;
					tmp_bfr.Add_byte(src[i]);
					break;
				default:
					if (b == quote_byte) {
						byte next_byte = src[i + 1];
						if (next_byte == b) {
							if (!dirty) {dirty = true; tmp_bfr.Add_mid(src, bgn, i);}
							++i;
							tmp_bfr.Add_byte(src[i]);							
						}
					}
					else {
						if (dirty)
							tmp_bfr.Add_byte(b);
					}
					break;
			}
		}
		return dirty ? tmp_bfr.To_bry_and_clear() : Bry_.Mid(src, bgn, end);
	}
	public byte[] Data(byte[] src) {
		return Bry_.Mid(src, tag_lhs_end, tag_rhs_bgn);
	}
}
//	class Xoh_atr {
//		public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
//		public byte[] Val_bry() {return val_bry;} private byte[] val_bry;
//		public int Key_bgn() {return key_bgn;} private int key_bgn;
//		public int Key_end() {return key_end;} private int key_end;
//		public int Val_bgn() {return val_bgn;} private int val_bgn;
//		public int Val_end() {return val_end;} private int val_end;
//		public byte Val_quote_tid() {return val_quote_tid;} private byte val_quote_tid;
//	}
