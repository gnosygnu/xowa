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
package gplx.core.xmls; import gplx.*; import gplx.core.*;
public class Gfo_xml_wtr {
	private final Bry_bfr bfr = Bry_bfr.reset_(255);
	private byte quote_byte = Byte_ascii.Quote;
	private byte[] quote_escape = Bry_quote_2_escape;
	private String nde_cur = null;
	public void Quote_(boolean apos) {
		if (apos) {
			this.quote_byte = Byte_ascii.Apos;
			this.quote_escape = Bry_quote_1_escape;
		}
		else {
			this.quote_byte = Byte_ascii.Quote;
			this.quote_escape = Bry_quote_2_escape;
		}
	}
	public Gfo_xml_wtr Nde_lhs_bgn(String v) {
		this.nde_cur = v;
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add_str_utf8(nde_cur);
		return this;
	}
	public Gfo_xml_wtr Nde_lhs_end() {
		bfr.Add_byte(Byte_ascii.Angle_end);
		return this;
	}
	public Gfo_xml_wtr Nde_lhs(String v) {this.Nde_lhs_bgn(v); this.Nde_lhs_end(); return this;}
	public Gfo_xml_wtr Nde_rhs() {
		bfr.Add(Bry_nde_rhs_bgn).Add_str_utf8(nde_cur).Add_byte(Byte_ascii.Angle_end);	// EX: </node>
		this.nde_cur = null;
		return this;
	}
	public Gfo_xml_wtr Atr_bgn(String key) {
		bfr.Add_byte_space().Add_str_utf8(key).Add_byte(Byte_ascii.Eq).Add_byte(quote_byte);
		return this;
	}
	public Gfo_xml_wtr Atr_val_str_a7(String v)		{bfr.Add_str_ascii(v); return this;}
	public Gfo_xml_wtr Atr_val_str_u8(String v)		{bfr.Add_str_utf8 (v); return this;}
	public Gfo_xml_wtr Atr_val_bry		(byte[] v)	{bfr.Add(v); return this;}
	public Gfo_xml_wtr Atr_val_int		(int v)		{bfr.Add_int_variable(v); return this;}
	public Gfo_xml_wtr Atr_end() {
		bfr.Add_byte(quote_byte);
		return this;
	}
	public Gfo_xml_wtr Atr_kv_int(String key, int val)			{return Atr_kv_bry(key, Int_.Xto_bry(val));}
	public Gfo_xml_wtr Atr_kv_str_a7(String key, String val)	{return Atr_kv_bry(key, Bry_.new_ascii_(val));}
	public Gfo_xml_wtr Atr_kv_str_u8(String key, String val)	{return Atr_kv_bry(key, Bry_.new_utf8_(val));}
	public Gfo_xml_wtr Atr_kv_bry(String key, byte[] val) {
		bfr.Add_byte_space().Add_str_utf8(key);
		bfr.Add_byte(Byte_ascii.Eq);
		Atr_val_quote(val);
		return this;
	}
	private void Atr_val_quote(byte[] val_bry) {
		bfr.Add_byte(quote_byte);
		bfr.Add_bry_escape(quote_byte, quote_escape, val_bry, 0, val_bry.length);
		bfr.Add_byte(quote_byte);
	}
	public void Txt_bry(byte[] txt) {
		bfr.Add(txt);
	}

	private static final byte[]
	  Bry_nde_rhs_bgn		= Bry_.new_ascii_("</")
//		, Bry_nde_inline		= Bry_.new_ascii_("/>")
	, Bry_quote_1_escape	= Bry_.new_ascii_("&apos;")
	, Bry_quote_2_escape	= Bry_.new_ascii_("&quot;")
	;
}
