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
public class Int_flag_bldr__tst {
	private final Int_flag_bldr__fxt fxt = new Int_flag_bldr__fxt();
	@Test  public void Bld_pow_ary() {
		fxt.Test__bld_pow_ary(fxt.Make__ary(1, 1, 1, 1), fxt.Make__ary(8, 4, 2, 1));
		fxt.Test__bld_pow_ary(fxt.Make__ary(3, 2)      , fxt.Make__ary(4, 1));
	}
	@Test  public void To_int__1_1_1() {
		int[] seg_ary = fxt.Make__ary(1, 1, 1);
		fxt.Test__to_int(seg_ary						, fxt.Make__ary(0, 0, 0), 0);
		fxt.Test__to_int(seg_ary						, fxt.Make__ary(1, 1, 1), 7);
		fxt.Test__to_int(seg_ary						, fxt.Make__ary(1, 0, 0), 4);
		fxt.Test__to_int(seg_ary						, fxt.Make__ary(1, 1, 0), 6);
		fxt.Test__to_int(seg_ary						, fxt.Make__ary(0, 1, 1), 3);
	}
	@Test  public void To_int__2_3() {
		fxt.Test__to_int(fxt.Make__ary(2, 3)          , fxt.Make__ary(3, 7)      , 31);
		fxt.Test__to_int(fxt.Make__ary(1, 2, 3)       , fxt.Make__ary(1, 3, 7)   , 63);
	}
	@Test  public void To_int__11_4_5_5_6() {
		fxt.Test__to_int(fxt.Make__ary(11, 4, 5, 5, 6), fxt.Make__ary(2012, 6, 3, 23, 17), 2110135761);
		fxt.Test__to_int(fxt.Make__ary(11, 4, 5, 5, 6), fxt.Make__ary(2012, 6, 3, 23, 18), 2110135762);
	}
	@Test  public void To_int_ary() {
		fxt.Test__to_int_ary(fxt.Make__ary(1, 1, 1, 1)    ,         15, fxt.Make__ary(1, 1, 1, 1));
		fxt.Test__to_int_ary(fxt.Make__ary(3, 2)          ,         31, fxt.Make__ary(7, 3));
		fxt.Test__to_int_ary(fxt.Make__ary(3, 2, 1)       ,         63, fxt.Make__ary(7, 3, 1));
		fxt.Test__to_int_ary(fxt.Make__ary(12, 4, 5, 5, 6), 2110135761, fxt.Make__ary(2012, 6, 3, 23, 17));
		fxt.Test__to_int_ary(fxt.Make__ary(12, 4, 5, 5, 6), 2110135762, fxt.Make__ary(2012, 6, 3, 23, 18));
	}
	@Test  public void To_int_date_short() {
		fxt.Test__to_int_date_short("20120604 2359", 117843451);
		fxt.Test__to_int_date_short("20120604 2358", 117843450);
		fxt.Test__to_int_date_short("20120605 0000", 117843968);
	}
}
class Int_flag_bldr__fxt {
	public int[] Make__ary(int... v) {return v;}
	public void Test__bld_pow_ary(int[] seg_ary, int[] expd) {Tfds.Eq_ary_str(expd, Int_flag_bldr_.Bld_pow_ary(seg_ary));}
	public void Test__to_int(int[] seg_ary, int[] val_ary, int expd) {
		int[] pow_ary = Int_flag_bldr_.Bld_pow_ary(seg_ary);
		Tfds.Eq(expd, Int_flag_bldr_.To_int(pow_ary, val_ary));
		int[] actl_val_ary = Int_flag_bldr_.To_int_ary(pow_ary, expd);
		Tfds.Eq_ary(val_ary, actl_val_ary);
	}
	public void Test__to_int_ary(int[] seg_ary, int val, int[] expd) {
		int[] pow_ary = Int_flag_bldr_.Bld_pow_ary(seg_ary);
		Tfds.Eq_ary_str(expd, Int_flag_bldr_.To_int_ary(pow_ary, val));
	}
	public void Test__to_int_date_short(String date_str, int expd) {
		DateAdp date = DateAdp_.parse_fmt(date_str, "yyyyMMdd HHmm");
		int date_int = Int_flag_bldr_.To_int_date_short(date.XtoSegAry());
		Tfds.Eq(expd, date_int);
		Tfds.Eq(date_str, Int_flag_bldr_.To_date_short(date_int).XtoStr_fmt("yyyyMMdd HHmm"));
	}
}
