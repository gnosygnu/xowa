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
package gplx.xowa.apps.setups; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.versions.*;
public class Xoa_setup_mgr {
	public static void Delete_old_files(Xoae_app app) {
		String version_previous = app.Api_root().App().Env().Version_previous();
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		Io_url root_dir = app.Fsys_mgr().Root_dir();
		Delete_old_dir(usr_dlg, version_previous, "1.8.2.1"		, root_dir.GenSubDir_nest("user", "anonymous", "lang"));
		Delete_old_dir(usr_dlg, version_previous, "1.8.2.1"		, root_dir.GenSubDir_nest("user", "anonymous", "wiki", "#cfg"));
		Delete_old_dir(usr_dlg, version_previous, "1.10.2.1"	, root_dir.GenSubDir_nest("bin", "any", "javascript"));
		Delete_old_dir(usr_dlg, version_previous, "1.10.2.1"	, root_dir.GenSubDir_nest("bin", "any", "xowa", "html", "modules"));
	}
	@gplx.Internal protected static void Delete_old_dir(Gfo_usr_dlg usr_dlg, String version_prv, String version_del, Io_url dir) {
		if (Xoa_version_.Compare(version_prv, version_del) != CompareAble_.Less) return;
		usr_dlg.Log_many("", "", "setup:checking if dir exists: version_prv=~{0} version_del=~{1} dir=~{2}", version_prv, version_del, dir.Raw());
		if (!Io_mgr.I.ExistsDir(dir)) return;
		usr_dlg.Log_many("", "", "setup:deleting dir", version_prv, version_del, dir.Raw());
		Io_mgr.I.DeleteDirDeep(dir);
	}
}
