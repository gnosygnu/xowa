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
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.net.*; import gplx.langs.htmls.*; import gplx.gfui.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.apps.urls.*;
import gplx.xowa.parsers.*;
public class Prefs_mgr implements GfoInvkAble {
	public Prefs_mgr(Xoae_app app) {
		this.app = app;
		atrs_hash = Hash_adp_bry.cs();
		atrs_hash.Add(Bry_prop, Byte_obj_val.new_(Tid_prop));
		atrs_hash.Add(Bry_prop_get, Byte_obj_val.new_(Tid_prop_get));
		atrs_hash.Add(Bry_prop_set, Byte_obj_val.new_(Tid_prop_set));
		html_wtr = new Prefs_html_wtr(this);
	}	private Xoae_app app; private Hash_adp_bry atrs_hash; private Html_parser html_rdr = new Html_parser(); private Prefs_html_wtr html_wtr;
	private Prefs_trg_mgr option_trgs_mgr = new Prefs_trg_mgr();
	public void Html_box_mok_(Xog_html_itm v) {this.html_box_mok = v;} private Xog_html_itm html_box_mok;
	public byte[] Props_get(byte[] src) {
//			option_trgs_mgr.Init(app.Gui_mgr().Browser_win().Active_page().Url());
		if (props_get_fmtr == null) props_get_fmtr = Bry_fmtr.keys_().Eval_mgr_(app.Gfs_mgr().Eval_mgr());
		src = this.Parse_wikitext_to_html(src);
		props_get_fmtr.Fmt_(src);
		Bry_bfr bfr = Bry_bfr.new_();
		try {src = props_get_fmtr.Fmt_(src).Bld_bry_none(bfr);}
		catch (Exception e) {src = Bry_.Add(src, Bry_.new_u8(Err_.Message_gplx_full(e)));}
		Html_nde[] hndes = html_rdr.Parse_as_ary(src);
		hndes = Html_selecter.Select(src, hndes, atrs_hash);
		int pos = 0;
		int len = hndes.length;
		for (int i = 0; i < len; i++) {
			Html_nde hnde = hndes[i];
			bfr.Add_mid(src, pos, hnde.Tag_lhs_bgn());
			html_wtr.Write(bfr, src, hnde, i, option_trgs_mgr.Trg_type(), option_trgs_mgr.Trg_val());
			pos = hnde.Tag_rhs_end();
		}
		bfr.Add_mid(src, pos, src.length);
		return bfr.To_bry_and_clear();
	}	private Bry_fmtr props_get_fmtr; 
	private void Props_set_and_reload() {
		Xoae_page page = app.Gui_mgr().Browser_win().Active_page();
		Props_set(page.Data_raw());
		page.Wikie().Parser_mgr().Parse(page, true);	// reparse in order to save new values to root; needed for history and going back / fwd; DATE:2014-02-07
		// app.Api_root().Gui().Page().View().Reload();	// force reload to update page; needed for language; DATE:2014-05-26; NOTE: deactivate on 2015-08-13; refreshing page causes options to not show
		Xog_tab_itm tab = app.Gui_mgr().Browser_win().Active_tab(); if (tab != null) tab.Exec_notify(Bool_.Y, "options saved");
	}
	public void Props_set(byte[] src) {
		src = Bry_.Replace(src, Bry_.new_a7("<xowa_cmd>"), Bry_.new_a7("&lt;xowa_cmd>"));
		src = Bry_.Replace(src, Bry_.new_a7("</xowa_cmd>"), Bry_.new_a7("&lt;/xowa_cmd>"));
		src = this.Parse_wikitext_to_html(src);	
		Html_nde[] hndes = html_rdr.Parse_as_ary(src);
		hndes = Html_selecter.Select(src, hndes, atrs_hash);
		int len = hndes.length;
		boolean tidy_enabled = app.Html_mgr().Tidy_mgr().Enabled();
		Bry_bfr cmd_bfr = Bry_bfr.reset_(255);
		for (int i = 0; i < len; i++) {
			Html_nde hnde = hndes[i];
			Props_set_by_hnde(cmd_bfr, src, hnde, i, tidy_enabled);
		}
		app.Cfg_mgr().Db_save_txt();
	}
	private void Props_set_by_hnde(Bry_bfr cmd_bfr, byte[] src, Html_nde hnde, int i, boolean tidy_enabled) {
		byte[] eval_code = hnde.Atrs_val_by_key_bry(Bry_prop);
		if 	(eval_code == null) eval_code = hnde.Atrs_val_by_key_bry(Bry_prop_set);
		String hnde_val = null;
		String hnde_key = "xowa_prop_" + Int_.To_str(i);
		Xog_html_itm html_itm = html_box_mok == null ? app.Gui_mgr().Browser_win().Active_tab().Html_itm() : html_box_mok;	// TEST:
		byte elem_tid = Prefs_mgr.Elem_tid_tid_of(hnde);
		switch (elem_tid) {
			case Elem_tid_input_text:
			case Elem_tid_input_xowa_io:
			case Elem_tid_select: 			hnde_val = html_itm.Html_elem_atr_get_str(hnde_key, gplx.gfui.Gfui_html.Atr_value); break;
			case Elem_tid_textarea:			hnde_val = Html_utl.Unescape_as_str(html_itm.Html_elem_atr_get_str(hnde_key, gplx.gfui.Gfui_html.Atr_value)); break;
			case Elem_tid_input_checkbox:	hnde_val = html_itm.Html_elem_atr_get_bool(hnde_key, "checked") ? "y" : "n"; break;
		}			
		byte[] get_cmd = Props_get(eval_code); 
		Object get_val = Eval_run(Bry_.Add(get_cmd, Byte_ascii.Semic));
		String get_str = Object_.Xto_str_strict_or_null_mark(get_val);
		hnde_val = Scrub_tidy_trailing_nl_in_textarea(tidy_enabled, elem_tid, hnde_val);
		get_str  = Scrub_tidy_trailing_nl_in_textarea(tidy_enabled, elem_tid, get_str);
		if (String_.Eq(get_str, hnde_val)) return;
		try		{app.Cfg_mgr().Set_by_app(String_.new_u8(get_cmd), hnde_val);}
		catch (Exception e) {app.Usr_dlg().Warn_many("", "", "pref update failed: code=~{0} err=~{1}", eval_code, Err_.Message_gplx_full(e));}
	}
	private Object Eval_run(byte[] cmd) {
		try {return Eval(cmd);}
		catch (Exception e) {Err_.Noop(e); return null;}		
	}
	private byte[] Parse_wikitext_to_html(byte[] src) {
		Xowe_wiki wiki = app.Usere().Wiki();		// NOTE: this limits prefs to home_wiki only
		Xop_root_tkn root = new Xop_root_tkn();
		Xop_ctx ctx = Xop_ctx.new_main_page(wiki);	// NOTE: always create new ctx; do not reuse existing, else popup will clear out existing page's prefs; DATE:2015-04-29
		wiki.Parser_mgr().Main().Parse_text_to_wdom(root, ctx, ctx.Tkn_mkr(), src, 0);
		return root.Data_mid();			
	}
	public Object Eval(byte[] code) {return app.Gfs_mgr().Run_str(String_.new_a7(code));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exec_get))		return Props_get(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_save))			Props_set_and_reload();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_exec_get = "exec_get", Invk_save = "save";
	private static final byte Tid_prop = 0, Tid_prop_get = 1, Tid_prop_set = 2;
	public static final byte[] Bry_prop = Bry_.new_a7("xowa_prop"), Bry_prop_get = Bry_.new_a7("xowa_prop_get"), Bry_prop_set = Bry_.new_a7("xowa_prop_set"), Bry_id = Bry_.new_a7("id");
	public static byte Elem_tid_tid_of(Html_nde hnde) {
		byte[] elem_name = Bry_.Mid(hnde.Src(), hnde.Name_bgn(), hnde.Name_end());
		if		(Bry_.Eq(elem_name, Nde_textarea)) 			return Elem_tid_textarea;
		else if	(Bry_.Eq(elem_name, Nde_select)) 			return Elem_tid_select;
		else if	(Bry_.Eq(elem_name, Nde_input)) {
			byte[] input_type = hnde.Atrs_val_by_key_bry(Input_type);
			if 		(input_type == null) 						return Elem_tid_input_text;// treat <input /> as <input type='text'/>
			if		(Bry_.Eq(input_type, Type_text))			return Elem_tid_input_text;
			else if	(Bry_.Eq(input_type, Type_checkbox))		return Elem_tid_input_checkbox;
			else if	(Bry_.Eq(input_type, Type_combo))			return Elem_tid_input_combo;
			else if	(Bry_.Eq(input_type, Type_xowa_io))			return Elem_tid_input_xowa_io;
			else 												return Elem_tid_null;
		}
		else													return Elem_tid_null;
	}	static final byte[] Input_type = Bry_.new_a7("type"), Nde_input = Bry_.new_a7("input"), Nde_textarea = Bry_.new_a7("textarea"), Nde_select = Bry_.new_a7("select"), Type_text = Bry_.new_a7("text"), Type_checkbox = Bry_.new_a7("checkbox"), Type_combo = Bry_.new_a7("xowa_combo"), Type_xowa_io = Bry_.new_a7("xowa_io");
	public static String Scrub_tidy_trailing_nl_in_textarea(boolean tidy_enabled, byte elem_tid, String val) {
		return	// if tidy_enabled and text_area and ends with \n, then remove \n; DATE:2014-06-21
			(	tidy_enabled
			&&	elem_tid == Elem_tid_textarea
			&&	String_.Has_at_end(val, "\n")
			)
			? String_.MidByLen(val, 0, String_.Len(val) - 1)
			: val
			;
	}
	public static final byte Elem_tid_null = 0, Elem_tid_input_text = 1, Elem_tid_textarea = 2, Elem_tid_input_checkbox = 3, Elem_tid_select = 4, Elem_tid_input_combo = 5, Elem_tid_input_xowa_io = 6;	
}
class Prefs_trg_mgr {
	private Gfo_qarg_mgr arg_hash = new Gfo_qarg_mgr();
	public byte[] Trg_type() {return trg_type;} private byte[] trg_type;
	public byte[] Trg_val() {return trg_val;} private byte[] trg_val;
	public void Init(Xoa_url url) {
		arg_hash.Load(url.Qargs_ary());
		trg_type = arg_hash.Get_val_bry_or(Prefs_trg_mgr.Arg_option_trg_type_bry, null);
		trg_val = arg_hash.Get_val_bry_or(Prefs_trg_mgr.Arg_option_trg_val_bry, null);
	}
	public static final byte[] Arg_option_trg_type_bry = Bry_.new_a7("option_trg_type"), Arg_option_trg_val_bry = Bry_.new_a7("option_trg_val");
}
