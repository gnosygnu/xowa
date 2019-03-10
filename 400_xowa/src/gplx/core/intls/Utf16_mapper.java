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
package gplx.core.intls; import gplx.*; import gplx.core.*;
public class Utf16_mapper {
	private final    int[] ary;
	private final    int dim_len;
	public byte[] Src_bry() {return src_bry;} private final    byte[] src_bry;
	public String Src_str() {return src_str;} private final    String src_str;
	public int Len_in_codes() {return len_in_codes;} private int len_in_codes;
	public int Len_in_chars() {return len_in_chars;} private int len_in_chars;
	public int Get_code_for_byte_or_neg1(int idx) {return idx < dim_len ? ary[(dim_len * Dims_code_for_byte) + idx] : Invalid;}
	public int Get_byte_for_code_or_neg1(int idx) {return idx < dim_len ? ary[(dim_len * Dims_byte_for_code) + idx] : Invalid;}
	public int Get_code_for_char_or_neg1(int idx) {return idx < dim_len ? ary[(dim_len * Dims_code_for_char) + idx] : Invalid;}
	public int Get_char_for_code_or_neg1(int idx) {return idx < dim_len ? ary[(dim_len * Dims_char_for_code) + idx] : Invalid;}
	public int Get_code_for_byte_or_fail(int idx) {int rv = Get_code_for_byte_or_neg1(idx); if (idx == Invalid) throw Err_.new_wo_type("invalid idx", "src", src_bry, "type", "code_for_byte", "idx", idx); return rv;}
	public int Get_byte_for_code_or_fail(int idx) {int rv = Get_byte_for_code_or_neg1(idx); if (idx == Invalid) throw Err_.new_wo_type("invalid idx", "src", src_bry, "type", "byte_for_code", "idx", idx); return rv;}
	public int Get_code_for_char_or_fail(int idx) {int rv = Get_code_for_char_or_neg1(idx); if (idx == Invalid) throw Err_.new_wo_type("invalid idx", "src", src_bry, "type", "code_for_char", "idx", idx); return rv;}
	public int Get_char_for_code_or_fail(int idx) {int rv = Get_char_for_code_or_neg1(idx); if (idx == Invalid) throw Err_.new_wo_type("invalid idx", "src", src_bry, "type", "char_for_code", "idx", idx); return rv;}
	public Utf16_mapper(String src_str, byte[] src_bry, int src_bry_len) {
		// create ary
		this.src_str = src_str;
		this.src_bry = src_bry;
		this.dim_len = src_bry_len + 1; // +1 to capture end + 1
		int ary_len = dim_len * Dims_total;
		this.ary = new int[dim_len * Dims_total];
		for (int i = 0; i < ary_len; i++)
			ary[i] = Invalid;

		// init
		int pos_in_bytes = 0, pos_in_chars = 0, pos_in_codes = 0;

		// loop till EOS
		while (true) {
			// update
			ary[(dim_len * Dims_code_for_byte) + pos_in_bytes] = pos_in_codes;
			ary[(dim_len * Dims_byte_for_code) + pos_in_codes] = pos_in_bytes;
			ary[(dim_len * Dims_code_for_char) + pos_in_chars] = pos_in_codes;
			ary[(dim_len * Dims_char_for_code) + pos_in_codes] = pos_in_chars;

			// exit if EOS
			if (pos_in_bytes >= src_bry_len) break;

			// get lengths
			int cur_len_in_bytes = Utf8_.Len_of_char_by_1st_byte(src_bry[pos_in_bytes]);
			int cur_len_in_chars = cur_len_in_bytes == 4 ? 2 : 1; // NOTE: 3 bytes represent up to U+FFFF (65,536) which will fit in 1; REF:en.w:UTF-8; ISSUE#:377; DATE:2019-03-04

			// increment
			pos_in_bytes += cur_len_in_bytes;
			pos_in_chars += cur_len_in_chars;
			pos_in_codes += 1;
		}

		// set lens
		this.len_in_codes = pos_in_codes;
		this.len_in_chars = pos_in_chars;
	}

	public static final int
	  Invalid            = -1
	, Dims_total         = 4
	, Dims_code_for_byte = 0
	, Dims_byte_for_code = 1
	, Dims_code_for_char = 2
	, Dims_char_for_code = 3
	;
}
