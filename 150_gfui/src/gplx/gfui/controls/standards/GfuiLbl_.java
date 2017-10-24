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
import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiLbl_ {
	public static GfuiLbl sub_(String key, GfuiElem owner) {
		GfuiLbl rv = new_();
		rv.Owner_(owner, key); // must be after ctor, else "Error creating window handle"
		rv.TextMgr().AlignH_(GfuiAlign_.Mid);
		return rv;
	}
	public static GfuiLbl kit_(Gfui_kit kit, String key, GxwElem elm, Keyval_hash ctorArgs) {
		GfuiLbl rv = new GfuiLbl();
		rv.ctor_kit_GfuiElemBase(kit, key, elm, ctorArgs);
		return rv;
	}
	public static GfuiLbl prefix_(String key, GfuiElem owner, String text) {
		GfuiLbl rv = sub_(key, owner);
		rv.Text_(text);
		rv.TextMgr().AlignH_(GfuiAlign_.Left);
		return rv;
	}
	public static GfuiLbl menu_(String key, GfuiElem owner, String text, String tipText) {
		GfuiLbl rv = sub_(key, owner);
		rv.Text_(text);
		rv.TextMgr().AlignH_(GfuiAlign_.Mid);
		rv.TipText_(tipText);
		rv.Border().All_(PenAdp_.black_());
		return rv;
	}
	public static final    String Text_Null = null;
	@gplx.Internal protected static GfuiLbl new_() {
		GfuiLbl rv = new GfuiLbl();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_false_());
		return rv;
	}
}
