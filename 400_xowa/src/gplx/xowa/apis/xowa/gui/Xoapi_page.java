/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.apis.xowa.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.apis.xowa.gui.pages.*;
public class Xoapi_page implements GfoInvkAble {
	public void Init_by_kit(Xoa_app app) {
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
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_view = "view", Invk_selection = "selection", Invk_edit = "edit";
}
