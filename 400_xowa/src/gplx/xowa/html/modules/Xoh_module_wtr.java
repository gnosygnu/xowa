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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.html.*;
public class Xoh_module_wtr {
	private int indent; private int reset_bgn, reset_end;
	public Bry_bfr Bfr() {return bfr;} private Bry_bfr bfr;
	public void Init(Bry_bfr bfr)	{this.bfr = bfr;}
	public void Term()				{this.bfr = null;}
	public void Write_css_include(byte[] url) {
		Write_nl_and_indent();
		bfr.Add(Css_include_bgn).Add(url).Add(Css_include_end);
	}
	public void Write_js_include(byte[] url) {
		Write_nl_and_indent();
		bfr.Add(Js_include_bgn).Add(url).Add(Js_include_end);
	}
	public void Write_css_style_bgn() {
		reset_bgn = bfr.Len();
		Write_nl_and_indent();
		bfr.Add(Html_tag_.Style_lhs_w_type);
		Indent_add();
		reset_end = bfr.Len();
	}	
	public void Write_css_style_end() {
		Indent_del();
		if (Reset()) return;
		Write_nl_and_indent();
		bfr.Add(Html_tag_.Style_rhs);
	}
	public void Write_css_style_itm(byte[] bry) {
		Write_nl_and_indent();
		bfr.Add(bry);
	}	
	public void Write_js_line(byte[] bry) {
		Write_nl_and_indent();
		bfr.Add(bry);
	}	
	public void Write_js_script_bgn() {
		reset_bgn = bfr.Len();
		Write_nl_and_indent();
		bfr.Add(Html_tag_.Script_lhs_w_type);
		Indent_add();
		reset_end = bfr.Len();
	}	
	public void Write_js_script_end() {
		Indent_del();
		if (Reset()) return;
		Write_nl_and_indent();
		bfr.Add(Html_tag_.Script_rhs);
	}
	public void Write_js_head_global_bgn() {
		reset_bgn = bfr.Len();
		Write_nl_and_indent();
		bfr.Add(Js_globals_ini_var_bgn);
		Indent_add();
		reset_end = bfr.Len();
	}
	public void Write_js_head_global_end() {
		Indent_del();
		if (Reset()) return;
		Write_nl_and_indent();
		bfr.Add(Js_globals_ini_var_end);
	}
	private boolean Reset() {
		if (bfr.Len() == reset_end) {			// itms wrote nothing
			bfr.Delete_rng_to_end(reset_bgn);	// delete bgn
			return true;
		}
		else
			return false;
	}
	public void Write_js_global_ini_atr_val(byte[] key, boolean val)		{Write_js_global_ini_atr(key, Bool_.N, val ? Bool_.True_bry : Bool_.False_bry);}
	public void Write_js_global_ini_atr_val(byte[] key, byte[] val)		{Write_js_global_ini_atr(key, Bool_.Y, val);}
	public void Write_js_global_ini_atr_obj(byte[] key, byte[] val)		{Write_js_global_ini_atr(key, Bool_.N, val);}
	public void Write_js_global_ini_atr_msg(Xow_wiki wiki, byte[] key)	{Write_js_global_ini_atr(key, Bool_.Y, wiki.Msg_mgr().Val_by_key_obj(key));}
	private void Write_js_global_ini_atr(byte[] key, boolean quote_val, byte[] val) {
		Write_js_global_ini_atr_bgn(key);
		if (quote_val)
			Write_quote(Byte_ascii.Apos, val);
		else
			bfr.Add(val);
		bfr.Add_byte(Byte_ascii.Comma);
	}
	public void Write_js_global_ini_atr_val(byte[] key, int val) {
		Write_js_global_ini_atr_bgn(key);
		bfr.Add_int_variable(val);
		bfr.Add_byte(Byte_ascii.Comma);
	}
	private void Write_js_global_ini_atr_bgn(byte[] key) {
		Write_nl_and_indent();
		bfr.Add_byte_apos();
		bfr.Add(key);
		bfr.Add_byte_apos();
		bfr.Add(Js_globals_ini_atr_mid);
	}
	public void Write_js_var(byte[] key, boolean quote_val, byte[] val) {
		Write_nl_and_indent();
		bfr.Add(Js_var_bgn);
		bfr.Add(key);
		bfr.Add(Js_var_mid);
		if (quote_val)
			Write_quote(Byte_ascii.Apos, val);
		else
			bfr.Add(val);
		bfr.Add(Js_var_end);
	}
	public void Write(byte[] v)	{
		Indent();
		bfr.Add(v);
	}
	private void Write_quote(byte quote_byte, byte[] val) {
		int val_len = val.length;
		bfr.Add_byte(quote_byte);
		for (int i = 0; i < val_len; i++) {
			byte b = val[i];
			if (b == quote_byte) bfr.Add_byte(b);	// double up quotes
			bfr.Add_byte(b);
		}
		bfr.Add_byte(quote_byte);
	}
	private void Write_nl_and_indent() {
		bfr.Add_byte_nl(); Indent();
	}
	private void Indent() {bfr.Add_byte_repeat(Byte_ascii.Space, indent);}
	public void Indent_add() {indent += 2;}
	public void Indent_del() {indent -= 2;}
	private static final byte[]
	  Css_include_bgn			= Bry_.new_ascii_("<link rel=\"stylesheet\" href=\"")
	, Css_include_end			= Bry_.new_ascii_("\" type='text/css'>")
	, Js_include_bgn			= Bry_.new_ascii_("<script src=\"")
	, Js_include_end			= Bry_.new_ascii_("\" type='text/javascript'></script>")
	, Js_globals_ini_var_bgn	= Bry_.new_ascii_("var xowa_global_values = {")
	, Js_globals_ini_var_end	= Bry_.new_ascii_("}")
	, Js_globals_ini_atr_mid	= Bry_.new_ascii_(" : ")
	, Js_var_bgn				= Bry_.new_ascii_("var ")
	, Js_var_mid				= Bry_.new_ascii_(" = ")
	, Js_var_end				= Bry_.new_ascii_(";")
	;
}
