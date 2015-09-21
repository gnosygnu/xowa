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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Html_wtr {
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	private List_adp nde_stack = List_adp_.new_();
	public byte Atr_quote() {return atr_quote;} public Html_wtr Atr_quote_(byte v) {atr_quote = v; return this;} private byte atr_quote = Byte_ascii.Quote;
	public Html_wtr Nde_full_atrs(byte[] tag, byte[] text, boolean text_escape, byte[]... atrs) {
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
	public Html_wtr Nde_full(byte[] tag, byte[] text) {
		Nde_bgn_hdr(tag);
		Txt(text);
		Nde_end();
		return this;
	}
	public Html_wtr Txt_mid(byte[] src, int bgn, int end) {bfr.Add_mid(src, bgn, end); return this;}
	public Html_wtr Txt_byte(byte v) {bfr.Add_byte(v); return this;}
	public Html_wtr Txt_raw(byte[] v) {bfr.Add(v); return this;}
	public Html_wtr Txt(byte[] v) {
		if (v != null) {
			bfr.Add(Html_utl.Escape_html_as_bry(v));
		}
		return this;
	}
	public Html_wtr Nde_bgn_hdr(byte[] name) {
		this.Nde_bgn(name);
		this.Nde_end_hdr();
		return this;
	}
	public Html_wtr Nde_bgn(byte[] name) {
		bfr.Add_byte(Byte_ascii.Lt);
		bfr.Add(name);
		nde_stack.Add(name);
		return this;
	}
	public Html_wtr Atr(byte[] key, byte[] val) {
		Write_atr_bry(bfr, Bool_.Y, atr_quote, key, val);
		return this;
	}
	public Html_wtr Nde_end_inline() {
		bfr.Add_byte(Byte_ascii.Slash).Add_byte(Byte_ascii.Gt);
		List_adp_.Pop_last(nde_stack);
		return this;
	}
	public Html_wtr Nde_end_hdr() {
		bfr.Add_byte(Byte_ascii.Gt);
		return this;
	}
	public Html_wtr Nde_end() {
		byte[] name = (byte[])List_adp_.Pop_last(nde_stack);
		bfr.Add_byte(Byte_ascii.Lt).Add_byte(Byte_ascii.Slash);
		bfr.Add(name);
		bfr.Add_byte(Byte_ascii.Gt);
		return this;
	}
	public byte[] Xto_bry_and_clear() {return bfr.Xto_bry_and_clear();}
	public byte[] Xto_bry() {return bfr.Xto_bry();}
	public String Xto_str() {return bfr.Xto_str();}
	public static void Write_atr_bry(Bry_bfr bfr, byte[] key, byte[] val) {Write_atr_bry(bfr, Bool_.Y, Byte_ascii.Quote, key, val);}
	public static void Write_atr_bry(Bry_bfr bfr, boolean write_space, byte atr_quote, byte[] key, byte[] val) {
		if (Bry_.Len_eq_0(val)) return;	// don't write empty
		if (write_space) bfr.Add_byte_space();
		bfr.Add(key);
		bfr.Add_byte(Byte_ascii.Eq);
		bfr.Add_byte(atr_quote);
		Html_utl.Escape_html_to_bfr(bfr, val, 0, val.length, false, false, false, true, true);
		bfr.Add_byte(atr_quote);
	}
	public static void Write_atr_int(Bry_bfr bfr, byte[] key, int val) {Write_atr_int(bfr, Bool_.Y, Byte_ascii.Quote, key, val);}
	public static void Write_atr_int(Bry_bfr bfr, boolean write_space, byte atr_quote, byte[] key, int val) {
		if (write_space) bfr.Add_byte_space();
		bfr.Add(key);
		bfr.Add_byte(Byte_ascii.Eq);
		bfr.Add_byte(atr_quote);
		bfr.Add_int_variable(val);
		bfr.Add_byte(atr_quote);
	}
}
