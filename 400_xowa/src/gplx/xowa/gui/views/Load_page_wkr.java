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
	private Xog_tab_itm tab;
	public Load_page_wkr(Xog_tab_itm tab, Xowe_wiki wiki, Xoa_url url, Xoa_ttl ttl) {this.tab = tab; this.wiki = wiki; this.url = url; this.ttl = ttl;}
	public String Name() {return "xowa.load_page_wkr";}
	public boolean Resume() {return false;}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoae_page Page() {return page;} private Xoae_page page;
	public Xoa_url Url() {return url;} private Xoa_url url;
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public boolean Hdump_enabled() {return hdump_enabled;} private boolean hdump_enabled;
	public Exception Exc() {return exc;} private Exception exc;
	private final static Object thread_lock = new Object();
	public static boolean Running() {
		boolean rv = false;
		synchronized (thread_lock) {
			rv = running;
		}
		return rv;
	}	private static boolean running = false;
	private static void Running_(boolean v) {
		synchronized (thread_lock) {
			running = v;
		}
	}
	public void Exec() {
		try {
			Running_(true);
			int wait_count = 0;
			while (gplx.xowa.html.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100) {
				Thread_adp_.Sleep(10);
			}
			Xoae_app app = wiki.Appe();
			app.Usr_dlg().Log_many("", "", "page.load: url=~{0}", url.Xto_full_str_safe());
			if (Env_.System_memory_free() < app.Sys_cfg().Free_mem_when())	// check if low in memory
				app.Free_mem(false);										// clear caches (which will clear bry_bfr_mk)
			else															// not low in memory
				app.Utl__bfr_mkr().Clear();									// clear bry_bfr_mk only; NOTE: call before page parse, not when page is first added, else threading errors; DATE:2014-05-30
			this.page = wiki.GetPageByTtl(url, ttl, wiki.Lang(), tab, false);
			int html_db_id = page.Revision_data().Html_db_id();
			if (wiki.Html__hdump_enabled())
				hdump_enabled = true;
			wait_count = 0;
			while (gplx.xowa.html.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100) {
				Thread_adp_.Sleep(10);
			}
			if (wiki.Html__hdump_enabled() && html_db_id != -1) {
				wiki.ParsePage(page, false);
//					wiki.Html__hdump_rdr().Get_by_ttl(page);
			}
			else
				wiki.ParsePage(page, false);
			GfoInvkAble_.InvkCmd_val(tab.Cmd_sync(), Xog_tab_itm.Invk_show_url_loaded_swt, this);
		}
		catch (Exception e) {
			this.exc = e;
			GfoInvkAble_.InvkCmd_val(tab.Cmd_sync(), Xog_tab_itm.Invk_show_url_failed_swt, this);
		}
		finally {
			Running_(false);
		}
	}
}
