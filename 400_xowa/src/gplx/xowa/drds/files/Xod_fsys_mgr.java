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
