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
	}	void tst_XtoBitStr(int val, String expd) {Tfds.Eq(expd, Bit_.XtoBitStr(val));}
	@Test  public void Bld_pow_ary() {
		tst_Bld_pow_ary(ary_(1, 1, 1, 1), ary_(8, 4, 2, 1));
		tst_Bld_pow_ary(ary_(3, 2)      , ary_(4, 1));
	}	void tst_Bld_pow_ary(int[] seg_ary, int[] expd) {Tfds.Eq_ary_str(expd, Bit_.Bld_pow_ary(seg_ary));}
	@Test  public void Xto_int() {
		tst_Xto_int(ary_(1, 1, 1, 1)    , ary_(1, 1, 1, 1), 15);
		tst_Xto_int(ary_(1, 1, 1, 1)    , ary_(0, 0, 0, 0), 0);
		tst_Xto_int(ary_(1, 1, 1, 1)    , ary_(1, 0, 0, 1), 9);
		tst_Xto_int(ary_(1, 1, 1, 1)    , ary_(0, 1, 1, 0), 6);
		tst_Xto_int(ary_(3, 2)          , ary_(7, 3)      , 31);
		tst_Xto_int(ary_(3, 2, 1)       , ary_(7, 3, 1)   , 63);
		tst_Xto_int(ary_(11, 4, 5, 5, 6), ary_(2012, 6, 3, 23, 17), 2110135761);
		tst_Xto_int(ary_(11, 4, 5, 5, 6), ary_(2012, 6, 3, 23, 18), 2110135762);
	}
	private void tst_Xto_int(int[] seg_ary, int[] val_ary, int expd) {
		int[] pow_ary = Bit_.Bld_pow_ary(seg_ary);
		Tfds.Eq(expd, Bit_.Xto_int(pow_ary, val_ary));
	}
	@Test  public void Xto_intAry() {
		tst_Xto_intAry(ary_(1, 1, 1, 1)    ,         15, ary_(1, 1, 1, 1));
		tst_Xto_intAry(ary_(3, 2)          ,         31, ary_(7, 3));
		tst_Xto_intAry(ary_(3, 2, 1)       ,         63, ary_(7, 3, 1));
		tst_Xto_intAry(ary_(12, 4, 5, 5, 6), 2110135761, ary_(2012, 6, 3, 23, 17));
		tst_Xto_intAry(ary_(12, 4, 5, 5, 6), 2110135762, ary_(2012, 6, 3, 23, 18));
	}
	private void tst_Xto_intAry(int[] seg_ary, int val, int[] expd) {
		int[] pow_ary = Bit_.Bld_pow_ary(seg_ary);
		Tfds.Eq_ary_str(expd, Bit_.Xto_intAry(pow_ary, val));
	}
	int[] ary_(int... v) {return v;}
	@Test  public void Xto_int_date_short() {
		tst_Xto_int_date_short("20120604 2359", 117843451);
		tst_Xto_int_date_short("20120604 2358", 117843450);
		tst_Xto_int_date_short("20120605 0000", 117843968);
	}
	private void tst_Xto_int_date_short(String date_str, int expd) {
		DateAdp date = DateAdp_.parse_fmt(date_str, "yyyyMMdd HHmm");
		int date_int = Bit_.Xto_int_date_short(date.XtoSegAry());
		Tfds.Eq(expd, date_int);
		Tfds.Eq(date_str, Bit_.Xto_date_short(date_int).XtoStr_fmt("yyyyMMdd HHmm"));
	}
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
