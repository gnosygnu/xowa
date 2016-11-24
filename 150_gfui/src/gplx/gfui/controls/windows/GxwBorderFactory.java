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
