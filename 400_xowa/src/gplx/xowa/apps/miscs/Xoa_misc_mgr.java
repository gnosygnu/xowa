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
package gplx.xowa.apps.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_misc_mgr implements Gfo_invk {
	private Xoa_app app;
	public void Init_by_app(Xoa_app app) {
		this.app = app;
		app.Cfg().Bind_many_app(this, Cfg__web_enabled, Cfg__logs_enabled, Cfg__script);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__web_enabled))	gplx.core.ios.IoEngine_system.Web_access_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__logs_enabled)) {
			if (app.Tid_is_edit()) {
				Xoae_app appe = (Xoae_app)app;
				boolean logs_enabled = m.ReadYn("v");
				appe.Log_wtr().Enabled_(logs_enabled);
				if (!logs_enabled)
					Io_mgr.Instance.DeleteFil_args(appe.Log_wtr().Session_fil()).MissingFails_off().Exec();
			}
		}
		else if	(ctx.Match(k, Cfg__script)) {
			String script = m.ReadStr("v");
			Object rslt = app.Gfs_mgr().Run_str(script);
			if (rslt == Gfo_invk_.Rv_error) {
				app.Usr_dlg().Warn_many("", "", "custom script failed: ~{0}", script);
			}
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Cfg__web_enabled = "xowa.app.web.enabled", Cfg__logs_enabled = "xowa.app.logs.enabled", Cfg__script = "xowa.app.startup.script";
}
