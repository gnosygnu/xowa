/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.js;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Js_wtr {
	private final BryWtr bfr = BryWtr.NewAndReset(32);
	private int arg_idx = 0, ary_idx = 0;
	public byte Quote_char() {return quote_char;} public Js_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = AsciiByte.Quote;
	public void Clear()                            {bfr.Clear();}
	public String To_str()                        {return bfr.ToStr();}
	public String To_str_and_clear()            {return bfr.ToStrAndClear();}
	public Js_wtr Func_init(String name) {return Func_init(BryUtl.NewU8(name));}
	public Js_wtr Func_init(byte[] name) {
		bfr.Add(name).AddByte(AsciiByte.ParenBgn);
		arg_idx = 0;
		return this;
	}
	public Js_wtr Func_term() {
		bfr.AddByte(AsciiByte.ParenEnd).AddByteSemic();
		return this;
	}
	public Js_wtr Prm_str(String v) {return Prm_bry(BryUtl.NewU8(v));}
	public Js_wtr Prm_bry(byte[] v) {
		Prm_spr();
		Write_val(v);
		return this;
	}
	public Js_wtr Prm_obj_ary(Object[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			Object itm = ary[i];
			if (i != 0) bfr.AddByte(AsciiByte.Comma);
			boolean val_needs_quotes = true;
			if         (    ClassUtl.EqByObj(BoolUtl.ClsRefType, itm)
					||    ClassUtl.EqByObj(IntUtl.ClsRefType, itm)
					||    ClassUtl.EqByObj(LongUtl.ClsRefType, itm)
				) {
				val_needs_quotes = false;
			}
			if (val_needs_quotes)
				Write_val(BryUtl.NewU8(ObjectUtl.ToStrOrNullMark(itm)));
			else
				bfr.AddObjStrict(itm);
		}
		return this;
	}
	public Js_wtr Ary_init() {
		ary_idx = 0;
		bfr.AddByte(AsciiByte.BrackBgn);
		return this;
	}
	public Js_wtr Ary_term() {
		bfr.AddByte(AsciiByte.BrackEnd);
		return this;
	}
	public void Prm_spr() {
		if (arg_idx != 0) bfr.AddByte(AsciiByte.Comma);
		++arg_idx;
	}
	private void Ary_spr() {
		if (ary_idx != 0) bfr.AddByte(AsciiByte.Comma);
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
		bfr.AddByte(quote_char);
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Backslash:    // "\"    -> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
				case AsciiByte.Quote:
				case AsciiByte.Apos:        bfr.AddByte(AsciiByte.Backslash).AddByte(b); break;
				case AsciiByte.Nl:            bfr.AddByte(AsciiByte.Backslash).AddByte(AsciiByte.Ltr_n); break;    // "\n" -> "\\n"
				case AsciiByte.Cr:            break;// skip
				default:                    bfr.AddByte(b); break;
			}
		}
		bfr.AddByte(quote_char);
	}
	private static final byte[] Keyword_return = BryUtl.NewA7("return ");
}
