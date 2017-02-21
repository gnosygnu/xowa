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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Scrib_fsys_mgr_tst {
	@Before public void init() {fxt.Clear();} private Scrib_fsys_mgr_fxt fxt = new Scrib_fsys_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init("mem/xowa/"
		, "mw.lua"
		, "mw.ustring.lua"
		, "ustring/ustring.lua"
		, "ustring/normalization-data.lua"
		, "ustring/README"
		);
		fxt.Test_exists_y("mw", "mw.ustring", "ustring", "ustring/normalization-data");
		fxt.Test_exists_n("README");
	}
}
class Scrib_fsys_mgr_fxt {
	private Scrib_fsys_mgr fsys_mgr;
	public void Clear() {
		Io_mgr.Instance.InitEngine_mem();
		fsys_mgr = new Scrib_fsys_mgr();
	}
	public Scrib_fsys_mgr_fxt Init(String root_dir, String... rel_paths) {
		Io_url root_url = Io_url_.mem_dir_(root_dir);
		fsys_mgr.Root_dir_(root_url);
		Io_url script_dir = fsys_mgr.Script_dir();
		int rel_paths_len = rel_paths.length;
		for (int i = 0; i < rel_paths_len; i++) {
			String rel_path = rel_paths[i];
			Io_url fil_url = script_dir.GenSubFil(rel_path);
			Io_mgr.Instance.SaveFilStr(fil_url, fil_url.Raw());
		}
		return this;
	}
	public Scrib_fsys_mgr_fxt Test_exists_y(String... keys) {Test_exists(Bool_.Y, keys); return this;}
	public Scrib_fsys_mgr_fxt Test_exists_n(String... keys) {Test_exists(Bool_.N, keys); return this;}
	private void Test_exists(boolean expd, String[] keys) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; i++) {
			String key = keys[i];
			String code = fsys_mgr.Get_or_null(key);
			Tfds.Eq(expd, code != null, key);
		}
	}
}
