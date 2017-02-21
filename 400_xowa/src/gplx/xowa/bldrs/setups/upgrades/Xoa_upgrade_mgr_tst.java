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
import org.junit.*;
public class Xoa_upgrade_mgr_tst {
	@Test  public void Run() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Io_url old_history_dir = app.Usere().Fsys_mgr().App_data_dir();
		Io_url new_history_dir = app.Usere().Fsys_mgr().App_data_dir().GenSubDir("history");
		Io_mgr.Instance.SaveFilStr(old_history_dir.GenSubFil("page_history.csv"), "test");
		Xoa_upgrade_mgr.Check(app);
		Tfds.Eq("test", Io_mgr.Instance.LoadFilStr(old_history_dir.GenSubFil("page_history.csv")));	// old file still exists
		Tfds.Eq("test", Io_mgr.Instance.LoadFilStr(new_history_dir.GenSubFil("page_history.csv")));	// new file exists
		Io_mgr.Instance.SaveFilStr(new_history_dir.GenSubFil("page_history.csv"), "test1");			// dirty file
		Xoa_upgrade_mgr.Check(app);																// rerun
		Tfds.Eq("test1", Io_mgr.Instance.LoadFilStr(new_history_dir.GenSubFil("page_history.csv")));	// dirty file remains (not replaced by old file)
	}
}
