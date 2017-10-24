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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_doc_wtr {
	private int indent = -2;
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public Json_doc_wtr Indent() {return Indent(indent);}
	private Json_doc_wtr Indent(int v) {if (v > 0) bfr.Add_byte_repeat(Byte_ascii.Space, v); return this;}
	public Json_doc_wtr Indent_add() {indent += 2; return this;}
	public Json_doc_wtr Indent_del() {indent -= 2; return this;}
	public Json_doc_wtr Nde_bgn() {Indent_add();	Indent(); bfr.Add_byte(Byte_ascii.Curly_bgn).Add_byte_nl(); return this;}
	public Json_doc_wtr Nde_end() {					Indent(); bfr.Add_byte(Byte_ascii.Curly_end).Add_byte_nl(); Indent_del(); return this;}
	public Json_doc_wtr Ary_bgn() {Indent_add();	Indent(); bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte_nl(); return this;}
	public Json_doc_wtr Ary_end() {					Indent(); bfr.Add_byte(Byte_ascii.Brack_end).Add_byte_nl(); Indent_del(); return this;}
	public Json_doc_wtr New_line() {bfr.Add_byte_nl(); return this;}
	public Json_doc_wtr Str(byte[] v) {
		if (v == null)
			bfr.Add(Object_.Bry__null);
		else
			bfr.Add_byte(Byte_ascii.Quote).Add(v).Add_byte(Byte_ascii.Quote);
		return this;
	}
	public Json_doc_wtr Int(int v) {bfr.Add_int_variable(v); return this;}
	public Json_doc_wtr Double(double v) {bfr.Add_double(v); return this;}
	public Json_doc_wtr Comma() {Indent(); bfr.Add_byte(Byte_ascii.Comma).Add_byte_nl(); return this;}
	public Json_doc_wtr Kv_ary_empty(boolean comma, byte[] key) {
		Key_internal(comma, key);
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Brack_end);
		bfr.Add_byte_nl();
		return this;
	}
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
	public Json_doc_wtr Kv(boolean comma, byte[] key, int v) {
		Key_internal(comma, key);
		Int(v);
		bfr.Add_byte_nl();
		return this;
	}
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
		bfr.Add_byte(comma ? Byte_ascii.Comma : Byte_ascii.Space);
		bfr.Add_byte(Byte_ascii.Space);
		return this;
	}
	Json_doc_wtr Key_internal(boolean comma, byte[] key) {
		Indent();
		bfr.Add_byte(comma ? Byte_ascii.Comma : Byte_ascii.Space);
		bfr.Add_byte(Byte_ascii.Space);
		Str(key);
		bfr.Add_byte(Byte_ascii.Colon);
		return this;
	}
	public byte[] Bld() {return bfr.To_bry_and_clear();}
	public String Bld_as_str() {return bfr.To_str_and_clear();}
}
