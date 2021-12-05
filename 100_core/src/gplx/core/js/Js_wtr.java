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
package gplx.core.js;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Int_;
import gplx.Long_;
import gplx.Object_;
import gplx.Type_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
public class Js_wtr {
	private final Bry_bfr bfr = Bry_bfr_.Reset(32);
	private int arg_idx = 0, ary_idx = 0;
	public byte Quote_char() {return quote_char;} public Js_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = AsciiByte.Quote;
	public void Clear()							{bfr.Clear();}
	public String To_str()						{return bfr.To_str();}
	public String To_str_and_clear()			{return bfr.To_str_and_clear();}
	public Js_wtr Func_init(String name) {return Func_init(Bry_.new_u8(name));}
	public Js_wtr Func_init(byte[] name) {
		bfr.Add(name).Add_byte(AsciiByte.ParenBgn);
		arg_idx = 0;
		return this;
	}
	public Js_wtr Func_term() {
		bfr.Add_byte(AsciiByte.ParenEnd).Add_byte_semic();
		return this;
	}
	public Js_wtr Prm_str(String v) {return Prm_bry(Bry_.new_u8(v));}
	public Js_wtr Prm_bry(byte[] v) {
		Prm_spr();
		Write_val(v);
		return this;
	}
	public Js_wtr Prm_obj_ary(Object[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			Object itm = ary[i];
			if (i != 0) bfr.Add_byte(AsciiByte.Comma);
			boolean val_needs_quotes = true;
			if 		(	Type_.Eq_by_obj(itm, BoolUtl.ClsRefType)
					||	Type_.Eq_by_obj(itm, Int_.Cls_ref_type)
					||	Type_.Eq_by_obj(itm, Long_.Cls_ref_type)
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
		bfr.Add_byte(AsciiByte.BrackBgn);
		return this;
	}
	public Js_wtr Ary_term() {
		bfr.Add_byte(AsciiByte.BrackEnd);
		return this;
	}
	public void Prm_spr() {
		if (arg_idx != 0) bfr.Add_byte(AsciiByte.Comma);
		++arg_idx;
	}
	private void Ary_spr() {
		if (ary_idx != 0) bfr.Add_byte(AsciiByte.Comma);
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
				case AsciiByte.Backslash:	// "\"	-> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
				case AsciiByte.Quote:
				case AsciiByte.Apos:		bfr.Add_byte(AsciiByte.Backslash).Add_byte(b); break;
				case AsciiByte.Nl:			bfr.Add_byte(AsciiByte.Backslash).Add_byte(AsciiByte.Ltr_n); break;	// "\n" -> "\\n"
				case AsciiByte.Cr:			break;// skip
				default:					bfr.Add_byte(b); break;
			}
		}
		bfr.Add_byte(quote_char);
	}
	private static final byte[] Keyword_return = Bry_.new_a7("return ");
}
