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
import gplx.core.consoles.*;
public class UsrMsgWkr_console implements UsrMsgWkr {
	public void ExecUsrMsg(int type, UsrMsg umsg) {
		String text = umsg.To_str();
		if		(type == UsrMsgWkr_.Type_Warn)
			text = "!!!!" + text;
		else if (type == UsrMsgWkr_.Type_Stop)
			text = "****" + text;
		Console_adp__sys.Instance.Write_str(text);
	}
	public static void RegAll(UsrDlg dlg) {
		UsrMsgWkr wkr = new UsrMsgWkr_console();
		dlg.Reg(UsrMsgWkr_.Type_Note, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Stop, wkr);
		dlg.Reg(UsrMsgWkr_.Type_Warn, wkr);
	}
}
