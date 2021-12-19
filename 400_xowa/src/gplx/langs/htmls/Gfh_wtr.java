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
package gplx.langs.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Gfh_wtr {
	private BryWtr bfr = BryWtr.NewAndReset(255);
	private List_adp nde_stack = List_adp_.New();
	public byte Atr_quote() {return atr_quote;} public Gfh_wtr Atr_quote_(byte v) {atr_quote = v; return this;} private byte atr_quote = AsciiByte.Quote;
	public Gfh_wtr Nde_full_atrs(byte[] tag, byte[] text, boolean text_escape, byte[]... atrs) {
		Nde_bgn(tag);
		int atrs_len = atrs.length;
		for (int i = 0; i < atrs_len; i += 2) {
			byte[] key = atrs[i];
			byte[] val = atrs[i + 1];
			Atr(key, val);
		}
		Nde_end_hdr();
		if (text_escape)
			Txt(text);
		else
			bfr.Add(text);
		Nde_end();
		return this;
	}
	public Gfh_wtr Nde_full(byte[] tag, byte[] text) {
		Nde_bgn_hdr(tag);
		Txt(text);
		Nde_end();
		return this;
	}
	public Gfh_wtr Txt_mid(byte[] src, int bgn, int end) {bfr.AddMid(src, bgn, end); return this;}
	public Gfh_wtr Txt_byte(byte v) {bfr.AddByte(v); return this;}
	public Gfh_wtr Txt_raw(byte[] v) {bfr.Add(v); return this;}
	public Gfh_wtr Txt(byte[] v) {
		if (v != null) {
			bfr.Add(Gfh_utl.Escape_html_as_bry(v));
		}
		return this;
	}
	public Gfh_wtr Nde_bgn_hdr(byte[] name) {
		this.Nde_bgn(name);
		this.Nde_end_hdr();
		return this;
	}
	public Gfh_wtr Nde_bgn(byte[] name) {
		bfr.AddByte(AsciiByte.Lt);
		bfr.Add(name);
		nde_stack.Add(name);
		return this;
	}
	public Gfh_wtr Atr(byte[] key, byte[] val) {
		Write_atr_bry(bfr, BoolUtl.Y, atr_quote, key, val);
		return this;
	}
	public Gfh_wtr Nde_end_inline() {
		bfr.AddByte(AsciiByte.Slash).AddByte(AsciiByte.Gt);
		List_adp_.PopLast(nde_stack);
		return this;
	}
	public Gfh_wtr Nde_end_hdr() {
		bfr.AddByte(AsciiByte.Gt);
		return this;
	}
	public Gfh_wtr Nde_end() {
		byte[] name = (byte[])List_adp_.PopLast(nde_stack);
		bfr.AddByte(AsciiByte.Lt).AddByte(AsciiByte.Slash);
		bfr.Add(name);
		bfr.AddByte(AsciiByte.Gt);
		return this;
	}
	public byte[] To_bry_and_clear() {return bfr.ToBryAndClear();}
	public byte[] Xto_bry() {return bfr.ToBry();}
	public String Xto_str() {return bfr.ToStr();}
	public static void Write_atr_bry(BryWtr bfr, byte[] key, byte[] val) {Write_atr_bry(bfr, BoolUtl.Y, AsciiByte.Quote, key, val);}
	public static void Write_atr_bry(BryWtr bfr, boolean write_space, byte atr_quote, byte[] key, byte[] val) {
		if (BryUtl.IsNullOrEmpty(val)) return;	// don't write empty
		if (write_space) bfr.AddByteSpace();
		bfr.Add(key);
		bfr.AddByte(AsciiByte.Eq);
		bfr.AddByte(atr_quote);
		Gfh_utl.Escape_html_to_bfr(bfr, val, 0, val.length, false, false, false, true, true);
		bfr.AddByte(atr_quote);
	}
	public static void Write_atr_int(BryWtr bfr, byte[] key, int val) {Write_atr_int(bfr, BoolUtl.Y, AsciiByte.Quote, key, val);}
	public static void Write_atr_int(BryWtr bfr, boolean write_space, byte atr_quote, byte[] key, int val) {
		if (write_space) bfr.AddByteSpace();
		bfr.Add(key);
		bfr.AddByte(AsciiByte.Eq);
		bfr.AddByte(atr_quote);
		bfr.AddIntVariable(val);
		bfr.AddByte(atr_quote);
	}
}
