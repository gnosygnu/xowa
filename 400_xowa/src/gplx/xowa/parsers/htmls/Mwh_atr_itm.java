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
public class Mwh_atr_itm {
	public Mwh_atr_itm
	( byte[] src, boolean valid, boolean repeated, boolean key_exists, int atr_bgn, int atr_end
	, int key_bgn, int key_end, byte[] key_bry
	, int val_bgn, int val_end, byte[] val_bry
	, int eql_pos, int qte_tid
	) {
		this.src = src;
		this.valid = valid; this.repeated = repeated; this.key_exists = key_exists;
		this.atr_bgn = atr_bgn; this.atr_end = atr_end;
		this.key_bgn = key_bgn; this.key_end = key_end; this.key_bry = key_bry;
		this.val_bgn = val_bgn; this.val_end = val_end; this.val_bry = val_bry;
		this.eql_pos = eql_pos; this.qte_tid = qte_tid;
	}
	public byte[] Src() {return src;} private final byte[] src;
	public boolean Valid() {return valid;} private final boolean valid;
	public boolean Key_exists() {return key_exists;} private final boolean key_exists;
	public boolean Repeated() {return repeated;} private final boolean repeated;
	public int Atr_bgn() {return atr_bgn;} private int atr_bgn;
	public int Atr_end() {return atr_end;} private int atr_end;
	public int Key_bgn() {return key_bgn;} private final int key_bgn;
	public int Key_end() {return key_end;} private final int key_end;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte Key_tid() {return key_tid;} public Mwh_atr_itm Key_tid_(byte v) {key_tid = v; return this;} private byte key_tid;
	public int Val_bgn() {return val_bgn;} private final int val_bgn;
	public int Val_end() {return val_end;} private final int val_end;
	public byte[] Val_bry() {return val_bry;} private byte[] val_bry;
	public int Eql_pos() {return eql_pos;} private final int eql_pos;
	public int Qte_tid() {return qte_tid;} private final int qte_tid;
	public Mwh_atr_itm Atr_rng(int bgn, int end) {this.atr_bgn = bgn; this.atr_end = end; return this;}
	public String Val_as_str() {return String_.new_u8(Val_as_bry());}
	public byte[] Val_as_bry() {if (val_bry == null) val_bry = Bry_.Mid(src, val_bgn, val_end); return val_bry;}	// NOTE: val_bry is cached
	public byte[] Val_as_bry__blank_to_null() {byte[] rv = Val_as_bry(); return Bry_.Len_eq_0(rv) ? null : rv;}
	public int Val_as_int_or(int or) {return val_bry == null ? Bry_.To_int_or__lax(src, val_bgn, val_end, or) : Bry_.To_int_or(val_bry, or);}
	public boolean Val_as_bool_by_int() {return Val_as_int_or(0) == 1;}
	public boolean Val_as_bool() {return Bry_.Eq(Bry_.Lcase__all(Val_as_bry()), Bool_.True_bry);}
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
		if (valid)			rv |= Mwh_atr_itm.Mask__valid;
		if (repeated)		rv |= Mwh_atr_itm.Mask__repeated;
		if (key_exists)		rv |= Mwh_atr_itm.Mask__key_exists;
		if (val_made)		rv |= Mwh_atr_itm.Mask__val_made;
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
//		public static final byte Key_tid_generic = 0, Key_tid_id = 1, Key_tid_style = 2, Key_tid_role = 3;
}
