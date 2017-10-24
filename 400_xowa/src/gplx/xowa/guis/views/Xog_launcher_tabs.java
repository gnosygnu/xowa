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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.apps.urls.*;
class Xog_launcher_tabs {
	public void Launch(Xog_win_itm win) {
		Xoae_app app = win.App(); Gfo_log_bfr log_bfr = app.Log_bfr();
		log_bfr.Add("app.launch.page.bgn");			
		Io_fil_marker fil_marker = new Io_fil_marker().Usr_dlg_(app.Usr_dlg()).Url_(app.Usere().Fsys_mgr().App_temp_dir().GenSubFil_nest("session", "launch.tabs.marker"));
		boolean tabs_restored = false;
		Xowe_wiki home_wiki = app.Usere().Wiki();
		if (fil_marker.Bgn())
			tabs_restored = Restore_tabs(app, home_wiki, win, fil_marker);
		if (!tabs_restored)
			Restore_tab_failover(app, home_wiki, win);
		// tab.Html_itm().Html_box().Focus(); // focus the html_box so wheel scroll works; DATE:2013-02-08
		app.Usr_dlg().Prog_none("", "", "");
		log_bfr.Add("app.launch.page.end");
		app.Usr_dlg().Log_wkr().Log_to_session_direct(log_bfr.Xto_str());
	}
	private boolean Restore_tabs(Xoae_app app, Xowe_wiki home_wiki, Xog_win_itm win, Io_fil_marker fil_marker) {
		Xog_startup_tabs startup_tabs = new Xog_startup_tabs().Init_by_app(app).Calc();
		String[] launch_urls = startup_tabs.Startup_urls();
		try {
			int launch_urls_len = launch_urls.length;
			for (int i = 0; i < launch_urls_len; ++i) {
				String launch_url = launch_urls[i];
				Launch_tab(win, home_wiki, launch_url);
			}
			if (startup_tabs.Startup_idx() != -1)
				app.Gui_mgr().Browser_win().Tab_mgr().Tabs_select_by_idx(startup_tabs.Startup_idx());
			fil_marker.End();
			return true;
		}
		catch (Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to launch urls: urls=~{0} err=~{1}", String_.AryXtoStr(launch_urls), Err_.Message_gplx_full(e));
			Restore_tab_failover(app, home_wiki, win);
			return false;
		}
	}
	private void Restore_tab_failover(Xoae_app app, Xowe_wiki home_wiki, Xog_win_itm win) {
		try {Launch_tab(win, home_wiki, Xog_startup_tabs.Url__home_main);}
		catch (Exception e) {app.Usr_dlg().Warn_many("", "", "failed to launch failover tab: err=~{0}", Err_.Message_gplx_full(e));}
	}
	private void Launch_tab(Xog_win_itm win, Xowe_wiki home_wiki, String launch_str) {
		Xoae_app app = win.App();
		Xoa_url launch_url = home_wiki.Utl__url_parser().Parse_by_urlbar_or_null(launch_str); if (launch_url == null) return;
		Xowe_wiki launch_wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(launch_url.Wiki_bry());
		Xoa_ttl launch_ttl = Xoa_ttl.Parse(launch_wiki, launch_url.Page_bry());
		Xog_tab_itm tab = win.Tab_mgr().Tabs_new_init(launch_wiki, Xoae_page.New(launch_wiki, launch_ttl)); // WORKAROUND: set the tab to an empty page, else null ref later; DATE:2014-07-23
		tab.Show_url_bgn(launch_url);
	}
	public static final    Xog_launcher_tabs Instance = new Xog_launcher_tabs(); Xog_launcher_tabs() {}
}
class Io_fil_marker {
	private Io_url url;
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
	public Io_fil_marker Usr_dlg_(Gfo_usr_dlg v) {this.usr_dlg = v; return this;}
	public Io_fil_marker Url_(Io_url url) {this.url = url; return this;}
	public boolean Bgn() {
		boolean rv = false;
		synchronized (this) {
			try {
				rv = !Io_mgr.Instance.ExistsFil(url);		// exists = fail; !exists = pass;
				if (rv)										// pass: file does not exist;
					Io_mgr.Instance.SaveFilStr(url, "");	// create
				else										// file exists from previous run
					Io_mgr.Instance.DeleteFil(url);			// delete
			}
			catch (Exception exc) {					// something unexpected happened
				usr_dlg.Warn_many("", "", "marker.bgn failed: url=~{0} err=~{1}", url.Raw(), Err_.Message_gplx_full(exc));
				Io_mgr.Instance.DeleteFil(url);				// try to delete it again
			}
		}
		return rv;
	}
	public void End() {
		synchronized (this) {
			try {
				Io_mgr.Instance.DeleteFil(url);				// delete
			}
			catch (Exception exc) {
				usr_dlg.Warn_many("", "", "marker.end failed: url=~{0} err=~{1}", url.Raw(), Err_.Message_gplx_full(exc));
				Io_mgr.Instance.DeleteFil(url);				// try to delete it again
			}
		}
	}
}
