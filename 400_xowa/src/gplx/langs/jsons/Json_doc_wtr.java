/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import gplx.Double_;
import gplx.Object_;
import gplx.String_;
import gplx.core.encoders.Hex_utl_;
import gplx.objects.strings.unicodes.Ustring;
import gplx.objects.strings.unicodes.UstringUtl;

public class Json_doc_wtr {
	private int indent = -2;
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public void Opt_unicode_y_() {opt_unicode = true;} private boolean opt_unicode;
	public Json_doc_wtr Indent() {return Indent(indent);}
	private Json_doc_wtr Indent(int v) {if (v > 0) bfr.Add_byte_repeat(AsciiByte.Space, v); return this;}
	public Json_doc_wtr Indent_add() {indent += 2; return this;}
	public Json_doc_wtr Indent_del() {indent -= 2; return this;}
	public Json_doc_wtr Nde_bgn() {Indent_add();	Indent(); bfr.Add_byte(AsciiByte.CurlyBgn).Add_byte_nl(); return this;}
	public Json_doc_wtr Nde_end() {					Indent(); bfr.Add_byte(AsciiByte.CurlyEnd).Add_byte_nl(); Indent_del(); return this;}
	public Json_doc_wtr Ary_bgn() {Indent_add();	Indent(); bfr.Add_byte(AsciiByte.BrackBgn).Add_byte_nl(); return this;}
	public Json_doc_wtr Ary_end() {					Indent(); bfr.Add_byte(AsciiByte.BrackEnd).Add_byte_nl(); Indent_del(); return this;}
	public Json_doc_wtr New_line() {bfr.Add_byte_nl(); return this;}
	public Json_doc_wtr Str(byte[] v) {
		if (v == null)
			bfr.Add(Object_.Bry__null);
		else {
			bfr.Add_byte(AsciiByte.Quote);
			if (opt_unicode) {
				Ustring ustr = UstringUtl.NewCodepoints(String_.new_u8(v));
				int ustr_len = ustr.LenInData();
				for (int i = 0; i < ustr_len; i++) {
					int cp = ustr.GetData(i);
					Write_str_codepoint(bfr, cp);
				}
			}
			else {
				bfr.Add_bry_escape(AsciiByte.Quote, Escaped__quote, v, 0, v.length);
			}
			bfr.Add_byte(AsciiByte.Quote);
		}
		return this;
	}
	private void Write_str_codepoint(Bry_bfr bfr, int val) {
		switch (val) { // REF: https://www.json.org/
			case AsciiByte.Quote:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Quote);
				break;
			case AsciiByte.Backslash:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Backslash);
				break;
			case AsciiByte.Backfeed:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_b);
				break;
			case AsciiByte.Formfeed:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_f);
				break;
			case AsciiByte.Nl:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_n);
				break;
			case AsciiByte.Cr:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_r);
				break;
			case AsciiByte.Tab:
				bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_t);
				break;
			default:
				if (   val < AsciiByte.Space // control characters
					|| val == 160  // nbsp
					|| val == 8206 // left to right
					|| val == 8207 // right to left
					) {
					// convert to \u1234
					bfr.Add_byte_backslash().Add_byte(AsciiByte.Ltr_u).Add_str_a7(Hex_utl_.To_str(val, 4));
				}
				else {
					bfr.Add_u8_int(val);
				}
				break;
		}
	}
	public Json_doc_wtr Int(int v) {bfr.Add_int_variable(v); return this;}
	public Json_doc_wtr Double(double v) {bfr.Add_double(v); return this;}
	public Json_doc_wtr Comma() {Indent(); bfr.Add_byte(AsciiByte.Comma).Add_byte_nl(); return this;}
	public Json_doc_wtr Kv_ary_empty(boolean comma, byte[] key) {
		Key_internal(comma, key);
		bfr.Add_byte(AsciiByte.BrackBgn).Add_byte(AsciiByte.BrackEnd);
		bfr.Add_byte_nl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, byte[] val) {return Kv(comma, Bry_.new_u8(key), val);}
	public Json_doc_wtr Kv(boolean comma, byte[] key, byte[] val) {
		Key_internal(comma, key);
		Str(val);
		bfr.Add_byte_nl();
		return this;
	}
	public Json_doc_wtr Kv_double(boolean comma, byte[] key, double v) {
		Key_internal(comma, key);
		Double(v);
		bfr.Add_byte_nl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, int v) {return Kv(comma, Bry_.new_u8(key), v);}
	public Json_doc_wtr Kv(boolean comma, byte[] key, int v) {
		Key_internal(comma, key);
		Int(v);
		bfr.Add_byte_nl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, double v) {return Kv(comma, Bry_.new_u8(key), Bry_.new_u8(Double_.To_str(v)));}
	public Json_doc_wtr Kv(boolean comma, String key, boolean v) {return Kv(comma, Bry_.new_u8(key), v ? BoolUtl.YBry : BoolUtl.NBry);}
	public Json_doc_wtr Key(boolean comma, String key) {return Key(comma, Bry_.new_u8(key));}
	public Json_doc_wtr Key(boolean comma, byte[] key) {
		Key_internal(comma, key);
		bfr.Add_byte_nl();
		return this;
	}
	public Json_doc_wtr Val(boolean comma, int v) {
		Val_internal(comma);
		Int(v);
		New_line();
		return this;
	}
	public Json_doc_wtr Val(boolean comma, byte[] v) {
		Val_internal(comma);
		Str(v);
		New_line();
		return this;
	}
	Json_doc_wtr Val_internal(boolean comma) {
		Indent();
		bfr.Add_byte(comma ? AsciiByte.Comma : AsciiByte.Space);
		bfr.Add_byte(AsciiByte.Space);
		return this;
	}
	Json_doc_wtr Key_internal(boolean comma, byte[] key) {
		Indent();
		bfr.Add_byte(comma ? AsciiByte.Comma : AsciiByte.Space);
		bfr.Add_byte(AsciiByte.Space);
		Str(key);
		bfr.Add_byte(AsciiByte.Colon);
		return this;
	}
	public byte[] Bld() {return bfr.To_bry_and_clear();}
	public String Bld_as_str() {return bfr.To_str_and_clear();}
	private static final byte[] Escaped__quote = Bry_.new_a7("\\\"");
}
