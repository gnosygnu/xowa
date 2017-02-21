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
package gplx.xowa.apps.apis.xowa.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.apis.xowa.gui.pages.*;
public class Xoapi_page implements Gfo_invk {
	public void Init_by_kit(Xoae_app app) {
		view.Init_by_kit(app);
		selection.Init_by_kit(app);
		edit.Init_by_kit(app);
	}
	public Xoapi_view			View() {return view;} private Xoapi_view view = new Xoapi_view();
	public Xoapi_edit			Edit() {return edit;} private Xoapi_edit edit = new Xoapi_edit();
	public Xoapi_selection		Selection() {return selection;} private Xoapi_selection selection = new Xoapi_selection();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_view))	 		return view;
		else if	(ctx.Match(k, Invk_selection)) 		return selection;
		else if	(ctx.Match(k, Invk_edit)) 			return edit;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_view = "view", Invk_selection = "selection", Invk_edit = "edit";
}
