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
package gplx.xowa.parsers.htmls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.arrays.IntAryUtl;
import org.junit.*;
public class Mwh_atr_mgr_tst {
	private final Mwh_atr_mgr_fxt fxt = new Mwh_atr_mgr_fxt();
	@Test public void Atr_utl_make() {
		// key="val"
		fxt.Test_atr_utl_make(Mwh_atr_itm_.Qte_tid__qute, Mwh_atr_itm_.Mask__valid__y, Mwh_atr_itm_.Mask__repeated__n, Mwh_atr_itm_.Mask__key_exists__y, Mwh_atr_itm_.Mask__val_made__n, 42);
		// key=val key=v<nowiki/>al
		fxt.Test_atr_utl_make(Mwh_atr_itm_.Qte_tid__none, Mwh_atr_itm_.Mask__valid__y, Mwh_atr_itm_.Mask__repeated__y, Mwh_atr_itm_.Mask__key_exists__y, Mwh_atr_itm_.Mask__val_made__y, 120);
	}
	@Test public void Resize() {// PURPOSE:make sure that array gets resized without losing data; ISSUE#:579 DATE:2019-09-27
		Mwh_atr_mgr atr_mgr = new Mwh_atr_mgr(1);

		int[] expd = new int[] {2, 3, 4, 5};
		atr_mgr.Add(0, 1, true, true, true, 2, 3, 4, 5, BryUtl.Empty, 0, 0, 0, 0, BryUtl.Empty);
		GfoTstr.EqAry(expd, IntAryUtl.Mid(atr_mgr.Data_ary(), 3, 6));

		atr_mgr.Add(1, 0, true, true, true, 0, 0, 0, 0, BryUtl.Empty, 0, 0, 0, 0, BryUtl.Empty);
		GfoTstr.EqAry(expd, IntAryUtl.Mid(atr_mgr.Data_ary(), 3, 6));
	}
}
class Mwh_atr_mgr_fxt {
	public void Test_atr_utl_make(int qte_tid, boolean valid, boolean repeated, boolean key_exists, boolean val_made, int expd) {
		int atr_utl = Mwh_atr_itm_.Calc_atr_utl(qte_tid, valid, repeated, key_exists, val_made);
		GfoTstr.Eq(expd, atr_utl);
		GfoTstr.Eq(qte_tid, Mwh_atr_itm_.Calc_qte_tid(atr_utl));
		GfoTstr.Eq(valid, (atr_utl & Mwh_atr_itm_.Mask__valid) == Mwh_atr_itm_.Mask__valid);
		GfoTstr.Eq(repeated, (atr_utl & Mwh_atr_itm_.Mask__repeated) == Mwh_atr_itm_.Mask__repeated);
		GfoTstr.Eq(key_exists, (atr_utl & Mwh_atr_itm_.Mask__key_exists) == Mwh_atr_itm_.Mask__key_exists);
		GfoTstr.Eq(val_made, (atr_utl & Mwh_atr_itm_.Mask__val_made) == Mwh_atr_itm_.Mask__val_made);
	}
}
