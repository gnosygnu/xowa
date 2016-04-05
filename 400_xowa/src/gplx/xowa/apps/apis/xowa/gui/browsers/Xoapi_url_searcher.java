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
package gplx.xowa.apps.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.gfui.*;
import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*; import gplx.xowa.addons.apps.searchs.searchers.cbks.*; 
import gplx.xowa.apps.apis.xowa.addons.searchs.*;
import gplx.xowa.guis.views.*;
class Xoapi_url_searcher implements GfoEvObj {
	private final    Xoae_app app;
	private final    Xoapi_url_bar url_bar_api; private Srch_search_addon addon;
	private final    GfuiComboBox url_bar;
	public Xoapi_url_searcher(Xoae_app app) {	// called by Init_by_kit
		this.app = app;
		this.evMgr = GfoEvMgr.new_(this);
		this.url_bar = app.Gui_mgr().Browser_win().Url_box();
		this.url_bar_api = app.Api_root().Addon().Search().Url_bar();
		GfoEvMgr_.SubSame_many(url_bar_api, this, Xoapi_url_bar.Evt__jump_len_changed, Xoapi_url_bar.Evt__visible_rows_changed, Xoapi_url_bar.Evt__ns_ids_changed);
		url_bar.Items__jump_len_(url_bar_api.Jump_len());
		url_bar.Items__visible_rows_(url_bar_api.Visible_rows());
	}
	public GfoEvMgr		EvMgr()					{return evMgr;}				private final    GfoEvMgr evMgr;
	public void Search() {
		if (!url_bar_api.Enabled()) return;
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
		if (!url_bar.List_visible()) url_bar.Items__size_to_fit(url_bar_api.Max_results());	// resize offscreen; handles 1st search when dropdown flashes briefly in middle of screen before being moved under bar; DATE:2016-03-24
		Srch_search_qry qry = Srch_search_qry.New__url_bar(wiki, url_bar_api, Bry_.new_u8(search_str));
		Srch_rslt_cbk__url_bar cbk = new Srch_rslt_cbk__url_bar(app, url_bar, url_bar_api);
		Xoa_app_.Usr_dlg().Prog_one("", "", "searching (please wait): ~{0}", search_str);
		addon.Search(qry, cbk);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Xoapi_url_bar.Evt__jump_len_changed)) 			{url_bar.Items__jump_len_(m.ReadInt("v"));}
		else if	(ctx.Match(k, Xoapi_url_bar.Evt__visible_rows_changed)) 		{url_bar.Items__visible_rows_(m.ReadInt("v"));}
		else if	(ctx.Match(k, Xoapi_url_bar.Evt__ns_ids_changed)) 				{if (addon != null) addon.Clear_rslts_cache();}	// invalidate cache when ns changes; else ns_0 rslts will show up in ns_100; DATE:2016-03-24
		return this;
	}
}
