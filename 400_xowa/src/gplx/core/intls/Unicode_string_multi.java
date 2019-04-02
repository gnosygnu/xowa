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
class Unicode_string_multi implements Unicode_string {
	private final    int[] codes;
	private final    int[] codes_to_bytes;
	private final    int[] codes_to_chars;
	private final    int[] bytes_to_chars;
	private final    int[] bytes_to_codes;
	private final    int[] chars_to_codes;

	public Unicode_string_multi(String src, byte[] bytes, int bytes_len, int[] codes, int codes_len, int chars_len) {
		// set member vars
		this.src = src;
		this.bytes = bytes;
		this.bytes_len = bytes_len;
		this.codes = codes;
		this.codes_len = codes_len;
		this.chars_len = chars_len;

		// init maps
		this.codes_to_bytes = new int[codes_len + Adj_end];
		this.codes_to_chars = new int[codes_len + Adj_end];
		this.bytes_to_codes = New_int_ary(bytes_len);
		this.bytes_to_chars = New_int_ary(bytes_len);
		this.chars_to_codes = New_int_ary(chars_len);

		// init loop
		int codes_pos = 0;
		int bytes_pos = 0;
		int chars_pos = 0;

		// loop till EOS
		while (true) {
			// update
			codes_to_bytes[codes_pos] = bytes_pos;
			codes_to_chars[codes_pos] = chars_pos;
			bytes_to_chars[bytes_pos] = chars_pos;
			bytes_to_codes[bytes_pos] = codes_pos;
			chars_to_codes[chars_pos] = codes_pos;

			if (bytes_pos == bytes_len) break;

			// increment
			int cur_byte_len = Utf8_.Len_of_char_by_1st_byte(bytes[bytes_pos]);
			bytes_pos += cur_byte_len;
			chars_pos += Utf8_.Len_of_char_by_bytes_len(cur_byte_len);
			codes_pos += 1;
		}
	}
	public boolean Tid_is_single() {return false;}
	public String Src_string() {return src;} private final    String src;
	public byte[] Src_bytes() {return bytes;} private final    byte[] bytes;
	public int Len_codes() {return codes_len;} private final    int codes_len;
	public int Len_chars() {return chars_len;} private final    int chars_len;
	public int Len_bytes() {return bytes_len;} private final    int bytes_len;
	public int Val_codes(int i) {return codes[i];}
	public int Pos_codes_to_bytes(int i) {return codes_to_bytes[i];}
	public int Pos_codes_to_chars(int i) {return codes_to_chars[i];}
	public int Pos_bytes_to_chars(int i) {int rv = bytes_to_chars[i]; if (rv == Invalid) throw Err_.new_wo_type("invalid i", "src", src, "type", "bytes_to_chars", "i", i); return rv;}
	public int Pos_bytes_to_codes(int i) {int rv = bytes_to_codes[i]; if (rv == Invalid) throw Err_.new_wo_type("invalid i", "src", src, "type", "bytes_to_codes", "i", i); return rv;}
	public int Pos_chars_to_codes(int i) {int rv = chars_to_codes[i]; if (rv == Invalid) throw Err_.new_wo_type("invalid i", "src", src, "type", "chars_to_codes", "i", i); return rv;}

	private static final int Invalid = -1, Adj_end = 1; // +1 to store last pos as len of String; needed for regex which returns match.Find_end() which will be len of String; EX: abc -> [0, 1, 2, 3]
	private static int[] New_int_ary(int len) {
		int rv_len = len + Adj_end;
		int[] rv = new int[rv_len];
		for (int i = 0; i < rv_len; i++)
			rv[i] = Invalid;
		return rv;
	}
}
