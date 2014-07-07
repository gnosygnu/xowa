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
public interface Gxw_html extends GxwElem {
	String		Html_doc_html();
	void		Html_doc_html_(String s);
	String		Html_doc_selected_get_text_or_href();
	String		Html_doc_selected_get_href_or_text();
	String		Html_doc_selected_get_src_or_empty();
	boolean		Html_doc_find(String id, String find, boolean dir_fwd, boolean case_match, boolean wrap_find);
	void		Html_doc_body_focus();
	void		Html_doc_selection_focus_toggle();
	String		Html_elem_atr_get_str	(String id, String atr_key);
	boolean		Html_elem_atr_get_bool	(String id, String atr_key);
	boolean		Html_elem_atr_set		(String id, String atr_key, String val);
	boolean		Html_elem_atr_set_append(String id, String atr_key, String append);
	boolean		Html_elem_delete(String id);
	boolean		Html_elem_replace_html(String id, String html);
	boolean		Html_gallery_packed_exec();
	boolean		Html_elem_focus(String id);
	boolean		Html_elem_scroll_into_view(String id);
	boolean		Html_elem_img_update(String id, String src, int w, int h);
	String		Html_window_vpos();
	boolean		Html_window_vpos_(String v);
	boolean		Html_window_print_preview();
	String		Html_active_atr_get_str(String atr_key, String or);
	void		Html_js_enabled_(boolean v);
	void		Html_js_eval_proc(String name, String... args);
	String		Html_js_eval_script(String script);
	void		Html_js_cbks_add(String js_func_name, GfoInvkAble invk);
	void		Html_invk_src_(GfoEvObj v);
}
