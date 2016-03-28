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
public class GfuiFormPanel extends GfuiElemBase {
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.Width_(60);	// default to 60; do not force callers to always set width
		GfuiWin ownerForm = (GfuiWin)ctorArgs.Get_val_or(GfuiElem_.InitKey_ownerWin, null);

		GfoFactory_gfui.Btn_MoveBox(this, ownerForm);
		GfoFactory_gfui.Btn_MinWin2(this);
		GfoFactory_gfui.Btn_QuitWin3(this);

		this.Lyt_activate();
		this.Lyt().Bands_add(GftBand.new_().Cells_num_(3));
	}
	public static GfuiFormPanel new_(GfuiWin form) {
		GfuiFormPanel rv = new GfuiFormPanel();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_().Add(GfuiElem_.InitKey_ownerWin, form));
		rv.Owner_(form, "formPanel");
		return rv;
	}
}
