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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_doc_srl {
	private int indent = -1;
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public boolean Ws_enabled() {return ws_enabled;} public void Ws_enabled_(boolean v) {ws_enabled = v;} private boolean ws_enabled = false;
	public byte[] Bld() {return bfr.Xto_bry_and_clear();}
	public String Bld_as_str() {return bfr.Xto_str_and_clear();}
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
