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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.controls.elems.*;
public class GfuiFocusMgr implements Gfo_evt_mgr_owner {
	public static final    String FocusChanged_evt = "focusChanged_evt";
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public GfuiElem FocusedElem() {return focusedElem;} GfuiElem focusedElem;
	public GfuiElem FocusedElemPrev() {return focusedElemPrev;} GfuiElem focusedElemPrev;
	public void FocusedElem_set(GfuiElem focused) {
		focusedElemPrev = focusedElem;
		this.focusedElem = focused;
		Gfo_evt_mgr_.Pub_val(this, FocusChanged_evt, focused);
	}
	public static final    GfuiFocusMgr Instance = new GfuiFocusMgr(); GfuiFocusMgr() {}
}
