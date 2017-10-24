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
package gplx.xowa.drds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
public class Xod_fsys_mgr {
	public Xod_fsys_mgr(Gfo_log log, Xod_activity_adp activity) {
		this.usr_data_dir = Io_url_.lnx_dir_(activity.Fsys__files_dir() + "/");
		// this.usr_temp_dir = Io_url_.lnx_dir_(activity.Fsys__cache_dir() + "/");
		this.usr_data_fil = usr_data_dir.GenSubFil_nest("usr-anonymous.sqlite3");			// should go to /xowa/usr/usr-anonymous.sqlite3
		this.app_root_dir = usr_data_dir.GenSubDir_nest("files", "xowa");
		String sdcard_rw = activity.Fsys__sdcard_rw_or_null();
		if (sdcard_rw != null) {
			app_root_dir = Io_url_.lnx_dir_(sdcard_rw + "files/xowa/");
		}
		log.Info("fsys_mgr:root_dir", "root", app_root_dir.Xto_api());
	}
	public Io_url Usr_data_dir() {return usr_data_dir;} private Io_url usr_data_dir;		// cleared by "Clear data"; maps to both @gplx.Internal protected and external storage
	// private Io_url Usr_temp_dir() {return usr_temp_dir;} private Io_url usr_temp_dir;	// cleared by "Clear cache"
	public Io_url Usr_data_fil() {return usr_data_fil;} private Io_url usr_data_fil;
	public Io_url App_root_dir() {return app_root_dir;} private Io_url app_root_dir;
}
