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
public interface Unicode_string {
	boolean Tid_is_single();
	String Src_string();
	byte[] Src_bytes();
	int Len_codes();
	int Len_chars();
	int Len_bytes();
	int Val_codes(int i);
	int Pos_codes_to_bytes(int i);
	int Pos_codes_to_chars(int i);
	int Pos_bytes_to_codes(int i);
	int Pos_chars_to_codes(int i);
}
class Unicode_string_single implements Unicode_string { // 1 byte == 1 codepoint
	private final    int[] codes;
	public Unicode_string_single(String src_string, byte[] src_bytes, int[] codes, int codes_len) {
		this.src_string = src_string;
		this.src_bytes = src_bytes;
		this.codes = codes;
		this.codes_len = codes_len;
	}
	public boolean Tid_is_single() {return true;}
	public String Src_string() {return src_string;} private final    String src_string;
	public byte[] Src_bytes() {return src_bytes;} private final    byte[] src_bytes;
	public int Len_codes() {return codes_len;} private final    int codes_len;
	public int Len_chars() {return codes_len;}
	public int Len_bytes() {return codes_len;}
	public int Val_codes(int i) {return codes[i];}
	public int Pos_codes_to_bytes(int i) {if (i < 0 || i > codes_len) throw Err_.new_wo_type("invalid idx", "src", src_string, "idx", i); return i;}
	public int Pos_codes_to_chars(int i) {if (i < 0 || i > codes_len) throw Err_.new_wo_type("invalid idx", "src", src_string, "idx", i);return i;}
	public int Pos_bytes_to_codes(int i) {if (i < 0 || i > codes_len) throw Err_.new_wo_type("invalid idx", "src", src_string, "idx", i);return i;}
	public int Pos_chars_to_codes(int i) {if (i < 0 || i > codes_len) throw Err_.new_wo_type("invalid idx", "src", src_string, "idx", i);return i;}
}
