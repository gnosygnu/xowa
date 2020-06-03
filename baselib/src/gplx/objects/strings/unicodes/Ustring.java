/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.strings.unicodes;

import gplx.objects.errs.Err_;
import gplx.objects.strings.String_;
import gplx.objects.strings.char_sources.Char_source;

public interface Ustring extends Char_source {
	int Len_in_chars();
	int Map_data_to_char(int pos);
	int Map_char_to_data(int pos);
}
class Ustring_single implements Ustring { // 1 char == 1 codepoint
	public Ustring_single(String src, int src_len) {
		this.src = src;
		this.src_len = src_len;
	}
	public String Src() {return src;} private final String src;
	public int Len_in_chars() {return src_len;} private final int src_len;
	public int Len_in_data() {return src_len;}
	public String Substring(int bgn, int end) {return src.substring(bgn, end);}
	public byte[] SubstringAsBry(int bgn, int end) {
		String rv = src.substring(bgn, end);
		try {
			return rv.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("failed to get bytes; src=" + src);
		}
	}
	public int Index_of(Char_source find, int bgn) {return src.indexOf(find.Src(), bgn);} 
	public boolean Eq(int lhs_bgn, Char_source rhs, int rhs_bgn, int rhs_end) {
		if (src_len < lhs_bgn + rhs_end || rhs.Len_in_data() < rhs_bgn + rhs_end)
			return false;
		while ( --rhs_end>=0 ) 
			if (this.Get_data(lhs_bgn++) != rhs.Get_data(rhs_bgn++))
				return false;
		return true;
	}
	public int Get_data(int i) {return String_.Char_at(src, i);}
	public int Map_data_to_char(int i) {if (i < 0 || i > src_len) throw Err_.New_fmt("invalid idx; idx={0} src={1}", i, src); return i;}
	public int Map_char_to_data(int i) {if (i < 0 || i > src_len) throw Err_.New_fmt("invalid idx; idx={0} src={1}", i, src); return i;}
}
class Ustring_codepoints implements Ustring {
	private final int[] codes;
	public Ustring_codepoints(String src, int chars_len, int codes_len) {
		// set members
		this.src = src;
		this.chars_len = chars_len;
		this.codes_len = codes_len;

		// make codes[]
		this.codes = new int[codes_len];
		int code_idx = 0;
		for (int i = 0; i < chars_len; i++) {
			char c = src.charAt(i); 
			if (c >= Ustring_.Surrogate_hi_bgn && c <= Ustring_.Surrogate_hi_end) { // character is 1st part of surrogate-pair
				i++;
				if (i == chars_len) throw Err_.New_fmt("invalid surrogate pair found; src={0}", src);
				int c2 = src.charAt(i); 
				codes[code_idx++] = Ustring_.Surrogate_cp_bgn + (c - Ustring_.Surrogate_hi_bgn) * Ustring_.Surrogate_range + (c2 - Ustring_.Surrogate_lo_bgn);
			}
			else {
				codes[code_idx++] = c;
			}
		}
	}
	public String Src() {return src;} private final String src;
	public String Substring(int bgn, int end) {
		int len = 0;
		for (int i = bgn; i < end; i++) {
			int code = codes[i];
			len += code >= Ustring_.Surrogate_cp_bgn && code <= Ustring_.Surrogate_cp_end ? 2 : 1;
		}
		char[] rv = new char[len];
		int rv_idx = 0;
		for (int i = bgn; i < end; i++) {
			int code = codes[i];
			if (code >= Ustring_.Surrogate_cp_bgn && code <= Ustring_.Surrogate_cp_end) {
				rv[rv_idx++] = (char)((code - 0x10000) / 0x400 + 0xD800);
				rv[rv_idx++] = (char)((code - 0x10000) % 0x400 + 0xDC00);
			}
			else {
				rv[rv_idx++] = (char)code;
			}
		}
		return new String(rv);
	}
	public byte[] SubstringAsBry(int bgn, int end) {
		String rv = src.substring(bgn, end);
		try {
			return rv.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("failed to get bytes; src=" + src);
		}
	}
	public int Index_of(Char_source find, int bgn) {
		int find_len = find.Len_in_data();
		int codes_len = codes.length;
		for (int i = bgn; i < codes.length; i++) {
			boolean found = true;
			for (int j = 0; j < find_len; j++) {
				int codes_idx = i + j; 
				if (codes_idx >= codes_len) {
					found = false;
					break;					
				}
				if (codes[codes_idx] != find.Get_data(j)) {
					found = false;
					break;
				}
			}
			if (found == true)
				return i;
		}
		return -1;
	}
	public boolean Eq(int lhs_bgn, Char_source rhs, int rhs_bgn, int rhs_end) {
		if (this.Len_in_data() < lhs_bgn + rhs_end || rhs.Len_in_data() < rhs_bgn + rhs_end)
			return false;
		while ( --rhs_end>=0 ) 
			if ((this.Get_data(lhs_bgn++) != rhs.Get_data(rhs_bgn++)))
				return false;
		return true;
	}
	public int Len_in_chars() {return chars_len;} private final int chars_len;
	public int Len_in_data() {return codes_len;} private final int codes_len;
	public int Get_data(int i) {return codes[i];}
	public int Map_data_to_char(int code_pos) {
		if (code_pos == codes_len) return chars_len; // if char_pos is chars_len, return codes_len; allows "int end = u.Map_char_to_data(str_len)"

		// sum all items before requested pos
		int rv = 0;
		for (int i = 0; i < code_pos; i++) {
			rv += codes[i] < Ustring_.Surrogate_cp_bgn ? 1 : 2;
		}
		return rv;
	}
	public int Map_char_to_data(int char_pos) {
		if (char_pos == chars_len) return codes_len; // if char_pos is chars_len, return codes_len; allows "int end = u.Map_char_to_data(str_len)"

		// sum all items before requested pos
		int rv = 0;
		for (int i = 0; i < char_pos; i++) {
			char c = src.charAt(i); 
			if (c >= Ustring_.Surrogate_hi_bgn && c <= Ustring_.Surrogate_hi_end){ // Surrogate_hi
				if (i == char_pos - 1) // char_pos is Surrogate_lo; return -1 since Surrogate_lo doesn't map to a code_pos
					return -1;
			}
			else
				rv++;
		}
		return rv;
	}
}
