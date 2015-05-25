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
package gplx.xowa.servers; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.gfui.*;
import gplx.xowa.servers.tcp.*;
import gplx.xowa.servers.http.*; import gplx.xowa.gui.views.*;
public class Gxw_html_server implements Gxw_html {
	private Xosrv_socket_wtr wtr; private Gfo_usr_dlg usr_dlg;
	private Gfui_html_cfg cfg;
	public Gxw_html_server(Gfo_usr_dlg usr_dlg, Xosrv_socket_wtr wtr) {
		this.usr_dlg = usr_dlg; this.wtr = wtr;
		cfg = Swt_kit._.Html_cfg();
	} 
	public String		Html_doc_html() {return Exec(cfg.Doc_html());}
	public void			Html_doc_html_load_by_mem(String html) {Exec("location.reload(true);");}	// HACK: force reload of page
	public void			Html_doc_html_load_by_url(String path, String html) {Exec("location.reload(true);");}	// HACK: force reload of page
	public byte			Html_doc_html_load_tid() {return html_doc_html_load_tid;} private byte html_doc_html_load_tid;
	public void			Html_doc_html_load_tid_(byte v) {html_doc_html_load_tid = v;}
	public void			Html_dispose() {}
	public String		Html_doc_selected_get_text_or_href() {return Exec(cfg.Doc_selected_get_text_or_href());}
	public String		Html_doc_selected_get_href_or_text() {return Exec(cfg.Doc_selected_get_href_or_text());}
	public String		Html_doc_selected_get_src_or_empty() {return Exec(cfg.Doc_selected_get_src_or_empty());}
	public String		Html_doc_selected_get_active_or_selection() {return Exec(cfg.Doc_selected_get_active_or_selection());}
	public boolean			Html_doc_find(String id, String find, boolean dir_fwd, boolean case_match, boolean wrap_find) {throw Err_.not_implemented_();}
	public void			Html_doc_body_focus() {Exec(cfg.Doc_body_focus());}
	public void			Html_doc_selection_focus_toggle() {Exec(cfg.Doc_selection_focus_toggle());}
	public boolean			Html_doc_loaded() {return Bool_.cast_(Exec(cfg.Doc_loaded()));}
	public String		Html_elem_atr_get_str	(String id, String atr_key) {return Exec(cfg.Elem_atr_get(id, atr_key));}
	public boolean			Html_elem_atr_get_bool	(String id, String atr_key) {return Bool_.parse_(Exec(cfg.Elem_atr_get(id, atr_key)));}
	public boolean			Html_elem_atr_set		(String id, String atr_key, String val) {return Exec_as_bool(cfg.Elem_atr_set(id, atr_key, val));}
	public boolean			Html_elem_atr_set_append(String id, String atr_key, String val) {return Exec_as_bool(cfg.Elem_atr_set_append(id, atr_key, val));}
	public boolean			Html_elem_delete(String id) {return Exec_as_bool(cfg.Elem_delete(id));}
	public boolean			Html_elem_replace_html(String id, String html) {return Exec_as_bool(cfg.Elem_replace_html(id, html));}
	public boolean			Html_elem_append_above(String id, String html) {return Exec_as_bool(cfg.Elem_append_above(id, html));}
	public boolean			Html_gallery_packed_exec() {return Exec_as_bool(cfg.Gallery_packed_exec());}
	public boolean			Html_elem_focus(String id) {return Exec_as_bool(cfg.Elem_focus(id));}
	public boolean			Html_elem_scroll_into_view(String id) {return Exec_as_bool(cfg.Elem_scroll_into_view(id));}
	public boolean			Html_elem_img_update(String id, String src, int w, int h) {return Exec_as_bool(cfg.Elem_img_update(id, src, w, h));}
	public String		Html_window_vpos() {return Exec(cfg.Window_vpos());}
	public boolean			Html_window_vpos_(String v) {
		if (String_.Len_eq_0(v)) return false;	// HACK: v == "" when switching between Read and Edit
		Gfui_html_cfg.Html_window_vpos_parse(v, scroll_top, node_path);
		return Exec_as_bool(cfg.Window_vpos_(scroll_top.Val(), node_path.Val()));
	}	private String_obj_ref scroll_top = String_obj_ref.null_(), node_path = String_obj_ref.null_();
	public boolean			Html_window_print_preview() {return Exec_as_bool(cfg.Window_print_preview());}
	public String		Html_active_atr_get_str(String atr_key, String or) {return Exec(cfg.Active_atr_get_str(atr_key));}
	public void			Html_js_enabled_(boolean v) {}
	public void			Html_js_eval_proc(String name, String... args) {
		Bry_fmtr fmtr = cfg.Js_scripts_get(name);
		Exec(fmtr.Bld_str_many(args));
	}
	public String		Html_js_eval_script(String script) {return Exec(script);}
	public void			Html_js_cbks_add(String js_func_name, GfoInvkAble invk) {}
	public void			Html_invk_src_(GfoEvObj v) {}
	public GxwCore_base	Core() {throw Err_.not_implemented_();}
	public GxwCbkHost	Host() {throw Err_.not_implemented_();} public void Host_set(GxwCbkHost host) {throw Err_.not_implemented_();}
	public Object		UnderElem() {throw Err_.not_implemented_();}
	public String		TextVal() {throw Err_.not_implemented_();} public void TextVal_set(String v) {throw Err_.not_implemented_();} 
			public void			EnableDoubleBuffering() {throw Err_.not_implemented_();}
	private boolean Exec_as_bool(String s) {
		Exec(s);
		return true;	// NOTE: js is async, so immediate return value is not possible; return true for now;
	}
	private String Exec(String s) {
		if (wtr == null) return "";	// HACK: handles http_server
		s = "(function () {" + s + "})();"; // NOTE: dependent on firefox addon which does 'var result = Function("with(arguments[0]){return "+cmd_text+"}")(session.window);'; DATE:2014-01-28
		Xosrv_msg msg = Xosrv_msg.new_(Xosrv_cmd_types.Browser_exec, Bry_.Empty, Bry_.Empty, Bry_.Empty, Bry_.Empty, Bry_.new_u8(s));
		usr_dlg.Note_many("", "", "sending browser.js: msg=~{0}", s);
		wtr.Write(msg);
		return "";
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set)) {}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set";
	public static void Init_gui_for_server(Xoae_app app, Xosrv_socket_wtr wtr) {
		Mem_kit mem_kit = (Mem_kit)gplx.gfui.Gfui_kit_.Mem();
		mem_kit.New_html_impl_prototype_(new Gxw_html_server(app.Usr_dlg(), wtr));	// NOTE: set prototype before calling Kit_
		app.Gui_mgr().Kit_(mem_kit);
	}
	public static void Assert_tab(Xoae_app app, Xoae_page page) {
		Xog_win_itm browser_win = app.Gui_mgr().Browser_win();
		if (browser_win.Active_tab() == null) {									// no active tab
			Xowe_wiki wiki = page.Wikie();										// take wiki from current page; NOTE: do not take from browser_win.Active_tab().Wiki(); DATE:2015-02-23
			browser_win.Tab_mgr().Tabs_new_init(wiki, page);					// create at least one active tab; DATE:2014-07-30
		}
	}
}
