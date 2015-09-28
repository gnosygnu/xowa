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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Mwh_atr_mgr_tst {
	private final Mwh_atr_mgr_fxt fxt = new Mwh_atr_mgr_fxt();
	@Test  public void Atr_utl_make() 	{
		// key="val"
		fxt.Test_atr_utl_make(Mwh_atr_itm.Qte_tid__qute, Mwh_atr_itm.Mask__valid__y, Mwh_atr_itm.Mask__repeated__n, Mwh_atr_itm.Mask__key_exists__y, Mwh_atr_itm.Mask__val_made__n, 42);
		// key=val key=v<nowiki/>al
		fxt.Test_atr_utl_make(Mwh_atr_itm.Qte_tid__none, Mwh_atr_itm.Mask__valid__y, Mwh_atr_itm.Mask__repeated__y, Mwh_atr_itm.Mask__key_exists__y, Mwh_atr_itm.Mask__val_made__y, 120);
	}
}
class Mwh_atr_mgr_fxt {
	public void Test_atr_utl_make(int qte_tid, boolean valid, boolean repeated, boolean key_exists, boolean val_made, int expd) {
		int atr_utl = Mwh_atr_itm.Calc_atr_utl(qte_tid, valid, repeated, key_exists, val_made);
		Tfds.Eq_int(expd, atr_utl);
		Tfds.Eq_int(qte_tid, Mwh_atr_itm.Calc_qte_tid(atr_utl));
		Tfds.Eq_bool(valid, (atr_utl & Mwh_atr_itm.Mask__valid) == Mwh_atr_itm.Mask__valid);
		Tfds.Eq_bool(repeated, (atr_utl & Mwh_atr_itm.Mask__repeated) == Mwh_atr_itm.Mask__repeated);
		Tfds.Eq_bool(key_exists, (atr_utl & Mwh_atr_itm.Mask__key_exists) == Mwh_atr_itm.Mask__key_exists);
		Tfds.Eq_bool(val_made, (atr_utl & Mwh_atr_itm.Mask__val_made) == Mwh_atr_itm.Mask__val_made);
	}
}
