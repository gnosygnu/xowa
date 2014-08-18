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
package gplx.xowa; import gplx.*;
public class Xop_xatr_itm {
	public static final byte Tid_null = 0, Tid_invalid = 1, Tid_repeat = 2, Tid_key_val = 3, Tid_key_only = 4;	// NOTE: id order is important; see below;
	public byte Tid() {return tid;} private byte tid; 
	public void Tid_to_repeat_() {tid = Tid_repeat;}
	public void Tid_to_invalid_() {tid = Tid_invalid;}
	public boolean Tid_is_key_only() {return tid == Tid_key_only;}
	public int Key_bgn() {return key_bgn;} private int key_bgn;
	public int Key_end() {return key_end;} private int key_end;
	public byte[] Key_bry() {return key_bry;} public Xop_xatr_itm Key_bry_(byte[] v) {key_bry = v; return this;} private byte[] key_bry;
	public byte[] Val_bry() {return val_bry;} public Xop_xatr_itm Val_bry_(byte[] v) {val_bry = v; return this;} private byte[] val_bry;
	public void Key_rng_(int key_bgn, int key_end) {this.key_bgn = key_bgn; this.key_end = key_end;}
	public byte Key_tid() {return key_tid;} public Xop_xatr_itm Key_tid_(byte v) {key_tid = v; return this;} private byte key_tid;
	public int Val_bgn() {return val_bgn;} private int val_bgn;
	public int Val_end() {return val_end;} private int val_end;
	public int Atr_bgn() {return atr_bgn;} private int atr_bgn;
	public int Atr_end() {return atr_end;} private int atr_end;
	public int Eq_pos() {return eq_pos;} private int eq_pos;
	public boolean Invalid() {return tid < Tid_key_val;}	// NOTE: Tid order is important
	public byte Quote_byte() {return quote_byte;} private byte quote_byte;
	public byte[] Val_as_bry(byte[] src) {if (val_bry == null) val_bry = Bry_.Mid(src, val_bgn, val_end); return val_bry;}	// NOTE: val_bry is cached
	public byte[] Val_as_bry__blank_to_null(byte[] src) {byte[] rv = Val_as_bry(src); return Bry_.Len_eq_0(rv) ? null : rv;}
	public int Val_as_int_or(byte[] src, int or) {return val_bry == null ? Bry_.Xto_int_or_lax(src, val_bgn, val_end, or) : Bry_.Xto_int_or(val_bry, or);}
	public boolean Val_as_bool_by_int(byte[] src) {return Val_as_int_or(src, 0) == 1;}
	public boolean Val_as_bool(byte[] src) {return Bry_.Eq(Bry_.Lower_ascii(Val_as_bry(src)), Bool_.True_bry);}
	public static Xop_xatr_itm[] Xatr_parse(Xoa_app app, Xop_xnde_atr_parser parser, Hash_adp_bry hash, Xow_wiki wiki, byte[] src, Xop_xnde_tkn xnde) {
		Xop_xatr_itm[] xatr_ary = app.Xatr_parser().Parse(app.Msg_log(), src, xnde.Atrs_bgn(), xnde.Atrs_end());
		for (int i = 0; i < xatr_ary.length; i++) {
			Xop_xatr_itm xatr = xatr_ary[i];
			if (xatr.Invalid()) continue;
			Object xatr_key_obj = hash.Get_by_mid(src, xatr.Key_bgn(), xatr.Key_end());
			parser.Xatr_parse(wiki, src, xatr, xatr_key_obj);
		}
		return xatr_ary;
	}
	public Xop_xatr_itm(int atr_bgn, int atr_end) {
		this.tid = Tid_invalid; this.atr_bgn = atr_bgn; this.atr_end = atr_end;
	}
	public Xop_xatr_itm(byte quote_byte, int atr_bgn, int atr_end, int key_bgn, int key_end) {
		this.tid = Tid_key_only; this.quote_byte = quote_byte; this.atr_bgn = atr_bgn; this.atr_end = atr_end; this.key_bgn = key_bgn; this.key_end = key_end; this.val_bgn = key_bgn; this.val_end = key_end;
	}
	public Xop_xatr_itm(byte quote_byte, int atr_bgn, int atr_end, int key_bgn, int key_end, int val_bgn, int val_end, int eq_pos) {
		this.tid = Tid_key_val; this.quote_byte = quote_byte; this.atr_bgn = atr_bgn; this.atr_end = atr_end; this.key_bgn = key_bgn; this.key_end = key_end; this.val_bgn = val_bgn; this.val_end = val_end; this.eq_pos = eq_pos;
	}
	public static final Xop_xatr_itm[] Ary_empty = new Xop_xatr_itm[0];
	public static final byte Key_tid_generic = 0, Key_tid_id = 1, Key_tid_style = 2, Key_tid_role = 3;
}
