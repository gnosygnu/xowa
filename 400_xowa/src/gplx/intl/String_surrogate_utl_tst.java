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
package gplx.intl; import gplx.*;
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
		byte[] src_bry = Bry_.new_utf8_(src_str); int src_len = src_bry.length;
		Tfds.Eq(expd_count	, codepoint_utl.Count_surrogates__char_idx(src_bry, src_len, bgn_byte, char_idx));
		Tfds.Eq(expd_pos	, codepoint_utl.Byte_pos());
	}
	public void Test_count_surrogates__codepoint_idx(String src_str, int bgn_byte, int char_idx, int expd_count, int expd_pos) {
		byte[] src_bry = Bry_.new_utf8_(src_str); int src_len = src_bry.length;
		Tfds.Eq(expd_count	, codepoint_utl.Count_surrogates__codepoint_idx1(src_bry, src_len, bgn_byte, char_idx), "count");
		Tfds.Eq(expd_pos	, codepoint_utl.Byte_pos(), "pos");
	}
}
