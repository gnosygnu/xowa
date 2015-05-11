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
import org.junit.*;
public class Xoa_setup_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoa_setup_mgr_fxt fxt = new Xoa_setup_mgr_fxt();
	@Test  public void Compare() {
		fxt.Test_delete_old_dir("mem/dir/", "1.8.1.1"	, "1.8.2.1", Bool_.Y);		// version is earlier than checkpoint; delete
		fxt.Test_delete_old_dir("mem/dir/", "1.8.2.1"	, "1.8.2.1", Bool_.N);		// version is not earlier than checkpoint; don't delete
		fxt.Test_delete_old_dir("mem/dir/", ""			, "1.8.2.1", Bool_.Y);		// version is empty; delete;
	}
}
class Xoa_setup_mgr_fxt {
	public void Clear() {}
	public void Test_delete_old_dir(String dir_str, String version_prv, String version_del, boolean expd) {
		Io_url dir = Io_url_.new_fil_(dir_str);
		Io_mgr._.CreateDirIfAbsent(dir);
		Xoa_setup_mgr.Delete_old_dir(Gfo_usr_dlg_.Noop, version_prv, version_del, dir);
		Tfds.Eq(expd, !Io_mgr._.ExistsDir(dir), version_prv + "|" + version_del);
	}
}
