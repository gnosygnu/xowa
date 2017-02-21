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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*; import gplx.xowa.bldrs.setups.addons.*;
import gplx.xowa.bldrs.setups.maints.*;
public class Xoi_setup_mgr implements Gfo_invk {
	public Xoi_setup_mgr(Xoae_app app) {
		this.app = app;
		cmd_mgr = new Xoi_cmd_mgr(this);
		maint_mgr = new Xoa_maint_mgr(app);
	}
	public void Init_by_app(Xoae_app app) {
		addon_mgr.Init_by_app(app);
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xoi_cmd_mgr Cmd_mgr() {return cmd_mgr;} private Xoi_cmd_mgr cmd_mgr;
	public Xoi_addon_mgr Addon_mgr() {return addon_mgr;} private Xoi_addon_mgr addon_mgr = new Xoi_addon_mgr();
	public Xoa_maint_mgr Maint_mgr() {return maint_mgr;} private Xoa_maint_mgr maint_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cmds))								return cmd_mgr;
		else if	(ctx.Match(k, Invk_addons))								return addon_mgr;
		else if	(ctx.Match(k, Invk_maint))								return maint_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}
	static final String Invk_cmds = "cmds", Invk_addons = "addons", Invk_maint = "maint";
	static final String GRP_KEY = "xowa.setup";
}
