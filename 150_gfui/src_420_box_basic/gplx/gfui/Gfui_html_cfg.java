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
public class Gfui_html_cfg implements GfoInvkAble {
	public String Doc_html()													{return Exec_fmt(fmtr_doc_html);}									private Bry_fmtr fmtr_doc_html = Bry_fmtr.new_();
	public String Doc_body_focus()												{return Exec_fmt(fmtr_doc_body_focus);}								private Bry_fmtr fmtr_doc_body_focus = Bry_fmtr.new_();
	public String Doc_selected_get_text_or_href()								{return Exec_fmt(fmtr_doc_selected_get_text_or_href);}				private Bry_fmtr fmtr_doc_selected_get_text_or_href = Bry_fmtr.keys_();
	public String Doc_selected_get_href_or_text()								{return Exec_fmt(fmtr_doc_selected_get_href_or_text);}				private Bry_fmtr fmtr_doc_selected_get_href_or_text = Bry_fmtr.keys_();
	public String Doc_selected_get_src_or_empty()								{return Exec_fmt(fmtr_doc_selected_get_src_or_empty);}				private Bry_fmtr fmtr_doc_selected_get_src_or_empty = Bry_fmtr.keys_();
	public String Doc_find_html(String find, boolean dir_fwd, boolean case_match, boolean wrap_find, boolean search_text_is_diff, int prv_find_bgn) {
		return Exec_fmt(fmtr_doc_find_html, find, Bool_.XtoStr_lower(dir_fwd), Bool_.XtoStr_lower(case_match), Bool_.XtoStr_lower(wrap_find), Bool_.XtoStr_lower(search_text_is_diff), Int_.XtoStr(prv_find_bgn));
	}	private Bry_fmtr fmtr_doc_find_html = Bry_fmtr.keys_("find_text", "dir_fwd", "case_match", "wrap_find", "find_text_is_diff", "prv_find_bgn");
	public String Doc_find_edit(String find, boolean dir_fwd, boolean case_match, boolean wrap_find, boolean search_text_is_diff, int prv_find_bgn) {
		return Exec_fmt(fmtr_doc_find_edit, find, Bool_.XtoStr_lower(dir_fwd), Bool_.XtoStr_lower(case_match), Bool_.XtoStr_lower(wrap_find), Bool_.XtoStr_lower(search_text_is_diff), Int_.XtoStr(prv_find_bgn));
	}	private Bry_fmtr fmtr_doc_find_edit = Bry_fmtr.keys_("find_text", "dir_fwd", "case_match", "wrap_find", "find_text_is_diff", "prv_find_bgn");
	public String Elem_atr_get(String elem_id, String atr_key)					{return Exec_fmt(fmtr_elem_atr_get, elem_id, atr_key);}				private Bry_fmtr fmtr_elem_atr_get = Bry_fmtr.keys_("elem_id", "atr_key");
	public String Elem_atr_get_toString(String elem_id, String atr_key)			{return Exec_fmt(fmtr_elem_atr_get_toString, elem_id, atr_key);}	private Bry_fmtr fmtr_elem_atr_get_toString = Bry_fmtr.keys_("elem_id", "atr_key");
	public String Elem_atr_set(String elem_id, String atr_key, String atr_val)	{return Exec_fmt(fmtr_elem_atr_set, elem_id, atr_key, atr_val);}	private Bry_fmtr fmtr_elem_atr_set = Bry_fmtr.keys_("elem_id", "atr_key", "atr_val");
	public String Elem_atr_set_append(String elem_id, String atr_key, String atr_val) {
		return Exec_fmt(fmtr_elem_atr_set_append, elem_id, atr_key, atr_val);
	}	private Bry_fmtr fmtr_elem_atr_set_append = Bry_fmtr.keys_("elem_id", "atr_key", "atr_val");
	public String Elem_delete(String elem_id)									{return Exec_fmt(fmtr_elem_delete, elem_id);}						private Bry_fmtr fmtr_elem_delete = Bry_fmtr.keys_("elem_id");
	public String Elem_replace_html(String id, String html)						{return Exec_fmt(fmtr_elem_replace_html, id, Escape_quote(html));}	private Bry_fmtr fmtr_elem_replace_html = Bry_fmtr.keys_("id", "html");
	public String Gallery_packed_exec()											{return Exec_fmt(fmtr_gallery_packed_exec);}						private Bry_fmtr fmtr_gallery_packed_exec = Bry_fmtr.keys_();
	public String Elem_focus(String elem_id)									{return Exec_fmt(fmtr_elem_focus, elem_id);}						private Bry_fmtr fmtr_elem_focus = Bry_fmtr.keys_("elem_id");
	public String Elem_scroll_into_view(String elem_id)							{return Exec_fmt(fmtr_elem_scroll_into_view, elem_id);}				private Bry_fmtr fmtr_elem_scroll_into_view = Bry_fmtr.keys_("elem_id");
	public String Elem_img_update(String id, String src, int w, int h)			{return Exec_fmt(fmtr_elem_img_update, id, src, Int_.XtoStr(w), Int_.XtoStr(h));} Bry_fmtr fmtr_elem_img_update = Bry_fmtr.keys_("elem_id", "elem_src", "elem_width", "elem_height");
	public String Window_vpos()													{return Exec_fmt(fmtr_window_vpos);}								private Bry_fmtr fmtr_window_vpos = Bry_fmtr.new_();
	public String Window_vpos_(String node_path, String scroll_top)				{return Exec_fmt(fmtr_window_vpos_, node_path, scroll_top);}		private Bry_fmtr fmtr_window_vpos_ = Bry_fmtr.keys_("node_path", "scroll_top");
	public String Window_print_preview()										{return Exec_fmt(fmtr_window_print_preview);}						private Bry_fmtr fmtr_window_print_preview = Bry_fmtr.keys_();
	public String Active_atr_get_str(String atr_key)							{return Exec_fmt(fmtr_active_atr_get, atr_key);}					private Bry_fmtr fmtr_active_atr_get = Bry_fmtr.keys_("atr_key");
	public Bry_fmtr Js_scripts_get(String name) {return (Bry_fmtr)js_scripts.Fetch(name);}
	private void Js_scripts_add(String name, String text) {
		Bry_fmtr fmtr = Bry_fmtr.new_(text);
		js_scripts.AddReplace(name, fmtr);
	}	private OrderedHash js_scripts = OrderedHash_.new_();
	private String Exec_fmt(Bry_fmtr fmtr, String... vals) {
		if (debug_file != null) GfsCore._.ExecFile(debug_file);
		return fmtr.Bld_str_many(vals);
	}	private Io_url debug_file = null;
	private static String Escape_quote(String v) {
		String rv = v;
		rv = String_.Replace(rv, "'", "\\'");
		rv = String_.Replace(rv, "\"", "\\\"");
		rv = String_.Replace(rv, "\n", "\\n");
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_doc_html_))							fmtr_doc_html.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_body_focus_))					fmtr_doc_body_focus.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_selected_get_text_or_href_))		fmtr_doc_selected_get_text_or_href.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_selected_get_href_or_text_))		fmtr_doc_selected_get_href_or_text.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_selected_get_src_or_empty_))		fmtr_doc_selected_get_src_or_empty.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_find_html_))						fmtr_doc_find_html.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_find_edit_))						fmtr_doc_find_edit.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_atr_get_))						fmtr_elem_atr_get.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_atr_get_toString_))				fmtr_elem_atr_get_toString.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_atr_set_))						fmtr_elem_atr_set.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_atr_set_append_))				fmtr_elem_atr_set_append.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_delete_))						fmtr_elem_delete.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_replace_html_))					fmtr_elem_replace_html.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_gallery_packed_exec_))				fmtr_gallery_packed_exec.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_focus_))						fmtr_elem_focus.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_scroll_into_view_))				fmtr_elem_scroll_into_view.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_img_update_))					fmtr_elem_img_update.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_path_get_))						fmtr_window_vpos.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_win_print_preview_))					fmtr_window_print_preview.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_elem_path_set_))						fmtr_window_vpos_.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_doc_active_atr_get_))				fmtr_active_atr_get.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_js_scripts_add))						Js_scripts_add(m.ReadStr("name"), m.ReadStr("text"));
		else if	(ctx.Match(k, Invk_debug_file_))						debug_file = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static void Html_window_vpos_parse(String v, String_obj_ref scroll_top, String_obj_ref node_path) {
		int pipe_pos = String_.FindFwd(v, "|"); if (pipe_pos == String_.Find_none) return; // if elem_get_path returns invalid value, don't fail; DATE:2014-04-05
		scroll_top.Val_(String_.Mid(v, 0, pipe_pos));
		String node_path_val = String_.Mid(v, pipe_pos + 1, String_.Len(v));
		node_path_val = "'" + String_.Replace(node_path_val, ",", "','") + "'";
		node_path.Val_(node_path_val);
	}
	public static final String Invk_debug_file_ = "debug_file_"
	, Invk_doc_html_ = "doc_html_", Invk_doc_body_focus_ = "doc_body_focus_", Invk_doc_active_atr_get_ = "doc_active_atr_get_", Invk_doc_find_html_ = "doc_find_html_", Invk_doc_find_edit_ = "doc_find_edit_"
	, Invk_doc_selected_get_text_or_href_ = "doc_selected_get_text_or_href_", Invk_doc_selected_get_href_or_text_ = "doc_selected_get_href_or_text_", Invk_doc_selected_get_src_or_empty_ = "doc_selected_get_src_or_empty_"
	, Invk_win_print_preview_ = "win_print_preview_"
	, Invk_elem_atr_get_ = "elem_atr_get_", Invk_elem_atr_get_toString_ = "elem_atr_get_toString_", Invk_elem_atr_set_ = "elem_atr_set_", Invk_elem_atr_set_append_ = "elem_atr_set_append_"
	, Invk_elem_path_get_ = "elem_path_get_", Invk_elem_path_set_ = "elem_path_set_"
	, Invk_elem_focus_ = "elem_focus_", Invk_elem_scroll_into_view_ = "elem_scroll_into_view_"
	, Invk_elem_img_update_ = "elem_img_update_", Invk_elem_delete_ = "elem_delete_", Invk_elem_replace_html_ = "elem_replace_html_", Invk_gallery_packed_exec_ = "gallery_packed_exec_"
	, Invk_js_scripts_add = "js_scripts_add"
	, Invk_javascript_enabled_ = "javascript_enabled_"
	;
}
