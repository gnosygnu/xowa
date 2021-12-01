/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.guis.views;

import gplx.Bool_;
import gplx.Err_;
import gplx.GfoMsg;
import gplx.Gfo_evt_itm;
import gplx.Gfo_evt_mgr;
import gplx.Gfo_evt_mgr_;
import gplx.Gfo_invk_;
import gplx.GfsCtx;
import gplx.Int_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.gfui.controls.gxws.Gxw_html_load_tid_;
import gplx.gfui.controls.standards.Gfui_tab_itm;
import gplx.gfui.controls.standards.Gfui_tab_itm_data;
import gplx.gfui.controls.standards.Gfui_tab_mgr;
import gplx.gfui.controls.tabs.TabBox_;
import gplx.gfui.kits.core.Gfui_kit;
import gplx.gfui.kits.swts.Swt_html_utl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.hrefs.Xoh_href_;
import gplx.xowa.specials.Xow_special_meta_;

public class Xog_tab_mgr implements Gfo_evt_itm {
	private Ordered_hash tab_regy = Ordered_hash_.New(); private int tab_uid = 0;
	private boolean btns__hide_if_one; private int btns__height;
	public Xog_tab_mgr(Xog_win_itm win) {
		this.win = win;
		ev_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public Xog_win_itm Win() {return win;} private Xog_win_itm win;
	public Gfui_tab_mgr Tab_mgr() {return tab_mgr;} private Gfui_tab_mgr tab_mgr;
	public int Btns__min_chars() {return btns__min_chars;} private int btns__min_chars;
	public int Btns__max_chars() {return btns__max_chars;} private int btns__max_chars;
	public boolean Javascript_enabled() {return javascript_enabled;} private boolean javascript_enabled = true;
	private byte page_load_mode;
	public boolean Page_load_mode_is_url() {return page_load_mode == Gxw_html_load_tid_.Tid_url;}
	public void Init_by_kit(Gfui_kit kit) {
		tab_mgr = kit.New_tab_mgr("xowa.tab_mgr", win.Win_box());
		active_tab = Xog_tab_itm_.Null;
		Gfo_evt_mgr_.Sub_same_many(tab_mgr, this, Gfui_tab_mgr.Evt_tab_selected, Gfui_tab_mgr.Evt_tab_closed, Gfui_tab_mgr.Evt_tab_switched);			
		win.App().Cfg().Bind_many_app(this, Cfg__page_load_mode
		, Cfg__place_on_top, Cfg__height, Cfg__hide_if_one, Cfg__curved, Cfg__close_btn_visible, Cfg__unselected_close_btn_visible, Cfg__max_chars, Cfg__min_chars);
	}
	public Xog_tab_itm Active_tab() {return active_tab;} private Xog_tab_itm active_tab;
	public Xog_tab_itm Active_tab_assert() {
		if (active_tab == Xog_tab_itm_.Null) this.Tabs_new_dflt(true);
		return active_tab;
	}
	public boolean Active_tab_is_null() {return active_tab == Xog_tab_itm_.Null;}
	private void Btns_text_recalc() {
		int len = this.Tabs_len();
		for (int i = 0; i < len; i++) {
			Xog_tab_itm tab_itm = this.Tabs_get_at(i);
			tab_itm.Tab_name_();
		}
	}
	public int Tabs_len() {return tab_regy.Len();}
	public Xog_tab_itm Tabs_new_init(Xowe_wiki wiki, Xoae_page page) {return this.Tabs_new(true, true, wiki, page);}
	public Xog_tab_itm Tabs_get_at(int i) {return (Xog_tab_itm)tab_regy.Get_at(i);}
	public Xog_tab_itm Tabs_new_dflt() {return Tabs_new_dflt(false);}
	public Xog_tab_itm Tabs_new_dflt(boolean focus) {
		boolean active_tab_is_null = this.Active_tab_is_null();
		Xowe_wiki cur_wiki = active_tab_is_null ? win.App().Usere().Wiki() : active_tab.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(cur_wiki, Xow_special_meta_.Itm__default_tab.Ttl_bry());
		Xoa_url url = cur_wiki.Utl__url_parser().Parse_by_urlbar_or_null(ttl.Full_db_as_str()); if (url == null) throw Err_.new_("url", "invalid url", "url", url);
		Xog_tab_itm rv = Tabs_new(focus, active_tab_is_null, cur_wiki, Xoae_page.New(cur_wiki, ttl));
		rv.Page_update_ui();
		rv.Show_url_bgn(url);
		return rv;
	}
	private Xog_tab_itm Tabs_new(boolean focus, boolean active_tab_is_null, Xowe_wiki wiki, Xoae_page page) {
		String tab_key = "tab_" + Int_.To_str(tab_uid++); int tab_idx = tab_regy.Len();
		Gfui_tab_itm_data tab_data = new Gfui_tab_itm_data(tab_key, tab_idx);
		Xog_tab_itm rv = new Xog_tab_itm(this, tab_data, wiki, page);
		Gfui_tab_itm tab_box = tab_mgr.Tabs_add(tab_data);
		rv.Make_html_box(tab_uid, tab_box, win, tab_mgr);
		rv.Html_itm().Js_enabled_(javascript_enabled);
		tab_box.Subs_add(rv.Html_itm().Html_box());
		tab_regy.Add(tab_key, rv);
		if (	focus
			||	active_tab_is_null // NOTE: must select 1st tab, else nothing will show in tab box
			) {
			tab_mgr.Tabs_select_by_idx(rv.Tab_idx());
			active_tab = rv;
		}
		Tabs_hide_if_one_chk(false);
		return rv;
	}
	public void Tabs_new_dupe(boolean focus) {
		if (this.Active_tab_is_null()) return;
		String url = active_tab.Page().Url().To_str();
		Tabs_new_dflt(focus);
		win.Page__navigate_by_url_bar(url);
	}
	public void Tabs_javascript_enabled_(boolean v) {
		this.javascript_enabled = v;
		int len = tab_regy.Len();
		for (int i = 0; i < len; i++) {
			Xog_tab_itm tab = Tabs_get_by_idx_or_warn(i);
			tab.Html_itm().Js_enabled_(v);
		}
	}
	private void Tabs_selected(String key) {
		Xog_tab_itm tab = Tabs_get_by_key_or_warn(key); if (tab == null) return;
		active_tab = tab;
		Xoae_page page = tab.Page();
		Xog_tab_itm_read_mgr.Update_selected_tab(win, page.Url(), page.Ttl());
		tab.Html_itm().Tab_selected(page);
	}
	public void Tabs_close_cur() {
		if (this.Active_tab_is_null()) return;
		Tabs__pub_close(active_tab);
		tab_mgr.Tabs_close_by_idx(active_tab.Tab_idx());
		Xog_tab_itm cur_tab = this.Active_tab();			// get new current tab for line below
		if (cur_tab != null) cur_tab.Html_box().Focus();	// NOTE: needed to focus tab box else tab button will be focused; DATE:2014-07-13
	}
	public void Tabs_close_others() {this.Tabs_close_to_bgn(); this.Tabs_close_to_end();}
	public void Tabs_close_to_bgn() {if (Active_tab_is_null()) return; Tabs_close_rng(0							, active_tab.Tab_idx());}
	public void Tabs_close_to_end() {if (Active_tab_is_null()) return; Tabs_close_rng(active_tab.Tab_idx() + 1	, tab_regy.Len());}
	public void Tabs_close_rng(int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xog_tab_itm tab = Tabs_get_at(bgn);
			if (!Tabs__pub_close(tab)) return;
		}
		for (int i = bgn; i < end; i++)
			tab_mgr.Tabs_close_by_idx(bgn);	// NOTE: close at bgn, not at i, b/c each close will remove a tab from collection
	}
	public boolean Tabs__pub_close_all() {return Tabs__pub_close_rng(0, this.Tabs_len());}
	public boolean Tabs__pub_close_rng(int bgn, int end) {
		boolean rv = true;
		for (int i = bgn; i < end; i++) {
			Xog_tab_itm tab = Tabs_get_at(i);
			boolean close_allowed = Tabs__pub_close(tab);
			if (!close_allowed) rv = false;
		}
		return rv;
	}
	public boolean Tabs__pub_close(Xog_tab_itm tab) {
		return tab.Page().Tab_data().Close_mgr().When_close(tab, Xoa_url.Null);
	}
	public void Tabs_close_undo() {
		if (closed_undo_list.Len() == 0) return;
		String url = (String)List_adp_.Pop(closed_undo_list);
		Tabs_new_dflt(true);
		win.Page__navigate_by_url_bar(url);
	}
	private List_adp closed_undo_list = List_adp_.New();
	private void Tabs_closed(String key) {
		Xog_tab_itm itm = Tabs_get_by_key_or_warn(key); if (itm == null) return;
		itm.Html_box().Html_dispose();
		closed_undo_list.Add(itm.Page().Url().To_str());
		tab_regy.Del(key);
		if (tab_regy.Len() == 0) {
			active_tab = Xog_tab_itm_.Null;
			Xog_tab_itm_read_mgr.Update_selected_tab_blank(win);
		}
		else
			Tabs_recalc_idx();
		Tabs_hide_if_one_chk(false);
	}
	private Xog_tab_itm Tabs_get_by_key_or_warn(String key) {
		Xog_tab_itm rv = (Xog_tab_itm)tab_regy.GetByOrNull(key); if (rv == null) win.App().Usr_dlg().Warn_many("", "", "tab.selected could not find tab; key={0}", key);
		return rv;
	}
	private Xog_tab_itm Tabs_get_by_idx_or_warn(int idx) {
		Xog_tab_itm rv = (Xog_tab_itm)tab_regy.Get_at(idx); if (rv == null) win.App().Usr_dlg().Warn_many("", "", "tab.selected could not find tab; idx={0}", idx);
		return rv;
	}
	private void Tabs_recalc_idx() {
		int len = tab_regy.Len();
		for (int i = 0; i < len; i++) {
			Xog_tab_itm itm = Tabs_get_by_idx_or_warn(i);
			itm.Tab_idx_(i);
		}
	}
	public void Tabs_select(boolean fwd) {
		if (this.Active_tab_is_null()) return;
		int new_idx = TabBox_.Cycle(fwd, active_tab.Tab_idx(), tab_regy.Len());
		tab_mgr.Tabs_select_by_idx(new_idx);
	}
	public void Tabs_select_by_idx(int v) {
		if (v < 0 || v >= tab_regy.Len()) return;
		tab_mgr.Tabs_select_by_idx(v);
	}
	public void Tabs_move(boolean fwd) {
		if (this.Active_tab_is_null()) return;
		int src_idx = active_tab.Tab_idx();
		int trg_idx = TabBox_.Cycle(fwd, src_idx, tab_regy.Len());
		tab_mgr.Tabs_switch(src_idx, trg_idx);
	}
	private void Tabs_switched(String src_key, String trg_key) {
		Xog_tab_itm src_itm = Tabs_get_by_key_or_warn(src_key);
		Xog_tab_itm trg_itm = Tabs_get_by_key_or_warn(trg_key);
		src_itm.Switch_mem(trg_itm);
		active_tab = trg_itm;	// NOTE: src_itm initiated switch, but trg_itm is now active b/c everything in src_itm has now been reparented to trg_itm; DATE:2014-05-12
	}
	public void Tabs_new_link(boolean focus, String link) {	// handle empty link
		if (String_.Len_eq_0(link)) {
			if (this.Active_tab_is_null()) return;
			link = active_tab.Html_itm().Html_selected_get_active_or_selection();
			// 2020-12-16|ISSUE#:823|Open in new tab creates links like `about:/wiki/PAGE_NAME` or `about:/site/WIKI_NAME/wiki/PAGE_NAME`
			link = Swt_html_utl.NormalizeSwtUrl(link);
			if (link.startsWith(Xoh_href_.Str__site)) {
				link = link.substring(Xoh_href_.Str__site.length());
			}
			else if (link.startsWith(Xoh_href_.Str__wiki)) {
				link = active_tab.Wiki().Domain_str() + link;
			}
			link = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode_str(link);	// NOTE: must decode else url-encoded special pages don't work; EX:home/wiki/Special:XowaCfg%3Fgrp%3Dxowa.html.css; DATE:2017-01-02
		}
		if (String_.Len_eq_0(link)) {win.App().Usr_dlg().Prog_many("", "", "no link or text selected"); return;}
		Tabs_new_link(link, focus);
	}
	public void Tabs_new_link(String link, boolean focus) {
		Xowe_wiki wiki = active_tab.Wiki();
		Xog_tab_itm new_tab = Tabs_new(focus, false, wiki, Xoae_page.New(wiki, active_tab.Page().Ttl()));	// NOTE: do not use ttl from link, else middle-clicking pages with anchors won't work; DATE:2015-05-03
		Xoa_url url = wiki.Utl__url_parser().Parse_by_urlbar_or_null(link);	if (url == null) return; // NOTE: link must be of form domain/wiki/page; DATE:2014-05-27			
		new_tab.Show_url_bgn(url);
		if (focus)
			tab_mgr.Tabs_select_by_idx(new_tab.Tab_idx());
	}
	private void Tabs_hide_if_one_chk(boolean force) {
		if (btns__hide_if_one || force) {// run code only if enabled or forced
			if (tab_regy.Len() == 1) {
				int desired_height = btns__hide_if_one ? 0 : btns__height;
				if (tab_mgr.Btns_height() != desired_height)
					tab_mgr.Btns_height_(desired_height);
			}
			else {
				if (tab_mgr.Btns_height() != btns__height)
					tab_mgr.Btns_height_(btns__height);
			}
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_tabs_new_dflt__at_dflt__focus_y))						Tabs_new_dflt(Bool_.Y);
		else if	(ctx.Match(k, Invk_tabs_new_link__at_dflt__focus_n))						Tabs_new_link(Bool_.N, m.ReadStrOr("v", null));
		else if	(ctx.Match(k, Invk_tabs_new_link__at_dflt__focus_y))						Tabs_new_link(Bool_.Y, m.ReadStrOr("v", null));
		else if	(ctx.Match(k, Gfui_tab_mgr.Evt_tab_selected))								Tabs_selected(m.ReadStr("key"));
		else if	(ctx.Match(k, Gfui_tab_mgr.Evt_tab_closed))									Tabs_closed(m.ReadStr("key"));
		else if	(ctx.Match(k, Gfui_tab_mgr.Evt_tab_switched))								Tabs_switched(m.ReadStr("src"), m.ReadStr("trg"));
		else if	(ctx.Match(k, Invk_tabs_close_cur))											Tabs_close_cur();
		else if	(ctx.Match(k, Invk_tabs_select_bwd))										Tabs_select(Bool_.N);
		else if	(ctx.Match(k, Invk_tabs_select_fwd))										Tabs_select(Bool_.Y);
		else if	(ctx.Match(k, Invk_tabs_switch_cur_bwd))									Tabs_move(Bool_.N);
		else if	(ctx.Match(k, Invk_tabs_switch_cur_fwd))									Tabs_move(Bool_.Y);

