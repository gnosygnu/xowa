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
public interface UsrMsgWkr {
	void ExecUsrMsg(int type, UsrMsg umsg);
}
class UsrMsgWkrList {
	public void Add(UsrMsgWkr v) {
		if (wkr == null && list == null)
			wkr = v;
		else {
			if (list == null) {
				list = List_adp_.New();
				list.Add(wkr);
				wkr = null;
			}
			list.Add(v);
		}
	}
	public void Del(UsrMsgWkr v) {
//			list.Del(v);
	}
	public void Exec(UsrMsg umsg) {
		if (wkr != null)
			wkr.ExecUsrMsg(type, umsg);
		else if (list != null) {
			for (Object lObj : list) {
				UsrMsgWkr l = (UsrMsgWkr)lObj;
				l.ExecUsrMsg(type, umsg);
			}
		}
	}
	List_adp list; UsrMsgWkr wkr; int type;
        public UsrMsgWkrList(int type) {this.type = type;}
}
