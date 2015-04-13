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
class Mem_html extends GxwTextMemo_lang implements Gxw_html {		public String Html_doc_html() {
		return String_.Replace(this.TextVal(), "\r\n", "\n");
	}
	public String Html_doc_selected_get_sub_atr(String tag, String sub_tag, int sub_idx, String sub_atr) {return "";}
	public String Html_doc_selected_get(String host, String page) {return "";}
	public String Html_doc_selected_get_text_or_href() {return "";}
	public String Html_doc_selected_get_href_or_text() {return "";}
	public String Html_doc_selected_get_src_or_empty() {return "";}
	public String Html_doc_selected_get_active_or_selection() {return "";}
	public boolean Html_window_print_preview() {return false;}
	public void Html_invk_src_(GfoEvObj v) {}
	public String Html_elem_atr_get_str(String elem_id, String atr_key) {
		if		(String_.Eq(atr_key, Gfui_html.Atr_value))	return String_.Replace(this.TextVal(), "\r\n", "\n");
		else													throw Err_.unhandled(atr_key);
	}
	public Object Html_elem_atr_get_obj(String elem_id, String atr_key) {
		if		(String_.Eq(atr_key, Gfui_html.Atr_value))	return String_.Replace(this.TextVal(), "\r\n", "\n");
		else													throw Err_.unhandled(atr_key);
	}
	public boolean Html_elem_atr_get_bool(String elem_id, String atr_key) {
		if		(String_.Eq(atr_key, Gfui_html.Atr_value))	return Bool_.parse_(String_.Replace(this.TextVal(), "\r\n", "\n"));
		else													throw Err_.unhandled(atr_key);
	}
	public boolean Html_elem_atr_set(String elem_id, String atr_key, String v) {
		if		(String_.Eq(atr_key, Gfui_html.Atr_value))		this.TextVal_set(v);
		else													throw Err_.unhandled(atr_key);
		return true;
	}
	public boolean Html_elem_atr_set_append(String elem_id, String atr_key, String append) {
		if		(String_.Eq(atr_key, Gfui_html.Atr_value))		this.TextVal_set(this.TextVal() + append);
		else													throw Err_.unhandled(atr_key);
		return true;
	}
	public void Html_doc_html_load_by_mem(String s) {
//			this.Core().ForeColor_set(plainText ? ColorAdp_.Black : ColorAdp_.Gray);
		s = String_.Replace(s, "\r", "");
		s = String_.Replace(s, "\n", "\r\n");
		this.TextVal_set(s);
		this.SelBgn_set(0);
		html_doc_html_load_tid = Gxw_html_load_tid_.Tid_mem;
	}
	public void Html_doc_html_load_by_url(String path, String html) {
		html_doc_html_load_tid = Gxw_html_load_tid_.Tid_url;
	}
	public byte Html_doc_html_load_tid() {return html_doc_html_load_tid;} private byte html_doc_html_load_tid;
	public void Html_doc_html_load_tid_(byte v) {html_doc_html_load_tid = v;}
	public String Html_active_atr_get_str(String atrKey, String or) { // NOTE: fuzzy way of finding current href; EX: <a href="a">b</a>
		String txt = this.TextVal();
		int pos = this.SelBgn();
		String rv = ExtractAtr(atrKey, txt, pos);
		return rv == null ? or : rv;
	}
	public void Html_doc_body_focus() {}
	public void Html_doc_selection_focus_toggle() {}
	public String Html_window_vpos() {return "";}
	public boolean Html_window_vpos_(String v) {return true;}
	public boolean Html_elem_focus(String v) {return true;}
	public boolean Html_elem_img_update(String elem_id, String elem_src, int elem_width, int elem_height) {return true;}
	public boolean Html_elem_delete(String elem_id) {return true;}
	public boolean Html_elem_replace_html(String id, String html) {return true;}
	public boolean Html_elem_append_above(String id, String html) {return true;}
	public boolean Html_gallery_packed_exec() {return true;}
	public String Html_js_eval_script(String script) {return "";}
	String ExtractAtr(String key, String txt, int pos) {
		int key_pos = String_.FindBwd(txt, key, pos);	if (key_pos == String_.Find_none) return null;
		int q0 = String_.FindFwd(txt, "\"", key_pos);	if (q0 == String_.Find_none) return null;
		int q1 = String_.FindFwd(txt, "\"", q0 + 1);	if (q1 == String_.Find_none) return null;
		if (!Int_.Between(pos, q0, q1)) return null;	// current pos is not between nearest quotes
		return String_.Mid(txt, q0 + 1, q1);
	}
	public boolean Html_doc_find(String elem_id, String find, boolean dir_fwd, boolean case_match, boolean wrap_find) {
//			String txt = this.TextVal();
//			int pos = this.SelBgn();
//			int bgn = String_.FindFwd(txt, find, pos);	if (bgn == String_.Find_none) return false;
//			if (bgn == pos) {
//				bgn = String_.FindFwd(txt, find, pos + 1);
//				if (bgn == String_.Find_none) {
//					bgn = String_.FindFwd(txt, find, 0);
//					if (bgn == String_.Find_none) return false;
//				}
//			}
//			this.SelBgn_set(bgn);
//			this.SelLen_set(String_.Len(find));
//			this.ScrollTillSelectionStartIsFirstLine();
		txtFindMgr.Text_(this.TextVal());
		int cur = this.SelBgn();
		int[] ary = txtFindMgr.FindByUi(find, this.SelBgn(), this.SelLen(), false);
		if (ary[0] != cur) {
			this.SelBgn_set(ary[0]);
			this.SelLen_set(ary[1]);
			this.ScrollTillCaretIsVisible();
		}
		else {
			ary = txtFindMgr.FindByUi(find, this.SelBgn() + 1, 0, false);
			if (ary[0] != 0) {
				this.SelBgn_set(ary[0]);
				this.SelLen_set(ary[1]);
				this.ScrollTillCaretIsVisible();
//					this.ScrollTillSelectionStartIsFirstLine();
			}
		}
		return true;
	}
	public boolean Html_elem_scroll_into_view(String id) {return false;}
	public void Html_js_enabled_(boolean v) {}
	public void Html_js_eval_proc(String proc, String... args) {}
	public void Html_js_cbks_add(String js_func_name, GfoInvkAble invk) {}
	public void Html_dispose() {}
	private TxtFindMgr txtFindMgr = new TxtFindMgr();
	public Mem_html() {
		this.ctor_MsTextBoxMultiline_();
	}
}
class Mem_tab_mgr extends GxwElem_mock_base implements Gxw_tab_mgr {	public ColorAdp Btns_selected_color() {return btns_selected_color;} public void Btns_selected_color_(ColorAdp v) {btns_selected_color = v;} private ColorAdp btns_selected_color;
	public ColorAdp Btns_unselected_color() {return btns_unselected_color;} public void Btns_unselected_color_(ColorAdp v) {btns_unselected_color = v;} private ColorAdp btns_unselected_color;
	public Gxw_tab_itm Tabs_add(Gfui_tab_itm_data tab_data) {return new Mem_tab_itm();}
	public void Tabs_select_by_idx(int i) {}
	public void Tabs_close_by_idx(int i) {}
	public void Tabs_switch(int src, int trg) {}
	public int Btns_height() {return 0;} public void Btns_height_(int v) {}
	public boolean Btns_place_on_top() {return false;} public void Btns_place_on_top_(boolean v) {}
	public boolean Btns_curved() {return false;} public void Btns_curved_(boolean v) {}
	public boolean Btns_close_visible() {return false;} public void Btns_close_visible_(boolean v) {}
	public boolean Btns_unselected_close_visible() {return false;} public void Btns_unselected_close_visible_(boolean v) {}
}
class Mem_tab_itm extends GxwElem_mock_base implements Gxw_tab_itm {	public void Subs_add(GfuiElem sub) {}
	public Gfui_tab_itm_data Tab_data() {return tab_data;} private Gfui_tab_itm_data tab_data = new Gfui_tab_itm_data("null", -1);
	public String Tab_name() {return tab_name;} public void Tab_name_(String v) {tab_name = v;} private String tab_name;
	public String Tab_tip_text() {return tab_tip_text;} public void Tab_tip_text_(String v) {tab_tip_text = v;} private String tab_tip_text;
}
