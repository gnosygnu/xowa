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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.layouts.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.elems.*;
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
