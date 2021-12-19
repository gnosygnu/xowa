/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.strings.unicodes;
import gplx.types.basics.utls.BryUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class Utf8UtlTest {
	private Utf8UtlTstr tstr = new Utf8UtlTstr();
	@Test public void Get_prv_char_pos0() {
		tstr.TestGetPrvCharPos0("abcd", 3);        // len=1; (note that bry.len = 4)
		tstr.TestGetPrvCharPos0("a", 0);           // len=1; short-String
		tstr.TestGetPrvCharPos0("abc¢", 3);        // len=2; (note that bry.len = 5)
		tstr.TestGetPrvCharPos0("abc€", 3);        // len=3; (note that bry.len = 6)
		tstr.TestGetPrvCharPos0("abc" + StringUtl.NewU8(ByteUtl.AryByInts(240, 164, 173, 162)), 3);        // len=4; (note that bry.len = 7)
	}
	@Test public void Increment_char_at_last_pos() {
		tstr.TestIncrementCharAtLastPos("a", "b");
		tstr.TestIncrementCharAtLastPos("abc", "abd");
		tstr.TestIncrementCharAtLastPos("É", "Ê");    // len=2
		tstr.TestIncrementCharAtLastPos("€", "₭");    // len=3
	}
//        @Test public void Increment_char_at_last_pos_exhaustive_check() {    // check all values; commented for perf
//            Bry_bfr bfr = Bry_bfr_.New();
//            int bgn = 32;
//            while (true) {
//                byte[] bgn_bry = Utf16_.Encode_int_to_bry(bgn);
//                int end = Utf8_.Increment_char(bgn);
//                if (end == Utf8_.Codepoint_max) break;
////                if (bgn > 1024 * 1024) break;
//                byte[] end_by_codepoint_next = Utf16_.Encode_int_to_bry(end);
//                byte[] end_by_increment_char = Utf8_.Increment_char_at_last_pos(bgn_bry);
//                if (!Bry_.Eq(end_by_codepoint_next, end_by_increment_char)) {
//                    Tfds.Write(bgn);
//                }
////                bfr    .Add_int_variable(bgn).Add_byte(Byte_ascii.Tab)
////                    .Add(bgn_bry).Add_byte(Byte_ascii.Tab)
////                    .Add(end_by_codepoint_next).Add_byte(Byte_ascii.Tab)
////                    .Add(end_by_increment_char).Add_byte(Byte_ascii.Tab)
////                    .Add_byte_nl()
////                    ;
//                bgn = end;
//                bgn_bry = end_by_codepoint_next;
//            }
//            Tfds.WriteText(bfr.To_str_and_clear());
//        }
}
class Utf8UtlTstr {
	public void TestGetPrvCharPos0(String srcStr, int expd) {
		byte[] srcBry = BryUtl.NewU8(srcStr);
		GfoTstr.Eq(expd, Utf8Utl.GetPrvCharPos0(srcBry, srcBry.length));
		GfoTstr.Eq(expd, Utf8Utl.GetPrvCharPos0Old(srcBry, srcBry.length - 1));
	}
	public void TestIncrementCharAtLastPos(String str, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(Utf8Utl.IncrementCharAtLastPos(BryUtl.NewU8(str))));}
}
