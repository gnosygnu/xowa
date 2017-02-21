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
package gplx.xowa.bldrs.setups.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_upgrade_mgr {
	public static void Check(Xoae_app app) {
		Upgrade_history(app);
	}
	private static void Upgrade_history(Xoae_app app) {
		Io_url old_history_dir = app.Usere().Fsys_mgr().App_data_dir();
		Io_url new_history_dir = app.Usere().Fsys_mgr().App_data_dir().GenSubDir("history");
		if (Io_mgr.Instance.ExistsDir(new_history_dir)) return;	// new_history_dir exists;
		app.Usr_dlg().Log_many("", "", "moving history files");
		Io_url[] old_history_fils = Io_mgr.Instance.QueryDir_args(old_history_dir).Recur_(false).ExecAsUrlAry();
		int len = old_history_fils.length;
		for (int i = 0; i < len; i++) {
			Io_url old_history_fil = old_history_fils[i];
			Io_mgr.Instance.CopyFil(old_history_fil, new_history_dir.GenSubFil(old_history_fil.NameAndExt()), false);
		}
		app.Usr_dlg().Log_many("", "", "moved history files: ~{0}", len);
	}
}
