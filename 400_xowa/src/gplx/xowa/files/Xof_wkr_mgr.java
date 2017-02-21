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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.fsdb.*;
import gplx.xowa.files.fsdb.fs_roots.*;
class Xof_wkr_mgr implements Gfo_invk {
	private Xow_file_mgr file_mgr;
	public Xof_wkr_mgr(Xow_file_mgr file_mgr) {this.file_mgr = file_mgr;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_new(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
	private Xof_fsdb_mgr Get_or_new(String key) {
		if (String_.Eq(key, "fs.dir")) {
			return Fs_root_core.Set_fsdb_mgr(file_mgr, file_mgr.Wiki());
		}
		else
			throw Err_.new_unhandled(key);
	}
}
