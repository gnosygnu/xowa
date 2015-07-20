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
package gplx.core.json; import gplx.*; import gplx.core.*;
public class Json_wtr {
	private final Bry_bfr bfr = Bry_bfr.new_(255);
	private int indent;
	private boolean nde_itm_is_first;
	private boolean ary_itm_is_first;
	public byte Opt_quote_byte() {return opt_quote_byte;} public Json_wtr Opt_quote_byte_(byte v) {opt_quote_byte = v; return this;} private byte opt_quote_byte = Byte_ascii.Quote;
	public boolean Opt_ws() {return opt_ws;} public Json_wtr Opt_ws_(boolean v) {opt_ws = v; return this;} private boolean opt_ws = true;
	public byte[] To_bry_and_clear() {return bfr.Xto_bry_and_clear();}
	public String To_str_and_clear() {return bfr.Xto_str_and_clear();}
	public Bry_bfr Bfr() {return bfr;}
	public Json_wtr Clear() {
		indent = 0;
		nde_itm_is_first = ary_itm_is_first = true;
		return this;
	}
	public Json_wtr Doc_bgn() {return Add_grp_bgn(Sym_nde_bgn);}
	public Json_wtr Doc_end() {Add_grp_end(Bool_.Y, Sym_nde_end); return Add_nl();}
	public void Indent_(int v) {this.indent = v;}
	public Json_wtr Nde_bgn(String nde) {return Nde_bgn(Bry_.new_u8(nde));}
	public Json_wtr Nde_bgn(byte[] nde) {
		Add_indent_itm(nde_itm_is_first);
		Add_key(nde);
		Add_nl();
		return Add_grp_bgn(Sym_nde_bgn);
	}
	public Json_wtr Nde_end() {Add_grp_end(Bool_.Y, Sym_nde_end); return Add_nl();}
	public Json_wtr Ary_bgn(String nde) {
		Add_indent_itm(nde_itm_is_first);
		Add_key(Bry_.new_u8(nde));
		Add_nl();
		ary_itm_is_first = true;
		return Add_grp_bgn(Sym_ary_bgn);
	}
	public Json_wtr Ary_itm_str(String itm) {
		Add_indent_itm(ary_itm_is_first);
		Add_itm_bry(Bry_.new_u8(itm));
		Add_nl();
		ary_itm_is_first = false;
		return this;
	}
	public Json_wtr Ary_end() {Add_grp_end(Bool_.N, Sym_ary_end); return Add_nl();}
	public Json_wtr Kv_bool(String key, boolean val)	{return Kv_bool(Bry_.new_u8(key), val);}
	public Json_wtr Kv_bool(byte[] key, boolean val)	{return Kv_raw(key, val ? Bool_.True_bry : Bool_.False_bry);}
	public Json_wtr Kv_int(String key, int val)		{return Kv_raw(Bry_.new_u8(key), Int_.Xto_bry(val));}
	private Json_wtr Kv_raw(byte[] key, byte[] val) {
		Add_indent_itm(nde_itm_is_first);
		Add_key(key);
		bfr.Add(val);
		Add_nl();
		nde_itm_is_first = false;
		return this;
	}
	public Json_wtr Kv_str(String key, String val) {return Kv_bry(Bry_.new_u8(key), Bry_.new_u8(val));}
	public Json_wtr Kv_str(byte[] key, String val) {return Kv_bry(key, Bry_.new_u8(val));}
	public Json_wtr Kv_bry(String key, byte[] val) {return Kv_bry(Bry_.new_u8(key), val);}
	public Json_wtr Kv_bry(byte[] key, byte[] val) {
		Add_indent_itm(nde_itm_is_first);
		Add_key(key);
		Add_itm_bry(val);
		Add_nl();
		nde_itm_is_first = false;
		return this;
	}
	public Json_wtr Kv_bfr(String key, Bry_bfr val) {
		Add_indent_itm(nde_itm_is_first);
		Add_key(Bry_.new_u8(key));
		Add_itm_bry(val.Bfr(), 0, val.Len());
		Add_nl();
		nde_itm_is_first = false;
		val.Clear();
		return this;
	}
	private Json_wtr Add_grp_bgn(byte[] grp_sym) {
		Add_indent(0);
		bfr.Add(grp_sym);
		++indent;
		nde_itm_is_first = true;
		return this;
	}
	private Json_wtr Add_grp_end(boolean grp_is_nde, byte[] grp_sym) {
		--indent;
		if ((grp_is_nde && nde_itm_is_first) || (!grp_is_nde && ary_itm_is_first))
			Add_nl();
		Add_indent(0);
		nde_itm_is_first = false;
		bfr.Add(grp_sym);
		return this;
	}
	private Json_wtr Add_key(byte[] bry) {
		Add_itm_bry(bry);
		bfr.Add_byte_colon();
		return this;
	}
	private void Add_itm_bry(byte[] bry) {Add_itm_bry(bry, 0, bry.length);}
	private void Add_itm_bry(byte[] bry, int bgn, int end) {
		bfr.Add_byte(opt_quote_byte);
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Backslash:	bfr.Add_byte(Byte_ascii.Backslash).Add_byte(b); break; // "\"	-> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
				case Byte_ascii.Quote:		bfr.Add_byte(Byte_ascii.Backslash).Add_byte(b); break;
				case Byte_ascii.Apos:		bfr.Add_byte(b); break;
				case Byte_ascii.Nl:			bfr.Add_byte_repeat(Byte_ascii.Backslash, 2).Add_byte(Byte_ascii.Ltr_n); break;	// "\n" -> "\\n"
				case Byte_ascii.Cr:			break;// skip
				default:					bfr.Add_byte(b); break;
			}
		}
		bfr.Add_byte(opt_quote_byte);
	}
	private void Add_indent_itm(boolean v) {
		if (v) {
			bfr.Add_byte_space();
		}
		else {
			Add_indent(-1);
			bfr.Add(Sym_itm_spr);
		}
	}
	private Json_wtr Add_nl() {
		if (opt_ws)
			bfr.Add_byte_nl();
		return this;
	}
	private void Add_indent(int adj) {
		int level = indent + adj;
		if (opt_ws && level > 0)
			bfr.Add_byte_repeat(Byte_ascii.Space, level * 2);
	}
	private static final byte[]
	  Sym_nde_bgn	= Bry_.new_a7("{")
	, Sym_nde_end	= Bry_.new_a7("}")
	, Sym_ary_bgn	= Bry_.new_a7("[")
	, Sym_ary_end	= Bry_.new_a7("]")
	, Sym_itm_spr	= Bry_.new_a7(", ")
	;
}
