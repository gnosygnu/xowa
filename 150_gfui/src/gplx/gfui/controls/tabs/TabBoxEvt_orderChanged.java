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
package gplx.gfui.controls.tabs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
public class TabBoxEvt_orderChanged {
	public int CurIdx() {return curIdx;} public TabBoxEvt_orderChanged CurIdx_(int v) {curIdx = v; return this;} int curIdx;
	public int NewIdx() {return newIdx;} public TabBoxEvt_orderChanged NewIdx_(int v) {newIdx = v; return this;} int newIdx;

	public static final    String OrderChanged_evt = "OrderChanged_evt";
	public static void Publish(TabBox tabBox, int curIdx, int newIdx) {
		Gfo_evt_mgr_.Pub_vals(tabBox, OrderChanged_evt, Keyval_.new_("curIdx", curIdx), Keyval_.new_("newIdx", newIdx));
	}
	public static TabBoxEvt_orderChanged Handle(GfsCtx ctx, GfoMsg m) {
		TabBoxEvt_orderChanged rv = new TabBoxEvt_orderChanged();
		rv.curIdx = m.ReadInt("curIdx");
		rv.newIdx = m.ReadInt("newIdx");
		return rv;
	}
}
