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
public class Bit__tst {
	@Before public void init() {fxt.Clear();} private Bit__fxt fxt = new Bit__fxt();
	@Test  public void XtoBitStr() {
		tst_XtoBitStr(  0, "00000000");
		tst_XtoBitStr(  1, "00000001");
		tst_XtoBitStr(  2, "00000010");
		tst_XtoBitStr(  3, "00000011");
		tst_XtoBitStr(255, "11111111");
	}	void tst_XtoBitStr(int val, String expd) {Tfds.Eq(expd, Bit_.ToBitStr(val));}
	@Test   public void Shift_lhs() {// simple: shift 1 bit
		fxt.Test_shift_lhs(1, 1,  2);
		fxt.Test_shift_lhs(2, 1,  4);
		fxt.Test_shift_lhs(3, 1,  6);
		fxt.Test_shift_lhs(4, 1,  8);
	}
	@Test   public void Shift_rhs() {
		fxt.Test_shift_rhs(2, 1,  1);
		fxt.Test_shift_rhs(4, 1,  2);
		fxt.Test_shift_rhs(6, 1,  3);
		fxt.Test_shift_rhs(8, 1,  4);
	}
	@Test   public void Shift_lhs_to_int() {
		int[] shift_ary = Int_.Ary(0, 3, 5);
		fxt.Test_shift_lhs_to_int(shift_ary, Int_.Ary(0, 0, 0),  0);
		fxt.Test_shift_lhs_to_int(shift_ary, Int_.Ary(7, 0, 0),  7);	// 1st 3 bits
		fxt.Test_shift_lhs_to_int(shift_ary, Int_.Ary(0, 3, 0), 24);	// 2nd 2 bits
		fxt.Test_shift_lhs_to_int(shift_ary, Int_.Ary(0, 0, 1), 32);	// 3rd 1 bit
		fxt.Test_shift_lhs_to_int(shift_ary, Int_.Ary(7, 3, 1), 63);	// many bits
	}
	@Test   public void Shift_rhs_to_ary() {
		int[] shift_ary = Int_.Ary(0, 3, 5);
		fxt.Test_shift_rhs_to_ary(shift_ary,  0, Int_.Ary(0, 0, 0));
		fxt.Test_shift_rhs_to_ary(shift_ary,  7, Int_.Ary(7, 0, 0));	// 1st 3 bits
		fxt.Test_shift_rhs_to_ary(shift_ary, 24, Int_.Ary(0, 3, 0));	// 2nd 2 bits
		fxt.Test_shift_rhs_to_ary(shift_ary, 32, Int_.Ary(0, 0, 1));	// 3rd 1 bit
		fxt.Test_shift_rhs_to_ary(shift_ary, 63, Int_.Ary(7, 3, 1));	// many bits
	}
}
class Bit__fxt {
	public void Clear() {}
	public void Test_shift_lhs(int val, int shift, int expd) {Tfds.Eq(expd, Bit_.Shift_lhs(val, shift));}
	public void Test_shift_rhs(int val, int shift, int expd) {Tfds.Eq(expd, Bit_.Shift_rhs(val, shift));}
	public void Test_shift_lhs_to_int(int[] shift_ary, int[] val_ary, int expd) {Tfds.Eq(expd, Bit_.Shift_lhs_to_int(shift_ary, val_ary));}
	public void Test_shift_rhs_to_ary(int[] shift_ary, int val, int[] expd_ary) {
		int[] actl_ary = Int_.Ary(0, 0, 0);
		Bit_.Shift_rhs_to_ary(actl_ary, shift_ary, val);
		Tfds.Eq_ary(expd_ary, actl_ary);
	}
}
