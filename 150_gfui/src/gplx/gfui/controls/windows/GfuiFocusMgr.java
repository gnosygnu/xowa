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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.controls.elems.*;
public class GfuiFocusMgr implements Gfo_evt_mgr_owner {
	public static final    String FocusChanged_evt = "focusChanged_evt";
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public GfuiElem FocusedElem() {return focusedElem;} GfuiElem focusedElem;
	public GfuiElem FocusedElemPrev() {return focusedElemPrev;} GfuiElem focusedElemPrev;
	public void FocusedElem_set(GfuiElem focused) {
		focusedElemPrev = focusedElem;
		this.focusedElem = focused;
		Gfo_evt_mgr_.Pub_val(this, FocusChanged_evt, focused);
	}
	public static final    GfuiFocusMgr Instance = new GfuiFocusMgr(); GfuiFocusMgr() {}
}
