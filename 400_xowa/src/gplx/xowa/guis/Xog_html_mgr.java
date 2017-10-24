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
package gplx.xowa.guis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.portal.*;
public class Xog_html_mgr implements Gfo_invk {
	public Xog_html_mgr(Xoae_app app) {portal_mgr = new Xoa_portal_mgr(app);}
	public Xoa_portal_mgr Portal_mgr() {return portal_mgr;} private Xoa_portal_mgr portal_mgr;
	public String Auto_focus_id() {return auto_focus_id;} private String auto_focus_id = "";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_auto_focus_id_))				auto_focus_id = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_portal))						return portal_mgr;
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_auto_focus_id_ = "auto_focus_id_", Invk_portal = "portal";
}
