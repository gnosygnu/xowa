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
}
