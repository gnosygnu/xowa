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
import gplx.xowa.addons.apps.updates.dbs.*;
public class Xoa_update_startup {
	public static boolean Show_at_startup(Xoa_app app) {
		try {
			// set default to this version
			Xocfg_mgr cfg = app.Cfg();
			cfg.Dflt_mgr().Add(Cfg__version_cutoff, Int_.To_str(Xoa_app_.Version_id));

			// exit if disabled
			if (!cfg.Get_bool_app_or(Cfg__enabled, true)) return false;

			// check online for updates
			Io_url db_url = Xoa_update_db_mgr_.Url(app);
			Xoa_update_db_mgr_.Download_from_inet(app, Bool_.Y, db_url);
			
			// check offline for updates
			int version_cutoff = cfg.Get_int_app_or(Cfg__version_cutoff, Xoa_app_.Version_id);
			return Xoa_update_db_mgr_.Select(db_url, version_cutoff).length > 0;
		} catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "starup:fatal error while looking up app-update-reminder; err=~{0}", Err_.Message_gplx_log(exc));
			return false;
		}
	}
	public static int Version_cutoff(Xoa_app app)			{return app.Cfg().Get_int_app_or(Cfg__version_cutoff, Xoa_app_.Version_id);}
	public static void Version_cutoff_(Xoa_app app, int id) {app.Cfg().Set_int_app(Cfg__version_cutoff, id);}

	private static final String
	  Cfg__enabled						= "xowa.app.update.startup.enabled"
	, Cfg__version_cutoff				= "xowa.app.update.startup.version_cutoff";
}
