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
package gplx.xowa.addons.bldrs.xodirs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
public class Xobc_xodir_cfg {
	public static final String 
	  Key__selected_dir = "xowa.xodir.selected_dir"
	, Key__custom_dir = "xowa.xodir.custom_dir"
	;
	public static void Set_app_str__selected(Xoa_app app, byte[] val_bry) {
		// if wnt, replace "\"; note that url-encoding while navigating dirs will always convert "\" to "/"
		if (gplx.core.envs.Op_sys.Cur().Tid_is_wnt()) val_bry = Bry_.Replace(val_bry, Byte_ascii.Slash, Byte_ascii.Backslash);

		app.User().User_db_mgr().Cfg().Set_app_bry(Xobc_xodir_cfg.Key__selected_dir, val_bry);
	}
}
