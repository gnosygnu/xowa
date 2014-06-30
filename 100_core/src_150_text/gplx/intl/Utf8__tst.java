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
public class Utf8__tst {
	private Utf8__fxt fxt = new Utf8__fxt();
	@Test  public void Get_pos0_of_char_bwd() {
		fxt.Test_Get_pos0_of_char_bwd("abcd", 3);		// len=1; (note that bry.len = 4)
		fxt.Test_Get_pos0_of_char_bwd("a", 0);			// len=1; short-String
		fxt.Test_Get_pos0_of_char_bwd("abc¢", 3);		// len=2; (note that bry.len = 5)
		fxt.Test_Get_pos0_of_char_bwd("abc€", 3);		// len=3; (note that bry.len = 6)
		fxt.Test_Get_pos0_of_char_bwd("abc" + String_.new_utf8_(Byte_.Ary_by_ints(240, 164, 173, 162)), 3);		// len=4; (note that bry.len = 7)
	}
	@Test  public void Increment_char_at_last_pos() {
		fxt.Test_Increment_char_at_last_pos("a", "b");
		fxt.Test_Increment_char_at_last_pos("abc", "abd");
		fxt.Test_Increment_char_at_last_pos("É", "Ê");	// len=2
		fxt.Test_Increment_char_at_last_pos("€", "₭");	// len=3
	}
//		@Test  public void Increment_char_at_last_pos_exhaustive_check() {	// check all values; commented for perf
//			Bry_bfr bfr = Bry_bfr.new_();
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
//			Tfds.WriteText(bfr.XtoStrAndClear());
//		}
}
class Utf8__fxt {
	public void Test_Get_pos0_of_char_bwd(String str, int expd) {
		byte[] bry = Bry_.new_utf8_(str);
		int pos = bry.length - 1;	// always start from last char
		Tfds.Eq(expd, Utf8_.Get_pos0_of_char_bwd(bry, pos));
	}
	public void Test_Increment_char_at_last_pos(String str, String expd) {
		Tfds.Eq(expd, String_.new_utf8_(Utf8_.Increment_char_at_last_pos(Bry_.new_utf8_(str))));
	}
}
