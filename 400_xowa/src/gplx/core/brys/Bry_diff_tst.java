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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Bry_diff_tst {
	@Before public void init() {} private final Bry_diff_fxt fxt = new Bry_diff_fxt();
	@Test   public void Diff_1st() {
		fxt.Test__diff_1st("a|b|c"	, "a|b|c"	, null		, null);
		fxt.Test__diff_1st("a|b|c"	, "a|b1|c"	, "b"		, "b1");
		fxt.Test__diff_1st("a|b|"	, "a|b|c"	, "<<EOS>>"	, "c");
		fxt.Test__diff_1st("a|b|c"	, "a|b|"	, "c"		, "<<EOS>>");
	}
	@Test   public void Diff_1st_show() {
		fxt.Test__diff_1st("a|b<c>d|e"	, "a|b<c>e|e"	, "<c>d", "<c>e");
	}
}
class Bry_diff_fxt {
	public void Test__diff_1st(String lhs, String rhs, String expd_lhs, String expd_rhs) {
		byte[] lhs_src = Bry_.new_u8(lhs);
		byte[] rhs_src = Bry_.new_u8(rhs);
		byte[][] actl = Bry_diff_.Diff_1st(lhs_src, 0, lhs_src.length, rhs_src, 0, rhs_src.length, Byte_ascii.Pipe_bry, Byte_ascii.Angle_bgn_bry, 255);
		if (expd_lhs == null && expd_rhs == null)
			Tfds.Eq_true(actl == null, "actl not null");
		else {
			Tfds.Eq_bry(Bry_.new_u8(expd_lhs), actl[0]);
			Tfds.Eq_bry(Bry_.new_u8(expd_rhs), actl[1]);
		}
	}
}
