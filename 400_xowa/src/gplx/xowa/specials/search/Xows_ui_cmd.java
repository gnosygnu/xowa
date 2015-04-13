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
import gplx.threads.*; import gplx.xowa.wikis.*; import gplx.xowa.files.gui.*; import gplx.xowa.gui.views.*;
class Xows_ui_cmd implements GfoInvkAble, Cancelable, Xog_tab_close_lnr {
	private final Xows_core mgr;
	private final Xows_ui_qry qry; private final Xows_ui_rslt rslt; private final Xowe_wiki wiki; private final Xog_tab_close_mgr tab_close_mgr;
	private final boolean async; private Xows_ui_async async_wkr; private final Xog_js_wkr js_wkr;
	private Xows_db_cache cache;
	public Xows_ui_cmd(Xows_core mgr, Xows_ui_qry qry, Xowe_wiki wiki, Xog_tab_close_mgr tab_close_mgr, Xog_js_wkr js_wkr) {
		this.mgr = mgr; this.qry = qry; this.wiki = wiki; this.tab_close_mgr = tab_close_mgr; this.js_wkr = js_wkr;
		this.async = Xoa_app_.Mode_is_gui() && qry.Async_db();
		this.rslt = new Xows_ui_rslt();
		this.key = gplx.html.Html_utl.Encode_id_as_bry(Bry_.Add_w_dlm(Byte_ascii.Pipe, qry.Key(), wiki.Domain_bry()));
	}
	public byte[] Key_for_html() {return key;} private byte[] key;
	public String Wiki_domain_str() {return wiki.Domain_str();}
	public byte[] Wiki_domain_bry() {return wiki.Domain_bry();}
	public Xows_ui_rslt Rslt() {return rslt;}
	public boolean Canceled() {synchronized (this) {return canceled;}} private boolean canceled;
	public void Cancel() {
		Xoa_app_.Usr_dlg().Prog_many("", "", "search canceled: key=~{0}", key);
		synchronized (this) {
			canceled = true;
		}
	}
	public void Cancel_reset() {}
	public void Search_db() {
		tab_close_mgr.Add(this);
		mgr.Db().Search(this, qry, rslt, cache, wiki);
		mgr.Search_end(this);
		synchronized (this) {
			if (canceled) return; 	// NOTE: must check else throws SWT exception
		}
		js_wkr.Html_atr_set("xowa_cancel_" + wiki.Domain_str(), "style", "display:none;");
		Xoa_app_.Usr_dlg().Prog_many("", "", "");
	}
	public boolean Search() {
		this.cache = mgr.Get_cache_or_new(key);
		boolean rv = false;
		if (qry.Itms_end() > cache.Count() && !cache.Done()) {
			if (async)
				ThreadAdp_.invk_(this, Invk_search_db).Start();
			else
				Search_db();
			rv = true;
		}
		cache.Get_between(rslt, qry.Itms_bgn(), qry.Itms_end());
		return rv;
	}
	public boolean When_close(Xog_tab_itm tab) {
		this.Cancel();
		return true;
	}
	public void Add_rslt(Xows_db_row rslt) {
		cache.Add(rslt);
		if (async) {
			if (async_wkr == null)
				async_wkr = new Xows_ui_async(this, mgr.Html_wkr().Html_rows(), js_wkr, qry.Page_len(), wiki.Domain_bry());
			synchronized (this) {
				if (canceled) return;
			}
			async_wkr.Add(rslt);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_search_db))		Search_db();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_search_db = "search_db";
}
