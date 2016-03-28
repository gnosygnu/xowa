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
