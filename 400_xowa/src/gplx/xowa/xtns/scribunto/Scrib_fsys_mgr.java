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
public class Scrib_fsys_mgr {
	private Ordered_hash libs;
	private Io_url[] lib_dirs = new Io_url[lib_dirs_len]; static final int lib_dirs_len = 3;
	public Io_url Root_dir() {return root_dir;} private Io_url root_dir;
	public Io_url Script_dir() {return script_dir;} private Io_url script_dir;
	public void Root_dir_(Io_url v) {
		root_dir = v;
		script_dir = root_dir.GenSubDir_nest("engines", "LuaCommon", "lualib");
		lib_dirs[0] = script_dir;
		lib_dirs[1] = script_dir.GenSubDir("luabit");
		lib_dirs[2] = script_dir.GenSubDir("ustring");
	}
	public String Get_or_null(String name) {
		if (libs == null) libs = libs_init(script_dir);
		Object lib_fil_obj = libs.Get_by(name); if (lib_fil_obj == null) return null;
		gplx.core.ios.Io_fil lib_fil = (gplx.core.ios.Io_fil)lib_fil_obj;
		String lib_data = lib_fil.Data();
		if (lib_data == null) {
			lib_data = Io_mgr.Instance.LoadFilStr(lib_fil.Url());
			lib_fil.Data_(lib_data);
		}
		return lib_data;
	}
	private static Ordered_hash libs_init(Io_url script_dir) {
		Ordered_hash rv = Ordered_hash_.New();
		Io_url[] fils = Io_mgr.Instance.QueryDir_args(script_dir).Recur_().ExecAsUrlAry();
		int fils_len = fils.length;
		for (int i = 0; i < fils_len; i++) {
			Io_url fil = fils[i];
			if (!String_.Eq(fil.Ext(), ".lua")) continue;	// ignore readme.txt, readme
			gplx.core.ios.Io_fil fil_itm = new gplx.core.ios.Io_fil(fil, null);
			rv.Add_if_dupe_use_1st(fil.NameOnly(), fil_itm);
			rv.Add_if_dupe_use_1st(String_.Replace(String_.DelEndIf(fil.GenRelUrl_orEmpty(script_dir), ".lua"), "\\", "/"), fil_itm);
		}
		return rv;
	}
	public void Shrink() {
		int len = libs.Count();
		for (int i = 0; i < len; i++) {
			gplx.core.ios.Io_fil fil = (gplx.core.ios.Io_fil)libs.Get_at(i);
			fil.Url_(null).Data_(null);
		}
	}
}
