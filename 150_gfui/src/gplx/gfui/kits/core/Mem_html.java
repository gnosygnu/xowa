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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.draws.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
class Mem_html extends GxwTextMemo_lang implements Gxw_html {		public void Html_invk_src_(Gfo_evt_itm v) {}
	public void Html_doc_html_load_by_mem(String s) {
//			this.Core().ForeColor_set(plainText ? ColorAdp_.Black : ColorAdp_.Gray);
		s = String_.Replace(s, "\r", "");
		s = String_.Replace(s, "\n", "\r\n");
		this.TextVal_set(s);
		this.SelBgn_set(0);
		html_doc_html_load_tid = Gxw_html_load_tid_.Tid_mem;
	}
	public void Html_doc_html_load_by_url(Io_url path, String html) {
		html_doc_html_load_tid = Gxw_html_load_tid_.Tid_url;
	}
	public byte Html_doc_html_load_tid() {return html_doc_html_load_tid;} private byte html_doc_html_load_tid;
	public void Html_doc_html_load_tid_(byte v) {html_doc_html_load_tid = v;}
	public String Html_js_eval_script(String script) {return "";}
	public Object Html_js_eval_script_as_obj(String script) {return "";}
	public String Html_js_send_json(String name, String data) {return "";}
	String ExtractAtr(String key, String txt, int pos) {
		int key_pos = String_.FindBwd(txt, key, pos);	if (key_pos == String_.Find_none) return null;
		int q0 = String_.FindFwd(txt, "\"", key_pos);	if (q0 == String_.Find_none) return null;
		int q1 = String_.FindFwd(txt, "\"", q0 + 1);	if (q1 == String_.Find_none) return null;
		if (!Int_.Between(pos, q0, q1)) return null;	// current pos is not between nearest quotes
		return String_.Mid(txt, q0 + 1, q1);
	}
	public void Html_js_enabled_(boolean v) {}
	public String Html_js_eval_proc_as_str(String proc, Object... args) {return "not implemented by mem_html";}
	public boolean Html_js_eval_proc_as_bool(String proc, Object... args) {return false;}
	public void Html_js_cbks_add(String js_func_name, Gfo_invk invk) {}
	public void Html_dispose() {}
	public Mem_html() {
		this.ctor_MsTextBoxMultiline_();
	}
}
class Mem_tab_mgr extends GxwElem_mock_base implements Gxw_tab_mgr {	public ColorAdp Btns_selected_background() {return btns_selected_background;} public void Btns_selected_background_(ColorAdp v) {btns_selected_background = v;} private ColorAdp btns_selected_background;
	public ColorAdp Btns_selected_foreground() {return btns_selected_foreground;} public void Btns_selected_foreground_(ColorAdp v) {btns_selected_foreground = v;} private ColorAdp btns_selected_foreground;
	public ColorAdp Btns_unselected_background() {return btns_unselected_background;} public void Btns_unselected_background_(ColorAdp v) {btns_unselected_background = v;} private ColorAdp btns_unselected_background;
	public ColorAdp Btns_unselected_foreground() {return btns_unselected_foreground;} public void Btns_unselected_foreground_(ColorAdp v) {btns_unselected_foreground = v;} private ColorAdp btns_unselected_foreground;
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
