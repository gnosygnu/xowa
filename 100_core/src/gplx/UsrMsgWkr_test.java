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
public class UsrMsgWkr_test implements UsrMsgWkr {
	public void ExecUsrMsg(int type, UsrMsg m) {
		msgs.Add(m);
	}
	public boolean HasWarn(UsrMsg um) {
		for (int i = 0; i < msgs.Count(); i++) {
			UsrMsg found = (UsrMsg)msgs.Get_at(i);
			if (String_.Eq(um.To_str(), found.To_str())) return true;
		}
		return false;
	}
	public static UsrMsgWkr_test RegAll(UsrDlg dlg) {
		UsrMsgWkr_test wkr = new UsrMsgWkr_test();
		dlg.Reg(UsrMsgWkr_.Type_Note, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Stop, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Warn, wkr);
		return wkr;
	}
	List_adp msgs = List_adp_.New();
}
