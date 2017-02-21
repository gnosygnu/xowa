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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.langs.jsons.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xoa_update_db_mgr_ {
	public static Io_url Url(Xoa_app app) {return app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "install", "update", "xoa_update.sqlite3");}
	public static Xoa_app_version_itm[] Select(Io_url db_url, int id) {
		Xoa_update_db_mgr db_mgr = new Xoa_update_db_mgr(db_url);
		return db_mgr.Tbl__app_version().Select_by_id(id);
	}
	public static boolean Download_from_inet(Xoa_app app, boolean exit_if_too_soon, Io_url db_url) {
		try {
			// exit if web_access disabled
			if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return false;

			// exit if inet checked too soon
			if (exit_if_too_soon) {
				Xocfg_mgr cfg = app.Cfg();
				int inet_interval = cfg.Get_int_app_or(Cfg__inet_interval, 7);
				DateAdp inet_date = cfg.Get_date_app_or(Cfg__inet_date, DateAdp_.MinValue);
				if (Datetime_now.Get().Diff_days(inet_date) < inet_interval) return false;
				cfg.Set_date_app(Cfg__inet_date, Datetime_now.Get());
			}

			// get online version
			String latest_version_url = app.Cfg().Get_str_app_or("xowa.app.update.inet.server_url", "http://xowa.org");	// CFG:Cfg__
			byte[] latest_version_bry = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty).Exec_as_bry(latest_version_url + "/admin/app_update/xoa_update.json");
			Json_doc jdoc = new Json_parser().Parse(latest_version_bry);
			int latest_version_id = jdoc.Root_nde().Get_as_int_or("version_id", -1);

			// compare and download if necessary
			if (latest_version_id > Xoa_app_.Version_id) {
				String update_db_url = latest_version_url + "/admin/app_update/xoa_update.sqlite3";
				Io_mgr.Instance.DownloadFil(update_db_url, db_url);
			}
			return true;
		} catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "app_update:db download failed;err=~{0}", Err_.Message_gplx_log(exc));
			return false;
		}
	}
	private static final String
	  Cfg__inet_interval				= "xowa.app.update.inet.check_interval"
	, Cfg__inet_date					= "xowa.app.update.inet.check_date";
}
