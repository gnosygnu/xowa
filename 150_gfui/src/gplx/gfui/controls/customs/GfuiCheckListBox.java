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
import gplx.core.lists.*;
import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiCheckListBox extends GfuiElemBase {
	public void Items_reverse() {checkListBox.Items_reverse();}
	public void Items_count() {checkListBox.Items_count();}
	public void Items_setAt(int i, boolean v) {checkListBox.Items_setCheckedAt(i, v);}
	public void Items_setAll(boolean v) {checkListBox.Items_setAll(v);}
	public void Items_clear() {checkListBox.Items_clear();}
	public void Items_add(Object item, boolean v) {checkListBox.Items_add(item, v);}
	public List_adp Items_getAll() {return checkListBox.Items_getAll();}
	public List_adp Items_getChecked() {return checkListBox.Items_getChecked();}

	GxwCheckListBox checkListBox;
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return new GxwCheckListBox_lang();}
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.checkListBox = (GxwCheckListBox)UnderElem();
	}
	public static GfuiCheckListBox new_() {
		GfuiCheckListBox rv = new GfuiCheckListBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
}
