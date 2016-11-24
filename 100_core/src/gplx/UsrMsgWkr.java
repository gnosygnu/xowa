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
