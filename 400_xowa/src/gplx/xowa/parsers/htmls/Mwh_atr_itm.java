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
	public boolean Invalid() {return repeated || !valid;} 
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
	public byte Qte_byte() {
		switch (qte_tid) {
			case Mwh_atr_itm_.Qte_tid__none:	return Byte_ascii.Null;
			case Mwh_atr_itm_.Qte_tid__apos:	return Byte_ascii.Apos;
			case Mwh_atr_itm_.Qte_tid__qute:	return Byte_ascii.Quote;
			default:							throw Err_.new_unhandled(qte_tid);
		}
	}
	public Mwh_atr_itm Atr_rng(int bgn, int end) {this.atr_bgn = bgn; this.atr_end = end; return this;}
	public void Key_bry_(byte[] v) {this.key_bry = v;}
	public void Val_bry_(byte[] v) {this.val_bry = v;}
	public String Val_as_str() {return String_.new_u8(Val_as_bry());}
	public byte[] Val_as_bry() {if (val_bry == null) val_bry = Bry_.Mid(src, val_bgn, val_end); return val_bry;}	// NOTE: val_bry is cached
	public byte[] Val_as_bry__blank_to_null() {byte[] rv = Val_as_bry(); return Bry_.Len_eq_0(rv) ? null : rv;}
	public int Val_as_int_or(int or) {return val_bry == null ? Bry_.To_int_or__lax(src, val_bgn, val_end, or) : Bry_.To_int_or(val_bry, or);}
	public boolean Val_as_bool_by_int() {return Val_as_int_or(0) == 1;}
	public boolean Val_as_bool() {return Bry_.Eq(Bry_.Lcase__all(Val_as_bry()), Bool_.True_bry);}
}
