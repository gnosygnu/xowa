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
package gplx;
public class UsrMsgWkr_console implements UsrMsgWkr {
	public void ExecUsrMsg(int type, UsrMsg umsg) {
		String text = umsg.XtoStr();
		if		(type == UsrMsgWkr_.Type_Warn)
			text = "!!!!" + text;
		else if (type == UsrMsgWkr_.Type_Stop)
			text = "****" + text;
		ConsoleAdp._.WriteText(text);
	}
	public static void RegAll(UsrDlg dlg) {
		UsrMsgWkr wkr = new UsrMsgWkr_console();
		dlg.Reg(UsrMsgWkr_.Type_Note, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Stop, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Warn, wkr);
	}
}
