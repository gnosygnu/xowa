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
public class Gfo_usr_dlg_ui_test implements Gfo_usr_dlg_ui {
	public String[] Xto_str_ary() {return msgs.XtoStrAry();}
	public ListAdp Warns() {return warns;}
	public String_ring Prog_msgs() {return ring;} String_ring ring = new String_ring().Max_(0);
	public void Clear() {msgs.Clear(); warns.Clear();}
	public void Write_prog(String text) {msgs.Add(text);} ListAdp msgs = ListAdp_.new_();
	public void Write_note(String text) {msgs.Add(text);}
	public void Write_warn(String text) {warns.Add(text);} ListAdp warns = ListAdp_.new_();
	public void Write_stop(String text) {msgs.Add(text);}
}
