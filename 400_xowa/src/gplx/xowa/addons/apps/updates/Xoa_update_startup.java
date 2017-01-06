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
package gplx.xowa.addons.apps.updates; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xoa_update_startup {
	public static boolean Show_at_startup(Xoa_app app) {
		try {
			// exit if disabled
			Xocfg_mgr cfg = app.Cfg();
			if (!cfg.Get_bool_app_or(Cfg__enabled, true)) return false;

			// get inet_interval and date
			int inet_interval = cfg.Get_int_app_or(Cfg__inet_interval, 7);
			DateAdp inet_date = cfg.Get_date_app_or(Cfg__inet_date, DateAdp_.MinValue);

			// if enough time has passed, check internet for update_db
			Io_url db_url = Xoa_update_db.Url(app);
			if (Datetime_now.Get().Diff_days(inet_date) > inet_interval) {
				Xoa_update_db.Download_from_inet(app, db_url);
				cfg.Set_date_app(Cfg__inet_date, Datetime_now.Get());
				return true;
			}

			// check if there are versions to update
			DateAdp cutoff_date = cfg.Get_date_app_or(Cfg__cutoff_date, DateAdp_.parse_fmt(Xoa_app_.Build_date, Xoa_app_.Build_date_fmt));
			if (Xoa_update_db.Select(db_url, cutoff_date).length > 0)
				return true;
		} catch (Exception exc) {Gfo_usr_dlg_.Instance.Warn_many("", "", "starup:fatal error while looking up app-update-reminder; err=~{0}", Err_.Message_gplx_log(exc));}
		return false;
	}
	public static void Set_cutoff_date_to_now(Xoa_app app) {
		app.Cfg().Set_date_app(Cfg__cutoff_date, Datetime_now.Get());
	}

	private static final String
	  Cfg__enabled						= "xowa.app.update.startup.enabled"
	, Cfg__inet_interval				= "xowa.app.update.startup.inet_interval"
	, Cfg__inet_date					= "xowa.app.update.startup.inet_date"
	, Cfg__cutoff_date					= "xowa.app.update.startup.cutoff_date";
}
