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
import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiTextBox_ {
	public static GfuiTextBox as_(Object obj) {return obj instanceof GfuiTextBox ? (GfuiTextBox)obj : null;}
	public static GfuiTextBox cast(Object obj) {try {return (GfuiTextBox)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiTextBox.class, obj);}}
	public static final    String NewLine = "\n";	
	public static final    String Ctor_Memo = "TextBox_Memo";

	public static GfuiTextBox new_() {
		GfuiTextBox rv = new GfuiTextBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
	public static GfuiTextBox fld_(String key, GfuiElem owner) {
		GfuiTextBox rv = new_();
		rv.Owner_(owner, key);
		return rv;
	}
	public static GfuiTextMemo multi_(String key, GfuiElem owner) {
		GfuiTextMemo rv = new GfuiTextMemo();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		rv.Key_of_GfuiElem_(key).Owner_(owner);
		return rv;
	}
	public static GfuiTextBox kit_(Gfui_kit kit, String key, GxwTextFld wk_textBox, Keyval_hash ctorArgs) {
		GfuiTextBox rv = new GfuiTextBox();
		rv.ctor_kit_GfuiElemBase(kit, key, wk_textBox, ctorArgs);
		return rv;
	}
}
