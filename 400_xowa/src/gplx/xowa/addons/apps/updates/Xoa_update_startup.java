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
