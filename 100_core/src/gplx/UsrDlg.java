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
public class UsrDlg {
	public int Verbosity() {return verbosity;} public UsrDlg Verbosity_(int v) {verbosity = v; return this;} int verbosity = UsrMsgWkr_.Type_Note;
	public void Note(String text, Object... ary)		{Exec(text, ary, noteWkrs);}
	public void Warn(String text, Object... ary)		{Exec(text, ary, warnWkrs);}
	public void Stop(String text, Object... ary)		{Exec(text, ary, stopWkrs);}
	public void Note(UsrMsg msg)							{Exec(UsrMsgWkr_.Type_Note, msg);}
	public void Warn(UsrMsg msg)							{Exec(UsrMsgWkr_.Type_Warn, msg);}
	public void Stop(UsrMsg msg)							{Exec(UsrMsgWkr_.Type_Stop, msg);}
	public void Exec(int type, UsrMsg umsg) {
		UsrMsgWkrList list = GetList(type);
		list.Exec(umsg);
	}
	void Exec(String text, Object[] ary, UsrMsgWkrList list) {
		String msg = String_.Format(text, ary);
		list.Exec(UsrMsg.new_(msg));
	}
	public void Reg(int type, UsrMsgWkr wkr) {
		UsrMsgWkrList list = GetList(type);
		list.Add(wkr);
	}
	public void RegOff(int type, UsrMsgWkr wkr) {
		UsrMsgWkrList list = GetList(type);
		list.Del(wkr);
	}
	UsrMsgWkrList GetList(int type) {
		if		(type == UsrMsgWkr_.Type_Note)			return noteWkrs;
		else if (type == UsrMsgWkr_.Type_Warn)			return warnWkrs;
		else if (type == UsrMsgWkr_.Type_Stop)			return stopWkrs;
		else throw Err_.new_unhandled(type);
	}
	UsrMsgWkrList noteWkrs = new UsrMsgWkrList(UsrMsgWkr_.Type_Note), warnWkrs = new UsrMsgWkrList(UsrMsgWkr_.Type_Warn), stopWkrs = new UsrMsgWkrList(UsrMsgWkr_.Type_Stop);
	public static UsrDlg new_() {return new UsrDlg();}
}
