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
		Io_mgr.Instance.CreateDirIfAbsent(dir);
		Xoa_setup_mgr.Delete_old_dir(Gfo_usr_dlg_.Noop, version_prv, version_del, dir);
		Tfds.Eq(expd, !Io_mgr.Instance.ExistsDir(dir), version_prv + "|" + version_del);
	}
}
