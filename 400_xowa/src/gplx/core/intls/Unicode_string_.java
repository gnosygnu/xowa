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
public class Unicode_string_ {
	public static Unicode_string New(String orig) {
		// null
		if (orig == null)
			return new Unicode_string_single(null, null, null, 0);

		// init bytes
		byte[] bytes = Bry_.new_u8(orig);
		int bytes_len = bytes.length;

		// init codes
		int[] codes = new int[bytes_len];
		int codes_len = 0;

		// loop
		int bytes_pos = 0;
		int chars_pos = 0;
		while (bytes_pos < bytes_len) {
			// set codes
			codes[codes_len] = Utf16_.Decode_to_int(bytes, bytes_pos);

			// increment
			int cur_byte_len = Utf8_.Len_of_char_by_1st_byte(bytes[bytes_pos]);
			bytes_pos += cur_byte_len;
			chars_pos += Utf8_.Len_of_char_by_bytes_len(cur_byte_len);
			codes_len += 1;
		}
		return codes_len == bytes_len 
			? (Unicode_string)new Unicode_string_single(orig, bytes, codes, codes_len)
			: (Unicode_string)new Unicode_string_multi (orig, bytes, bytes_len, codes, codes_len, chars_pos);
	}
}
