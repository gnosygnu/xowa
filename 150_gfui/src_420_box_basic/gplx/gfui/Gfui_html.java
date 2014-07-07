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
package gplx.gfui; import gplx.*;
public class Gfui_html extends GfuiElemBase {		
	public void					Under_html_(Gxw_html v) {under = v;} private Gxw_html under;
	public String				Html_doc_html() {return under.Html_doc_html();}
	public void					Html_doc_html_(String s) {under.Html_doc_html_(s);}
	public String				Html_doc_selected_get_text_or_href() {return under.Html_doc_selected_get_text_or_href();}
	public String				Html_doc_selected_get_href_or_text() {return under.Html_doc_selected_get_href_or_text();}
	public String				Html_doc_selected_get_src_or_empty() {return under.Html_doc_selected_get_src_or_empty();}
	public boolean				Html_doc_find(String elem_id, String find, boolean dir_fwd, boolean case_match, boolean wrap_find) {return under.Html_doc_find(elem_id, find, dir_fwd, case_match, wrap_find);}
	public void					Html_doc_body_focus() {under.Html_doc_body_focus();}
	public void					Html_doc_selection_focus_toggle() {under.Html_doc_selection_focus_toggle();}
	@gplx.Virtual public String		Html_elem_atr_get_str(String elem_id, String atr_key) {return under.Html_elem_atr_get_str(elem_id, atr_key);}
	@gplx.Virtual public boolean			Html_elem_atr_get_bool(String elem_id, String atr_key) {return under.Html_elem_atr_get_bool(elem_id, atr_key);}
	public boolean					Html_elem_atr_set(String elem_id, String atr_key, String v) {return under.Html_elem_atr_set(elem_id, atr_key, v);}
	public boolean					Html_elem_atr_set_append(String elem_id, String atr_key, String append) {return under.Html_elem_atr_set_append(elem_id, atr_key, append);}
	public boolean			  		Html_elem_delete(String elem_id) {return under.Html_elem_delete(elem_id);}
	public boolean			  		Html_elem_replace_html(String id, String html) {return under.Html_elem_replace_html(id, html);}
	public boolean					Html_gallery_packed_exec() {return under.Html_gallery_packed_exec();}
	public void					Html_elem_focus(String elem_id) {under.Html_elem_focus(elem_id);}
	public boolean			  		Html_elem_scroll_into_view(String elem_id) {return under.Html_elem_scroll_into_view(elem_id);}
	public boolean					Html_elem_img_update(String elem_id, String elem_src, int elem_width, int elem_height) {return under.Html_elem_img_update(elem_id, elem_src, elem_width, elem_height);}
	public String				Html_window_vpos() {return under.Html_window_vpos();}
	public void					Html_window_vpos_(String v) {under.Html_window_vpos_(v);}
	public boolean					Html_window_print_preview() {return under.Html_window_print_preview();}
	public String				Html_active_atr_get_str(String atrKey, String or) {return under.Html_active_atr_get_str(atrKey, or);}
	public void					Html_js_enabled_(boolean v) {under.Html_js_enabled_(v);}
	public void					Html_js_eval_proc(String name, String... args) {under.Html_js_eval_proc(name, args);}
	public String				Html_js_eval_script(String script) {return under.Html_js_eval_script(script);}
	public void					Html_js_cbks_add(String js_func_name, GfoInvkAble invk) {under.Html_js_cbks_add(js_func_name, invk);}
	public void					Html_invk_src_(GfoEvObj v) {under.Html_invk_src_(v);}
	@Override public GfuiElem Text_(String v) {
		this.Html_doc_html_(v);
		return this;
	}
	public static Gfui_html kit_(Gfui_kit kit, String key, Gxw_html under, KeyValHash ctorArgs) {
		Gfui_html rv = new Gfui_html();
		rv.ctor_kit_GfuiElemBase(kit, key, (GxwElem)under, ctorArgs);
		rv.under = under;
		return rv;
	}
	public static Gfui_html mem_(String key, Gxw_html under) {
		Gfui_html rv = new Gfui_html();
		rv.Key_of_GfuiElem_(key);
		rv.under = under;
		return rv;
	}
	public static Object Js_args_exec(GfoInvkAble invk, Object[] args) {
		GfoMsg m = Js_args_to_msg(args);
		return GfoInvkAble_.InvkCmd_msg(invk, m.Key(), m);
	}
	public static GfoMsg Js_args_to_msg(Object[] args) {
		String proc = (String)args[0];
		GfoMsg rv = GfoMsg_.new_parse_(proc);
		for (int i = 1; i < args.length; i++)
			rv.Add(Int_.XtoStr(i), args[i]);	// NOTE: args[i] can be either String or String[]
		return rv;
	}
	public static final String Atr_href = "href", Atr_title = "title", Atr_value = "value", Atr_innerHTML = "innerHTML", Atr_src = "src";
	public static final String Elem_id_body = "body";
	public static final String Evt_location_changed = "location_changed", Evt_location_changing = "location_changing", Evt_link_hover = "link_hover", Evt_win_resized = "win_resized";
}
