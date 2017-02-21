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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.gfxs.*; import gplx.gfui.imgs.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiLbl extends GfuiElemBase { // standard label does not support tooltips
	@Override public void Click() {
		int focusOrder = this.OwnerElem().SubElems().IndexOfA(this);
		GfuiElem focusNext = this.OwnerElem().SubElems().Get_at(focusOrder + 1);	// FIXME: incorporate into new FocusOrder
		focusNext.Focus();
	}
	@Override public boolean PaintCbk(PaintArgs args) {
		super.PaintCbk(args);
		this.TextMgr().DrawData(args.Graphics());
		return true;
	}		
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.CustomDraw_set(true);
	}
	@Override public void ctor_kit_GfuiElemBase(Gfui_kit kit, String key, GxwElem underElem, Keyval_hash ctorArgs) {
		super.ctor_kit_GfuiElemBase(kit, key, underElem, ctorArgs);
		this.CustomDraw_set(true);
	}
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.lbl_();}
}
