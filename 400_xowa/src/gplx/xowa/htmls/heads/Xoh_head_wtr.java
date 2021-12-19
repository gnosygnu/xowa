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
package gplx.xowa.htmls.heads;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
import gplx.xowa.*;
import gplx.langs.htmls.*;
public class Xoh_head_wtr {
	private int indent; private int reset_bgn, reset_end;
	private boolean js_tail_inited = false;
	public BryWtr Bfr() {return bfr;} private BryWtr bfr;
	public Xoh_head_wtr Init(BryWtr bfr)	{this.bfr = bfr; return this;}
	public void Term() {
		this.bfr = null;
		js_tail_inited = true;
	}
	public void Write_css_include(Io_url url) {Write_css_include(url.To_http_file_bry());}
	public void Write_css_include(byte[] url) {
		Write_nl_and_indent();
		bfr.Add(Css_include_bgn).Add(url).Add(Css_include_end);
	}
	public void Write_js_include(Io_url url) {Write_js_include(url.To_http_file_bry());}
	public void Write_js_include(byte[] url) {
		Write_nl_and_indent();
		bfr.Add(Js_include_bgn).Add(url).Add(Js_include_end);
	}
	public void Write_css_style_bgn() {
		reset_bgn = bfr.Len();
		Write_nl_and_indent();
		bfr.Add(Gfh_tag_.Style_lhs_w_type);
		Indent_add();
		reset_end = bfr.Len();
	}	
	public void Write_css_style_end() {
		Indent_del();
		if (Reset()) return;
		Write_nl_and_indent();
		bfr.Add(Gfh_tag_.Style_rhs);
	}
	public void Write_css_style_ary(byte[][] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			Write_css_style_itm(ary[i]);
	}
	public void Write_css_style_itm(byte[] bry) {
		Write_nl_and_indent();
		bfr.Add(bry);
	}	
	public void Write_js_lines(byte[][] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			Write_js_line(ary[i]);
	}
	public void Write_js_line(String str) {Write_js_line(BryUtl.NewU8(str));}
	public void Write_js_line(byte[] bry) {
		Write_nl_and_indent();
		bfr.Add(bry);
	}	
	public void Write_js_line_ary(byte[]... ary) {
		Write_nl_and_indent();
		for (byte[] bry : ary)
			bfr.Add(bry);
	}	
	public void Write_js_script_bgn() {
		reset_bgn = bfr.Len();
		Write_nl_and_indent();
		bfr.Add(Gfh_tag_.Script_lhs_w_type);
		Indent_add();
		reset_end = bfr.Len();
	}	
	public void Write_js_script_end() {
		Indent_del();
		if (Reset()) return;
		Write_nl_and_indent();
		bfr.Add(Gfh_tag_.Script_rhs);
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
	private void Write_js_tail_init() {
		if (js_tail_inited) return;
		js_tail_inited = true;
		Write_js_line(Js_line_1);
	}
	public void Write_js_tail_load_lib(Io_url url) {Write_js_tail_load_lib(url.To_http_file_bry());}
	public void Write_js_tail_load_lib(byte[] url) {
		Write_js_tail_init();
		Write_nl_and_indent();
		bfr.Add(Js_line_2_bgn);
		bfr.Add(url);
		bfr.Add(Js_line_2_end);
	}
	public byte[] To_bry_and_clear() {return bfr.ToBryAndClear();}
	private static final byte[]
	  Js_line_1						= BryUtl.NewA7("xowa.js.jquery.init();")
	, Js_line_2_bgn					= BryUtl.NewA7("xowa.js.load_lib('")
	, Js_line_2_end					= BryUtl.NewA7("');")
	;
	private boolean Reset() {
		if (bfr.Len() == reset_end) {			// itms wrote nothing
			bfr.DelRngToEnd(reset_bgn);	// delete bgn
			return true;
		}
		else
			return false;
	}
	public void Write_js_global_ini_atr_val(byte[] key, boolean val)		{Write_js_global_ini_atr(key, BoolUtl.N, val ? BoolUtl.TrueBry : BoolUtl.FalseBry);}
	public void Write_js_global_ini_atr_val(byte[] key, byte[] val)		{Write_js_global_ini_atr(key, BoolUtl.Y, val);}
	public void Write_js_global_ini_atr_obj(byte[] key, byte[] val)		{Write_js_global_ini_atr(key, BoolUtl.N, val);}
	public void Write_js_global_ini_atr_msg(Xowe_wiki wiki, byte[] key)	{Write_js_global_ini_atr(key, BoolUtl.Y, wiki.Msg_mgr().Val_by_key_obj(key));}
	private void Write_js_global_ini_atr(byte[] key, boolean quote_val, byte[] val) {
		Write_js_global_ini_atr_bgn(key);
		if (quote_val)
			Write_js_quote(AsciiByte.Apos, val);
		else
			bfr.Add(val);
		bfr.AddByte(AsciiByte.Comma);
	}
	public void Write_js_global_ini_atr_val(byte[] key, int val) {
		Write_js_global_ini_atr_bgn(key);
		bfr.AddIntVariable(val);
		bfr.AddByte(AsciiByte.Comma);
	}
	private void Write_js_global_ini_atr_bgn(byte[] key) {
		Write_nl_and_indent();
		bfr.AddByteApos();
		bfr.Add(key);
		bfr.AddByteApos();
		bfr.Add(Js_globals_ini_atr_mid);
	}
	public void Write_js_ary_bgn() {js_ary_idx = 0; bfr.AddByte(AsciiByte.BrackBgn);}
	public void Write_js_ary_itm(byte[] val) {
		if (++js_ary_idx != 1) bfr.Add(js_ary_dlm);
		Write_js_quote(AsciiByte.Apos, val);
	}	private int js_ary_idx = 0; private static final byte[] js_ary_dlm = BryUtl.NewA7(", ");
	public void Write_js_ary_end() {js_ary_idx = 0; bfr.AddByte(AsciiByte.BrackEnd);}
	public void Write_js_init_global(byte[] key) {	// EX: xowa.client = {};
		Write_nl_and_indent();
		bfr.Add(key);
		bfr.Add(Js_init_obj);
	}
	public void Write_js_alias_var(byte[] alias, byte[] key) {	// EX: var x_s = xowa.server;
		Write_nl_and_indent();
		bfr.Add(Js_var_bgn);
		bfr.Add(alias);
		bfr.Add(Js_var_mid);
		bfr.Add(key);
		bfr.Add(Js_var_end);
	}
	public void Write_js_alias_kv(byte[] alias, byte[] key, byte[] val) {	// EX: x_s.port = 8080;
		Write_nl_and_indent();
		bfr.Add(alias).AddByteDot().Add(key);
		bfr.Add(Js_var_mid);
		Write_js_quote(AsciiByte.Apos, val);
		bfr.Add(Js_var_end);
	}
	public void Write_js_xowa_var(byte[] key, boolean quote_val, byte[] val) {	// EX: var xowa.app.mode = 'gui';
		Write_nl_and_indent();
		bfr.Add(key);
		bfr.Add(Js_var_mid);
		if (quote_val)
			Write_js_quote(AsciiByte.Apos, val);
		else
			bfr.Add(val);
		bfr.Add(Js_var_end);
	}
	public void Write_js_var(byte[] key, boolean quote_val, byte[] val) {
		Write_nl_and_indent();
		bfr.Add(Js_var_bgn);
		bfr.Add(key);
		bfr.Add(Js_var_mid);
		if (quote_val)
			Write_js_quote(AsciiByte.Apos, val);
		else
			bfr.Add(val);
		bfr.Add(Js_var_end);
	}
	public void Write(byte[] v)	{
		Indent();
		bfr.Add(v);
	}
	private void Write_js_quote(byte quote_byte, byte[] val) {
		int val_len = val.length;
		bfr.AddByte(quote_byte);
		for (int i = 0; i < val_len; i++) {
			byte b = val[i];
			if		(b == quote_byte)			bfr.AddByteBackslash();	// escape quote
			else if (b == AsciiByte.Backslash) bfr.AddByteBackslash();	// escape backslash
			bfr.AddByte(b);
		}
		bfr.AddByte(quote_byte);
	}
	private void Write_nl_and_indent() {
		bfr.AddByteNl(); Indent();
	}
	private void Indent() {bfr.AddByteRepeat(AsciiByte.Space, indent);}
	public Xoh_head_wtr Indent_add() {indent += 2; return this;}
	public Xoh_head_wtr Indent_del() {indent -= 2; return this;}
	private static final byte[]
	  Css_include_bgn			= BryUtl.NewA7("<link rel=\"stylesheet\" href=\"")
	, Css_include_end			= BryUtl.NewA7("\" type='text/css'>")
	, Js_include_bgn			= BryUtl.NewA7("<script src=\"")
	, Js_include_end			= BryUtl.NewA7("\" type='text/javascript'></script>")
	, Js_globals_ini_var_bgn	= BryUtl.NewA7("var xowa_global_values = {")
	, Js_globals_ini_var_end	= BryUtl.NewA7("}")
	, Js_globals_ini_atr_mid	= BryUtl.NewA7(" : ")
	, Js_var_bgn				= BryUtl.NewA7("var ")
	, Js_var_mid				= BryUtl.NewA7(" = ")
	, Js_var_end				= BryUtl.NewA7(";")
	, Js_init_obj				= BryUtl.NewA7(" = {};")
	;
}