		else if	(ctx.Match(k, Cfg__place_on_top))											tab_mgr.Btns_place_on_top_(m.ReadYn("v"));
		else if	(ctx.Match(k, Cfg__height))													{btns__height = m.ReadInt("v"); tab_mgr.Btns_height_(btns__height);}
		else if	(ctx.Match(k, Cfg__hide_if_one))											{btns__hide_if_one = m.ReadYn("v"); Tabs_hide_if_one_chk(true);}
		else if	(ctx.Match(k, Cfg__curved))													tab_mgr.Btns_curved_(m.ReadYn("v"));
		else if	(ctx.Match(k, Cfg__close_btn_visible))										tab_mgr.Btns_close_visible_(m.ReadYn("v"));
		else if	(ctx.Match(k, Cfg__unselected_close_btn_visible))							tab_mgr.Btns_unselected_close_visible_(m.ReadYn("v"));
		else if	(ctx.Match(k, Cfg__max_chars))												{btns__max_chars = m.ReadInt("v"); Btns_text_recalc();}
		else if	(ctx.Match(k, Cfg__min_chars))												{btns__min_chars = m.ReadInt("v"); Btns_text_recalc();}

		else if	(ctx.Match(k, Cfg__javascript_enabled))										Tabs_javascript_enabled_(m.ReadYnOrY("v"));	// NOTE: must be "OrY" else broken cfg.db will break cfg_maint; DATE:2016-12-15
		else if	(ctx.Match(k, Cfg__page_load_mode))											Page_load_mode_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private void Page_load_mode_(String v) {
		page_load_mode = Gxw_html_load_tid_.Xto_tid(v);
		// Gfo_evt_mgr_.Pub_val(this, Evt_load_tid_changed, load_tid);
	}

