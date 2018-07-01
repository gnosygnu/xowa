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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Mwh_atr_mgr_tst {
	private final Mwh_atr_mgr_fxt fxt = new Mwh_atr_mgr_fxt();
	@Test  public void Atr_utl_make() 	{
		// key="val"
		fxt.Test_atr_utl_make(Mwh_atr_itm_.Qte_tid__qute, Mwh_atr_itm_.Mask__valid__y, Mwh_atr_itm_.Mask__repeated__n, Mwh_atr_itm_.Mask__key_exists__y, Mwh_atr_itm_.Mask__val_made__n, 42);
		// key=val key=v<nowiki/>al
		fxt.Test_atr_utl_make(Mwh_atr_itm_.Qte_tid__none, Mwh_atr_itm_.Mask__valid__y, Mwh_atr_itm_.Mask__repeated__y, Mwh_atr_itm_.Mask__key_exists__y, Mwh_atr_itm_.Mask__val_made__y, 120);
	}
}
class Mwh_atr_mgr_fxt {
	public void Test_atr_utl_make(int qte_tid, boolean valid, boolean repeated, boolean key_exists, boolean val_made, int expd) {
		int atr_utl = Mwh_atr_itm_.Calc_atr_utl(qte_tid, valid, repeated, key_exists, val_made);
		Tfds.Eq_int(expd, atr_utl);
		Tfds.Eq_int(qte_tid, Mwh_atr_itm_.Calc_qte_tid(atr_utl));
		Tfds.Eq_bool(valid, (atr_utl & Mwh_atr_itm_.Mask__valid) == Mwh_atr_itm_.Mask__valid);
		Tfds.Eq_bool(repeated, (atr_utl & Mwh_atr_itm_.Mask__repeated) == Mwh_atr_itm_.Mask__repeated);
		Tfds.Eq_bool(key_exists, (atr_utl & Mwh_atr_itm_.Mask__key_exists) == Mwh_atr_itm_.Mask__key_exists);
		Tfds.Eq_bool(val_made, (atr_utl & Mwh_atr_itm_.Mask__val_made) == Mwh_atr_itm_.Mask__val_made);
	}
}
