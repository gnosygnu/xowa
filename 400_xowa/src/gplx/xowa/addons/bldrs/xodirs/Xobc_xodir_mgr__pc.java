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
package gplx.xowa.addons.bldrs.xodirs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app;
public class Xobc_xodir_mgr__pc implements Xobc_xodir_mgr {
	public Xobc_xodir_mgr__pc(Xoa_app app) {
	}
	public Xobc_xodir_dir[] Get_dirs(Xoa_app app) {
		int len = 2;
		String dflt = app.Fsys_mgr().Root_dir().Raw();
		String selected = app.User().User_db_mgr().Cfg().Get_app_str_or(Xobc_xodir_cfg.Key__selected_dir, dflt);
		String custom = app.User().User_db_mgr().Cfg().Get_app_str_or(Xobc_xodir_cfg.Key__custom_dir, "(choose your own folder)");
		Xobc_xodir_dir[] rv = new Xobc_xodir_dir[len];
		rv[0] = new Xobc_xodir_dir(StringUtl.Eq(selected, dflt), BoolUtl.N, BryUtl.NewU8(dflt));
		rv[1] = new Xobc_xodir_dir(StringUtl.Eq(selected, custom), BoolUtl.Y, BryUtl.NewU8(custom));
		return rv;
	}
}
