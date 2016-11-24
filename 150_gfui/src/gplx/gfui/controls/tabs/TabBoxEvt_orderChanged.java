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
package gplx.gfui.controls.tabs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public class TabBoxEvt_orderChanged {
	public int CurIdx() {return curIdx;} public TabBoxEvt_orderChanged CurIdx_(int v) {curIdx = v; return this;} int curIdx;
	public int NewIdx() {return newIdx;} public TabBoxEvt_orderChanged NewIdx_(int v) {newIdx = v; return this;} int newIdx;

	public static final    String OrderChanged_evt = "OrderChanged_evt";
	public static void Publish(TabBox tabBox, int curIdx, int newIdx) {
		Gfo_evt_mgr_.Pub_vals(tabBox, OrderChanged_evt, Keyval_.new_("curIdx", curIdx), Keyval_.new_("newIdx", newIdx));
	}
	public static TabBoxEvt_orderChanged Handle(GfsCtx ctx, GfoMsg m) {
		TabBoxEvt_orderChanged rv = new TabBoxEvt_orderChanged();
		rv.curIdx = m.ReadInt("curIdx");
		rv.newIdx = m.ReadInt("newIdx");
		return rv;
	}
}
