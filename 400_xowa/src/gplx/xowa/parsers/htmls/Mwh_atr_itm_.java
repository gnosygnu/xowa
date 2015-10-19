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
public class Mwh_atr_itm_ {
	public static final Mwh_atr_itm[] Ary_empty = new Mwh_atr_itm[0];
	public static final int Atr_tid__invalid = 1, Atr_tid__repeat = 2, Atr_tid__pair = 4, Atr_tid__name = 8;	// NOTE: id order is important; see above;
	public static final int Qte_tid__none = 0, Qte_tid__apos = 1, Qte_tid__qute = 2;
	public static final int Mask__qte__none = 0, Mask__qte__apos = 1, Mask__qte_qute = 2;
	public static final int 
	  Mask__valid		= 8
	, Mask__repeated	= 16
	, Mask__key_exists	= 32
	, Mask__val_made	= 64
	;
	public static final boolean Mask__valid__n = false, Mask__valid__y = true;
	public static final boolean Mask__key_exists__n = false, Mask__key_exists__y = true;
	public static final boolean Mask__repeated__n = false, Mask__repeated__y = true;
	public static final boolean Mask__val_made__n = false, Mask__val_made__y = true;
	public static int Calc_atr_utl(int qte_tid, boolean valid, boolean repeated, boolean key_exists, boolean val_made) {
		int rv = qte_tid;
		if (valid)			rv |= Mwh_atr_itm_.Mask__valid;
		if (repeated)		rv |= Mwh_atr_itm_.Mask__repeated;
		if (key_exists)		rv |= Mwh_atr_itm_.Mask__key_exists;
		if (val_made)		rv |= Mwh_atr_itm_.Mask__val_made;
		return rv;
	}
	public static int Calc_qte_tid(int val) {
		return val & ((1 << 3) - 1);
	}
	public static byte Calc_qte_byte(int[] data_ary, int idx) {
		int val = data_ary[idx + Mwh_atr_mgr.Idx_atr_utl];
		int qte_tid = (val & ((1 << 3) - 1));
		return qte_tid == Qte_tid__apos ? Byte_ascii.Apos : Byte_ascii.Quote;
	}
	public static final byte Key_tid__generic = 0, Key_tid__id = 1, Key_tid__style = 2, Key_tid__role = 3;
}
