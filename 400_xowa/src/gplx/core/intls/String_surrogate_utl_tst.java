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
import org.junit.*;
public class String_surrogate_utl_tst {
	@Before public void init() {fxt.Clear();} private String_surrogate_utl_fxt fxt = new String_surrogate_utl_fxt();
	@Test   public void Char_idx() {
		String test_str = "aé𡼾bî𡼾";
		fxt.Test_count_surrogates__char_idx			(test_str,  0, 1, 0,  1);		// a
		fxt.Test_count_surrogates__char_idx			(test_str,  0, 2, 0,  3);		// aé
		fxt.Test_count_surrogates__char_idx			(test_str,  0, 3, 1,  7);		// aé𡼾
		fxt.Test_count_surrogates__char_idx			(test_str,  7, 1, 0,  8);		// b
		fxt.Test_count_surrogates__char_idx			(test_str,  7, 2, 0, 10);		// bî
		fxt.Test_count_surrogates__char_idx			(test_str,  7, 3, 1, 14);		// bî𡼾
		fxt.Test_count_surrogates__char_idx			(test_str,  0, 6, 2, 14);		// aé𡼾bî𡼾
		fxt.Test_count_surrogates__char_idx			(test_str, 14, 7, 0, 14);		// PURPOSE: test out of bounds; DATE:2014-09-02
	}
	@Test   public void Codepoint_idx() {
		String test_str = "aé𡼾bî𡼾";
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  0, 1, 0,  1);		// a
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  0, 2, 0,  3);		// aé
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  0, 4, 1,  7);		// aé𡼾
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  7, 1, 0,  8);		// b
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  7, 2, 0, 10);		// bî
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  7, 4, 1, 14);		// bî𡼾
		fxt.Test_count_surrogates__codepoint_idx	(test_str,  0, 8, 2, 14);		// aé𡼾bî𡼾
	}
}
class String_surrogate_utl_fxt {
	private String_surrogate_utl codepoint_utl = new String_surrogate_utl();
	public void Clear() {}
	public void Test_count_surrogates__char_idx(String src_str, int bgn_byte, int char_idx, int expd_count, int expd_pos) {
		byte[] src_bry = Bry_.new_u8(src_str); int src_len = src_bry.length;
		Tfds.Eq(expd_count	, codepoint_utl.Count_surrogates__char_idx(src_bry, src_len, bgn_byte, char_idx));
		Tfds.Eq(expd_pos	, codepoint_utl.Byte_pos());
	}
	public void Test_count_surrogates__codepoint_idx(String src_str, int bgn_byte, int char_idx, int expd_count, int expd_pos) {
		byte[] src_bry = Bry_.new_u8(src_str); int src_len = src_bry.length;
		Tfds.Eq(expd_count	, codepoint_utl.Count_surrogates__codepoint_idx1(src_bry, src_len, bgn_byte, char_idx), "count");
		Tfds.Eq(expd_pos	, codepoint_utl.Byte_pos(), "pos");
	}
}
