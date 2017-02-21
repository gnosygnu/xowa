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
