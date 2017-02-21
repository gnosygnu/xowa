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
package gplx.xowa.bldrs.setups.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
public class Xoi_addon_mgr implements Gfo_invk {
	Xoi_firefox_installer Firefox() {return firefox;} private Xoi_firefox_installer firefox = new Xoi_firefox_installer();
	public void Init_by_app(Xoae_app app) {
		firefox.Init_by_app(app);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_firefox)) 		return firefox;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_firefox = "firefox";
}
