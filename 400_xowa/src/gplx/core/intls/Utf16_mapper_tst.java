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
import org.junit.*; import gplx.core.tests.*;
public class Utf16_mapper_tst {
	private final    Utf16_mapper_fxt fxt = new Utf16_mapper_fxt();
	@Test  public void A() {
		fxt.Test__map("a¢€𤭢"
			, Int_ary_.New( 0,  1, -1,  2, -1, -1,  3, -1, -1, -1,  4)
			, Int_ary_.New( 0,  1,  3,  6, 10, -1, -1, -1, -1, -1, -1)
			, Int_ary_.New( 0,  1,  2,  3, -1,  4, -1, -1, -1, -1, -1)
			, Int_ary_.New( 0,  1,  2,  3,  5, -1, -1, -1, -1, -1, -1)
			);
	}
}
class Utf16_mapper_fxt {
	public void Test__map(String src_str, int[] expd_code_for_byte, int[] expd_byte_for_code, int[] expd_code_for_char, int[] expd_char_for_code) {
		byte[] src_bry = Bry_.new_u8(src_str);
		int src_len = src_bry.length;
		Utf16_mapper mapper = new Utf16_mapper(src_str, src_bry, src_len);
		Test__ary(mapper, src_len, Utf16_mapper.Dims_code_for_byte, expd_code_for_byte);
		Test__ary(mapper, src_len, Utf16_mapper.Dims_byte_for_code, expd_byte_for_code);
		Test__ary(mapper, src_len, Utf16_mapper.Dims_code_for_char, expd_code_for_char);
		Test__ary(mapper, src_len, Utf16_mapper.Dims_char_for_code, expd_char_for_code);
	}
	private void Test__ary(Utf16_mapper mapper, int src_len, int dim_type, int[] expd) {
		int actl_len = src_len + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			int v = -1;
			switch (dim_type) {
				case Utf16_mapper.Dims_code_for_byte:
					v = mapper.Get_code_for_byte_or_neg1(i);
					break;
				case Utf16_mapper.Dims_byte_for_code:
					v = mapper.Get_byte_for_code_or_neg1(i);
					break;
				case Utf16_mapper.Dims_code_for_char:
					v = mapper.Get_code_for_char_or_neg1(i);
					break;
				case Utf16_mapper.Dims_char_for_code:
					v = mapper.Get_char_for_code_or_neg1(i);
					break;
			}
			actl[i] = v;
		}
		Gftest.Eq__ary(expd, actl, Int_.To_str(dim_type));
	}
}
