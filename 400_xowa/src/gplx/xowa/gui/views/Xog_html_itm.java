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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.gfui.*; import gplx.html.*; import gplx.xowa.gui.menus.*; import gplx.xowa.gui.menus.dom.*; import gplx.xowa.html.modules.*; import gplx.xowa.pages.*;
public class Xog_html_itm implements GfoInvkAble, GfoEvObj {
	private Xoae_app app;
	public Xog_html_itm(Xog_tab_itm owner_tab) {
		this.owner_tab = owner_tab;
		app = owner_tab.Tab_mgr().Win().App();
		js_cbk = new Xog_html_js_cbk(this);
		Gfui_kit kit = owner_tab.Tab_mgr().Win().Kit();
		cmd_sync = kit.New_cmd_sync(this);	// NOTE: always use sync; async will cause some images to be "lost" in update;
		cmd_async = kit.New_cmd_async(this);
		ev_mgr = GfoEvMgr.new_(this);
	}
	public GfoEvMgr			EvMgr() {return ev_mgr;} private GfoEvMgr ev_mgr;
	public Xog_tab_itm		Owner_tab() {return owner_tab;} private Xog_tab_itm owner_tab;
	public Gfui_html		Html_box() {return html_box;} private Gfui_html html_box;
	public Xog_html_js_cbk	Js_cbk() {return js_cbk;} private Xog_html_js_cbk js_cbk;
	public GfoInvkAble		Cmd_sync() {return cmd_sync;} private GfoInvkAble cmd_sync;
	public GfoInvkAble		Cmd_async() {return cmd_async;} private GfoInvkAble cmd_async; 
	public void Switch_mem(Xog_html_itm comp) {
		Xog_tab_itm temp_owner_tab = owner_tab;		// NOTE: reparent owner_tab, since owner_tab will be switching its html_itm
		this.owner_tab = comp.owner_tab;
		comp.owner_tab = temp_owner_tab;
	}
	public void Html_box_(Gfui_html html_box) {
		this.html_box = html_box;
		html_box.Html_js_cbks_add("xowa_exec", js_cbk);
	}
	public String Html_selected_get_src_or_empty() {return html_box.Html_doc_selected_get_src_or_empty();}
	public String Html_selected_get_href_or_text() {return Html_extract_text(html_box.Html_doc_selected_get_href_or_text());}
	public String Html_selected_get_text_or_href() {return Html_extract_text(html_box.Html_doc_selected_get_text_or_href());}
	public String Html_selected_get_active_or_selection() {return Html_extract_text(html_box.Html_doc_selected_get_active_or_selection());}		
	private String Html_extract_text(String v) {
		Xoae_page page = owner_tab.Page();
		String site = owner_tab.Wiki().Domain_str();
		String ttl = String_.new_utf8_(page.Ttl().Full_db());
		return Xog_html_itm__href_extractor.Html_extract_text(site, ttl, v);
	}
	public void Show(Xoae_page page) {
		byte view_mode = owner_tab.View_mode();			
		byte[] html_src = owner_tab.Wiki().Html_mgr().Page_wtr_mgr().Gen(page, view_mode);
		Html_src_(page, html_src);
		if (view_mode == Xopg_view_mode.Tid_read){			// used only for Xosrh test; DATE:2014-01-29
			html_box.Html_doc_body_focus();					// NOTE: only focus if read so up / down will scroll box; edit / html should focus edit-box; DATE:2014-06-05
			page.Root().Data_htm_(html_src);
		}
	}
	private void Html_src_(Xoae_page page, byte[] html_bry) {
		String html_str = String_.new_utf8_(html_bry);
		if (owner_tab.Tab_mgr().Html_load_tid__url()) {
			Io_url html_url = app.User().Fsys_mgr().App_temp_html_dir().GenSubFil_ary(owner_tab.Tab_key(), ".html");
			try {html_box.Html_doc_html_load_by_url(html_url.Xto_api(), html_str);}
			catch (Exception e) {
				app.Usr_dlg().Warn_many("", "", "failed to write html to file; writing directly by memory: page=~{0} file=~{1} err=~{2}", page.Url().Xto_full_str_safe(), html_url.Raw(), Err_.Message_gplx(e));
				html_box.Html_doc_html_load_by_mem(html_str);
			}
		}
		else
			html_box.Html_doc_html_load_by_mem(html_str);
	}
	public void Html_swap(Xog_html_itm trg_itm) {
		Xog_html_itm src_itm = this;
		Gfui_html src_html = html_box;
		Gfui_html trg_html = trg_itm.html_box;
		Xog_html_js_cbk src_js_cbk = js_cbk;
		Xog_html_js_cbk trg_js_cbk = trg_itm.js_cbk;
		src_itm.html_box = trg_html;
		trg_itm.html_box = src_html;
		src_itm.js_cbk = trg_js_cbk;
		trg_itm.js_cbk = src_js_cbk;
	}
	public byte[] Get_elem_value_for_edit_box_as_bry()	{return Bry_.new_utf8_(this.Get_elem_value_for_edit_box());}
	public String Get_elem_value_for_edit_box()			{return html_box.Html_elem_atr_get_str(Elem_id__xowa_edit_data_box, Gfui_html.Atr_value);}
	public String Get_elem_value(String elem_id)		{return html_box.Html_elem_atr_get_str(elem_id, Gfui_html.Atr_value);}
	public void Html_img_update(String elem_id, String elem_src, int elem_width, int elem_height) {
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_img_update).Add("elem_id", elem_id).Add("elem_src", elem_src).Add("elem_width", elem_width).Add("elem_height", elem_height);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_img_update, m);
	}	Object guard = new Object();
	public void Html_elem_delete(String elem_id) {
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_elem_delete).Add("elem_id", elem_id);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_elem_delete, m);
	}
	public void Html_atr_set(String elem_id, String atr_key, String atr_val) {
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_elem_atr_set).Add("elem_id", elem_id).Add("atr_key", atr_key).Add("atr_val", atr_val);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_elem_atr_set, m);
	}
	public void Html_elem_atr_set_append(String elem_id, String atr_key, String atr_val) {
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_elem_atr_set_append).Add("elem_id", elem_id).Add("atr_key", atr_key).Add("atr_val", atr_val);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_elem_atr_set_append, m);
	}
	public void Html_elem_replace_html(String id, String html) {
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_elem_replace_html).Add("id", id).Add("html", html);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_elem_replace_html, m);
	}
	public void Html_gallery_packed_exec() {
		if (!String_.Eq(owner_tab.Tab_key(), owner_tab.Tab_mgr().Active_tab().Tab_key())) return;	// do not exec unless active;
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_gallery_packed_exec);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_gallery_packed_exec, m);
		module_packed_done = true;
	}
	public void Html_popups_bind_hover_to_doc() {
		if (!String_.Eq(owner_tab.Tab_key(), owner_tab.Tab_mgr().Active_tab().Tab_key())) return;	// do not exec unless active;
		GfoMsg m = GfoMsg_.new_cast_(Invk_html_popups_bind_hover_to_doc);
		GfoInvkAble_.InvkCmd_msg(cmd_sync, Invk_html_popups_bind_hover_to_doc, m);
		module_popups_done = true;
	}
	private boolean module_packed_done = false, module_popups_done = false;
	public void Tab_selected(Xoae_page page) {
		Xoh_module_mgr module_mgr = page.Html_data().Module_mgr();
		if (module_mgr.Itm_gallery().Enabled() && !module_packed_done)
			this.Html_gallery_packed_exec();
		if (module_mgr.Itm_popups().Enabled() && !module_popups_done)
			this.Html_popups_bind_hover_to_doc();
	}
	public void Scroll_page_by_bmk_gui()	{GfoInvkAble_.InvkCmd(cmd_async, Invk_scroll_page_by_bmk);}
	private void Scroll_page_by_bmk() {
		if (!String_.Eq(owner_tab.Tab_key(), owner_tab.Tab_mgr().Active_tab().Tab_key())) return; // only set html page position on active tab; otherwise, page "scrolls down" mysteriously on unseen tab; DATE:2014-05-02
		String html_doc_pos = owner_tab.Page().Html_data().Bmk_pos();
		if (html_doc_pos == null) {
			String auto_focus_id = app.Gui_mgr().Html_mgr().Auto_focus_id();
			if (String_.Len_eq_0(auto_focus_id)) return;		// don't focus anything
			if (String_.Eq(auto_focus_id, " first_anchor"))		// NOTE: HTML 4/5 do not allow space as id; XOWA using space here to create a unique_key that will never collide with any id
				html_box.Html_doc_body_focus();					// NOTE: will focus body if content-editable, or first_anchor otherwise
			else
				html_box.Html_elem_focus(auto_focus_id);
		}
		else if (String_.Eq(html_doc_pos, gplx.xowa.gui.history.Xog_history_itm.Html_doc_pos_toc))	// NOTE: special case to handle TOC clicks; DATE:2013-07-17
			Scroll_page_by_id("toc");
		else
			html_box.Html_window_vpos_(html_doc_pos);
	}
	public void Scroll_page_by_id_gui(String id)	{GfoInvkAble_.InvkCmd_val(cmd_async, Invk_scroll_page_by_id, id);}
	private boolean Scroll_page_by_id(String id) {
		return (id == null) 
			? false
			: html_box.Html_elem_scroll_into_view(Xoa_app_.Utl_encoder_mgr().Id().Encode_str(id));
	}
	public void Js_enabled_(boolean v) {
		html_box.Html_js_enabled_(v);
	}
	private void When_menu_detected() {
		Xoa_gui_mgr gui_mgr = app.Gui_mgr(); Gfui_kit kit = gui_mgr.Kit();
		Xog_popup_mnu_mgr popup_mnu_mgr = gui_mgr.Menu_mgr().Popup();
		Xog_mnu_grp popup_mnu = popup_mnu_mgr.Html_page();
		if		(String_.Len_gt_0(this.Html_selected_get_src_or_empty()))
			popup_mnu = popup_mnu_mgr.Html_file();
		else if (String_.Len_gt_0(this.Html_selected_get_text_or_href()))
			popup_mnu = popup_mnu_mgr.Html_link();
		kit.Set_mnu_popup(html_box, popup_mnu.Under_mnu());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_img_update))					html_box.Html_elem_img_update(m.ReadStr("elem_id"), m.ReadStr("elem_src"), m.ReadInt("elem_width"), m.ReadInt("elem_height"));
		else if	(ctx.Match(k, Invk_html_elem_atr_set))					html_box.Html_elem_atr_set(m.ReadStr("elem_id"), m.ReadStr("atr_key"), m.ReadStr("atr_val"));
		else if	(ctx.Match(k, Invk_html_elem_atr_set_append))			html_box.Html_elem_atr_set_append(m.ReadStr("elem_id"), m.ReadStr("atr_key"), m.ReadStr("atr_val"));
		else if	(ctx.Match(k, Invk_html_elem_delete))					html_box.Html_elem_delete(m.ReadStr("elem_id"));
		else if	(ctx.Match(k, Invk_html_elem_replace_html))				html_box.Html_elem_replace_html(m.ReadStr("id"), m.ReadStr("html"));
		else if	(ctx.Match(k, Invk_html_gallery_packed_exec))			html_box.Html_gallery_packed_exec();
		else if	(ctx.Match(k, Invk_html_popups_bind_hover_to_doc))		html_box.Html_js_eval_script("xowa_popups_bind_doc();");
		else if (ctx.Match(k, Invk_scroll_page_by_bmk))					Scroll_page_by_bmk();
		else if (ctx.Match(k, Invk_scroll_page_by_id))					Scroll_page_by_id(m.ReadStr("v"));
		else if (ctx.Match(k, Invk_html_elem_focus))					html_box.Html_elem_focus(m.ReadStr("v"));
		else if (ctx.Match(k, GfuiElemKeys.Evt_menu_detected))			When_menu_detected();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_html_gallery_packed_exec = "html_gallery_packed_exec", Invk_html_popups_bind_hover_to_doc = "html_popups_bind_hover_to_doc"
	, Invk_html_img_update = "html_img_update", Invk_html_elem_atr_set = "html_elem_atr_set"
	, Invk_html_elem_atr_set_append = "html_elem_atr_set_append", Invk_html_elem_delete = "html_elem_delete", Invk_html_elem_replace_html = "html_elem_replace_html"
	, Invk_scroll_page_by_bmk = "scroll_page_by_bmk", Invk_scroll_page_by_id = "scroll_page_by_id"
	;
	public static final String 
	  Elem_id__xowa_edit_data_box		= "xowa_edit_data_box"
	, Elem_id__xowa_edit_rename_box		= "xowa_edit_rename_box"
	, Elem_id__first_heading			= "firstHeading"
	, Invk_html_elem_focus				= "html_elem_focus"
	;
}
class Xog_html_itm__href_extractor {
	private static final byte Text_tid_none = 0, Text_tid_text = 1, Text_tid_href = 2;
	private static final byte Href_tid_wiki = 1, Href_tid_site = 2, Href_tid_anchor = 3;
	private static final byte[] File_protocol_bry = Bry_.new_ascii_("file://");
	private static final int File_protocol_len = File_protocol_bry.length;
	private static final Btrie_slim_mgr href_trie = Btrie_slim_mgr.cs_()
	.Add_str_byte("/site/"		, Href_tid_site)
	.Add_str_byte("/wiki/"		, Href_tid_wiki)
	.Add_str_byte("#"			, Href_tid_anchor)
	;
	public static String Html_extract_text(String site, String page, String text_str) {
		byte[] text_bry = Bry_.new_utf8_(text_str);
		int text_tid = Byte_ascii.Xto_digit(text_bry[0]);
		int text_len = text_bry.length;
		switch (text_tid) {
			case Text_tid_none: return "";
			case Text_tid_text: return String_.new_utf8_(text_bry, 2, text_len);	// 2 to skip "1|"
			case Text_tid_href: break;	// fall through to below
			default: throw Err_.unhandled(text_tid);
		}
		int href_bgn = 2;	// 2 for length of "2|"
		if (Bry_.HasAtBgn(text_bry, File_protocol_bry, 2, text_len)) {
			href_bgn += File_protocol_len;	// skip "file://"
		}
		Byte_obj_val href_tid = (Byte_obj_val)href_trie.Match_bgn(text_bry, href_bgn, text_len);
		if (href_tid != null) {
			switch (href_tid.Val()) {
				case Href_tid_wiki:			return site + String_.new_utf8_(text_bry, href_bgn, text_len);		
				case Href_tid_site:			return String_.new_utf8_(text_bry, href_bgn + 6, text_len);			// +6 to skip "site/"
				case Href_tid_anchor:		return site + "/wiki/" + page + String_.new_utf8_(text_bry, href_bgn, text_len);
			}
		}
		return String_.new_utf8_(text_bry, 2, text_len);	// 2 to skip "2|"; handles "http://" text as well as any fall-thru from above
	}
	public static final Xog_html_itm__href_extractor _ = new Xog_html_itm__href_extractor(); Xog_html_itm__href_extractor() {}
}
