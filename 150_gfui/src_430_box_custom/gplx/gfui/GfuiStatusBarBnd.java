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
public class GfuiStatusBarBnd implements InjectAble {
	public GfuiStatusBar Bar() {return statusBar;} GfuiStatusBar statusBar = GfuiStatusBar.new_();
	public void Inject(Object owner) {
		GfuiWin form = GfuiWin_.as_(owner); if (form == null) throw Err_.new_type_mismatch(GfuiWin.class, owner);
		statusBar.Owner_(form, "statusBar");
		IptBnd_.cmd_to_(IptCfg_.Null, form, statusBar, GfuiStatusBar.StatusBarFocus_cmd, IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.T));
		statusBar.MoveButton().TargetElem_set(form);
	}
	public static GfuiStatusBarBnd new_() {return new GfuiStatusBarBnd();} GfuiStatusBarBnd() {}
}
