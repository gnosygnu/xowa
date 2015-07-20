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
package gplx.xowa.users.prefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.html.*;
class Prefs_html_wtr {
	public Prefs_html_wtr(Prefs_mgr prefs_mgr) {this.prefs_mgr = prefs_mgr;} Prefs_mgr prefs_mgr; Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Write(Bry_bfr bfr, byte[] src, Html_nde hnde, int prop_idx, byte[] trg_type, byte[] trg_val) {
		Object prop_val = Eval_prop_get(hnde);
		byte elem_type = Prefs_mgr.Elem_tid_tid_of(hnde);
		bfr.Add_mid(src, hnde.Tag_lhs_bgn(), hnde.Tag_lhs_end() - 1);
		switch (elem_type) {
			case Prefs_mgr.Elem_tid_input_text:		this.Write_input(bfr, hnde, prop_idx, prop_val); break;
			case Prefs_mgr.Elem_tid_select:			this.Write_select(bfr, hnde, prop_idx, prop_val); break;
			case Prefs_mgr.Elem_tid_input_xowa_io:	this.Write_io(bfr, hnde, prop_idx, prop_val); return;
			case Prefs_mgr.Elem_tid_input_checkbox:	this.Write_checkbox(bfr, hnde, prop_idx, prop_val); break;
			case Prefs_mgr.Elem_tid_textarea:		this.Write_textarea(bfr, hnde, prop_idx, prop_val); break;
		}
		bfr.Add_mid(src, hnde.Tag_rhs_bgn(), hnde.Tag_rhs_end());		
	}
	private void Write_input(Bry_bfr bfr, Html_nde hnde, int prop_idx, Object prop_val) {
		Write__id(bfr, prop_idx);						// " id='xowa_prop_123'"
		Write__value_atr(bfr, prop_val);				// " value='abc'"
		Write__nde_end(bfr);							// ">"
	}
	private void Write_textarea(Bry_bfr bfr, Html_nde hnde, int prop_idx, Object prop_val) {
		Write__id(bfr, prop_idx);						// " id='xowa_prop_123'"
		Write__nde_end(bfr);							// ">"
		bfr.Add(Html_utl.Escape_html_as_bry(Bry_.new_u8(Object_.Xto_str_strict_or_empty(prop_val))));
														// "abcde"
	}
	private void Write_checkbox(Bry_bfr bfr, Html_nde hnde, int prop_idx, Object prop_val) {
		Write__id(bfr, prop_idx);						// " id='xowa_prop_123'"
		boolean prop_val_is_true = String_.Eq((String)prop_val, "y");
		if (prop_val_is_true)
			bfr.Add(Atr_stub_checked);					// " checked='checked'"
		Write__nde_end(bfr);							// ">"
	}
	private void Write_select(Bry_bfr bfr, Html_nde hnde, int prop_idx, Object prop_val) {
		KeyVal[] options_list = Get_select_options(hnde);
		Write__id(bfr, prop_idx);						// " id='xowa_prop_123'"
		Write__nde_end(bfr);							// ">"
		bfr.Add_byte_nl();								// "\n"
		int len = options_list.length;
		for (int i = 0; i < len; i++) {
			KeyVal option = options_list[i];
			bfr.Add(Nde_stub_option_bgn);				// "  <option value='"
			bfr.Add_str(option.Key());					// "option_key"
			bfr.Add_byte(Byte_ascii.Apos);				// "'"
			if (String_.Eq(Object_.Xto_str_strict_or_empty(prop_val), option.Key()))
				bfr.Add_str_a7(" selected='selected'");	// " selected='selected'"
			bfr.Add_byte(Byte_ascii.Gt);				// ">"
			bfr.Add_str(option.Val_to_str_or_empty());	// "option_text"
			bfr.Add(Nde_stub_option_end);				// "</option>\n"
		}
	}
	private void Write_io(Bry_bfr bfr, Html_nde hnde, int prop_idx, Object prop_val) {
		Write__id(bfr, prop_idx);						// " id='xowa_prop_123'"
		Write__value_atr(bfr, prop_val);				// " value='abc'"
		Write__nde_end(bfr);							// ">"
		Write__tag_end(bfr, hnde);						// "</input>"
		Write_io_btn(bfr, hnde, prop_idx);
	}
	private void Write_io_btn(Bry_bfr bfr, Html_nde hnde, int prop_idx) {
		bfr.Add_str_a7("<button id='xowa_prop_").Add_int_variable(prop_idx).Add_str("_io").Add_byte(Byte_ascii.Apos);
		bfr.Add_str_a7(" class='options_button' onclick='xowa_io_select(\"file\", \"");
		bfr.Add_str_a7("xowa_prop_").Add_int_variable(prop_idx);
		byte[] xowa_io_msg = hnde.Atrs_val_by_key_bry(Bry_.new_a7("xowa_io_msg"));
		if (xowa_io_msg == null) xowa_io_msg = Bry_.new_a7("Please select a file.");
		bfr.Add_str_a7("\", \"").Add(xowa_io_msg).Add_str("\");'>");
		bfr.Add_str_a7("...</button>").Add_byte_nl();		
	}
	private static final byte[] Atr_key_xowa_prop_list = Bry_.new_a7("xowa_prop_list")
		, Atr_stub_id = Bry_.new_a7(" id='xowa_prop_")
		, Atr_stub_value = Bry_.new_a7(" value='")
		, Atr_stub_checked = Bry_.new_a7(" checked='checked'")
		, Nde_stub_option_bgn = Bry_.new_a7("  <option value='")
		, Nde_stub_option_end = Bry_.new_a7("</option>\n")
		;
	Object Eval_prop_get(Html_nde hnde) {
		byte[] cmd = hnde.Atrs_val_by_key_bry(Prefs_mgr.Bry_prop);
		if (cmd == null) 
			cmd = hnde.Atrs_val_by_key_bry(Prefs_mgr.Bry_prop_get);
		tmp_bfr.Add(cmd).Add_byte(Byte_ascii.Semic);
		try {return prefs_mgr.Eval(tmp_bfr.Xto_bry_and_clear());}
		catch (Exception e) {return Err_.Message_gplx_full(e);}
	}
	KeyVal[] Get_select_options(Html_nde hnde) {
		byte[] options_list_key = hnde.Atrs_val_by_key_bry(Atr_key_xowa_prop_list);
		tmp_bfr.Add(options_list_key).Add_byte(Byte_ascii.Semic);
		try {return (KeyVal[])prefs_mgr.Eval(tmp_bfr.Xto_bry_and_clear());}
		catch (Exception e) {Err_.Noop(e); return KeyVal_.Ary_empty;}
	}
	private void Write__nde_end(Bry_bfr bfr) {bfr.Add_byte(Byte_ascii.Gt);}
	private void Write__id(Bry_bfr bfr, int prop_idx) {
		bfr.Add(Atr_stub_id);				// " id='xowa_prop_"
		bfr.Add_int_variable(prop_idx);		// "123"
		bfr.Add_byte(Byte_ascii.Apos);		// "'"
	}
	private void Write__value_atr(Bry_bfr bfr, Object prop_val) {
		bfr.Add(Atr_stub_value);					// " value='"
		bfr.Add(Html_utl.Escape_for_atr_val_as_bry(tmp_bfr, Byte_ascii.Apos, Object_.Xto_str_strict_or_empty(prop_val)));	
													// "abcde"
		bfr.Add_byte(Byte_ascii.Apos);				// "'"
	}
	private void Write__tag_end(Bry_bfr bfr, Html_nde hnde) {
		bfr.Add_mid(hnde.Src(), hnde.Tag_rhs_bgn(), hnde.Tag_rhs_end()); 	// "</input>"
		// bfr.Add_byte_nl();												// "\n"; NOTE: do not write \n; will move to next line; DATE:2013-10-16
	}
}
