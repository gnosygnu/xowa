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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cmd_mgr_invk implements Gfo_invk {
	private Xoae_app app; private Xog_cmd_mgr cmd_mgr;
	public void Ctor(Xoae_app app, Xog_cmd_mgr cmd_mgr) {this.app = app; this.cmd_mgr = cmd_mgr;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Xog_cmd_itm cmd_itm = cmd_mgr.Get_or_null(k);
		if (cmd_itm == null) return Gfo_invk_.Rv_unhandled;
		return app.Gfs_mgr().Run_str(cmd_itm.Cmd());
	}
}
