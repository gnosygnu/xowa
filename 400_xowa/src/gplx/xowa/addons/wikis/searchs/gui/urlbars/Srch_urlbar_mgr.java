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
package gplx.xowa.addons.wikis.searchs.gui.urlbars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.gui.*;
import gplx.gfui.controls.standards.*;
import gplx.gfui.kits.core.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.cbks.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.guis.views.*;
public class Srch_urlbar_mgr implements Gfo_invk {	// NOTE: needs to be app-level b/c binding to key events in urlbar
	private Srch_search_addon addon;
	private Xoae_app app;
	private GfuiComboBox url_bar;
	private boolean enabled = true;
	private int max_results = 10;
	private boolean auto_wildcard = true;
	private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr().Add_main_if_empty();
	private Srch_crt_scanner_syms syms = Srch_crt_scanner_syms.New__dflt();
	private void Ns_ids_(String s) {
		int[] ns_ids = Int_.Ary_empty;
		if (String_.Eq(s, "*")) {}	// leave as int[0]; ns_mgr will interpret as wildcard
		else {
			ns_ids = Int_.Ary_parse(s, ",");
		}
		ns_mgr.Add_by_int_ids(ns_ids);
		if (addon != null) addon.Clear_rslts_cache();	// invalidate cache when ns changes; else ns_0 rslts will show up in ns_100; DATE:2016-03-24
	}

	public void Init_by_kit(Xoae_app app, Gfui_kit kit) {
		// get url_bar and set defaults
		this.url_bar = app.Gui_mgr().Browser_win().Url_box();
		url_bar.Items__jump_len_(5);
		url_bar.Items__visible_rows_(10);

		this.app = app;
		app.Cfg().Bind_many_app(this, Cfg__enabled, Cfg__max_results, Cfg__auto_wildcard, Cfg__ns_ids, Cfg__visible_rows, Cfg__jump_len);
	}
	public void Search() {
		if (!enabled) return;
		Xog_tab_itm active_tab = app.Gui_mgr().Browser_win().Tab_mgr().Active_tab(); if (active_tab == null) return;
		Xow_wiki wiki = active_tab.Wiki();

		String search_str = url_bar.Text();
		url_bar.Text_fallback_(search_str);

		// remove "en.wikipedia.org/wiki/"
		// String url_bgn = wiki.Domain_str() + gplx.xowa.htmls.hrefs.Xoh_href_.Str__wiki;
		// if (String_.Has_at_bgn(search_str, url_bgn))
		//	search_str = String_.Mid(search_str, String_.Len(url_bgn));
		if (String_.Len_eq_0(search_str)) {
			url_bar.Items__update(String_.Ary_empty);
			return;
		}

		if (addon == null) {
			addon = Srch_search_addon.Get(wiki);
		}
		else {
			if (!Bry_.Eq(wiki.Domain_bry(), addon.Wiki_domain()))	// NOTE: url_bar_api caches addon at wiki level; need to check if wiki has changed
				addon = Srch_search_addon.Get(wiki);
		}
		if (addon.Db_mgr().Cfg().Version_id__needs_upgrade()) return;	// exit early, else will flash "searching" message below; note that url-bar should not trigger upgrade;
		url_bar.List_sel_idx_(0);	// clear selected list item; EX: search "a" -> page down; sel is row #5 -> search "b" -> sel should not be #5; DATE:2016-03-24
		if (!url_bar.List_visible()) url_bar.Items__size_to_fit(max_results);	// resize offscreen; handles 1st search when dropdown flashes briefly in middle of screen before being moved under bar; DATE:2016-03-24

		Srch_search_qry qry = Srch_search_qry.New__url_bar(wiki, ns_mgr, syms, auto_wildcard, max_results, Bry_.new_u8(search_str));
		Srch_rslt_cbk__url_bar cbk = new Srch_rslt_cbk__url_bar(app, url_bar, max_results);
		Xoa_app_.Usr_dlg().Prog_one("", "", "searching (please wait): ~{0}", search_str);
		addon.Search(qry, cbk);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__enabled)) 						enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__max_results))					max_results = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__auto_wildcard))					auto_wildcard = m.ReadYn("v");
		else if (ctx.Match(k, Cfg__ns_ids))							Ns_ids_(m.ReadStr("v"));
		else if	(ctx.Match(k, Cfg__visible_rows))					url_bar.Items__visible_rows_(m.ReadInt("v"));
		else if (ctx.Match(k, Cfg__jump_len))						url_bar.Items__jump_len_(m.ReadInt("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__enabled				= "xowa.gui.url_bar.search.enabled"
	, Cfg__max_results			= "xowa.gui.url_bar.search.max_results"
	, Cfg__auto_wildcard		= "xowa.gui.url_bar.search.auto_wildcard"
	, Cfg__ns_ids				= "xowa.gui.url_bar.search.ns_ids"
	, Cfg__visible_rows			= "xowa.gui.url_bar.search.visible_rows"
	, Cfg__jump_len				= "xowa.gui.url_bar.search.jump_len"
	;
}
