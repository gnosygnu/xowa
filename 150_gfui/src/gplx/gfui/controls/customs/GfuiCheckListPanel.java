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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.layouts.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
public class GfuiCheckListPanel extends GfuiElemBase {
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		InitToggleWidget();
		InitReverseWidget();
		InitListBoxWidget();

		this.Lyt_activate();
		this.Lyt().Bands_add(GftBand.new_().Cell_abs_(60));
		this.Lyt().Bands_add(GftBand.new_().Cell_abs_(70));
		this.Lyt().Bands_add(GftBand.fillAll_());
	}
	public void Items_clear() {listBox.Items_clear();}
	public void Items_add(Object item, boolean checkBoxState) {listBox.Items_add(item, checkBoxState);}
	public List_adp Items_getAll() {return listBox.Items_getAll();}
	public List_adp Items_getChecked() {return listBox.Items_getChecked();}
	public void SetAllCheckStates(boolean v) {
		listBox.Items_setAll(v);
	}
	void InitToggleWidget() {
		toggle = GfuiChkBox_.invk_("toggle", this, "&toggle", this, ToggleChecks_cmd); toggle.Size_(60, 20); toggle.AlignH_set(GfuiAlign_.Left);
	}
	void InitReverseWidget() {
		GfuiBtn_.invk_("reverse", this, this, ReverseChks_cmd).Size_(70, 20).Text_("&reverse");
	}
	void InitListBoxWidget() {
		listBox.Owner_(this, "listBox");
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, ToggleChecks_cmd))		SetAllCheckStates(toggle.Val());
		else if	(ctx.Match(k, ReverseChks_cmd))			listBox.Items_reverse();
		else return super.Invk(ctx, ikey, k, m);
		return this;
	}	public static final    String ToggleChecks_cmd = "ToggleChecks", ReverseChks_cmd = "ReverseChks";
	GfuiChkBox toggle;
	GfuiCheckListBox listBox = GfuiCheckListBox.new_();
	public static GfuiCheckListPanel new_() {
		GfuiCheckListPanel rv = new GfuiCheckListPanel();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}
}
