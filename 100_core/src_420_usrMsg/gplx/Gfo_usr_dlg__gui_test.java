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
import gplx.core.strings.*;
public class Gfo_usr_dlg__gui_test implements Gfo_usr_dlg__gui {
	public String[] Xto_str_ary()			{return msgs.To_str_ary();}
	public String[] Xto_str_ary_and_clear() {String[] rv = msgs.To_str_ary(); this.Clear(); return rv;}
	public List_adp Warns() {return warns;}
	public String_ring Prog_msgs() {return ring;} String_ring ring = new String_ring().Max_(0);
	public void Clear() {msgs.Clear(); warns.Clear();}
	public void Write_prog(String text) {msgs.Add(text);} List_adp msgs = List_adp_.new_();
	public void Write_note(String text) {msgs.Add(text);}
	public void Write_warn(String text) {warns.Add(text);} List_adp warns = List_adp_.new_();
	public void Write_stop(String text) {msgs.Add(text);}
}
