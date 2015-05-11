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
public class Gfo_usr_dlg__gui_ {
	public static final Gfo_usr_dlg__gui Noop		= new Gfo_usr_dlg__gui_noop();
	public static final Gfo_usr_dlg__gui Console	= new Gfo_usr_dlg__gui_console();
	public static final Gfo_usr_dlg__gui Test		= new Gfo_usr_dlg__gui_test();
}
class Gfo_usr_dlg__gui_noop implements Gfo_usr_dlg__gui {
	public void Clear() {}
	public String_ring Prog_msgs() {return ring;} String_ring ring = new String_ring().Max_(0);
	public void Write_prog(String text) {}
	public void Write_note(String text) {}
	public void Write_warn(String text) {}
	public void Write_stop(String text) {}
}
class Gfo_usr_dlg__gui_console implements Gfo_usr_dlg__gui {
	public void Clear() {}
	public String_ring Prog_msgs() {return ring;} String_ring ring = new String_ring().Max_(0);
	public void Write_prog(String text) {console.WriteTempText(text);}
	public void Write_note(String text) {console.WriteLine(text);}
	public void Write_warn(String text) {console.WriteLine(text);}
	public void Write_stop(String text) {console.WriteLine(text);}
	ConsoleAdp console = ConsoleAdp._;
}
