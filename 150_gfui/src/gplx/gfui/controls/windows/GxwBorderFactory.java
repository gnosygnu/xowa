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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import gplx.*; import gplx.gfui.*;
import gplx.gfui.kits.core.GfuiEnv_;
import gplx.langs.gfs.GfsCore;
public class GxwBorderFactory {
	public static final javax.swing.border.Border Empty = new EmptyBorder(0, 0, 1, 0);	
}
class GfuiMenuBarItmCmd implements ActionListener {
	public void actionPerformed(ActionEvent ev) {
		try {
			GfsCore.Instance.ExecOne(GfsCtx.Instance, GfuiMenuBarItm.CmdMsg(itm));
		}
		catch (Exception e) {
			GfuiEnv_.ShowMsg(Err_.Message_gplx_full(e));
		}
	}
	public static GfuiMenuBarItmCmd new_(GfuiMenuBarItm itm) {
		GfuiMenuBarItmCmd cmd = new GfuiMenuBarItmCmd();
		cmd.itm = itm;
		return cmd;
	}	GfuiMenuBarItm itm;
}
