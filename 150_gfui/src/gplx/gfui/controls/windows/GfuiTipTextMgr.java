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
