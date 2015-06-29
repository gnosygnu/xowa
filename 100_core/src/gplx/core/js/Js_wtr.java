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
package gplx.core.js; import gplx.*; import gplx.core.*;
public class Js_wtr {
	private final Bry_bfr bfr = Bry_bfr.reset_(255);
	private int arg_idx = 0, ary_idx = 0;
	public byte Quote_char() {return quote_char;} public Js_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = Byte_ascii.Quote;
	public void Clear()							{bfr.Clear();}
	public String To_str()						{return bfr.Xto_str();}
	public String To_str_and_clear()			{return bfr.Xto_str_and_clear();}
	public Js_wtr Func_init(String name) {return Func_init(Bry_.new_u8(name));}
	public Js_wtr Func_init(byte[] name) {
		bfr.Add(name).Add_byte(Byte_ascii.Paren_bgn);
		arg_idx = 0;
		return this;
	}
	public Js_wtr Func_term() {
		bfr.Add_byte(Byte_ascii.Paren_end).Add_byte_semic();
		return this;
	}
	public Js_wtr Prm_bry(byte[] bry) {
		Prm_spr();
		Write_val(bry);
		return this;
	}
	public Js_wtr Prm_obj_ary(Object[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			Object itm = ary[i];
			if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
			boolean val_needs_quotes = true;
			if 		(	ClassAdp_.Eq_typeSafe(itm, Bool_.Cls_ref_type)
					||	ClassAdp_.Eq_typeSafe(itm, Int_.Cls_ref_type)
					||	ClassAdp_.Eq_typeSafe(itm, Long_.Cls_ref_type)
				) {
				val_needs_quotes = false;
			}
			if (val_needs_quotes)
				Write_val(Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(itm)));
			else
				bfr.Add_obj_strict(itm);
		}
		return this;
	}
	public Js_wtr Ary_init() {
		ary_idx = 0;
		bfr.Add_byte(Byte_ascii.Brack_bgn);
		return this;
	}
	public Js_wtr Ary_term() {
		bfr.Add_byte(Byte_ascii.Brack_end);
		return this;
	}
	public void Prm_spr() {
		if (arg_idx != 0) bfr.Add_byte(Byte_ascii.Comma);
		++arg_idx;
	}
	private void Ary_spr() {
		if (ary_idx != 0) bfr.Add_byte(Byte_ascii.Comma);
		++ary_idx;
	}
	public Js_wtr Ary_bry(byte[] bry) {
		Ary_spr();
		Write_val(bry);
		return this;
	}
	private Js_wtr Write_keyword_return() {bfr.Add(Keyword_return); return this;}
	public Js_wtr Write_statement_return_func(String func, Object... args) {
		this.Write_keyword_return();
		this.Func_init(func);
		this.Prm_obj_ary(args);
		this.Func_term();
		return this;
	}
	public void Write_val(byte[] bry) {
		bfr.Add_byte(quote_char);
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Backslash:	// "\"	-> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
				case Byte_ascii.Quote:
				case Byte_ascii.Apos:		bfr.Add_byte(Byte_ascii.Backslash).Add_byte(b); break;
				case Byte_ascii.Nl:			bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Ltr_n); break;	// "\n" -> "\\n"
				case Byte_ascii.Cr:			break;// skip
				default:					bfr.Add_byte(b); break;
			}
		}
		bfr.Add_byte(quote_char);
	}
	private static final byte[] Keyword_return = Bry_.new_a7("return ");
}
