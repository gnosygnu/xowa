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
package gplx.xowa.addons.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*;
import gplx.core.threads.*;
import gplx.xowa.files.gui.*; import gplx.xowa.guis.views.*;
import gplx.xowa.addons.searchs.specials.htmls.*; import gplx.xowa.addons.searchs.searchers.*; import gplx.xowa.addons.searchs.searchers.rslts.*;
public class Srch_special_cmd implements GfoInvkAble, Srch_rslt_cbk, Xog_tab_close_lnr {
	private final    Srch_special_searcher mgr; private final    Srch_search_qry qry;
	public final    Xow_wiki wiki; private final    Xog_tab_close_mgr tab_close_mgr; private final    Xog_js_wkr js_wkr;
	private Srch_html_row_wkr html_row_wkr; private final    boolean async; 
	public final    byte[] key; private boolean canceled = false;
	public Srch_special_cmd(Srch_special_searcher mgr, Srch_search_qry qry, Xow_wiki wiki, Xog_tab_close_mgr tab_close_mgr, Xog_js_wkr js_wkr, byte[] key, boolean search_is_async) {
		this.mgr = mgr; this.qry = qry; this.wiki = wiki; this.tab_close_mgr = tab_close_mgr; this.js_wkr = js_wkr; this.key = key;
		this.async = wiki.App().Mode().Tid_is_gui() && search_is_async;
	}
	public void On_cancel() {
		canceled = true;
		Xoa_app_.Usr_dlg().Prog_many("", "", "search canceled: key=~{0}", key);
		this.Hide_cancel_btn();
	}
	public void Search() {
		if (async) {	// NOTE: async useful with multiple wikis; allows parallel searches;
			Srch_html_row_bldr html_row_bldr = new Srch_html_row_bldr(new gplx.xowa.htmls.core.htmls.utls.Xoh_lnki_bldr(wiki.App(), wiki.App().Html__href_wtr()));
			html_row_wkr = new Srch_html_row_wkr(html_row_bldr, js_wkr, qry.Slab_end - qry.Slab_bgn, wiki.Domain_bry());
			Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_search_db, this, Invk_search_db).Start();
		}
		else
			Search_db();
	}
	private void Search_db() {
		synchronized (mgr) {	// THREAD: needed else multiple Special:Search pages will fail at startup; DATE:2016-03-27
			tab_close_mgr.Add(this);
			// DEPRECATE: causes search to fail when using go back / go forward; DELETE:2016-05; DATE:2016-03-27
			//	if (async) {
			//		while (!page.Html_data().Mode_wtxt_shown())	// NOTE:must check to see if page is shown; else async can happen first, and then be overwritten by page_showing; DATE:2015-04-26
			//		Thread_adp_.Sleep(100);
			//	}
			Srch_search_addon.Get(wiki).Search(qry, this);
			mgr.Search__done(this);
			if (canceled) return; 	// NOTE: must check else throws SWT exception
			this.Hide_cancel_btn();
		}
		Xoa_app_.Usr_dlg().Prog_many("", "", "");
	}
	private void Hide_cancel_btn()			{Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_search_cancel, this, Invk_hide_cancel).Start();}
	private void Hide_cancel_btn_async()	{js_wkr.Html_atr_set("xowa_cancel_" + wiki.Domain_str(), "style", "display:none;");}
	public void On_rslts_found(Srch_search_qry qry, Srch_rslt_list rslts_list, int rslts_bgn, int rslts_end) {
		if (rslts_list.Rslts_are_first) {
			if (rslts_bgn > qry.Slab_bgn) {
				for (int i = qry.Slab_bgn; i < rslts_bgn; ++i) {
					Srch_rslt_row row = rslts_list.Get_at(i);
					html_row_wkr.On_rslt_found(row);
				}
			}
		}
		for (int i = rslts_bgn; i < rslts_end; ++i) {
			if (i < qry.Slab_bgn) continue;	// do not write row if < slab_bgn; occurs when restarting app directly at page > 1; EX: 11-20 requested; 1-20 returned; do not write 1-10;
			if (i >= qry.Slab_end) break;	// do not write row if > slab_end; occurs when paging forward; EX: 01-10 requested; 1-12 retrieved; do not write 11, 12
			Srch_rslt_row row = rslts_list.Get_at(i);
			html_row_wkr.On_rslt_found(row);
		}
	}
	public boolean When_close(Xog_tab_itm tab, Xoa_url url) {
		if (url != Xoa_url.Null) {	// not called by close_tab (Ctrl+W)
			byte[] cancel_arg = url.Qargs_mgr().Get_val_bry_or(Srch_qarg_mgr.Bry__cancel, null);
			if (cancel_arg != null)	return true; // cancel arg exists; assume tab is not being closed; note that cancel will be processed by Xows_page__special; DATE:2015-04-30
		}
		this.On_cancel();
		return true;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_search_db))		Search_db();
		else if	(ctx.Match(k, Invk_hide_cancel))	Hide_cancel_btn_async();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_search_db = "search_db", Invk_hide_cancel = "hide_cancel";
}
/*
NOTE:show_existing. code needed to show A1
EX: search="A*": "A" has 400 words; "A1" has 1;
. search 1-20 returns 20 words for A and 1 word for A1.
.. the 1st A word has a len of 999 and the 20th A word has a length of 900;
.. A1 has a length of 799
. search 21-40 returns 20 words for A
.. the 21st word has a len of 899 and the 40th has a len of 800
.. A1 should show up briefly, and then get pushed off screen by 21-40
. search 61-40 returns 20 words for A
.. A1 must show up
*/
