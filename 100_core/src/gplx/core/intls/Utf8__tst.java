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
public class Utf8__tst {
	private Utf8__fxt fxt = new Utf8__fxt();
	@Test  public void Get_prv_char_pos0() {
		fxt.Test__Get_prv_char_pos0("abcd", 3);        // len=1; (note that bry.len = 4)
		fxt.Test__Get_prv_char_pos0("a", 0);           // len=1; short-String
		fxt.Test__Get_prv_char_pos0("abc¢", 3);        // len=2; (note that bry.len = 5)
		fxt.Test__Get_prv_char_pos0("abc€", 3);        // len=3; (note that bry.len = 6)
		fxt.Test__Get_prv_char_pos0("abc" + String_.new_u8(Byte_.Ary_by_ints(240, 164, 173, 162)), 3);		// len=4; (note that bry.len = 7)
	}
	@Test  public void Increment_char_at_last_pos() {
		fxt.Test_Increment_char_at_last_pos("a", "b");
		fxt.Test_Increment_char_at_last_pos("abc", "abd");
		fxt.Test_Increment_char_at_last_pos("É", "Ê");	// len=2
		fxt.Test_Increment_char_at_last_pos("€", "₭");	// len=3
	}
//		@Test  public void Increment_char_at_last_pos_exhaustive_check() {	// check all values; commented for perf
//			Bry_bfr bfr = Bry_bfr_.New();
//			int bgn = 32;
//			while (true) {
//				byte[] bgn_bry = Utf16_.Encode_int_to_bry(bgn);
//				int end = Utf8_.Increment_char(bgn);
//				if (end == Utf8_.Codepoint_max) break;
////				if (bgn > 1024 * 1024) break;
//				byte[] end_by_codepoint_next = Utf16_.Encode_int_to_bry(end);
//				byte[] end_by_increment_char = Utf8_.Increment_char_at_last_pos(bgn_bry);
//				if (!Bry_.Eq(end_by_codepoint_next, end_by_increment_char)) {
//					Tfds.Write(bgn);
//				}				
////				bfr	.Add_int_variable(bgn).Add_byte(Byte_ascii.Tab)
////					.Add(bgn_bry).Add_byte(Byte_ascii.Tab)
////					.Add(end_by_codepoint_next).Add_byte(Byte_ascii.Tab)
////					.Add(end_by_increment_char).Add_byte(Byte_ascii.Tab)
////					.Add_byte_nl()
////					;
//				bgn = end;
//				bgn_bry = end_by_codepoint_next;
//			}
//			Tfds.WriteText(bfr.To_str_and_clear());
//		}
}
class Utf8__fxt {
	public void Test__Get_prv_char_pos0(String src_str, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Tfds.Eq(expd, Utf8_.Get_prv_char_pos0    (src_bry, src_bry.length));
		Tfds.Eq(expd, Utf8_.Get_prv_char_pos0_old(src_bry, src_bry.length - 1));
	}
	public void Test_Increment_char_at_last_pos(String str, String expd) {
		Tfds.Eq(expd, String_.new_u8(Utf8_.Increment_char_at_last_pos(Bry_.new_u8(str))));
	}
}
