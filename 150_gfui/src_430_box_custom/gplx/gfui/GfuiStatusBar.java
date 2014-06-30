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
public class GfuiStatusBar extends GfuiElemBase {
	public GfuiStatusBox Box() {return statusBox;} GfuiStatusBox statusBox;
	public GfuiMoveElemBtn MoveButton() {return moveBtn;} GfuiMoveElemBtn moveBtn;
	void StatusBar_Focus() {
		statusBox.Focus_able_(true);
		statusBox.Visible_set(true);
		statusBox.Focus();
	}		
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, StatusBarFocus_cmd))			StatusBar_Focus();
		else return super.Invk(ctx, ikey, k, m);
		return this;
	}	public static final String StatusBarFocus_cmd = "StatusBarFocus";
	@Override public void ctor_GfuiBox_base(KeyValHash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		moveBtn = GfuiMoveElemBtn.new_();
		statusBox = GfuiStatusBox_.new_("statusBox");
		statusBox.Owner_(this).Border_off_().Visible_on_();
		moveBtn.Owner_(this, "moveBox");
		GfoFactory_gfui.Btn_MinWin2(this);
		GfoFactory_gfui.Btn_QuitWin3(this);
		this.Lyt_activate();
		this.Lyt().Bands_add(GftBand.new_().Cell_pct_(100).Cell_abs_(20).Cell_abs_(20).Cell_abs_(20));
	}
	public static GfuiStatusBar new_() {
		GfuiStatusBar rv = new GfuiStatusBar();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}
}
