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
package gplx.xowa.apps.apis.xowa.gui.browsers;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.xowa.*;
import gplx.gfui.controls.standards.*; import gplx.xowa.guis.views.*;
public class Xoapi_search implements Gfo_invk {
	public void Init_by_kit(Xoae_app app) {this.app = app;} private Xoae_app app;
	private GfuiTextBox Search_box() {return app.Gui_mgr().Browser_win().Search_box();}
	private Xog_win_itm Win() {return app.Gui_mgr().Browser_win();}
	public void Focus() {this.Search_box().Focus_select_all();}
	public void Exec()	{this.Win().Page__navigate_by_search();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_focus)) 					this.Focus();
		else if	(ctx.Match(k, Invk_exec)) 					this.Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_focus = "focus";
	public static final String Invk_exec = "exec";
}
