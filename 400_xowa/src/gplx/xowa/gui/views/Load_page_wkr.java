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
import gplx.core.threads.*;
public class Load_page_wkr implements Gfo_thread_wkr {
	private static final Object thread_lock = new Object(); private static boolean running = false;
	private final Xog_tab_itm tab;
	public Load_page_wkr(Xog_tab_itm tab, Xowe_wiki wiki, Xoa_url url, Xoa_ttl ttl) {this.tab = tab; this.wiki = wiki; this.url = url; this.ttl = ttl;}
	public String Name() {return "xowa.load_page_wkr";}
	public boolean Resume() {return false;}
	public Xowe_wiki Wiki() {return wiki;} private final Xowe_wiki wiki;
	public Xoa_url Url() {return url;} private final Xoa_url url;
	public Xoa_ttl Ttl() {return ttl;} private final Xoa_ttl ttl;
	public Xoae_page Page() {return page;} private Xoae_page page;
	public boolean Hdump_enabled() {return hdump_enabled;} private boolean hdump_enabled;
	public Exception Exec_err() {return exec_err;} private Exception exec_err;
	public void Exec() {
		try {
			Running_(true);
			int wait_count = 0;
			while (gplx.xowa.html.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100)
				Thread_adp_.Sleep(10);
			Xoae_app app = wiki.Appe();
			app.Usr_dlg().Log_many("", "", "page.load: url=~{0}", url.To_str());
			if (Env_.System_memory_free() < app.Sys_cfg().Free_mem_when())	// check if low in memory
				Xow_wiki_.Rls_mem(wiki, false);								// clear caches (which will clear bry_bfr_mkr)
			else															// not low in memory
				app.Utl__bfr_mkr().Clear();									// clear bry_bfr_mkr only; NOTE: call before page parse, not when page is first added, else threading errors; DATE:2014-05-30
			this.page = wiki.Data_mgr().Load_page_by_ttl(url, ttl, wiki.Lang(), tab, false);
			this.hdump_enabled = wiki.Html__hdump_enabled();
			wait_count = 0;
			while (gplx.xowa.html.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100)
				Thread_adp_.Sleep(10);
			if (hdump_enabled && page.Revision_data().Html_db_id() != -1) {
				// wiki.Parser_mgr().Parse(page, false);
				wiki.Html__hdump_rdr().Get_by_ttl(page);
			}
			else
				wiki.Parser_mgr().Parse(page, false);
			GfoInvkAble_.InvkCmd_val(tab.Cmd_sync(), Xog_tab_itm.Invk_show_url_loaded_swt, this);
		}
		catch (Exception e) {
			this.exec_err = e;
			GfoInvkAble_.InvkCmd_val(tab.Cmd_sync(), Xog_tab_itm.Invk_show_url_failed_swt, this);
		}
		finally {
			Running_(false);
		}
	}
	public static boolean Running() {
		boolean rv = false;
		synchronized (thread_lock) {
			rv = running;
		}
		return rv;
	}
	private static void Running_(boolean v) {
		synchronized (thread_lock) {
			running = v;
		}
	}
}
