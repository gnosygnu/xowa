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
public abstract class GfuiIoUrlSelectBox extends GfuiElemBase {
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		label = GfuiLbl_.sub_("label", this);
		pathBox.Owner_(this, "pathBox");
		GfuiBtn_.invk_("selectPath", this, this, SelectAction_cmd).Text_("...");

		this.Lyt_activate();
		this.Lyt().Bands_add(GftBand.new_().Cell_abs_(60).Cell_pct_(100).Cell_abs_(30));
	}
	public static final String PathSelected_evt = "PathSelected_evt";
	public GfuiLbl Label() {return label;} GfuiLbl label;
	public Io_url Url() {return Io_url_.new_any_(pathBox.TextMgr().Val());}
	public Io_url StartingFolder() {return startingFolder;}
	public void StartingFolder_set(Io_url v) {this.startingFolder = v;} Io_url startingFolder = Io_url_.Empty;
	@Override public void Focus() {pathBox.Focus();}

	void SelectAction() {
		Io_url selectedPath = SelectAction_hook(this.startingFolder);
		if (selectedPath.EqNull()) return;
		pathBox.Text_(selectedPath.Raw());
		GfoEvMgr_.Pub(this, PathSelected_evt);
	}
	protected abstract Io_url SelectAction_hook(Io_url startingFolder);
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, SelectAction_cmd))		SelectAction();
		else return super.Invk(ctx, ikey, k, m);
		return this;
	}	public static final String SelectAction_cmd = "SelectAction";
	
	GfuiComboBox pathBox = GfuiComboBox.new_();
}
class IoFileSelector extends GfuiIoUrlSelectBox { 	@Override protected Io_url SelectAction_hook(Io_url startingFolder) {
		return GfuiIoDialogUtl.SelectFile(startingFolder);
	}
	public static IoFileSelector new_() {
		IoFileSelector rv = new IoFileSelector();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}	IoFileSelector() {}
}
class IoFolderSelector extends GfuiIoUrlSelectBox {		@Override protected Io_url SelectAction_hook(Io_url startingFolder) {
		Io_url url = GfuiIoDialogUtl.SelectDir(startingFolder);
		this.StartingFolder_set(url);
		return url;
	}
	public static IoFolderSelector new_() {
		IoFolderSelector rv = new IoFolderSelector();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}	IoFolderSelector() {}
}
