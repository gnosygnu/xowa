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
public class Json_doc_srl {
	private int indent = -1;
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public boolean Ws_enabled() {return ws_enabled;} public void Ws_enabled_(boolean v) {ws_enabled = v;} private boolean ws_enabled = false;
	public byte[] Bld() {return bfr.To_bry_and_clear();}
	public String Bld_as_str() {return bfr.To_str_and_clear();}
	public Json_doc_srl Write_root(byte[] key, Object val) {
		Write_nde_bgn();
		Write_obj(false, key, val);
		Write_nde_end();
		return this;
	}
	public void Write_obj(boolean comma, byte[] key, Object val) {
		Class<?> t = Type_adp_.ClassOf_obj(val);
		if	(Type_adp_.Is_array(t))
			Write_kv_ary(comma, key, (Object[])val);
		else
			Write_kv_str(comma, key, Object_.Xto_str_strict_or_empty(val));
	}
	private void Write_kv_ary(boolean comma, byte[] key, Object[] val) {
		Write_key(comma, key); Write_new_line();	// '"key":\n'
		Write_ary_bgn();							// '[\n'
		Indent_add();								// -->
		int len = val.length;
		for (int i = 0; i < len; i++) {
			Write_itm_hdr(i != 0);					// ', '
			Write_str(Bry_.new_u8(Object_.Xto_str_strict_or_null(val[i])));
			Write_new_line();
		}
		Indent_del();
		Write_ary_end();
	}
	private void Write_kv_str(boolean comma, byte[] key, String val) {
		Write_key(comma, key);							// "key":
		Write_str(Bry_.new_u8(val));					// "val"
		Write_new_line();								// \n
	}
	private void Write_key(boolean comma, byte[] key) {	// "key":
		Write_indent();
		Write_str(key);
		bfr.Add_byte(Byte_ascii.Colon);
	}
	private void Write_indent() {if (ws_enabled && indent > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent);}
	private void Write_str(byte[] v) {
		if (v == null)
			bfr.Add(Object_.Bry__null);
		else
			bfr.Add_byte(Byte_ascii.Quote).Add(v).Add_byte(Byte_ascii.Quote);
	}
	private void Write_comma(boolean comma) {
		if (comma)
			bfr.Add_byte(Byte_ascii.Comma);
		else {
			if (ws_enabled) 
				bfr.Add_byte(Byte_ascii.Space);
		}
		if (ws_enabled)
			bfr.Add_byte(Byte_ascii.Space);
	}
	private void Write_ary_bgn() {Indent_add();	Write_indent(); bfr.Add_byte(Byte_ascii.Brack_bgn); Write_new_line();}
	private void Write_ary_end() {				Write_indent(); bfr.Add_byte(Byte_ascii.Brack_end); Write_new_line();	Indent_del();}
	private void Write_nde_bgn() {Indent_add();	Write_indent(); bfr.Add_byte(Byte_ascii.Curly_bgn); Write_new_line();}
	private void Write_nde_end() {				Write_indent(); bfr.Add_byte(Byte_ascii.Curly_end); Write_new_line();	Indent_del();}
	private void Write_itm_hdr(boolean comma) {
		Write_indent();
		Write_comma(comma);
	}
	private void Indent_add() {indent += 2;}
	private void Indent_del() {indent -= 2;}
	private void Write_new_line() {if (ws_enabled) bfr.Add_byte_nl();}
}
