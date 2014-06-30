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
package gplx.gfui; import gplx.*;
public class TabBoxEvt_tabSelect {
	public static String Key = "TabBoxEvt_tabSelect";
	public static final String SelectedChanged_evt = "SelectedChanged_evt";
	public static void Send(TabBoxMgr tabBoxMgr, TabPnlItm oldTab, TabPnlItm newTab) {
		GfoEvMgr_.PubVal(tabBoxMgr, Key, new TabPnlItm[] {oldTab, newTab});
	}
	@gplx.Internal protected static void Select(TabBox tabBox, GfsCtx ctx, GfoMsg m) {
		TabPnlItm[] ary = (TabPnlItm[])m.CastObj("v");
		Select(tabBox, ary[0], ary[1]);
	}
	@gplx.Internal protected static void Select(TabBox tabBox, TabPnlItm curTabItm, TabPnlItm newTabItm) {
		TabPnlAreaMgr.Select(tabBox, curTabItm, newTabItm);
		TabBtnAreaMgr.Select(tabBox, curTabItm, newTabItm);
		GfoEvMgr_.PubVal(tabBox, SelectedChanged_evt, newTabItm.Idx());
	}		
	public static int Handle(GfsCtx ctx, GfoMsg m) {
		return m.ReadInt("v");
	}
}
