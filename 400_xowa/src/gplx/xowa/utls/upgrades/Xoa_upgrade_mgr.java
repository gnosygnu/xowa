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
package gplx.xowa.utls.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.utls.*;
import gplx.xowa.wikis.*;
public class Xoa_upgrade_mgr {
	public static void Check(Xoae_app app) {
		Upgrade_history(app);
	}
	public static void Check(Xowe_wiki wiki) {
		if (wiki.Domain_tid() == Xow_domain_.Tid_int_home) return;	// home wiki never needs to be migrated
		try {
			if (Bry_.Eq(wiki.Props().Bldr_version(), Bry_.Empty)) {	// version is ""; wiki must be created prior to v0.2.1; create wiki_core.gfs
				Upgrader_v00_02_01 mgr = new Upgrader_v00_02_01();
				mgr.Run(wiki);
			}
		} catch (Exception e) {wiki.Appe().Usr_dlg().Warn_many(GRP_KEY, "migrate.fail", "unknown error during migrate; ~{0} ~{1}", wiki.Domain_str(), Err_.Message_gplx_brief(e));}
	}
	private static void Upgrade_history(Xoae_app app) {
		Io_url old_history_dir = app.User().Fsys_mgr().App_data_dir();
		Io_url new_history_dir = app.User().Fsys_mgr().App_data_dir().GenSubDir("history");
		if (Io_mgr._.ExistsDir(new_history_dir)) return;	// new_history_dir exists;
		app.Usr_dlg().Log_many(GRP_KEY, "history.move.bgn", "moving history files");
		Io_url[] old_history_fils = Io_mgr._.QueryDir_args(old_history_dir).Recur_(false).ExecAsUrlAry();
		int len = old_history_fils.length;
		for (int i = 0; i < len; i++) {
			Io_url old_history_fil = old_history_fils[i];
			Io_mgr._.CopyFil(old_history_fil, new_history_dir.GenSubFil(old_history_fil.NameAndExt()), false);
		}
		app.Usr_dlg().Log_many(GRP_KEY, "history.move.end", "moved history files: ~{0}", len);
	}
	static final String GRP_KEY = "xowa.app.upgrades";
}
