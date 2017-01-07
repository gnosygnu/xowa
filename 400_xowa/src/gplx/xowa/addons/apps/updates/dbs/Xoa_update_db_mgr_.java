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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xoa_update_db_mgr_ {
	public static Io_url Url(Xoa_app app) {return app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "install", "update", "xoa_update.sqlite3");}
	public static Xoa_app_version_itm[] Select(Io_url db_url, DateAdp cutoff_date) {
		Xoa_update_db_mgr db_mgr = new Xoa_update_db_mgr(db_url);
		return db_mgr.Tbl__app_version().Select_by_date(cutoff_date.XtoStr_fmt(Xocfg_mgr.Fmt__time));
	}
	public static boolean Download_from_inet(Xoa_app app, boolean exit_if_too_soon, Io_url db_url) {
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

		// check text file to see if version changed
		Io_url trg_summary_fil = db_url.OwnerDir().GenSubFil("xoa_update.txt");
		int trg_summary_version = Bry_.To_int_or(Io_mgr.Instance.LoadFilBryOr(trg_summary_fil, Bry_.new_a7("-1")), -1);
		String src_summary_server = app.Cfg().Get_str_app_or("xowa.app.update.inet.server_url", "http://xowa.org");	// CFG:Cfg__
		byte[] src_summary_bry = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty).Exec_as_bry(src_summary_server + "/admin/app_update/xoa_update.txt");
		if (src_summary_bry == null) return false;
		int src_summary_version = Bry_.To_int(src_summary_bry);

		// download database
		if (src_summary_version > trg_summary_version) {
			String src_details_url = src_summary_server + "/admin/app_update/xoa_update.sqlite3";
			Io_url trg_details_url = db_url;
			Io_mgr.Instance.DownloadFil(src_details_url, trg_details_url);
			Io_mgr.Instance.SaveFilStr(trg_summary_fil, Int_.To_str(src_summary_version));
		}
		return true;
	}
	private static final String
	  Cfg__inet_interval				= "xowa.app.update.inet.check_interval"
	, Cfg__inet_date					= "xowa.app.update.inet.check_date";
}