	public static final String
	  Invk_tabs_select_fwd		= "tabs_select_fwd"		, Invk_tabs_select_bwd = "tabs_select_bwd"
	, Invk_tabs_switch_cur_fwd	= "tabs_switch_cur_fwd"	, Invk_tabs_switch_cur_bwd = "tabs_switch_cur_bwd"
	, Invk_tabs_new_dflt__at_dflt__focus_y = "tabs_new_dflt__at_dflt__focus_y"
	, Invk_tabs_new_link__at_dflt__focus_n = "tabs_new_link__at_dflt__focus_n"
	, Invk_tabs_new_link__at_dflt__focus_y = "tabs_new_link__at_dflt__focus_y"
    , Invk_tabs_close_cur		= "tabs_close_cur"
	;
	private static final String
	  Cfg__place_on_top						= "xowa.gui.tabs.place_on_top"
	, Cfg__height							= "xowa.gui.tabs.height"
	, Cfg__hide_if_one						= "xowa.gui.tabs.hide_if_one"
	, Cfg__curved							= "xowa.gui.tabs.curved"
	, Cfg__close_btn_visible				= "xowa.gui.tabs.close_btn_visible"
	, Cfg__unselected_close_btn_visible		= "xowa.gui.tabs.unselected_close_btn_visible"
	, Cfg__max_chars						= "xowa.gui.tabs.max_chars"
	, Cfg__min_chars						= "xowa.gui.tabs.min_chars"
	, Cfg__javascript_enabled				= "xowa.gui.html_box.javascript_enabled"
	, Cfg__page_load_mode					= "xowa.gui.html_box.page_load_mode"
	;
}
