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
public class Gfui_tab_itm extends GfuiElemBase {
	private Gxw_tab_itm under;
	public String Tab_name() {return under.Tab_name();} public void Tab_name_(String v) {under.Tab_name_(v);}
	public String Tab_tip_text() {return under.Tab_tip_text();} public void Tab_tip_text_(String v) {under.Tab_tip_text_(v);}
	public void Subs_add(GfuiElem elem) {under.Subs_add(elem);}
	public static Gfui_tab_itm kit_(Gfui_kit kit, String key, Gxw_tab_itm under, Keyval_hash ctor_args) {
		Gfui_tab_itm rv = new Gfui_tab_itm();
		// rv.ctor_kit_GfuiElemBase(kit, key, (GxwElem)under, ctor_args);	// causes swt_tab_itm to break, since it's not a real Swt Control
		rv.under = under;
		return rv;
	}
}
