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
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import gplx.gfui.controls.elems.*;
class GfuiTipTextMgr implements GfuiWinOpenAble {
		public void Open_exec(GfuiWin form, GfuiElemBase owner, GfuiElemBase sub) {
		if (!(sub.UnderElem() instanceof JComponent)) return;
		if (String_.Eq(sub.TipText(), "")) return;	// don't register components without tooltips; will leave blue dots (blue tool tip windows with 1x1 size)
		JComponent jcomp = (JComponent)sub.UnderElem();
		ToolTipManager.sharedInstance().registerComponent(jcomp);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		ToolTipManager.sharedInstance().setDismissDelay(1000);
		ToolTipManager.sharedInstance().setReshowDelay(0);
		jcomp.setToolTipText(sub.TipText());
	}
		public static final    GfuiTipTextMgr Instance = new GfuiTipTextMgr();
}
