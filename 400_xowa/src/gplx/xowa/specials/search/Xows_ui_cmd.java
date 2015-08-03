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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.threads.*; import gplx.html.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.gui.*; import gplx.xowa.gui.views.*;
class Xows_ui_cmd implements GfoInvkAble, Cancelable, Xog_tab_close_lnr {
	private final Xows_core mgr; private final Xows_ui_qry qry; private final Xows_ui_rslt rslt; 
	private final Xow_wiki wiki; private final Xoae_page page; private final Xog_tab_close_mgr tab_close_mgr; private final Xog_js_wkr js_wkr;
	private final boolean async; private Xows_ui_async async_wkr; 
	private Xows_db_cache cache;
	public Xows_ui_cmd(Xows_core mgr, Xows_ui_qry qry, Xow_wiki wiki, Xoae_page page, Xog_tab_close_mgr tab_close_mgr, Xog_js_wkr js_wkr) {
		this.mgr = mgr; this.qry = qry; this.wiki = wiki; this.page = page; this.tab_close_mgr = tab_close_mgr; this.js_wkr = js_wkr;
		this.async = wiki.App().App_type().Uid_is_gui() && qry.Async_db();
		this.rslt = new Xows_ui_rslt();
		this.key = Html_utl.Encode_id_as_bry(Bry_.Add(qry.Key(), Byte_ascii.Pipe_bry, wiki.Domain_bry()));
	}
	public byte[] Key() {return key;} private final byte[] key;
	public Xow_wiki Wiki() {return wiki;}
	public Xows_ui_rslt Rslt() {return rslt;}
	public boolean Canceled() {return canceled;} private boolean canceled;
	public void Cancel() {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search canceled: key=~{0}", key);
		canceled = true;
		this.Hide_cancel_btn();
	}
	public void Cancel_reset() {}
	public boolean Search() {
		this.cache = mgr.Get_cache_or_new(key);
		boolean rv = false, fill_from_cache = true;
		if (!cache.Done() && (qry.Itms_end() > cache.Itms_end())) {
			if (async) {
				fill_from_cache = false; // NOTE: do not retrieve cached results to page, else ui_async cmd will add out of order; DATE:2015-04-24
				if (async_wkr == null) async_wkr = new Xows_ui_async(this, new Xows_html_row(new gplx.xowa.html.wtrs.Xoh_lnki_bldr(wiki.App(), wiki.App().Html__href_wtr())), js_wkr, qry.Page_len(), wiki.Domain_bry());
				Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_search_db, this, Invk_search_db).Start();
			}
			else
				Search_db();
			rv = true;
		}
		if (fill_from_cache) {
			cache.Get_between(rslt, qry.Itms_bgn(), qry.Itms_end());
			qry.Page_max_(cache.Count() / qry.Page_len());
		}
		return rv;
	}
	public void Search_db() {
		tab_close_mgr.Add(this);
		if (async) {
			while (!page.Html_data().Mode_wtxt_shown())	// NOTE:must check for wtxt_shown else async can happen first, and then be overwritten by wtxt; DATE:2015-04-26
				Thread_adp_.Sleep(100);
			// show any existing items in the cache on screen; new updates wil bump these off; SEE:NOTE:show_existing;DATE:2015-04-26
			int qry_itms_bgn = qry.Itms_bgn(), cache_count = cache.Count();
			for (int i = qry_itms_bgn; i < cache_count; ++i)
				async_wkr.Add(cache.Get_at(i));
		}
		new Xows_db_wkr().Search(this, qry, rslt, cache, wiki);
		mgr.Search_end(this);
		if (this.Canceled()) return; 	// NOTE: must check else throws SWT exception
		this.Hide_cancel_btn();
		if (cache.Done())
			qry.Page_max_(cache.Count() / qry.Page_len());
		Xoa_app_.Usr_dlg().Prog_many("", "", "");
	}
	private void Hide_cancel_btn() {
		Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_search_cancel, this, Invk_hide_cancel).Start();
	}
	private void Hide_cancel_btn_async() {
		js_wkr.Html_atr_set("xowa_cancel_" + wiki.Domain_str(), "style", "display:none;");
	}
	public void Add_rslt(Xows_db_row rslt) {
		cache.Add(rslt);
		if (async) async_wkr.Add(rslt);
	}
	public boolean When_close(Xog_tab_itm tab, Xoa_url url) {
		if (url != Xoa_url.Null) {	// not called by close_tab (Ctrl+W)
			byte[] cancel_arg = url.Qargs_mgr().Get_val_bry_or(Xows_arg_mgr.Arg_bry_cancel, null);
			if (cancel_arg != null)	return true; // cancel arg exists; assume tab is not being closed; note that cancel will be processed by Xows_page__special; DATE:2015-04-30
		}
		this.Cancel();
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
